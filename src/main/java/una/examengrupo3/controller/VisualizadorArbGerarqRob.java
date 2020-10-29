/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package una.examengrupo3.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import una.examengrupo3.services.ProvinciaService;
import una.examengrupo3.util.Respuesta;

/**
 * FXML Controller class
 *
 * @author rober
 */
public class VisualizadorArbGerarqRob extends Controller implements Initializable {

    @FXML
    public Label lblTreeLevel;
    @FXML
    public AnchorPane apCharger;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    @Override
    public void initialize() {
    }

    @FXML
    public void onActionNivelAnterior(ActionEvent event) {
    }

}
