package com.kotkina.ContaclsWebDB.repositories;

import com.kotkina.ContaclsWebDB.entities.Contact;
import com.kotkina.ContaclsWebDB.exceptions.ContactNotExistsException;
import com.kotkina.ContaclsWebDB.repositories.mapper.ContactMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcContactRepository implements ContactRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Contact> findById(Long id) {
        String sql = "SELECT * FROM contact WHERE id = ?";
        Contact contact = DataAccessUtils.singleResult(
                jdbcTemplate.query(sql,
                        new ArgumentPreparedStatementSetter(new Object[]{id}),
                        new RowMapperResultSetExtractor<>(new ContactMapper(), 1))
        );
        return Optional.ofNullable(contact);
    }

    @Override
    public List<Contact> findAll() {
        String sql = "SELECT * FROM contact";
        return jdbcTemplate.query(sql, new ContactMapper());
    }

    @Override
    public Contact save(Contact contact) {
        contact.setId(System.currentTimeMillis());

        String sql = "INSERT INTO contact (id, first_name, last_name, email, phone) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, contact.getId(), contact.getFirstName(), contact.getLastName(), contact.getEmail(), contact.getPhone());

        return contact;
    }

    @Override
    public Contact update(Contact contact) {
        Contact existedContact = findById(contact.getId()).orElse(null);
        if (existedContact == null) {
            throw new ContactNotExistsException("No contacts with such id: " + contact.getId());
        }

        String sql = "UPDATE contact SET first_name = ?, last_name = ?, email = ?, phone = ? WHERE id = ?";
        jdbcTemplate.update(sql, contact.getFirstName(), contact.getLastName(), contact.getEmail(), contact.getPhone(), contact.getId());
        return contact;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM contact WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void batchInsert(List<Contact> contacts) {
        String sql = "INSERT INTO contact (id, first_name, last_name, email, phone) VALUES (?, ?, ?, ?, ?) ON CONFLICT DO NOTHING";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Contact contact = contacts.get(i);
                ps.setLong(1, contact.getId());
                ps.setString(2, contact.getFirstName());
                ps.setString(3, contact.getLastName());
                ps.setString(4, contact.getEmail());
                ps.setString(5, contact.getPhone());
            }

            @Override
            public int getBatchSize() {
                return contacts.size();
            }
        });
    }
}
