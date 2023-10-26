package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.ControllerTests;
import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.SpecialtyService;
import guru.springframework.sfgpetclinic.services.VetService;
import guru.springframework.sfgpetclinic.services.map.SpecialtyMapService;
import guru.springframework.sfgpetclinic.services.map.VetMapService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class VetControllerTest implements ControllerTests {

    VetController vetController;
    VetService vetService;
    SpecialtyService specialtyService;

    @BeforeEach
    void setUp() {
        specialtyService = new SpecialtyMapService();
        vetService = new VetMapService(specialtyService);
        vetController = new VetController(vetService);

        Vet vet1 = new Vet(1L, "joe", "buck", null);
        Vet vet2 = new Vet(2L, "Jimmy", "Smith", null);

        vetService.save(vet1);
        vetService.save(vet2);
    }

    @Test
    void listVets() {
        Model model = new Model() {
            Map<String, Object> map = new HashMap<>();
            @Override
            public void addAttribute(String key, Object o) {
                map.put(key, o);
            }

            @Override
            public void addAttribute(Object o) {
                // do nothing
            }

            @Override
            public Map<String, Object> getMap() {
                return map;
            }
        };
        assertEquals("vets/index", vetController.listVets(model));

        Set modelAttributes = (Set) model.getMap().get("vets");

        assertThat(modelAttributes.size()).isEqualTo(2);
    }
}