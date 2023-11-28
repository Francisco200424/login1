import java.time.LocalDateTime;

public class Cita {
    private int idCita;
    private int idAlumno;
    private int idProfesor;
    private LocalDateTime fechaCita;
    private String motivo;
    private String ubicacion;
    private String estado;

    public Cita(int idCita, int idAlumno, int idProfesor, LocalDateTime fechaCita, String motivo, String ubicacion) {
        this.idCita = idCita;
        this.idAlumno = idAlumno;
        this.idProfesor = idProfesor;
        this.fechaCita = fechaCita;
        this.motivo = motivo;
        this.ubicacion = ubicacion;
        this.estado = "pendiente";  // Estado inicial
    }

    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }

    public LocalDateTime getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(LocalDateTime fechaCita) {
        this.fechaCita = fechaCita;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
