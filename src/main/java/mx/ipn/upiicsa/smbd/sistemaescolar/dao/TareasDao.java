package mx.ipn.upiicsa.smbd.sistemaescolar.dao;

import mx.ipn.upiicsa.smbd.sistemaescolar.model.Tarea;

import java.util.List;

public interface TareasDao {
    List<Tarea> getTareasByCurso(Integer idCurso);
    void addTareaByCourse(Tarea tarea, Integer idCurso);
    boolean deleteTareas(Integer idTarea);
    Tarea getTareasById(Integer idTarea);
}
