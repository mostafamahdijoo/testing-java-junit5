package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Specialty;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialtySDJpaServiceTest {

    @Mock
    SpecialtyRepository specialtyRepository;

    @InjectMocks
    SpecialtySDJpaService specialtyService;

    @Test
    void delete() {
        specialtyService.delete(new Specialty());
    }

    @Test
    void deleteById() {
        //when
        specialtyService.deleteById(1L);
        specialtyService.deleteById(1L);

        //then
        then(specialtyRepository).should(times(2)).deleteById(1L);
        //verify(specialtyRepository, times(2)).deleteById(1L);
    }

    @Test
    void deleteByIdAtLeast() {
        //when
        specialtyService.deleteById(1L);
        specialtyService.deleteById(1L);

        //then
        then(specialtyRepository).should(atLeastOnce()).deleteById(1L);
        //verify(specialtyRepository, atLeastOnce()).deleteById(1L);
    }

    @Test
    void deleteByIdAtMost() {
        //when
        specialtyService.deleteById(1L);
        specialtyService.deleteById(1L);

        //then
        then(specialtyRepository).should(atMost(5)).deleteById(1L);
        //verify(specialtyRepository, atMost(5)).deleteById(1L);
    }

    @Test
    void deleteByIdNever() {
        //when
        specialtyService.deleteById(1L);
        specialtyService.deleteById(1L);

        //then
        then(specialtyRepository).should(atLeastOnce()).deleteById(1L);
        //verify(specialtyRepository, atLeastOnce()).deleteById(1L);
        then(specialtyRepository).should(never()).deleteById(5L);
        //verify(specialtyRepository, never()).deleteById(5L);
    }

    @Test
    void findByIdTest() {
        //given
        Specialty specialty = new Specialty();
        given(specialtyRepository.findById(1L)).willReturn(Optional.of(specialty));

        //when
        Specialty foundSpecialty = specialtyService.findById(1L);

        //then
        assertThat(foundSpecialty).isNotNull();
        then(specialtyRepository).should().findById(anyLong());
        then(specialtyRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void testDeleteByObject() {
        //given
        Specialty Specialty = new Specialty();

        //when
        specialtyService.delete(Specialty);

        //then
        then(specialtyRepository).should().delete(any(Specialty.class));
        //verify(specialtyRepository).delete(any(Specialty.class));
    }
}