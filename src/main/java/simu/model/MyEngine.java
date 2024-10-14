package simu.model;

import controller.IControllerForEng;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import simu.framework.ArrivalProcess;
import simu.framework.Clock;
import simu.framework.Engine;
import simu.framework.Event;
import simu.model.entity.Order;

/**
 * MyEngine class extends the Engine class and handles the simulation of the order processing system.
 */
public class MyEngine extends Engine {
    private int ordHndlAmount;
    private int warehouseAmount;
    private int packagerAmount;
    private int orderInterval;
    private int shippingInterval;
    int shippingAmount = 1;// hardcoded one car
    int orderCount = 0;
    int packageCount = 0;
    int packageShippedCount = 0;
    private ArrivalProcess arrivalProcess;
    private ServicePoint[][] servicePoints;


    /**
     * Constructor for MyEngine.
     *
     * @param controller       the controller interface for the engine
     * @param ordHndlAmount    the number of order handlers
     * @param warehouseAmount  the number of warehousers
     * @param packagerAmount   the number of packagers
     * @param orderInterval    the interval between orders
     * @param shippingInterval the interval for shipping
     */

    public MyEngine(IControllerForEng controller, int ordHndlAmount, int warehouseAmount, int packagerAmount, int orderInterval, int shippingInterval) {
        super(controller);
        this.ordHndlAmount = ordHndlAmount;
        this.warehouseAmount = warehouseAmount;
        this.packagerAmount = packagerAmount;
        this.orderInterval = orderInterval;
        this.shippingInterval = shippingInterval;
/** Amount of servicepoints are created for simulation with given amount of order handlers, warehousers, packagers and shipping points(last one is hardcoded)*/
        arrivalProcess = new ArrivalProcess(new Negexp(orderInterval, 5), eventList, EventType.ARR1);
        servicePoints = new ServicePoint[4][];
        servicePoints[0] = new ServicePoint[ordHndlAmount];//servicepoint[0]= orderHandler
        servicePoints[1] = new ServicePoint[warehouseAmount];//servicepoint[1]= warehouse
        servicePoints[2] = new ServicePoint[packagerAmount];//servicepoint[2]= packaging
        servicePoints[3] = new ServicePoint[shippingAmount];//servicepoint[3]= shipping
        /**for loops for making desired amount of servicepoints*/
        for (int i = 0; i < ordHndlAmount; i++) {
            servicePoints[0][i] = new ServicePoint(new Normal(8, 1), eventList, EventType.ORDHNDL);
        }
        for (int i = 0; i < warehouseAmount; i++) {
            servicePoints[1][i] = new ServicePoint(new Normal(30, 2), eventList, EventType.WAREHOUSE);
        }
        for (int i = 0; i < packagerAmount; i++) {
            servicePoints[2][i] = new ServicePoint(new Normal(45, 5), eventList, EventType.PACKAGE);
        }
        for (int i = 0; i < shippingAmount; i++) {
            servicePoints[3][i] = new ServicePoint(new Normal(this.shippingInterval, 1), eventList, EventType.INSHIPPING);
        }

    }

    @Override
    protected void initialization() {
        // First event, generate arrival
        arrivalProcess.generateNext();


    }

    /**
     * Handles the events during the simulation.
     *
     * @param evt the event to be processed
     */
    @Override
    protected void runEvent(Event evt) {  // B-vaiheen tapahtumat

        Order a;

        switch ((EventType) evt.getType()) {

            case ARR1:
                int minQueueSize = servicePoints[0][0].getQueueSize();//initilize  next min queue  size
                int queueIndex = 0;//initilize  next min queue index
                Order ord = new Order(controller.getSimulation());
                controller.save(ord);
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
                arrivalProcess.generateNext();
                controller.visualizeArrival();
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
                        controller.visualizeWarehouse();

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
                        controller.visualizePacking();

                    }
                }


                break;

            case PACKAGE:
                int minQueueSize3 = servicePoints[3][0].getQueueSize();//initilize  next min queue  size
                for (int i = 0; i < packagerAmount; i++) {
                    if (servicePoints[2][i].isBusy()) {
                        int queueIndex3 = 0;//initilize  next min queue index
                        a = (Order) servicePoints[2][i].getFromQueue();
                        for (int j = 0; j < shippingAmount; j++) {
                            int currentQueueSize = servicePoints[3][j].getQueueSize();
                            if (currentQueueSize < minQueueSize3) {
                                minQueueSize3 = currentQueueSize;
                                queueIndex3 = j;
                            }
                        }
                        packageCount++;//count packages for statistics
                        servicePoints[3][queueIndex3].addToQueue(a);//add order to shipping queue
                        controller.visualizeShipping();
                        controller.showProgress();
                    }
                }
                break;

            case INSHIPPING:
                for (int i = 0; i < shippingAmount; i++) {
                    do {
                        a = (Order) servicePoints[3][i].getFromQueue();
                        if (a != null) {
                            double completionTime = Clock.getInstance().getTime();
                            a.setCompletionTime(completionTime);
                            controller.update(a.getSimulation().getSimulationID(), a.getOrderID(), completionTime);
                            controller.showAverageTime(controller.getAverageTime());//TODO tee näistä fiksummat
                            controller.showTotalShipped(a.getPackagesProcessed());
                            packageShippedCount++;
                            a.report();
                        }
                    } while (a != null);
                    controller.visualizeClear();


                }


        }
    }
    /**
     * Tries to serve the next event in the queue.
     */w
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
        for (int i = 0; i < shippingAmount; i++) {
            if (!servicePoints[3][i].isBusy() && servicePoints[3][i].isQueue()) {
                servicePoints[3][i].serve();
            }
        }

    }

    @Override
    protected void results() {
        System.out.println("Simulation ended in time : " + Clock.getInstance().getTime());
        System.out.println("Order interval: " + orderInterval + " minutes " +
                "Order handlers: " + ordHndlAmount + " Warehousers: " + warehouseAmount + " Packagers: " + packagerAmount + " Shipping interval: " + shippingAmount
                + " Orders arrived: " + orderCount +
                " Orders packed: " + packageCount +
                " Orders shipped: " + packageShippedCount);

        controller.showAverageTime(controller.getAverageTime()); // tämä uus
    }


}
