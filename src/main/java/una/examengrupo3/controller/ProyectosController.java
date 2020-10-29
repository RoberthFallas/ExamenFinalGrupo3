package una.examengrupo3.controller;

import java.net.URL;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
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


import una.examengrupo3.model.ProyectoDTO;
import una.examengrupo3.model.TareaDTO;
import una.examengrupo3.service.ProyectoService;
import una.examengrupo3.service.TareaService;
import una.examengrupo3.util.AppContext;
import una.examengrupo3.util.FlowController;
import una.examengrupo3.util.Mensaje;
import una.examengrupo3.util.Respuesta;

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
    public Slider sliderPrioridad;
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

    private boolean isEditionProjectMode = false;

    TreeItem<HBox> root  = new TreeItem<>();
    List<ProyectoDTO> projects ;
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
            Optional<ProyectoDTO> proj =  projects.stream().filter(k -> k.getId() == Long.valueOf(data[1])).findFirst();
            if(proj.isPresent()) {
                projectSelected = proj.get();
                openModalForCreateTask();
            }

        }
    };
    EventHandler<MouseEvent> taskOnClick = new EventHandler<>() {
        @Override
        public void handle(MouseEvent e) {
            HBox hBox = (HBox) e.getSource();
            String[] data = hBox.getId().split("_");
            Optional<ProyectoDTO> proj = projects.stream().filter(k -> k.getId() == Long.valueOf(data[1])).findFirst();
            if (proj.isPresent()) {
                projectSelected = proj.get();
                Optional<TareaDTO> tarea = proj.get().getTareas().stream().filter(k -> k.getId() == Long.valueOf(data[0])).findFirst();
                if (tarea.isPresent()) tareaSelected = tarea.get();

                showDetailsOfSelectedTask();
            }
        }
    };
    EventHandler<MouseEvent> projectOnClick = new EventHandler<>() {
        @Override
        public void handle(MouseEvent e) {
            HBox hBox = (HBox) e.getSource();
            String id = hBox.getId();
            Optional<ProyectoDTO> proj = projects.stream().filter(k -> k.getId() == Long.valueOf(id)).findFirst();
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
            Optional<ProyectoDTO> proj =  projects.stream().filter(k -> k.getId() == Long.valueOf(data[1])).findFirst();
            if(proj.isPresent()) projectSelected = proj.get();
            System.out.println(projectSelected.toString());

        }
    };


    private void bindProperties(){
        labelImportenciaPercent.textProperty().bind( Bindings.format(
                "%.2f", sliderImportancia.valueProperty()
        ));
        labelUrgenciaPercent.textProperty().bind( Bindings.format(
                "%.2f", sliderUrgencia.valueProperty()
        ));
        labelPrioridadPercent.textProperty().bind( Bindings.format(
                "%.2f", sliderPrioridad.valueProperty()
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
        sliderPrioridad.setValue(tareaSelected.getUrgencia() * tareaSelected.getUrgencia());
        sliderUrgencia.setValue(tareaSelected.getUrgencia());
        sliderAvance.setValue(tareaSelected.getPorcentajeAvance());
        showDetailsOfSelectedProject();
    }

    private  void clearTaskDetailsInPane(boolean status){
        taskPane.setVisible(!status);
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

        hboxTitulos.getChildren().addAll(taskTitleLabel,sep);
        Label estado = new Label("Importancia: " + tarea.getImportancia().toString() +  "     %"+ tarea.getPorcentajeAvance()) ;
        hBoxEstado.getChildren().add(estado);
        hBox = setStyleByPercent(hBox, tarea.getPorcentajeAvance());
        vBox.getChildren().addAll(hboxTitulos,hBoxEstado);
        hBox.getChildren().addAll(vBox);
        hBox.setOnMouseClicked(taskOnClick);
        hBox.setAlignment(Pos.CENTER);

        return  hBox;

    }
    private HBox setStyleByPercent(HBox box, long percent){

        if(percent<=25)  box.getStyleClass().add("hboxRedTask");
        if(percent>25 && percent <=50)  box.getStyleClass().add("hboxOrangeTask");
        if(percent>50 && percent <=75)  box.getStyleClass().add("hboxYellowTask");
        if(percent>75)  box.getStyleClass().add("hboxGreenTask");

        return box;

    }
    private HBox makeBasicHbox(String title){
        HBox hBox = new HBox();
        HBox separator = new HBox();
        HBox.setHgrow(separator, Priority.ALWAYS);
        hBox.setAlignment(Pos.CENTER);
        Label taskTitleLabel = new Label(title);
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

        }else{
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Información", this.getStage(), "No se pudieron cargar los proyectos");
        }

    }

    private void  showProjects(){
        root.getChildren().clear();
        for (ProyectoDTO projet:projects){
           TreeItem p =  makeProjectBranch(projet,root);
            System.out.println("make projint");
           if(projet.getTareas()!=null) projet.getTareas().forEach(k ->makeTaskBranch(k,p));
        }
    }

    private  void projectEdition(boolean status){
        clearTaskDetailsInPane(true);
        isEditionProjectMode = status;
       editButton.setVisible(!status);
       txtObjective.setEditable(status);
       txtProjectName.setEditable(status);
       updateProjectButton.setVisible(status);
       cancelEdtionProjectButton.setVisible(status);

    }

    private  void taskEdition(boolean status){
        txtDescripcion.setEditable(status);
        dateInicio.setDisable(!status);
        dateInicio.setStyle("-fx-opacity: 1");
        dateInicio.getEditor().setStyle("-fx-opacity: 1");;
        dateFinal.setStyle("-fx-opacity: 1");
        dateFinal.getEditor().setStyle("-fx-opacity: 1");
        dateFinal.setDisable(!status);
        sliderImportancia.setDisable(!status);
        //sliderPrioridad.setDisable(!status);
        sliderUrgencia.setDisable(!status);
        sliderAvance.setDisable(!status);
        editButton.setVisible(!status);
        deleteButton.setVisible(!status);
        updateButton.setVisible(status);
        cancelEdtionTaskButton.setVisible(status);

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("entra");
        root.setExpanded(true);
        treeViewProjects.setRoot(root);
        treeViewProjects.setShowRoot(false);
        bindProperties();
        loadProjects();
        showProjects();
        taskEdition(false);
        projectEdition(false);

    }

    public void addNewtask(){
        System.out.println("Refresca");
        TareaDTO newtask = (TareaDTO) AppContext.getInstance().get("taskCreated");
        if(newtask!=null){
            projects.stream().filter(k -> projectSelected.getId() == k.getId()).findFirst().get().getTareas().add(newtask);
            showProjects();
        }
    }

    private TareaDTO updateInputDataToSelectedTask(){

        TareaDTO updatedTask = tareaSelected;

        updatedTask.setDescripcion(txtDescripcion.getText());
        System.out.println(dateFinal.getValue());
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

    private void updateTaskInList(TareaDTO tarea){
        sliderPrioridad.setValue(tarea.getUrgencia() * tarea.getUrgencia());
        projects.stream().filter(k -> projectSelected.getId() == k.getId()).findFirst().get().updateTask(tarea);
    }

    private void updateProjectInList(ProyectoDTO proyectoDTO){
        projects.forEach(k -> {
            if(k.getId() == proyectoDTO.getId()) k = proyectoDTO;
        });
        showProjects();
    }


    @Override
    public void initialize() {


    }


    public void deleteSelectedTaskOnAction(ActionEvent actionEvent) {

    }

    public void editSelectedTaskOnAction(ActionEvent actionEvent) {
        taskEdition(true);
    }

    public   void addNewProject(){

        ProyectoDTO proyectoDTO = (ProyectoDTO) AppContext.getInstance().get("createProject");
        System.out.println(proyectoDTO.getId());
        projects.add(proyectoDTO);
        showProjects();


    }

    public void newProjectOnAction(ActionEvent actionEvent) {

        AppContext.getInstance().set("proyectosController", this);
        FlowController.getInstance().goViewInWindowModal("CrearProyecto", this.getStage(), false);

    }

    public void updateTaskOnAction(ActionEvent actionEvent) {

        Respuesta respuesta = new TareaService().update(updateInputDataToSelectedTask());
        if(respuesta.getEstado()){
            tareaSelected = (TareaDTO) respuesta.getResultado("data");
            updateTaskInList(tareaSelected);
            showProjects();
        }
        taskEdition(false);

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
        projectEdition(false);
    }

    public void updateProjectButtonOnAction(ActionEvent actionEvent) {
        updateProjectFromInputData();
    }

    public void cancelEdtionTaskOnAction(ActionEvent actionEvent) {
        taskEdition(false);
    }
}
