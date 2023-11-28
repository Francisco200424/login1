public class Usuario {
    private int id;
    private String nombreUsuario;
    private String contrasena; // Corregido el nombre del campo
    private String apellido;

    public Usuario(int id, String nombreUsuario, String contrasena, String apellido) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.apellido = apellido;
    }

    public int getId() {
        return id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContrasena() { // Corregido el nombre del método
        return contrasena;
    }

    public String getApellido() {
        return apellido;
    }

    // Métodos adicionales según sea necesario...

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", apellido='" + apellido + '\'' +
                '}';
    }
}
