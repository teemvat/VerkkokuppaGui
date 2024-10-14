package controller;

import simu.model.entity.Simulation;

public interface IControllerForView {

    // Rajapinta, joka tarjotaan  käyttöliittymälle:

    public void startSimulation();
    public void fast();
    public void slow();

    void populatePastSimulations();
    long getDelay();


    Simulation getSimulationByID(int i);

    void searchPackage(int i);

    int getOrderInterval();
}
