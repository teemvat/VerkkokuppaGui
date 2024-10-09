package view;


import java.text.DecimalFormat;

import controller.Controller;
import controller.IControllerForView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import simu.framework.Trace;
import simu.framework.Trace.Level;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

import java.io.IOException;
import java.text.DecimalFormat;


public class SimulatorGUI extends Application implements ISimulatorUI {

    //Kontrollerin esittely (tarvitaan käyttöliittymässä)
    private IControllerForView controller = new Controller(this);

    // Käyttöliittymäkomponentit:
    @FXML
    private TextField time;
    @FXML
    private TextField delay;
    @FXML
    private Label result;
    @FXML
    private Label totalOrders;

    private Label timeLabel;
    private Label delayLabel;
    private Label resultLabel;

    @FXML
    private Button slowButton;
    @FXML
    private Button fastButton;

    @FXML
    private Button startButton;

    @FXML
    private ProgressBar simuProgress;

    @FXML
    private HBox box1;
    @FXML
    private HBox box2;
    @FXML
    private HBox box3;
    @FXML
    private HBox box4;

    @FXML
    private IVisualization screen1 = new Visualization(450,90);
    @FXML
    private IVisualization screen2 = new Visualization(450,90);
    @FXML
    private IVisualization screen3 = new Visualization(450,90);
    @FXML
    private IVisualization screen4 = new Visualization(450,90);


    @FXML
    private Spinner<Integer> orderIntervalField;
    @FXML
    private Spinner<Integer> orderHandlersField;
    @FXML
    private Spinner<Integer> warehousersField;
    @FXML
    private Spinner<Integer> packagersField;
    @FXML
    private Spinner<Integer> pickupField;


    @Override
    public void init() {

        Trace.setTraceLevel(Level.INFO);

        controller = new Controller(this);
    }

    @Override
    public void start(Stage primaryStage) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/verkkokuppa.fxml"));
            fxmlLoader.setController(this);

            Parent root = fxmlLoader.load();

            primaryStage.setTitle("Verkkokuppa simulator");

            primaryStage.setOnCloseRequest(evt -> {
                Platform.exit();
                System.exit(0);
            });

            box1.getChildren().add((Canvas)screen1);
            box2.getChildren().add((Canvas)screen2);
            box3.getChildren().add((Canvas)screen3);
            box4.getChildren().add((Canvas)screen4);

            orderIntervalField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,200,1,1));
            orderHandlersField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 25, 1, 1));
            warehousersField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 25,1,1));
            packagersField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 25,1,1));
            pickupField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(100,2000,100,100));

            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    //Käyttöliittymän rajapintametodit (kutsutaan kontrollerista)

    @Override
    public double getTime() {
        double t;
        if (!time.getText().isEmpty()){
            t = Double.parseDouble(time.getText());
        } else{
            t = 1000.0; // tai joku defaulttiarvo
        }
        return t;
    }

    @Override
    public long getDelay() { // tän vois ehkä haluta muuks ku longiks? vois tehdä jopa slideriks
        long d;
        if (!delay.getText().isEmpty()){
            d = Long.parseLong(delay.getText());
        } else {
            d = 10;
        }
        return d;
    }

    @Override
    public void setEndTime(double time) {
        DecimalFormat formatter = new DecimalFormat("#0.00");
        this.result.setText(formatter.format(time));
    }

    @Override
    public void setAverageTime(double time){
        Platform.runLater(() -> {
            DecimalFormat formatter = new DecimalFormat("#0.00");
            this.result.setText(formatter.format(time));
        });
    }

    @Override
    public void setReadyOrders(int ordercount){
        this.totalOrders.setText("Shipped: " + ordercount);
    }

    @FXML
    public void startSimulation(){
        controller.startSimulation();
    }

    @Override
    public void setSimuProgress(double d){
        simuProgress.setProgress(d);
    }

    public void slow(){
        controller.slow();
        delay.setText(String.valueOf(controller.getDelay()));
    }

    public void fast(){
        controller.fast();
        delay.setText(String.valueOf(controller.getDelay()));
    }


    @Override
    public IVisualization getVisualization1() {
        return screen1;
    }

    @Override
    public IVisualization getVisualization2() {
        return screen2;
    }

    @Override
    public IVisualization getVisualization3() {
        return screen3;
    }

    @Override
    public int getOrderInterval(){
        return orderIntervalField.getValue();
    }
    @Override
    public IVisualization getVisualization4() {
        return screen4;
    }
    @Override
    public int getOrderHandlers(){
        return orderHandlersField.getValue();
    }
    @Override
    public int getWarehousers(){
        return warehousersField.getValue();
    }
    @Override
    public int getPackagers(){
        return packagersField.getValue();
    }

    @Override
    public int getPickupInterval(){
        return pickupField.getValue();
    }

    @Override
    public void setLock(){
        time.setEditable(false);
        delay.setEditable(false);
        orderIntervalField.setDisable(true);
        orderHandlersField.setDisable(true);
        warehousersField.setDisable(true);
        packagersField.setDisable(true);
        pickupField.setDisable(true);
    }

    @Override
    public void setEdit(){
        time.setEditable(true);
        delay.setEditable(true);
        orderIntervalField.setDisable(false);
        orderHandlersField.setDisable(false);
        warehousersField.setDisable(false);
        packagersField.setDisable(false);
        pickupField.setDisable(false);
    }
}




