package com.kotkina.ContaclsWebDB.services;

import com.kotkina.ContaclsWebDB.entities.Contact;
import com.kotkina.ContaclsWebDB.exceptions.ContactNotExistsException;
import com.kotkina.ContaclsWebDB.repositories.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    @Override
    public List<Contact> findAll() {
        return contactRepository.findAll();
    }

    @Override
    public Contact findById(Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new ContactNotExistsException("No contacts with such id: " + id));
    }

    @Override
    public Contact save(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public Contact update(Contact contact) {
        return contactRepository.update(contact);
    }

    @Override
    public void deleteById(Long id) {
        contactRepository.deleteById(id);
    }

    @Override
    public void batchInsert(List<Contact> contacts) {
        contactRepository.batchInsert(contacts);
    }
}
