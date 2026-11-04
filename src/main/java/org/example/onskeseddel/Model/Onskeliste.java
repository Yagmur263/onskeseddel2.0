package org.example.onskeseddel.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Onskeliste {
    private int id;
    private String title;
    private String delingsKode = UUID.randomUUID(). toString();
    private List<OnskeModel> Ønsker = new ArrayList<>();

public Onskeliste() {
}
    public int getId(){
    return id;
    }
    public void setId(int id){
    this.id = id;

    }
    public String gettitle(){
    return title;
    }
    public void setTitle(String title){
    this.title = title;

    }
    public String getDelingsKode(){
    return delingsKode;
    }
    public void setDelingsKode(String delingsKode){
    this.delingsKode = delingsKode;
    }

}
