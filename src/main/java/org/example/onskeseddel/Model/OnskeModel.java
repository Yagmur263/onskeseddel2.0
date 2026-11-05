package org.example.onskeseddel.Model;

public class OnskeModel {

    private int id;
    private int onskelistId;
    private String navn;
    private String beskrivelse;
    private String link;
    private double pris;
    private int antal = 1;
    private boolean købt;

public OnskeModel() {
        this.id = id;
        this.onskelistId = onskelistId;
        this.navn = navn;
        this.beskrivelse = beskrivelse;
        this.link = link;
        this.pris = pris;
        this.antal = antal;
        this.købt = købt;
    }

    public int getId() {
        return id;
    }
    public void setId(int id){
    this.id = id;

    }
    public int getWishlistId() {
        return onskelistId;
    }
    public void setOnskelistId(int onskelistId){
        this.onskelistId = onskelistId;

    }
    public String getNavn() {
        return navn;
    }
    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getBeskrivelse(){
    return beskrivelse;
    }
    public void setBeskrivelse(String beskrivelse){
    this.beskrivelse = beskrivelse;

    }
    public String getlink(){
    return link;
    }
    public void setlink(String link){
    this.link = link;

    }
    public double getPris(){
        return pris;
    }
    public void setPris(double pris) {
        this.pris = pris;

    }
    public int getAntal() {
        return antal;
    }
    public void setAntal(int antal) {
        this.antal = antal;
    }

    public boolean getKøbt(){
    return købt;
    }
    public void setKøbt(boolean købt){
    this.købt = købt;

    }


    }

