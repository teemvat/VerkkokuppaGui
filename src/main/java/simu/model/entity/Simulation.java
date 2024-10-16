package simu.model.entity;

import controller.Controller;
import jakarta.persistence.*;


/**
 * The Simulation class represents a simulation in the simulation.
 * A simulation is created when the user starts a new simulation.
 * It contains the user-provided values and the calculated values.
 * The calculated values are updated during the simulation.
 */
@Entity
@Table(name = "simulation")
public class Simulation {

    /**
     * The simulation_id is the primary key of the simulation table.
     * It is generated automatically.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int simulation_id;

    // User-provided values:

    /**
     * The simulation_time is the time the simulation will run.
     */
    private double simulation_time;

    /**
     * The order_interval is the time between two orders.
     */
    private double order_interval;

    /**
     * The pickup_interval is the time between two pickups.
     */
    private double pickup_interval;

    /**
     * The orderhandlers is the number of order handlers in the simulation.
     * Order handlers are responsible for processing orders and sending them to the warehouse.
     */
    private int orderhandlers;

    /**
     * The warehousers is the number of warehousers in the simulation.
     * Warehouse workers are responsible for picking up the orders from the warehouse.
     */
    private int warehousers;

    /**
     * The packagers is the number of packagers in the simulation.
     * Packagers are responsible for packing the orders and sending them to the customers.
     */
    private int packagers;

    // Calculated values:

    /**
     * The packages_received is the number of packages received in the simulation.
     * It is updated during the simulation.
     */
    private int packages_received;

    /**
     * The packages_processed is the number of packages processed in the simulation.
     * It is updated during the simulation.
     */
    private int packages_processed;

    /**
     * The avg_time is the average time a package spends in the warehouse.
     * It is updated during the simulation.
     */
    private double avg_time;


    /**
     * The Simulation constructor creates a new simulation and sets the user-provided values.
     *
     * @param controller The controller that contains the user-provided values
     */
    public Simulation(Controller controller) {
        this.simulation_time = controller.getSimulationTime();
        this.order_interval = Double.parseDouble(String.valueOf(controller.getOrderInterval()));
        this.pickup_interval = controller.getPickupInterval();
        this.orderhandlers = controller.getOrdHndlAmount();
        this.warehousers = controller.getWarehouseAmount();
        this.packagers = controller.getPackagerAmount();
        this.packages_received = 0;
        this.packages_processed = 0;
        this.avg_time = 0;
    }

    /**
     * The Simulation constructor creates a new simulation.
     * It is used by the JPA to create simulations from the database.
     */
    public Simulation() {
    }


    /**
     * The getSimulationID method returns the database id of the simulation.
     *
     * @return The simulation_id of the simulation
     */
    public int getSimulationID() {
        return simulation_id;
    }

    /**
     * The getOrderhandlers method returns the number of order handlers in the simulation.
     * Order handlers are responsible for receiving orders.
     *
     * @return The number of order handlers in the simulation
     */
    public int getOrderhandlers() {
        return orderhandlers;
    }

    /**
     * The getWarehousers method returns the number of warehousers in the simulation.
     * Warehousers are responsible for collecting the items for the orders and moving them to the packagers.
     *
     * @return The number of warehousers in the simulation
     */
    public int getWarehousers() {
        return warehousers;
    }

    /**
     * The getPackagers method returns the number of packagers in the simulation.
     * Packagers are responsible for packaging the items and moving the orders to the pickup area.
     *
     * @return The number of packagers in the simulation
     */
    public int getPackagers() {
        return packagers;
    }

    /**
     * The getPackagesProcessed method returns the number of packages processed in the simulation.
     *
     * @return The number of packages processed in the simulation
     */
    public int getPackagesProcessed() {
        return packages_processed;
    }

    /**
     * The setPackagesProcessed method sets the number of packages processed in the simulation.
     * It is used to update the value during the simulation.
     *
     * @param packages The number of packages processed in the simulation
     */
    public void setPackagesProcessed(int packages) {
        this.packages_processed = packages;
    }

    /**
     * The getSimulationTime method returns the time the simulation will run.
     * The simulation time is set by the user.
     *
     * @return The simulation_time of the simulation
     */
    public double getSimulationTime() {
        return simulation_time;
    }

    /**
     * The getAverageTime method returns the average time a package spends in the warehouse.
     * The average time is updated during the simulation.
     *
     * @return The average time a package spends in the warehouse
     */
    public double getAverageTime() {
        return avg_time;
    }

    /**
     * The getOrder_interval method returns the time between two orders.
     * The order interval is set by the user.
     *
     * @return The order_interval of the simulation
     */
    public double getOrder_interval() {
        return order_interval;
    }

    /**
     * The getPickup_interval method returns the time between two pickups.
     * The pickup interval is set by the user.
     *
     * @return The pickup_interval of the simulation
     */
    public double getPickup_interval() {
        return pickup_interval;
    }

    /**
     * The getPackages_received method returns the number of packages received in the simulation.
     * The number of packages received is updated during the simulation.
     *
     * @return The number of packages received in the simulation
     */
    public int getPackages_received() {
        return packages_received;
    }

    /**
     * The setPackages_received method sets the number of packages received in the simulation.
     * It is used to update the value during the simulation.
     *
     * @param packages_received The number of packages received in the simulation
     */
    public void setPackages_received(int packages_received) {
        this.packages_received = packages_received;
    }


    /**
     * The updateAverageTime method updates the average time a package spends in the warehouse.
     * It is used to calculate the average time during the simulation.
     *
     * @param newTime The time a package spent in the warehouse
     */
    public void updateAverageTime(double newTime) {
        if (packages_processed > 0) {
            this.avg_time = ((this.avg_time * this.packages_processed) + newTime) / (this.packages_processed + 1);
        }
    }


    /**
     * The toString method returns a string representation of the simulation.
     *
     * @return A string representation of the simulation
     */
    public String toString1(){
        return "# "+simulation_id;
    }
}