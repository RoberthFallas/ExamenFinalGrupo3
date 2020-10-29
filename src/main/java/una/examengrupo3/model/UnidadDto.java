/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package una.examengrupo3.model;

import java.util.Date;

/**
 *
 * @author roberth
 */
public class UnidadDto extends SuperUnidad {

    private TipoUnidadDto tipoUnidad;
    private DistritoDto distrito;
    private Date fecha_actualizacion;

    @Override
    public Long getPoblacion() {
        return this.poblacion;
    }

    @Override
    public Float getAreaMetrosCuadrados() {
        return this.areaMetrosCuadrados;
    }

    public TipoUnidadDto getTipoUnidad() {
        return tipoUnidad;
    }

    public void setTipoUnidad(TipoUnidadDto tipoUnidad) {
        this.tipoUnidad = tipoUnidad;
    }

    public DistritoDto getDistrito() {
        return distrito;
    }

    public void setDistrito(DistritoDto distrito) {
        this.distrito = distrito;
    }

    public Date getFecha_actualizacion() {
        return fecha_actualizacion;
    }

    public void setFecha_actualizacion(Date fecha_actualizacion) {
        this.fecha_actualizacion = fecha_actualizacion;
    }

}
