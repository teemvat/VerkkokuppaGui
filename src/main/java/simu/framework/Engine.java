package simu.framework;


import controller.Controller;
import controller.IControllerForEng; // UUSI

public abstract class Engine extends Thread implements IEngine {
    private double simulationTime = 0;
    private long delay = 0;

    private Clock clock;

    protected EventList eventList;

    protected IControllerForEng controller;


    public Engine(IControllerForEng controller) {

        this.controller = controller;

        clock = Clock.getInstance(); // Otetaan kello muuttujaan yksinkertaistamaan koodia

        eventList = new EventList();

        // Palvelupisteet luodaan simu.model-pakkauksessa Moottorin aliluokassa


    }

	@Override
	public void setSimulationTime(double time) {
		simulationTime = time;
	}


	@Override
	public void setDelay(long time) {
		this.delay = time;
	}

	@Override
	public long getDelay() {
		return delay;
	}

    @Override
    public void run() {
        initialization(); // luodaan mm. ensimmäinen tapahtuma
        while (simulation()) {
            delay();
            clock.setTime(currentTime());
            runBEvent();
            tryCEvent();
        }
        results();
        controller.setEdit();

    }

    private void runBEvent() {
        while (eventList.getNextTime() == clock.getTime()) {
            runEvent(eventList.remove());
        }
    }

    protected abstract void tryCEvent();


    private double currentTime() {
        return eventList.getNextTime();
    }

    private boolean simulation() {

        return clock.getTime() < simulationTime;
    }


    private void delay() {
        try {
            sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected abstract void initialization(); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa

    protected abstract void runEvent(Event evt);  // Määritellään simu.model-pakkauksessa Moottorin aliluokassa

    protected abstract void results(); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa


}