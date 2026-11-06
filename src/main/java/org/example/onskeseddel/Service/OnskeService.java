package org.example.onskeseddel.Service;

import org.example.onskeseddel.Model.OnskeModel;
import org.example.onskeseddel.Repository.OnskeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class OnskeService {
    public final OnskeRepository onskeRepository;

    public OnskeService(OnskeRepository onskeRepository) {
        this.onskeRepository = onskeRepository;
    }

    public void OpretOnske(OnskeModel onske) {
        onskeRepository.opretOnske(onske);
    }

    public ArrayList<OnskeModel> hentOnske() {
       return onskeRepository.hentOnske();
    }

    public String getOnske() {
        return onskeRepository.getOnske();
    }
}
