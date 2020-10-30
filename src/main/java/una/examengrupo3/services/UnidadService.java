/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package una.examengrupo3.services;

import una.examengrupo3.model.UnidadDto;
import una.examengrupo3.util.Respuesta;
import una.examengrupo3.util.RestConector;

/**
 *
 * @author roberth
 */
public class UnidadService {

    private RestConector rc;

    public Respuesta create(UnidadDto unidad) {
        rc = new RestConector("/unidades/create");
        rc.changeDateFormatSerialization("yyy-MM-dd'T'HH:mm:ss.SSSX");
        rc.post((UnidadDto) unidad);
        if (rc.isError()) {
            return new Respuesta(false, "Error al registrar nueva unidad en el sistema.", "");
        }
        return new Respuesta(true, "", "", "data", rc.<UnidadDto>getResultAsObject(UnidadDto.class));
    }
}
