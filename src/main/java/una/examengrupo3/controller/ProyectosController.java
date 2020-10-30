package una.examengrupo3.controller;

import java.net.URL;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


import javafx.util.Callback;
import una.examengrupo3.model.ProyectoDTO;
import una.examengrupo3.model.Rango;
import una.examengrupo3.model.TareaDTO;
import una.examengrupo3.service.ProyectoService;
import una.examengrupo3.service.TareaService;
import una.examengrupo3.util.*;

public class ProyectosController extends Controller implements Initializable {

    public TreeView treeViewProjects;
    public TareaDTO tareaSelected ;
    public ProyectoDTO projectSelected;
    public Pane taskPane;
    public TextField txtDescripcion;
    public DatePicker dateInicio;
    public DatePicker dateFinal;
    public Slider sliderAvance;
    public Slider sliderUrgencia;
    //public Slider sliderPrioridad;
    public Slider sliderImportancia;
    public Label labelPrioridadPercent;
    public Label labelImportenciaPercent;
    public Label labelUrgenciaPercent;
    public Label labelAvancePercent;
    public Button editButton;
    public Button updateButton;
    public Button deleteButton;
    public Button editProjectButton;
    public TextField txtProjectName;
    public TextField txtObjective;
    public Button updateProjectButton;
    public Button cancelEdtionProjectButton;
    public Button cancelEdtionTaskButton;
    public VBox generalDetailsPane;
    public Pane projectPane;
    public Pane paneInformation;
    public Pane generalInfoPane;
    public ComboBox<String> comboColors;
    public Button showAllButton;
    public Label txtPrioridad;
    private List<Rango> rangos;

    private boolean isEditionProjectMode = false;
    private boolean isEditionTaskMode = false;

    TreeItem<HBox> root  = new TreeItem<>();
    List<ProyectoDTO> showDataProjects;
     List<ProyectoDTO> projects;
    ObservableList<String> colors;
    Rango actualRange = null;


    private void loadDefaulfRanges(){
        rangos = new ArrayList<>();
        rangos.add(new Rango(0,25, "#ea7590"));
        rangos.add(new Rango(26,50, "#e59736"));
        rangos.add(new Rango(51,75, "#eade75"));
        rangos.add(new Rango(76,100, "#75ea88"));
    }
    private  void updateComboColors(){
        colors = FXCollections.observableArrayList();
        rangos.forEach(r -> colors.add(r.getColor()));
        comboColors.setItems(colors);

        Callback<ListView<String>, ListCell<String>> factory = new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> list) {
                return new ColorRectCell();
            }
        };

        comboColors.setCellFactory(factory);
        comboColors.setButtonCell(factory.call(null));


    }
    private TreeItem<HBox> makeTaskBranch(TareaDTO tarea, TreeItem<HBox> parent){

        TreeItem<HBox> item = new TreeItem<>();
        HBox hBox = makeTaskHbox(tarea);
        hBox.setId(tarea.getId()+"_"+parent.getValue().getId());
        item.setValue(hBox);
        item.setExpanded(true);
        parent.getChildren().add(item);
        return  item;

    }
    private TreeItem<HBox> makeProjectBranch(ProyectoDTO project, TreeItem<HBox> parent){

        TreeItem<HBox> item = new TreeItem<>();
        HBox hBox = makeProjectHbox(project);
        hBox.setId(project.getId().toString());
        hBox.setOnMouseClicked(projectOnClick);
        item.setValue(hBox);
        item.setExpanded(true);
        parent.getChildren().add(item);
        return  item;


    }


    private void openModalForCreateTask(){

        ProyectosController proyectosController = this;
        AppContext.getInstance().set("projectSelected", projectSelected);
        AppContext.getInstance().set("proyectosController", proyectosController);
        FlowController.getInstance().goViewInWindowModal("CrearTareaModal", this.getStage(), false);
    }

    EventHandler<ActionEvent> addOnClick = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            Button button = (Button) e.getSource();
            String[] data = button.getId().split("_");
            Optional<ProyectoDTO> proj =  showDataProjects.stream().filter(k -> k.getId() == Long.valueOf(data[1])).findFirst();
            if(proj.isPresent()) {
                projectSelected = proj.get();
                openModalForCreateTask();
            }

        }
    };

    private boolean showConfirmation(String message){
        return  new Mensaje().showConfirmation("Alerta", this.getStage(), message);
    }

    EventHandler<MouseEvent> taskOnClick = new EventHandler<>() {
        @Override
        public void handle(MouseEvent e) {
            boolean seleccionar = true;
            if(isEditionTaskMode || isEditionProjectMode) {
                boolean confirmation = showConfirmation("Se encuentra usted editando una tarea o proyecto,al seleccionar otra tarea o proyecto perderá los datos editados, ¿Desea seleccionar otra tarea de todas maneras? ");
                seleccionar = confirmation;
                if(confirmation) {
                    projectEdition(false);
                    taskEdition(false);
                }
            }
            if(seleccionar){
                HBox hBox = (HBox) e.getSource();
                String[] data = hBox.getId().split("_");
                Optional<ProyectoDTO> proj = showDataProjects.stream().filter(k -> k.getId() == Long.valueOf(data[1])).findFirst();
                if (proj.isPresent()) {
                    projectSelected = proj.get();
                    Optional<TareaDTO> tarea = proj.get().getTareas().stream().filter(k -> k.getId() == Long.valueOf(data[0])).findFirst();
                    if (tarea.isPresent()) tareaSelected = tarea.get();
                    showDetailsOfSelectedTask();
                }
            }

        }
    };
    EventHandler<MouseEvent> projectOnClick = new EventHandler<>() {
        @Override
        public void handle(MouseEvent e) {
            HBox hBox = (HBox) e.getSource();
            String id = hBox.getId();
            Optional<ProyectoDTO> proj = showDataProjects.stream().filter(k -> k.getId() == Long.valueOf(id)).findFirst();
            if (proj.isPresent()) {
                projectSelected = proj.get();
                clearTaskDetailsInPane(true);
                showDetailsOfSelectedProject();
            }
        }
    };
    EventHandler<ActionEvent> deleteOnClick = new EventHandler<>() {
        @Override
        public void handle(ActionEvent e) {
            Button button = (Button) e.getSource();
            String[] data = button.getId().split("_");
            Optional<ProyectoDTO> proj =  showDataProjects.stream().filter(k -> k.getId() == Long.valueOf(data[1])).findFirst();
            if(proj.isPresent()){
                projectSelected = proj.get();
                safeDeleteProject();
            }
        }
    };

    private void safeDeleteProject(){
        Boolean confirmation = new Mensaje().showConfirmation("Alerta",this.getStage(),"¿Realmente desea eliminar el proyecto?");

        if(confirmation){
            Respuesta respuesta = new ProyectoService().delete(projectSelected);
            if(respuesta.getEstado()){
                new Mensaje().show(Alert.AlertType.INFORMATION, "Información", "Se ha eliminado el proyecto con éxito");
                showDataProjects.remove(projectSelected);
                showProjects();
                showInformationPane(true);
                projectSelected= null;
            }
        }
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
    private void showDetailsOfSelectedProject(){
        txtProjectName.setText(projectSelected.getNombre());
        txtObjective.setText(projectSelected.getObjetivo());
    }
    private void showDetailsOfSelectedTask(){
        clearTaskDetailsInPane(false);
        txtDescripcion.setText(tareaSelected.getDescripcion());
        dateInicio.setValue(tareaSelected.getFechaInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        dateFinal.setValue(tareaSelected.getFechaFinalizacion().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        sliderImportancia.setValue(tareaSelected.getImportancia());
        // sliderPrioridad.valueProperty().bind(sliderImportancia.valueProperty() * sliderUrgencia.valueProperty());
        txtPrioridad.setText(String.valueOf((tareaSelected.getUrgencia() * tareaSelected.getUrgencia())+"%"));
        sliderUrgencia.setValue(tareaSelected.getUrgencia());
        sliderAvance.setValue(tareaSelected.getPorcentajeAvance());
        showDetailsOfSelectedProject();
    }

    private  void clearTaskDetailsInPane(boolean status){
        showInformationPane(false);
        taskPane.setVisible(!status);
        projectPane.setVisible(true);

    }

    private HBox makeTaskHbox(TareaDTO tarea){
        HBox hBox = new HBox();
        VBox vBox = new VBox();
        HBox hboxTitulos = new HBox();
        HBox hBoxEstado = new HBox();
        HBox sep = new HBox();
        hboxTitulos.setAlignment(Pos.CENTER);
        HBox.setHgrow(hboxTitulos, Priority.ALWAYS);
        Label taskTitleLabel = new Label(tarea.getDescripcion());
        taskTitleLabel.getStyleClass().add("taskTittle");
        hboxTitulos.getChildren().addAll(taskTitleLabel,sep);
        Label estado = new Label("Importancia: " + tarea.getImportancia().toString() +  "     %"+ tarea.getPorcentajeAvance()) ;
        estado.getStyleClass().add("taskDetails");
        hBoxEstado.getChildren().add(estado);
        hBox = setStyleByPercent(hBox, tarea.getPorcentajeAvance());
        vBox.getChildren().addAll(hboxTitulos,hBoxEstado);
        hBox.getChildren().addAll(vBox);
        hBox.setOnMouseClicked(taskOnClick);
        hBox.setAlignment(Pos.CENTER);

        return  hBox;

    }
    private HBox setStyleByPercent(HBox box, long percent){

        rangos.forEach(k -> {

            if(k.isInRank(percent)) box.setStyle("-fx-background-color: " + k.getColor() + ";\n" +
                    "    -fx-padding: 15;\n" +
                    "    -fx-spacing: 10;" );
        });

        return box;

    }
    private HBox makeBasicHbox(String title){
        HBox hBox = new HBox();
        HBox separator = new HBox();
        HBox.setHgrow(separator, Priority.ALWAYS);
        hBox.setAlignment(Pos.CENTER);
        Label taskTitleLabel = new Label(title);
        taskTitleLabel.getStyleClass().add("projectTittle");
        hBox.getChildren().addAll(taskTitleLabel, separator);

        return  hBox;
    }
    private  HBox makeProjectHbox(ProyectoDTO proyectoDTO){
        HBox hBox = makeBasicHbox(proyectoDTO.getNombre());
        hBox.getStyleClass().add("hboxProject");
        Button addButton = new Button();
        addButton.setId("add_"+proyectoDTO.getId().toString());
        addButton.setOnAction(addOnClick);
        Button deleteButton = new Button();
        deleteButton.setId("delete_"+proyectoDTO.getId().toString());
        addButton.setGraphic(new ImageView(String.valueOf(getClass().getResource("/img/plus.png"))));

        deleteButton.setGraphic(new ImageView(String.valueOf(getClass().getResource("/img/delete.png"))));
        deleteButton.setOnAction(deleteOnClick);
        hBox.getChildren().addAll(addButton, deleteButton);
        return hBox;

    }

    private void loadProjects(){
        Respuesta respuesta = new ProyectoService().getByEstado(true);

        if(respuesta.getEstado()){
            projects = (List<ProyectoDTO>) respuesta.getResultado("data");
            AppContext.getInstance().set("projects", projects);
            showDataProjects =projects;

        }else{
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Información", this.getStage(), "No se pudieron cargar los proyectos");
        }

    }

    private void  showProjects(){
        root.getChildren().clear();
        for (ProyectoDTO projet: showDataProjects){
           TreeItem p =  makeProjectBranch(projet,root);

           if(projet.getTareas()!=null) projet.getTareas().forEach(k ->{
               if(actualRange == null ) makeTaskBranch(k,p);
               else {
                   if(actualRange.isInRank(k.getPorcentajeAvance())) makeTaskBranch(k,p);
               }
           });
        }
    }

    private void showInformationPane(boolean status){
       paneInformation.setVisible(status);
       generalInfoPane.setVisible(!status);
    }

    private  void projectEdition(boolean status){
        clearTaskDetailsInPane(true);
        isEditionProjectMode = status;
       editButton.setVisible(!status);
       txtObjective.setEditable(status);
       txtProjectName.setEditable(status);
       updateProjectButton.setVisible(status);
       cancelEdtionProjectButton.setVisible(status);
       editProjectButton.setVisible(!status);

    }

    private  void taskEdition(boolean status){
        isEditionTaskMode = status;
        txtDescripcion.setEditable(status);
        dateInicio.setDisable(!status);
        dateInicio.setStyle("-fx-opacity: 1");
        dateInicio.getEditor().setStyle("-fx-opacity: 1");;
        dateFinal.setStyle("-fx-opacity: 1");
        dateFinal.getEditor().setStyle("-fx-opacity: 1");
        dateFinal.setDisable(!status);
        sliderImportancia.setDisable(!status);
        editProjectButton.setVisible(!status);
        sliderUrgencia.setDisable(!status);
        sliderAvance.setDisable(!status);
        editButton.setVisible(!status);
        deleteButton.setVisible(!status);
        updateButton.setVisible(status);
        cancelEdtionTaskButton.setVisible(status);

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        root.setExpanded(true);
        treeViewProjects.setRoot(root);
        treeViewProjects.setShowRoot(false);
        loadDefaulfRanges();
        bindProperties();
        loadProjects();
        showProjects();
        taskEdition(false);
        projectEdition(false);
        showInformationPane(true);

    }

    public void addNewtask(){

        TareaDTO newtask = (TareaDTO) AppContext.getInstance().get("taskCreated");
        if(newtask!=null){
            showDataProjects.stream().filter(k -> projectSelected.getId() == k.getId()).findFirst().get().getTareas().add(newtask);
            showProjects();
        }
    }

    private TareaDTO updateInputDataToSelectedTask(){

        TareaDTO updatedTask = tareaSelected;

        updatedTask.setDescripcion(txtDescripcion.getText());
        updatedTask.setFechaFinalizacion( dateFinal.getValue());
        updatedTask.setFechaInicio(dateInicio.getValue());
        updatedTask.setImportancia((long) sliderImportancia.getValue());
        updatedTask.setUrgencia((long)sliderUrgencia.getValue());
        updatedTask.setPorcentajeAvance((long)sliderAvance.getValue());
        ProyectoDTO proyectoDTO = new ProyectoDTO();
        proyectoDTO.setId(projectSelected.getId());
        updatedTask.setProyecto(proyectoDTO);
        return  updatedTask;

    }

    private boolean  validateTaskData(){
        if(txtDescripcion.getText().isEmpty() || dateFinal.getValue()==null || dateInicio.getValue() == null) return false;
        return true;
    }

    private void updateTaskInList(TareaDTO tarea){
        txtPrioridad.setText(String.valueOf((tarea.getUrgencia() * tarea.getUrgencia()))+ "%");
        showDataProjects.stream().filter(k -> projectSelected.getId() == k.getId()).findFirst().get().updateTask(tarea);
    }

    private void updateProjectInList(ProyectoDTO proyectoDTO){
        showDataProjects.forEach(k -> {
            if(k.getId() == proyectoDTO.getId()) k = proyectoDTO;
        });
        showProjects();
    }


    @Override
    public void initialize() {

        updateComboColors();
    }


    public void deleteSelectedTaskOnAction(ActionEvent actionEvent) {

        Boolean confirmation = new Mensaje().showConfirmation("Alerta",this.getStage(),"¿Realmente desea eliminar la tarea?");

        if(confirmation){
            Respuesta respuesta = new TareaService().delete(tareaSelected);
            if(respuesta.getEstado()){
                showDataProjects.stream().filter(k -> projectSelected.getId() == k.getId()).findFirst().get().deleteTask(tareaSelected);
                showProjects();
                showInformationPane(true);
                tareaSelected = null;
               new Mensaje().show(Alert.AlertType.INFORMATION, "Información", "Se ha eliminado la tarea con éxito");
            }
        }


    }

    public void editSelectedTaskOnAction(ActionEvent actionEvent) {
        taskEdition(true);
    }

    public   void addNewProject(){

        ProyectoDTO proyectoDTO = (ProyectoDTO) AppContext.getInstance().get("createProject");
        showDataProjects.add(proyectoDTO);
        showProjects();


    }

    public void newProjectOnAction(ActionEvent actionEvent) {

        AppContext.getInstance().set("proyectosController", this);
        FlowController.getInstance().goViewInWindowModal("CrearProyecto", this.getStage(), false);

    }

    public void updateTaskOnAction(ActionEvent actionEvent) {

        if(validateTaskData()){
            Respuesta respuesta = new TareaService().update(updateInputDataToSelectedTask());
            if(respuesta.getEstado()){
                tareaSelected = (TareaDTO) respuesta.getResultado("data");
                updateTaskInList(tareaSelected);
                showProjects();
                new Mensaje().showModal(Alert.AlertType.INFORMATION, "Información", this.getStage(), "Se ha actualizado la tarea  con éxito");
            }
            taskEdition(false);
        }else{
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Información", this.getStage(), "Se deben completar todos los campos");
        }



    }

    private void updateProjectFromInputData(){
        ProyectoDTO proyectoDTO = new ProyectoDTO(txtProjectName.getText(),txtObjective.getText());
        proyectoDTO.setId(projectSelected.getId());
        Respuesta respuesta = new ProyectoService().update(proyectoDTO);
        if(respuesta.getEstado()){
            projectEdition(false);
            updateProjectInList(proyectoDTO);
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Información", this.getStage(), "Se ha actualizado el proyecto con éxito");
        }
    }
    public void editProjectOnAction(ActionEvent actionEvent) {
        projectEdition(true);
    }

    public void cancelEdtionProjectOnAction(ActionEvent actionEvent) {
        showDetailsOfSelectedProject();
        projectEdition(false);
    }

    public void updateProjectButtonOnAction(ActionEvent actionEvent) {


        if(!txtProjectName.getText().isEmpty() && !txtObjective.getText().isEmpty()){
            boolean seleccionar = true;
            if(isEditionTaskMode || isEditionProjectMode) {
                seleccionar = showConfirmation("Se encuentra usted editando una tarea o proyecto,al seleccionar otra tarea o proyecto perderá los datos editados, ¿Desea seleccionar otra tarea de todas maneras? ");
                if(seleccionar) {
                    projectEdition(false);
                    taskEdition(false);
                }
            }
            if(seleccionar) updateProjectFromInputData();
        }else{
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Información", this.getStage(), "Se deben completar todos los campos");
        }


    }

    public void cancelEdtionTaskOnAction(ActionEvent actionEvent) {
        showDetailsOfSelectedTask();
        taskEdition(false);
    }

    public void updateRangeColors(){
        rangos = (List<Rango>) AppContext.getInstance().get("rangos");
        updateComboColors();
        showProjects();

    }

    public void settingsOnAction(ActionEvent actionEvent) {
        AppContext.getInstance().set("proyectosController", this);
        AppContext.getInstance().set("rangos", rangos);
        FlowController.getInstance().goViewInWindowModal("RangoColores", this.getStage(),false);
    }

    public void comboOnAction(ActionEvent actionEvent) {
        String color = comboColors.getSelectionModel().getSelectedItem();
        Optional<Rango> rango = rangos.stream().filter( r -> r.getColor().equals(color)).findFirst();
        if(rango.isPresent()) {
            actualRange = rango.get();
            showProjects();
        }
        showAllButton.setVisible(true);
    }

    public void showAllOnAction(ActionEvent actionEvent) {
        actualRange = null;
        comboColors.getSelectionModel().clearSelection();
        showAllButton.setVisible(false);
        showProjects();
    }
}
