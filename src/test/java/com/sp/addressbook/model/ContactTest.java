package com.sp.addressbook.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class ContactTest {

    @Test
    public void itShouldCreateNewContactUsingContactBuilder() {
        Contact contact = new Contact.ContactBuilder()
                .withName(new Name("Wills", "Smith"))
                .withPhoneNumber(new PhoneNumber("+61101837401374"))
                .build();
        assertThat(contact).isNotNull();
        assertThat(contact.getName().getFirstName()).isEqualTo("Wills");
        assertThat(contact.getName().getLastName()).isEqualTo("Smith");
        assertThat(contact.getPhoneNumber().getNumber()).isEqualTo("+61101837401374");
    }


    @Test
    public void contactShouldBeEqualToAnotherWhenNameAndPhoneEqual() {
        Contact contact1 = new Contact.ContactBuilder()
                .withName(new Name("Wills", "Smith"))
                .withPhoneNumber(new PhoneNumber("+61101837401374"))
                .build();
        Contact contact2 = new Contact.ContactBuilder()
                .withName(new Name("Wills", "Smith"))
                .withPhoneNumber(new PhoneNumber("+61101837401374"))
                .build();
        assertThat(contact1.equals(contact2)).isTrue();
    }

}
