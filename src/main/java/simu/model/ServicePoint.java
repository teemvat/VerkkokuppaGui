package simu.model;

import java.util.LinkedList;
import eduni.distributions.ContinuousGenerator;
import simu.framework.Clock;
import simu.framework.Event;
import simu.framework.EventList;
import simu.framework.Trace;
import simu.model.entity.Order;

/**
 * The class ServicePoint represents a service point in the simulation model.
 * The service point has a queue of orders and serves the orders in the queue.
 */
public class ServicePoint {

	/**
	 * The queue of orders waiting for service.
	 * The queue is implemented as a linked list.
	 */
	private final LinkedList<Order> queue = new LinkedList<>();

	/**
	 * The generator for the service times.
	 */
	private final ContinuousGenerator generator;

	/**
	 * The event list where new events are added.
	 */
	private final EventList eventList;

	/**
	 * The type of the event that is scheduled when starting a new service.
	 */
	private final EventType scheduledEventType;

	/**
	 * The flag indicating whether the service point is busy or not.
	 */
	private boolean busy = false;


	/**
	 * Creates a new service point with the given generator, event list and event type.
	 *
	 * @param generator The generator for the service times.
	 * @param eventList The event list where new events are added.
	 * @param type The type of the event that is scheduled when starting a new service.
	 */
	public ServicePoint(ContinuousGenerator generator, EventList eventList, EventType type){
		this.eventList = eventList;
		this.generator = generator;
		this.scheduledEventType = type;
	}

	/**
	 * Adds an order to the end of the queue.
	 * The first order in the queue is the one being served.
	 *
	 * @param a The order to be added to the queue.
	 */
	public void addToQueue(Order a){
		queue.add(a);
	}

	/**
	 * Removes the first order from the queue and returns it.
	 *
	 * @return The first order in the queue.
	 */
	public Order getFromQueue(){
		busy = false;
		return queue.poll();
	}

	/**
	 * Starts a new service for the first order in the queue.
	 * Returns the first order in the queue without removing it.
	 */
	public void serve(){
		Trace.out(Trace.Level.INFO, "Start new service for the order " + queue.peek().getOrderNumber());
		
		busy = true;
		double serviceTime = generator.sample();
		eventList.add(new Event(scheduledEventType, Clock.getInstance().getTime()+serviceTime));
	}

	/**
	 * Checks if the service point is busy.
	 *
	 * @return True if the service point is busy, false otherwise.
	 */
	public boolean isBusy(){
		return busy;
	}

	/**
	 * Checks if the queue is empty.
	 *
	 * @return True if the queue is not empty, false otherwise.
	 */
	public boolean isQueue(){
		return queue.size() != 0;
	}

	/**
	 * Returns the number of orders in the queue.
	 *
	 * @return The number of orders in the queue.
	 */
	public int getQueueSize() {
		return queue.size();
	}
}
