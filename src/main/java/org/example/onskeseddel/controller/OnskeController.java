package org.example.onskeseddel.controller;

import org.example.onskeseddel.Model.OnskeModel;
import org.example.onskeseddel.Model.Onskeliste;
import org.example.onskeseddel.Service.OnskeService;
import org.example.onskeseddel.Service.OnskelisteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
public class OnskeController {

    private final OnskelisteService onskelisteService;
    private final OnskeService onskeService;

    public OnskeController(OnskelisteService onskelisteService, OnskeService onskeService) {
        this.onskelisteService = onskelisteService;
        this.onskeService = onskeService;
    }

    @GetMapping("/")
    public String forside(Model model) {
        model.addAttribute("lister", onskelisteService.hentAlle());
        model.addAttribute("nyListe", new Onskeliste());
        return "index";
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
                .path("/del/" + liste.getDelingskode())
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
}