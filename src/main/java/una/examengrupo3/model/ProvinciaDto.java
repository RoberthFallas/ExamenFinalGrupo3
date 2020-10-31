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
public class ProvinciaDto extends SuperUnidad {

    private List<CantonDto> cantones;

    @Override
    public Long getPoblacion() {
        if (cantones != null) {
            poblacion = cantones.stream().mapToLong(canton -> canton.getPoblacion()).sum();
        } else {
            cantones = new ArrayList();
            poblacion = 0L;
        }
        return this.poblacion;
    }

    @Override
    @SuppressWarnings("null")
    public Float getAreaMetrosCuadrados() {
        areaMetrosCuadrados = 0F;
        if (cantones != null) {
            cantones.forEach(unidad -> this.areaMetrosCuadrados += unidad.getAreaMetrosCuadrados());
        } else {
            cantones = new ArrayList();  
        }
        return this.areaMetrosCuadrados;
    }

    public List<CantonDto> getCantones() {
        return cantones;
    }

    public void setCantones(List<CantonDto> cantones) {
        this.cantones = cantones;
    }

    @Override
    public List<SuperUnidad> getAuxSuperUnidadList() {
        if (cantones == null) {
            cantones = new ArrayList();
        }
        auxSuperUnidadList = new ArrayList();
        cantones.forEach(cant -> auxSuperUnidadList.add(cant));
        return auxSuperUnidadList;
    }

    @Override
    public void addNewChildren(SuperUnidad superU) {
        this.cantones.add((CantonDto) superU);
    }

}
