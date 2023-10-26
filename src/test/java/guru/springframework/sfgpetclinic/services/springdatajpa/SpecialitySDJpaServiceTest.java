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
        specialtyService.deleteById(1L);
        specialtyService.deleteById(1L);

        verify(specialtyRepository, times(2)).deleteById(1L);
    }

    @Test
    void deleteByIdAtLeast() {
        specialtyService.deleteById(1L);
        specialtyService.deleteById(1L);

        verify(specialtyRepository, atLeastOnce()).deleteById(1L);
    }

    @Test
    void deleteByIdAtMost() {
        specialtyService.deleteById(1L);
        specialtyService.deleteById(1L);

        verify(specialtyRepository, atMost(5)).deleteById(1L);
    }

    @Test
    void deleteByIdNever() {
        specialtyService.deleteById(1L);
        specialtyService.deleteById(1L);

        verify(specialtyRepository, atLeastOnce()).deleteById(1L);

        verify(specialtyRepository, never()).deleteById(5L);
    }

    @Test
    void findByIdTest() {
        Specialty Specialty = new Specialty();

        when(specialtyRepository.findById(1L)).thenReturn(Optional.of(Specialty));

        Specialty foundSpecialty = specialtyService.findById(1L);

        assertThat(foundSpecialty).isNotNull();

        verify(specialtyRepository).findById(anyLong());
    }

    @Test
    void testDeleteByObject() {
        Specialty Specialty = new Specialty();
        specialtyService.delete(Specialty);
        verify(specialtyRepository).delete(any(Specialty.class));
    }
}