package view;


import java.text.DecimalFormat;

import controller.Controller;
import controller.IControllerForView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import simu.framework.Trace;
import simu.framework.Trace.Level;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;



public class SimulatorGUI extends Application implements ISimulatorUI {

    //Kontrollerin esittely (tarvitaan käyttöliittymässä)
    private IControllerForView controller;

    // Käyttöliittymäkomponentit:
    private TextField time;
    private TextField delay;
    private Label result;
    private Label timeLabel;
    private Label delayLabel;
    private Label resultLabel;

    private Button startButton;
    private Button slowButton;
    private Button fastButton;

    private IVisualization screen;


    @Override
    public void init() {

        Trace.setTraceLevel(Level.INFO);

        controller = new Controller(this);
    }

    @Override
    public void start(Stage primaryStage) {
        // Käyttöliittymän rakentaminen
        try {

            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent evt) {
                    Platform.exit();
                    System.exit(0);
                }
            });


            primaryStage.setTitle("Simulator");

            startButton = new Button();
            startButton.setText("Start simulation");
            startButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    controller.startSimulation();
                    startButton.setDisable(true);
                }
            });

            slowButton = new Button();
            slowButton.setText("Slower");
            slowButton.setOnAction(e -> controller.slow());

            fastButton = new Button();
            fastButton.setText("Faster");
            fastButton.setOnAction(e -> controller.fast());

            timeLabel = new Label("Simulation time:");
            timeLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            time = new TextField();
            time.setPromptText("Give time");
            time.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            time.setPrefWidth(150);

            delayLabel = new Label("Delay:");
            delayLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            delay = new TextField();
            delay.setPromptText("Simulation speed");
            delay.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            delay.setPrefWidth(150);

            resultLabel = new Label("Order average time:");
            resultLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            result = new Label();
            result.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            result.setPrefWidth(150);

            HBox hBox = new HBox();
            hBox.setPadding(new Insets(15, 12, 15, 12)); // marginaalit ylÃ¤, oikea, ala, vasen
            hBox.setSpacing(10);   // noodien välimatka 10 pikseliä

            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setVgap(10);
            grid.setHgap(5);
            grid.requestFocus();

            grid.add(timeLabel, 0, 0);   // sarake, rivi
            grid.add(time, 1, 0);          // sarake, rivi
            grid.add(delayLabel, 0, 1);      // sarake, rivi
            grid.add(delay, 1, 1);           // sarake, rivi
            grid.add(resultLabel, 0, 2);      // sarake, rivi
            grid.add(result, 1, 2);           // sarake, rivi
            grid.add(startButton, 0, 3);  // sarake, rivi
            grid.add(fastButton, 0, 4);   // sarake, rivi
            grid.add(slowButton, 1, 4);   // sarake, rivi

            screen = new Visualization(400, 200);

            // Täytetään boxi:
            hBox.getChildren().addAll(grid, (Canvas) screen);

            Scene scene = new Scene(hBox);
            primaryStage.setScene(scene);
            primaryStage.show();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Käyttöliittymän rajapintametodit (kutsutaan kontrollerista)

    @Override
    public double getTime() {
        return Double.parseDouble(time.getText());
    }

    @Override
    public long getDelay() {
        return Long.parseLong(delay.getText());
    }

    @Override
    public void setEndTime(double time) {
        DecimalFormat formatter = new DecimalFormat("#0.00");
        this.result.setText(formatter.format(time));
    }

    public void setAverageTime(double time){
        DecimalFormat formatter = new DecimalFormat("#0.00");
        this.result.setText(formatter.format(time));
    }


    @Override
    public IVisualization getVisualization() {
        return screen;
    }
}




