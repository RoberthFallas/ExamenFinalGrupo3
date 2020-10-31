/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package una.examengrupo3.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import una.examengrupo3.model.CobroPendienteDto;
import una.examengrupo3.util.RequestHTTP;
import una.examengrupo3.util.Respuesta;

/**
 *
 * @author LordLalo
 */
public class CobroPendienteService {

    Gson gson = new GsonBuilder()
            .setDateFormat("yyy-MM-dd").create();

    public Respuesta create(CobroPendienteDto cobroPendienteDTO) {
        try {
            RequestHTTP requestHTTP = new RequestHTTP();
            HttpResponse respuesta = requestHTTP.post("http://localhost:1250/cobroPendiente/crear", gson.toJson(cobroPendienteDTO));
            System.out.println(respuesta.body().toString());
            if (requestHTTP.getStatus() != 200) {
                if (respuesta.statusCode() == 204) {
                    return new Respuesta(false, "Parece que no hay resultados en la búsqueda", String.valueOf(requestHTTP.getStatus()));
                }
                return new Respuesta(false, "Parece que algo ha salido mal. Si el problema persiste solicita ayuda del encargado del sistema.", String.valueOf(requestHTTP.getStatus()));
            }

            //List<AuthenticationResponse> users = new Gson().fromJson(respuesta.body().toString(), new TypeToken<>() {}.getType());
            CobroPendienteDto servicioMantenimientoDto = gson.fromJson(respuesta.body().toString(), CobroPendienteDto.class);
//
            return new Respuesta(true, "", "", "data", servicioMantenimientoDto);
        } catch (Exception ex) {
            Logger.getLogger(CobroPendienteService.class.getName()).log(Level.SEVERE, " logIn() ->", ex);
            return new Respuesta(false, "Ha ocurrido un error al establecer comunicación con el servidor.", ex.getMessage());
        }
    }
    public Respuesta generarCobros() {
        try {
            RequestHTTP requestHTTP = new RequestHTTP();
            HttpResponse respuesta = requestHTTP.get("http://localhost:1250/cobroPendiente/generarCobro/true");
            if (requestHTTP.getStatus() != 200) {
                if (respuesta.statusCode() == 500) {
                    return new Respuesta(false, "Parece que has introducido mal los datos.", String.valueOf(requestHTTP.getStatus()));
                }
                return new Respuesta(false, "Parece que algo ha salido mal. Si el problema persiste solicita ayuda del encargado del sistema.", String.valueOf(requestHTTP.getStatus()));
            }
            System.out.println(respuesta.body().toString());
           // AvionDto avion = g.fromJson(respuesta.body().toString(), AvionDto.class);
            return new Respuesta(true, "", "", "data", "");
        } catch (Exception ex) {
            Logger.getLogger(CobroPendienteService.class.getName()).log(Level.SEVERE, " Cobro() ->", ex);
            return new Respuesta(false, "Ha ocurrido un error al establecer comunicación con el servidor.", ex.getMessage());
        }
    }
}
