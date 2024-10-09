package controller;

import simu.model.entity.Order;
import simu.model.entity.Simulation;

public interface IControllerForEng {

    // Rajapinta, joka tarjotaan moottorille:

    public void showEndTime(double time);
    public void visualizeArrival();
    public void visualizeWarehouse();
    public void visualizePacking();
    public void visualizeShipping();
    public <T> T save(T entity);

    void showAverageTime(double time);
    void showTotalShipped(int orders);

    double getAverageTime();

    void visualizeClear();

    void showProgress();

    double getInterval();

    int getOrdHndlAmount();

    int getWarehouseAmount();

    int getPackagerAmount();

    int getPickupInterval();

    double getSimulationTime();
     void visualizeOrder();
     void update (int simulationID,int orderID,double time);

    int getOrderInterval();


    Simulation getSimulation();
}
