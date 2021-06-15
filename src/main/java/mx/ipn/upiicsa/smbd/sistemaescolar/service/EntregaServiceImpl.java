package mx.ipn.upiicsa.smbd.sistemaescolar.service;

import mx.ipn.upiicsa.smbd.sistemaescolar.dao.EntregaDao;
import mx.ipn.upiicsa.smbd.sistemaescolar.model.Entrega;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EntregaServiceImpl implements EntregaService {
    private final EntregaDao entregaDao;

    @Autowired
    public EntregaServiceImpl(EntregaDao entregaDao) {
        this.entregaDao = entregaDao;
    }

    @Override
    public Entrega getEntregaByAlumno(Integer idTarea, Integer boleta, Integer idCurso) {
        return entregaDao.getEntregaByAlumno(idTarea, boleta, idCurso);
    }

    @Override
    public boolean uploadEntrega(Entrega entrega) {
        return entregaDao.uploadEntrega(entrega);
    }

    @Override
    public List<Entrega> getEntregasByAlumno(Integer id_curso, Integer boleta) {
        List<Entrega> entregas = new ArrayList<>();
        entregas = entregaDao.getEntregasByAlumno(id_curso, boleta);
        return entregas;
    }


}
