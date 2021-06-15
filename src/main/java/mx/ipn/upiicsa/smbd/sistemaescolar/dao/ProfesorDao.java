package mx.ipn.upiicsa.smbd.sistemaescolar.dao;

import mx.ipn.upiicsa.smbd.sistemaescolar.model.Profesor;

import java.util.List;

public interface ProfesorDao {
    List<Profesor> getProfesores();
    Profesor getProfesorById(Integer idProfesor);
    Profesor addProfesor(Profesor profesor);
    void editarProfesor(Profesor profesor, Integer id);
    void eliminarProfesor(Integer idProfesor);
}
