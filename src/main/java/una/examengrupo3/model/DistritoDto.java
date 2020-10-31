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
 * @author roberth
 */
public class DistritoDto extends SuperUnidad {

    private CantonDto canton;
    private List<UnidadDto> unidades;

    @Override
    public Long getPoblacion() {
        if (unidades != null) {
            poblacion = unidades.stream().mapToLong(unidad -> unidad.getPoblacion()).sum();
        } else {
            unidades = new ArrayList();
            poblacion = 0L;
        }
        return this.poblacion;
    }

    @Override
    @SuppressWarnings("null")
    public Float getAreaMetrosCuadrados() {
        areaMetrosCuadrados = 0F;
        if (unidades != null) {
            unidades.forEach(unidad -> this.areaMetrosCuadrados += unidad.getAreaMetrosCuadrados());
        } else {
            unidades = new ArrayList();
        }
        return this.areaMetrosCuadrados;
    }

    public CantonDto getCanton() {
        return canton;
    }

    public void setCanton(CantonDto canton) {
        this.canton = canton;
    }

    public void setUnidades(List<UnidadDto> unidades) {
        this.unidades = unidades;
    }

    public List<UnidadDto> getUnidades() {
        return unidades;
    }

    @Override
    public List<SuperUnidad> getAuxSuperUnidadList() {
        if (unidades == null) {
            unidades = new ArrayList();
        }
        auxSuperUnidadList = new ArrayList();
        unidades.forEach(unid -> auxSuperUnidadList.add(unid));
        return auxSuperUnidadList;
    }

    @Override
    public void addNewChildren(SuperUnidad superU) {
        this.unidades.add((UnidadDto) superU);
    }

}
