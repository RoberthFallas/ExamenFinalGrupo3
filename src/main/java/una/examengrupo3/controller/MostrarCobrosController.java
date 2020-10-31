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
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import una.examengrupo3.model.ClienteDto;
import una.examengrupo3.model.CobroPendienteDto;
import una.examengrupo3.model.MembresiaClienteDto;
import una.examengrupo3.service.ClienteService;
import una.examengrupo3.service.CobroPendienteService;
import una.examengrupo3.util.Mensaje;
import una.examengrupo3.util.Respuesta;

/**
 * FXML Controller class
 *
 * @author LordLalo
 */
public class MostrarCobrosController extends Controller implements Initializable {

    @FXML
    public TreeTableView<ClienteDto> treeTabla;
    @FXML
    public TreeTableColumn<ClienteDto, String> treeDatos;
    List<ClienteDto> listaClientes = new ArrayList<>();
    @FXML
    public TextField txtCedula;
    @FXML
    public TextField txtNombre;
    @FXML
    public TextField txtPrimerApellido;
    @FXML
    public TextField txtSegundoApellido;
    @FXML
    public Button btnBuscar;
    @FXML
    public Button btnGenerarCobros;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        treeDatos.setCellValueFactory(new TreeItemPropertyValueFactory<>("nombre"));

        //  CargarDatos();
    }

    @Override
    public void initialize() {
    }

    public void CargarDatos() {
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
            listaClientes = (List<ClienteDto>) respuesta.getResultado("data");
        }
        ClienteDto cl = new ClienteDto();
        cl.setNombre("Cliente");
        TreeItem clientes = new TreeItem(cl);
        for (ClienteDto cliente : listaClientes) {
            TreeItem persona = new TreeItem(new ClienteDto(cliente.getNombre() + " " + cliente.getApellido1() + " " + cliente.getApellido2()));
            TreeItem datosPersonales = new TreeItem(new ClienteDto("Datos Personales"));
            TreeItem cedula = new TreeItem(new ClienteDto("Cédula: " + cliente.getCedula()));
            int anno = (cliente.getFechaNacimiento().getYear() + 1900);
            int mes = (cliente.getFechaNacimiento().getMonth() + 1);
            int dia = (cliente.getFechaNacimiento().getDay() + 4);

            TreeItem fechaNacimiento = new TreeItem(new ClienteDto("Fecha Nacimiento: " + dia + "/" + mes + "/" + anno));
            datosPersonales.getChildren().addAll(cedula, fechaNacimiento);
            persona.getChildren().add(datosPersonales);
            List<MembresiaClienteDto> listMembresiaCliente = new ArrayList<>();
            listMembresiaCliente = (List<MembresiaClienteDto>) cliente.getMembrebesiaCliente();
            TreeItem membresiaTree = new TreeItem(new ClienteDto("Membresias"));
            for (MembresiaClienteDto membresiaCliente : listMembresiaCliente) {

                TreeItem m = new TreeItem(new ClienteDto(membresiaCliente.getMembresiaId().getServicioId().getNombre()));
                List<CobroPendienteDto> listaCobroPendiente = new ArrayList<>();
                listaCobroPendiente = (List<CobroPendienteDto>) membresiaCliente.getCobroPendiente();
                for (CobroPendienteDto cobroPendiente : listaCobroPendiente) {
                    int annoV = (cobroPendiente.getFechaVencimiento().getYear() + 1900);
                    int mesV = (cobroPendiente.getFechaVencimiento().getMonth() + 1);
                    int diaV = (cobroPendiente.getFechaVencimiento().getDay() + 4);
                    TreeItem fecha = new TreeItem(new ClienteDto("Fecha Vencimiento :" + diaV + "/" + mesV + "/" + annoV));
                    TreeItem periodo = new TreeItem(new ClienteDto("Periodo :" + cobroPendiente.getPeriodo()));
                    TreeItem monto = new TreeItem(new ClienteDto("Monto :" + cobroPendiente.getMonto()));
                    fecha.getChildren().addAll(periodo, monto);
                    m.getChildren().addAll(fecha);
                }

                membresiaTree.getChildren().add(m);

            }
            persona.getChildren().add(membresiaTree);
            clientes.getChildren().add(persona);
        }
        treeTabla.setRoot(clientes);

    }

    @FXML
    public void buscarClientess(ActionEvent event) {
        // treeTabla.getColumns().clear();
        CargarDatos();
    }

    @FXML
    public void goGenerarCobros(ActionEvent event) {
        Mensaje m = new Mensaje();

        if (m.showConfirmation("Generar Cobros", this.getStage(), "Desea Realizar los cobros").booleanValue()) {
            Thread th = new Thread(() -> {
                Respuesta resp = new CobroPendienteService().generarCobros();
                Platform.runLater(() -> {
                    if (resp.getEstado()) {
                        Mensaje mensaje = new Mensaje();
                        mensaje.show(Alert.AlertType.INFORMATION, "Crear Cobros", " Los Cobros fueron Generados Correctamente");
                    } else {
                        Mensaje mensaje = new Mensaje();
                        mensaje.show(Alert.AlertType.WARNING, "Crear Cobros", "Los Cobros fueron Generados anteriormente");
                    }
                });
            });
            th.start();
        }

    }
}
