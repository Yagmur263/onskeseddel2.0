package org.example.onskeseddel.Service;

import org.example.onskeseddel.Model.Onskeliste;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.example.onskeseddel.Model.OnskeModel;

    @SpringBootTest
    @AutoConfigureMockMvc
    class OnskeIntegrationTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private OnskelisteService onskelisteService;

        private final List<Integer> oprettedeLister = new ArrayList<>();

        @Autowired
        private OnskeService onskeService;

        @AfterEach
        void ryd() {
            oprettedeLister.forEach(onskelisteService::slet);
        }

        private Onskeliste opretTestliste() {
            String titel = "Test " + UUID.randomUUID();
            Onskeliste liste = new Onskeliste();
            liste.setTitel(titel);
            onskelisteService.opret(liste);

            Onskeliste gemt = onskelisteService.hentAlle().stream()
                    .filter(l -> l.getTitel().equals(titel))
                    .findFirst()
                    .orElseThrow();
            oprettedeLister.add(gemt.getId());
            return gemt;
        }

        @Test
        void forsidenKanVises() throws Exception {
            mockMvc.perform(get("/"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("index"));
        }

        @Test
        void opretListeViaFormularGemmerIDatabasen() throws Exception {
            String titel = "Test " + UUID.randomUUID();

            mockMvc.perform(post("/lister").param("titel", titel))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/"));

            Onskeliste fundet = onskelisteService.hentAlle().stream()
                    .filter(l -> l.getTitel().equals(titel))
                    .findFirst()
                    .orElse(null);
            assertNotNull(fundet);
            oprettedeLister.add(fundet.getId());
        }

        @Test
        void tomTitelOpretterIkkeEnListe() throws Exception {
            int foer = onskelisteService.hentAlle().size();

            mockMvc.perform(post("/lister").param("titel", "   "))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(flash().attributeExists("fejl"));

            assertEquals(foer, onskelisteService.hentAlle().size());
        }

        @Test
        void tilfoejOnskeGemmesPaaListen() throws Exception {
            Onskeliste liste = opretTestliste();

            mockMvc.perform(post("/lister/" + liste.getId() + "/onsker")
                            .param("navn", "Ny laptop")
                            .param("pris", "7999.00")
                            .param("antal", "1"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/lister/" + liste.getId()));

            Onskeliste hentet = onskelisteService.hentMedOnsker(liste.getId());
            assertEquals(1, hentet.getOnsker().size());
            assertEquals("Ny laptop", hentet.getOnsker().get(0).getNavn());
        }

        @Test
        void delingslinketViserListen() throws Exception {
            Onskeliste liste = opretTestliste();

            mockMvc.perform(get("/del/" + liste.getDelingsKode()))
                    .andExpect(status().isOk())
                    .andExpect(view().name("del"));
        }

        @Test
        void ugyldigDelingskodeSenderTilForsiden() throws Exception {
            mockMvc.perform(get("/del/findes-ikke"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/"));
        }

        @Test
        void vennerKnappenAabnerListeViaLink() throws Exception {
            Onskeliste liste = opretTestliste();

            mockMvc.perform(post("/venner/find")
                            .param("link", "http://localhost:8080/del/" + liste.getDelingsKode()))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/del/" + liste.getDelingsKode()));

        }
        private int opretOnske(Onskeliste liste) {
            OnskeModel onske = new OnskeModel();
            onske.setOnskelisteId(liste.getId());
            onske.setNavn("Bøger");
            onske.setPris(100);
            onske.setAntal(1);
            onskeService.tilfoej(onske);
            return onskelisteService.hentMedOnsker(liste.getId()).getOnsker().get(0).getId();
        }

        @Test
        void gaestKanMarkereOnskeSomKoebtEnGang() throws Exception {
            Onskeliste liste = opretTestliste();
            int onskeId = opretOnske(liste);
            String url = "/del/" + liste.getDelingsKode() + "/onsker/" + onskeId + "/koebt";

            mockMvc.perform(post(url))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(flash().attributeCount(0));

            assertTrue(onskelisteService.hentMedOnsker(liste.getId()).getOnsker().get(0).isKoebt());

            mockMvc.perform(post(url))
                    .andExpect(flash().attributeExists("fejl"));
        }

        @Test
        void onskeKanIkkeMarkeresMedForkertDelingskode() throws Exception {
            Onskeliste liste = opretTestliste();
            int onskeId = opretOnske(liste);

            mockMvc.perform(post("/del/" + UUID.randomUUID() + "/onsker/" + onskeId + "/koebt"))
                    .andExpect(flash().attributeExists("fejl"));

            assertFalse(onskelisteService.hentMedOnsker(liste.getId()).getOnsker().get(0).isKoebt());
        }
    }

