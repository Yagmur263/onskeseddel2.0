package org.example.onskeseddel.controller;
import org.example.onskeseddel.Model.OnskeModel;
import org.example.onskeseddel.Repository.OnskeRepository;
import org.example.onskeseddel.Service.OnskeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OnskeController {
    private final OnskeService onskeService;


    public OnskeController(OnskeService onskeService) {
        this.onskeService = onskeService;
    }

    @RequestMapping("/ØnskeSeddel")
    public String visonske() {
        return "/ØnskeSeddel";
    }

    @GetMapping("/oprettelse")
    public String OpretOnske(Model model) {
        OnskeModel onske = new OnskeModel();
        model.addAttribute("OpretOnske", onske);
        return "/oprettelse";
    }

    @PostMapping("/oprettelse")
    public String gemOnske(@ModelAttribute OnskeModel onskemodel) {
        return "redirect/";
    }

}





