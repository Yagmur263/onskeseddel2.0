package org.example.onskeseddel.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Onskeliste {
    private int id;
    private String titel;
    private String delingsKode = UUID.randomUUID().toString();
    private List<OnskeModel> onsker = new ArrayList<>();

    public Onskeliste() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getDelingsKode() {
        return delingsKode;
    }

    public void setDelingsKode(String delingsKode) {
        this.delingsKode = delingsKode;
    }

    public List<OnskeModel> getOnsker() {
        return onsker;
    }

    public void setOnsker(List<OnskeModel> onsker) {
        this.onsker = onsker;
    }
}
