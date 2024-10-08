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
    int orderCount = 0;
    int packageCount = 0;
    int packageShippedCount = 0;
    private ArrivalProcess arrivalProcess;
    private ServicePoint[][] servicePoints;
    int shippingInterval;
    //orginal servicepoints
    // private ServicePoint[] servicePoints;


    public MyEngine(IControllerForEng controller, int ordHndlAmount, int warehouseAmount, int packagerAmount, int shippingInterval) {
        super(controller);
        this.ordHndlAmount = ordHndlAmount;
        this.warehouseAmount = warehouseAmount;
        this.packagerAmount = packagerAmount;
        this.shippingInterval = shippingInterval;


        arrivalProcess = new ArrivalProcess(new Negexp(30, 5), eventList, EventType.ARR1);
        servicePoints = new ServicePoint[4][];
        servicePoints[0] = new ServicePoint[ordHndlAmount];//servicepoint[0]= orderHandler
        servicePoints[1] = new ServicePoint[warehouseAmount];//servicepoint[1]= warehouse
        servicePoints[2] = new ServicePoint[packagerAmount];//servicepoint[2]= packaging
        servicePoints[3] = new ServicePoint[1];//servicepoint[3]= shipping

        System.out.println("Order handlers: " + ordHndlAmount + " Warehousers: " + warehouseAmount + " Packagers: " + packagerAmount);

        /**************************************************/
        for (int i = 0; i < ordHndlAmount; i++) {
            servicePoints[0][i] = new ServicePoint(new Normal(1, 1), eventList, EventType.ORDHNDL);
        }
        for (int i = 0; i < warehouseAmount; i++) {
            servicePoints[1][i] = new ServicePoint(new Normal(10, 2), eventList, EventType.WAREHOUSE);
        }
        for (int i = 0; i < packagerAmount; i++) {
            servicePoints[2][i] = new ServicePoint(new Normal(5, 5), eventList, EventType.PACKAGE);
        }
        servicePoints[3][0] = new ServicePoint(new Normal(shippingInterval, 0.1), eventList, EventType.INSHIPPING);

        //servicePoints[3][0] = new ServicePoint(new Normal(0.3, 1), eventList, EventType.INSHIPPING);
        /**************************************************/


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
                int minQueueSize = servicePoints[0][0].getQueueSize();//initilize  next min queue  size
                int queueIndex = 0;//initilize  next min queue index
                Order ord = new Order();
                orderCount++;
                for (int i = 0; i < ordHndlAmount; i++) {
                    int currentQueueSize = servicePoints[0][i].getQueueSize();
                    if (currentQueueSize < minQueueSize) {//get the index of the next min queue so we can add order to smallest queue
                        minQueueSize = currentQueueSize;
                        queueIndex = i;
                    }
                    //System.out.println("Order added to queue" + i);
                }
                servicePoints[0][queueIndex].addToQueue(ord);//add order to OrderHandler queue
                controller.visualizeOrder();
                arrivalProcess.generateNext();

                //TODO: check if these need to be in for loop
                break;

            case ORDHNDL:

                int minQueueSize1 = servicePoints[1][0].getQueueSize();//initilize  next min queue  size
                for (int i = 0; i < ordHndlAmount; i++) {
                    if (servicePoints[0][i].isBusy()) {
                        int queueIndex1 = 0;//initilize  next min queue index
                        a = (Order) servicePoints[0][i].getFromQueue();
                        for (int j = 0; j < warehouseAmount; j++) {
                            int currentQueueSize = servicePoints[1][j].getQueueSize();
                            if (currentQueueSize < minQueueSize1) {//get the index of the next min queue so we can add order to smallest queue
                                minQueueSize1 = currentQueueSize;
                                queueIndex1 = j;
                            }


                        }
                        servicePoints[1][queueIndex1].addToQueue(a);
                    }
                }


                break;

            case WAREHOUSE:

                int minQueueSize2 = servicePoints[2][0].getQueueSize();//initilize  next min queue  size
                for (int i = 0; i < warehouseAmount; i++) {
                    if (servicePoints[1][i].isBusy()) {
                        int queueIndex2 = 0;//initilize  next min queue index
                        a = (Order) servicePoints[1][i].getFromQueue();
                        for (int j = 0; j < packagerAmount; j++) {
                            int currentQueueSize = servicePoints[2][j].getQueueSize();
                            if (currentQueueSize < minQueueSize2) {//get the index of the next min queue so we can add order to smallest queue
                                minQueueSize2 = currentQueueSize;
                                queueIndex2 = j;
                            }
                        }
                        servicePoints[2][queueIndex2].addToQueue(a);
                    }
                }


                break;

            case PACKAGE:
                int minQueueSize3 = servicePoints[3][0].getQueueSize();//initilize  next min queue  size
                for (int i = 0; i < packagerAmount; i++) {
                    if (servicePoints[2][i].isBusy()) {
                        int queueIndex3 = 0;//initilize  next min queue index
                        a = (Order) servicePoints[2][i].getFromQueue();
                        packageCount++;//count packages for statistics
                        servicePoints[3][0].addToQueue(a);//add order to shipping queue


                    }
                }
                break;

            case INSHIPPING:
                packageShippedCount++;
                a = (Order) servicePoints[3][0].getFromQueue();
                a.setEndTime(Clock.getInstance().getTime());
                //a.report();


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
        System.out.println("" +
                "Order handlers: " + ordHndlAmount + " Warehousers: " + warehouseAmount + " Packagers: " + packagerAmount + " Shipping interval: " + shippingInterval
                +"Orders arrived: "+orderCount+
                " Orders packed: " + packageCount +
                " Orders shipped: " + packageShippedCount);

        controller.showEndTime(Clock.getInstance().getTime()); // tämä uus
    }


}
