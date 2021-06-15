package mx.ipn.upiicsa.smbd.sistemaescolar.service;

import mx.ipn.upiicsa.smbd.sistemaescolar.dao.AcademiaDao;
import mx.ipn.upiicsa.smbd.sistemaescolar.model.Academia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AcademiasServiceImpl implements AcademiasService {
    private final AcademiaDao academiaDao ;

    private List<Academia> academias;

    @Autowired
    public AcademiasServiceImpl(AcademiaDao academiaDao) {
        this.academiaDao = academiaDao;
        academias = new ArrayList<>();
    }

    @Override
    public List<Academia> getAcademias() {
        academias = academiaDao.getAcademias();
        return academias;
    }
}
