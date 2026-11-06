package org.example.onskeseddel.controller;
import org.example.onskeseddel.Model.OnskeModel;
import org.example.onskeseddel.Service.OnskeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
public class OnskeController {
    private final OnskeService onskeService;


    public OnskeController(OnskeService onskeService) {
        this.onskeService = onskeService;
    }

    @RequestMapping("ØnskeSeddel")
    public String visonske() {
        return "/ØnskeSeddel";
    }

    @GetMapping("/oprettelse")
    public String OpretOnske(Model model) {
        OnskeModel onske = new OnskeModel();
        model.addAttribute("OpretOnske", onske);
        return "/oprettelse";
    }
   @PostMapping ("/oprettelse")
    public String gemOnske(@ModelAttribute OnskeModel onskeModel){
          onskeService.OpretOnske(onskeModel);
       return "redirect:/hentOnske";
   }
@GetMapping ("/hentOnske")
    public String hentOnske(Model model){
    ArrayList<OnskeModel> hent = onskeService.hentOnske();
    model.addAttribute("onskerListe", hent);
    return "hentOnske";
}

}





