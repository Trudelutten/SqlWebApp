package no.noroff.SqlWebApp;

public class UserUpdateInput {
    private long id;
    private String tableName;
    private String attributeName;
    private String value;

    public UserUpdateInput() {
    }

    public UserUpdateInput(long id, String attributeName, String value) {
        this.id = id;
        this.tableName = "Persons";
        this.attributeName = attributeName;
        this.value = value;
    }

    public UserUpdateInput(long id, String tableName, String attributeName, String value) {
        this.id = id;
        this.tableName = tableName;
        this.attributeName = attributeName;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
