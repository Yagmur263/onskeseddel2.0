package org.example.onskeseddel.Service;

import org.example.onskeseddel.Model.Onskeliste;
import org.example.onskeseddel.Repository.OnskeRepository;
import org.example.onskeseddel.Repository.OnskelisteRepository;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.List;

@Service
public class OnskelisteService {

    private final OnskelisteRepository onskelisteRepository;
    private final OnskeRepository onskeRepository;

    public OnskelisteService(OnskelisteRepository onskelisteRepository,
                             OnskeRepository onskeRepository) {
        this.onskelisteRepository = onskelisteRepository;
        this.onskeRepository = onskeRepository;
    }

    public List<Onskeliste> hentAlle() {
        return onskelisteRepository.findAlle();
    }

    public Onskeliste hentMedOnsker(int id) {
        Onskeliste liste = onskelisteRepository.findById(id);
        if (liste == null) {
            return null;
        }
        liste.setOnsker(onskeRepository.findAlleForListe(id));
        return liste;
    }

    public void opret(Onskeliste liste) {
        if (liste.getTitel() == null || liste.getTitel().isBlank()) {
            throw new IllegalArgumentException("En ønskeliste skal have en titel");
        }
        liste.setTitel(liste.getTitel().trim());
        onskelisteRepository.gem(liste);
    }

    public void slet(int id) {
        onskelisteRepository.slet(id);

    }
    public Onskeliste hentViaDelingskode(String delingskode) {
        Onskeliste liste = onskelisteRepository.findByDelingskode(delingskode);
        if (liste == null) {
            return null;
        }
        liste.setOnsker(onskeRepository.findAlleForListe(liste.getId()));
        return liste;

    }
    private static final Pattern DELINGSKODE =
            Pattern.compile("[0-9a-fA-F]{8}(-[0-9a-fA-F]{4}){3}-[0-9a-fA-F]{12}");

    public Onskeliste findViaLinkEllerKode(String tekst) {
        if (tekst == null) {
            return null;
        }
        Matcher matcher = DELINGSKODE.matcher(tekst);
        if (!matcher.find()) {
            return null;
        }
        return onskelisteRepository.findByDelingskode(matcher.group().toLowerCase());
    }
}