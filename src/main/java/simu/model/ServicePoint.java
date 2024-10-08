package simu.model;

import java.util.LinkedList;
import eduni.distributions.ContinuousGenerator;
import simu.framework.Clock;
import simu.framework.Event;
import simu.framework.EventList;
import simu.framework.Trace;

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
	public Order checkEvent(){
		// tarkistetaan Eventti olio
		//System.out.println("CheckEvent: "+queue.peek().getOrderType());
		return queue.peek();
	}


	public Order getFromQueue(){  // Poistetaan palvelussa ollut
		busy = false;
		return queue.poll();
	}


	public void serve(){  //Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana
			Order order = queue.peek();
			//System.out.println("Serving order with ID: " + order.getId());
			busy = true;
			double serviceTime = generator.sample();
			eventList.add(new Event(scheduledEventType, Clock.getInstance().getTime() + serviceTime));

	}



	public boolean isBusy(){
		return busy;
	}



	public boolean isQueue(){
		return queue.size() != 0;
	}

	public int getQueueSize() {
		return queue.size();
	}

}
