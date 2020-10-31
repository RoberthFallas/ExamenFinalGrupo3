/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package una.examengrupo3.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import una.examengrupo3.model.TipoUnidadDto;
import una.examengrupo3.model.UnidadDto;
import una.examengrupo3.services.TipoUnidadService;
import una.examengrupo3.services.UnidadService;
import una.examengrupo3.util.AppContext;
import una.examengrupo3.util.Mensaje;
import una.examengrupo3.util.Respuesta;

/**
 * FXML Controller class
 *
 * @author roberth
 */
public class EditorUnidadController extends Controller implements Initializable {

    @FXML
    public Label tittle;
    @FXML
    public TextField txtNombre;
    @FXML
    public TextField txtCodigo;
    @FXML
    public ComboBox<TipoUnidadDto> cbTipoUnidad;
    private UnidadDto unidad;
    @FXML
    public TextField txtPoblación;
    @FXML
    public TextField txtExtencion;

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
        unidad = (UnidadDto) AppContext.getInstance().get("unid");
        chargeTipoUnidad();
    }

    @FXML
    public void onActionGuradarCambios(ActionEvent event) {
        if (!txtCodigo.getText().isBlank() && !txtNombre.getText().isBlank() && cbTipoUnidad.getValue() != null) {
            chargeDatos();
            Respuesta resp = new UnidadService().create(unidad);
            if (resp.getEstado()) {
                new Mensaje().show(Alert.AlertType.INFORMATION, "Ación exitosa", "Cambios guardados");
                unidad = (UnidadDto) resp.getResultado("data");
                updateBack();
            } else {
                new Mensaje().show(Alert.AlertType.WARNING, "Observa con atención", resp.getMensaje());
            }
        } else {
            new Mensaje().show(Alert.AlertType.INFORMATION, "Observa con atención", "Parece que alguno de los campos ha quedado vacío.");
        }
    }

    private void chargeTipoUnidad() {
        cbTipoUnidad.setPromptText("Cargando...");
        Thread th = new Thread(() -> {
            Respuesta resp = new TipoUnidadService().findAll();
            Platform.runLater(() -> {
                if (resp.getEstado()) {
                    cbTipoUnidad.setPromptText("Tipo de unidad");
                    cbTipoUnidad.getItems().clear();
                    cbTipoUnidad.getItems().addAll((List) resp.getResultado("data"));
                } else {
                    cbTipoUnidad.setPromptText(resp.getMensaje());
                }
            }
            );
        });
        th.start();
    }

    private void windowFuntions() {
        Platform.runLater(() -> {
            this.getStage().setOnCloseRequest(event -> {
                clearContext();
            });
        });
    }

    private void clearContext() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtExtencion.setText("");
        txtPoblación.setText("");
        cbTipoUnidad.setValue(null);
        cbTipoUnidad.getItems().clear();
        unidad = null;
    }

    private void chargeDatos() {
        unidad.setCodigo(Integer.valueOf(txtCodigo.getText()));
        unidad.setNombre(txtNombre.getText().trim());
        unidad.setTipoUnidad(cbTipoUnidad.getValue());
        unidad.setAreaMetrosCuadrados(Float.valueOf(txtExtencion.getText()));
        unidad.setPoblacion(Long.valueOf(txtPoblación.getText()));
    }

    private void updateBack() {
        VisualizadorArbGerarqRob va  = (VisualizadorArbGerarqRob) AppContext.getInstance().get("visualArb");
        va.addCloudFromExterior(unidad);
    }

}
