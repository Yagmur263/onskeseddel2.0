package org.example.onskeseddel.Model;

public class OnskeModel {

    private String navn;
    private double pris;
    private int antal;

    public OnskeModel(){
        this.navn = navn;
        this.pris = pris;
        this.antal = antal;
    }

    public String getNavn() {
        return navn;
    }
    public double getPris(){
        return pris;
    }

    public int getAntal() {
        return antal;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public void setPris(double pris) {
        this.pris = pris;
    }

    public void setAntal(int antal) {
        this.antal = antal;
    }
}
