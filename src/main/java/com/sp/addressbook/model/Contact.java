package com.sp.addressbook.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.Objects;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonDeserialize(builder = Contact.ContactBuilder.class)
public class Contact {

    private Name name;
    private PhoneNumber phoneNumber;
    private String id;

    private Contact(String id, Name name, PhoneNumber phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.id = id;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.phoneNumber);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || !(obj instanceof Contact))
            return false;
        Contact contact = (Contact) obj;
        return contact.getName().equals(this.name) && contact.getPhoneNumber().equals(this.phoneNumber);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name=" + name +
                ", phoneNumber=" + phoneNumber +
                ", id='" + id + '\'' +
                '}';
    }

    @JsonPOJOBuilder()
    public static class ContactBuilder {
        Name name;
        String id;
        PhoneNumber number;

        public ContactBuilder withName(Name name) {
            this.name = name;
            return this;
        }

        public ContactBuilder withPhoneNumber(PhoneNumber number) {
            this.number = number;
            return this;
        }

        public ContactBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public Contact build() {
            return new Contact(id, name, number);
        }
    }
}
