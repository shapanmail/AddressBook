package com.sp.addressbook.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NameTest {

    @Test
    public void itShouldCreateNewNameUsingConstructor() {
        Name name = new Name("John", "Calli");
        assertThat(name).isNotNull();
        assertThat(name.getFirstName()).isEqualTo("John");
        assertThat(name.getLastName()).isEqualTo("Calli");

    }

    @Test
    public void NameShouldBeEqualToAnotherWhenFirstAndLastNameAreEqual() {
        Name name1 = new Name("John", "Calli");
        Name name2 = new Name("John", "Calli");
        assertThat(name1).isEqualTo(name2);

    }
}
