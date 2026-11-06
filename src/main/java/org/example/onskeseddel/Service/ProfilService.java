package org.example.onskeseddel.Service;

import org.example.onskeseddel.Repository.OnskeRepository;
import org.example.onskeseddel.Repository.OnskelisteRepository;
import org.example.onskeseddel.Repository.PersonRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfilService {

    private final PersonRepository personRepository;
    private final OnskelisteRepository onskelisteRepository;
    private final OnskeRepository onskeRepository;

    public ProfilService(PersonRepository personRepository,
                         OnskelisteRepository onskelisteRepository,
                         OnskeRepository onskeRepository) {
        this.personRepository = personRepository;
        this.onskelisteRepository = onskelisteRepository;
        this.onskeRepository = onskeRepository;
    }

    public String hentNavn() {
        return personRepository.findNavn();
    }

    public void gemNavn(String navn) {
        if (navn == null || navn.isBlank()) {
            throw new IllegalArgumentException("Skriv dit navn");
        }
        String rent = navn.trim();
        if (rent.length() > 100) {
            throw new IllegalArgumentException("Navnet må højst være 100 tegn");
        }
        personRepository.gemNavn(rent);
    }

    public int antalLister() {
        return onskelisteRepository.antalLister();
    }

    public int antalOnsker() {
        return onskeRepository.antalOnsker();
    }

    public String initialer(String navn) {
        if (navn == null || navn.isBlank()) {
            return "";
        }
        String[] dele = navn.trim().split("\\s+");
        String foerste = dele[0].substring(0, 1);
        String sidste = dele.length > 1 ? dele[dele.length - 1].substring(0, 1) : "";
        return (foerste + sidste).toUpperCase();
    }
}