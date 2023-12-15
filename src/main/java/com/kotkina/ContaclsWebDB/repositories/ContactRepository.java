package com.kotkina.ContaclsWebDB.repositories;

import com.kotkina.ContaclsWebDB.entities.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactRepository {

    Optional<Contact> findById(Long id);

    List<Contact> findAll();

    Contact save(Contact contact);

    Contact update(Contact contact);

    void deleteById(Long id);

    void batchInsert(List<Contact> contacts);
}
