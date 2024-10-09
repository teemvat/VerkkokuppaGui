package simu.model;

import controller.Controller;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import simu.framework.ArrivalProcess;
import simu.framework.Clock;
import simu.framework.Engine;
import simu.framework.Event;
import simu.model.entity.Order;

public class MyEngine extends Engine {

    private ArrivalProcess arrivalProcess;

    private ServicePoint[] servicePoints;


    public MyEngine(Controller controller) {
        super(controller);
//		servicepoint[0]= orderHandler
//		servicepoint[1]= warehouse
//		servicepoint[2]= packaging
//		servicepoint[3]= shipping
        arrivalProcess = new ArrivalProcess(new Negexp(15, 5), eventList, EventType.ARR1);
        servicePoints = new ServicePoint[4];
        servicePoints[0] = new ServicePoint(new Normal(15, 5), eventList, EventType.ORDHNDL);
        servicePoints[1] = new ServicePoint(new Normal(15, 5), eventList, EventType.WAREHOUSE);
        servicePoints[2] = new ServicePoint(new Normal(15, 5), eventList, EventType.PACKAGE);
        servicePoints[3] = new ServicePoint(new Normal(15, 5), eventList, EventType.INSHIPPING);
    }

    @Override
    protected void initialization() {
        arrivalProcess.generateNext(); // Ensimmäinen saapuminen järjestelmään
    }

    @Override
    protected void runEvent(Event evt) {  // B-vaiheen tapahtumat

        Order a;

        switch ((EventType) evt.getType()) {

            case ARR1:
                servicePoints[0].addToQueue(controller.save(new Order(controller.getSimulation())));
                arrivalProcess.generateNext();
                controller.visualizeArrival();
                break;

            case ORDHNDL:
                a = (Order) servicePoints[0].getFromQueue(); //
                servicePoints[1].addToQueue(a);
                break;

            case WAREHOUSE:
                a = (Order) servicePoints[1].getFromQueue();
                controller.visualizeWarehouse();
                servicePoints[2].addToQueue(a);
                break;

            case PACKAGE:
                a = (Order) servicePoints[2].getFromQueue();
                controller.visualizePacking();
                servicePoints[3].addToQueue(a);
                break;

            case INSHIPPING:
                a = (Order) servicePoints[3].getFromQueue();
                controller.visualizeShipping();
                controller.showAverageTime(controller.getAverageTime());
                controller.showTotalShipped(a.getPackagesProcessed()); //TODO: tähän jostain se arvo!!
                controller.update(a.getSimulationID(), a.getOrderID(), Clock.getInstance().getTime());

                a.report();
        }
        controller.showProgress();
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

        controller.showAverageTime(controller.getAverageTime()); // tämä uus
    }




}
