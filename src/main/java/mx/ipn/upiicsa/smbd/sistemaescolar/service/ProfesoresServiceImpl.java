package mx.ipn.upiicsa.smbd.sistemaescolar.service;

import mx.ipn.upiicsa.smbd.sistemaescolar.dao.ProfesorDao;
import mx.ipn.upiicsa.smbd.sistemaescolar.model.Perfil;
import mx.ipn.upiicsa.smbd.sistemaescolar.model.Profesor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfesoresServiceImpl implements ProfesoresService {
    private final ProfesorDao profesorDao;
    private final PasswordEncoder passwordEncoder;

    private List<Profesor> profesores;

    @Autowired
    public ProfesoresServiceImpl(ProfesorDao profesorDao, PasswordEncoder passwordEncoder) {
        this.profesorDao = profesorDao;
        this.passwordEncoder = passwordEncoder;
        profesores = new ArrayList<>();
    }

    @Override
    public List<Profesor> getProfesores() {
        profesores = profesorDao.getProfesores();
        return profesores;
    }

    @Override
    public Profesor guardarProfesor(Profesor profesor) {
        Profesor res = null;
        boolean isValid = true;
        if (profesor.getIdProfesor() == null) {
            isValid = false;
        } else if (profesores.stream().anyMatch(tempProfesor -> tempProfesor.getIdProfesor().equals(profesor.getIdProfesor()))) {
            isValid = false;
        }
        if (profesor.getPrimerApellido().equals("") || profesor.getPrimerApellido() == null) {
            isValid = false;
        }
        if (profesor.getPrimerNombre().equals("") || profesor.getPrimerNombre() == null) {
            isValid = false;
        }
        if (isValid) {
            Integer user = profesor.getIdProfesor();
            String password = profesor.getPrimerApellido().substring(0, 2) +
                    profesor.getPrimerNombre().substring(0, 2) +
                    profesor.getIdProfesor().toString().substring(0, 4);
            System.out.println(password);
            String passwordEncrypt = passwordEncoder.encode(password);

            Perfil perfil = new Perfil();
            perfil.setUser(user.toString());
            perfil.setPassword(passwordEncrypt);
            perfil.setIdRol(2);
            perfil.setStatus(1);

            profesor.setPerfil(perfil);
            res = profesorDao.addProfesor(profesor);
        }
        return res;
    }

    @Override
    public Profesor getProfesorById(Integer idProfesor) {
        return profesorDao.getProfesorById(idProfesor);
    }

    @Override
    public void editarProfesor(Profesor profesor, Integer idProfesor) {
        profesorDao.editarProfesor(profesor, idProfesor);
    }

    @Override
    public void eliminarProfesor(Integer idProfesor) {
        profesorDao.eliminarProfesor(idProfesor);
    }
}
