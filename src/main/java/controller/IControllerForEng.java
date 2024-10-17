package controller;

import simu.model.entity.Simulation;

/**
 * This interface is used to provide methods for the engine.
 */
public interface IControllerForEng {

    /**
     * This method is used to visualize the arrival of an order.
     */
    public void visualizeArrival();

    /**
     * This method is used to visualize the warehouse.
     */
    public void visualizeWarehouse();

    /**
     * This method is used to visualize the packing of an order.
     */
    public void visualizePacking();

    /**
     * This method is used to visualize the shipping of an order.
     */
    public void visualizeShipping();

    /**
     * This method is used to save an entity.
     *
     * @param entity the entity to be saved
     * @return the saved entity
     */
    public <T> T save(T entity);

    /**
     * This method is used to update a Simulation and Order entities.
     *
     * @param id1 the id of the Simulation to be updated.
     * @param id2 the id of the Order to be updated.
     * @param time completion time of the Order.
     */
    public void update(int id1, int id2, double time);

    /**
     * This method shows the average time the orders have been in the system.
     * @param time the average time
     */
    void showAverageTime(double time);

    /**
     * This method shows the total amount of orders that have been shipped.
     * @param orders
     */
    void showTotalShipped(int orders);

    /**
     * This method returns the average time the orders have been in the system.
     * @return
     */
    double getAverageTime();

    /**
     * This method clears the visualization.
     */
    void visualizeClear();

    /**
     * This method is used to show the progress of the simulation.
     */
    void showProgress();

    /**
     * This method is used to set the simulation UI to edit mode.
     */
    void setEdit();

    /**
     * Returns the amount of order handlers.
     * @return
     */
    int getOrdHndlAmount();

    /**
     * Returns the amount of warehouse workers.
     * @return
     */
    int getWarehouseAmount();

    /**
     * Returns the amount of packagers.
     * @return
     */
    int getPackagerAmount();

    /**
     * Returns the intervals of which orders are picked up.
     * @return
     */
    int getPickupInterval();

    /**
     * Returns the time the simulation has been set to run.
     * @return
     */
    double getSimulationTime();

    /**
     * Returns the simulation.
     * @return
     */
    Simulation getSimulation();
}
