package mx.ipn.upiicsa.smbd.sistemaescolar.dao;

import mx.ipn.upiicsa.smbd.sistemaescolar.model.Alumno;
import mx.ipn.upiicsa.smbd.sistemaescolar.model.Programa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class AlumnoDaoImpl implements AlumnoDao {
    private final TransactionTemplate transactionTemplate;
    private final JdbcTemplate jdbcTemplate;
    private final ProgramaDao programaDao;

    @Autowired
    public AlumnoDaoImpl(DataSource dataSource, ProgramaDao programaDao) {
        transactionTemplate = new TransactionTemplate(new DataSourceTransactionManager(dataSource));
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.programaDao = programaDao;
    }

    private RowMapper<Alumno> alumnoRowMapper() {
            return (ResultSet rs, int rowNumber) -> {
                Alumno alumno = new Alumno();
                alumno.setBoleta(rs.getInt("boleta"));
                alumno.setPrimerApellido(rs.getString("primer_apellido"));
                alumno.setSegundoApellido(rs.getString("segundo_apellido"));
                alumno.setPrimerNombre(rs.getString("primer_nombre"));
                alumno.setSegundoNombre(rs.getString("segundo_nombre"));
                alumno.setCreditos(rs.getInt("num_creditos"));
                Programa programa;
                programa = programaDao.getProgramaById(rs.getString("id_programa"));
                alumno.setPrograma(programa);
                return alumno;
            };
    }

    @Override
    public List<Alumno> getAlumnos() {
        List<Alumno> alumnos;
        alumnos = jdbcTemplate.query("SELECT * FROM estudiantes ORDER BY id_programa, primer_apellido", alumnoRowMapper());
        return alumnos;
    }

    @Override
    public List<Alumno> getAlumnosByCourse(Integer idCourse) {
        List<Alumno> alumnos;
        alumnos = jdbcTemplate.query("SELECT * FROM estudiantes WHERE boleta IN (SELECT boleta FROM inscritos WHERE id_curso::integer = ?)", alumnoRowMapper(), idCourse);
        return alumnos;
    }

    @Override
    public Alumno getAlumnoById(Integer boleta) {
        Alumno alumno;
        alumno = jdbcTemplate.queryForObject("SELECT * FROM estudiantes WHERE boleta::integer = ?", alumnoRowMapper(), boleta);
        return alumno;
    }

    @Override
    public boolean addAlumno(Alumno alumno) {
        AtomicBoolean res = new AtomicBoolean(true);

        Integer boleta = alumno.getBoleta();
        String primerApellido = alumno.getPrimerApellido();
        String segundoApellido = alumno.getSegundoApellido();
        String primerNombre = alumno.getPrimerNombre();
        String segundoNombre = alumno.getSegundoNombre();
        String idPrograma = alumno.getPrograma().getId();
        String usuario = alumno.getPerfil().getUser();
        String password = alumno.getPerfil().getPassword();
        Integer creditos = alumno.getCreditos();
        Integer rol = alumno.getPerfil().getIdRol();
        Integer status = alumno.getPerfil().getStatus();

        try {
            transactionTemplate.executeWithoutResult(transactionStatus -> {
                try {
                    jdbcTemplate.update("INSERT INTO perfiles(usuario, password, id_rol, estatus) VALUES (?,?,?,?)",
                            usuario, password, rol, status);
                    Integer idPerfil = jdbcTemplate.queryForObject("SELECT id_perfil FROM perfiles WHERE usuario = ?", Integer.class, usuario);
                    jdbcTemplate.update("INSERT INTO estudiantes(boleta, primer_apellido, segundo_apellido, " +
                                    "primer_nombre, segundo_nombre, num_creditos, id_programa, id_perfil) VALUES (?,?,?,?,?,?,?,?)",
                            boleta, primerApellido, segundoApellido, primerNombre, segundoNombre, creditos, idPrograma, idPerfil);
                } catch (NoSuchElementException e) {
                    transactionStatus.setRollbackOnly();
                    res.set(false);
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            res.set(false);
        }
        return res.get();
    }

    @Override
    public boolean editarAlumno(Alumno alumno, Integer idAlumno) {
        boolean res = true;
        Integer boleta = alumno.getBoleta();
        String primerApellido = alumno.getPrimerApellido();
        String segundoApellido = alumno.getSegundoApellido();
        String primerNombre = alumno.getPrimerNombre();
        String segundoNombre = alumno.getSegundoNombre();
        String idPrograma = alumno.getPrograma().getId();
        Integer creditos = alumno.getCreditos();
        try {
            jdbcTemplate.update("UPDATE estudiantes SET primer_apellido = ?, segundo_apellido = ?, primer_nombre = ?, segundo_nombre = ? , boleta = ?, id_programa = ?, num_creditos = ? WHERE boleta::integer = ?",
                    primerApellido, segundoApellido, primerNombre, segundoNombre, boleta, idPrograma, creditos, idAlumno);
        } catch (Exception e) {
            res = false;
        }
        return res;
    }

    @Override
    public void eliminarAlumno(Integer boleta) {
        transactionTemplate.execute(transactionStatus -> {
            jdbcTemplate.update("DELETE FROM estudiantes WHERE boleta::integer = ?", boleta);
            jdbcTemplate.update("DELETE FROM perfiles WHERE usuario = ?", boleta.toString());
            return null;
        });
    }
}
