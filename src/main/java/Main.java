import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.List;



public class Main {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/postgres", "postgres", "admin")) {
            System.out.println("Conexión exitosa a la base de datos PostgreSQL.");

            Registro registro = new Registro(connection);
            InicioSesion inicioSesion = new InicioSesion(connection);
            AgendaCitas agendaCitas = new AgendaCitas(connection);
            Scanner scanner = new Scanner(System.in);

            int idAlumno = -1;
            int idProfesor = -1;

            while (true) {
                System.out.println("\n--- Menú Principal ---");
                System.out.println("1. Registrar Alumno");
                System.out.println("2. Registrar Profesor");
                System.out.println("3. Iniciar Sesión");
                System.out.println("4. Salir");
                System.out.println("Seleccione una opción:");

                int opcion = scanner.nextInt();

                switch (opcion) {
                    case 1:
                        registro.registrarAlumno();
                        break;
                    case 2:
                        registro.registrarProfesor();
                        break;
                    case 3:
                        System.out.println("Ingrese su nombre de usuario:");
                        String nombreUsuario = scanner.next();
                        System.out.println("Ingrese su contraseña:");
                        String contrasena = scanner.next();

                        Usuario usuario = inicioSesion.iniciarSesion(nombreUsuario, contrasena);

                        if (usuario != null) {
                            if (usuario instanceof Alumno) {
                                idAlumno = usuario.getId();
                                System.out.println("Inicio de sesión exitoso. ID del alumno: " + idAlumno);
                            } else if (usuario instanceof Profesor) {
                                idProfesor = usuario.getId();
                                System.out.println("Inicio de sesión exitoso. ID del profesor: " + idProfesor);
                            }
                        } else {
                            System.out.println("Inicio de sesión fallido. Credenciales incorrectas.");
                        }

                        if (idAlumno != -1 || idProfesor != -1) {
                            // Menú secundario después de iniciar sesión
                            while (true) {
                                System.out.println("\n--- Menú Secundario ---");
                                System.out.println("1. Agendar Cita");
                                System.out.println("2. Ver Listado de Citas");
                                System.out.println("3. Responder Cita (Solo para Profesores)");
                                System.out.println("4. Ver Listado de Profesores");
                                System.out.println("5. Cerrar Sesión");

                                System.out.println("Seleccione una opción:");

                                int opcionSecundaria = scanner.nextInt();

                                switch (opcionSecundaria) {
                                    case 1:
                                        if (idAlumno != -1) {
                                            agendaCitas.agendarCitaAlumnoProfesor(idAlumno);
                                        } else if (idProfesor != -1) {
                                            agendaCitas.agendarCitaAlumnoProfesor(idAlumno);
                                        }
                                        break;
                                    case 2:
                                        // Mostrar listado de citas asignadas al profesor
                                        if (idProfesor != -1) {
                                            List<Cita> citasAsignadas = agendaCitas.obtenerCitasAsignadasAProfesor(idProfesor);
                                            if (!citasAsignadas.isEmpty()) {
                                                System.out.println("Listado de Citas Asignadas:");
                                                for (Cita cita : citasAsignadas) {
                                                    System.out.println("ID Cita: " + cita.getIdCita() +
                                                            ", ID Alumno: " + cita.getIdAlumno() +
                                                            ", Fecha: " + cita.getFechaCita() +

                                                            ", Estado: " + cita.getEstado());
                                                }
                                            } else {
                                                System.out.println("No tiene citas asignadas.");
                                            }
                                        } else {
                                            System.out.println("No tiene permisos para ver el listado de citas.");
                                        }
                                        break;
                                    case 3:
                                        // Responder cita (solo para profesores)
                                        if (idProfesor != -1) {
                                            agendaCitas.responderCita(idProfesor);
                                        } else {
                                            System.out.println("No tiene permisos para responder citas.");
                                        }
                                        break;
                                    case 4:
                                        // Ver listado de profesores
                                        agendaCitas.mostrarListadoProfesores();
                                        break;
                                    case 5:
                                        // Cerrar sesión
                                        idAlumno = -1;
                                        idProfesor = -1;
                                        System.out.println("Sesión cerrada.");
                                        break;
                                    case 6:
                                        System.out.println("Volviendo al Menú Principal.");
                                        break;
                                    default:
                                        System.out.println("Opción no válida. Inténtelo de nuevo.");
                                }

                                if (opcionSecundaria == 5) {
                                    break; // Salir del menú secundario
                                }
                            }
                        }
                        break;
                    case 4:
                        System.out.println("Saliendo del programa.");
                        System.exit(0);
                    default:
                        System.out.println("Opción no válida. Inténtelo de nuevo.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void mostrarMenuPrincipal() {
        System.out.println("\n--- Menú Principal ---");
        System.out.println("1. Registrar Alumno");
        System.out.println("2. Registrar Profesor");
        System.out.println("3. Iniciar Sesión");
        System.out.println("4. Salir");
        System.out.println("Seleccione una opción:");
    }

    private static void mostrarMenuSecundario() {
        System.out.println("\n--- Menú Secundario ---");
        System.out.println("1. Agendar Cita");
        System.out.println("2. Ver Listado de Citas");
        System.out.println("3. Responder Cita (Solo para Profesores)");
        System.out.println("4. Cerrar Sesión");

        System.out.println("Seleccione una opción:");
    }
}
