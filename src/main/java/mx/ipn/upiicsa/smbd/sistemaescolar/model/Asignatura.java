package mx.ipn.upiicsa.smbd.sistemaescolar.model;

public class Asignatura {
    private String idAsignatura;
    private String nombre;
    private Academia academia;
    private Integer creditos;

    public String getIdAsignatura() {
        return idAsignatura;
    }

    public void setIdAsignatura(String idAsignatura) {
        this.idAsignatura = idAsignatura;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Academia getAcademia() {
        return academia;
    }

    public void setAcademia(Academia academia) {
        this.academia = academia;
    }

    public Integer getCreditos() {
        return creditos;
    }

    public void setCreditos(Integer creditos) {
        this.creditos = creditos;
    }

    @Override
    public String toString() {
        return "Asignatura{" +
                "id='" + idAsignatura + '\'' +
                ", nombre='" + nombre + '\'' +
                ", academia=" + academia +
                ", creditos=" + creditos +
                '}';
    }
}
