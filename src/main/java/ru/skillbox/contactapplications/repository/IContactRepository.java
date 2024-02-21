package ru.skillbox.contactapplications.repository;

import ru.skillbox.contactapplications.model.Contact;

import java.util.List;
import java.util.Optional;

public interface IContactRepository {

    List<Contact> findAll();
    Optional<Contact> findById(Long id);
    Contact save(Contact contact);
    Contact update(Contact contact);
    void deleteById(Long id);
    void batchInsert(List<Contact> contacts);
}
