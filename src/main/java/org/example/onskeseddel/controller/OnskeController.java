package org.example.onskeseddel.controller;

import org.example.onskeseddel.Model.OnskeModel;
import org.example.onskeseddel.Model.Onskeliste;
import org.example.onskeseddel.Service.OnskeService;
import org.example.onskeseddel.Service.OnskelisteService;
import org.example.onskeseddel.Service.ProfilService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@Controller
public class OnskeController {

    private final OnskelisteService onskelisteService;
    private final OnskeService onskeService;
    private final ProfilService profilService;

    public OnskeController(OnskelisteService onskelisteService,
                           OnskeService onskeService,
                           ProfilService profilService) {
        this.onskelisteService = onskelisteService;
        this.onskeService = onskeService;
        this.profilService = profilService;
    }

    @GetMapping("/")
    public String forside(Model model) {
        String navn = profilService.hentNavn();
        model.addAttribute("navn", navn);
        model.addAttribute("initialer", profilService.initialer(navn));
        model.addAttribute("antalLister", profilService.antalLister());
        model.addAttribute("antalOnsker", profilService.antalOnsker());
        model.addAttribute("lister", onskelisteService.hentAlle());
        model.addAttribute("nyListe", new Onskeliste());
        return "index";
    }
    //ss
    @GetMapping ("/profil")
    public String getProfil(Model model){
        model.addAttribute("navn", profilService.hentNavn());
        return "profil";
    }

    @PostMapping("/profil")
    public String gemProfil(@RequestParam String navn, RedirectAttributes ra) {
        try {
            profilService.gemNavn(navn);
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("fejl", e.getMessage());
        }
        return "redirect:/profil";
    }

    @PostMapping("/lister")
    public String opretListe(@ModelAttribute Onskeliste nyListe, RedirectAttributes ra) {
        try {
            onskelisteService.opret(nyListe);
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("fejl", e.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping("/lister/{listeId}")
    public String visListe(@PathVariable int listeId, Model model, RedirectAttributes ra) {
        Onskeliste liste = onskelisteService.hentMedOnsker(listeId);
        if (liste == null) {
            ra.addFlashAttribute("fejl", "Ønskesedlen findes ikke");
            return "redirect:/";
        }
        String delingslink = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/del/" + liste.getDelingsKode())
                .toUriString();

        model.addAttribute("liste", liste);
        model.addAttribute("nytOnske", new OnskeModel());
        model.addAttribute("delingslink", delingslink);
        return "liste";
    }

    @PostMapping("/lister/{listeId}/onsker")
    public String tilfoejOnske(@PathVariable int listeId,
                               @ModelAttribute OnskeModel nytOnske,
                               RedirectAttributes ra) {
        nytOnske.setOnskelisteId(listeId);
        try {
            onskeService.tilfoej(nytOnske);
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("fejl", e.getMessage());
        }
        return "redirect:/lister/" + listeId;
    }

    @PostMapping("/lister/{listeId}/onsker/{onskeId}/slet")
    public String sletOnske(@PathVariable int listeId, @PathVariable int onskeId) {
        onskeService.slet(onskeId);
        return "redirect:/lister/" + listeId;
    }

    @PostMapping("/lister/{listeId}/slet")
    public String sletListe(@PathVariable int listeId) {
        onskelisteService.slet(listeId);
        return "redirect:/";
    }

    @GetMapping("/del/{delingskode}")
    public String delt(@PathVariable String delingskode, Model model, RedirectAttributes ra) {
        Onskeliste liste = onskelisteService.hentViaDelingskode(delingskode);
        if (liste == null) {
            ra.addFlashAttribute("fejl", "Delingslinket er ugyldigt");
            return "redirect:/";
        }
        model.addAttribute("liste", liste);
        return "del";

    }
    @PostMapping("/venner/find")
    public String findVen(@RequestParam String link, RedirectAttributes ra) {
        Onskeliste liste = onskelisteService.findViaLinkEllerKode(link);
        if (liste == null) {
            ra.addFlashAttribute("fejl", "Vi kunne ikke finde en ønskeliste med det link");
            return "redirect:/";
        }
        return "redirect:/del/" + liste.getDelingsKode();

    }
    @PostMapping("/del/{delingskode}/onsker/{onskeId}/koebt")
    public String markerKoebt(@PathVariable String delingskode,
                              @PathVariable int onskeId,
                              RedirectAttributes ra) {
        if (!onskeService.markerSomKoebt(onskeId, delingskode)) {
            ra.addFlashAttribute("fejl", "Ønsket kunne ikke markeres. Måske er det allerede købt.");
        }
        return "redirect:/del/" + delingskode;
    }
}