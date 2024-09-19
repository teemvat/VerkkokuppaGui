package simu.framework;
import eduni.distributions.*;

public class ArriveProcess {
	
	private ContinuousGenerator generator;
	private EventList eventList;
	private IEventType type;

	public ArriveProcess(ContinuousGenerator g, EventList el, IEventType type){
		this.generator = g;
		this.eventList = el;
		this.type = type;
	}

	public void generateNext(){
		Event e = new Event(type, Clock.getInstance().getTime()+ generator.sample());
		eventList.add(e);
	}

}
