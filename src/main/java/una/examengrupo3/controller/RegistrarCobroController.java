/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package una.examengrupo3.controller;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import una.examengrupo3.model.ClienteDTO;
import una.examengrupo3.model.MembresiaClienteDTO;
import una.examengrupo3.model.MembresiaDTO;
import una.examengrupo3.service.ClienteService;
import una.examengrupo3.util.AppContext;
import una.examengrupo3.util.FlowController;
import una.examengrupo3.util.Respuesta;

/**
 * FXML Controller class
 *
 * @author LordLalo
 */
public class RegistrarCobroController extends Controller implements Initializable {
    
    @FXML
    public Button btnBuscarCliente;
    public TextField txtNombre;
    @FXML
    public Label lblNombre;
    @FXML
    public HBox btnBuscar;
    @FXML
    public Label lblCedula;
    @FXML
    public Label lblPrimerApellido;
    @FXML
    public Label lblSegundoApellido;
    @FXML
    public ComboBox<MembresiaClienteDTO> cbxMembresia;
    @FXML
    public HBox Periosidad;
    @FXML
    public Label lblServicio;
    @FXML
    public Label lblMonto;
    @FXML
    public Label lblPeriosidad;
    List<MembresiaClienteDTO> membresiaCliente = new ArrayList<>();
    @FXML
    public Label labelFecha;
    @FXML
    public ComboBox<String> cbxPeriodo;
        
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cbxPeriodo.getItems().addAll("Mensual","Bimensual","Trismetal","Cuatrimestral","Anuales");
        cbxMembresia.setDisable(true);
        
        labelFecha.setText(Date.from(Instant.now()).toString());
        CargarDatos();
    }
    
    @Override
    public void initialize() {
        CargarDatos();
    }
    
    @FXML
    public void onActionBuscar(ActionEvent event) {
        FlowController.getInstance().goView("BuscarCliente");
    }
    
    public void CargarDatos() {
        lblNombre.setText("Nombre");
        ClienteDTO client;
        if (AppContext.getInstance().get("cliente") != null) {
            client = (ClienteDTO) AppContext.getInstance().get("cliente");
            lblNombre.setText(lblNombre.getText() + ": " + client.getNombre());
            lblCedula.setText(lblCedula.getText() + ": " + client.getCedula());
            lblPrimerApellido.setText(lblPrimerApellido.getText() + ": " + client.getApellido1());
            lblSegundoApellido.setText(lblSegundoApellido.getText() + ": " + client.getApellido2());
            //client.getMembrebesiaCliente();

            membresiaCliente = (List<MembresiaClienteDTO>) client.getMembrebesiaCliente();
//            lblNombre.setText(membresiaCliente.getMembresiaId().getDescripcion());
            for (MembresiaClienteDTO m : membresiaCliente) {
                cbxMembresia.getItems().add(m);
            }
            cbxMembresia.setDisable(false);
        }
    }
    
    @FXML
    public void onActionMostrar(ActionEvent event) {
        limpiar();
        lblPeriosidad.setText(lblPeriosidad.getText() + ": " + cbxMembresia.getValue().getMembresiaId().getPeriodicidad());
        lblServicio.setText(lblServicio.getText() + ": " + cbxMembresia.getValue().getMembresiaId().getServicioId().getNombre());
        lblMonto.setText(lblMonto.getText() + ": $" + cbxMembresia.getValue().getMembresiaId().getMonto().toString());
        
    }
    
    public void limpiar() {
        lblPeriosidad.setText("Periosidad");
        lblServicio.setText("Servicio");
        lblMonto.setText("Monto");
    }

    @FXML
    public void selecionPeriodo(ActionEvent event) {
    }
}
