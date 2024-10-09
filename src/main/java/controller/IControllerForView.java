package controller;

import simu.model.entity.Simulation;

public interface IControllerForView {

    // Rajapinta, joka tarjotaan  käyttöliittymälle:

    public void startSimulation();
    public void fast();
    public void slow();
    public void newHistoryWindow();

    void searchSimulation(int i);

    void populatePastSimulations();
    void showProgress();

    Simulation getSimulationByID(int i);

    void searchPackage(int i);
}
