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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import una.examengrupo3.model.ClienteDTO;
import una.examengrupo3.service.ClienteService;
import una.examengrupo3.util.AppContext;
import una.examengrupo3.util.FlowController;
import una.examengrupo3.util.Mensaje;
import una.examengrupo3.util.Respuesta;

/**
 * FXML Controller class
 *
 * @author LordLalo
 */
public class BuscarClienteController extends Controller implements Initializable {

    @FXML
    public TextField txtNombre;
    @FXML
    public TextField txtPrimerApellido;
    @FXML
    public TextField txtSegundoApellido;
    @FXML
    public TextField txtCedula;
    @FXML
    public Button btnBuscar;
    @FXML
    public TableColumn<ClienteDTO, Long> tabId;
    @FXML
    public TableColumn<ClienteDTO, String> tabCedula;
    @FXML
    public TableColumn<ClienteDTO, String> tabNombre;
    @FXML
    public TableColumn<ClienteDTO, String> TabPrimerApellido;
    @FXML
    public TableColumn<ClienteDTO, String> TabSegundoApellido;
    @FXML
    public TableColumn<ClienteDTO, Void> TabAccion;
    @FXML
    public TableView<ClienteDTO> tabVClientes;
    public List<ClienteDTO> listCliente = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tabId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tabNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tabCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        TabPrimerApellido.setCellValueFactory(new PropertyValueFactory<>("apellido1"));
        TabSegundoApellido.setCellValueFactory(new PropertyValueFactory<>("apellido2"));

    }

    @Override
    public void initialize() {
      
    }

    @FXML
    public void onActionBuscar(ActionEvent event) {
        String pNombre = "none";
        String pApellido1 = "none";
        String pApellido2 = "none";
        String pCedula = "none";
        if (!txtNombre.getText().isBlank()) {
            pNombre = txtNombre.getText();
        }
        if (!txtPrimerApellido.getText().isBlank()) {
            pApellido1 = txtPrimerApellido.getText();
        }
        if (!txtSegundoApellido.getText().isBlank()) {
            pApellido2 = txtSegundoApellido.getText();
        }
        if (!txtCedula.getText().isBlank()) {
            pCedula = txtCedula.getText();
        }
        ClienteService clienteService = new ClienteService();
        Respuesta respuesta = clienteService.buscar(pNombre, pApellido1, pApellido2, pCedula);
        if (respuesta.getEstado()) {
            listCliente = (List<ClienteDTO>) respuesta.getResultado("data");

        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Falla al extraer los datos del cliente", this.getStage(), respuesta.getMensaje());
        }
        tabVClientes.getItems().clear();
        for (ClienteDTO clienteDTO : listCliente) {
            tabVClientes.getItems().add(clienteDTO);
        }
        addButtonToTable();
    }

    private void addButtonToTable() {

        Callback<TableColumn<ClienteDTO, Void>, TableCell<ClienteDTO, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<ClienteDTO, Void> call(final TableColumn<ClienteDTO, Void> param) {
                final TableCell<ClienteDTO, Void> cell = new TableCell<>() {

                    public Button acceptar = new Button("Acceptar");

                    {
                        acceptar.setOnAction((ActionEvent event) -> {
                            ClienteDTO clienteDTO = getTableView().getItems().get(getIndex());
                            AppContext.getInstance().set("cliente", clienteDTO);
                            FlowController.getInstance().goBack();
                            //FlowController.getInstance().goBack();
                        });
                    }

                    public HBox v = new HBox(acceptar);

                    {
                        v.setSpacing(2);
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(v);
                        }
                    }

                };
                return cell;
            }
        };

        TabAccion.setCellFactory(cellFactory);

        // tableResultados.getColumns().add(columAcciones);
    }

}
