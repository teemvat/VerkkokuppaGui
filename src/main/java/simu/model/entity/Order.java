package simu.model.entity;

import jakarta.persistence.*;
import simu.framework.Clock;
import simu.framework.Trace;


@Entity
@Table(name="processed_order")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int order_id;
	@ManyToOne
	@JoinColumn(name = "simulation_id", nullable = false)
	private Simulation simulation;
	private int order_number;
	private double arrival_time;
	private double completion_time;
	private double processing_time;
	private static int counter = 1;


	public Order(Simulation simulation){
		this.simulation = simulation;
		this.order_number = counter++;
		this.arrival_time = Clock.getInstance().getTime();
		this.completion_time = 0;
		this.processing_time = 0;

		simulation.setPackages_received(simulation.getPackages_received() + 1);

		Trace.out(Trace.Level.INFO, "New order nro " + order_id + " arrived at "+ arrival_time);
	}

	public Order() {}


	public int getOrderID() {
		return order_id;
	}
	public void setID(int id) {
		this.order_id = id;
	}
    public Simulation getSimulation() {
        return simulation;
    }
    public void setSimulation(Simulation simulation) {
        //this.simulation = simulation;
    }
	public int getSimulationID() {return simulation.getSimulationID();}
    public int getOrderNumber() {
        return order_number;
    }
    public void setOrderNumber(int orderNumber) {
        this.order_number = orderNumber;
    }
	public double getArrivalTime() {
		return arrival_time;
	}
	public void setArrivalTime(double arrivalTime) {
		this.arrival_time = arrivalTime;
	}
	public double getCompletionTime() {
		return completion_time;
	}
	public static int getCounter() {
		return counter;
	}
	public static void setCounter(int counter) {
		Order.counter = counter;
	}
	public void setCompletionTime(double completionTime) {
		this.completion_time = completionTime;
	}
	public double getProcessingTime() {
		return processing_time;
	}

	public void setProcessingTime() {
		if (completion_time > 0 && arrival_time > 0) {
			this.processing_time = completion_time - arrival_time;
		}
	}

	public void report(){
		Trace.out(Trace.Level.INFO, "\nOrder "+ order_id + " ready! ");
		Trace.out(Trace.Level.INFO, "Order "+ order_id + " arrived: " + arrival_time);
		Trace.out(Trace.Level.INFO,"Order "+ order_id + " exited: " + completion_time);
		Trace.out(Trace.Level.INFO,"Order "+ order_id + " stayed: " + (completion_time - arrival_time));

		System.out.println("Order average service time: "+ simulation.getAverageTime());
	}


}
