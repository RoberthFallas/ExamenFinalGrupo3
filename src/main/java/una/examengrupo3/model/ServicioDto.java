/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package una.examengrupo3.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LordLalo
 */
public class ServicioDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private List<MembresiaDTO> membresia = new ArrayList<>();

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<MembresiaDTO> getMembresia() {
        return membresia;
    }

    public void setMembresia(List<MembresiaDTO> membresia) {
        this.membresia = membresia;
       
    }
    
}
