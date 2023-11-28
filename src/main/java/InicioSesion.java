import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class InicioSesion {
    private Connection connection;

    public InicioSesion(Connection connection) {
        this.connection = connection;
    }

    public Usuario iniciarSesion(String nombreUsuario, String contrasena) {
        Scanner scanner = new Scanner(System.in);

        // Consulta SQL para verificar las credenciales y obtener el ID del alumno
        String verificarCredencialesAlumnoSQL = "SELECT id, nombre_usuario, apellido FROM alumnos WHERE nombre_usuario = ? AND contrasena = ?";

        try (PreparedStatement preparedStatementAlumno = connection.prepareStatement(verificarCredencialesAlumnoSQL)) {
            preparedStatementAlumno.setString(1, nombreUsuario);
            preparedStatementAlumno.setString(2, contrasena);

            try (ResultSet resultSetAlumno = preparedStatementAlumno.executeQuery()) {
                if (resultSetAlumno.next()) {
                    int id = resultSetAlumno.getInt("id");
                    String nombre = resultSetAlumno.getString("nombre_usuario");
                    String apellido = resultSetAlumno.getString("apellido");

                    // Obtener datos específicos de alumno
                    String obtenerDatosAlumnoSQL = "SELECT cif, carrera, facultad FROM alumnos WHERE id = ?";
                    try (PreparedStatement obtenerDatosAlumnoStmt = connection.prepareStatement(obtenerDatosAlumnoSQL)) {
                        obtenerDatosAlumnoStmt.setInt(1, id);
                        try (ResultSet datosAlumno = obtenerDatosAlumnoStmt.executeQuery()) {
                            if (datosAlumno.next()) {
                                String cif = datosAlumno.getString("cif");
                                String carrera = datosAlumno.getString("carrera");
                                String facultad = datosAlumno.getString("facultad");
                                return new Alumno(id, nombre, contrasena, apellido, cif, carrera, facultad);
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Si no se encontró en la tabla de alumnos, intentar en la tabla de profesores
        String verificarCredencialesProfesorSQL = "SELECT id_profesor, nombre_usuario, apellido, cif_docente, facultad FROM profesores WHERE nombre_usuario = ? AND contrasena = ?";

        try (PreparedStatement preparedStatementProfesor = connection.prepareStatement(verificarCredencialesProfesorSQL)) {
            preparedStatementProfesor.setString(1, nombreUsuario);
            preparedStatementProfesor.setString(2, contrasena);

            try (ResultSet resultSetProfesor = preparedStatementProfesor.executeQuery()) {
                if (resultSetProfesor.next()) {
                    int id = resultSetProfesor.getInt("id_profesor");
                    String nombre = resultSetProfesor.getString("nombre_usuario");
                    String apellido = resultSetProfesor.getString("apellido");
                    String cifDocente = resultSetProfesor.getString("cif_docente");
                    String facultad = resultSetProfesor.getString("facultad");
                    return new Profesor(id, nombre, contrasena, apellido, cifDocente, facultad);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Inicio de sesión fallido. Credenciales incorrectas.");
        return null;
    }
}
