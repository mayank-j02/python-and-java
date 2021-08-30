package application.speedometer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortException;
import jssc.SerialPortList;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

public class HelloApplication extends Application {
    
    
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Speedometer");
        stage.setScene(scene);
        stage.show();
    }

//    public void main1(String[] args) {
//        String[] portList = SerialPortList.getPortNames();
//
//        HelloController obj = new HelloController();
//        obj.connect(portList[0]);
//    }

    public static void main(String[] args) {
        launch();
    }
}
