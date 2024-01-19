package ru.skillbox.contactapplications.service;

import ru.skillbox.contactapplications.exceptions.ContactNotFoundException;
import ru.skillbox.contactapplications.model.Contact;

import java.util.List;

public interface IContactService {
    List<Contact> findAll();
    Contact findById(Long id) throws ContactNotFoundException;
    Contact save(Contact contact);
    Contact update(Contact contact);
    void deleteById(Long id);
    void batchInsert(List<Contact> contacts);
}
