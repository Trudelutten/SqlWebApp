package no.noroff.SqlWebApp;

public class DeleteInput {
    private String value;

    public DeleteInput() {
    }

    public DeleteInput(String value) {
        this.value = value;
    }

    public void setValue(String v) {
        value = v;
    }
    public String getValue(){
        return value;
    }

}
