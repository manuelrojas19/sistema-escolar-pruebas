package mx.ipn.upiicsa.smbd.sistemaescolar.dao;

import mx.ipn.upiicsa.smbd.sistemaescolar.model.Academia;
import mx.ipn.upiicsa.smbd.sistemaescolar.model.Asignatura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.List;

@Component
public class AsignaturaDaoImpl implements AsignaturaDao {
    private final JdbcTemplate jdbcTemplate;
    private final AcademiaDao academiaDao;

    @Autowired
    public AsignaturaDaoImpl(DataSource dataSource, AcademiaDao academiaDao) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.academiaDao = academiaDao;
    }

    public RowMapper<Asignatura> asignaturaRowMapper() {
        return (ResultSet rs, int rowNumber) -> {
            Asignatura asignatura = new Asignatura();
            asignatura.setIdAsignatura(rs.getString("id_asignatura"));
            asignatura.setNombre(rs.getString("nombre_asignatura"));
            Academia academia;
            academia = academiaDao.getAcademiaById(rs.getString("id_academia"));
            asignatura.setAcademia(academia);
            asignatura.setCreditos(rs.getInt("creditos"));
            return asignatura;
        };
    }

    @Override
    public List<Asignatura> getAsignaturas() {
        List<Asignatura> asignaturas;
        asignaturas = jdbcTemplate.query("SELECT * FROM asignaturas", asignaturaRowMapper());
        return asignaturas;
    }

    @Override
    public Asignatura getAsignaturaById(String idAsignatura) {
        Asignatura asignatura;
        asignatura = jdbcTemplate.queryForObject("SELECT * FROM asignaturas WHERE id_asignatura = ?", asignaturaRowMapper(), idAsignatura);
        return asignatura;
    }

    @Override
    public boolean addAsignatura(Asignatura asignatura) {
        boolean res = true;
        String idAsignatura = asignatura.getIdAsignatura();
        String nombreAsignatura = asignatura.getNombre();
        String idAcademia = asignatura.getAcademia().getId();
        Integer creditos = asignatura.getCreditos();
        try {
            jdbcTemplate.update("INSERT INTO asignaturas(id_asignatura, nombre_asignatura, id_academia, creditos) VALUES (?,?,?,?)", idAsignatura, nombreAsignatura, idAcademia, creditos);
        } catch (Exception e) {
            res = false;
        }
        return res;
    }

    @Override
    public boolean deleteAsignatura(String idAsignatura) {
        boolean res = true;
        try {
            jdbcTemplate.update("DELETE FROM asignaturas WHERE id_asignatura = ?", idAsignatura);
        } catch (Exception e) {
            e.printStackTrace();
            res = false;
        }
        return false;
    }
}
