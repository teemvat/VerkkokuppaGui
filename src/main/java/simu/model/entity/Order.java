package simu.model.entity;

import jakarta.persistence.*;
import simu.framework.Clock;
import simu.framework.Trace;

/**
 * The Order class represents an order in the simulation.
 * An order is created when a new package arrives at the warehouse.
 */
@Entity
@Table(name="processed_order")
public class Order {

	/**
	 * The order_id is the primary key of the order table. It is generated automatically.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int order_id;

	/**
	 * The simulation is the simulation to which the order belongs.
	 */
	@ManyToOne
	@JoinColumn(name = "simulation_id", nullable = false)
	private Simulation simulation;

	/**
	 * The order_number is the number of the order. It is unique for each order.
	 */
	private int order_number;

	/**
	 * The arrival_time is the time when the order arrived at the warehouse.
	 */
	private double arrival_time;

	/**
	 * The completion_time is the time when the order was processed and left the warehouse.
	 */
	private double completion_time;

	/**
	 * The processing_time is the time the order spent in the warehouse.
	 */
	private double processing_time;

	/**
	 * The counter is used to generate unique order numbers.
	 */
	private static int counter = 1;


	/**
	 * The Order constructor creates a new order and sets the arrival time.
	 * It also increments the number of packages received in the simulation.
	 *
	 * @param simulation The simulation to which the order belongs
	 */
	public Order(Simulation simulation){
		this.simulation = simulation;
		this.order_number = counter++;
		this.arrival_time = Clock.getInstance().getTime();
		this.completion_time = 0;
		this.processing_time = 0;

		simulation.setPackages_received(simulation.getPackages_received() + 1);

		Trace.out(Trace.Level.INFO, "New order nro " + this.order_number + " arrived at "+ arrival_time);
	}

	/**
	 * The Order constructor creates a new order.
	 * It is used by the JPA to create orders from the database.
	 */
	public Order() {}


	/**
	 * The getOrderID method returns the database id of the order.
	 *
	 * @return The order_id of the order
	 */
	public int getOrderID() {
		return order_id;
	}

	/**
	 * The getSimulation method returns the simulation to which the order belongs.
	 *
	 * @return The simulation of the order
	 */
    public Simulation getSimulation() {
        return simulation;
    }

	/**
	 * The getSimulationID method returns the database id of the simulation to which the order belongs.
	 *
	 * @return The simulation_id of the order
	 */
	public int getSimulationID() {
		return simulation.getSimulationID();
	}

	/**
	 * The getPackagesProcessed method returns the number of packages processed in the simulation.
	 *
	 * @return The number of packages processed in the simulation
	 */
	public int getPackagesProcessed() {
		return simulation.getPackagesProcessed();
	}

	/**
	 * The getOrderNumber method returns the number of the order.
	 *
	 * @return The order_number of the order
	 */
    public int getOrderNumber() {
        return order_number;
    }

	/**
	 * The getArrivalTime method returns the time when the order arrived at the warehouse.
	 *
	 * @return The arrival_time of the order
	 */
	public double getArrivalTime() {
		return arrival_time;
	}

	/**
	 * The getCompletionTime method returns the time when the order was processed and left the warehouse.
	 *
	 * @return The completion_time of the order
	 */
	public double getCompletionTime() {
		return completion_time;
	}

	/**
	 * The setCompletionTime method sets the time when the order was processed and left the warehouse.
	 *
	 * @param completionTime The completion_time of the order
	 */
	public void setCompletionTime(double completionTime) {
		this.completion_time = completionTime;
	}

	/**
	 * The getProcessingTime method returns the time the order spent in the warehouse.
	 *
	 * @return The processing_time of the order
	 */
	public double getProcessingTime() {
		return processing_time;
	}

	/**
	 * The setProcessingTime method calculates and sets the processing time of the order.
	 *
	 * @param arrival_time The arrival_time of the order
	 * @param completion_time The completion_time of the order
	 */
	public void setProcessingTime(double arrival_time, double completion_time) {
		this.processing_time = completion_time - arrival_time;
	}


	/**
	 * The report method prints the order information to the console.
	 */
	public void report(){
		Trace.out(Trace.Level.INFO, "\nOrder "+ this.order_number + " ready! ");
		Trace.out(Trace.Level.INFO, "Order "+ this.order_number + " arrived: " + arrival_time);
		Trace.out(Trace.Level.INFO,"Order "+ this.order_number + " exited: " + completion_time);
		Trace.out(Trace.Level.INFO,"Order "+ this.order_number + " stayed: " + (completion_time - arrival_time));
		System.out.println("Order average service time: "+ simulation.getAverageTime());
	}
}
