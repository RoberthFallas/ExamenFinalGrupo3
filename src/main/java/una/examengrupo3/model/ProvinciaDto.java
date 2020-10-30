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
        if (this.poblacion == null) {
            poblacion = cantones.stream().mapToLong(canton -> canton.getPoblacion()).sum();
        }
        return this.poblacion;
    }

    @Override
    @SuppressWarnings("null")
    public Float getAreaMetrosCuadrados() {
        if (this.areaMetrosCuadrados == null) {
            areaMetrosCuadrados = areaMetrosCuadrados == null ? 0f : areaMetrosCuadrados;
            cantones.forEach(canton -> this.areaMetrosCuadrados += canton.getAreaMetrosCuadrados());
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
        if (this.auxSuperUnidadList == null) {
            auxSuperUnidadList = new ArrayList();
            cantones.forEach(cant -> auxSuperUnidadList.add(cant));
        }
        return auxSuperUnidadList;
    }

}
