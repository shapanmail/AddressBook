package com.sp.addressbook.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.constraints.*;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonDeserialize(builder = AddressBook.AddressBookBuilder.class)
public class AddressBook {

    private List<Contact> contacts = new ArrayList<>();
    private String name;

    private AddressBook(String name) {
        this.name = name;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void add(Contact contact) {
        this.contacts.add(contact);
    }

    public boolean remove(String id) {
        return this.contacts.removeIf(c -> c.getId().equals(id));
    }

    @Override
    public String toString() {
        return this.name + " : "
                + this.contacts.stream().map(contact -> contact.toString()).collect(
                Collectors.joining(","));
    }

    public List<Contact> contacts() {
        return this.contacts;
    }

    public String name() {
        return this.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || !(obj instanceof AddressBook))
            return false;
        AddressBook book = (AddressBook) obj;
        return book.name().equals(this.name);
    }

    @JsonPOJOBuilder()
    public static class AddressBookBuilder {
        String name;
        List<Contact> contacts = new ArrayList<>();

        public AddressBookBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public AddressBookBuilder withContactList(List<Contact> contactList) {
            this.contacts = contactList;
            return this;
        }

        public AddressBook build() {
            AddressBook book = new AddressBook(name);
            book.setContacts(this.contacts);
            return book;
        }
    }


}
