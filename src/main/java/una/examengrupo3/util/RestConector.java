/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package una.examengrupo3.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author roberth :)
 */
public class RestConector {

    private static final HttpClient CLIENT = HttpClient.newBuilder().version(Version.HTTP_2).build();
    private static final String WSDIRECTION = (String) AppContext.getInstance().get("resturlRoberth");
    private HttpResponse responce;
    private URI urlMethod;
    private Gson converter;

    public RestConector(String urlMethod) {
        this.urlMethod = URI.create(WSDIRECTION + urlMethod);
    }

    public RestConector() {
    }

    public void get(String direcction) {
        this.urlMethod = URI.create(WSDIRECTION + direcction);
        HttpRequest request = HttpRequest.newBuilder(urlMethod).GET().build();
        try {
            responce = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(RestConector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void post(Object obj) {
        HttpRequest request = HttpRequest.newBuilder(urlMethod)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(convertToJson(obj))).build();
        try {
            responce = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(RestConector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void put(Object obj) {
        HttpRequest request = HttpRequest.newBuilder(urlMethod)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(convertToJson(obj))).build();
        try {
            responce = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(RestConector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isError() {
        return responce.statusCode() < 200 || responce.statusCode() > 299;
    }

    public boolean isEmptyResult() {
        return responce.statusCode() == 204;
    }

    public <ObjectType> List<ObjectType> getResultAsList(Class<ObjectType> ObjectTypeClass) {
        Type objType = TypeToken.getParameterized(List.class, ObjectTypeClass).getType();
        return new Gson().fromJson(responce.body().toString(), objType);
    }

    public <ObjectType> ObjectType getResultAsObject(Class<ObjectType> t) {
        return new Gson().fromJson(responce.body().toString(), t);
    }

    public void delete() {
        throw new UnsupportedOperationException("Disponible próximamente");
    }

    public void changeDateFormatSerialization(String format) {
        converter = new GsonBuilder().setDateFormat(format).create();
    }

    private String convertToJson(Object obj) {
        converter = converter != null ? converter : new Gson();
        return new Gson().toJson(obj);
    }

}
