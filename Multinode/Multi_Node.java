package application.javaarduino;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import eu.hansolo.medusa.skins.SimpleDigitalSkin;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import jssc.SerialPort;
import static jssc.SerialPort.MASK_RXCHAR;
import jssc.SerialPortEvent;
import jssc.SerialPortException;
import jssc.SerialPortList;
import eu.hansolo.medusa.Gauge;

public class Main extends Application {

    Gauge gauge = new Gauge();
    Gauge gauge1 = new Gauge();

    SerialPort arduinoPort = null;
    ObservableList<String> portList;

    Label labelValue;
    final int NUM_OF_POINT = 50;
    XYChart.Series series;

    private void detectPort(){

        portList = FXCollections.observableArrayList();

        String[] serialPortNames = SerialPortList.getPortNames();
        for(String name: serialPortNames){
            System.out.println(name);
            portList.add(name);
        }
    }

    @Override
    public void start(Stage primaryStage) {

        labelValue = new Label();
        labelValue.setFont(new Font("Arial", 28));

        detectPort();
        final ComboBox comboBoxPorts = new ComboBox(portList);
        comboBoxPorts.valueProperty()
                .addListener(new ChangeListener<String>() {

                    @Override
                    public void changed(ObservableValue<? extends String> observable,
                                        String oldValue, String newValue) {

                        System.out.println(newValue);
                        disconnectArduino();
                        connectArduino(newValue);
                    }
                });

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time");
        yAxis.setLabel("count");

        final LineChart<Number,Number> lineChart =
                new LineChart<>(xAxis,yAxis);
        lineChart.setTitle("Arduino Real time data");

        series = new XYChart.Series();
        lineChart.setLegendVisible(false);
        lineChart.getData().add(series);
        lineChart.setAnimated(false);
        lineChart.setCreateSymbols(false);

        for(int i=0; i<NUM_OF_POINT; i++){
            series.getData().add(new XYChart.Data(i, 0));
        }

        double x = 0, y = 0;

        gauge.setSkin(new SimpleDigitalSkin(gauge));
        gauge.setMinValue(0.00);
        gauge.setMaxValue(255.00);
        gauge.setMaxHeight(100000);
        gauge.setMaxWidth(100000);

        gauge1.setMinValue(0.00);
        gauge1.setMaxValue(1023.00);
        gauge1.setMaxHeight(100000);
        gauge1.setMaxWidth(100000);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(
                comboBoxPorts, lineChart, gauge, gauge1);

        StackPane root = new StackPane();
        root.getChildren().add(vBox);

        Scene scene = new Scene(root, 500, 400);

        primaryStage.setTitle("Speedometer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void shiftSeriesData(float newValue)
    {
        for(int i=0; i<NUM_OF_POINT-1; i++){
            XYChart.Data<String, Number> ShiftDataUp =
                    (XYChart.Data<String, Number>)series.getData().get(i+1);
            Number shiftValue = ShiftDataUp.getYValue();
            XYChart.Data<String, Number> ShiftDataDn =
                    (XYChart.Data<String, Number>)series.getData().get(i);
            ShiftDataDn.setYValue(shiftValue);
        }
        XYChart.Data<String, Number> lastData =
                (XYChart.Data<String, Number>)series.getData().get(NUM_OF_POINT-1);
        lastData.setYValue(newValue);
    }

    public boolean connectArduino(String port){



        System.out.println("Arduino is connected.");

        boolean success = false;
        SerialPort serialPort = new SerialPort(port);
        try {
            serialPort.openPort();
            serialPort.setParams(
                    SerialPort.BAUDRATE_9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            serialPort.setEventsMask(MASK_RXCHAR);
            serialPort.addEventListener((SerialPortEvent serialPortEvent) -> {
                if(serialPortEvent.isRXCHAR()){
                    try {

                        byte[] b = serialPort.readBytes();
//                      int value = b[0] & 0xff;
                        int value = b[0];
                        int value1 = value*(1023/255);
                        String st = String.valueOf(value1);
                        System.out.println(st);
                        int term = b[2];
                        int term1 = term*(1023/255);
                        String st1 = String.valueOf(term1);
                        System.out.println(st1);
                        System.out.println();

                        //int[] i = serialPort.readIntArray();
//                      String st = String.valueOf(value);
                        System.out.println(st);




                        Platform.runLater(() -> {
                            //labelValue.setText(st);

//                            try {
//                                excel(st);
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            for(int j =0;j<10;j++){
//                                int value = i[j];
//                                String st = String.valueOf(value);
//                                System.out.println(st);
                                gauge.setValue(Double.parseDouble(st));
                            //}
                            gauge1.setValue(Double.parseDouble(st1));

                            //shiftSeriesData((float)value1);
                        });
                    } catch (SerialPortException ex) {
                        Logger.getLogger(Main.class.getName())
                                .log(Level.SEVERE, null, ex);
                    }
                }
            });

            arduinoPort = serialPort;
            success = true;
        } catch (SerialPortException ex) {
            Logger.getLogger(Main.class.getName())
                    .log(Level.SEVERE, null, ex);
            System.out.println("SerialPortException: " + ex.toString());
        }

        return success;
    }


    public void disconnectArduino(){

        System.out.println("Arduino is disconnected.");
        if(arduinoPort != null){
            try {
                arduinoPort.removeEventListener();

                if(arduinoPort.isOpened()){
                    arduinoPort.closePort();
                }

            } catch (SerialPortException ex) {
                Logger.getLogger(Main.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void stop() throws Exception {
        disconnectArduino();
        super.stop();
    }

//    public void excel(String count) throws IOException {
//        String filename = "/Users/mayank/Desktop/ExcelCount.xlsx";
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        XSSFSheet sheet = workbook.createSheet("Arduino Count");
//
//        for (int i = 0; i < 1; i++) {
//            XSSFRow rowhead = sheet.createRow(i);
//            for (int j = 0; j < 1; j++) {
//                rowhead.createCell(j).setCellValue(count);
//            }
//        }
//        FileOutputStream fileOut = null;
//        try {
//            fileOut = new FileOutputStream(filename);
//            workbook.write(fileOut);
//            fileOut.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("The excel file has been updated.");
//
//    }

    public static void main(String[] args) {launch(args);}
}
