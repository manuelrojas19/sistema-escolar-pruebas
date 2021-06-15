package mx.ipn.upiicsa.smbd.sistemaescolar.service;

import mx.ipn.upiicsa.smbd.sistemaescolar.dao.TareasDao;
import mx.ipn.upiicsa.smbd.sistemaescolar.model.Tarea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TareasServiceImpl implements TareasServices {
    private final TareasDao tareasDao;

    @Autowired
    public TareasServiceImpl(TareasDao tareasDao) {
        this.tareasDao = tareasDao;
    }

    @Override
    public List<Tarea> getTareasByCurso(Integer idCurso) {
        return tareasDao.getTareasByCurso(idCurso);
    }

    @Override
    public Tarea getTareaById(Integer idTarea) {
        return tareasDao.getTareasById(idTarea);
    }

    @Override
    public void addTareaByCourse(Integer idCurso, Tarea tarea) {
        tareasDao.addTareaByCourse(tarea, idCurso);
    }

    @Override
    public boolean deleteTarea(Integer idTarea) {
        return tareasDao.deleteTareas(idTarea);
    }
}
