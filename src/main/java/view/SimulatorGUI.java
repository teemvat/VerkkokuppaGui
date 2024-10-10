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
import javafx.stage.Modality;
import javafx.stage.Stage;
import simu.framework.Trace;
import simu.framework.Trace.Level;
import simu.model.entity.Simulation;

import java.io.IOException;
import java.util.List;


public class SimulatorGUI extends Application implements ISimulatorUI {

    //Kontrollerin esittely (tarvitaan käyttöliittymässä)
    private IControllerForView controller = new Controller(this);
    private String currentSimulation = null;

    // Käyttöliittymäkomponentit:
    @FXML
    private TextField time;
    @FXML
    private TextField delay;
    @FXML
    private TextField idSearchField;
    @FXML
    private TextField packageIdField;


    @FXML
    private Label result;
    @FXML
    private Label totalOrders;

    private Label timeLabel;
    private Label delayLabel;
    private Label resultLabel;

    @FXML
    private Label simuStat1;
    @FXML
    private Label simuStat2;
    @FXML
    private Label simuStat3;
    @FXML
    private Label simuStat4;
    @FXML
    private Label simuStat5;
    @FXML
    private Label simuStat6;
    @FXML
    private Label simuStat7;
    @FXML
    private Label simuStat8;
    @FXML
    private Label simuStat9;
    @FXML
    private Label simuStat10;
    @FXML
    private Label ordStat1;
    @FXML
    private Label ordStat2;
    @FXML
    private Label ordStat3;
    @FXML
    private Label ordStat4;
    @FXML
    private Label ordStat5;
    @FXML
    private Label ordStat6;


    @FXML
    private Button slowButton;
    @FXML
    private Button fastButton;

    @FXML
    private Button startButton;

    @FXML
    private Button historyButton;

    @FXML
    private Button packageSearch;

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
    private Spinner<Integer> orderHandlersField;
    @FXML
    private Spinner<Integer> warehousersField;
    @FXML
    private Spinner<Integer> packagersField;
    @FXML
    private Spinner<Integer> pickupField;

    @FXML
    private ListView<String> pastSimus;



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

            primaryStage.setTitle("Verkkokuppa Simulator");

            primaryStage.setOnCloseRequest(evt -> {
                Platform.exit();
                System.exit(0);
            });

            box1.getChildren().add((Canvas)screen1);
            box2.getChildren().add((Canvas)screen2);
            box3.getChildren().add((Canvas)screen3);
            box4.getChildren().add((Canvas)screen4);

            orderHandlersField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 25));
            warehousersField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 25));
            packagersField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 25));
            pickupField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 25));

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
        DecimalFormat formatter = new DecimalFormat("#0.00");
        this.result.setText(formatter.format(time));
    }

    @Override
    public void setReadyOrders(int ordercount){
        this.totalOrders.setText("Shipped: " + ordercount);
    }

    @FXML
    public void startSimulation(){
        controller.startSimulation();
    }

    @FXML
    public void searchPackage(){
        controller.searchPackage(Integer.parseInt(packageIdField.getText().replaceAll("[^0-9]", "")));
    }

    @FXML
    public void searchSimulation(){
        controller.getSimulationByID(Integer.parseInt(idSearchField.getText().replaceAll("[^0-9]", "")));
    }

    @Override
    public void setSimuProgress(double d){
        simuProgress.setProgress(d);
    }

    public void slow(){
        controller.slow();
    }

    public void fast(){
        controller.fast();
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
    public int gerOrderInterval() {
        return 15;
    }

    @Override
    public int getPickupInterval(){
        return pickupField.getValue();
    }
    @Override
    public int getSimulationID(){
        return Integer.parseInt(idSearchField.getText());
    }

    @Override
    public void newHistoryWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/simulationHistory.fxml"));
            fxmlLoader.setController(this);
            Parent root = fxmlLoader.load();
            Stage history = new Stage();
            history.setTitle("Simulation History");
            history.setScene(new Scene(root));
            history.initModality(Modality.APPLICATION_MODAL); // Make the window modal
            controller.populatePastSimulations();
            pastSimus.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                currentSimulation = newValue;
                updateSimuStat();

            });
            history.showAndWait(); // Open the window as a modal dialog
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void updateSimuStat(){
        if (currentSimulation != null){
            controller.getSimulationByID(Integer.parseInt(currentSimulation.replaceAll("[^a-zA-Z0-9]", "")));
        }
    }

    @Override
    public void updateSimuStat1(int id){
        if (id != 0){
            String text = "Simulation ID: #"+id;
            simuStat1.setText(text);
        }
    }
    @Override
    public void updateSimuStat2(double time){
        if (time != 0){
            String text = "Simulation Time: "+time;
            simuStat2.setText(text);
        }
    }
    @Override
    public void updateSimuStat3(double oInterval){
        if (oInterval != 0){
            String text = "Order Interval: "+oInterval;
            simuStat3.setText(text);
        }
    }
    @Override
    public void updateSimuStat4(double puInterval){
        if (puInterval != 0){
            String text = "Pickup Interval: "+puInterval;
            simuStat4.setText(text);
        }
    }
    @Override
    public void updateSimuStat5(int oh){
        if (oh != 0){
            String text = "Order Handlers: "+oh;
            simuStat5.setText(text);
        }
    }
    @Override
    public void updateSimuStat6(int wh){
        if (wh != 0){
            String text = "Warehousers: "+wh;
            simuStat6.setText(text);
        }
    }
    @Override
    public void updateSimuStat7(int pck){
        if (pck != 0){
            String text = "Packagers: "+pck;
            simuStat7.setText(text);
        }
    }
    @Override
    public void updateSimuStat8(int pReceived){
        if (pReceived != 0){
            String text = "Packets Received: "+pReceived;
            simuStat8.setText(text);
        }
    }
    @Override
    public void updateSimuStat9(int pProcessed){
        if (pProcessed != 0){
            String text = "Packets Processed: "+pProcessed;
            simuStat9.setText(text);
        }
    }
    @Override
    public void updateSimuStat10(double avgTime){
        if (avgTime != 0){
            String text = "Average Time: "+avgTime;
            simuStat10.setText(text);
        }
    }

    @Override
    public void updateOrdStat1(int id){
        if (id != 0){
            String text = "Order ID: #"+id;
            ordStat1.setText(text);
        }
    }
    @Override
    public void updateOrdStat2(int simID){
        if (simID != 0){
            String text = "Simulation ID: #"+simID;
            ordStat2.setText(text);
        }
    }
    @Override
    public void updateOrdStat3(int oNum){
        if (oNum != 0){
            String text = "Order Number: #"+oNum;
            ordStat3.setText(text);
        }
    }
    @Override
    public void updateOrdStat4(double arrivalTime){
        if (arrivalTime != 0){
            String text = "Arrival Time: "+arrivalTime;
            ordStat4.setText(text);
        }
    }

    @Override
    public void updateOrdStat5(double completionTime){
        if (completionTime != 0){
            String text = "Completion Time: "+completionTime;
            ordStat5.setText(text);
        }
    }

    @Override
    public void updateOrdStat6(double processingTime){
        if (processingTime != 0){
            String text = "Processing Time: "+processingTime;
            ordStat6.setText(text);
        }
    }


    @Override
    public void addSimulationToHistory(List<Simulation> sims) {
        for (Simulation sim : sims) {
            pastSimus.getItems().add(sim.toString1());
        }
    }


}






