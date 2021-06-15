package mx.ipn.upiicsa.smbd.sistemaescolar.dao;

import mx.ipn.upiicsa.smbd.sistemaescolar.model.Academia;

import java.util.List;

public interface AcademiaDao {
    List<Academia> getAcademias();
    Academia getAcademiaById(String id);
}
