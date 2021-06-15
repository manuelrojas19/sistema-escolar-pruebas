package mx.ipn.upiicsa.smbd.sistemaescolar.service;

import mx.ipn.upiicsa.smbd.sistemaescolar.dao.AlumnoDao;
import mx.ipn.upiicsa.smbd.sistemaescolar.model.Alumno;
import mx.ipn.upiicsa.smbd.sistemaescolar.model.Perfil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlumnosServiceImpl implements AlumnosService {
    private final AlumnoDao alumnoDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AlumnosServiceImpl(AlumnoDao alumnoDao, PasswordEncoder passwordEncoder) {
        this.alumnoDao = alumnoDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Alumno> getAlumnos() {
        List<Alumno> alumnos;
        alumnos = alumnoDao.getAlumnos();
        return alumnos;
    }

    @Override
    public List<Alumno> getAlumnosByCourse(Integer idCourse) {
        return alumnoDao.getAlumnosByCourse(idCourse);
    }

    @Override
    public Alumno getAlumnoById(Integer boleta) {
        return alumnoDao.getAlumnoById(boleta);
    }

    @Override
    public boolean guardarAlumno(Alumno alumno) {

        List<Alumno> alumnos = alumnoDao.getAlumnos();

        boolean res = false;
        boolean isValid = true;
        if (alumno.getBoleta() == null) {
            isValid = false;
        } else if (alumnos.stream().anyMatch(tempAlumno -> tempAlumno.getBoleta().equals(alumno.getBoleta()))) {
            System.out.println("Boleta repetida");
            isValid = false;
        }
        if (alumno.getPrimerApellido().equals("") || alumno.getPrimerApellido() == null) {
            isValid = false;
        }
        if (alumno.getPrimerNombre().equals("") || alumno.getPrimerNombre() == null) {
            isValid = false;
        }
        if (isValid) {
            String user = alumno.getBoleta().toString();
            String password = alumno.getPrimerApellido().substring(0, 2) +
                    alumno.getPrimerNombre().substring(0, 2) +
                    alumno.getBoleta().toString().substring(0, 4);
            System.out.println(password);
            String passwordEncrypt = passwordEncoder.encode(password);

            Perfil perfil = new Perfil();
            perfil.setUser(user);
            perfil.setPassword(passwordEncrypt);
            perfil.setIdRol(3);
            perfil.setStatus(1);

            alumno.setPerfil(perfil);

            res = alumnoDao.addAlumno(alumno);
        }
        return res;
    }

    @Override
    public boolean editAlumno(Alumno alumno, Integer id) {
        return alumnoDao.editarAlumno(alumno, id);
    }

    @Override
    public void eliminarAlumno(Integer boleta) {
        alumnoDao.eliminarAlumno(boleta);
    }
}
