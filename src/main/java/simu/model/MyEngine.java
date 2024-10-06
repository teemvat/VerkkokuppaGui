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
            servicePoints[0][i] = new ServicePoint(new Normal(15, 5), eventList, EventType.ORDHNDL);
        }
        for (int i = 0; i <= warehouseAmount; i++) {
            servicePoints[1][i] = new ServicePoint(new Normal(15, 5), eventList, EventType.WAREHOUSE);
        }
        for (int i = 0; i <= packagerAmount; i++) {
            servicePoints[2][i] = new ServicePoint(new Normal(15, 5), eventList, EventType.PACKAGE);
        }
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
                int secureIdex=0;//this is for the case when warehouseAmount is less than ordHndlAmount so it will not go out of bounds
                for (int i = 0; i <= ordHndlAmount; i++) {
                    a = (Order) servicePoints[0][i].getFromQueue();
                    if(i==warehouseAmount-i){
                        //if ordHnld index is same as warehouse maximum index, start adding to warehouse from the beginning.
                        // if  odrnd 4 wants to add to warehouse 3, it will place order to warehouse 0 so it will have 2 on queue
                        secureIdex=0;
                    }
                    servicePoints[1][secureIdex].addToQueue(a);
                    secureIdex++;

                }

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

        controller.showEndTime(Clock.getInstance().getTime()); // tämä uus
    }


}
