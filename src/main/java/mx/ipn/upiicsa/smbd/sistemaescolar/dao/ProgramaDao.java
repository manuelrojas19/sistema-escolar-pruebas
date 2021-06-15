package mx.ipn.upiicsa.smbd.sistemaescolar.dao;

import mx.ipn.upiicsa.smbd.sistemaescolar.model.Programa;

import java.util.List;

public interface ProgramaDao {
    List<Programa> getProgramas();
    Programa getProgramaById(String idPrograma);
}
