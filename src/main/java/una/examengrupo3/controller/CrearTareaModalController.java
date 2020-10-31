package una.examengrupo3.controller;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import una.examengrupo3.model.ProyectoDTO;
import una.examengrupo3.model.TareaDTO;
import una.examengrupo3.service.TareaService;
import una.examengrupo3.util.AppContext;
import una.examengrupo3.util.Mensaje;
import una.examengrupo3.util.Respuesta;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CrearTareaModalController  extends Controller implements Initializable {

    public Pane taskPane;
    public TextField txtDescripcion;
    public DatePicker dateInicio;
    public DatePicker dateFinal;
    public Slider sliderAvance;
    public Slider sliderUrgencia;
    public Slider sliderImportancia;
    public Label labelImportenciaPercent;
    public Label labelUrgenciaPercent;
    public Label labelAvancePercent;
    public Label labelProjecName;
    ProyectoDTO projectSelected;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        projectSelected = (ProyectoDTO) AppContext.getInstance().get("projectSelected");
        labelProjecName.setText("Proyecto: " + projectSelected.getNombre());
        bindProperties();
    }

    private  TareaDTO createTaskWithDataInput(){


        TareaDTO tareaDTO = new TareaDTO();
        tareaDTO.setDescripcion(txtDescripcion.getText());
        tareaDTO.setFechaFinalizacion( dateFinal.getValue());
        tareaDTO.setFechaInicio(dateInicio.getValue());
        tareaDTO.setImportancia((long) sliderImportancia.getValue());
        tareaDTO.setUrgencia((long)sliderUrgencia.getValue());
        tareaDTO.setPorcentajeAvance((long)sliderAvance.getValue());
        tareaDTO.setProyecto(projectSelected);

        return tareaDTO;

    }

    private boolean  validateData(){
        if(txtDescripcion.getText().isEmpty() || dateFinal.getValue()==null || dateInicio.getValue() == null) return false;
        return true;
    }

    private void clearData(){
       txtDescripcion.setText("");
       dateFinal.setValue(LocalDate.now());
       dateFinal.setValue(LocalDate.now());
       sliderImportancia.setValue(0);
       sliderAvance.setValue(0);
       sliderUrgencia.setValue(0);
    }

    private void bindProperties(){
        labelImportenciaPercent.textProperty().bind( Bindings.format(
                "%.2f", sliderImportancia.valueProperty()
        ));
        labelUrgenciaPercent.textProperty().bind( Bindings.format(
                "%.2f", sliderUrgencia.valueProperty()
        ));
        labelAvancePercent.textProperty().bind( Bindings.format(
                "%.2f", sliderAvance.valueProperty()
        ));


    }

    @Override
    public void initialize() {

    }

    private void refreshBack() {
        if (AppContext.getInstance().get("proyectosController") != null) {
            ((ProyectosController) AppContext.getInstance().get("proyectosController")).addNewtask();
        }
    }

    public void createTaskOnAction(ActionEvent actionEvent) {
      if(validateData()){
          Respuesta respuesta = new TareaService().create(createTaskWithDataInput());

          if(respuesta.getEstado()){
              TareaDTO createdTask = (TareaDTO) respuesta.getResultado("data");
              AppContext.getInstance().set("taskCreated",createdTask );
              refreshBack();
              new Mensaje().showModal(Alert.AlertType.INFORMATION, "Información", this.getStage(), "Se ha creado la tarea con éxito");
              clearData();
              this.getStage().close();
          }
      }else{
          new Mensaje().showModal(Alert.AlertType.INFORMATION, "Información", this.getStage(), "Se deben completar todos los campos");
      }

    }
}
