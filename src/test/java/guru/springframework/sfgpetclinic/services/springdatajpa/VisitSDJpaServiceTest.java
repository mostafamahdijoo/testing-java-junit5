package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

    @Mock
    VisitRepository visitRepository;

    @InjectMocks
    VisitSDJpaService visitSDJpaService;

    @Test
    void findAll() {
        Set<Visit> visitSet = visitSDJpaService.findAll();
        assertThat(visitSet).size().isZero();

        verify(visitRepository).findAll();
    }

    @Test
    void findById() {
        visitSDJpaService.findById(1L);
        verify(visitRepository).findById(anyLong());
    }

    @Test
    void save() {
        Visit visit = new Visit();
        when(visitRepository.save(any(Visit.class))).thenReturn(visit);
        Visit savedVisit = visitSDJpaService.save(visit);
        assertThat(savedVisit).isNotNull();
        verify(visitRepository).save(any(Visit.class));
    }

    @Test
    void delete() {
        Visit visit = new Visit();
        visitSDJpaService.delete(visit);
        verify(visitRepository).delete(any(Visit.class));
    }

    @Test
    void deleteById() {
        visitSDJpaService.deleteById(1L);
        verify(visitRepository).deleteById(anyLong());
    }
}