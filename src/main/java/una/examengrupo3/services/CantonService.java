/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package una.examengrupo3.services;

import una.examengrupo3.model.CantonDto;
import una.examengrupo3.util.Respuesta;
import una.examengrupo3.util.RestConector;

/**
 *
 * @author roberth
 */
public class CantonService {

    private RestConector restC;

    public Respuesta create(CantonDto canton) {
        restC = new RestConector("/cantones/create");
        restC.changeDateFormatSerialization("yyy-MM-dd'T'HH:mm:ss.SSSX");
        restC.post((CantonDto) canton);
        if (restC.isError()) {
            return new Respuesta(false, "Error al registrar nuevo cantón en el sistema.", "");
        }
        return new Respuesta(true, "", "", "data", restC.<CantonDto>getResultAsObject(CantonDto.class));
    }
}
