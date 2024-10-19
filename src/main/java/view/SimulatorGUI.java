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

import java.util.List;
import java.io.IOException;


/**
 * SimulatorGUI class is responsible for the graphical user interface of the simulator.
 * It extends Application and implements ISimulatorUI.
 */
public class SimulatorGUI extends Application implements ISimulatorUI {

    // Declaration of the controller (needed in the user interface)
    private IControllerForView controller = new Controller(this);
    private String currentSimulation = null;

    /**
     * Declaration of the graphical user interface components.
     */

    @FXML
    private TextField time;
    @FXML
    private TextField delay;
    @FXML
    private TextField idSearchField;
    @FXML
    private TextField packageIdField;



    @FXML
    private Label completedOrderResult;
    private Label delayLabel;
    @FXML
    private Label timeResult;

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
    private IVisualization screen1 = new Visualization(450, 90);
    @FXML
    private IVisualization screen2 = new Visualization(450, 90);
    @FXML
    private IVisualization screen3 = new Visualization(450, 90);
    @FXML
    private IVisualization screen4 = new Visualization(450, 90);


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

    @FXML
    private ListView<String> pastSimus;


    /**
     * Initialize the graphical user interface.
     */
    @Override
    public void init() {

        Trace.setTraceLevel(Level.INFO);

        controller = new Controller(this);
    }

    /**
     * Start the graphical user interface.
     * 1. Load the FXML file.
     * 2. Set the controller.
     * 3. Create the scene.
     * 4. Set the stage.
     * 5. Show the stage.
     *
     * @param primaryStage the primary stage of the application
     */
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

            box1.getChildren().add((Canvas) screen1);
            box2.getChildren().add((Canvas) screen2);
            box3.getChildren().add((Canvas) screen3);
            box4.getChildren().add((Canvas) screen4);

            orderIntervalField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 200, 1, 1));
            orderHandlersField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 25, 1, 1));
            warehousersField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 25, 1, 1));
            packagersField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 25, 1, 1));
            pickupField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(100, 2000, 100, 100));

            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    //Käyttöliittymän rajapintametodit (kutsutaan kontrollerista)

    /**
     * Gets the time, which determines how long the simulation will run, from the graphical user interface.
     */
    @Override
    public double getTime() {
        double t;
        if (!time.getText().isEmpty()) {
            t = Double.parseDouble(time.getText());
        } else {
            t = 1000.0; // tai joku defaulttiarvo
        }
        return t;
    }

    /**
     * Gets the delay, which determines how fast the simulation will run, from the graphical user interface.
     */
    @Override
    public long getDelay() {
        long d;
        if (!delay.getText().isEmpty()) {
            d = Long.parseLong(delay.getText());
        } else {
            d = 10;
        }
        return d;
    }

    /**
     * Updates the average processing time of the orders to the graphical user interface.
     *
     * @param time the average processing time
     */
    @Override
    public void setAverageTime(double time) {
        Platform.runLater(() -> {
            DecimalFormat formatter = new DecimalFormat("#0.00");
            this.timeResult.setText(formatter.format(time));
        });
    }

    /**
     * Resets results and progressbar from the graphical user interface.
     */
    @Override
    public void startReset() {
        Platform.runLater(() -> {
            this.timeResult.setText("");
            this.completedOrderResult.setText("");
            this.simuProgress.setProgress(0);
        });
    }

    /**
     * Updates the amount of ready orders to the graphical user interface.
     *
     * @param ordercount The amount of ready orders.
     */
    @Override
    public void setReadyOrders(int ordercount) {
        this.completedOrderResult.setText("Shipped: " + ordercount);
    }

    /**
     * Starts the simulation.
     */
    @FXML
    public void startSimulation() {
        controller.startSimulation();
    }

    /**
     * Searches for a package by its ID.
     */
    @FXML
    public void searchPackage() {
        controller.searchPackage(Integer.parseInt(packageIdField.getText().replaceAll("[^0-9]", "")));
    }

    /**
     * Searches for a simulation by its ID.
     */
    @FXML
    public void searchSimulation() {
        controller.getSimulationByID(Integer.parseInt(idSearchField.getText().replaceAll("[^0-9]", "")));
    }

    /**
     * Set the progress of the simulation.
     *
     * @param d the progress of the simulation
     */
    @Override
    public void setSimuProgress(double d) {
        simuProgress.setProgress(d);
    }

    /**
     * Slows down the simulation.
     */
    public void slow() {
        controller.slow();
        delay.setText(String.valueOf(controller.getDelay()));
    }

    /**
     * Speeds up the simulation.
     */
    public void fast() {
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
    public int getOrderInterval() {
        return orderIntervalField.getValue();
    }

    @Override
    public IVisualization getVisualization4() {
        return screen4;
    }

    @Override
    public int getOrderHandlers() {
        return orderHandlersField.getValue();
    }

    @Override
    public int getWarehousers() {
        return warehousersField.getValue();
    }

    @Override
    public int getPackagers() {
        return packagersField.getValue();
    }

    @Override
    public int getPickupInterval() {
        return pickupField.getValue();
    }

    /**
     * Lock the simulation parameter options in the graphical user interface.
     */
    @Override
    public void setLock() {
        time.setEditable(false);
        delay.setEditable(false);
        orderIntervalField.setDisable(true);
        orderHandlersField.setDisable(true);
        warehousersField.setDisable(true);
        packagersField.setDisable(true);
        pickupField.setDisable(true);
    }

    /**
     * Make simulation parameter options editable in the graphical user interface.
     */
    @Override
    public void setEdit() {
        time.setEditable(true);
        delay.setEditable(true);
        orderIntervalField.setDisable(false);
        orderHandlersField.setDisable(false);
        warehousersField.setDisable(false);
        packagersField.setDisable(false);
        pickupField.setDisable(false);
    }


    /**
     * Open a new window for the simulation history.
     */
    @FXML
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


    /**
     * Update the simulation statistics in the simulation history window.
     */
    @FXML
    public void updateSimuStat() {
        if (currentSimulation != null) {
            controller.getSimulationByID(Integer.parseInt(currentSimulation.replaceAll("[^a-zA-Z0-9]", "")));
        }
    }

    /**
     * Update the simulation ID statistic-label in the simulation history window.
     *
     * @param id the simulation ID
     */
    @Override
    public void updateSimuStat1(int id) {
        if (id != 0 && id != Double.POSITIVE_INFINITY) {
            String text = "Simulation ID: #" + id;
            simuStat1.setText(text);
        } else {
            simuStat1.setText("Simulation ID: No data");
        }
    }

    /**
     * Update the simulation time statistic-label in the simulation history window.
     *
     * @param time the duration of simulation
     */
    @Override
    public void updateSimuStat2(double time) {
        if (time != 0 && time != Double.POSITIVE_INFINITY) {
            String text = "Simulation Time: " + time;
            simuStat2.setText(text);
        } else {
            simuStat2.setText("Simulation Time: No data");
        }
    }

    /**
     * Update the order interval statistic-label in the simulation history window.
     *
     * @param oInterval the order interval of simulation
     */
    @Override
    public void updateSimuStat3(double oInterval) {
        if (oInterval != 0 && oInterval != Double.POSITIVE_INFINITY) {
            String text = "Order Interval: " + oInterval;
            simuStat3.setText(text);
        } else {
            simuStat3.setText("Order Interval: No data");
        }
    }

    /**
     * Update the pickup interval statistic-label in the simulation history window.
     *
     * @param puInterval the pickup interval of simulation
     */
    @Override
    public void updateSimuStat4(double puInterval) {
        if (puInterval != 0 && puInterval != Double.POSITIVE_INFINITY) {
            String text = "Pickup Interval: " + puInterval;
            simuStat4.setText(text);
        } else {
            simuStat4.setText("Pickup Interval: No data");
        }
    }

    /**
     * Update the order handlers statistic-label in the simulation history window.
     *
     * @param oh the amount of order handlers
     */
    @Override
    public void updateSimuStat5(int oh) {
        if (oh != 0 && oh != Double.POSITIVE_INFINITY) {
            String text = "Order Handlers: " + oh;
            simuStat5.setText(text);
        } else {
            simuStat5.setText("Order Handlers: No data");
        }
    }

    /**
     * Update the warehousers statistic-label in the simulation history window.
     *
     * @param wh the amount of warehousers
     */
    @Override
    public void updateSimuStat6(int wh) {
        if (wh != 0 && wh != Double.POSITIVE_INFINITY) {
            String text = "Warehousers: " + wh;
            simuStat6.setText(text);
        } else {
            simuStat6.setText("Warehousers: No data");
        }
    }

    /**
     * Update the packagers statistic-label in the simulation history window.
     *
     * @param pck the amount of packagers
     */
    @Override
    public void updateSimuStat7(int pck) {
        if (pck != 0 && pck != Double.POSITIVE_INFINITY) {
            String text = "Packagers: " + pck;
            simuStat7.setText(text);
        } else {
            simuStat7.setText("Packagers: No data");
        }
    }

    /**
     * Update the amount of received packets statistic-label in the simulation history window.
     *
     * @param pReceived the amount of received packets
     */
    @Override
    public void updateSimuStat8(int pReceived) {
        if (pReceived != 0 && pReceived != Double.POSITIVE_INFINITY) {
            String text = "Packets Received: " + pReceived;
            simuStat8.setText(text);
        } else {
            simuStat8.setText("Packets Received: No data");
        }
    }

    /**
     * Update the amount of processed packets statistic-label in the simulation history window.
     *
     * @param pProcessed the amount of processed packets
     */
    @Override
    public void updateSimuStat9(int pProcessed) {
        if (pProcessed != 0 && pProcessed != Double.POSITIVE_INFINITY) {
            String text = "Packets Processed: " + pProcessed;
            simuStat9.setText(text);
        } else {
            simuStat9.setText("Packets Processed: No data");
        }
    }

    /**
     * Update the average processing time statistic-label in the simulation history window.
     *
     * @param avgTime the average processing time
     */
    @Override
    public void updateSimuStat10(double avgTime) {
        if (avgTime != 0 && avgTime != Double.POSITIVE_INFINITY) {
            String text = String.format("Average Time: %1$.2f", avgTime);
            simuStat10.setText(text);
        } else {
            simuStat10.setText("Average Time: No data");
        }
    }


    /**
     * Update the order ID in the order history window.
     *
     * @param id the order ID
     */
    @Override
    public void updateOrdStat1(int id) {
        if (id != 0) {
            String text = "Order ID: #" + id;
            ordStat1.setText(text);
        }
    }

    /**
     * Update the simulation ID in the order history window.
     *
     * @param simID the simulation ID associated with the order
     */
    @Override
    public void updateOrdStat2(int simID) {
        if (simID != 0) {
            String text = "Simulation ID: #" + simID;
            ordStat2.setText(text);
        }
    }

    /**
     * Update the order number in the order history window.
     *
     * @param oNum the order number
     */
    @Override
    public void updateOrdStat3(int oNum) {
        if (oNum != 0) {
            String text = "Order Number: #" + oNum;
            ordStat3.setText(text);
        }
    }

    /**
     * Update the order arrival time in the order history window.
     *
     * @param arrivalTime the arrival time of the order
     */
    @Override
    public void updateOrdStat4(double arrivalTime) {
        if (arrivalTime != 0) {
            String text = String.format("Arrival Time: %1$.2f", arrivalTime);
            ordStat4.setText(text);
        }
    }

    /**
     * Update the order completion time in the order history window.
     *
     * @param completionTime the completion time of the order
     */
    @Override
    public void updateOrdStat5(double completionTime) {
        if (completionTime != 0) {
            String text = String.format("Completion Time: %1$.2f", completionTime);
            ordStat5.setText(text);
        }
    }

    /**
     * Update the order processing time in the order history window.
     *
     * @param processingTime the processing time of the order
     */
    @Override
    public void updateOrdStat6(double processingTime) {
        if (processingTime != 0) {
            String text = String.format("Processing Time: %1$.2f", processingTime);
            ordStat6.setText(text);
        }
    }

    /**
     * Populates the simulation history with past simulations.
     *
     * @param sims the list of past simulations
     */
    @Override
    public void addSimulationToHistory(List<Simulation> sims) {
        for (Simulation sim : sims) {
            pastSimus.getItems().add(sim.toString1());
        }
    }
}







