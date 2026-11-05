package org.example.onskeseddel.Service;

import org.example.onskeseddel.Model.OnskeModel;
import org.example.onskeseddel.Model.Onskeliste;
import org.example.onskeseddel.Repository.OnskeRepository;
import org.example.onskeseddel.Repository.OnskelisteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OnskelisteServiceTest {

    @Mock
    private OnskelisteRepository onskelisteRepository;

    @Mock
    private OnskeRepository onskeRepository;

    @InjectMocks
    private OnskelisteService service;

    @Test
    void opretMedTomTitelKasterFejl() {
        Onskeliste liste = new Onskeliste();
        liste.setTitel("");

        assertThrows(IllegalArgumentException.class, () -> service.opret(liste));
        verify(onskelisteRepository, never()).gem(any());
    }

    @Test
    void opretMedKunMellemrumKasterFejl() {
        Onskeliste liste = new Onskeliste();
        liste.setTitel("   ");

        assertThrows(IllegalArgumentException.class, () -> service.opret(liste));
    }

    @Test
    void opretMedNullTitelKasterFejl() {
        Onskeliste liste = new Onskeliste();
        liste.setTitel(null);

        assertThrows(IllegalArgumentException.class, () -> service.opret(liste));
    }

    @Test
    void opretFjernerMellemrumOmkringTitel() {
        Onskeliste liste = new Onskeliste();
        liste.setTitel("  Jul 2026  ");

        service.opret(liste);

        ArgumentCaptor<Onskeliste> captor = ArgumentCaptor.forClass(Onskeliste.class);
        verify(onskelisteRepository).gem(captor.capture());
        assertEquals("Jul 2026", captor.getValue().getTitel());
    }

    @Test
    void hentMedOnskerReturnererNullHvisListenIkkeFindes() {
        when(onskelisteRepository.findById(99)).thenReturn(null);

        assertNull(service.hentMedOnsker(99));
        verify(onskeRepository, never()).findAlleForListe(anyInt());
    }

    @Test
    void hentMedOnskerFylderListenMedOnsker() {
        Onskeliste liste = new Onskeliste();
        liste.setId(1);
        liste.setTitel("Jul 2026");

        OnskeModel laptop = new OnskeModel();
        laptop.setNavn("Ny laptop");

        when(onskelisteRepository.findById(1)).thenReturn(liste);
        when(onskeRepository.findAlleForListe(1)).thenReturn(List.of(laptop));

        Onskeliste resultat = service.hentMedOnsker(1);

        assertEquals(1, resultat.getOnsker().size());
        assertEquals("Ny laptop", resultat.getOnsker().get(0).getNavn());
    }

    @Test
    void hentAlleReturnererAlleListerFraRepository() {
        when(onskelisteRepository.findAlle()).thenReturn(List.of(new Onskeliste(), new Onskeliste()));

        assertEquals(2, service.hentAlle().size());
    }
}