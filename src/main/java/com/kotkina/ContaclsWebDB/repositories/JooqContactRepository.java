package com.kotkina.ContaclsWebDB.repositories;

import com.kotkina.ContaclsWebDB.Tables;
import com.kotkina.ContaclsWebDB.entities.Contact;
import com.kotkina.ContaclsWebDB.exceptions.ContactNotExistsException;
import com.kotkina.ContaclsWebDB.tables.records.ContactRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JooqContactRepository implements ContactRepository {

    private final DSLContext dslContext;

    @Override
    public Optional<Contact> findById(Long id) {
        return dslContext.selectFrom(Tables.CONTACT)
                .where(Tables.CONTACT.ID.eq(id))
                .fetchOptional()
                .map(contactRecord -> contactRecord.into(Contact.class));
    }

    @Override
    public List<Contact> findAll() {
        return dslContext.selectFrom(Tables.CONTACT)
                .fetchInto(Contact.class);
    }

    @Override
    public Contact save(Contact contact) {
        contact.setId(System.currentTimeMillis());

        ContactRecord contactRecord = dslContext.newRecord(Tables.CONTACT, contact);
        contactRecord.store();
        return contactRecord.into(Contact.class);
    }

    @Override
    public Contact update(Contact contact) {
        var savedContactRecord = dslContext.update(Tables.CONTACT)
                .set(dslContext.newRecord(Tables.CONTACT, contact))
                .where(Tables.CONTACT.ID.eq(contact.getId()))
                .returning()
                .fetchOptional();
        return savedContactRecord
                .map(contactRecord -> contactRecord.into(Contact.class))
                .orElseThrow(() -> new ContactNotExistsException("No contacts with such id: " + contact.getId()));
    }

    @Override
    public void deleteById(Long id) {
        dslContext.deleteFrom(Tables.CONTACT)
                .where(Tables.CONTACT.ID.eq(id))
                .execute();
    }

    @Override
    public void batchInsert(List<Contact> contacts) {
        List<Query> insertQueries = new ArrayList<>();

        for (Contact contact : contacts) {
            insertQueries.add(
                    dslContext.insertInto(
                            Tables.CONTACT,
                            Tables.CONTACT.ID,
                            Tables.CONTACT.FIRST_NAME,
                            Tables.CONTACT.LAST_NAME,
                            Tables.CONTACT.EMAIL,
                            Tables.CONTACT.PHONE
                    ).values(
                            contact.getId(),
                            contact.getFirstName(),
                            contact.getLastName(),
                            contact.getEmail(),
                            contact.getPhone()
                    ).onConflictDoNothing()
            );
        }

        dslContext.batch(insertQueries).execute();
    }
}
