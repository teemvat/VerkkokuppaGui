package controller;

import simu.model.entity.Simulation;

/**
 * This interface provides methods for the user interface.
 */
public interface IControllerForView {

    public void startSimulation();

    public void fast();

    public void slow();

    void populatePastSimulations();

    long getDelay();

    Simulation getSimulationByID(int i);

    void searchPackage(int i);

    int getOrderInterval();
}
