/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package una.examengrupo3.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import una.examengrupo3.model.CantonDto;
import una.examengrupo3.services.CantonService;
import una.examengrupo3.util.AppContext;
import una.examengrupo3.util.Mensaje;
import una.examengrupo3.util.Respuesta;

/**
 * FXML Controller class
 *
 * @author roberth
 */
public class EditorCantonController extends Controller implements Initializable {

    @FXML
    public Label tittle;
    @FXML
    public TextField txtNombre;
    @FXML
    public TextField txtCodigo;
    public CantonDto canton;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        windowFuntions();
    }

    @Override
    public void initialize() {
        canton = (CantonDto) AppContext.getInstance().get("cant");
    }

    @FXML
    public void onActionGuradarCambios(ActionEvent event) {
        if (!txtCodigo.getText().isBlank() && !txtNombre.getText().isBlank()) {
            chargeDatos();
            Respuesta resp = new CantonService().create(canton);
            if (resp.getEstado()) {
                new Mensaje().show(Alert.AlertType.INFORMATION, "Ación exitosa", "Cambios guardados");
                updateBack();
            } else {
                new Mensaje().show(Alert.AlertType.WARNING, "Observa con atención", resp.getMensaje());
            }
        } else {
            new Mensaje().show(Alert.AlertType.INFORMATION, "Observa con atención", "Parece que alguno de los campos ha quedado vacío.");
        }
    }

    private void chargeDatos() {
        canton.setCodigo(Integer.valueOf(txtCodigo.getText()));
        canton.setNombre(txtNombre.getText().trim());
    }

    private void updateBack() {
        VisualizadorArbGerarqRob va  = (VisualizadorArbGerarqRob) AppContext.getInstance().get("visualArb");
        va.addCloudFromExterior(canton);
    }

    public void windowFuntions() {
        Platform.runLater(() -> {
            this.getStage().setOnCloseRequest(event -> {
                clearContext();
            });
        });
    }

    public void clearContext() {
        txtCodigo.setText("");
        txtNombre.setText("");
        canton = null;
    }

}
