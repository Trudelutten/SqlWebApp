package no.noroff.SqlWebApp.controllers;

import no.noroff.SqlWebApp.SqlWebApplication;
import no.noroff.SqlWebApp.models.Email;
import no.noroff.SqlWebApp.models.PhoneNumber;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmailController {

    @GetMapping("/email/{eID}")
    public Email emailGet(@PathVariable int eID) {

        System.out.println("Trying to find email: " + eID);

        Email email = SqlWebApplication.sqlConn.selectEmail(eID);

        return email;

    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/email")
    public Email insertNewEmail(@RequestBody Email email) {
        System.out.println("phoneNumber" + email.getEmail() + "added");
        System.out.println(email.getEmailCategory());
        SqlWebApplication.sqlConn.insertEmails(email);
        return email;
    }

}