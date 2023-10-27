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
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
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
        //when
        Set<Visit> visitSet = visitSDJpaService.findAll();

        //then
        assertThat(visitSet).size().isZero();
        then(visitRepository).should().findAll();
        //verify(visitRepository).findAll();
    }

    @Test
    void findById() {
        //when
        visitSDJpaService.findById(1L);

        //then
        then(visitRepository).should().findById(anyLong());
        //verify(visitRepository).findById(anyLong());
    }

    @Test
    void save() {
        //given
        Visit visit = new Visit();
        given(visitRepository.save(any(Visit.class))).willReturn(visit);
        //when(visitRepository.save(any(Visit.class))).thenReturn(visit);

        //when
        Visit savedVisit = visitSDJpaService.save(visit);

        //then
        assertThat(savedVisit).isNotNull();
        then(visitRepository).should().save(any(Visit.class));
        //verify(visitRepository).save(any(Visit.class));
    }

    @Test
    void delete() {
        //given
        Visit visit = new Visit();

        //when
        visitSDJpaService.delete(visit);

        //then
        then(visitRepository).should().delete(any(Visit.class));
        //verify(visitRepository).delete(any(Visit.class));
    }

    @Test
    void deleteById() {
        //when
        visitSDJpaService.deleteById(1L);

        //then
        then(visitRepository).should().deleteById(anyLong());
        //verify(visitRepository).deleteById(anyLong());
    }
}