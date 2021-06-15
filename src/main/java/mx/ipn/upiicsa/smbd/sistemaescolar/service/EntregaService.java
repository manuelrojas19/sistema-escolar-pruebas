package mx.ipn.upiicsa.smbd.sistemaescolar.service;

import mx.ipn.upiicsa.smbd.sistemaescolar.model.Entrega;

import java.util.List;

public interface EntregaService {
    Entrega getEntregaByAlumno(Integer idTarea, Integer boleta, Integer idCurso);
    boolean uploadEntrega(Entrega entrega);
    List<Entrega> getEntregasByAlumno(Integer id_curso, Integer boleta);
}
