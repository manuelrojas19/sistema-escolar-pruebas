package mx.ipn.upiicsa.smbd.sistemaescolar.service;

import mx.ipn.upiicsa.smbd.sistemaescolar.model.Tarea;

import java.util.List;

public interface TareasServices {
    List<Tarea> getTareasByCurso(Integer idCurso);
    Tarea getTareaById(Integer idTarea);
    void addTareaByCourse(Integer idCurso, Tarea tarea);
    boolean deleteTarea(Integer idTarea);
}
