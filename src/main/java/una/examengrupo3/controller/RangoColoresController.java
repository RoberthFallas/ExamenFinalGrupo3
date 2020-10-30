package una.examengrupo3.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.Chart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import una.examengrupo3.model.Rango;
import una.examengrupo3.util.AppContext;
import una.examengrupo3.util.Mensaje;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class RangoColoresController  extends  Controller implements Initializable {
    public PieChart pieChartColor;
    public TextField txtStart;
    public TextField txtHasta;
    public ColorPicker colorPicker;
    public Button addRankButton;
    public Pane paneEdit;
    public Button editButton;
    public Button closeButton;
    public Button cancelButton;
    public Button saveButton;
    ObservableList observableList = FXCollections.observableArrayList();
    ObservableList<PieChart.Data> pieChartData;
    boolean editionMode = false;
    List<Rango> rangos ;
    List<Rango> newRanks = new ArrayList<>();

    int porcentajeRestante = 100;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private  void loadRangos(){
        rangos = (List<Rango>) AppContext.getInstance().get("rangos");
        observableList.clear();

        if(rangos!=null){
            for (Rango r:rangos) {
                makePieChart(r);
            }
        }

    }

    private void   makePieChart(Rango rango){
        String style = "-fx-pie-color:" + rango.getColor() + ";";
        PieChart.Data pieChart = new PieChart.Data(rango.getRangoInWords(), rango.getPercentInRange());
        observableList.add(pieChart);
        pieChartColor.setData(observableList);
        pieChart.getNode().setStyle(style);

    }

    private void makeNoAsignedPieChart(){
        if(!observableList.isEmpty()) observableList.remove(0);
        long porcentaje = porcentajeRestante+1;
        PieChart.Data pieChartNoAsigned = new PieChart.Data("No asignado " +porcentaje + "%", porcentajeRestante);
        observableList.add(0, pieChartNoAsigned);
        pieChartNoAsigned.getNode().setStyle("-fx-pie-color:#FFFFFF;");
    }

    @Override
    public void initialize() {
        loadRangos();
        editionMode(false);
    }

    private void editionMode(boolean status){
        txtStart.setText("0");
        txtHasta.setText("");
        if(status) {
            observableList.clear();
            makeNoAsignedPieChart();
            newRanks.clear();
        }
        txtHasta.setEditable(status);
        addRankButton.setDisable(!status);
        editionMode = true;
        saveButton.setVisible(status);
        cancelButton.setVisible(status);
        colorPicker.setDisable(!status);
        editButton.setVisible(!status);


    }
    public void addRankOnAction(ActionEvent actionEvent) {

        if(isValidData()){
            Rango rango = new Rango(Long.valueOf(txtStart.getText()), Long.valueOf(txtHasta.getText()), toRGBCode(colorPicker.getValue()));

            porcentajeRestante -= rango.getPercentInRange()+1;;
            newRanks.add(rango);
            makePieChart(rango);
            System.out.println(porcentajeRestante);
            if(porcentajeRestante>0){
                System.out.println("Entra");
                makeNoAsignedPieChart();
                txtStart.setText(String.valueOf(rango.getHasta()+1));
                txtHasta.setText("");

            }else {

                observableList.remove(0);
                txtStart.setText("100");
                txtHasta.setEditable(false);
            }

        }
    }

    private boolean isValidData(){

        try {
            if(Long.parseLong(txtHasta.getText())>100 || Long.parseLong(txtHasta.getText())< Long.parseLong(txtStart.getText())){
                new Mensaje().show(Alert.AlertType.ERROR, "Información no válida", "El dato ingresado no es válido. Ingrese un número entre "+txtStart.getText() + " y 100");
                return  false;
            }
            return true;
        }catch (NumberFormatException exception){
            new Mensaje().show(Alert.AlertType.ERROR, "Información no válida", "Se esperaba el ingreso de un número");
        }

        return false;
    }


    public  String toRGBCode( Color color )
    {
        return String.format( "#%02X%02X%02X",
                (int)( color.getRed() * 255 ),
                (int)( color.getGreen() * 255 ),
                (int)( color.getBlue() * 255 ) );
    }

    public void saveOnAction(ActionEvent actionEvent) {

        if(editionMode && !newRanks.isEmpty() && porcentajeRestante == -1){
            ProyectosController controller = (ProyectosController) AppContext.getInstance().get("proyectosController");
            AppContext.getInstance().set("rangos", newRanks);
            controller.updateRangeColors();
            rangos = List.copyOf(newRanks);
            porcentajeRestante = 100;
            editionMode(false);

        }else {
             new Mensaje().show( Alert.AlertType.WARNING,"Información", "Aún queda porcentaje no asignado, no se puede guardar hasta que se asigne un color a todos los pocentajes de 0% a 100%");
        }

    }

    public void editButtonOnAction(ActionEvent actionEvent) {

         editionMode(true);
    }


    public void cancelEdtionOnAction(ActionEvent actionEvent) {
        if (editionMode && porcentajeRestante > 1) {
            boolean confirmation = new Mensaje().showConfirmation("Alerta", this.getStage(), "Aún queda porcentaje no asignado, si cancela la edición se perderán los nuevos rangos ingresados, ¿Desea cancelar de todas maneras?").booleanValue();
            if (confirmation) {
                editionMode = false;
                loadRangos();
            }
        }

    }

    public void closeButtonOnAction(ActionEvent actionEvent) {
        this.getStage().close();
    }
}
