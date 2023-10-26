package guru.springframework.sfgpetclinic.model;

import guru.springframework.sfgpetclinic.ModelTests;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonTest implements ModelTests {

    @Test
    void groupedAssertions() {
        //given
        Person person = new Person(1L, "Mostafa", "Mahdijoo");
        //then
        assertAll(
                () -> assertEquals("Mostafa", person.getFirstName()),
                () -> assertEquals("Mahdijoo", person.getLastName())
        );
    }

    @Test
    void groupedAssertionMsgs() {
        //given
        Person person = new Person(1L, "Mostafa", "Mahdijoo");

        //then
        assertAll("Test Props Set",
                () -> assertEquals("Mostafa", person.getFirstName(), "First Name Failed"),
                () -> assertEquals("Mahdijoo", person.getLastName(), "Last Name Failed")
        );
    }
}