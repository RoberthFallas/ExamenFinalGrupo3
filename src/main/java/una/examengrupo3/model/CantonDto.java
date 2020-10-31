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
public class CantonDto extends SuperUnidad {

    private ProvinciaDto provincia;
    private List<DistritoDto> distritos;

    @Override
    public Long getPoblacion() {
        if (distritos != null) {
            poblacion = distritos.stream().mapToLong(distrito -> distrito.getPoblacion()).sum();
        } else {
            distritos = new ArrayList();
            poblacion = 0L;
        }
        return this.poblacion;
    }

    @Override
    @SuppressWarnings("null")
    public Float getAreaMetrosCuadrados() {
        areaMetrosCuadrados = 0F;
        if (distritos != null) {
            distritos.forEach(distrito -> this.areaMetrosCuadrados += distrito.getAreaMetrosCuadrados());
        } else {
            distritos = new ArrayList();
        }
        return this.areaMetrosCuadrados;
    }

    public ProvinciaDto getProvincia() {
        return provincia;
    }

    public void setProvincia(ProvinciaDto provincia) {
        this.provincia = provincia;
    }

    public List<DistritoDto> getDistritos() {
        return distritos;
    }

    public void setDistritos(List<DistritoDto> distritos) {
        this.distritos = distritos;
    }

    @Override
    public List<SuperUnidad> getAuxSuperUnidadList() {
        if (distritos == null) {
            distritos = new ArrayList();
        }
        auxSuperUnidadList = new ArrayList();
        distritos.forEach(distr -> auxSuperUnidadList.add(distr));
        return auxSuperUnidadList;
    }

    @Override
    public void addNewChildren(SuperUnidad superU) {
        this.distritos.add((DistritoDto) superU);
    }

}
