package una.examengrupo3.model;


import java.util.List;

public class ProyectoDTO {

    Long id;
    String nombre;
    String objetivo;
    Boolean estado;

    private List<TareaDTO> tareas ;

    public ProyectoDTO() {

    }

    public ProyectoDTO(String nombre, String objetivo) {
        this.estado = true;
        this.nombre = nombre;
        this.objetivo = objetivo;
        this.tareas = null;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public List<TareaDTO> getTareas() {
        return tareas;
    }

    public void updateTask(TareaDTO tareaDTO){
       tareas.forEach(t -> {
           if(t.getId() == tareaDTO.getId()) t = tareaDTO;
       });
    }



    @Override
    public String toString() {
        return  nombre + '\'' +
                " objetivo='" + objetivo + '\'';

    }
}
