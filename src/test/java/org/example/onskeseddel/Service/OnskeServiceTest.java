package org.example.onskeseddel.Service;

import org.example.onskeseddel.Model.OnskeModel;
import org.example.onskeseddel.Repository.OnskeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OnskeServiceTest {

    @Mock
    private OnskeRepository onskeRepository;

    @InjectMocks
    private OnskeService service;

    private OnskeModel gyldigtOnske() {
        OnskeModel onske = new OnskeModel();
        onske.setOnskelisteId(1);
        onske.setNavn("Ny laptop");
        onske.setPris(7999.0);
        onske.setAntal(1);
        return onske;
    }

    @Test
    void tilfoejGyldigtOnskeGemmerIRepository() {
        OnskeModel onske = gyldigtOnske();

        service.tilfoej(onske);

        verify(onskeRepository).gem(onske);
    }

    @Test
    void tilfoejUdenNavnKasterFejl() {
        OnskeModel onske = gyldigtOnske();
        onske.setNavn("");

        assertThrows(IllegalArgumentException.class, () -> service.tilfoej(onske));
        verify(onskeRepository, never()).gem(any());
    }

    @Test
    void tilfoejMedAntalNulKasterFejl() {
        OnskeModel onske = gyldigtOnske();
        onske.setAntal(0);

        assertThrows(IllegalArgumentException.class, () -> service.tilfoej(onske));
    }

    @Test
    void tilfoejMedNegativPrisKasterFejl() {
        OnskeModel onske = gyldigtOnske();
        onske.setPris(-5);

        assertThrows(IllegalArgumentException.class, () -> service.tilfoej(onske));
    }

    @Test
    void sletKalderRepositoryMedRigtigtId() {
        service.slet(5);

        verify(onskeRepository).slet(5);
    }
}