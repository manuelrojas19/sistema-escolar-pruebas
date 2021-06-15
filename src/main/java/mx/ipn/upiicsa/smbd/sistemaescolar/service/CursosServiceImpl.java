package mx.ipn.upiicsa.smbd.sistemaescolar.service;

import mx.ipn.upiicsa.smbd.sistemaescolar.dao.CursoDao;
import mx.ipn.upiicsa.smbd.sistemaescolar.model.Alumno;
import mx.ipn.upiicsa.smbd.sistemaescolar.model.Curso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CursosServiceImpl implements CursosService {
    private final CursoDao cursoDao;

    private List<Curso> cursos;

    @Autowired
    public CursosServiceImpl(CursoDao cursoDao) {
        this.cursoDao = cursoDao;
        cursos = new ArrayList<>();
    }

    @Override
    public List<Curso> getCursos() {
        cursos = cursoDao.getCursos();
        return cursos;
    }

    @Override
    public List<Curso> getCursosByAlumno(Integer boleta) {
        return cursoDao.getCursosByAlumno(boleta);
    }

    @Override
    public List<Curso> getCursosByProfesor(Integer idProfesor) {
        return cursoDao.getCursosByProfesor(idProfesor);
    }

    @Override
    public List<Curso> getEnrollableCoursesByAlumno(Integer boleta) {
        return cursoDao.getEnrollableCoursesByAlumno(boleta);
    }

    @Override
    public Curso getCursoById(Integer id) {
        return cursoDao.getCursoById(id);
    }

    @Override
    public boolean guardarCurso(Curso curso) {
        return cursoDao.addCurso(curso);
    }

    @Override
    public boolean deleteCurso(Integer idCurso) {
        return cursoDao.deleteCurso(idCurso);
    }

    @Override
    public boolean enrollAlumnoInCurso(Curso curso, Alumno alumno) {
        boolean res = false;
        boolean isValid = true;
        if (alumno.getCreditos() - curso.getAsignatura().getCreditos() < 0) {
            isValid = false;
        }
        if (isValid) {
            res = cursoDao.enrollAlumnoInCurso(curso, alumno.getBoleta());
        }
        return res;
    }
}
