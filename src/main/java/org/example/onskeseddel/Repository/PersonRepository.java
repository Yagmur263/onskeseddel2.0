package org.example.onskeseddel.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonRepository {

    private final JdbcTemplate jdbcTemplate;

    public PersonRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String findNavn() {
        List<String> resultat = jdbcTemplate.query(
                "SELECT navn FROM person WHERE id = 1", (rs, i) -> rs.getString("navn"));
        return resultat.isEmpty() ? null : resultat.get(0);
    }

    public void gemNavn(String navn) {
        jdbcTemplate.update(
                "INSERT INTO person (id, navn) VALUES (1, ?) ON DUPLICATE KEY UPDATE navn = ?",
                navn, navn);
    }
}