package simu.framework;

import simu.model.EventType;

public class Event implements Comparable<Event> {
	
		
	private EventType event;
	private double time;
	
	public Event(EventType event, double time){
		this.event = event;
		this.time = time;
	}
	
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

	@Override
	public int compareTo(Event arg) {
		if (this.time < arg.time) return -1;
		else if (this.time > arg.time) return 1;
		return 0;
	}
	
	
	

}
