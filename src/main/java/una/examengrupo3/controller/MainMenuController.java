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
import una.examengrupo3.util.FlowController;

/**
 * FXML Controller class
 *
 * @author roberth
 */
public class MainMenuController extends Controller implements Initializable {

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
    }

    @FXML
    public void goMonicaModule(ActionEvent event) {
        FlowController.getInstance().goView("proyectos");
    }

    @FXML
    public void goGerardoModule(ActionEvent event) {
        FlowController.getInstance().goView("MostrarCobros");
    }

    @FXML
    public void goRoberthModule(ActionEvent event) {
        FlowController.getInstance().goView("VisualizadorArbGerarqRob");
    }

}
