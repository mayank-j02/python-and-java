package application.gpacalculator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController {

    @FXML
    private TextField myTextField1, myTextField2, myTextField3;
    @FXML
    private Button myButton1, myButton2, mybutton3;
    @FXML
    private Label myLabel;
    @FXML
    private ListView myListView;

    String grade;
    //double marks;
    double credit;
    double point = 0, sum = 0;
    double gpa;

    public void reset(ActionEvent event){
        //marks = 0;
        sum = 0;
        credit = 0;
        gpa = 0.0;
        myLabel.setText("GPA = " + gpa);
        myTextField1.setText("");
        myTextField2.setText("");
        myListView.getItems().clear();
    }

    public void addmore(ActionEvent event) {
        //marks = Integer.parseInt(myTextField1.getText());
        String sn = myTextField3.getText();
        grade = myTextField1.getText();
        if(grade.equals("A+") || grade.equals("a+") ){
            point = 10;
        }
        else if(grade.equals("A") || grade.equals("a") ){
            point = 9;
        }
        else if(grade.equals("B") || grade.equals("b") ){
            point = 8;
        }
        else if(grade.equals("C") || grade.equals("c") ){
            point = 7;
        }
        else if(grade.equals("D") || grade.equals("d")){
            point = 6;
        }
        else if(grade.equals("E") || grade.equals("e")){
            point = 5;
        }
        else if(grade.equals("F") || grade.equals("f")){
            point = 0;
        }
        else {
            myLabel.setText("Invalid Input");
        }
        double cred = Integer.parseInt(myTextField2.getText());
        myListView.getItems().add(sn + ": " + grade);
        credit += cred;
        sum = sum + (cred*point);
        myTextField1.setText("");
        myTextField2.setText("");
        myTextField3.setText("");

        System.out.println("credits: " + credit);
        System.out.println(sum);

        gpa = (sum/credit);
        //myLabel.setText(String.valueOf("GPA = " + String.format("%.2f", gpa)));
    }

    public void submit(ActionEvent event) {
        grade = myTextField1.getText();
        String sn = myTextField3.getText();

        if(grade.equals("A+") || grade.equals("a+") ){
            point = 10;
        }
        else if(grade.equals("A") || grade.equals("a") ){
            point = 9;
        }
        else if(grade.equals("B") || grade.equals("b") ){
            point = 8;
        }
        else if(grade.equals("C") || grade.equals("c") ){
            point = 7;
        }
        else if(grade.equals("D") || grade.equals("d")){
            point = 6;
        }
        else if(grade.equals("E") || grade.equals("e")){
            point = 5;
        }
        else if(grade.equals("F") || grade.equals("f")){
            point = 0;
        }
        else {
            myLabel.setText("Invalid Input");
        }
        double cred = Integer.parseInt(myTextField2.getText());
        myListView.getItems().add(sn + ": " + grade.toUpperCase() +"    Credit: "+ cred);

        credit += cred;
        sum = sum + (cred*point);
        myTextField1.setText("");
        myTextField2.setText("");
        myTextField3.setText("");

        System.out.println("credits: " + credit);
        System.out.println(sum);

        gpa = (sum/credit);
        myLabel.setText(String.valueOf("GPA = " + String.format("%.2f", gpa)));
    }
}
