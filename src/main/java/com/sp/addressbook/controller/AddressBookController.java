package com.sp.addressbook.controller;

import com.sp.addressbook.model.AddressBook;
import com.sp.addressbook.model.Contact;
import com.sp.addressbook.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    @PostMapping("/addressbooks")
    @ResponseBody
    public ResponseEntity<AddressBook> createAddressBook(@RequestBody AddressBook addressBook) {
        AddressBook book = addressBookService.createAddressBook(addressBook.name());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{name}")
                .buildAndExpand(book.name())
                .toUri();

        return ResponseEntity.created(uri)
                .body(book);
    }

    @DeleteMapping("/addressbooks/{addressBookName}")
    public ResponseEntity deleteAddressBook(@PathVariable String addressBookName) {
        addressBookService.deleteAddressBook(addressBookName);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/addressbooks/{addressBookName}/contacts")
    public ResponseEntity<Contact> addContactToAddressBook(
            @PathVariable String addressBookName, @RequestBody Contact newContact) {
        Contact contact = addressBookService.addContact(addressBookName, newContact);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(contact.getId())
                .toUri();

        return ResponseEntity.created(uri)
                .body(contact);
    }

    @DeleteMapping("/addressbooks/{addressBookName}/contacts/{contactId}")
    public ResponseEntity<Contact> removeContactFromAddressBook(@PathVariable String addressBookName,
                                                                @PathVariable String contactId) {
        addressBookService.removeContact(addressBookName, contactId);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/addressbooks/{addressBookName}/contacts")
    public ResponseEntity<List<Contact>> getAllContactsFromAddressBook(@PathVariable String addressBookName) {
        List<Contact> contactList = addressBookService.retrieveContacts(addressBookName);
        return ResponseEntity.ok()
                .body(contactList);
    }

    @GetMapping("/contacts/findByName")
    public ResponseEntity<List<Contact>> findByFirstOrLastName(@RequestParam("name") String name) {
        return ResponseEntity.ok()
                .body(addressBookService.searchByFirstOrLastName(name));
    }

    @GetMapping("/contacts/findByPhone")
    public ResponseEntity<List<Contact>> findByPhone(@RequestParam("phone") String phone) {
        return ResponseEntity.ok()
                .body(addressBookService.searchByPhoneNumber(phone));
    }

    @GetMapping("/contacts")
    public List<Contact> retrieveUniqueContactsFromAllAddressBooks(@RequestParam(value = "unique",
            defaultValue = "false") Boolean unique) {
        System.out.println("Unique" + unique);
        List<Contact> contacts = addressBookService.retrieveAllUniqueContacts(unique);
        System.out.println("Unique contacts" + contacts.toString());
        return contacts;
    }

}
