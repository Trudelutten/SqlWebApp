package no.noroff.SqlWebApp.models;

import no.noroff.SqlWebApp.SqlWebApplication;


public class Relationship {
    private int rID;
    private int p1;
    private int p2;
    private String p1p2;
    private String p2p1;

    public Relationship() {
    }

    public Relationship(int p1, int p2, String p1p2, String p2p1) {
        this.p1 = p1;
        this.p2 = p2;
        this.p1p2 = p1p2;
        this.p2p1 = p2p1;
    }

    //Constructor
    public Relationship(int rID, int p1, int p2, String p1p2, String p2p1) {
        this.rID = rID;
        this.p1 = p1;
        this.p2 = p2;
        this.p1p2 = p1p2;
        this.p2p1 = p2p1;
    }

    /* GETTERS */
    public int getrID () {
        return rID;
    }
    public int getP1() {
        return p1;
    }
    public int getP2() {
        return p2;
    }
    public String getP1p2() {
        return p1p2;
    }
    public String getP2p1() {
        return p2p1;
    }

    /* SETTERS*/
    public void setP1p2(String p1p2) {
        this.p1p2 = p1p2;
    }
    public void setP2p1(String p2p1) {
        this.p2p1 = p2p1;
    }

}
