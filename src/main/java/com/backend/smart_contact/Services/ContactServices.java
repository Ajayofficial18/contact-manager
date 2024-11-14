package com.backend.smart_contact.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.backend.smart_contact.Entities.Contact;
import com.backend.smart_contact.Repository.ContactRepository;

@Component
public class ContactServices {

    private ContactRepository contactRepository;

    public List<Contact> allContacts(int id){
        return this.contactRepository.findContactByUser(id);
    }

    public Contact findContactById(int id){
        Optional <Contact> optional= this.contactRepository.findById(id);
        Contact contact=optional.get();
        return contact;
    }

    public void deleteContactById(int id){
        this.contactRepository.deleteById(id);
    }

}
