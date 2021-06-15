package mx.ipn.upiicsa.smbd.sistemaescolar.model;

public class Perfil {
    private String user;
    private String password;
    private Integer idRol;
    private Integer status;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Perfil{" +
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", idRol=" + idRol +
                ", status=" + status +
                '}';
    }
}
