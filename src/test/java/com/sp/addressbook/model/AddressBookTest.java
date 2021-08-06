package com.sp.addressbook.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AddressBookTest {

    @Test
    public void initialAddressBookShouldBeEmpty() {
        AddressBook book = new AddressBook.AddressBookBuilder().withName("name").build();
        assertThat(book.contacts().size()).isEqualTo(0);
    }

    @Test
    public void itShouldBeAbleToAddNewEntryToAddressBook() {
        AddressBook book = new AddressBook.AddressBookBuilder().withName("name").build();
        book.add(new Contact.ContactBuilder()
                .withName(new Name("Wills", "Smith"))
                .withPhoneNumber(new PhoneNumber("+61101837401374"))
                .build());
        assertThat(book.contacts().size()).isEqualTo(1);
        assertThat(book.contacts().get(0).getName()).isEqualToComparingFieldByFieldRecursively(
                new Name("Wills", "Smith"));
        assertThat(book.contacts().get(0).getPhoneNumber()).isEqualToComparingFieldByFieldRecursively(
                new PhoneNumber("+61101837401374"));
    }

    @Test
    public void itShouldBeAbleToDeleteExistingEntriesFromAddressBook() {

        AddressBook book = new AddressBook.AddressBookBuilder().withName("name").build();
        book.add(new Contact.ContactBuilder()
                .withName(new Name("Wills", "Smith"))
                .withPhoneNumber(new PhoneNumber("+61101837401374"))
                .withId("1")
                .build());
        book.add(new Contact.ContactBuilder()
                .withName(new Name("Wills1", "Smith1"))
                .withPhoneNumber(new PhoneNumber("+61101837401374"))
                .withId("2")
                .build());
        assertThat(book.contacts().size()).isEqualTo(2);
        boolean isDeleted = book.remove("1");
        assertThat(isDeleted).isTrue();
        assertThat(book.contacts().size()).isEqualTo(1);
    }
}
