package ru.skillbox.contactapplications.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.skillbox.contactapplications.model.Contact;
import ru.skillbox.contactapplications.repository.IContactRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty("application.init.download-default-contacts")
public class DownloadDefaultContactListener {

    private final IContactRepository contactRepository;

    @EventListener(ApplicationStartedEvent.class)
    private void downloadDefaultContacts() {
        List<Contact> contacts = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            long id = i + 1;
            Contact contact = new Contact();
            contact.setId(id);
            contact.setFirstName("First name " + id);
            contact.setLastName("Last name " + id);
            contact.setEmail("someemail" + id + "@domen.com");
            contact.setPhone("1234567" + id);
            contacts.add(contact);
        }
        contactRepository.batchInsert(contacts);

    }

}
