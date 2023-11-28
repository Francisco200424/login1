import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AgendaCitas {
    private Connection connection;
    private GestorCitas gestorCitas;


    public AgendaCitas(Connection connection) {
        this.connection = connection;
        this.gestorCitas = new GestorCitas();

        // Crear la tabla de citas si no existe
        crearTablaCitas();
    }

    private void crearTablaCitas() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS citas (" +
                "id_cita SERIAL PRIMARY KEY," +
                "id_alumno INT," +
                "id_profesor INT," +
                "fecha_cita TIMESTAMP," +
                "motivo VARCHAR(255)," +
                "ubicacion VARCHAR(255)," +
                "estado VARCHAR(20)" +
                ")";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableSQL);
            System.out.println("Tabla 'citas' creada exitosamente (o ya existía).");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al crear la tabla 'citas'.");
        }
    }

    public void agendarCitaAlumnoProfesor(int idAlumno) {
        Scanner scanner = new Scanner(System.in);

        // Pide el idProfesor por teclado
        System.out.println("Ingrese el ID del profesor:");
        int idProfesor = scanner.nextInt();

        System.out.println("Ingrese la fecha y hora de la cita (YYYY-MM-DD HH:mm):");
        String fechaCitaStr = scanner.nextLine();  // Limpiar el buffer
        fechaCitaStr = scanner.nextLine();  // Leer la fecha
        LocalDateTime fechaCita = LocalDateTime.parse(fechaCitaStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        System.out.println("Ingrese el motivo de la cita:");
        String motivo = scanner.nextLine();

        System.out.println("Ingrese la ubicación de la cita:");
        String ubicacion = scanner.nextLine();

        // Crear la cita
        Cita nuevaCita = new Cita(0, idAlumno, idProfesor, fechaCita, motivo, ubicacion);

        // Agregar la cita a la base de datos
        gestorCitas.agregarCita(nuevaCita);

        System.out.println("Cita agendada correctamente.");
    }



    public void mostrarListadoProfesores() {
        String query = "SELECT * FROM profesores";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            System.out.println("\n--- Listado de Profesores ---");
            while (resultSet.next()) {
                int idProfesor = resultSet.getInt("id_profesor");
                String nombreProfesor = resultSet.getString("nombre_usuario");
                String apellidoProfesor = resultSet.getString("apellido");
                String facultadProfesor = resultSet.getString("facultad");

                System.out.println("ID: " + idProfesor + ", Nombre: " + nombreProfesor +
                        ", Apellido: " + apellidoProfesor + ", Facultad: " + facultadProfesor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Cita> obtenerCitasAsignadasAProfesor(int idProfesor) {
        List<Cita> citasAsignadas = new ArrayList<>();
        String obtenerCitasAsignadasSQL = "SELECT * FROM citas WHERE id_profesor = ? AND estado = 'pendiente'";

        try (PreparedStatement preparedStatement = connection.prepareStatement(obtenerCitasAsignadasSQL)) {
            preparedStatement.setInt(1, idProfesor);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int idCita = resultSet.getInt("id_cita");
                    int idAlumno = resultSet.getInt("id_alumno");
                    LocalDateTime fechaCita = resultSet.getTimestamp("fecha_cita").toLocalDateTime();
                    String motivo = resultSet.getString("motivo");
                    String ubicacion = resultSet.getString("ubicacion");

                    // Crear una nueva cita con los valores obtenidos de la base de datos
                    Cita cita = new Cita(idCita, idAlumno, idProfesor, fechaCita, motivo, ubicacion);
                    cita.setEstado("pendiente");  // Ajusta el estado según lo que necesites
                    citasAsignadas.add(cita);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return citasAsignadas;
    }








    public void responderCita(int idProfesor) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el ID de la cita que desea responder:");
        int idCita = scanner.nextInt();

        // Verificar si el profesor tiene permisos para responder la cita
        if (verificarPermisoResponderCita(idProfesor, idCita)) {
            // Aquí puedes implementar la lógica para responder la cita
            System.out.println("Ingrese su respuesta para la cita ID " + idCita + ":");
            String respuesta = scanner.next();

            // Actualizar el estado de la cita a "respondida" en la base de datos
            actualizarEstadoCita(idCita, "respondida");

            System.out.println("Respuesta enviada correctamente.");
        } else {
            System.out.println("No tiene permisos para responder citas.");
        }
    }

    private boolean verificarPermisoResponderCita(int idProfesor, int idCita) {
        // Implementa la lógica para verificar si el profesor tiene permisos para responder esta cita
        // Puedes verificar si la cita pertenece al profesor, si está en estado pendiente, etc.

        // Obtener la cita de la base de datos para verificar si está dirigida al profesor específico
        Cita cita = obtenerCitaPorId(idCita);

        return cita != null && cita.getIdProfesor() == idProfesor && "pendiente".equals(cita.getEstado());
    }

    private Cita obtenerCitaPorId(int idCita) {
        // Implementa la lógica para obtener la cita de la base de datos por su ID
        // Retorna null si no se encuentra la cita
        // Puedes usar una consulta SQL similar a "SELECT * FROM citas WHERE id_cita = ?"
        return null;
    }



    private void actualizarEstadoCita(int idCita, String nuevoEstado) {
        // Implementa la lógica para actualizar el estado de la cita en la base de datos
        String actualizarEstadoSQL = "UPDATE citas SET estado = ? WHERE id_cita = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(actualizarEstadoSQL)) {
            preparedStatement.setString(1, nuevoEstado);
            preparedStatement.setInt(2, idCita);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ... (otros métodos)
}
