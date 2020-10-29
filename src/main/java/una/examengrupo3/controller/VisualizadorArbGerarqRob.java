/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package una.examengrupo3.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import una.examengrupo3.model.ProvinciaDto;
import una.examengrupo3.services.ProvinciaService;
import una.examengrupo3.util.AppContext;
import una.examengrupo3.util.FlowController;
import una.examengrupo3.util.Mensaje;
import una.examengrupo3.util.Respuesta;
import una.examengrupo3.util.SUCharger;

/**
 * FXML Controller class
 *
 * @author roberth
 */
public class VisualizadorArbGerarqRob extends Controller implements Initializable {

    @FXML
    public Label lblTreeLevel;
    @FXML
    public AnchorPane apCharger;
    @FXML
    public VBox treeLevelCharger;
    @FXML
    public Button plusButton;
    @FXML
    public HBox hbLocationCharger;
    private Integer profunidadActual;
    private Stack<List<SUCharger>> niveles;

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
        clearUI();
        cargarProvincias(false);
    }

    @FXML
    public void onActionNivelAnterior(ActionEvent event) {
        if (profunidadActual > 0) {
            hbLocationCharger.getChildren().remove(profunidadActual.intValue());
            profunidadActual--;
            goToNewLevel(niveles.pop(), false);
        } else {
            FlowController.getInstance().goBack();
        }
    }

    @FXML
    public void NuevoElemento(ActionEvent event) {
        switch (profunidadActual) {
            case 0:
                nuevaProvincia();
                break;
            case 1:
                nuevoCanton();
                break;
        }
    }

    public void goToNewLevel(List<SUCharger> suList, boolean save) {
        if (save) {
            niveles.push(suList);
            profunidadActual++;
        }
        treeLevelCharger.getChildren().removeIf(node -> !"plus-button".equals(node.getId()));
        treeLevelCharger.getChildren().addAll(suList);
        suList.forEach(cloud -> {
            putActionButonToCloud(cloud);
        });

    }

    public Label newSubTittle(String text) {
        Label toRet = new Label(text);
        toRet.getStyleClass().add("subtittle");
        return toRet;
    }

    public void cargarProvincias(boolean refreshMode) {
        Thread th = new Thread(() -> {
            Respuesta resp = new ProvinciaService().findAll();
            Platform.runLater(() -> {
                if (resp.getEstado()) {
                    List<ProvinciaDto> provincias = (List) resp.getResultado("data");
                    List<SUCharger> suList = new ArrayList();
                    provincias.forEach(prov -> suList.add(new SUCharger(prov)));
                    goToNewLevel(suList, !refreshMode);
                    changeTreeTittle();
                } else {
                    new Mensaje().show(AlertType.WARNING, "Atención", resp.getMensaje());
                }
            });
        });
        th.start();
    }

    public void nuevaProvincia() {
        AppContext.getInstance().set("prov", new ProvinciaDto());
        AppContext.getInstance().set("visualArb", this);
        FlowController.getInstance().goViewInWindowModal("EditorProvCantDistr", this.getStage(), false);
    }

    public void nuevoCanton() {

    }

    public void nuevoDistrito() {

    }

    public void nuevaUnidad() {

    }

    public void putActionButonToCloud(SUCharger cloud) {
        Button action = new Button("Ampliar");
        action.setOnAction(event -> {
            AppContext.getInstance().set("toShowDetall", cloud.getsUnidad());
            FlowController.getInstance().<AnchorPane>chargeOn(apCharger, "VisualizadorSuperUnid");
        });
        cloud.getChildren().add(action);
        action.setLayoutX(110);
        action.setLayoutY(30);
    }

    public void clearUI() {
        lblTreeLevel.setText("Cargando...");
        hbLocationCharger.getChildren().clear();
        niveles = new Stack();
        profunidadActual = -1;
        treeLevelCharger.getChildren().removeIf(node -> !"plus-button".equals(node.getId()));
    }

    public void changeTreeTittle() {
        switch (profunidadActual) {
            case 0:
                lblTreeLevel.setText("Provincias");
                break;
            case 1:
                lblTreeLevel.setText("Cantones");
                break;
            case 2:
                lblTreeLevel.setText("Distritos");
                break;
            default:
                lblTreeLevel.setText("Unidades");
                break;
        }
    }

}
