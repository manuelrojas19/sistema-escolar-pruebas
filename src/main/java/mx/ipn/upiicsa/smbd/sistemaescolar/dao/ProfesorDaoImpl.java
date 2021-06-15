package mx.ipn.upiicsa.smbd.sistemaescolar.dao;

import mx.ipn.upiicsa.smbd.sistemaescolar.model.Academia;
import mx.ipn.upiicsa.smbd.sistemaescolar.model.Profesor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class ProfesorDaoImpl implements ProfesorDao {
    private final TransactionTemplate transactionTemplate;
    private final JdbcTemplate jdbcTemplate;
    private final AcademiaDao academiaDao;

    @Autowired
    ProfesorDaoImpl(DataSource dataSource, AcademiaDao academiaDao) {
        transactionTemplate = new TransactionTemplate(new DataSourceTransactionManager(dataSource));
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.academiaDao = academiaDao;
    }

    private RowMapper<Profesor> profesorRowMapper() {
        return (rs, rowNumber) -> {
            try {
                Profesor profesor = new Profesor();
                profesor.setIdProfesor(rs.getInt("id_profesor"));
                profesor.setPrimerApellido(rs.getString("primer_apellido"));
                profesor.setSegundoApellido(rs.getString("segundo_apellido"));
                profesor.setPrimerNombre(rs.getString("primer_nombre"));
                profesor.setSegundoNombre(rs.getString("segundo_nombre"));
                Academia academia;
                academia = academiaDao.getAcademiaById(rs.getString("id_academia"));
                profesor.setAcademia(academia);
                return profesor;
            } catch (SQLException e) {
                throw new RuntimeException("Error al obtener los datos de la base de datos", e);
            }
        };
    }

    @Override
    public List<Profesor> getProfesores() {
        List<Profesor> profesorList;
        profesorList = jdbcTemplate.query("SELECT * FROM profesores ORDER BY id_academia", profesorRowMapper());
        return profesorList;
    }

    @Override
    public Profesor getProfesorById(Integer idProfesor) {
        Profesor profesorOut;
        profesorOut = jdbcTemplate.queryForObject("SELECT * FROM profesores WHERE id_profesor::integer = ?", profesorRowMapper(), idProfesor);
        return profesorOut;
    }

    @Override
    public Profesor addProfesor(Profesor profesor) {
        Integer idProfesor = profesor.getIdProfesor();
        String primerApellido = profesor.getPrimerApellido();
        String segundoApellido = profesor.getSegundoApellido();
        String primerNombre = profesor.getPrimerNombre();
        String segundoNombre = profesor.getSegundoNombre();
        String idAcademia = profesor.getAcademia().getId();
        String usuario = profesor.getPerfil().getUser();
        String password = profesor.getPerfil().getPassword();
        Integer rol = profesor.getPerfil().getIdRol();
        Integer status = profesor.getPerfil().getStatus();

        try {
            transactionTemplate.executeWithoutResult(transactionStatus -> {
                try {
                    jdbcTemplate.update("INSERT INTO perfiles(usuario, password, id_rol, estatus) VALUES (?,?,?,?)",
                            usuario, password, rol, status);
                    Integer idPerfil = jdbcTemplate.queryForObject("SELECT id_perfil FROM perfiles WHERE usuario = ?", Integer.class, usuario);
                    jdbcTemplate.update("INSERT INTO profesores(id_profesor, primer_apellido, segundo_apellido, primer_nombre, segundo_nombre, id_academia, id_perfil) values (?,?,?,?,?,?,?)", idProfesor, primerApellido, segundoApellido, primerNombre, segundoNombre, idAcademia, idPerfil);
                } catch (NoSuchElementException e) {
                    transactionStatus.setRollbackOnly();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return profesor;
    }

    @Override
    public void editarProfesor(Profesor profesor, Integer idProfesor) {
        Integer id = profesor.getIdProfesor();
        String primerApellido = profesor.getPrimerApellido();
        String segundoApellido = profesor.getSegundoApellido();
        String primerNombre = profesor.getPrimerNombre();
        String segundoNombre = profesor.getSegundoNombre();
        String idAcademia = profesor.getAcademia().getId();
        jdbcTemplate.update("UPDATE profesores SET id_profesor = ?, primer_apellido = ?, segundo_apellido = ?, primer_nombre = ?, segundo_nombre = ?, id_academia = ? WHERE id_profesor::integer = ?",
                id, primerApellido, segundoApellido, primerNombre, segundoNombre, idAcademia, idProfesor);
    }

    @Override
    public void eliminarProfesor(Integer idProfesor) {
        transactionTemplate.executeWithoutResult(transactionStatus -> {
            jdbcTemplate.update("DELETE FROM profesores WHERE id_profesor::integer  = ?", idProfesor);
            jdbcTemplate.update("DELETE FROM perfiles WHERE usuario  = ?", idProfesor.toString());
        });
    }

}
