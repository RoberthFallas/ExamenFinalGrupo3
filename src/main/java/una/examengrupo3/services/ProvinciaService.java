/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package una.examengrupo3.services;

import java.util.List;
import una.examengrupo3.model.ProvinciaDto;
import una.examengrupo3.util.Respuesta;
import una.examengrupo3.util.RestConector;

/**
 *
 * @author roberth
 */
public class ProvinciaService {

    private RestConector restC;

    public Respuesta findAll() {
        try {
            restC = new RestConector();
            restC.get("/provincias/findAll");
            if (!restC.isError()) {
                if (!restC.isEmptyResult()) {
                    List<ProvinciaDto> result = restC.<ProvinciaDto>getResultAsList(ProvinciaDto.class);
                    return new Respuesta(true, "", "", "data", result);
                }
                return new Respuesta(false, "No hay provincias disposibles para mostrar", "");
            }
            return new Respuesta(false, "Un error en el servidor ha impedido obtener el listado de provincias", "");
        } catch (Exception ex) {
            return new Respuesta(false, "No ha sido posible establecer contacto con el servidor", ex.getMessage());
        }
    }

    public Respuesta create(ProvinciaDto provincia) {
        restC = new RestConector("/provincias/create");
        restC.changeDateFormatSerialization("yyy-MM-dd'T'HH:mm:ss.SSSX");
        restC.post((ProvinciaDto) provincia);
        if (restC.isError()) {
            return new Respuesta(false, "Error al registrar nueva provincia en el sistema.", "");
        }
        return new Respuesta(true, "", "", "data", restC.<ProvinciaDto>getResultAsObject(ProvinciaDto.class));
    }
}
