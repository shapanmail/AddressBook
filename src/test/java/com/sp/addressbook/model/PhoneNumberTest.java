package com.sp.addressbook.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PhoneNumberTest {

    @Test
    public void itShouldCreateNewPhoneNumberUsingConstructor() {
        PhoneNumber number = new PhoneNumber("+6198800876");
        assertThat(number).isNotNull();
        assertThat(number.getNumber()).isEqualTo("+6198800876");
    }

    @Test
    public void phoneNumberShouldBeEqualToAnotherWhenNumbersAreEqual() {
        PhoneNumber number1 = new PhoneNumber("+6198800876");
        PhoneNumber number2 = new PhoneNumber("+6198800876");
        assertThat(number1).isEqualTo(number2);
    }
}
