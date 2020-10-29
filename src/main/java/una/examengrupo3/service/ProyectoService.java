package una.examengrupo3.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import una.examengrupo3.model.ProyectoDTO;
import una.examengrupo3.util.RequestHTTP;
import una.examengrupo3.util.Respuesta;

import java.net.http.HttpResponse;
import java.util.List;

public class ProyectoService {

    Gson gson = new GsonBuilder()
            .setDateFormat("yyy-MM-dd'T'HH:mm:ss.SSSX").create();

    public Respuesta getByEstado(Boolean estado){
        try {
            RequestHTTP requestHTTP = new RequestHTTP();
            HttpResponse respuesta = requestHTTP.get("http://localhost:2304/proyectos/getByEstado/"+estado.toString());
            System.out.println(respuesta.body().toString());
            if (requestHTTP.getStatus()!=200) {
                if (respuesta.statusCode() == 204) {
                    return new Respuesta(false, "Parece que no hay resultados en la búsqueda", String.valueOf(requestHTTP.getStatus()));
                }
                return new Respuesta(false, "Parece que algo ha salido mal. Si el problema persiste solicita ayuda del encargado del sistema." ,String.valueOf(requestHTTP.getStatus()));
            }

            List<ProyectoDTO> proyectos = gson.fromJson(respuesta.body().toString(), new TypeToken<List<ProyectoDTO>>() {}.getType());
            return new Respuesta(true, "", "", "data", proyectos);

        } catch (Exception ex) {
            System.out.println("ha ocurrido un error");
            return new Respuesta(false, "Ha ocurrido un error al establecer comunicación con el servidor.", ex.getMessage());
        }
    }

    public Respuesta create(ProyectoDTO proyectoDto){
        try {
            RequestHTTP requestHTTP = new RequestHTTP();
            HttpResponse respuesta = requestHTTP.post("http://localhost:2304/proyectos/create", gson.toJson(proyectoDto));
            System.out.println("respuesta: " + respuesta.body().toString());
            if (requestHTTP.getStatus()!=200) {
                if (respuesta.statusCode() == 204) {
                    return new Respuesta(false, "Parece que no hay resultados en la búsqueda", String.valueOf(requestHTTP.getStatus()));
                }
                return new Respuesta(false, "Parece que algo ha salido mal. Si el problema persiste solicita ayuda del encargado del sistema." ,String.valueOf(requestHTTP.getStatus()));
            }

            ProyectoDTO proyecto = gson.fromJson(respuesta.body().toString(), ProyectoDTO.class);
            return new Respuesta(true, "", "", "data", proyecto);

        } catch (Exception ex) {
            System.out.println("ha ocurrido un error" + ex.getMessage());
            return new Respuesta(false, "Ha ocurrido un error al establecer comunicación con el servidor.", ex.getMessage());
        }
    }

    public Respuesta update(ProyectoDTO proyectoDto){
        try {
            RequestHTTP requestHTTP = new RequestHTTP();
            HttpResponse respuesta = requestHTTP.put("http://localhost:2304/proyectos/update", gson.toJson(proyectoDto));
            System.out.println("respuesta: " + respuesta.body().toString());
            if (requestHTTP.getStatus()!=200) {
                if (respuesta.statusCode() == 204) {
                    return new Respuesta(false, "Parece que no hay resultados en la búsqueda", String.valueOf(requestHTTP.getStatus()));
                }
                return new Respuesta(false, "Parece que algo ha salido mal. Si el problema persiste solicita ayuda del encargado del sistema." ,String.valueOf(requestHTTP.getStatus()));
            }

            ProyectoDTO proyecto = gson.fromJson(respuesta.body().toString(), ProyectoDTO.class);
            return new Respuesta(true, "", "", "data", proyecto);

        } catch (Exception ex) {
            System.out.println("ha ocurrido un error" + ex.getMessage());
            return new Respuesta(false, "Ha ocurrido un error al establecer comunicación con el servidor.", ex.getMessage());
        }
    }

}
