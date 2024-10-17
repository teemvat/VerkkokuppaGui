package simu.framework;

import java.util.PriorityQueue;

/**
 * EventList class for simulation events
 */
public class EventList {
	private PriorityQueue<Event> list = new PriorityQueue<Event>();
	
	public EventList(){}

	/**
	 * Removes the first event from the list
	 * @return the first event
	 */
	public Event remove(){
		return list.remove();
	}

	/**
	 * Adds an event to the list
	 * @param t the event to be added
	 */
	public void add(Event t){
		list.add(t);
	}

	/**
	 * Gets the next event time
	 * @return the next event time
	 */
	public double getNextTime(){
		return list.peek().getTime();
	}
}
