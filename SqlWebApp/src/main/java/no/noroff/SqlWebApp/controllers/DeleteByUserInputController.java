package no.noroff.SqlWebApp.controllers;

import no.noroff.SqlWebApp.DeleteInput;
import no.noroff.SqlWebApp.SqlWebApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class DeleteByUserInputController {

    DeleteInput deleteInput;
    private String templateName = "deleteTemplate";

    @GetMapping("/delete")
    public String delete(Model model) {
        model.addAttribute("deleteInput", new DeleteInput());
        return templateName;
    }

    @PostMapping("/delete") //use the shorthand
    public String deleteResult(@ModelAttribute("deleteInput") DeleteInput deleteInput) {
        this.deleteInput = deleteInput;
        System.out.println("You are trying to delete person, pID=" + deleteInput.getValue());

        SqlWebApplication.sqlConn.delete(Integer.parseInt(deleteInput.getValue()));
        return "redirect:/persons/";
    }



}


