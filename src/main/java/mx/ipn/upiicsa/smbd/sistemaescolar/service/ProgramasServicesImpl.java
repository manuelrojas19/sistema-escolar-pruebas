package mx.ipn.upiicsa.smbd.sistemaescolar.service;

import mx.ipn.upiicsa.smbd.sistemaescolar.dao.ProgramaDao;
import mx.ipn.upiicsa.smbd.sistemaescolar.model.Programa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProgramasServicesImpl implements ProgramasService {
    private final ProgramaDao programaDao;

    private List<Programa> programas;

    @Autowired
    public ProgramasServicesImpl(ProgramaDao programaDao) {
        this.programaDao = programaDao;
        programas = new ArrayList<>();
    }

    @Override
    public List<Programa> getProgramas() {
        programas = programaDao.getProgramas();
        return programas;
    }
}
