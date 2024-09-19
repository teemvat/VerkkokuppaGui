package simu.framework;

import java.util.PriorityQueue;

public class EventList {
	private PriorityQueue<Event> list = new PriorityQueue<Event>();
	
	public EventList(){
	 
	}
	
	public Event remove(){
		Trace.out(Trace.Level.INFO,"Removing from eventlist " + list.peek().getType() + " " + list.peek().getTime() );
		return list.remove();
	}
	
	public void add(Event t){
		Trace.out(Trace.Level.INFO,"Added to eventlist " + t.getType() + " " + t.getTime());
		list.add(t);
	}
	
	public double getNextTime(){
		return list.peek().getTime();
	}
	
	
}
