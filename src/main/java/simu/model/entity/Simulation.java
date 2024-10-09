package simu.model.entity;

import controller.Controller;
import jakarta.persistence.*;


@Entity
@Table(name = "simulation")
public class Simulation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int simulation_id;
    // User-provided values:
    private double simulation_time;
    private double order_interval;
    private double pickup_interval;
    private int orderhandlers;
    private int warehousers;
    private int packagers;
    // Calculated values:
    private int packages_received;
    private int packages_processed;
    private double avg_time;


    public Simulation(Controller controller) {
        this.simulation_time = controller.getSimulationTime();
        this.order_interval = controller.getInterval();
        this.pickup_interval = controller.getPickupInterval();
        this.orderhandlers = controller.getOrdHndlAmount();
        this.warehousers = controller.getWarehouseAmount();
        this.packagers = controller.getPackagerAmount();
        this.packages_received = 0;
        this.packages_processed = 0;
        this.avg_time = 0;
    }

    public Simulation() {}


    public int getSimulationID() {
        return simulation_id;
    }

    public void setSimulationID(int id) {
        this.simulation_id = id;
    }

    public int getOrderhandlers() {
        return orderhandlers;
    }

    public void setOrderhandlers(int orderhandlers) {
        this.orderhandlers = orderhandlers;
    }

    public int getWarehousers() {
        return warehousers;
    }

    public void setWarehousers(int warehousers) {
        this.warehousers = warehousers;
    }

    public int getPackagers() {
        return packagers;
    }

    public void setPackagers(int packagers) {
        this.packagers = packagers;
    }
    public int getPackagesProcessed() {
        return packages_processed;
    }

    public void setPackagesProcessed(int packages) {
        this.packages_processed = packages;
    }

    public double getSimulationTime() {
        return simulation_time;
    }

    public void setSimulationTime(double time) {
        this.simulation_time = time;
    }

    public double getAverageTime() {
        return avg_time;
    }

    public void setAverageTime(double time) {
        this.avg_time = time;
    }
    public double getOrder_interval() {
        return order_interval;
    }

    public void setOrder_interval(double order_interval) {
        this.order_interval = order_interval;
    }
    public double getPickup_interval() {
        return pickup_interval;
    }
    public void setPickup_interval(double pickup_interval) {
        this.pickup_interval = pickup_interval;
    }
    public int getPackages_received() {
        return packages_received;
    }
    public void setPackages_received(int packages_received) {
        this.packages_received = packages_received;
    }

    public void updateAverageTime(double newTime) {
        if (packages_processed > 0) {
            this.avg_time = ((this.avg_time * this.packages_processed) + newTime) / (this.packages_processed + 1);
        }
    }

    public String toString1(){
        return "# "+simulation_id;
    }
}