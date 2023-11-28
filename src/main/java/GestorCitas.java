import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
    public class GestorCitas {



    private List<Cita> listaCitas;

    public  GestorCitas() {
        this.listaCitas = new ArrayList<>();
    }

        public void agregarCita(Cita cita) {
            String insertCitaSQL = "INSERT INTO citas (id_alumno, id_profesor, fecha_cita, motivo, ubicacion, estado) VALUES (?, ?, ?, ?, ?, ?)";

            try (Connection connection = obtenerConexion();  // Asegúrate de tener un método para obtener la conexión
                 PreparedStatement preparedStatement = connection.prepareStatement(insertCitaSQL)) {

                preparedStatement.setInt(1, cita.getIdAlumno());
                preparedStatement.setInt(2, cita.getIdProfesor());
                preparedStatement.setTimestamp(3, java.sql.Timestamp.valueOf(cita.getFechaCita()));
                preparedStatement.setString(4, cita.getMotivo());
                preparedStatement.setString(5, cita.getUbicacion());
                preparedStatement.setString(6, cita.getEstado());

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // ... (otros métodos)

        // Método ficticio para obtener la conexión a la base de datos
        private Connection obtenerConexion() throws SQLException {
            // Implementa la lógica para obtener la conexión a la base de datos
            return DriverManager.getConnection("jdbc:postgresql://localhost:5433/postgres", "postgres", "admin");
        }

        //public void agregarCita(Cita cita) {
        //listaCitas.add(cita);
    }

    // Puedes agregar más métodos según sea necesario


