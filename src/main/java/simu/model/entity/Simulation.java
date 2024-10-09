package simu.model.entity;

import controller.Controller;
import jakarta.persistence.*;


@Entity
@Table(name = "simulation")
public class Simulation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int simulation_id;

    private double simulation_time;
    private double order_interval;
    private int orderhandlers;
    private int warehousers;
    private int packagers;
    private int shippers;
    private int packages_processed;
    private double avg_time;
    private double total_time;



    public Simulation(Controller controller) {
        //super();

        // TODO katsotaan tämä yhdessä kuntoon (enginestä pitää saada tiedot ulos)

        //this.order_interval = 1;
        this.order_interval = controller.getInterval();
        //this.orderhandlers = 1;
        this.orderhandlers = controller.getOrdHndlAmount();
        //this.warehousers = 1;
        this.warehousers = controller.getWarehouseAmount();
        //this.packagers = 1;
        this.packagers = controller.getPackagerAmount();
        //this.shippers = 1;
        this.shippers = controller.getPickupInterval();
        this.packages_processed = 0;
        //this.simulation_time = 100;
        this.simulation_time = controller.getSimulationTime();
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

    public int getShippers() {
        return shippers;
    }

    public void setShippers(int shippers) {
        this.shippers = shippers;
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

    public void updateAverageTime(double newTime) {
        this.total_time += newTime;
        if (packages_processed > 0) {
            this.avg_time = this.total_time / this.packages_processed;
        }
    }

    public double getOrder_interval() {
        return order_interval;
    }

    public void setOrder_interval(double order_interval) {
        this.order_interval = order_interval;
    }
}