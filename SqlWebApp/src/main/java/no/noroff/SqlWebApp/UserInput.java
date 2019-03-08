package no.noroff.SqlWebApp;

public class UserInput {
    private String value;
    private String attribute;

    public void setValue(String v){value = v;}
    public void setAttribute(String v){attribute = v;}

    public String getValue(){return value;}
    public String getAttribute(){return attribute;}

}
