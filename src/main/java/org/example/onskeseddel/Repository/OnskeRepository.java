package org.example.onskeseddel.Repository;

import org.example.onskeseddel.Model.OnskeModel;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
@Repository
public class OnskeRepository {
    ArrayList<OnskeModel> onsker = new ArrayList<>();

    public void opretOnske (OnskeModel opret){
        onsker.add(opret);
    }
    public ArrayList<OnskeModel> hentOnske(){
       return onsker;
    }



}
