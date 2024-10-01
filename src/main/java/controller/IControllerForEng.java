package controller;

public interface IControllerForEng {

    // Rajapinta, joka tarjotaan moottorille:

    public void showEndTime(double time);
    public void visualizeOrder();

    void showAverageTime(double time);

    double getAverageTime();
}
