package com.example.telemetry_01;

import eu.hansolo.medusa.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import jssc.*;

import static jssc.SerialPort.MASK_RXCHAR;

import java.io.*;

import eu.hansolo.medusa.skins.*;

public class HelloApplication extends Application {

    BorderPane layout = new BorderPane(); //Creating a BorderPane as the base pane, on top of which different panes have been placed.

    final int NUM_OF_POINT = 75; //Set the limit of points visible in the line chart at once.
    XYChart.Series series1; //Construct the 1st Series for the 1st line chart.
    XYChart.Series series2; //Construct the 2nd Series for the 2nd line chart.
    XYChart.Series series3; //Construct the 1st Series for the 1st line chart.
    XYChart.Series series4;

    Gauge gauge1 = new Gauge(); //Initialize all the gauges.
    Gauge gauge2 = new Gauge();
    Gauge gauge3 = new Gauge();
    Gauge gauge4 = new Gauge();
    Gauge gauge6 = new Gauge();
    Gauge gauge5 = GaugeBuilder.create()
            .scaleDirection(Gauge.ScaleDirection.COUNTER_CLOCKWISE)
            .tickLabelLocation(TickLabelLocation.OUTSIDE)
            .startAngle(0)
            .angleRange(270)
            .minValue(-1)
            .maxValue(2)
            .zeroColor(Color.ORANGE)
            .majorTickMarkType(TickMarkType.TRIANGLE)
            .build();
  
    Gauge gauge7 = new Gauge();
    Gauge gauge8 = new Gauge();
    Gauge gauge9 = new Gauge();
    Gauge gauge10 = new Gauge();
    Gauge gauge11 = new Gauge();
    Gauge gauge12 = new Gauge();
    Gauge gauge14 = new Gauge();
    Gauge digital_gauge = new Gauge();

    SerialPort XBeePort = null; //Initializing the serial port of XBee, and setting it null.
    ObservableList<String> portList; //Initialing the port list.

    private void detectPort() { //Method to detect all serial ports connected.
        portList = FXCollections.observableArrayList();
        String[] serialPortNames = SerialPortList.getPortNames();
        for (String name : serialPortNames) {
            System.out.println(name);
            portList.add(name);
        }
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException { //Main method.

        detectPort();
        final ComboBox comboBoxPorts = new ComboBox(portList); //Creating a new ComboBox to display the serial ports.
        comboBoxPorts.valueProperty().addListener( //Code to check for active serial ports.
                (ChangeListener<String>) (observable, oldValue, newValue) -> {
                    System.out.println(newValue);
                    disconnectXBee();
                    connectXBee(newValue);
                }
        );

        final NumberAxis xAxis1 = new NumberAxis(); //Initializing the x axis of the first line chart.
        final NumberAxis yAxis1 = new NumberAxis(); //Initializing the y axis of the first line chart.
        final NumberAxis xAxis2 = new NumberAxis(); //Initializing the x axis of the second line chart.
        final NumberAxis yAxis2 = new NumberAxis(); //Initializing the y axis of the second line chart.
        xAxis1.setLabel("Time"); //Setting the name of the x axis of the fist line chart.
        yAxis1.setLabel("Value"); //Setting the name of the y axis of the fist line chart.
        xAxis2.setLabel("Time"); //Setting the name of the x axis of the second line chart.
        yAxis2.setLabel("Value"); //Setting the name of the y axis of the second line chart.
        final LineChart<Number, Number> lineChart1 = new LineChart<>(xAxis1, yAxis1); //Initializing the first line chart.
        final LineChart<Number, Number> lineChart2 = new LineChart<>(xAxis2, yAxis2); //Initializing the second line chart.
        series1 = new XYChart.Series(); //Initialing series for first line chart.
        series1.setName("TPS"); //Naming the series for the first line chart as RPM.
        series2 = new XYChart.Series(); //Initialing series for second line chart.
        series2.setName("BPS"); //Naming the series for the second line chart as OPS.
        lineChart1.getData().add(series1); //Adding series1 to the first line chart.
        lineChart2.getData().add(series2); //Adding series2 to the second line chart.
        lineChart1.setAnimated(false); //Disabling animation for the first line chart.
        lineChart2.setAnimated(false); //Disabling animation for the second line chart.
        lineChart1.setCreateSymbols(false); //Hiding data point symbols in the first line chart.
        lineChart2.setCreateSymbols(false); //Hiding data point symbols in the second line chart.

        final NumberAxis xAxis3 = new NumberAxis(); //Initializing the x axis of the first line chart.
        final NumberAxis yAxis3 = new NumberAxis(); //Initializing the y axis of the first line chart.
        final NumberAxis xAxis4 = new NumberAxis(); //Initializing the x axis of the second line chart.
        final NumberAxis yAxis4 = new NumberAxis(); //Initializing the y axis of the second line chart.
        xAxis3.setLabel("Time"); //Setting the name of the x axis of the fist line chart.
        yAxis3.setLabel("Value"); //Setting the name of the y axis of the fist line chart.
        xAxis4.setLabel("Time"); //Setting the name of the x axis of the second line chart.
        yAxis4.setLabel("Value"); //Setting the name of the y axis of the second line chart.
        final LineChart<Number, Number> lineChart3 = new LineChart<>(xAxis3, yAxis3); //Initializing the first line chart.
        final LineChart<Number, Number> lineChart4 = new LineChart<>(xAxis4, yAxis4); //Initializing the second line chart.
        series3 = new XYChart.Series(); //Initialing series for first line chart.
        series3.setName("RPM"); //Naming the series for the first line chart as RPM.
        series4 = new XYChart.Series(); //Initialing series for second line chart.
        series4.setName("WS Avg"); //Naming the series for the second line chart as OPS.
        lineChart3.getData().add(series3); //Adding series1 to the first line chart.
        lineChart4.getData().add(series4); //Adding series2 to the second line chart.
        lineChart3.setAnimated(false); //Disabling animation for the first line chart.
        lineChart4.setAnimated(false); //Disabling animation for the second line chart.
        lineChart3.setCreateSymbols(false); //Hiding data point symbols in the first line chart.
        lineChart4.setCreateSymbols(false); //Hiding data point symbols in the second line chart.


        for (int i = 0; i < NUM_OF_POINT; i++) {
            series1.getData().add(new XYChart.Data(i, 0)); //Preload the first line chart with 0.
            series2.getData().add(new XYChart.Data(i, 0)); //Preload the second line chart with 0.
            series3.getData().add(new XYChart.Data(i, 0));
            series4.getData().add(new XYChart.Data(i, 0));
        }

        Screen screen = Screen.getPrimary(); //Creating a screen.
        Rectangle2D bounds = screen.getVisualBounds(); //Getting dimensions of the user's screen.


        gauge1.setTitle("RPM"); //Gauge 1 settings
        gauge1.setMinValue(0.00);
        gauge1.setMaxValue(14000.00);
        gauge1.setValueVisible(true);
        gauge1.setMinorTickMarksVisible(false);
        gauge1.setMediumTickMarksVisible(true);
        gauge1.setPrefSize(200, 200);
        gauge1.setMediumTickMarkLengthFactor(0.25);
        gauge1.setSectionsVisible(true);
        gauge1.setMediumTickMarkColor(Color.valueOf("#00000050"));
        gauge1.setSections(new Section(12000, 14000, Color.rgb(255, 1, 1)));
        gauge1.setSectionTextVisible(true);
        gauge1.setMajorTickSpace(1000);
        gauge1.setMajorTickMarksVisible(true);

        digital_gauge.setTitle("Gear");
        digital_gauge.setAngleRange(120);
        digital_gauge.setSkinType(Gauge.SkinType.HORIZONTAL);
        digital_gauge.setForegroundBaseColor(Color.BLACK);
        digital_gauge.setKnobColor(Color.BLACK);
        digital_gauge.setMaxValue(6.0);
        digital_gauge.setPrefSize(200, 200);
        digital_gauge.setValueVisible(true);
        digital_gauge.setSectionsVisible(true);
        digital_gauge.setSections(new Section(4.5, 6, Color.rgb(255, 1, 1)));
        digital_gauge.setMinorTickMarksVisible(false);
        digital_gauge.setMediumTickMarksVisible(false);
        digital_gauge.setMajorTickMarkType(TickMarkType.BOX);
        digital_gauge.setTitle("Gear");
        digital_gauge.setNeedleSize(Gauge.NeedleSize.THIN);
        digital_gauge.setNeedleShape(Gauge.NeedleShape.ROUND);
        digital_gauge.setNeedleColor(Color.rgb(255, 61, 10));
        digital_gauge.setAngleRange(90);
        digital_gauge.setSectionTextVisible(true);
        digital_gauge.setCustomTickLabelsEnabled(true);
        digital_gauge.setCustomTickLabels("0", "1", "2", "3", "4", "5", "6");

        gauge2.setTitle("OPS"); //Gauge 2 settings
        gauge2.setMinValue(0.00);
        gauge2.setMaxValue(103.00);
        gauge2.setSkin(new LinearSkin(gauge2));
        gauge2.setOrientation(Orientation.VERTICAL);


        gauge3.setTitle("ECT 1"); //Gauge 3 settings
        gauge3.setMinValue(0.00);
        gauge3.setMaxValue(103.00);
        gauge3.setSkin(new LinearSkin(gauge3));
        gauge3.setOrientation(Orientation.HORIZONTAL);

        gauge4.setTitle("ECT 2"); //Gauge 4 settings
        gauge4.setMinValue(0.00);
        gauge4.setMaxValue(123.00);
        gauge4.setSkin(new LinearSkin(gauge4));
        gauge4.setOrientation(Orientation.HORIZONTAL);

        gauge8.setTitle("Speed"); //Gauge 8 settings
        gauge8.setMinValue(0.00);
        gauge8.setMaxValue(150.00);
        gauge8.setTickLabelsVisible(false);

        gauge9.setTitle("TPS"); //Gauge 9 settings
        gauge9.setSkin(new LinearSkin(gauge9));
        gauge9.setOrientation(Orientation.HORIZONTAL);
        gauge9.setMinValue(0.00);
        gauge9.setMaxValue(100.00);
        gauge9.setSectionsVisible(true);
        gauge9.setSections(new Section(50, 100, Color.rgb(255, 1, 1)));

        gauge12.setTitle("BPS"); //Gauge 12 settings
        gauge12.setMinValue(0.00);
        gauge12.setMaxValue(100.00);
        gauge12.setSkin(new LinearSkin(gauge12));
        gauge12.setOrientation(Orientation.HORIZONTAL);
        gauge12.setSectionsVisible(true);
        gauge12.setSections(new Section(50, 100, Color.rgb(255, 1, 1)));


        gauge11.setTitle("FPS"); //Gauge 11 settings
        gauge11.setMinValue(0.00);
        gauge11.setMaxValue(123.00);
        gauge11.setSkin(new LinearSkin(gauge11));
        gauge11.setOrientation(Orientation.VERTICAL);

        gauge14.setTitle("Voltage"); //Gauge 14 settings
        gauge14.setMinValue(0.00);
        gauge14.setMaxValue(102.00);
        gauge14.setSkin(new BatterySkin(gauge14));
        gauge14.setValueVisible(true);
        gauge14.setOrientation(Orientation.HORIZONTAL);


        gauge14.setNeedleColor(Color.BLACK);
        gauge14.setUnitColor(Color.BLACK);
        gauge14.setValueColor(Color.BLACK);
        gauge14.setForegroundBaseColor(Color.BLACK);

        gauge12.setUnitColor(Color.BLACK);
        gauge12.setValueColor(Color.BLACK);
        gauge12.setForegroundBaseColor(Color.BLACK);

        gauge11.setNeedleColor(Color.BLACK);
        gauge11.setUnitColor(Color.BLACK);
        gauge11.setValueColor(Color.BLACK);
        gauge11.setForegroundBaseColor(Color.BLACK);

        gauge9.setUnitColor(Color.BLACK);
        gauge9.setValueColor(Color.BLACK);
        gauge9.setForegroundBaseColor(Color.BLACK);

        gauge8.setNeedleColor(Color.BLACK);
        gauge8.setUnitColor(Color.BLACK);
        gauge8.setValueColor(Color.BLACK);
        gauge8.setForegroundBaseColor(Color.BLACK);

        gauge4.setNeedleColor(Color.BLACK);
        gauge4.setUnitColor(Color.BLACK);
        gauge4.setValueColor(Color.BLACK);
        gauge4.setForegroundBaseColor(Color.BLACK);

        gauge3.setNeedleColor(Color.BLACK);
        gauge3.setUnitColor(Color.BLACK);
        gauge3.setValueColor(Color.BLACK);
        gauge3.setForegroundBaseColor(Color.BLACK);

        gauge2.setNeedleColor(Color.BLACK);
        gauge2.setUnitColor(Color.BLACK);
        gauge2.setValueColor(Color.BLACK);
        gauge2.setForegroundBaseColor(Color.BLACK);

        Label voltage = new Label();
        voltage.setText("Voltage: 0.0");
        voltage.getPrefHeight();
        voltage.getPrefWidth();
        voltage.setFont(new Font("Arial", 42));
        voltage.setAlignment(Pos.CENTER);

        Button button = new Button();
        button.setText("Line Chart");

        Button button_Gauge = new Button();
        button_Gauge.setText("Gauge");

        Button LightMode = new Button();
        LightMode.setText("Light Mode");

        Button DarkMode = new Button();
        DarkMode.setText("Dark Mode");

        GridPane gridpane0 = new GridPane();
        gridpane0.setPrefHeight(bounds.getHeight() / 30);

        GridPane gridpane1 = new GridPane(); //Creating a GridPane for the gauges.
        gridpane1.setPrefHeight(bounds.getHeight() / 2);

        GridPane gridpane2 = new GridPane(); //Creating a GridPane for the line charts.

        gridpane2.setPrefHeight(bounds.getHeight() / 3); //Set the height of the line chart's grid pane 1/3rd of the screen's height.

        ColumnConstraints column0 = new ColumnConstraints(bounds.getWidth() / 8);
        column0.setHgrow(Priority.ALWAYS);
        ColumnConstraints column1 = new ColumnConstraints(bounds.getWidth() / 8);
        column1.setHgrow(Priority.ALWAYS);
        ColumnConstraints column2 = new ColumnConstraints(bounds.getWidth() / 8);
        column2.setHgrow(Priority.ALWAYS);
        ColumnConstraints column3 = new ColumnConstraints(bounds.getWidth() / 8);
        column3.setHgrow(Priority.ALWAYS);
        ColumnConstraints column4 = new ColumnConstraints(bounds.getWidth() / 8);
        column4.setHgrow(Priority.ALWAYS);
        ColumnConstraints column5 = new ColumnConstraints(bounds.getWidth() / 8);
        column5.setHgrow(Priority.ALWAYS);
        ColumnConstraints column6 = new ColumnConstraints(bounds.getWidth() / 8);
        column6.setHgrow(Priority.ALWAYS);
        ColumnConstraints column7 = new ColumnConstraints(bounds.getWidth() / 8);
        column7.setHgrow(Priority.ALWAYS);

        ColumnConstraints columna = new ColumnConstraints(bounds.getWidth() / 6);
        columna.setHgrow(Priority.ALWAYS);
        ColumnConstraints columnb = new ColumnConstraints(bounds.getWidth() / 6);
        columnb.setHgrow(Priority.ALWAYS);
        ColumnConstraints columnc = new ColumnConstraints(bounds.getWidth() / 6);
        columnc.setHgrow(Priority.ALWAYS);
        ColumnConstraints columnd = new ColumnConstraints(bounds.getWidth() / 6);
        columnd.setHgrow(Priority.ALWAYS);
        ColumnConstraints columne = new ColumnConstraints(bounds.getWidth() / 6);
        columne.setHgrow(Priority.ALWAYS);
        ColumnConstraints columnf = new ColumnConstraints(bounds.getWidth() / 6);
        columnf.setHgrow(Priority.ALWAYS);

        RowConstraints rowa = new RowConstraints(bounds.getHeight());
        rowa.setVgrow(Priority.ALWAYS);

        gridpane0.getColumnConstraints().addAll(columna, columnb, columnc, columnd, columne);
        gridpane0.setPadding(new Insets(0, 0, 5, 0));
        gridpane0.add(comboBoxPorts, 0, 0);
        gridpane0.setHalignment(comboBoxPorts, HPos.CENTER);
        gridpane0.add(button, 2, 0);
        gridpane0.setHalignment(button, HPos.CENTER);
        gridpane0.add(button_Gauge, 1, 0);
        gridpane0.setHalignment(button_Gauge, HPos.CENTER);
        gridpane0.add(LightMode, 3, 0);
        gridpane0.setHalignment(LightMode, HPos.CENTER);
        gridpane0.add(DarkMode, 4, 0);
        gridpane0.setHalignment(DarkMode, HPos.CENTER);

        gridpane1.getColumnConstraints().addAll(column0, column1, column2, column3, column4, column5, column6, column7);

        gridpane1.add(gauge9, 0, 0, 4, 1);
        gridpane1.add(gauge12, 0, 1, 4, 1);
        gridpane1.add(gauge3, 0, 2, 2, 1);
        gridpane1.add(gauge8, 4, 0, 2, 2);
        gridpane1.add(gauge4, 0, 3, 2, 1);
        gridpane1.add(gauge11, 6, 2, 1, 2);
        gridpane1.add(gauge2, 7, 2, 1, 2);
        gridpane1.add(voltage, 2, 3, 2, 2);
        gridpane1.setHalignment(voltage, HPos.CENTER);
        gridpane1.add(gauge1, 6, 0, 2, 2);
        gridpane1.add(gauge14, 2, 2, 2, 1);
        gridpane1.add(digital_gauge, 4, 2, 2, 2);

        RowConstraints row00 = new RowConstraints(bounds.getHeight() * 0.445);
        row00.setVgrow(Priority.ALWAYS);
        RowConstraints row01 = new RowConstraints(bounds.getHeight() * 0.445);
        row01.setVgrow(Priority.ALWAYS);
        ColumnConstraints column00 = new ColumnConstraints(bounds.getWidth() * 0.49);
        column00.setHgrow(Priority.ALWAYS);
        ColumnConstraints column1i = new ColumnConstraints(bounds.getWidth() * 0.49);
        column1i.setHgrow(Priority.ALWAYS);

        gridpane2.getRowConstraints().addAll(row00, row01);
        gridpane2.getColumnConstraints().addAll(column00, column1i);

        gridpane2.add(lineChart1, 0, 0);
        gridpane2.add(lineChart2, 1, 0);
        gridpane2.add(lineChart3, 0, 1);
        gridpane2.add(lineChart4, 1, 1);

        FileInputStream imageStream = new FileInputStream("/Users/mayank/Documents/Work/Codes/Java codes/Telemetry_01/src/main/java/com/example/telemetry_01/FM_Logo trans white.png");
        Image image = new Image(imageStream);
        ImageView imageView = new ImageView(image);
        imageView.setX(170);
        imageView.setY(10);
        imageView.setFitWidth(150);
        imageView.setPreserveRatio(true);
        gridpane0.add(imageView, 5, 0);
        gridpane0.setHalignment(imageView, HPos.RIGHT);

        FileInputStream imageStream1 = new FileInputStream("/Users/mayank/Documents/Work/Codes/Java codes/Telemetry_01/src/main/java/com/example/telemetry_01/Transperant logo.png");
        Image image1 = new Image(imageStream1);

        layout.setTop(gridpane0);
        layout.setCenter(gridpane1);

        button.setOnAction(e -> layout.setCenter(gridpane2));
        button_Gauge.setOnAction(e -> layout.setCenter(gridpane1));
        LightMode.setOnAction(e -> {
            gridpane0.setBackground(new Background(new BackgroundFill(Color.valueOf("#FFFFFF"), CornerRadii.EMPTY, Insets.EMPTY))); //Set the background colour as dark grey.
            gridpane1.setBackground(new Background(new BackgroundFill(Color.valueOf("#FFFFFF"), CornerRadii.EMPTY, Insets.EMPTY))); //Set the background colour as dark grey.
            gridpane2.setBackground(new Background(new BackgroundFill(Color.valueOf("#FFFFFF"), CornerRadii.EMPTY, Insets.EMPTY))); //Set the background colour as dark grey.

            gauge14.setNeedleColor(Color.BLACK);
            gauge14.setUnitColor(Color.BLACK);
            gauge14.setValueColor(Color.BLACK);
            gauge14.setForegroundBaseColor(Color.BLACK);

            digital_gauge.setForegroundBaseColor(Color.BLACK);

            voltage.setTextFill(Color.valueOf("000000"));

            gauge12.setNeedleColor(Color.BLACK);
            gauge12.setUnitColor(Color.BLACK);
            gauge12.setValueColor(Color.BLACK);
            gauge12.setForegroundBaseColor(Color.BLACK);

            gauge11.setNeedleColor(Color.BLACK);
            gauge11.setUnitColor(Color.BLACK);
            gauge11.setValueColor(Color.BLACK);
            gauge11.setForegroundBaseColor(Color.BLACK);

            gauge9.setNeedleColor(Color.BLACK);
            gauge9.setUnitColor(Color.BLACK);
            gauge9.setValueColor(Color.BLACK);
            gauge9.setForegroundBaseColor(Color.BLACK);

            gauge8.setNeedleColor(Color.BLACK);
            gauge8.setUnitColor(Color.BLACK);
            gauge8.setValueColor(Color.BLACK);
            gauge8.setForegroundBaseColor(Color.BLACK);

            gauge4.setNeedleColor(Color.BLACK);
            gauge4.setUnitColor(Color.BLACK);
            gauge4.setValueColor(Color.BLACK);
            gauge4.setForegroundBaseColor(Color.BLACK);

            gauge3.setNeedleColor(Color.BLACK);
            gauge3.setUnitColor(Color.BLACK);
            gauge3.setValueColor(Color.BLACK);
            gauge3.setForegroundBaseColor(Color.BLACK);

            gauge2.setNeedleColor(Color.BLACK);
            gauge2.setUnitColor(Color.BLACK);
            gauge2.setValueColor(Color.BLACK);
            gauge2.setForegroundBaseColor(Color.BLACK);

            gauge1.setNeedleColor(Color.BLACK);
            gauge1.setMediumTickMarkColor(Color.valueOf("#00000050"));
            gauge1.setUnitColor(Color.BLACK);
            gauge1.setValueColor(Color.BLACK);

        });
        DarkMode.setOnAction(e -> {
            gridpane0.setBackground(new Background(new BackgroundFill(Color.valueOf("#1c1d1f"), CornerRadii.EMPTY, Insets.EMPTY))); //Set the background colour as dark grey.
            gridpane1.setBackground(new Background(new BackgroundFill(Color.valueOf("#1c1d1f"), CornerRadii.EMPTY, Insets.EMPTY))); //Set the background colour as dark grey.
            gridpane2.setBackground(new Background(new BackgroundFill(Color.valueOf("#1c1d1f"), CornerRadii.EMPTY, Insets.EMPTY))); //Set the background colour as dark grey.

            gauge14.setNeedleColor(Color.WHITE);
            gauge14.setUnitColor(Color.WHITE);
            gauge14.setValueColor(Color.WHITE);
            gauge14.setForegroundBaseColor(Color.WHITE);

            digital_gauge.setForegroundBaseColor(Color.WHITE);

            gauge12.setNeedleColor(Color.WHITE);
            gauge12.setUnitColor(Color.WHITE);
            gauge12.setValueColor(Color.WHITE);
            gauge12.setForegroundBaseColor(Color.WHITE);

            voltage.setTextFill(Color.valueOf("FFFFFF"));

            gauge11.setNeedleColor(Color.WHITE);
            gauge11.setUnitColor(Color.WHITE);
            gauge11.setValueColor(Color.WHITE);
            gauge11.setForegroundBaseColor(Color.WHITE);

            gauge9.setNeedleColor(Color.WHITE);
            gauge9.setUnitColor(Color.WHITE);
            gauge9.setValueColor(Color.WHITE);
            gauge9.setForegroundBaseColor(Color.WHITE);

            gauge8.setNeedleColor(Color.WHITE);
            gauge8.setUnitColor(Color.WHITE);
            gauge8.setValueColor(Color.WHITE);
            gauge8.setForegroundBaseColor(Color.WHITE);

            gauge4.setNeedleColor(Color.WHITE);
            gauge4.setUnitColor(Color.WHITE);
            gauge4.setValueColor(Color.WHITE);
            gauge4.setForegroundBaseColor(Color.WHITE);

            gauge3.setNeedleColor(Color.WHITE);
            gauge3.setUnitColor(Color.WHITE);
            gauge3.setValueColor(Color.WHITE);
            gauge3.setForegroundBaseColor(Color.WHITE);

            gauge2.setNeedleColor(Color.WHITE);
            gauge2.setUnitColor(Color.WHITE);
            gauge2.setValueColor(Color.WHITE);
            gauge2.setForegroundBaseColor(Color.WHITE);

            gauge1.setNeedleColor(Color.RED);
            gauge1.setMediumTickMarkColor(Color.valueOf("#FFFFFF50"));
            gauge1.setValueColor(Color.WHITE);
            gauge1.setForegroundBaseColor(Color.WHITE);
        });

        Scene scene = new Scene(layout);

        primaryStage.setTitle("Genesis");
        primaryStage.getIcons().add(image1);
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void shiftSeriesData1(float newValue) {
        for (int i = 0; i < NUM_OF_POINT - 1; i++) {
            XYChart.Data<String, Number> ShiftDataUp = (XYChart.Data<String, Number>) series1.getData().get(i + 1);
            Number shiftValue = ShiftDataUp.getYValue();
            XYChart.Data<String, Number> ShiftDataDn = (XYChart.Data<String, Number>) series1.getData().get(i);
            ShiftDataDn.setYValue(shiftValue);
        }
        XYChart.Data<String, Number> lastData = (XYChart.Data<String, Number>) series1.getData().get(NUM_OF_POINT - 1);
        lastData.setYValue(newValue);
    }

    public void shiftSeriesData2(float newValue2) {
        for (int i = 0; i < NUM_OF_POINT - 1; i++) {
            XYChart.Data<String, Number> ShiftDataUp = (XYChart.Data<String, Number>) series2.getData().get(i + 1);
            Number shiftValue = ShiftDataUp.getYValue();
            XYChart.Data<String, Number> ShiftDataDn = (XYChart.Data<String, Number>) series2.getData().get(i);
            ShiftDataDn.setYValue(shiftValue);
        }
        XYChart.Data<String, Number> lastData = (XYChart.Data<String, Number>) series2.getData().get(NUM_OF_POINT - 1);
        lastData.setYValue(newValue2);
    }

    public void shiftSeriesData3(float newValue1) {
        for (int i = 0; i < NUM_OF_POINT - 1; i++) {
            XYChart.Data<String, Number> ShiftDataUp = (XYChart.Data<String, Number>) series3.getData().get(i + 1);
            Number shiftValue = ShiftDataUp.getYValue();
            XYChart.Data<String, Number> ShiftDataDn = (XYChart.Data<String, Number>) series3.getData().get(i);
            ShiftDataDn.setYValue(shiftValue);
        }
        XYChart.Data<String, Number> lastData = (XYChart.Data<String, Number>) series3.getData().get(NUM_OF_POINT - 1);
        lastData.setYValue(newValue1);
    }

    public void shiftSeriesData4(float newValue4) {
        for (int i = 0; i < NUM_OF_POINT - 1; i++) {
            XYChart.Data<String, Number> ShiftDataUp = (XYChart.Data<String, Number>) series4.getData().get(i + 1);
            Number shiftValue = ShiftDataUp.getYValue();
            XYChart.Data<String, Number> ShiftDataDn = (XYChart.Data<String, Number>) series4.getData().get(i);
            ShiftDataDn.setYValue(shiftValue);
        }
        XYChart.Data<String, Number> lastData = (XYChart.Data<String, Number>) series4.getData().get(NUM_OF_POINT - 1);
        lastData.setYValue(newValue4);
    }

    public void connectXBee(String port) {
        System.out.println("XBee is connected.");
        SerialPort serialPort = new SerialPort(port);
        try {
            serialPort.openPort();
            serialPort.setParams(SerialPort.BAUDRATE_115200,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            serialPort.setEventsMask(MASK_RXCHAR);
            serialPort.addEventListener((SerialPortEvent serialPortEvent) -> {
                if (serialPortEvent.isRXCHAR()) {
                    try {
                        byte[] l;

                        while (serialPort.isOpened()) {
                            l = serialPort.readBytes(11);
                            for(int m = 0;m<10;m++) {
                                int x = l[0] & 0xFF;
                                int o = l[1] & 0xFF;
                                if (x == 5 & o == 240 || x == 5 & o == 241 || x == 5 & o == 242) {
                                  
                                    int y = l[1] & 0xFF;
                                    int id = ((5 * 256) + y);

                                    if (id == 1520) {
                                        int rpm_1 = l[3] & 0xFF;
                                        int rpm_2 = l[2] & 0xFF;
                                        int rpm_total = ((rpm_1 * 256) + rpm_2);
                                        Platform.runLater(() -> gauge1.setValue(rpm_total));

                                        int ops_1 = l[5] & 0xFF;
                                        int ops_2 = l[4] & 0xFF;
                                        int ops_total = ((ops_1 * 256) + ops_2);
                                        Platform.runLater(() -> gauge2.setValue(ops_total));

                                        int ect1_1 = l[7] & 0xFF;
                                        int ect1_2 = l[6] & 0xFF;
                                        int ect1_total = ((ect1_1 * 256) + ect1_2);
                                        Platform.runLater(() -> gauge3.setValue(ect1_total));

                                        int ect2_1 = l[9] & 0xFF;
                                        int ect2_2 = l[8] & 0xFF;
                                        int ect2_total = ((ect2_1 * 256) + ect2_2);
                                        Platform.runLater(() -> gauge4.setValue(ect2_total));

                                        Platform.runLater(() -> {
                                            shiftSeriesData1((float) rpm_total);
                                            shiftSeriesData2((float) ops_total);
                                        });
                                    }

                                    if (id == 1521) {
                                        int wsfr_1 = l[3] & 0xFF;
                                        int wsfr_2 = l[2] & 0xFF;
                                        int wsfr_total = ((wsfr_1 * 256) + wsfr_2);
                                        Platform.runLater(() -> gauge5.setValue(wsfr_total));

                                        int wsfl_1 = l[5] & 0xFF;
                                        int wsfl_2 = l[4] & 0xFF;
                                        int wsfl_total = ((wsfl_1 * 256) + wsfl_2);
                                        Platform.runLater(() -> gauge6.setValue(wsfl_total));

                                        int wsrr_1 = l[7] & 0xFF;
                                        int wsrr_2 = l[6] & 0xFF;
                                        int wsrr_total = ((wsrr_1 * 256) + wsrr_2);
                                        Platform.runLater(() -> gauge7.setValue(wsrr_total));

                                        int wsrl_1 = l[9] & 0xFF;
                                        int wsrl_2 = l[8] & 0xFF;
                                        int wsrl_total = ((wsrl_1 * 256) + wsrl_2);
                                        Platform.runLater(() -> gauge8.setValue(wsrl_total));
                                    }

                                    if (id == 1522) {
                                        int tps_1 = l[3] & 0xFF;
                                        int tps_2 = l[2] & 0xFF;
                                        int tps_total = ((tps_1 * 256) + tps_2);
                                        Platform.runLater(() -> gauge9.setValue(tps_total));

                                        int vol_1 = l[5] & 0xFF;
                                        int vol_2 = l[4] & 0xFF;
                                        int vol_total = ((vol_1 * 256) + vol_2);
                                        Platform.runLater(() -> gauge10.setValue(vol_total));

                                        int fp_1 = l[7] & 0xFF;
                                        int fp_2 = l[6] & 0xFF;
                                        int fp_total = ((fp_1 * 256) + fp_2);
                                        Platform.runLater(() -> gauge11.setValue(fp_total));

                                        int hh_1 = l[9] & 0xFF;
                                        int hg_2 = l[8] & 0xFF;
                                        int bh_total = ((hh_1 * 256) + hg_2);
                                        Platform.runLater(() -> gauge12.setValue(bh_total));

                                        Platform.runLater(() -> {
                                            shiftSeriesData3((float) tps_total);
                                            shiftSeriesData4((float) vol_total);
                                        });
                                    }

                                    System.out.println("b[0] = " + (l[0] & 0xFF));
                                    System.out.println("b[1] = " + (l[1] & 0xFF));
                                    System.out.println("b[2] = " + (l[2] & 0xFF));
                                    System.out.println("b[3] = " + (l[3] & 0xFF));
                                    System.out.println("b[4] = " + (l[4] & 0xFF));
                                    System.out.println("b[5] = " + (l[5] & 0xFF));
                                    System.out.println("b[6] = " + (l[6] & 0xFF));
                                    System.out.println("b[7] = " + (l[7] & 0xFF));
                                    System.out.println("b[8] = " + (l[8] & 0xFF));
                                    System.out.println("b[9] = " + (l[9] & 0xFF));
                                    System.out.println();
                                }
                            }

                    } catch (SerialPortException ex) {
                        Logger.getLogger(HelloApplication.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            XBeePort = serialPort;
        } catch (SerialPortException ex) {
            Logger.getLogger(HelloApplication.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("SerialPortException: " + ex);
        }
    }

    public void disconnectXBee() {
        System.out.println("XBee has been disconnected.");
        if (XBeePort != null) {
            try {
                XBeePort.removeEventListener();
                if (XBeePort.isOpened()) {
                    XBeePort.closePort();
                }
            } catch (SerialPortException ex) {
                Logger.getLogger(HelloApplication.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void stop() throws Exception {
        disconnectXBee();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
