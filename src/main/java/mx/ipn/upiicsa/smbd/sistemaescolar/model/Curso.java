package mx.ipn.upiicsa.smbd.sistemaescolar.model;

public class Curso {
    private Integer idCurso;
    private Asignatura asignatura;
    private Profesor profesor;

    public Integer getId() {
        return idCurso;
    }

    public void setId(Integer id) {
        this.idCurso = id;
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    @Override
    public String toString() {
        return "Curso{" +
                "id='" + idCurso + '\'' +
                ", asignatura='" + asignatura + '\'' +
                ", profesor='" + profesor + '\'' +
                '}';
    }
}
