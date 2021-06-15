package mx.ipn.upiicsa.smbd.sistemaescolar.dao;

import mx.ipn.upiicsa.smbd.sistemaescolar.model.Asignatura;

import java.util.List;

public interface AsignaturaDao {
    List<Asignatura> getAsignaturas();
    Asignatura getAsignaturaById(String idAsignatura);
    boolean addAsignatura(Asignatura asignatura);
    boolean deleteAsignatura(String idAsignatura);
}
