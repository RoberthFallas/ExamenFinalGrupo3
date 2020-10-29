package una.examengrupo3.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import una.examengrupo3.model.TareaDTO;
import una.examengrupo3.util.RequestHTTP;
import una.examengrupo3.util.Respuesta;

import java.net.http.HttpResponse;
import java.util.List;

public class TareaService {


    Gson gson = new GsonBuilder()
            .setDateFormat("yyy-MM-dd'T'HH:mm:ss.SSSX").create();

    public Respuesta create(TareaDTO tareaDto){
        try {
            RequestHTTP requestHTTP = new RequestHTTP();
            HttpResponse respuesta = requestHTTP.post("http://localhost:2304/tareas/create", gson.toJson(tareaDto));
            System.out.println("respuesta: " + respuesta.body().toString());
            if (requestHTTP.getStatus()!=200) {
                if (respuesta.statusCode() == 204) {
                    return new Respuesta(false, "Parece que no hay resultados en la búsqueda", String.valueOf(requestHTTP.getStatus()));
                }
                return new Respuesta(false, "Parece que algo ha salido mal. Si el problema persiste solicita ayuda del encargado del sistema." ,String.valueOf(requestHTTP.getStatus()));
            }

             TareaDTO tarea = gson.fromJson(respuesta.body().toString(), TareaDTO.class);
            return new Respuesta(true, "", "", "data", tarea);

        } catch (Exception ex) {
            System.out.println("ha ocurrido un error" + ex.getMessage());
            return new Respuesta(false, "Ha ocurrido un error al establecer comunicación con el servidor.", ex.getMessage());
        }
    }

    public Respuesta update(TareaDTO tareaDto){
        try {
            RequestHTTP requestHTTP = new RequestHTTP();
            HttpResponse respuesta = requestHTTP.put("http://localhost:2304/tareas/update", gson.toJson(tareaDto));
            System.out.println("respuesta: " + respuesta.body().toString());
            if (requestHTTP.getStatus()!=200) {
                if (respuesta.statusCode() == 204) {
                    return new Respuesta(false, "Parece que no hay resultados en la búsqueda", String.valueOf(requestHTTP.getStatus()));
                }
                return new Respuesta(false, "Parece que algo ha salido mal. Si el problema persiste solicita ayuda del encargado del sistema." ,String.valueOf(requestHTTP.getStatus()));
            }

            TareaDTO tarea = gson.fromJson(respuesta.body().toString(), TareaDTO.class);
            return new Respuesta(true, "", "", "data", tarea);

        } catch (Exception ex) {
            System.out.println("ha ocurrido un error" + ex.getMessage());
            return new Respuesta(false, "Ha ocurrido un error al establecer comunicación con el servidor.", ex.getMessage());
        }
    }
}
