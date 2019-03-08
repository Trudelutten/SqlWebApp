package no.noroff.SqlWebApp.controllers;

import no.noroff.SqlWebApp.SqlWebApplication;
import no.noroff.SqlWebApp.UserUpdateInput;
import no.noroff.SqlWebApp.models.Email;
import no.noroff.SqlWebApp.models.Person;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UpdateController {

    UserUpdateInput userUpdateInput;
    private String templateName = "updateTemplate";

    @GetMapping("/update")
    public String update(Model model) {
        model.addAttribute("userUpdateInput", new UserUpdateInput());
        return templateName;
    }

    @PostMapping("/update") //use the shorthand
    public String deleteResult(@ModelAttribute("userUpdateInput") UserUpdateInput userUpdateInput,
                               RedirectAttributes re) {
        this.userUpdateInput = userUpdateInput;
        System.out.println("You are trying to update id: " + userUpdateInput.getId());

        System.out.println(userUpdateInput.getAttributeName());
        SqlWebApplication.sqlConn.updateTable("Persons", userUpdateInput.getId(), userUpdateInput.getAttributeName(), userUpdateInput.getValue());

        re.addAttribute("pid", userUpdateInput.getId());

        return "redirect:/persons/{pid}";
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/update-any")
    public void updateAnyTable(@RequestBody UserUpdateInput userUpdateInput) {

        System.out.println("Updating table" + userUpdateInput.getTableName());
        SqlWebApplication.sqlConn.updateTable(userUpdateInput.getTableName(),
                                        userUpdateInput.getId(),
                                        userUpdateInput.getAttributeName(),
                                        userUpdateInput.getValue());

    }

}
