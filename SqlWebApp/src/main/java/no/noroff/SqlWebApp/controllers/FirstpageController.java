package no.noroff.SqlWebApp.controllers;

import no.noroff.SqlWebApp.UserInput;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstpageController {
    String templateName = "firstpageTemplate";
    @GetMapping("/")
    public String firstPage(Model model) {
        model.addAttribute("userInput", new UserInput());
        return templateName;
    }
}
