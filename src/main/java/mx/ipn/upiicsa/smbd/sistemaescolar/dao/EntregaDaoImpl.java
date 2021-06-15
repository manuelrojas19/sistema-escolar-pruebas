package mx.ipn.upiicsa.smbd.sistemaescolar.dao;

import mx.ipn.upiicsa.smbd.sistemaescolar.model.Entrega;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class EntregaDaoImpl implements EntregaDao {
    private final TransactionTemplate transactionTemplate;
    private final JdbcTemplate jdbcTemplate;
    private final AlumnoDao alumnoDao;
    private final TareasDao tareasDao;

    @Autowired
    public EntregaDaoImpl(DataSource dataSource, AlumnoDao alumnoDao, TareasDao tareasDao) {
        transactionTemplate = new TransactionTemplate(new DataSourceTransactionManager(dataSource));
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.alumnoDao = alumnoDao;
        this.tareasDao = tareasDao;
    }

    private RowMapper<Entrega> entregaRowMapper() {
        return (resultSet, i) -> {
            try {
                Entrega entrega = new Entrega();
                entrega.setAlumno(alumnoDao.getAlumnoById(resultSet.getInt("boleta")));
                entrega.setTarea(tareasDao.getTareasById(resultSet.getInt("id_tarea")));
                entrega.setArchivo(resultSet.getString("archivo"));
                return entrega;
            } catch (SQLException e) {
                throw new RuntimeException("Error al obtener los datos de la base de datos", e);
            }
        };
    }

    /*
     * TODO: corregir esta madre
     * */
    @Override
    public Entrega getEntregaById(Integer idEntrega) {
        Entrega entrega;
        entrega = jdbcTemplate.queryForObject("SELECT * FROM entregas WHERE id_entrega::integer = ?", entregaRowMapper(), idEntrega);
        return entrega;
    }

    @Override
    public Entrega getEntregaByAlumno(Integer idTarea, Integer boleta, Integer idCurso) {
        Entrega entrega;
        try {
            entrega = jdbcTemplate.queryForObject("SELECT * FROM entregas WHERE id_tarea::integer = ? AND boleta::integer = ?", entregaRowMapper(), idTarea, boleta);
        } catch (Exception e) {
            entrega = null;
        }
        return entrega;
    }


    @Override
    public boolean uploadEntrega(Entrega entrega) {
        AtomicBoolean res = new AtomicBoolean(true);
        Integer boleta = entrega.getAlumno().getBoleta();
        Integer idTarea = entrega.getTarea().getId();
        String archivo = entrega.getArchivo();
        try {
            transactionTemplate.executeWithoutResult(transactionStatus -> {
                try {
                    jdbcTemplate.update("INSERT INTO entregas(id_tarea, boleta, archivo) VALUES (?,?,?)", idTarea, boleta, archivo);

                } catch (NoSuchElementException e) {
                    transactionStatus.setRollbackOnly();
                    res.set(false);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            res.set(false);
        }
        return res.get();
    }

    @Override
    public List<Entrega> getEntregasByAlumno(Integer idCurso, Integer boleta) {
        List<Entrega> entregas = new ArrayList<>();
        try {
            entregas = jdbcTemplate.query("SELECT * FROM entregas WHERE boleta::integer = ? AND id_tarea IN (SELECT id_tarea FROM tareas WHERE id_curso::integer = ?)", entregaRowMapper(), boleta, idCurso);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entregas;
    }
}
