package application.speedometer;

import eu.hansolo.medusa.Gauge;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortException;
import jssc.SerialPortList;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;


public class HelloController {
    @FXML
    private LineChart<String, Number> myLineChart;
    @FXML
    private Button myButton2, myButton3;
    @FXML
    private Gauge myGauge;

    private ScheduledExecutorService scheduledExecutorService;

    final int WINDOW_SIZE = 10;


    public void Start() {
        myLineChart.getData().clear();
        myGauge.setValue(0);
        myGauge.setUnit("Value/sec");
        XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();

        myLineChart.getData().add(series);

        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        scheduledExecutorService.scheduleAtFixedRate(() -> {

//            Integer random = ThreadLocalRandom.current().nextInt(10);
            String[] portList = SerialPortList.getPortNames();

            HelloController obj = new HelloController();
            Platform.runLater(() -> {
                Date now = new Date();
                series.getData().add(new XYChart.Data<String, Number>(simpleDateFormat.format(now), obj.connect(portList[0])));
                myGauge.setValue(obj.connect(portList[0]));

                if (series.getData().size() > WINDOW_SIZE)
                    series.getData().remove(0);
            });
        }, 0, 1, TimeUnit.SECONDS);
    }

    public void Reset(ActionEvent event) {
        scheduledExecutorService.shutdown();
        myGauge.setValue(0);
        myLineChart.getData().clear();
        myGauge.stop();
        }


    public double connect(String portname) {

        SerialPort port = new SerialPort(portname);
        try {
            port.openPort();

            port.setParams(
                    SerialPort.BAUDRATE_9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            port.addEventListener((SerialPortEvent event)->{
                if(event.isRXCHAR()) {
                    try {
                        String s = port.readString();
                        int i = Integer.parseInt(s);

                        System.out.print(i);
                    } catch(SerialPortException e){
                        e.printStackTrace();
                    }
                }
            });
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
