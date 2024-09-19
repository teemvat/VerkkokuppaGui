package simu.framework;

public abstract class Engine {
	
	private double simulationTime = 0;
	
	private Clock clock;
	
	protected EventList eventList;

	public Engine(){

		clock = Clock.getInstance(); // Otetaan kello muuttujaan yksinkertaistamaan koodia
		
		eventList = new EventList();
		
		// Palvelupisteet luodaan simu.model-pakkauksessa Moottorin aliluokassa 
		
		
	}

	public void setSimulationTime(double time) {
		simulationTime = time;
	}
	
	
	public void run(){
		initialization(); // luodaan mm. ensimmäinen tapahtuma
		while (simulation()){
			
			Trace.out(Trace.Level.INFO, "\nA-Phase: clock is " + currentTime());
			clock.setTime(currentTime());
			
			Trace.out(Trace.Level.INFO, "\nB-Phase:" );
			runBEvent();
			
			Trace.out(Trace.Level.INFO, "\nC-Phase:" );
			tryCEvent();

		}
		results();
		
	}
	
	private void runBEvent(){
		while (eventList.getNextTime() == clock.getTime()){
			runEvent(eventList.remove());
		}
	}

	private double currentTime(){
		return eventList.getNextTime();
	}
	
	private boolean simulation(){
		return clock.getTime() < simulationTime;
	}

	protected abstract void runEvent(Event t);  // Määritellään simu.model-pakkauksessa Moottorin aliluokassa
	protected abstract void tryCEvent();	// Määritellään simu.model-pakkauksessa Moottorin aliluokassa

	protected abstract void initialization(); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa

	protected abstract void results(); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa
	
}