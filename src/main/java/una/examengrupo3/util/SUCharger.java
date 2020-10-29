/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package una.examengrupo3.util;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import una.examengrupo3.model.ProvinciaDto;
import una.examengrupo3.model.SuperUnidad;

/**
 *
 * @author roberth
 */
public class SUCharger extends AnchorPane {

    private final SuperUnidad sUnidad;
    private final Label nombre;
    private final Label codigo;

    public SUCharger(SuperUnidad data) {
        this.sUnidad = data;
        nombre = new Label(sUnidad.getNombre());
        codigo = new Label("Código: " + sUnidad.getCodigo().toString());
        buildVisualAspects();
    }

    private void buildVisualAspects() {
        this.getChildren().addAll(nombre, codigo);
        nombre.setLayoutX(7);
        nombre.setLayoutY(7);
        codigo.setLayoutX(7);
        codigo.setLayoutY(40);
        this.setPrefWidth(160);
        this.setPrefHeight(60);
        styleSelect();
    }

    public void styleSelect() {
        if (this.sUnidad instanceof ProvinciaDto) {
            this.getStyleClass().add("SU-Nube-provincia");
        }
    }

    public SuperUnidad getsUnidad() {
        return sUnidad;
    }

}
