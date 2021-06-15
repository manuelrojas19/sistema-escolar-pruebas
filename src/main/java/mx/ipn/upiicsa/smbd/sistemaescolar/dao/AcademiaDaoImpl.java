package mx.ipn.upiicsa.smbd.sistemaescolar.dao;

import mx.ipn.upiicsa.smbd.sistemaescolar.model.Academia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.List;

@Component
public class AcademiaDaoImpl implements AcademiaDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AcademiaDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<Academia> academiaRowMapper() {
        return (ResultSet rs, int rowNumber) -> {
            Academia academia = new Academia();
            academia.setId(rs.getString("id_academia"));
            academia.setNombre(rs.getString("nombre_academia"));
            return academia;
        };
    }

    @Override
    public List<Academia> getAcademias() {
        List<Academia> academias;
        academias = jdbcTemplate.query("SELECT * FROM academias", academiaRowMapper());
        return academias;
    }

    @Override
    public Academia getAcademiaById(String idAcademia) {
        Academia academia;
        academia = jdbcTemplate.queryForObject("SELECT * FROM academias WHERE id_academia = ?", academiaRowMapper(), idAcademia);
        return academia;
    }
}
