package com.kotkina.ContaclsWebDB.listener;

import com.kotkina.ContaclsWebDB.entities.Contact;
import com.kotkina.ContaclsWebDB.services.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ContactsCreator {

    @Value("${app.init.contacts}")
    private boolean initializeContacts;

    private final ContactService contactService;

    @EventListener(ApplicationStartedEvent.class)
    public void createContacts() {
        if (initializeContacts) {
            log.info("Contacts initializing...");
            List<Contact> contacts = new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                int value = i + 1;
                contacts.add(Contact.builder()
                        .id((long) value)
                        .firstName("Marina " + value)
                        .lastName("Kotkina " + value)
                        .email(value + "@gmail.com")
                        .phone("+7 911 111 11 " + String.format("%02d", value))
                        .build());
            }

            contactService.batchInsert(contacts);
        }
    }
}
