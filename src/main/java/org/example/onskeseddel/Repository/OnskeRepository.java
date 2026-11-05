package org.example.onskeseddel.Repository;

import org.example.onskeseddel.Model.OnskeModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OnskeRepository {

    private final JdbcTemplate jdbcTemplate;

    public OnskeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<OnskeModel> rowMapper = (rs, rowNum) -> {
        OnskeModel onske = new OnskeModel();
        onske.setId(rs.getInt("id"));
        onske.setOnskelisteId(rs.getInt("onskeliste_id"));
        onske.setNavn(rs.getString("navn"));
        onske.setBeskrivelse(rs.getString("beskrivelse"));
        onske.setLink(rs.getString("link"));
        onske.setPris(rs.getDouble("pris"));
        onske.setAntal(rs.getInt("antal"));
        onske.setKoebt(rs.getBoolean("koebt"));
        return onske;
    };

    public List<OnskeModel> findAlleForListe(int onskelisteId) {
        return jdbcTemplate.query(
                "SELECT * FROM onske WHERE onskeliste_id = ?", rowMapper, onskelisteId);
    }

    public void gem(OnskeModel onske) {
        jdbcTemplate.update(
                "INSERT INTO onske (onskeliste_id, navn, beskrivelse, link, pris, antal) VALUES (?, ?, ?, ?, ?, ?)",
                onske.getOnskelisteId(), onske.getNavn(), onske.getBeskrivelse(),
                onske.getLink(), onske.getPris(), onske.getAntal());
    }

    public void slet(int id) {
        jdbcTemplate.update("DELETE FROM onske WHERE id = ?", id);
    }
}