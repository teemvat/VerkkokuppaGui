package controller;

import simu.model.entity.Simulation;

public interface IControllerForEng {

    // Rajapinta, joka tarjotaan moottorille:

    public void visualizeArrival();
    public void visualizeWarehouse();
    public void visualizePacking();
    public void visualizeShipping();
    public <T> T save(T entity);
    public void update(int id1, int id2, double time);

    void showAverageTime(double time);
    void showTotalShipped(int orders);

    double getAverageTime();

    void visualizeClear();

    void showProgress();

    void setEdit();

    int getOrdHndlAmount();

    int getWarehouseAmount();

    int getPackagerAmount();

    int getPickupInterval();

    double getSimulationTime();


    Simulation getSimulation();
}
