package com.sp.addressbook.service;


import com.sp.addressbook.exception.ConflictException;
import com.sp.addressbook.exception.NotFoundException;
import com.sp.addressbook.model.AddressBook;
import com.sp.addressbook.model.Contact;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class AddressBookService {

    private final Map<String, AddressBook> addressBooks = new HashMap<>();

    public AddressBook createAddressBook(String name) {

        if (addressBooks.containsKey(name)) {
            throw new ConflictException("Address book already exists!!");
        }
        AddressBook addressBook = new AddressBook.AddressBookBuilder().withName(name).build();
        addressBooks.put(name, addressBook);
        return addressBook;
    }

    public void deleteAddressBook(String name) {

        if (!addressBooks.containsKey(name)) {
            throw new NotFoundException("Address book not found!");
        }
        addressBooks.remove(name);
    }


    public Contact addContact(String addressBookName, Contact contact) {
        contact.setId(UUID.randomUUID().toString());
        if (!addressBooks.containsKey(addressBookName)) {
            throw new NotFoundException("Address book not found!");
        }
        AddressBook book = addressBooks.get(addressBookName);
        book.add(contact);
        return contact;
    }

    public void removeContact(String addressBookName, final String contactId) {
        if (!addressBooks.containsKey(addressBookName)) {
            throw new NotFoundException("Address book not found!");
        }
        boolean deleted = addressBooks.get(addressBookName).remove(contactId);
        if (!deleted) {
            throw new NotFoundException("Contact not found!");
        }

    }

    public List<Contact> retrieveContacts(String addressBookName) {
        if (!addressBooks.containsKey(addressBookName)) {
            throw new NotFoundException("Address book not found!");
        }
        return addressBooks.get(addressBookName).getContacts();
    }

    public List<Contact> searchByPhoneNumber(String phoneNumber) {
        Predicate<Contact> contactMatchesPhoneNumber = contact -> contact.getPhoneNumber().getNumber().equals(
                phoneNumber);
        return search(contactMatchesPhoneNumber);
    }

    public List<Contact> searchByFirstOrLastName(String name) {
        Predicate<Contact> contactMatchesFistOrLastName = contact -> contact.getName().getFirstName().equals(
                name) || contact.getName().getLastName().equals(name);
        return search(contactMatchesFistOrLastName);
    }

    private List<Contact> search(Predicate<Contact> criteria) {
        return this.addressBooks.values().stream().map((book) -> {
            return book.contacts().stream().filter(criteria).collect(Collectors.toList());
        }).flatMap(List::stream).collect(Collectors.toList());
    }

    public List<Contact> retrieveAllUniqueContacts(boolean unique) {
        List<Contact> allContacts = this.addressBooks.values().stream().map((book) -> book.contacts())
                .flatMap(List::stream).collect(Collectors.toList());
        return unique ? allContacts.stream().distinct().collect(Collectors.toList()) : allContacts;
    }
}

