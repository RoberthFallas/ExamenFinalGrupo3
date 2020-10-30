/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package una.examengrupo3.services;

import una.examengrupo3.model.CantonDto;
import una.examengrupo3.model.DistritoDto;
import una.examengrupo3.util.Respuesta;
import una.examengrupo3.util.RestConector;

/**
 *
 * @author roberth
 */
public class DistritoService {

    private RestConector restC;

    public Respuesta create(DistritoDto distrito) {
        restC = new RestConector("/distritos/create");
        restC.post((DistritoDto) distrito);
        if (restC.isError()) {
            return new Respuesta(false, "Error al registrar nuevo distrito en el sistema.", "");
        }
        return new Respuesta(true, "", "", "data", restC.<DistritoDto>getResultAsObject(DistritoDto.class));
    }
}
