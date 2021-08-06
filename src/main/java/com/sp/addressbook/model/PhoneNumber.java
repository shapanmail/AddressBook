package com.sp.addressbook.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.Objects;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PhoneNumber {

    private String number;

    public PhoneNumber(String number) {
        this.number = number;
    }

    public PhoneNumber() {}

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.number);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || !(obj instanceof PhoneNumber))
            return false;
        PhoneNumber phoneNumber = (PhoneNumber) obj;
        return phoneNumber.getNumber().equals(this.number);
    }

    @Override
    public String toString() {
        return "PhoneNumber : " + this.number;
    }

}
