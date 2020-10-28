/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package una.examengrupo3.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import una.examengrupo3.model.ClienteDTO;
import una.examengrupo3.util.RequestHTTP;
import una.examengrupo3.util.Respuesta;

/**
 *
 * @author LordLalo
 */
public class ClienteService {
      Gson gson = new GsonBuilder()
            .setDateFormat("yyy-MM-dd'T'HH:mm:ss.SSSX").create();
  public Respuesta buscarPorID(long  id){
        try {
            RequestHTTP requestHTTP = new RequestHTTP();
            HttpResponse respuesta = requestHTTP.get("http://localhost:1250/clientes/"+id);
            System.out.println(respuesta.body().toString());
            if (requestHTTP.getStatus()!=200) {
                if (respuesta.statusCode() == 500) {
                    return new Respuesta(false, "Parece error al extraer la imformación", String.valueOf(requestHTTP.getStatus()));
                }
                return new Respuesta(false, "Parece que algo ha salido mal. Si el problema persiste solicita ayuda del encargado del sistema." ,String.valueOf(requestHTTP.getStatus()));
            }

            ClienteDTO clienteDto = gson.fromJson(respuesta.body().toString(), ClienteDTO.class);
            return new Respuesta(true, "", "", "data", clienteDto);

        } catch (Exception ex) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, " BuscarID ->", ex);
            System.out.println("ha ocurrido un error");
            return new Respuesta(false, "Ha ocurrido un error al establecer comunicación con el servidor.", ex.getMessage());
        }
    }
    public Respuesta buscar(String nombre, String apellido1,String apellido2, String cedula) {
        try {
            String url = "http://localhost:1250/clientes/busquedaCompletaCliente/" + nombre + "/" + apellido1+"/"+apellido2+ "/" + cedula;
            
            RequestHTTP requestHTTP = new RequestHTTP();
            HttpResponse respuesta = requestHTTP.get(url);
            if (requestHTTP.getStatus() != 200) {
                if (respuesta.statusCode() == 500) {
                    return new Respuesta(false, "Parece que has introducido mal tus credenciales de acceso.", String.valueOf(requestHTTP.getStatus()));
                }
                return new Respuesta(false, "Parece que algo ha salido mal. Si el problema persiste solicita ayuda del encargado del sistema.", String.valueOf(requestHTTP.getStatus()));
            }
            System.out.println(respuesta.body().toString());

            //UsuarioDto usuarioDto = g.fromJson(respuesta.body().toString(), UsuarioDto.class);
            List<ClienteDTO> clienteDTO = new Gson().fromJson(respuesta.body().toString(), new TypeToken<List<ClienteDTO>>() {
            }.getType());
            return new Respuesta(true, "", "", "data",clienteDTO);

        } catch (Exception ex) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, " BuscarCliente() ->", ex);
            return new Respuesta(false, "Ha ocurrido un error al establecer comunicación con el servidor.", ex.getMessage());
        }
    }
}
