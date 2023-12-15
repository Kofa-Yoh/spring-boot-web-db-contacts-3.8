package com.kotkina.ContaclsWebDB.services;

import com.kotkina.ContaclsWebDB.entities.Contact;

import java.util.List;

public interface ContactService {

    List<Contact> findAll();

    Contact findById(Long id);

    Contact save(Contact contact);

    Contact update(Contact contact);

    void deleteById(Long id);

    void batchInsert(List<Contact> contacts);
}
