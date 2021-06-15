package mx.ipn.upiicsa.smbd.sistemaescolar.service;

import mx.ipn.upiicsa.smbd.sistemaescolar.model.Alumno;
import mx.ipn.upiicsa.smbd.sistemaescolar.model.Curso;

import java.util.List;

public interface CursosService {
    List<Curso> getCursos();
    List<Curso> getCursosByAlumno(Integer boleta);
    List<Curso> getCursosByProfesor(Integer idProfesor);
    List<Curso> getEnrollableCoursesByAlumno(Integer boleta);
    Curso getCursoById(Integer id);
    boolean guardarCurso(Curso curso);
    boolean deleteCurso(Integer idCurso);
    boolean enrollAlumnoInCurso(Curso curso, Alumno alumno);
}
