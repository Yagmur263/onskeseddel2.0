package org.example.onskeseddel.Service;

import org.example.onskeseddel.Model.OnskeModel;
import org.example.onskeseddel.Repository.OnskeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class OnskeService {
    public final OnskeRepository onskeRepository;

    OnskeService(OnskeRepository onskeRepository){
        this.onskeRepository = onskeRepository;
    }
    ArrayList<OnskeModel> onsker = new ArrayList<>();

    }

