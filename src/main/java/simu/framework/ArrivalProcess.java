package simu.framework;
import eduni.distributions.ContinuousGenerator;
import simu.model.EventType;
public class ArrivalProcess {
	
	private ContinuousGenerator generator;
	private EventList eventList;
	private EventType type;

	public ArrivalProcess(ContinuousGenerator g, EventList el, EventType type){
		this.generator = g;
		this.eventList = el;
		this.type = type;
	}

	public void generateNext(){
		Event evt = new Event(type, Clock.getInstance().getTime()+ generator.sample());
		eventList.add(evt);
	}

}
