package org.example.onskeseddel.Service;

import org.example.onskeseddel.Model.OnskeModel;
import org.example.onskeseddel.Repository.OnskeRepository;
import org.springframework.stereotype.Service;

@Service
public class OnskeService {

    private final OnskeRepository onskeRepository;

    public OnskeService(OnskeRepository onskeRepository) {
        this.onskeRepository = onskeRepository;
    }

    public void tilfoej(OnskeModel onske) {
        if (onske.getNavn() == null || onske.getNavn().isBlank()) {
            throw new IllegalArgumentException("Et ønske skal have et navn");
        }
        if (onske.getAntal() < 1) {
            throw new IllegalArgumentException("Antal skal være mindst 1");
        }
        if (onske.getPris() < 0) {
            throw new IllegalArgumentException("Prisen må ikke være negativ");
        }
        onskeRepository.gem(onske);
    }

    public void slet(int id) {
        onskeRepository.slet(id);
    }
}