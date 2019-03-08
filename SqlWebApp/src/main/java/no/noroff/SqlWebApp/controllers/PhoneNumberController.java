package no.noroff.SqlWebApp.controllers;


import no.noroff.SqlWebApp.SqlWebApplication;
import no.noroff.SqlWebApp.models.Person;
import no.noroff.SqlWebApp.models.PhoneNumber;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class PhoneNumberController {

    @GetMapping("/phonenumber/{pnID}")
    public PhoneNumber phoneNumberGet(@PathVariable int pnID) {

        System.out.println("Trying to find phone number: " + pnID);

        PhoneNumber phoneNumber = SqlWebApplication.sqlConn.selectPhoneNumber(pnID);

        return phoneNumber;

    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/phonenumber")
    public PhoneNumber insertNewPhoneNumber(@RequestBody PhoneNumber phoneNumber) {
        System.out.println("phoneNumber" + phoneNumber.getPhoneNumber() + "added");
        System.out.println(phoneNumber.getPhoneCategory());
        SqlWebApplication.sqlConn.insertPhoneNumber(phoneNumber);
        return phoneNumber;
    }
}
