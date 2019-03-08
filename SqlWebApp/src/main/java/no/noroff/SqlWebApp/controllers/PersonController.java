package no.noroff.SqlWebApp.controllers;

import no.noroff.SqlWebApp.SqlWebApplication;
import no.noroff.SqlWebApp.UserInput;
import no.noroff.SqlWebApp.models.Person;
import no.noroff.SqlWebApp.models.PhoneNumber;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

@RestController
public class PersonController {

    @GetMapping("/persons")
    public ArrayList<Person> personsGet() {
        System.out.println("Trying to find person: ");
        ArrayList<Person> personList = SqlWebApplication.sqlConn.selectAllPersons();
        return personList;
    }

    @GetMapping("/menu/{value}/{attribute}")
    public ArrayList<Person> personSearch(@PathVariable("value") String v, @PathVariable("attribute") String a ) {
        ArrayList<Person> personList = new ArrayList<>();
        if (a.equals("FirstName")|| a.equals("LastName")) {
            personList = SqlWebApplication.sqlConn.selectPersonByName(a,v);
        } else if (a.equals("PhoneNumber")) {
            ArrayList<PhoneNumber> phoneList = SqlWebApplication.sqlConn.selectAllEqualPhoneNumbers(v);
            for (PhoneNumber phoneNumber: phoneList) {
                personList.add(SqlWebApplication.sqlConn.selectPerson(phoneNumber.getpID()));
            }
        }
        return personList;

    }

    @GetMapping("/persons/{pID}")
    public Person personGet(@PathVariable int pID) {

        System.out.println("Trying to find person: " + pID);

        Person person = SqlWebApplication.sqlConn.selectPerson(pID);

        return person;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/persons")
    public Person insertNewPerson(@RequestBody Person person) {
        System.out.println("Person" + person.getFirstName() + "added");
        System.out.println(person.getDateOfBirth());
        SqlWebApplication.sqlConn.insertPerson(person);
        return person;
    }
}
