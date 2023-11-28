public class Profesor extends Usuario {
    private String cifDocente;
    private String facultad;

    public Profesor(int id, String nombreUsuario, String contrasena, String apellido, String cifDocente, String facultad) {
        super(id, nombreUsuario, contrasena, apellido);
        this.cifDocente = cifDocente;
        this.facultad = facultad;
    }

    public String getCifDocente() {
        return cifDocente;
    }

    public String getFacultad() {
        return facultad;
    }
}
