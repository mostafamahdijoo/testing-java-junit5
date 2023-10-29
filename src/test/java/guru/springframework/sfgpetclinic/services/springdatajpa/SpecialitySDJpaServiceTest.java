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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialtySDJpaServiceTest {

    @Mock(lenient = true)
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
        then(specialtyRepository).should(timeout(100).times(2)).deleteById(1L);
        //verify(specialtyRepository, times(2)).deleteById(1L);
    }

    @Test
    void deleteByIdAtLeast() {
        //when
        specialtyService.deleteById(1L);
        specialtyService.deleteById(1L);

        //then
        then(specialtyRepository).should(timeout(100).atLeastOnce()).deleteById(1L);
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
        then(specialtyRepository).should(timeout(100).atLeastOnce()).deleteById(1L);
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
        then(specialtyRepository).should(timeout(100)).findById(anyLong());
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

    @Test
    void testDoThrow() {
        doThrow(new RuntimeException("Boom")).when(specialtyRepository).delete(any());

        assertThrows(RuntimeException.class, () -> specialtyService.delete(new Specialty()));

        verify(specialtyRepository).delete(any());
    }

    @Test
    void testFindByIdThrows() {
        //given
        given(specialtyRepository.findById(anyLong())).willThrow(new RuntimeException("boom"));

        //when
        assertThrows(RuntimeException.class, () -> specialtyService.findById(anyLong()));

        //then
        then(specialtyRepository).should().findById(anyLong());
    }

    @Test
    void testDeleteBDD() {
        //given
        willThrow(new RuntimeException("boom")).given(specialtyRepository).delete(any());

        //when
        assertThrows(RuntimeException.class, () -> specialtyService.delete(new Specialty()));

        //then
        then(specialtyRepository).should().delete(any());
    }

    @Test
    void testSaveLambda() {
        //given
        final String MATCH_ME = "MATCH_ME";
        Specialty specialty = new Specialty();
        specialty.setDescription(MATCH_ME);

        Specialty savedSpecialty = new Specialty();
        savedSpecialty.setId(1L);

        //need mock to only return on match MATCH_ME string
        given(specialtyRepository.save(argThat(spclty -> spclty.getDescription().equals(MATCH_ME)))).willReturn(savedSpecialty);

        //when
        Specialty returnedSpecialty = specialtyService.save(specialty);

        //then
        assertThat(returnedSpecialty.getId()).isEqualTo(1L);
    }

    @Test
    void testSaveLambdaNotMatch() {
        //given
        final String MATCH_ME = "MATCH_ME";
        Specialty specialty = new Specialty();
        specialty.setDescription("Not a match");

        Specialty savedSpecialty = new Specialty();
        savedSpecialty.setId(1L);

        //need mock to only return on match MATCH_ME string
        //lenient().when(specialtyRepository.save(argThat(spclty -> spclty.getDescription().equals(MATCH_ME)))).thenReturn(savedSpecialty);
        given(specialtyRepository.save(argThat(spclty -> spclty.getDescription().equals(MATCH_ME)))).willReturn(savedSpecialty);

        //when
        Specialty returnedSpecialty = specialtyService.save(specialty);

        //then
        assertNull(returnedSpecialty);
    }
}