package mx.ipn.upiicsa.smbd.sistemaescolar.service;

import mx.ipn.upiicsa.smbd.sistemaescolar.model.Profesor;

import java.util.List;

public interface ProfesoresService {
    List<Profesor> getProfesores();
    Profesor guardarProfesor(Profesor profesor);
    Profesor getProfesorById(Integer idProfesor);
    void editarProfesor(Profesor profesor, Integer idProfesor);
    void eliminarProfesor(Integer idProfesor);
}
