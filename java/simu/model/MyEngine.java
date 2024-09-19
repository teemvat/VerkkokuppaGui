package simu.model;

import simu.framework.*;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;

public class MyEngine extends Engine {

    private ArriveProcess arriveProcess;

    private ServicePoint[] servicePoints;


    public MyEngine() {
//		servicepoint[0]= orderHandler
//		servicepoint[1]= warehouse
//		servicepoint[2]= packaging
//		servicepoint[3]= shipping
        arriveProcess = new ArriveProcess(new Negexp(15, 5), eventList, EventType.ARR1);
        servicePoints = new ServicePoint[4];
        servicePoints[0] = new ServicePoint(new Normal(15, 5), eventList, EventType.ORDHNDL);
        servicePoints[1] = new ServicePoint(new Normal(15, 5), eventList, EventType.WAREHOUSE);
        servicePoints[2] = new ServicePoint(new Normal(15, 5), eventList, EventType.PACKAGE);
        servicePoints[3] = new ServicePoint(new Normal(15, 5), eventList, EventType.INSHIPPING);




    }


    @Override
    protected void initialization() {
        arriveProcess.generateNext(); // Ensimmäinen saapuminen järjestelmään
    }

    @Override
    protected void runEvent(Event t) {  // B-vaiheen tapahtumat

        Order a;

        switch ((EventType) t.getType()) {

            case ARR1:
                servicePoints[0].addToQueue(new Order()); //ARR1 = tilaustyyppi 1 ja ARR2 = tilaustyyppi 2
                arriveProcess.generateNext();
                break;

            case ORDHNDL:
                a = (Order) servicePoints[0].getFromQueue(); //
                servicePoints[1].addToQueue(a);
                break;

            case WAREHOUSE:
                a = (Order) servicePoints[1].getFromQueue();
                servicePoints[2].addToQueue(a);
                break;

            case PACKAGE:
                a = (Order) servicePoints[2].getFromQueue();
                servicePoints[3].addToQueue(a);
                break;

            case INSHIPPING:
                a = (Order) servicePoints[3].getFromQueue();
                a.setEndTime(Clock.getInstance().getTime());
                a.report();
        }
    }

    @Override
    protected void tryCEvent() {
        for (ServicePoint s : servicePoints) {
            if (!s.isBusy() && s.isQueue()) {
                s.serve();
            }

        }
    }

    @Override
    protected void results() {
        System.out.println("Simulation ended in time : " + Clock.getInstance().getTime());
        System.out.println("Results ... are not implemented yet");
    }


}
