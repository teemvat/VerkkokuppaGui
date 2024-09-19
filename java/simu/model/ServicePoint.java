package simu.model;

import simu.framework.*;
import java.util.LinkedList;
import eduni.distributions.ContinuousGenerator;

// TODO:
// Palvelupistekohtaiset toiminnallisuudet, laskutoimitukset (+ tarvittavat muuttujat) ja raportointi koodattava
public class ServicePoint {

	private final LinkedList<Order> queue = new LinkedList<>(); // Tietorakennetoteutus
	private final ContinuousGenerator generator;
	private final EventList eventList;
	private final EventType scheduledEventType;
	
	//JonoStartegia strategia; //optio: asiakkaiden järjestys
	
	private boolean busy = false;


	public ServicePoint(ContinuousGenerator generator, EventList eventList, EventType type){
		this.eventList = eventList;
		this.generator = generator;
		this.scheduledEventType = type;
				
	}


	public void addToQueue(Order a){   // Jonon 1. asiakas aina palvelussa
		queue.add(a);
		
	}


	public Order getFromQueue(){  // Poistetaan palvelussa ollut
		busy = false;
		return queue.poll();
	}


	public void serve(){  //Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana
		
		Trace.out(Trace.Level.INFO, "Start new service for the order " + queue.peek().getId());
		
		busy = true;
		double serviceTime = generator.sample();
		eventList.add(new Event(scheduledEventType, Clock.getInstance().getTime()+serviceTime));
	}



	public boolean isBusy(){
		return busy;
	}



	public boolean isQueue(){
		return queue.size() != 0;
	}

}
