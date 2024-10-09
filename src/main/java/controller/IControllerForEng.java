package controller;

public interface IControllerForEng {

    // Rajapinta, joka tarjotaan moottorille:

    public void showEndTime(double time);
    public void visualizeArrival();
    public void visualizeWarehouse();
    public void visualizePacking();
    public void visualizeShipping();

    void showAverageTime(double time);

    double getAverageTime();

    void showProgress();
}
