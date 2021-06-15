package mx.ipn.upiicsa.smbd.sistemaescolar.service;

import mx.ipn.upiicsa.smbd.sistemaescolar.model.Alumno;

import java.util.List;

public interface AlumnosService {
    List<Alumno> getAlumnos();
    List<Alumno> getAlumnosByCourse(Integer idCourse);
    Alumno getAlumnoById(Integer boleta);
    boolean guardarAlumno(Alumno alumno);
    boolean editAlumno(Alumno alumno, Integer id);
    void eliminarAlumno(Integer boleta);
}
