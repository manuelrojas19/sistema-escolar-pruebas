package mx.ipn.upiicsa.smbd.sistemaescolar.dao;

import mx.ipn.upiicsa.smbd.sistemaescolar.model.Curso;

import java.util.List;

public interface CursoDao {
    List<Curso> getCursos();

    List<Curso> getCursosByAlumno(Integer boleta);

    List<Curso> getCursosByProfesor(Integer idProfesor);

    List<Curso> getEnrollableCoursesByAlumno(Integer boleta);

    Curso getCursoById(Integer id);

    boolean addCurso(Curso curso);

    boolean deleteCurso(Integer id);

    boolean enrollAlumnoInCurso(Curso curso, Integer boletaAlumno);
}
