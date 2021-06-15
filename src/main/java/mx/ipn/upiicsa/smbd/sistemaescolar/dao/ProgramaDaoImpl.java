package mx.ipn.upiicsa.smbd.sistemaescolar.dao;

import mx.ipn.upiicsa.smbd.sistemaescolar.model.Programa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class ProgramaDaoImpl implements ProgramaDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProgramaDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<Programa> programaAcademicoRowMapper() {
        return (resultSet, i) -> {
            Programa programa = new Programa();
            programa.setId(resultSet.getString("id_programa"));
            programa.setNombre(resultSet.getString("nombre_programa"));
            return programa;
        };
    }

    @Override
    public List<Programa> getProgramas() {
        List<Programa> programas;
        programas = jdbcTemplate.query("SELECT * FROM programas_academicos", programaAcademicoRowMapper());
        return programas;
    }

    @Override
    public Programa getProgramaById(String idPrograma) {
        Programa programa;
        programa = jdbcTemplate.queryForObject("SELECT * FROM programas_academicos WHERE id_programa = ?", programaAcademicoRowMapper(), idPrograma);
        return programa;
    }


}
