package controller;

public interface IControllerForEng {

    // Rajapinta, joka tarjotaan moottorille:

    public void showEndTime(double time);
    public void visualizeArrival();
    public void visualizeWarehouse();
    public void visualizePacking();
    public void visualizeShipping();

    void showAverageTime(double time);
    void showTotalShipped(int orders);

    double getAverageTime();

    void showProgress();

    double getInterval();

    int getOrdHndlAmount();

    int getWarehouseAmount();

    int getPackagerAmount();

    int getPickupInterval();

    double getSimulationTime();
}
    public void visualizeOrder();


