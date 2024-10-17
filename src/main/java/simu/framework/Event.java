package simu.framework;

import simu.model.EventType;

/**
 * Event class for simulation events
 */
public class Event implements Comparable<Event> {
	private EventType event;
	private double time;

	/**
	 * Constructor for Event
	 * @param event the type of event
	 * @param time the time of the event
	 */
	public Event(EventType event, double time){
		this.event = event;
		this.time = time;
	}

	/**Setters and getters*/

	public void setEvent(EventType event) {
		this.event = event;
	}

	public EventType getType() {
		return event;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public double getTime() {
		return time;
	}

	/**
	 * Compares two events based on their time
	 * @param arg the event to compare to
	 * @return -1 if this event is earlier, 1 if this event is later, 0 if they are at the same time
	 */
	@Override
	public int compareTo(Event arg) {
		if (this.time < arg.time) return -1;
		else if (this.time > arg.time) return 1;
		return 0;
	}
}
