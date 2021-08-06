package com.sp.addressbook.service;

import com.sp.addressbook.exception.ConflictException;
import com.sp.addressbook.exception.NotFoundException;
import com.sp.addressbook.model.AddressBook;
import com.sp.addressbook.model.Contact;
import com.sp.addressbook.model.Name;
import com.sp.addressbook.model.PhoneNumber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
class AddressBookServiceTest {
    @Autowired
    AddressBookService addressBookService;


    @Test
    void itShouldCreateNewAddressBook() {
        AddressBook book = addressBookService.createAddressBook("test1");
        assertThat(book.name()).isEqualTo("test1");
    }

    @Test
    void itShouldConflictWhenAddressBookExists() {
        AddressBook book = addressBookService.createAddressBook("newBook");
        assertThat(book.name()).isEqualTo("newBook");
        assertThatThrownBy(() -> {
            addressBookService.createAddressBook("newBook");
        }).isInstanceOf(ConflictException.class);
    }


    @Test
    void itShouldDeleteAddressBookAndThrowExceptionWhenNotFound() {
        AddressBook book = addressBookService.createAddressBook("newBook1");
        addressBookService.deleteAddressBook("newBook1");
        assertThatThrownBy(() -> {
            addressBookService.deleteAddressBook("newBook1");
        }).isInstanceOf(NotFoundException.class);

    }

    @Test
    void itShouldAddNewContactToAddressBook() {
        AddressBook book = addressBookService.createAddressBook("newBook2");
        Contact contact = addressBookService.addContact("newBook2", new Contact.ContactBuilder()
                .withName(new Name("Wills", "Smith"))
                .withPhoneNumber(new PhoneNumber("+61101837401374"))
                .build());
        assertThat(contact.getId()).isNotNull();
        assertThat(contact.getName().getFirstName()).isEqualTo("Wills");
        assertThat(contact.getName().getLastName()).isEqualTo("Smith");
        assertThat(contact.getPhoneNumber().getNumber()).isEqualTo("+61101837401374");
    }

    @Test
    void removeContact() {
        AddressBook book = addressBookService.createAddressBook("newBook3");
        Contact contact = addressBookService.addContact("newBook3", new Contact.ContactBuilder()
                .withName(new Name("Wills", "Smith"))
                .withPhoneNumber(new PhoneNumber("+61101837401374"))
                .build());
        try {
            addressBookService.removeContact("newBook3", contact.getId());
        } catch (Exception e) {
            Assertions.fail("Exception thrown in delete");
        }
    }

    @Test
    void retrieveContacts() {
        AddressBook book = addressBookService.createAddressBook("newBook4");
        Contact contact = addressBookService.addContact("newBook4", new Contact.ContactBuilder()
                .withName(new Name("Wills", "Smith"))
                .withPhoneNumber(new PhoneNumber("+61101837401374"))
                .build());
        List<Contact> contacts = addressBookService.retrieveContacts("newBook4");
        assertThat(contacts.size()).isEqualTo(1);
        Contact c = contacts.get(0);
        assertThat(contact.getName().getFirstName()).isEqualTo("Wills");
        assertThat(contact.getName().getLastName()).isEqualTo("Smith");
        assertThat(contact.getPhoneNumber().getNumber()).isEqualTo("+61101837401374");
    }

    @Test
    void searchByPhoneNumber() {
        AddressBook book = addressBookService.createAddressBook("newBook5");
        addressBookService.addContact("newBook5", new Contact.ContactBuilder()
                .withName(new Name("Wills", "Smith"))
                .withPhoneNumber(new PhoneNumber("+61101837401374"))
                .build());
        addressBookService.addContact("newBook5", new Contact.ContactBuilder()
                .withName(new Name("Wills1", "Smith1"))
                .withPhoneNumber(new PhoneNumber("+61101837401374"))
                .build());
    }

    @Test
    public void userShouldBeAbleSearchContactByPhoneNumberAccrossMultipleBook() {
        // create 10 addressbook
        IntStream.range(0, 10).forEach(bookCounter -> {
            AddressBook book = addressBookService.createAddressBook("sb-" + bookCounter);
        });

        // create 10 contact in each book
        IntStream.range(0, 10).forEach(bookCounter -> {
            IntStream.range(0, 10).forEach(contactCounter -> {
                Contact c = new Contact.ContactBuilder()
                        .withName(new Name("firstName-" + bookCounter, "lastName-" + contactCounter))
                        .withPhoneNumber(new PhoneNumber("043134062" + contactCounter))
                        .build();
                addressBookService.addContact("sb-" + contactCounter, c);
            });
        });

        assertThat(addressBookService.searchByPhoneNumber("0431340625").size()).isEqualTo(10);
    }

    @Test
    void searchByFirstOrLastName() {
        // create 10 addressbook
        IntStream.range(0, 10).forEach(bookCounter -> {
            AddressBook book = addressBookService.createAddressBook("sb1-" + bookCounter);
        });

        // create 10 contact in each book
        IntStream.range(0, 10).forEach(bookCounter -> {
            IntStream.range(0, 10).forEach(contactCounter -> {
                Contact c = new Contact.ContactBuilder()
                        .withName(new Name("firstName-" + contactCounter, "lastName-" + contactCounter))
                        .withPhoneNumber(new PhoneNumber("01234" + contactCounter))
                        .build();
                addressBookService.addContact("sb1-" + contactCounter, c);
            });
        });

        assertThat(addressBookService.searchByFirstOrLastName("firstName-1").size()).isEqualTo(10);
    }
}