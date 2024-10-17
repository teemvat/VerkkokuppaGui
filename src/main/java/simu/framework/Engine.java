package simu.framework;

import controller.IControllerForEng;

/**
 * Engine class for simulation engine
 */
public abstract class Engine extends Thread implements IEngine {
    private double simulationTime = 0;
    private long delay = 0;
    private Clock clock;
    protected EventList eventList;
    protected IControllerForEng controller;


    /**
     * Constructor for Engine
     */
    public Engine(IControllerForEng controller) {
        this.controller = controller;
        clock = Clock.getInstance();
        eventList = new EventList();
        // Service points are created in the subclass of Engine in the simu.model package
    }

    /**
     * Sets the simulation time
     * @param time the time to set
     */
	@Override
	public void setSimulationTime(double time) {
		simulationTime = time;
	}

    /**
     * Sets the delay
     * @param time the time to set
     */
	@Override
	public void setDelay(long time) {
		this.delay = time;
	}

    /**
     * Gets the delay
     * @return the delay
     */
	@Override
	public long getDelay() {
		return delay;
	}

    /**
     * The main loop of the simulation
     * Initializes the simulation, runs the simulation and prints the results
     */
    @Override
    public void run() {
        initialization();
        while (simulation()) {
            delay();
            clock.setTime(currentTime());
            runBEvent();
            tryCEvent();
        }
        results();
        controller.setEdit();
        //TODO: ohjelman nollaus
    }

    /**
     * Runs the next B event in the event list if it is at the current time of the clock and removes it from the list after running it.
     * If there are multiple events at the current time, they are all run.
     */
    private void runBEvent() {
        while (eventList.getNextTime() == clock.getTime()) {
            runEvent(eventList.remove());
        }
    }

    protected abstract void tryCEvent();

    /**
     * Gets the current time of the event list (the next time of the event list)
     * @return the current time
     */
    private double currentTime() {
        return eventList.getNextTime();
    }

    /**
     * Checks if the simulation should continue
     * @return true if the simulation should continue, false if not
     */
    private boolean simulation() {
        //Trace.out(Trace.Level.INFO, "Clock is " + clock.getTime());
        return clock.getTime() < simulationTime;
    }

    /**
     * Delays the simulation for the set delay time
     */
    private void delay() { // UUSI
        //Trace.out(Trace.Level.INFO, "Delay " + delay);
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