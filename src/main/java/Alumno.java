public class Alumno extends Usuario {
    private String cif;
    private String carrera;
    private String facultad;

    public Alumno(int id, String nombreUsuario, String contrasena, String apellido, String cif, String carrera, String facultad) {
        super(id, nombreUsuario, contrasena, apellido);
        this.cif = cif;
        this.carrera = carrera;
        this.facultad = facultad;
    }

    public String getCif() {
        return cif;
    }

    public String getCarrera() {
        return carrera;
    }

    public String getFacultad() {
        return facultad;
    }
}
