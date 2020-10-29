/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package una.examengrupo3.model;

import java.util.List;

/**
 *
 * @author roberth
 */
public class TipoUnidadDto {

    private Long id;
    private String nombre;
    private List<UnidadDto> unidades;

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

    public List<UnidadDto> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<UnidadDto> unidades) {
        this.unidades = unidades;
    }

}
