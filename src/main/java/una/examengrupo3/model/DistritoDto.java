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
public class DistritoDto extends SuperUnidad {

    private CantonDto canton;
    private List<DistritoDto> unidades;

    @Override
    public Long getPoblacion() {
        if (this.poblacion == null) {
            poblacion = unidades.stream().mapToLong(unidad -> unidad.getPoblacion()).sum();
        }
        return this.poblacion;
    }

    @Override
    public Float getAreaMetrosCuadrados() {
        if (this.areaMetrosCuadrados == null) {
            unidades.forEach(unidad -> this.areaMetrosCuadrados += unidad.getAreaMetrosCuadrados());
        }
        return this.areaMetrosCuadrados;
    }

    public CantonDto getCanton() {
        return canton;
    }

    public void setCanton(CantonDto canton) {
        this.canton = canton;
    }

    public void setUnidades(List<DistritoDto> unidades) {
        this.unidades = unidades;
    }

    public List<DistritoDto> getUnidades() {
        return unidades;
    }

}
