package mx.ipn.upiicsa.smbd.sistemaescolar.dao;

import mx.ipn.upiicsa.smbd.sistemaescolar.model.Asignatura;
import mx.ipn.upiicsa.smbd.sistemaescolar.model.Curso;
import mx.ipn.upiicsa.smbd.sistemaescolar.model.Profesor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class CursoDaoImpl implements CursoDao {
    private final TransactionTemplate transactionTemplate;
    private final JdbcTemplate jdbcTemplate;
    private final ProfesorDao profesorDao;
    private final AsignaturaDao asignaturaDao;

    @Autowired
    public CursoDaoImpl(TransactionTemplate transactionTemplate, DataSource dataSource, ProfesorDao profesorDao, AsignaturaDao asignaturaDao) {
        this.transactionTemplate = transactionTemplate;
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.profesorDao = profesorDao;
        this.asignaturaDao = asignaturaDao;
    }

    private RowMapper<Curso> cursoRowMapper() {
        return (rs, rowNumber) -> {
            try {
                Curso curso = new Curso();
                curso.setId(rs.getInt("id_curso"));
                Asignatura asignatura;
                asignatura = asignaturaDao.getAsignaturaById(rs.getString("id_asignatura"));
                curso.setAsignatura(asignatura);
                Profesor profesor = profesorDao.getProfesorById(rs.getInt("id_profesor"));
                curso.setProfesor(profesor);
                return curso;
            } catch (SQLException e) {
                throw new RuntimeException("A ocurrido un error al recuperar los datos de la base de datos", e);
            }
        };
    }

    @Override
    public List<Curso> getCursos() {
        List<Curso> cursoList;
        cursoList = jdbcTemplate.query("SELECT * FROM cursos", cursoRowMapper());
        return cursoList;
    }

    @Override
    public List<Curso> getCursosByAlumno(Integer boleta) {
        List<Curso> cursoList;
        cursoList = jdbcTemplate.query("SELECT * FROM cursos WHERE id_curso IN (SELECT id_curso FROM inscritos WHERE boleta::integer = ?)", cursoRowMapper(), boleta);
        return cursoList;
    }

    @Override
    public List<Curso> getCursosByProfesor(Integer idProfesor) {
        List<Curso> cursoList;
        cursoList = jdbcTemplate.query("SELECT * FROM cursos WHERE id_profesor IN (SELECT id_profesor FROM profesores WHERE id_profesor::integer = ?)", cursoRowMapper(), idProfesor);
        return cursoList;
    }

    @Override
    public List<Curso> getEnrollableCoursesByAlumno(Integer boleta) {
        List<Curso> cursoList;
        cursoList = jdbcTemplate.query("(SELECT * FROM cursos) EXCEPT (SELECT * FROM cursos where id_curso IN(SELECT id_curso from inscritos where boleta::integer = ?))", cursoRowMapper(), boleta);
        return cursoList;
    }

    @Override
    public Curso getCursoById(Integer idCurso) {
        Curso curso;
        curso = jdbcTemplate.queryForObject("SELECT * FROM cursos WHERE id_curso::integer = ?", cursoRowMapper(), idCurso);
        return curso;
    }

    @Override
    public boolean addCurso(Curso curso) {
        boolean res = true;
        String idAsignatura = curso.getAsignatura().getIdAsignatura();
        Integer idProfesor = curso.getProfesor().getIdProfesor();
        try {
            jdbcTemplate.update("INSERT INTO cursos(id_asignatura, id_profesor) VALUES (?,?)", idAsignatura, idProfesor);
        } catch (Exception e) {
            e.printStackTrace();
            res = false;
        }
        return res;
    }

    @Override
    public boolean deleteCurso(Integer id) {
        boolean res = true;
        try {
            jdbcTemplate.update("DELETE FROM cursos WHERE id_curso = ?", id);
        } catch (Exception e) {
            e.printStackTrace();
            res = false;
        }
        return res;
    }

    @Override
    public boolean enrollAlumnoInCurso(Curso curso, Integer boletaAlumno) {
        AtomicBoolean res = new AtomicBoolean(true);
        try {
            transactionTemplate.executeWithoutResult(transactionStatus -> {
                try {
                    jdbcTemplate.update("INSERT INTO inscritos(boleta, id_curso) VALUES (?,?)", boletaAlumno, curso.getId());
                    jdbcTemplate.update("UPDATE estudiantes SET num_creditos = num_creditos - ? WHERE boleta::integer = ?", curso.getAsignatura().getCreditos(), boletaAlumno);
                } catch (NoSuchElementException e) {
                    e.printStackTrace();
                    transactionStatus.setRollbackOnly();
                    res.set(false);
                }
            });
        } catch (Exception e) {
            res.set(false);
            e.printStackTrace();
        }
        return res.get();
    }
}
