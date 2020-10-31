package una.examengrupo3.model;



import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


public class TareaDTO {

    Long id;
    String descripcion;
    Date fechaInicio;
    Date fechaFinalizacion;
    Long importancia;
    Long urgencia;
    Long porcentajeAvance;
    ProyectoDTO proyecto ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        Date date = Date.from(fechaInicio.atStartOfDay(ZoneId.systemDefault()).toInstant());
        this.fechaInicio = date;
    }

    public Date getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(LocalDate fechaFinalizacion) {
       Date date = Date.from(fechaFinalizacion.atStartOfDay(ZoneId.systemDefault()).toInstant());
        this.fechaFinalizacion = date;
    }

    public Long getImportancia() {
        return importancia;
    }

    public void setImportancia(Long importancia) {
        this.importancia = importancia;
    }

    public Long getUrgencia() {
        return urgencia;
    }

    public void setUrgencia(Long urgencia) {
        this.urgencia = urgencia;
    }

    public Long getPorcentajeAvance() {
        return porcentajeAvance;
    }

    public void setPorcentajeAvance(Long porcentajeAvance) {
        this.porcentajeAvance = porcentajeAvance;
    }

    public ProyectoDTO getProyecto() {
        return proyecto;
    }

    public void setProyecto(ProyectoDTO proyecto) {
        this.proyecto = proyecto;
    }

    @Override
    public String toString() {
        return "TareaDTO{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaFinalizacion=" + fechaFinalizacion +
                ", importancia=" + importancia +
                ", urgencia=" + urgencia +
                ", porcentajeAvance=" + porcentajeAvance +
                ", proyecto=" + proyecto +
                '}';
    }
}
