package ru.skillbox.contactapplications.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;
import ru.skillbox.contactapplications.exceptions.ContactNotFoundException;
import ru.skillbox.contactapplications.model.Contact;
import ru.skillbox.contactapplications.repository.mapper.ContactRowMapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class JdbcContactRepository implements IContactRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Contact> findAll() {
        log.debug("JdbcContactRepository->findAll");
        String sql = "SELECT * FROM contact";
        return jdbcTemplate.query(sql, new ContactRowMapper());
    }

    @Override
    public Optional<Contact> findById(Long id) {
        log.debug("JdbcContactRepository->findById: {}", id);
        String sql = "SELECT * FROM contact WHERE id = ?";
        Contact contact = DataAccessUtils.singleResult(
                jdbcTemplate.query(
                        sql,
                        new ArgumentPreparedStatementSetter(new Object[] {id}),
                        new RowMapperResultSetExtractor<>(new ContactRowMapper(), 1)
                )
        );
        return Optional.ofNullable(contact);
    }

    @Override
    public Contact save(Contact contact) {
        log.debug("JdbcContactRepository->save: {}", contact);
        contact.setId(System.currentTimeMillis());
        String sql = "INSERT INTO contact (id, first_name, last_name, email, phone) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                contact.getId(),
                contact.getFirstName(),
                contact.getLastName(),
                contact.getEmail(),
                contact.getPhone());
        return contact;
    }

    @Override
    public Contact update(Contact contact) {
        log.debug("JdbcContactRepository->update: {}", contact);
        Contact existedContact = findById(contact.getId()).orElse(null);
        if (Objects.nonNull(existedContact)) {
            String sql = "UPDATE contact SET first_name = ?, last_name = ?, email = ?, phone = ? WHERE id = ?";
            jdbcTemplate.update(sql,
                    contact.getFirstName(),
                    contact.getLastName(),
                    contact.getEmail(),
                    contact.getPhone(),
                    contact.getId());

            return contact;
        } else {
            log.warn("Contact with id: {} notFound", contact.getId());
            throw new ContactNotFoundException("Contact not found. ID: " + contact.getId());
        }
    }

    @Override
    public void deleteById(Long id) {
        log.debug("JdbcContactRepository->deleteById ID: {}", id);
        String sql = "DELETE FROM contact WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void batchInsert(List<Contact> contacts) {
        log.debug("JdbcContactRepository->batchInsert");
        String sql = "INSERT INTO contact (id, first_name, last_name, email, phone) VALUES (?, ?, ?, ?, ?)";
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
