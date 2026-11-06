package org.example.onskeseddel.Model;

public class OnskeModel {

    private int id;
    private int onskelisteId;
    private String navn;
    private String beskrivelse;
    private String link;
    private double pris;
    private int antal = 1;
    private boolean koebt;

    public OnskeModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOnskelisteId() {
        return onskelisteId;
    }

    public void setOnskelisteId(int onskelisteId) {
        this.onskelisteId = onskelisteId;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public double getPris() {
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

    public boolean isKoebt() {
        return koebt;
    }

    public void setKoebt(boolean koebt) {
        this.koebt = koebt;
    }
}
