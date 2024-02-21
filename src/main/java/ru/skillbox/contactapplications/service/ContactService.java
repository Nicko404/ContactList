package ru.skillbox.contactapplications.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skillbox.contactapplications.exceptions.ContactNotFoundException;
import ru.skillbox.contactapplications.model.Contact;
import ru.skillbox.contactapplications.repository.IContactRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactService implements IContactService {

    private final IContactRepository contactRepository;

    @Override
    public List<Contact> findAll() {
        log.debug("ContactService->findAll");
        return new ArrayList<>(contactRepository.findAll());
    }

    @Override
    public Contact findById(Long id) throws ContactNotFoundException {
        log.debug("ContactService->findById. ID: {}", id);
        Optional<Contact> contactOptional = contactRepository.findById(id);
        return contactOptional.orElseThrow(() -> new ContactNotFoundException("Contact not found. ID: " + id));
    }

    @Override
    public Contact save(Contact contact) {
        log.debug("ContactService->save. {}", contact);
        return contactRepository.save(contact);
    }

    @Override
    public Contact update(Contact contact) {
        log.debug("ContactService->update. {}", contact);
        return contactRepository.update(contact);
    }

    @Override
    public void deleteById(Long id) {
        log.debug("ContactService->deleteById. ID: {}", id);
        contactRepository.deleteById(id);
    }

    @Override
    public void batchInsert(List<Contact> contacts) {
        log.debug("ContactService->batchInsert. Size: {}", contacts.size());
        contactRepository.batchInsert(contacts);
    }
}
