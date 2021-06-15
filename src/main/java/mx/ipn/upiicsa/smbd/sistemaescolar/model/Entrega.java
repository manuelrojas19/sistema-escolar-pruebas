package mx.ipn.upiicsa.smbd.sistemaescolar.model;

public class Entrega {
    private Tarea tarea;
    private Alumno alumno;
    private String archivo;
    private Curso curso;
    private boolean status;

    public Tarea getTarea() {
        return tarea;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    @Override
    public String toString() {
        return "Entrega{" +
                "tarea=" + tarea +
                ", alumno=" + alumno +
                ", archivo='" + archivo + '\'' +
                ", curso=" + curso +
                ", status=" + status +
                '}';
    }

}
