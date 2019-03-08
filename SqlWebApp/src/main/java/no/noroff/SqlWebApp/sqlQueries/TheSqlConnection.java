package no.noroff.SqlWebApp.sqlQueries;

import no.noroff.SqlWebApp.enumerators.EmailCategories;
import no.noroff.SqlWebApp.enumerators.PhoneCategories;
import no.noroff.SqlWebApp.models.Email;
import no.noroff.SqlWebApp.models.Person;
import no.noroff.SqlWebApp.models.PhoneNumber;
import no.noroff.SqlWebApp.models.Relationship;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

public class TheSqlConnection {
    private Connection conn = null;

    // Data for constructing our initial sqlite database
    String[] firstName = {"Leon", "Eliot", "Malak", "Katya", "Tobias", "Natalya", "Kelise", "Cieran", "Duke", "Lilly"};
    String[] lastName = {"Greig", "Villarreal", "Payne", "Whitfield", "Oliver", "Pace", "Sheldon", "Young", "Gray", "Hawkins"};
    String[] homeAddress = {"27 Shelby Ave", "8705 Kevin Ln", "24 South St", "5435 Louise Ave", "5414 County 150 Rd",
            "407 Carolina St", "26 9th Ave", "405 Sunrise Ave", "6 Buttonball Dr", "911 Clare Ave"};
    LocalDate[] dateOfBirth = { LocalDate.of(1980,01,1), LocalDate.of(1960,03,6),
            LocalDate.of(1980,06,7), LocalDate.of(2000,12,3),
            LocalDate.of(2010,07,21), LocalDate.of(1991,11,14),
            LocalDate.of(2001,03,30), LocalDate.of(1920,10,2),
            LocalDate.of(1999,04,12), LocalDate.of(1954,06,13)};
    String[] phoneNumbers = {"12345678", "23456789", "34567890", "45678901", "56789012", "67890123", "78901234", "89012345", "90123456", "12345678"};
    String[] phoneNumbersWork = {"22345678", "22456789", "22567890", "22678901", "22789012", "22890123", "22901234", "22012345", "22123456", "22345678"};
    String[] emails = new String[10];
    String[] emailsWork = new String[10];
    {
        for (int i = 0; i < firstName.length; i++) {
            emails[i] = firstName[i] + "." + lastName[i] + "@craigmail.com";
            emailsWork[i] = firstName[i] + "." + lastName[i] + "@workplace.com";
        }
    }


    public void connect() {
        String url = "jdbc:sqlite:src/main/resources/HappyFamily.sqlite";
        //String url = "jdbc:sqlite::resource:HappyFamily.sqlite";

        try {
            conn = DriverManager.getConnection(url);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //Close the connection
    public void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
        }
    }

    /* TABLE INITIALIZERS */
    public void initAllTables() {
        initPersons();
        initPhoneNumbers();
        initEmails();
        initRelationships();
    }

    public void initPersons() {
        //Connection conn = connect();
        // Checks whether table exists
        // if it doesn't exist to the following
        // 1. Create the table
        // 2. Fill the table

        String createStatement = "CREATE TABLE Persons (\n" +
                "    pID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "    FirstName varchar(255) NOT NULL,\n" +
                "    LastName varchar(255) NOT NULL,\n" +
                "    HomeAddress varchar(255),\n" +
                "    DateOfBirth Date\n" +
                "    );\n" +
                "\n";


        try (PreparedStatement pstmt = conn.prepareStatement(createStatement)) {
            pstmt.execute();
            System.out.println("Table Persons created");

            // FILL TABLE
            for (int i = 0; i < firstName.length; i++) {
                insertPerson(firstName[i], lastName[i], homeAddress[i], dateOfBirth[i]);
            }

        } catch (SQLException e) {
            System.out.println("Persons table creation statement failed. Maybe it already exists.");
        }

    }

    public void initPhoneNumbers() {
        // Checks whether table exists
        // if it doesn't exist to the following
        // 1. Create the table
        // 2. Fill the table
        //TODO: constraints on number


        String createStatement = "CREATE TABLE PhoneNumbers (\n" +
                "        pnID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "        pID int NOT NULL,\n" +
                "        PhoneCategory varchar(255),\n" +
                "        Number varchar(8),\n" +
                "        FOREIGN KEY(pID) REFERENCES Persons(pID)\n" +
                "    )";

        try ( PreparedStatement pstmt = conn.prepareStatement(createStatement)){
            // CREATE TABLE
            pstmt.execute();
            System.out.println("Table PhoneNumbers created");

            // FILL TABLE
            int p=1;
            for (int i = 0; i < firstName.length; i++) {
                insertPhoneNumber(p, PhoneCategories.MOBILE.toString(), phoneNumbers[i]);
                insertPhoneNumber(p, PhoneCategories.WORK.toString(), phoneNumbersWork[i]);
                p++;

            }


        } catch (SQLException E) {
            System.out.println("PhoneNumbers table creation statement failed. Maybe it already exists");
        }
    }

    public void initEmails() {
        // Checks whether table exists
        // if it doesn't exist to the following
        // 1. Create the table
        // 2. Fill the table


        String createStatement = "CREATE TABLE Emails (\n" +
                "    eID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "    pID int NOT NULL,\n" +
                "    EmailCategory varchar(255),\n" +
                "    Email varchar(255) NOT NULL,\n" +
                "    FOREIGN KEY(pID) REFERENCES Persons(pID)\n" +
                "    )";

        try ( PreparedStatement pstmt = conn.prepareStatement(createStatement)){
            // CREATE TABLE
            pstmt.execute();
            System.out.println("Table Emails created");

            // FILL TABLE
            int p = 1;
            for (int i = 0; i < firstName.length; i++) {
                insertEmails(p, EmailCategories.PERSONAL.toString(), emails[i]);
                insertEmails(p, EmailCategories.WORK.toString(), emailsWork[i]);
                p++;

            }

        } catch (SQLException E) {
            System.out.println("Emails table creation statement failed. Maybe it already exists.");
        }
    }

    public void initRelationships() {
        // Checks whether table  exists
        // if it doesn't exist to the following
        // 1. Create the table
        // 2. Fill the table


        String createStatement = "CREATE TABLE Relationships (\n" +
                "    rID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "    p1 int NOT NULL,\n" +
                "    p2 int NOT NULL,\n" +
                "    p1p2 varchar(255) NOT NULL,\n" +
                "    p2p1 varchar(255) NOT NULL,\n" +
                "    FOREIGN KEY(p1) REFERENCES Persons(pID),\n" +
                "    FOREIGN KEY(p2) REFERENCES Persons(pID)\n" +
                "    )";

      
        try ( PreparedStatement pstmt = conn.prepareStatement(createStatement)){
            // CREATE TABLE
            pstmt.execute();
            System.out.println("Table Relationship created");

            // FILL TABLE
            insertRelationship(1,2,"Brother","Brother");
            insertRelationship(4,5,"Sister","Brother");
            insertRelationship(8,10,"Father","Daughter");
            insertRelationship(10, 9, "Mother", "Son" );
        } catch (SQLException E) {
            System.out.println("Relationships table creation statement failed");
        }
    }

    /* INSERT METHODS */
    public int insertPerson(String firstName, String lastName, String homeAddress, LocalDate dateOfBirth) {
        // Inserts given person into table Person
        // returns pID
        //String sql = "INSERT INTO warehouses(name,capacity) VALUES(?,?)";
        //TODO:DATEOFBIRTH
        String sql = "INSERT INTO Persons(FirstName, LastName, HomeAddress, DateOfBirth) VALUES(?,?,?,?)";
        try {
            if (conn != null) {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, firstName);
                pstmt.setString(2, lastName);
                pstmt.setString(3, homeAddress);
                pstmt.setObject(4, Date.valueOf(dateOfBirth));
                pstmt.executeUpdate();
                return 0;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 1;
        }
        return -1;
    }

    public void insertPerson(Person person) {
        insertPerson(person.getFirstName(),
                    person.getLastName(),
                    person.getHomeAddress(),
                    person.getDateOfBirth().toLocalDate());
    }

    public int insertPhoneNumber(int pID, String pCategory, String phoneNumber) {
        // Inserts given phone number into table PhoneNumbers
        // returns pnID
        String sql = "INSERT INTO PhoneNumbers(pID, PhoneCategory, Number) VALUES(?,?,?)";

        if (checkPID(pID)) {
            try {
                if (conn != null) {
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, pID);
                    pstmt.setString(2, pCategory.toLowerCase());
                    pstmt.setString(3, phoneNumber);
                    pstmt.executeUpdate();
                    return 0;
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return 1;
            }
        } else {
            System.out.printf("The person (pID = %s) you are trying to insert phone number for does not exist.", pID);
        }
        return -1;
    }


    public void insertPhoneNumber(PhoneNumber phoneNumber) {
        insertPhoneNumber(phoneNumber.getpID(),
                        phoneNumber.getPhoneCategory(),
                        phoneNumber.getPhoneNumber());
    }

    // INSERT INTO EMAILS
    public int insertEmails(int pID, String emailCategory, String email) {
        // Inserts given phone number into table PhoneNumbers
        // returns pnID
        String sql = "INSERT INTO Emails(pID, EmailCategory, Email) VALUES(?,?,?)";

        if (checkPID(pID)) {

            try {
                if (conn != null) {
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, pID);
                    pstmt.setString(2, emailCategory.toLowerCase());
                    pstmt.setString(3, email);
                    pstmt.executeUpdate();
                    return 0;
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return 1;
            }
        } else {
            System.out.printf("The person (pID = %s) you are trying to insert email for does not exist.", pID);
        }
        return -1;
    }


    public void insertEmails(Email email) {
        insertEmails(email.getpID(),
                email.getEmailCategory(),
                email.getEmail());
    }


    // INSERT INTO RELATIONSHIPS
    public int insertRelationship(int p1, int p2, String rel1, String rel2) {
        String sql = "INSERT INTO Relationships(p1, p2, p1p2, p2p1) VALUES(?,?,?,?)";

        if (checkPID(p1) && checkPID(p2)) {
            try {
                if (conn != null) {
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, p1);
                    pstmt.setInt(2, p2);
                    pstmt.setString(3, rel1);
                    pstmt.setString(4, rel2);
                    pstmt.executeUpdate();
                    return 0;
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return 1;
            }
        }else {
            System.out.printf("Either person p1 = %s or person p2 = %s does not exist, so cannot form relationship.", p1, p2);
        }
        return -1;
    }


    public void insertRelationship(Relationship relationship) {
        insertRelationship(relationship.getP1(),
                        relationship.getP2(),
                        relationship.getP1p2(),
                        relationship.getP2p1());
    }


    /*  BASIC SELECTS*/
    public Person selectPerson(int pID) {
        String sql = "SELECT * FROM Persons WHERE pID = (?)";

        Person person = null;

        try {
            if (conn != null) {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, pID);
                ResultSet rs = pstmt.executeQuery();

                person = new Person(rs.getInt("pID"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("HomeAddress"),
                        rs.getDate("DateOfBirth"));
            }
        } catch (SQLException e) {
            System.out.println("Person (pID=" + pID + ") SELECT not working.");
            System.out.println(e.getMessage());
        }
        return person;
    }

    public PhoneNumber selectPhoneNumber (int pnID) {
        String sql = "SELECT * FROM PhoneNumbers WHERE pnID = (?)";
        PhoneNumber phoneNumber = null;

        try{
            if(conn != null) {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, pnID);
                ResultSet rs = pstmt.executeQuery();

                phoneNumber = new PhoneNumber(rs.getInt("pnID"),
                        rs.getInt("pID"),
                        rs.getString("PhoneCategory"),
                        rs.getString("Number"));
            }
        } catch (SQLException exc) {
            System.out.println("Phone number (pnID=" + pnID + ") SELECT not working.");
            System.out.println(exc.getMessage());
        }

        return phoneNumber;
    }

    public Relationship selectRelationship (int rID) {
        String sql = "SELECT * FROM Relationships WHERE rID = (?)";
        Relationship relationship = null;

        try{
            if(conn != null) {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, rID);
                ResultSet rs = pstmt.executeQuery();

                relationship = new Relationship(rs.getInt("rID"),
                        rs.getInt("p1"),
                        rs.getInt("p2"),
                        rs.getString("p1p2"),
                        rs.getString("p2p1"));
            }
        } catch (SQLException exc) {
            System.out.println("Relationship (rID=" + rID + ") SELECT not working.");
            System.out.println(exc.getMessage());
        }

        return relationship;

    }

    public Email selectEmail (int eID) {
        String sql = "SELECT * FROM Emails WHERE eID = (?)";
        Email email = null;

        try{
            if(conn != null) {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, eID);
                ResultSet rs = pstmt.executeQuery();

                email = new Email(rs.getInt("eID"),
                        rs.getInt("pID"),
                        rs.getString("EmailCategory"),
                        rs.getString("Email"));
            }
        } catch (SQLException exc) {
            System.out.println("Email (eID=" + eID + ") SELECT not working.");
            System.out.println(exc.getMessage());
        }

        return email;

    }

    /* ADVANCED SELECTS */
    /**
     * Returns a list of Person objects who have given firstname or given lastname
     * @param attribute = "FirstName" or "LastName"
     * @param searchName
     * @return
     */
    public ArrayList<Person> selectPersonByName(String attribute, String searchName) {
        ArrayList<Person> personList = new ArrayList<Person>();

        String sql = String.format("SELECT * FROM Persons WHERE %s = ? ", attribute);
        Person person = null;

        try {
            if (conn != null) {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, searchName);
                ResultSet rs = pstmt.executeQuery();

                while(rs.next()) {
                    person = new Person(rs.getInt("pID"),
                            rs.getString("FirstName"),
                            rs.getString("LastName"),
                            rs.getString("HomeAddress"),
                            rs.getDate("DateOfBirth"));
                    personList.add(person);
                }
            }
        } catch (SQLException e) {
            System.out.println("Person (Name=" + searchName + ") NOT Found.");
            System.out.println(e.getMessage());
        }
        return personList;
    }

    /**
     * Returns a list of PhoneNumber objects that contains given phone number.
     * @param searchNumber
     * @return
     */
    public ArrayList<PhoneNumber> selectAllEqualPhoneNumbers(String searchNumber) {
        ArrayList<PhoneNumber> phoneBook = new ArrayList<PhoneNumber>();

        String sql = "SELECT * FROM PhoneNumbers WHERE Number = ? ";
        PhoneNumber phoneNumber = null;

        try {
            if (conn != null) {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, searchNumber);
                ResultSet rs = pstmt.executeQuery();

                while(rs.next()) {
                    phoneNumber = new PhoneNumber(rs.getInt("pnID"),
                            rs.getInt("pID"),
                            rs.getString("PhoneCategory"),
                            rs.getString("Number"));
                    phoneBook.add(phoneNumber);
                }
            }
        } catch (SQLException e) {
            System.out.println("PhoneNumber (Number=" + searchNumber + ") NOT Found.");
            System.out.println(e.getMessage());
        }
        return phoneBook;
    }

    public ArrayList<PhoneNumber> selectPhoneNumberListBypID(int pID) {
        ArrayList<PhoneNumber> phoneBook = new ArrayList<PhoneNumber>();

        String sql = "SELECT * FROM PhoneNumbers WHERE pID = ? ";
        PhoneNumber phoneNumber = null;

        try {
            if (conn != null) {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, pID);
                ResultSet rs = pstmt.executeQuery();

                while(rs.next()) {
                    phoneNumber = new PhoneNumber(rs.getInt("pnID"),
                            rs.getInt("pID"),
                            rs.getString("PhoneCategory"),
                            rs.getString("Number"));
                    phoneBook.add(phoneNumber);
                }
            }
        } catch (SQLException e) {
            System.out.println("PhoneNumber (pID = " + pID + ") NOT Found.");
            System.out.println(e.getMessage());
        }
        return phoneBook;
    }

    /**
     * Returns a list of Email objects owned by given person.
     * @param pID
     * @return
     */
    public ArrayList<Email> selectEmailList (int pID) {
        ArrayList<Email> emailBook = new ArrayList<Email>();
        String sql = "SELECT * FROM Emails WHERE pID = (?)";
        Email email = null;

        try{
            if(conn != null) {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, pID);
                ResultSet rs = pstmt.executeQuery();

                while(rs.next()) {
                    email = new Email(rs.getInt("eID"),
                            rs.getInt("pID"),
                            rs.getString("EmailCategory"),
                            rs.getString("Email"));
                    emailBook.add(email);
                }
            }
        } catch (SQLException exc) {
            System.out.println("Email (pID=" + pID + ") SELECT not working.");
            System.out.println(exc.getMessage());
        }

        return emailBook;

    }

    public ArrayList<Relationship> selectPersonalRelationship(int pID) {
        String sql1 = "SELECT * FROM Relationships WHERE p1 = (?)";
        String sql2 = "SELECT * FROM Relationships WHERE p2 = (?)";
        ArrayList<Relationship> allRelations = new ArrayList<Relationship>();
        //Relationship relation = null;

        try{
            if(conn != null) {
                PreparedStatement pstmt1 = conn.prepareStatement(sql1);
                PreparedStatement pstmt2 = conn.prepareStatement(sql2);
                pstmt1.setInt(1, pID);
                pstmt2.setInt(1, pID);

                ResultSet rs1 = pstmt1.executeQuery();
                ResultSet rs2 = pstmt2.executeQuery();

                int rID=-1, p1=-1, p2=-1;
                String p1p2 = null, p2p1 = null;
                while(rs1.next()) {
                    rID = rs1.getInt("rID");
                    p1 = rs1.getInt("p1");
                    p2 = rs1.getInt("p2");
                    p1p2 = rs1.getString("p1p2");
                    p2p1 = rs1.getString("p2p1");
                    Relationship relation = new Relationship(rID, p1, p2, p1p2, p2p1);
                    allRelations.add(relation);
                }
                while(rs2.next()) {
                    Relationship relation = new Relationship(rs2.getInt("rID"),
                            rs2.getInt("p1"),
                            rs2.getInt("p2"),
                            rs2.getString("p1p2"),
                            rs2.getString("p2p1"));
                    allRelations.add(relation);
                }

            }
        } catch (SQLException exc) {
            System.out.println("Relationship (pID=" + pID + ") SELECT not working.");
            System.out.println(exc.getMessage());
        }

        return allRelations;
    }

    /* TOTAL SELECTS */
    public ArrayList<Relationship> selectAllRelations() {
        ArrayList<Relationship> allRelationships = new ArrayList<Relationship>();
        String sql = "SELECT * FROM Relationships";
        Relationship relation = null;

        try {
            if (conn != null) {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();

                while(rs.next()) {
                    relation = new Relationship(rs.getInt("rID"),
                            rs.getInt("p1"),
                            rs.getInt("p2"),
                            rs.getString("p1p2"),
                            rs.getString("p2p1"));

                    allRelationships.add(relation);
                    System.out.println("Made new relationship");


                }
            }
        } catch (SQLException e) {
            System.out.println("Not able to get Relationships.");
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return allRelationships;
    }

    public ArrayList<Person> selectAllPersons() {
        ArrayList<Person> personList = new ArrayList<Person>();
        String sql = "SELECT * FROM Persons";
        Person person = null;

        try {
            if (conn != null) {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();

                while(rs.next()) {
                    person = new Person(rs.getInt("pID"),
                            rs.getString("FirstName"),
                            rs.getString("LastName"),
                            rs.getString("HomeAddress"),
                            rs.getDate("DateOfBirth"));
                    personList.add(person);
                }
            }
        } catch (SQLException e) {
            System.out.println("Not able to get Persons.");
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return personList;

    }

    public void updateTable(String tableName, long id, String attributeName,  String value) {
        String idName = "x";

        switch (tableName){
            case "Persons":
                idName="pID";
                break;

            case "Emails":
                idName="eID";
                break;

            case "PhoneNumbers":
                idName="pnID";
                break;

            case "Relationships":
                idName="rID";
                break;
        }

        // System.out.printf(tableName +" "+ idF +" "+ idName +" "+ attributeName +" "+ value);
        String updateSql = String.format("UPDATE %s SET %s =? WHERE %s=?",tableName,attributeName, idName);
        PreparedStatement uStmt = null;

        try {

            uStmt = conn.prepareStatement(updateSql);
            if (attributeName.equals("DateOfBirth")) {
                uStmt.setDate(1,  Date.valueOf(LocalDate.parse(value)));
            } else {
                uStmt.setString(1, value);
            }
            uStmt.setLong(2, id);


            boolean autoCommit = conn.getAutoCommit();

            try {
                conn.setAutoCommit(false);
                uStmt.executeUpdate();
            } catch (SQLException exc) {
                conn.rollback();
            } finally {
                conn.setAutoCommit(autoCommit);
            }
        } catch (SQLException ex) {
            System.out.print(ex.getMessage());
            System.out.println("\tUpdate() denied");
        }


    }

    public void delete(int id) {
        //String sql = "DELETE " +
         //       "FROM Persons p JOIN PhoneNumbers pn ON p.pID = pn.pID" +
          //      "JOIN Emails e ON p.pID = e.pID WHERE pID = ?";
        String sql1 = "DELETE FROM Emails WHERE pID = ?";
        String sql2 = "DELETE FROM PhoneNumbers WHERE pID = ?";
        String sql3 = "DELETE FROM Relationships WHERE p1 = ? OR p2 = ?";
        String sql4 = "DELETE FROM Persons WHERE pID = ?";

        if(checkPID(id)) {
            try {
                PreparedStatement pstmt1 = conn.prepareStatement(sql1);
                // set the corresponding param
                pstmt1.setInt(1, id);

                System.out.println("deleted from Emails");

                PreparedStatement pstmt2 = conn.prepareStatement(sql2);
                // set the corresponding param
                pstmt2.setInt(1, id);
                System.out.println("deleted from PhoneNumbers");

                PreparedStatement pstmt3 = conn.prepareStatement(sql3);
                // set the corresponding param
                pstmt3.setInt(1, id);
                pstmt3.setInt(2, id);
                System.out.println("deleted from Relationships");

                PreparedStatement pstmt4 = conn.prepareStatement(sql4);
                // set the corresponding param
                pstmt4.setInt(1, id);
                System.out.println("deleted from Persons");

                boolean autoCommit = conn.getAutoCommit();
                try {
                    conn.setAutoCommit(false);
                    pstmt1.executeUpdate();
                    pstmt2.executeUpdate();
                    pstmt3.executeUpdate();
                    pstmt4.executeUpdate();
                } catch (SQLException exc) {
                    conn.rollback();
                } finally {
                    conn.setAutoCommit(autoCommit);
                    System.out.println("Person deleted");
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("The person you are trying to delete does not exist.");
        }
    }

    public boolean checkPID(int pID) {
        String sql = "SELECT pID from Persons WHERE pID = ?";
        //Person person = null;
        boolean personExcists = false;

        try {
            if (conn != null) {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, pID);
                ResultSet rs = pstmt.executeQuery();

                if(rs.next()) {
                    personExcists = true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Person (Name=" + pID + ") NOT Found.");
            System.out.println(e.getMessage());
        }

        return personExcists;

    }


}
