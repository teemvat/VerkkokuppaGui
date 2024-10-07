package controller;

import javafx.application.Platform;
import simu.framework.IEngine;
import simu.model.MyEngine;
import view.ISimulatorUI;

public class Controller implements IControllerForEng, IControllerForView {   // UUSI

    private IEngine engine;
    private ISimulatorUI ui;

    public Controller(ISimulatorUI ui) {
        this.ui = ui;

    }


    // Moottorin ohjausta:

    /**
     * This starts simulation and gets parameters from ui
     */
    @Override
    public void startSimulation() {
        engine = new MyEngine(this); // luodaan uusi moottorisäie jokaista simulointia varten
        engine.setSimulationTime(ui.getTime());
        engine.setDelay(ui.getDelay());
        engine.makeWorkers(ui.getOrderHandlers(), ui.getWarehousers(), ui.getPackagers());/*UI:sta saadut arvot**/
        ui.getVisualization().clearScreen();
        ((Thread) engine).start();
        //((Thread)moottori).run(); // Ei missään tapauksessa näin. Miksi?
    }

    @Override
    public void slow() { // hidastetaan moottorisäiettä
        engine.setDelay((long) (engine.getDelay() * 1.10));
    }

    @Override
    public void fast() { // nopeutetaan moottorisäiettä
        engine.setDelay((long) (engine.getDelay() * 0.9));
    }


    // Simulointitulosten välittämistä käyttöliittymään.
    // Koska FX-ui:n päivitykset tulevat moottorisäikeestä, ne pitää ohjata JavaFX-säikeeseen:

    @Override
    public void showEndTime(double time) {
        Platform.runLater(() -> ui.setEndTime(time));
    }


    @Override
    public void visualizeOrder() {
        Platform.runLater(new Runnable() {
            public void run() {
                ui.getVisualization().newPackage();
            }
        });
    }


}
