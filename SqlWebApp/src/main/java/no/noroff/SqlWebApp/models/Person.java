package no.noroff.SqlWebApp.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static no.noroff.SqlWebApp.SqlWebApplication.sqlConn;

public class Person {
    private int pID; // Never to be changed, thus final.
    private String firstName;
    private String lastName;
    private String homeAddress;
    private Date dateOfBirth;

    private Map<String, String> phoneNumbers = new HashMap<>(); // <PhoneCategory, phoneNumber>
    private Map<String, String> emails = new HashMap<>(); // <EmailCategory , emailAddress>
    private Map<String, String> relations = new HashMap<>();

    public Person(){
    }

    public Person(String firstName, String lastName, String homeAddress, Date dateOfBirth){
        this.firstName = firstName;
        this.lastName = lastName;
        this.homeAddress = homeAddress;
        this.dateOfBirth = dateOfBirth;

        setEmails();
        setPhoneNumbers();
        theRelations();
    }

    public Person(int pID, String firstName, String lastName, String homeAddress, Date dateOfBirth){
        this.pID = pID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.homeAddress = homeAddress;
        this.dateOfBirth = dateOfBirth;

        setEmails();
        setPhoneNumbers();
        theRelations();
    }


    public int getpID() {
        return pID;
    }

    public void setpID(int pID) {
        this.pID = pID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Map<String, String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(Map<String, String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public Map<String, String> getEmails() {
        return emails;
    }

    public void setEmails(Map<String, String> emails) {
        this.emails = emails;
    }

    public Map<String, String> getRelations() {
        return relations;
    }

    public void setRelations(Map<String, String> relations) {
        this.relations = relations;
    }

    private void setEmails() {
        ArrayList<Email> emailList = sqlConn.selectEmailList(pID);
        for(Email email: emailList){
            emails.put(email.getEmailCategory(), email.getEmail());
        }
    }

    private void setPhoneNumbers() {
        ArrayList<PhoneNumber> phoneNumberList = sqlConn.selectPhoneNumberListBypID(pID);
        for (PhoneNumber number: phoneNumberList) {
            phoneNumbers.put(number.getPhoneCategory(), number.getPhoneNumber());
        }
    }


    private void theRelations() {
        ArrayList<Relationship> allRelationships = sqlConn.selectPersonalRelationship(pID);
        for (Relationship relation : allRelationships) {
            if (pID == relation.getP1()) {
                relations.put(relation.getP1p2(),
                        "http://localhost:8080/persons/" + relation.getP2());
            } else if (pID == relation.getP2()) {
                relations.put(relation.getP2p1(),
                        "http://localhost:8080/persons/" + relation.getP1());
            }
        }
    }
}
