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
public class CantonDto extends SuperUnidad {

    private ProvinciaDto provincia;
    private List<DistritoDto> distritos;

    @Override
    public Long getPoblacion() {
        if (this.poblacion == null) {
            return distritos.stream().mapToLong(distrito -> distrito.getPoblacion()).sum();
        }
        return this.poblacion;
    }

    @Override
    public Float getAreaMetrosCuadrados() {
        if (this.areaMetrosCuadrados == null) {
            distritos.forEach(distrito -> this.areaMetrosCuadrados += distrito.getAreaMetrosCuadrados());
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

}
