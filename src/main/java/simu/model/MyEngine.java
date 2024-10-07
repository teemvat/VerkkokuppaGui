package simu.model;

import controller.IControllerForEng;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import simu.framework.ArrivalProcess;
import simu.framework.Clock;
import simu.framework.Engine;
import simu.framework.Event;

public class MyEngine extends Engine {

    private ArrivalProcess arrivalProcess;

    private ServicePoint[][] servicePoints;

    //orginal servicepoints
    // private ServicePoint[] servicePoints;


    public MyEngine(IControllerForEng controller) {
        super(controller);
//		servicepoint[0]= orderHandler
//		servicepoint[1]= warehouse
//		servicepoint[2]= packaging
//		servicepoint[3]= shipping


        arrivalProcess = new ArrivalProcess(new Negexp(15, 5), eventList, EventType.ARR1);
        servicePoints = new ServicePoint[4][0];

        //TODO: This is test if it works like this, ask teacher
        /**************************************************/
        for (int i = 0; i <= ordHndlAmount; i++) {
            servicePoints[0][i] = new ServicePoint(new Normal(3, 1), eventList, EventType.ORDHNDL);
        }
        for (int i = 0; i <= warehouseAmount; i++) {
            servicePoints[1][i] = new ServicePoint(new Normal(15, 5), eventList, EventType.WAREHOUSE);
        }
        for (int i = 0; i <= packagerAmount; i++) {
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
        arrivalProcess.generateNext(); // Ensimmäinen saapuminen järjestelmään
    }


    @Override
    protected void runEvent(Event evt) {  // B-vaiheen tapahtumat

        Order a;

        switch ((EventType) evt.getType()) {

            case ARR1:
                for (int i = 0; i <= ordHndlAmount; i++) {
                    servicePoints[0][i].addToQueue(new Order());
                }
                //TODO: check if these need to be in for loop
                controller.visualizeOrder();
                arrivalProcess.generateNext();
                break;

            case ORDHNDL:
                for (int i = 0; i <= ordHndlAmount; i++) {//TODO: kuinka tietää missä servicepointissa on kyseinen eventti olio??
                    if (servicePoints[0][i].isBusy()) {
                        if (servicePoints[0][i].checkEvent().getEndTime() == Clock.getInstance().getTime()) {
                            a = (Order) servicePoints[0][i].getFromQueue();
                            servicePoints[1][i].addToQueue(a);
                            for (int j = 0; j <= warehouseAmount; j++) {
                                if (!servicePoints[1][j].isQueue()) {//TODO:lisää siihen warehouseen missä vähiten jonoa
                                    servicePoints[1][j].addToQueue(a);
                                    break;
                                }
                            }
                        }

                    }

                }

                break;

            case WAREHOUSE:
                for (int i = 0; i <= warehouseAmount; i++) {//TODO: kuinka tietää missä servicepointissa on kyseinen eventti olio??
                    if (servicePoints[1][i].isBusy()) {
                        if (servicePoints[1][i].checkEvent().getEndTime() == Clock.getInstance().getTime()) {
                            a = (Order) servicePoints[1][i].getFromQueue();
                            for (int j = 0; j <= packagerAmount; j++) {
                                if (!servicePoints[2][j].isQueue()) {//TODO:lisää siihen warehouseen missä vähiten jonoa
                                    servicePoints[2][j].addToQueue(a);
                                    break;
                                }
                            }
                        }

                    }

                }

                break;

            case PACKAGE:
                for (int i = 0; i <= packagerAmount; i++) {//TODO: kuinka tietää missä servicepointissa on kyseinen eventti olio??
                    if (servicePoints[2][i].isBusy()) {
                        if (servicePoints[2][i].checkEvent().getEndTime() == Clock.getInstance().getTime()) {
                            a = (Order) servicePoints[2][i].getFromQueue();
                            servicePoints[3][0].addToQueue(a);

                        }

                    }
                }

                break;

            case INSHIPPING:
                a = (Order) servicePoints[3][0].getFromQueue();
                a.setEndTime(Clock.getInstance().getTime());
                a.report();
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
    /** */
    @Override
    protected void tryCEvent() {
        for (int i = 0; i <= ordHndlAmount; i++) {
            if (!servicePoints[0][i].isBusy() && servicePoints[0][i].isQueue()) {
                servicePoints[0][i].serve();
            }

        }
        for (int i = 0; i <= warehouseAmount; i++) {
            if (!servicePoints[1][i].isBusy() && servicePoints[1][i].isQueue()) {
                servicePoints[1][i].serve();
            }

        }

        for (int i = 0; i <= packagerAmount; i++) {
            if (!servicePoints[2][i].isBusy() && servicePoints[2][i].isQueue()) {
                servicePoints[2][i].serve();
            }

        }

        if (!servicePoints[3][0].isBusy() && servicePoints[3][0].isQueue()) {
            servicePoints[3][0].serve();
        }}

    @Override
    protected void results() {
        System.out.println("Simulation ended in time : " + Clock.getInstance().getTime());
        System.out.println("Results ... are not implemented yet");

        controller.showEndTime(Clock.getInstance().getTime()); // tämä uus
    }


}
