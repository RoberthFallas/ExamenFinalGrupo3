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
import javafx.scene.control.Button;
import una.examengrupo3.util.FlowController;

/**
 * FXML Controller class
 *
 * @author LordLalo
 */
public class MenuCobrosController extends Controller implements Initializable {

    @FXML
    public Button btnRegistar;
    @FXML
    public Button btnCobro;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void initialize() {
    }

    @FXML
    public void goRegistrar(ActionEvent event) {
         FlowController.getInstance().goView("CrearCliente");
    }

    @FXML
    public void onActionCobro(ActionEvent event) {
     FlowController.getInstance().goView("RegistrarCobro");
    }

}
