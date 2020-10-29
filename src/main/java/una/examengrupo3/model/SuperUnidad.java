/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package una.examengrupo3.model;

/**
 *
 * @author roberth
 */
public abstract class SuperUnidad {

    protected Long id;
    protected Integer codigo;
    protected String nombre;
    protected Float areaMetrosCuadrados;
    protected Long poblacion;

    public abstract Long getPoblacion();

    public abstract Float getAreaMetrosCuadrados();

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

}
