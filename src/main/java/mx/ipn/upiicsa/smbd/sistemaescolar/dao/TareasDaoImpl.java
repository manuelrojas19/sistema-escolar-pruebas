package mx.ipn.upiicsa.smbd.sistemaescolar.dao;

import mx.ipn.upiicsa.smbd.sistemaescolar.model.Curso;
import mx.ipn.upiicsa.smbd.sistemaescolar.model.Tarea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class TareasDaoImpl implements TareasDao{
    private final JdbcTemplate jdbcTemplate;
    private final CursoDao cursoDao;

    @Autowired
    public TareasDaoImpl(DataSource dataSource, CursoDao cursoDao) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.cursoDao = cursoDao;
    }

    private RowMapper<Tarea> tareasRowMapper() {
        return (resultSet, i) -> {
            Tarea tarea = new Tarea();
            tarea.setId(resultSet.getInt("id_tarea"));
            Curso curso;
            curso = cursoDao.getCursoById(resultSet.getInt("id_curso"));
            tarea.setCurso(curso);
            tarea.setTitulo(resultSet.getString("titulo_tarea"));
            tarea.setDescripcion(resultSet.getString("descripcion"));
            return tarea;
        };
    }

    @Override
    public List<Tarea> getTareasByCurso(Integer idCurso) {
        List<Tarea> tareas;
        tareas = jdbcTemplate.query("SELECT * FROM tareas WHERE id_curso::integer = ?", tareasRowMapper(), idCurso);
        return tareas;
    }

    @Override
    public void addTareaByCourse(Tarea tarea, Integer idCurso) {
        jdbcTemplate.update("INSERT INTO tareas(id_curso, titulo_tarea, descripcion) values (?, ?, ?)", idCurso, tarea.getTitulo(), tarea.getDescripcion());
    }

    @Override
    public boolean deleteTareas(Integer idTarea) {
        boolean res = true;
        try {
            jdbcTemplate.update("DELETE FROM tareas WHERE id_tarea::integer = ?", idTarea);
        } catch (Exception e) {
            e.printStackTrace();
            res = false;
        }
        return res;
    }

    @Override
    public Tarea getTareasById(Integer idTarea) {
        Tarea tarea;
        tarea = jdbcTemplate.queryForObject("SELECT * FROM tareas WHERE id_tarea::integer = ?", tareasRowMapper(), idTarea);
        return tarea;
    }
}
