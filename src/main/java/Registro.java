import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Registro {
    private Connection connection;

    public Registro(Connection connection) {
        this.connection = connection;
    }

    public void registrarAlumno() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese nombre de usuario del alumno:");
        String nombreUsuario = scanner.next();

        System.out.println("Ingrese contraseña del alumno:");
        String contraseña = scanner.next();

        System.out.println("Ingrese apellido del alumno:");
        String apellido = scanner.next();

        System.out.println("Ingrese CIF del alumno:");
        String cif = scanner.next();

        System.out.println("Ingrese carrera del alumno:");
        String carrera = scanner.next();

        System.out.println("Ingrese facultad del alumno:");
        String facultad = scanner.next();

        // Código para registrar en la base de datos (tabla de alumnos)
        String insertSQL = "INSERT INTO alumnos (nombre_usuario, contrasena, apellido, cif, carrera, facultad) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, nombreUsuario);
            preparedStatement.setString(2, contraseña);
            preparedStatement.setString(3, apellido);
            preparedStatement.setString(4, cif);
            preparedStatement.setString(5, carrera);
            preparedStatement.setString(6, facultad);
            preparedStatement.executeUpdate();
            System.out.println("Alumno registrado con éxito.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void registrarProfesor() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese nombre de usuario del profesor:");
        String nombreUsuario = scanner.next();

        System.out.println("Ingrese contraseña del profesor:");
        String contraseña = scanner.next();

        System.out.println("Ingrese apellido del profesor:");
        String apellido = scanner.next();

        System.out.println("Ingrese CIF del profesor:");
        String cifDocente = scanner.next();

        System.out.println("Ingrese facultad del profesor:");
        String facultad = scanner.next();

        // Código para registrar en la base de datos (tabla de profesores)
        String insertSQL = "INSERT INTO profesores (nombre_usuario, contrasena, apellido, cif_docente,facultad) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, nombreUsuario);
            preparedStatement.setString(2, contraseña);
            preparedStatement.setString(3, apellido);
            preparedStatement.setString(4, cifDocente);
            preparedStatement.setString(5, facultad);
            preparedStatement.executeUpdate();
            System.out.println("Profesor registrado con éxito.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Resto de la clase...
}
