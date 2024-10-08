package simu.framework;


import controller.IControllerForEng; // UUSI

public abstract class Engine extends Thread implements IEngine {  // UUDET MÄÄRITYKSET
//    protected int ordHndlAmount= 0;
//    protected int warehouseAmount= 0;
//    protected int packagerAmount= 0;
    private double simulationTime = 0;
    private long delay = 0;

    private Clock clock;

    protected EventList eventList;

    protected IControllerForEng controller; // UUSI


    public Engine(IControllerForEng controller) {  // UUSITTU

        this.controller = controller;  //UUSI

        clock = Clock.getInstance(); // Otetaan kello muuttujaan yksinkertaistamaan koodia

        eventList = new EventList();

        // Palvelupisteet luodaan simu.model-pakkauksessa Moottorin aliluokassa


    }

    @Override
    public void setSimulationTime(double time) {
        simulationTime = time;
    }

    @Override // UUSI
    public void setDelay(long time) {
        this.delay = time;
    }

    @Override // UUSI
    public long getDelay() {
        return delay;
    }

    @Override
    public void run() { // Entinen aja()
        initialization(); // luodaan mm. ensimmäinen tapahtuma
        while (simulation()) {
            delay(); // UUSI
            clock.setTime(currentTime());
            runBEvent();
            tryCEvent();
        }
        results();

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
        //Trace.out(Trace.Level.INFO, "Clock is " + clock.getTime());
        return clock.getTime() < simulationTime;
    }


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