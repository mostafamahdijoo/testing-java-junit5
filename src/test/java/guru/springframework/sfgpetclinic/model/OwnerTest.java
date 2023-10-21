package guru.springframework.sfgpetclinic.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OwnerTest {

    @Test
    void dependentAssertions() {

        Owner owner = new Owner(1l, "Mostafa", "Mahdijoo");
        owner.setCity("Kitchener");
        owner.setTelephone("1232432432");

        assertAll("Properties Test",
                () -> assertAll("Person Properties",
                        () -> assertEquals("Mostafa", owner.getFirstName(), "First Name Did Not Match"),
                        () -> assertEquals("Mahdijoo", owner.getLastName())
                ),
                () -> assertAll("Owner Properties",
                        () -> assertEquals("Kitchener", owner.getCity(), "City Did Not Match"),
                        () -> assertEquals("1232432432", owner.getTelephone())
                )
        );
    }
}