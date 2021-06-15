package mx.ipn.upiicsa.smbd.sistemaescolar.service;

import mx.ipn.upiicsa.smbd.sistemaescolar.dao.AsignaturaDao;
import mx.ipn.upiicsa.smbd.sistemaescolar.model.Asignatura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AsignaturasServiceImpl implements AsignaturasService {
    private final AsignaturaDao asignaturaDao;

    private List<Asignatura> asignaturas;

    @Autowired
    public AsignaturasServiceImpl(AsignaturaDao asignaturaDao) {
        this.asignaturaDao = asignaturaDao;
        asignaturas = new ArrayList<>();
    }

    @Override
    public List<Asignatura> getAsignaturas() {
        asignaturas = asignaturaDao.getAsignaturas();
        return asignaturas;
    }

    @Override
    public Asignatura getAsignaturaById(String id) {
        return asignaturaDao.getAsignaturaById(id);
    }

    @Override
    public boolean addAsignatura(Asignatura asignatura) {
        boolean res = false;
        boolean isValid = true;
        if (asignatura.getIdAsignatura().equals("") || asignatura.getIdAsignatura() == null) {
            isValid = false;
        }
        if (asignatura.getNombre().equals("") || asignatura.getNombre() == null) {
            isValid = false;
        }
        if (asignatura.getAcademia() == null) {
            isValid = false;
        }
        if (asignatura.getCreditos() == null || asignatura.getCreditos() <= 0) {
            isValid = false;
        }
        if (isValid) {
            res = asignaturaDao.addAsignatura(asignatura);
        }
        return res;
    }

    @Override
    public boolean deleteAsignatura(String id) {
        return asignaturaDao.deleteAsignatura(id);
    }
}
