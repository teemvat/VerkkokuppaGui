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

		Trace.out(Trace.Level.INFO, "New order nro " + this.order_number + " arrived at "+ arrival_time);
	}

	public Order() {}

	public int getOrderID() {
		return order_id;
	}
    public Simulation getSimulation() {
        return simulation;
    }
	public int getSimulationID() {return simulation.getSimulationID();}
	public int getPackagesProcessed() {return simulation.getPackagesProcessed();}
    public int getOrderNumber() {
        return order_number;
    }

	public double getArrivalTime() {
		return arrival_time;
	}

	public double getCompletionTime() {
		return completion_time;
	}
	public void setCompletionTime(double completionTime) {
		this.completion_time = completionTime;
	}
	public double getProcessingTime() {
		return processing_time;
	}
	public void setProcessingTime(double arrival_time, double completion_time) {
		this.processing_time = completion_time - arrival_time;
	}


	public void report(){
		Trace.out(Trace.Level.INFO, "\nOrder "+ this.order_number + " ready! ");
		Trace.out(Trace.Level.INFO, "Order "+ this.order_number + " arrived: " + arrival_time);
		Trace.out(Trace.Level.INFO,"Order "+ this.order_number + " exited: " + completion_time);
		Trace.out(Trace.Level.INFO,"Order "+ this.order_number + " stayed: " + (completion_time - arrival_time));
		System.out.println("Order average service time: "+ simulation.getAverageTime());
	}


}
