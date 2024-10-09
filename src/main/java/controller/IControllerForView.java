package controller;

public interface IControllerForView {

    // Rajapinta, joka tarjotaan  käyttöliittymälle:

    public void startSimulation();
    public void fast();
    public void slow();

    long getDelay();

    void showProgress();

    int getOrderInterval();
}
