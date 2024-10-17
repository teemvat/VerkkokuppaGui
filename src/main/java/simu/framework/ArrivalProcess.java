package simu.framework;

import eduni.distributions.ContinuousGenerator;
import simu.model.EventType;

/**
 * ArrivalProcess class for generating events
 */
public class ArrivalProcess {
	private ContinuousGenerator generator;
	private EventList eventList;
	private EventType type;

	public ArrivalProcess(ContinuousGenerator g, EventList el, EventType type){
		this.generator = g;
		this.eventList = el;
		this.type = type;
	}

	/**
	 * Generates the next event and adds it to the event list.
	 * The time of the event is the current time of the clock plus the sample from the generator.
	 */
	public void generateNext(){
		Event evt = new Event(type, Clock.getInstance().getTime()+ generator.sample());
		eventList.add(evt);
	}
}
