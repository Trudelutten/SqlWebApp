package no.noroff.SqlWebApp.models;

public class PhoneNumber {
    private int pID;
    private int pnID;
    private String phoneCategory;
    private String phoneNumber;

    //Constructors


    public PhoneNumber() {
    }

    public PhoneNumber(int pID, String phoneCategory, String phoneNumber) {
        this.pID = pID;
        this.phoneCategory = phoneCategory;
        this.phoneNumber = phoneNumber;
    }

    public PhoneNumber (int pnID, int pID, String phoneCategory, String phoneNumber) {
        this.pID = pID;
        this.pnID= pnID;
        this.phoneCategory = phoneCategory;
        this.phoneNumber = phoneNumber;
    }


    public int getpID() {
        return pID;
    }

    public int getPnID() {
        return pnID;
    }

    public String getPhoneCategory() {
        return phoneCategory;
    }

    public void setPhoneCategory(String phoneCategory) {
        this.phoneCategory = phoneCategory;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
