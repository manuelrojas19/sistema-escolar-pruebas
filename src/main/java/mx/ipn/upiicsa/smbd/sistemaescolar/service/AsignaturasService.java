package mx.ipn.upiicsa.smbd.sistemaescolar.service;

import mx.ipn.upiicsa.smbd.sistemaescolar.model.Asignatura;

import java.util.List;

public interface AsignaturasService {
    List<Asignatura> getAsignaturas();
    Asignatura getAsignaturaById(String id);
    boolean addAsignatura(Asignatura asignatura);
    boolean deleteAsignatura(String id);
}
