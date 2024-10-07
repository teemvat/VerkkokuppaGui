package simu.model;

import controller.IControllerForEng;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import simu.framework.ArrivalProcess;
import simu.framework.Clock;
import simu.framework.Engine;
import simu.framework.Event;

public class MyEngine extends Engine {
    private int ordHndlAmount;
    private int warehouseAmount;
    private int packagerAmount;
    private ArrivalProcess arrivalProcess;

    private ServicePoint[][] servicePoints;

    //orginal servicepoints
    // private ServicePoint[] servicePoints;


    public MyEngine(IControllerForEng controller, int ordHndlAmount, int warehouseAmount, int packagerAmount) {
        super(controller);
        this.ordHndlAmount = ordHndlAmount;
        this.warehouseAmount = warehouseAmount;
        this.packagerAmount = packagerAmount;


        arrivalProcess = new ArrivalProcess(new Negexp(15, 5), eventList, EventType.ARR1);
        servicePoints = new ServicePoint[4][];
        servicePoints[0] = new ServicePoint[ordHndlAmount];//servicepoint[0]= orderHandler
        servicePoints[1] = new ServicePoint[warehouseAmount];//servicepoint[1]= warehouse
        servicePoints[2] = new ServicePoint[packagerAmount];//servicepoint[2]= packaging
        servicePoints[3] = new ServicePoint[1];//servicepoint[3]= shipping

        System.out.println("Order handlers: " + ordHndlAmount + " Warehousers: " + warehouseAmount + " Packagers: " + packagerAmount);

        /**************************************************/
        for (int i = 0; i < ordHndlAmount; i++) {
            servicePoints[0][i] = new ServicePoint(new Normal(3, 1), eventList, EventType.ORDHNDL);
        }
        for (int i = 0; i < warehouseAmount; i++) {
            servicePoints[1][i] = new ServicePoint(new Normal(15, 5), eventList, EventType.WAREHOUSE);
        }
        for (int i = 0; i < packagerAmount; i++) {
            servicePoints[2][i] = new ServicePoint(new Normal(15, 5), eventList, EventType.PACKAGE);
        }
        servicePoints[3][0] = new ServicePoint(new Normal(15, 5), eventList, EventType.INSHIPPING);
        /**************************************************/


        //These are orginal
        /*servicePoints[0] = new ServicePoint(new Normal(15, 5), eventList, EventType.ORDHNDL);
        servicePoints[1] = new ServicePoint(new Normal(15, 5), eventList, EventType.WAREHOUSE);
        servicePoints[2] = new ServicePoint(new Normal(15, 5), eventList, EventType.PACKAGE);
        servicePoints[3] = new ServicePoint(new Normal(15, 5), eventList, EventType.INSHIPPING);
                    */


    }

    @Override
    protected void initialization() {
        for (int i = 0; i < ordHndlAmount; i++) {/** First event, generate arrivals as many as there are order handlers*/
            arrivalProcess.generateNext();
        }

    }


    @Override
    protected void runEvent(Event evt) {  // B-vaiheen tapahtumat

        Order a;

        switch ((EventType) evt.getType()) {

            case ARR1:
                for (int i = 0; i < ordHndlAmount; i++) {
                    servicePoints[0][i].addToQueue(new Order());
                    System.out.println("Order added to queue" + i);
                    controller.visualizeOrder();
                    arrivalProcess.generateNext();
                }

                //TODO: check if these need to be in for loop
                break;

            case ORDHNDL:
                for (int i = 0; i < ordHndlAmount; i++) {
                    for (int j = 0; j < warehouseAmount; j++) {
                        a = (Order) servicePoints[0][i].getFromQueue();
                        servicePoints[1][j].addToQueue(a);
                    }
                }

                break;

            case WAREHOUSE:
                for (int i = 0; i < warehouseAmount; i++) {
                    for (int j = 0; j < packagerAmount; j++) {
                        a = (Order) servicePoints[1][i].getFromQueue();
                        servicePoints[2][j].addToQueue(a);
                    }

                }

                break;

            case PACKAGE:
                for (int i = 0; i < packagerAmount; i++) {
                        a = (Order) servicePoints[2][i].getFromQueue();
                        servicePoints[3][0].addToQueue(a);

                }

                break;

            case INSHIPPING:
                a = (Order) servicePoints[3][0].getFromQueue();
                a.setEndTime(Clock.getInstance().getTime());
                a.report();
        }
    }


    @Override
    protected void tryCEvent() {
        for (int i = 0; i < ordHndlAmount; i++) {
            if (!servicePoints[0][i].isBusy() && servicePoints[0][i].isQueue()) {
                servicePoints[0][i].serve();
            }

        }
        for (int i = 0; i < warehouseAmount; i++) {
            if (!servicePoints[1][i].isBusy() && servicePoints[1][i].isQueue()) {
                servicePoints[1][i].serve();
            }

        }

        for (int i = 0; i < packagerAmount; i++) {
            if (!servicePoints[2][i].isBusy() && servicePoints[2][i].isQueue()) {
                servicePoints[2][i].serve();
            }

        }

        if (!servicePoints[3][0].isBusy() && servicePoints[3][0].isQueue()) {
            servicePoints[3][0].serve();
        }
    }

    @Override
    protected void results() {
        System.out.println("Simulation ended in time : " + Clock.getInstance().getTime());
        System.out.println("Results ... are not implemented yet");

        controller.showEndTime(Clock.getInstance().getTime()); // tämä uus
    }


}
//this is orginal switch case
/*    @Override
    protected void runEvent(Event evt) {  // B-vaiheen tapahtumat

        Order a;

        switch ((EventType) evt.getType()) {

            case ARR1:
                servicePoints[0].addToQueue(new Order()); //ARR1 = tilaustyyppi 1 ja ARR2 = tilaustyyppi 2
                controller.visualizeOrder();
                arrivalProcess.generateNext();
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
    }*/

    /*  @Override
    protected void tryCEvent() {
        for (ServicePoint s : servicePoints) {
            if (!s.isBusy() && s.isQueue()) {
                s.serve();
            }

        }
    }*/

/**
 *
 */