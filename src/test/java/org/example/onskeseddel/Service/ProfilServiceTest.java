package org.example.onskeseddel.Service;

import org.example.onskeseddel.Repository.OnskeRepository;
import org.example.onskeseddel.Repository.OnskelisteRepository;
import org.example.onskeseddel.Repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

    @ExtendWith(MockitoExtension.class)
    class ProfilServiceTest {

        @Mock
        private PersonRepository personRepository;

        @Mock
        private OnskelisteRepository onskelisteRepository;

        @Mock
        private OnskeRepository onskeRepository;

        @InjectMocks
        private ProfilService service;

        @Test
        void initialerAfFuldtNavn() {
            assertEquals("DK", service.initialer("Dursun Kekec"));
        }

        @Test
        void initialerAfEtNavn() {
            assertEquals("D", service.initialer("dursun"));
        }

        @Test
        void initialerAfTomtNavnErTomt() {
            assertEquals("", service.initialer("   "));
            assertEquals("", service.initialer(null));
        }

        @Test
        void gemNavnMedTomtNavnKasterFejl() {
            assertThrows(IllegalArgumentException.class, () -> service.gemNavn("  "));
            verify(personRepository, never()).gemNavn(any());
        }

        @Test
        void gemNavnFjernerMellemrum() {
            service.gemNavn("  Dursun Kekec  ");

            verify(personRepository).gemNavn("Dursun Kekec");
        }
    }
