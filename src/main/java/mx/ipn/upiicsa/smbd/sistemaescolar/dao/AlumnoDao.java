package mx.ipn.upiicsa.smbd.sistemaescolar.dao;

import mx.ipn.upiicsa.smbd.sistemaescolar.model.Alumno;

import java.util.List;

public interface AlumnoDao {
    List<Alumno> getAlumnos();
    List<Alumno> getAlumnosByCourse(Integer idCourse);
    Alumno getAlumnoById(Integer id);
    boolean addAlumno(Alumno alumno);
    boolean editarAlumno(Alumno alumno, Integer id);
    void eliminarAlumno(Integer boleta);
}
