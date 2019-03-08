package no.noroff.SqlWebApp.controllers;

import no.noroff.SqlWebApp.SqlWebApplication;
import no.noroff.SqlWebApp.models.PhoneNumber;
import no.noroff.SqlWebApp.models.Relationship;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.Relation;

@RestController
public class RelationshipsController {


    @GetMapping("/relationship/{rID}")
    public Relationship relationshipGet(@PathVariable int rID) {

        System.out.println("Trying to find relationship: " + rID);

        Relationship relationship = SqlWebApplication.sqlConn.selectRelationship(rID);

        return relationship;
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/relationship")
    public Relationship insertNewRelationship(@RequestBody Relationship relationship) {
        System.out.println("relationship added");
        SqlWebApplication.sqlConn.insertRelationship(relationship);
        return relationship;
    }
}
