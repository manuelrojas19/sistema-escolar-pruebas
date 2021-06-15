package mx.ipn.upiicsa.smbd.sistemaescolar.dao;

import mx.ipn.upiicsa.smbd.sistemaescolar.model.Entrega;

import java.util.List;

public interface EntregaDao {
    Entrega getEntregaById(Integer idEntrega);
    Entrega getEntregaByAlumno(Integer idTarea, Integer boleta, Integer idCurso);
    boolean uploadEntrega(Entrega entrega);
    List<Entrega> getEntregasByAlumno(Integer idCurso, Integer boleta);
}
