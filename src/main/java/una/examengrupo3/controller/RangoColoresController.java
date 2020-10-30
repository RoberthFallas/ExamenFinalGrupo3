package una.examengrupo3.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.Chart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import una.examengrupo3.model.Rango;
import una.examengrupo3.util.AppContext;

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
    ObservableList observableList = FXCollections.observableArrayList();
    ObservableList<PieChart.Data> pieChartData;
    List<Rango> rangos = new ArrayList<>();

    int porcentajeRestante = 100;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void initialize() {
        PieChart.Data pieChartNoAsigned = new PieChart.Data("No asignado " + porcentajeRestante + "%", porcentajeRestante);
        observableList.add(0, pieChartNoAsigned);

        pieChartColor.setData(observableList);
        pieChartNoAsigned.getNode().setStyle("-fx-pie-color:#968f8f;");
    }

    public void addRangeOnAction(ActionEvent actionEvent) {

        Rango rango = new Rango(Long.valueOf(txtStart.getText()), Long.valueOf(txtHasta.getText()), toRGBCode(colorPicker.getValue()));
        porcentajeRestante -= rango.getPercentInRange()+1;;
        rangos.add(rango);
        String style = "-fx-pie-color:" + toRGBCode(colorPicker.getValue()) + ";";
        PieChart.Data pieChart = new PieChart.Data(rango.getRangoInWords(), rango.getPercentInRange());
        observableList.add(pieChart);
        pieChart.getNode().setStyle(style);

        System.out.println(toRGBCode(colorPicker.getValue()));
        observableList.remove(0);


        pieChartColor.setData(observableList);

        if(porcentajeRestante!=-1){
            PieChart.Data pieChartNoAsigned = new PieChart.Data("No asignado " + porcentajeRestante + "%", porcentajeRestante);
            observableList.add(0, pieChartNoAsigned);
            pieChartNoAsigned.getNode().setStyle("-fx-pie-color:#FFFFFF;");
            txtStart.setText(String.valueOf(rango.getHasta()+1));
            txtHasta.setText("");

        }else {
            txtStart.setText("100");
            txtHasta.setEditable(false);
        }

    }


    public  void updateChartLegendColorFromItemName(PieChart chart, String itemToUpdate, Color legendColor){
        Set<Node> legendItems = chart.lookupAll("Label.chart-legend-item");
        if(legendItems.isEmpty()){ return; }
        String styleString = "-fx-background-color:" + toRGBCode(legendColor) + ";";
        for(Node legendItem : legendItems){
            Label legendLabel = (Label) legendItem;
            Node legend = legendLabel.getGraphic(); //The legend icon (circle by default)

            if(legend != null && legendLabel.getText().equals(itemToUpdate)){
                legend.setStyle(styleString);
            }
        }
    }

    public  String toRGBCode( Color color )
    {
        return String.format( "#%02X%02X%02X",
                (int)( color.getRed() * 255 ),
                (int)( color.getGreen() * 255 ),
                (int)( color.getBlue() * 255 ) );
    }

    public void saveOnAction(ActionEvent actionEvent) {

        ProyectosController controller = (ProyectosController) AppContext.getInstance().get("proyectosController");
        AppContext.getInstance().set("rangos", rangos);
        controller.updateRangeColors();
        this.getStage().close();


    }
}
