/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package una.examengrupo3.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import una.examengrupo3.model.SuperUnidad;
import una.examengrupo3.util.AppContext;

/**
 * FXML Controller class
 *
 * @author roberth
 */
public class VisualizadorSuperUnidController extends Controller implements Initializable {

    @FXML
    public Label lblNombre;
    @FXML
    public Label lblCodigo;
    @FXML
    public Label lblPoblacion;
    @FXML
    public Label lblExtencion;
    private SuperUnidad sUnidad;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void initialize() {
        sUnidad = (SuperUnidad) AppContext.getInstance().get("toShowDetall");
        AppContext.getInstance().delete("toShowDetall");
        chargeData();
    }

    public void chargeData() {
        lblCodigo.setText("Código: " + sUnidad.getCodigo());
        lblNombre.setText("Nombre: " + sUnidad.getNombre());
        lblExtencion.setText("Extención: " + sUnidad.getAreaMetrosCuadrados() + " metros cuadrados.");
        lblPoblacion.setText("Extención: " + sUnidad.getPoblacion() + " habitantes.");
    }

}
