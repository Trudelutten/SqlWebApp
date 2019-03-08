package no.noroff.SqlWebApp.models;

public class Email {
    private int eID;
    private int pID;
    private String emailCategory;
    private String email;

    public Email() {
    }

    public Email(int pID, String emailCategory, String email) {
        this.pID = pID;
        this.emailCategory = emailCategory;
        this.email = email;
    }

    //Constructor
    public Email (int eID, int pID, String emailCategory, String email) {
        this.eID = eID;
        this.pID = pID;
        this.emailCategory = emailCategory;
        this.email = email;
    }


    /* GETTERS*/
    public int geteID() {
        return eID;
    }
    public int getpID() {
        return pID;
    }
    public String getEmailCategory() {
        return emailCategory;
    }
    public String getEmail() {
        return (email);
    }

    /* SETTERS */
    public void setEmail(String email) {
        this.email = email;
    }
    public void setEmailCategory(String emailCategory) {
        this.emailCategory = emailCategory;
    }
}
