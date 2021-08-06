package com.sp.addressbook;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AddressBookIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void itShouldPerformAddressBookAdd() throws Exception {
        String content = "{\"name\" : \"sp\"}";
        this.mockMvc.perform(post("/addressbooks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andDo(print())
                .andExpect(status().isCreated());

        this.mockMvc.perform(post("/addressbooks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andDo(print())
                .andExpect(status().isConflict());

    }

    @Test
    void itShouldPerformAddressBookDelete() throws Exception {
        String content = "{\"name\" : \"mybook\"}";
        this.mockMvc.perform(post("/addressbooks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andDo(print())
                .andExpect(status().isCreated());

        this.mockMvc.perform(delete("/addressbooks/mybook"))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(delete("/addressbooks/mybook"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    void itShouldReturnAddressBookNotFound() throws Exception {
        String contactRequestContent = "{\"name\":{\"firstName\":\"Wills1\",\"lastName\":\"Smith1\"},\"phoneNumber\":{\"number\":\"+610123456\"}}";
        this.mockMvc.perform(post("/addressbooks/newBook/contacts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(contactRequestContent))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    void itShouldPerformContactOperations() throws Exception {
        String addressBookContent = "{\"name\" : \"newBook\"}";
        String contactRequestContent = "{\"name\":{\"firstName\":\"Wills1\",\"lastName\":\"Smith1\"},\"phoneNumber\":{\"number\":\"+610123456\"}}";
        this.createAddressBook(addressBookContent);
        this.mockMvc.perform(post("/addressbooks/newBook/contacts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(contactRequestContent))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(notNullValue()));
    }

    @Test
    void itShouldPerformUniqueContactRetrieveOperations() throws Exception {
        String addressBookContent1 = "{\"name\" : \"newBook1\"}";
        String addressBookContent2 = "{\"name\" : \"newBook2\"}";
        String contactRequestContent1 = "{\"name\":{\"firstName\":\"Wills1\",\"lastName\":\"Smith1\"},\"phoneNumber\":{\"number\":\"610123456\"}}";
        String contactRequestContent2 = "{\"name\":{\"firstName\":\"sp\",\"lastName\":\"rmn\"},\"phoneNumber\":{\"number\":\"610123450\"}}";
        this.createAddressBook(addressBookContent1);
        this.createAddressBook(addressBookContent2);
        this.createContact("newBook1", contactRequestContent1);
        this.createContact("newBook1", contactRequestContent2);
        this.createContact("newBook2", contactRequestContent2);
        // search unique contacts
        this.mockMvc.perform(get("/contacts")
                .param("unique", "false"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

    }

    private void createAddressBook(String content) throws Exception {
        this.mockMvc.perform(post("/addressbooks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andDo(print());
    }

    private void createContact(String addressbook, String content) throws Exception {
        this.mockMvc.perform(post("/addressbooks/" + addressbook + "/contacts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)).andDo(print());
    }

}