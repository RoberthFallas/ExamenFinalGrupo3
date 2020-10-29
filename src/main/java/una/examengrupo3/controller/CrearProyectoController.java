package una.examengrupo3.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import una.examengrupo3.App;
import una.examengrupo3.model.ProyectoDTO;
import una.examengrupo3.model.TareaDTO;
import una.examengrupo3.service.ProyectoService;
import una.examengrupo3.service.TareaService;
import una.examengrupo3.util.AppContext;
import una.examengrupo3.util.Mensaje;
import una.examengrupo3.util.Respuesta;

import java.net.URL;
import java.util.ResourceBundle;

public class CrearProyectoController extends Controller implements Initializable {
    public TextField txtNombre;
    public TextField txtObjetivo;
    public Button addButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void initialize() {

    }

    private ProyectoDTO createProjectFromInputData(){

        ProyectoDTO proyecto = new ProyectoDTO(txtNombre.getText(),txtObjetivo.getText());

        return proyecto;

    }

    public void addProjectOnAction(ActionEvent actionEvent) {

        Respuesta respuesta = new ProyectoService().create(createProjectFromInputData());
        if(respuesta.getEstado()){

            ProyectoDTO createdProject = (ProyectoDTO) respuesta.getResultado("data");
            AppContext.getInstance().set("createProject", createdProject);

            ProyectosController proyectosController = (ProyectosController) AppContext.getInstance().get("proyectosController");
            proyectosController.addNewProject();
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Información", this.getStage(), "Se ha creado el proyecto con éxito");
        }
    }
}
