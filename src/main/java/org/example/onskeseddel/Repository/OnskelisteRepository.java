package org.example.onskeseddel.Repository;

import org.example.onskeseddel.Model.Onskeliste;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OnskelisteRepository {

    private final JdbcTemplate jdbcTemplate;

    public OnskelisteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Onskeliste> rowMapper = (rs, rowNum) -> {
        Onskeliste liste = new Onskeliste();
        liste.setId(rs.getInt("id"));
        liste.setTitel(rs.getString("titel"));
        liste.setDelingsKode(rs.getString("delingskode"));
        return liste;
    };

    public List<Onskeliste> findAlle() {
        return jdbcTemplate.query("SELECT id, titel, delingskode FROM Onskeliste", rowMapper);
    }

    public Onskeliste findById(int id) {
        List<Onskeliste> resultat = jdbcTemplate.query(
                "SELECT id, titel, delingskode FROM Onskeliste WHERE id = ?", rowMapper, id);
        return resultat.isEmpty() ? null : resultat.get(0);
    }

    public void gem(Onskeliste liste) {
        jdbcTemplate.update(
                "INSERT INTO Onskeliste (titel, delingskode) VALUES (?, ?)",
                liste.getTitel(), liste.getDelingsKode());
    }

    public void slet(int id) {
        jdbcTemplate.update("DELETE FROM Onskeliste WHERE id = ?", id);

    }
    public Onskeliste findByDelingskode(String delingskode) {
        List<Onskeliste> resultat = jdbcTemplate.query(
                "SELECT id, titel, delingskode FROM onskeliste WHERE delingskode = ?",
                rowMapper, delingskode);
        return resultat.isEmpty() ? null : resultat.get(0);

    }
    public int antalLister() {
        Integer antal = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM onskeliste", Integer.class);
        return antal == null ? 0 : antal;
    }
}