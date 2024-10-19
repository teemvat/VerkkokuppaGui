package controller;

import dao.OrderDAO;
import dao.SimulationDAO;
import javafx.application.Platform;
import simu.framework.Clock;
import simu.framework.IEngine;
import simu.model.MyEngine;
import simu.model.entity.Order;
import simu.model.entity.Simulation;
import view.ISimulatorUI;
import java.util.ArrayList;
import java.util.List;


/**
 * The Controller class is the main controller of the application.
 * It is responsible for controlling the engine, the UI and the DAO.
 * The Controller class implements the IControllerForEng, IControllerForView and IControllerForDao interfaces.
 */
public class Controller implements IControllerForEng, IControllerForView, IControllerForDao {   // UUSI

    /**
     * The engine of the simulation.
     */
    private IEngine engine;

    /**
     * The UI of the simulation.
     */
    private ISimulatorUI ui;

    /**
     * DAO for simulations.
     */
    private SimulationDAO sdao = new SimulationDAO();

    /**
     * DAO for orders.
     */
    private OrderDAO odao = new OrderDAO();

    /**
     * The current simulation.
     */
    private Simulation simulation;


    /**
     * Constructor for the Controller class.
     *
     * @param ui The UI of the simulation.
     */
    public Controller(ISimulatorUI ui) {
        this.ui = ui;
    }


    /**
     * Starts the simulation by initializing and configuring the engine, updating the UI
     * and locking the interface to prevent user input during the simulation.
     *
     * The steps involved in starting the simulation are:
     * 1. Creates a new engine thread for each simulation.
     * 2. Saves a new simulation to the database.
     * 3. Sets the simulation time to the UI.
     * 4. Sets the delay to the UI.
     * 5. Clears the screens of the visualizations in the UI.
     * 6. Locks the UI to prevent user input during the simulation run time.
     * 7. Starts the engine thread.
     */
    @Override
    public void startSimulation() {
        engine = new MyEngine(this, getOrdHndlAmount(), getWarehouseAmount(), getPackagerAmount(), getOrderInterval(), getPickupInterval()); // luodaan uusi moottorisäie jokaista simulointia varten
        simulation = save(new Simulation(this));
        engine.setSimulationTime(ui.getTime());
        engine.setDelay(ui.getDelay());
        ui.getVisualization1().clearScreen();
        ui.getVisualization2().clearScreen();
        ui.getVisualization3().clearScreen();
        ui.getVisualization4().clearScreen();
        ui.setLock();
        ((Thread) engine).start();
    }

    /**
     * Slows down the engine thread by increasing the delay of the engine thread by 10%.
     * The delay is the time between each step of the engine thread.
     */
    @Override
    public void slow() { // hidastetaan moottorisäiettä
        engine.setDelay((long) (engine.getDelay() * 1.10));
    }

    /**
     * Increases the speed of the engine thread by decreasing the delay of the engine thread by 10%.
     * The delay is the time between each step of the engine thread.
     */
    @Override
    public void fast() { // nopeutetaan moottorisäiettä
        engine.setDelay((long) (engine.getDelay() * 0.9));
    }

    /**
     * Returns the delay of the engine thread.
     *
     * @return The delay of the engine thread.
     */
    @Override
    public long getDelay() {
        return engine.getDelay();
    }

    /**
     * Visualizes the arrival of a package in the UI by creating a new package in the first visualization screen.
     */
    @Override
    public void visualizeArrival() {
        Platform.runLater(new Runnable() {
            public void run() {
                ui.getVisualization1().newPackage();
            }
        });
    }

    /**
     * Visualizes the collection of the order in the UI by moving the package from the first visualization screen to the second visualization screen.
     */
    @Override
    public void visualizeWarehouse() {
        Platform.runLater(new Runnable() {
            public void run() {
                ui.getVisualization2().newPackage();
                ui.getVisualization1().movePackage();
            }
        });
    }

    /**
     * Visualizes the packing of a package in the UI by moving the package from the second visualization screen to the third visualization screen.
     */
    @Override
    public void visualizePacking() {
        Platform.runLater(new Runnable() {
            public void run() {
                ui.getVisualization3().newPackage();
                ui.getVisualization2().movePackage();
            }
        });
    }

    /**
     * Visualizes the shipping of a package in the UI by moving the package from the third visualization screen to the fourth visualization screen.
     */
    @Override
    public void visualizeShipping() {
        Platform.runLater(new Runnable() {
            public void run() {
                ui.getVisualization4().newPackage();
                ui.getVisualization3().movePackage();
            }
        });
    }

    /**
     * Visualizes the pickup of a package in the UI by removing the packages from the fourth visualization screen.
     */
	@Override
	public void visualizeClear(){
		Platform.runLater(new Runnable(){
			public void run(){
				ui.getVisualization4().clearScreen();
			}
		});
	}

    /**
     * Shows the progress of the simulation in the UI by updating the progress bar.
     * 1. Calculates the maximum time of the simulation.
     * 2. Calculates the current time of the simulation by using the Clock class to get the time.
     * 3. Sets the progress of the simulation to the UI by dividing the current time by the maximum time of the simulation.
     */
	@Override
	public void showProgress(){
		double maxTime = ui.getTime();
		double currentTime = Clock.getInstance().getTime();
		ui.setSimuProgress((currentTime + 4) / maxTime);
	}

    /**
     * Sets the simulation statistics in the UI by updating the simulation statistics labels.
     *
     * @param id The ID of the simulation.
     * @return The simulation with the given ID.
     */
    @Override
    public Simulation getSimulationByID(int id) {
        Simulation simulation1 = find(Simulation.class, id);
        setSimulationStats(simulation1);
        return simulation1;
    }

    /**
     * Searches for a package by its ID and sets the order statistics in the UI by updating the order statistics labels.
     *
     * @param id The id of the package.
     */
    @Override
    public void searchPackage(int id) {
        Order order = find(Order.class, id);
        setOrderStats(order);
    }

    /**
     * Sets the simulation statistics in the UI by updating the simulation statistics labels.
     *
     * @param simulation The simulation to be displayed.
     */
    public void setSimulationStats(Simulation simulation) {
        ui.updateSimuStat1(simulation.getSimulationID());
        ui.updateSimuStat2(simulation.getSimulationTime());
        ui.updateSimuStat3(simulation.getOrder_interval());
        ui.updateSimuStat4(simulation.getPickup_interval());
        ui.updateSimuStat5(simulation.getOrderhandlers());
        ui.updateSimuStat6(simulation.getWarehousers());
        ui.updateSimuStat7(simulation.getPackagers());
        ui.updateSimuStat8(simulation.getPackages_received());
        ui.updateSimuStat9(simulation.getPackagesProcessed());
        ui.updateSimuStat10(simulation.getAverageTime());
    }

    /**
     * Sets the order statistics in the UI by updating the order statistics labels.
     *
     * @param order The order to be displayed.
     */
    public void setOrderStats(Order order) {
        ui.updateOrdStat1(order.getOrderID());
        ui.updateOrdStat2(order.getSimulationID());
        ui.updateOrdStat3(order.getOrderNumber());
        ui.updateOrdStat4(order.getArrivalTime());
        ui.updateOrdStat5(order.getCompletionTime());
        ui.updateOrdStat6(order.getProcessingTime());
    }

    /**
     * Shows the average time of the simulation in the UI by updating the average time label.
     *
     * @param time The average time of the simulation.
     */
    public void showAverageTime(double time){
		Platform.runLater(() ->ui.setAverageTime(time));
	}

    /**
     * Shows the total number of orders in the UI by updating the total orders label.
     *
     * @param orders The total number of orders.
     */
	@Override
	public void showTotalShipped(int orders){
		Platform.runLater(() ->ui.setReadyOrders(orders));
	}

    /**
     * Returns the average time in which the packages are processed in the simulation.
     *
     * @return The average time
     */
    public double getAverageTime() {
        return simulation.getAverageTime();
    }

    /**
     * Saves an entity to the database.
     * 1. If the entity is a simulation, saves the simulation to the database.
     * 2. If the entity is an order, saves the order to the database.
     *
     * The method is called when a new simulation is started or when a new order is created.
     *
     * @param entity The entity to be saved.
     * @param <T> The type of the entity to be saved.
     * @return The entity that was saved.
     */
    @Override
    public <T> T save(T entity) {
        if (entity instanceof Simulation) {
            sdao.persist((Simulation) entity);
        } else if (entity instanceof Order) {
            odao.persist((Order) entity);
        }
        return entity;
    }

    /**
     * Updates the simulation and order information when a new order is completed.
     * 1. Updates the order completion time and processing time.
     * 2. Updates the simulation by increasing the number of packages processed and updating the average time.
     * 3. Updates the UI by setting the average time.
     * 4. Updates the order and simulation in the database.
     *
     * @param simulationID The id of the simulation.
     * @param orderID The id of the order.
     * @param completionTime The completion time of the order.
     */
	@Override
	public void update(int simulationID, int orderID, double completionTime) {
		Order o = odao.find(orderID);
		o.setCompletionTime(completionTime);
		o.setProcessingTime(o.getArrivalTime(), completionTime);
		odao.update(o);
        Simulation s = sdao.find(simulationID);
        s.setPackagesProcessed(s.getPackagesProcessed() + 1);
        s.updateAverageTime(o.getProcessingTime());
        ui.setAverageTime(s.getAverageTime());
        sdao.update(s);
    }

    /**
     * Finds an entity from the database by its id.
     * 1. If the type is Simulation, finds a simulation from the database by its id.
     * 2. If the type is Order, finds an order from the database by its id.
     *
     * @param type The type of the entity to be returned.
     * @param id The id of the entity to be returned.
     * @return The entity from the database.
     * @param <T> The type of the entity to be returned.
     */
    @Override
    public <T> T find(Class<T> type, int id) {
        if (type == Simulation.class) {
            return (T) sdao.find(id);
        } else if (type == Order.class) {
            return (T) odao.find(id);
        }
        return null;
    }

    /**
     * Returns all the simulations or orders from the database.
     * 1. If the type is Simulation, returns all the simulations from the database.
     * 2. If the type is Order, returns all the orders from the database.
     *
     * @param type The type of the entity to be returned.
     * @return A list of all the simulations or orders from the database.
     * @param <T> The type of the entity to be returned.
     */
    @Override
    public <T> List<T> findAll(Class<T> type) {
        if (type == Simulation.class) {
            return (List<T>) sdao.findAll();
        } else if (type == Order.class) {
            return (List<T>) odao.findAll();
        }
        return new ArrayList<>();
    }

    /**
     * Sets the edit mode to the UI to allow the user to edit the simulation settings before starting the simulation.
     */
    @Override
    public void setEdit(){
        ui.setEdit();
    }

    /**
     * Returns the amount of order handlers in the simulation.
     *
     * @return The amount of order handlers in the simulation.
     */
    @Override
    public int getOrdHndlAmount() {
        return ui.getOrderHandlers();
    }

    /**
     * Returns the amount of warehousers in the simulation.
     *
     * @return The amount of warehousers in the simulation.
     */
    @Override
    public int getWarehouseAmount() {
        return ui.getWarehousers();
    }

    /**
     * Returns the amount of packagers in the simulation.
     *
     * @return The amount of packagers in the simulation.
     */
    @Override
    public int getPackagerAmount() {
        return ui.getPackagers();
    }

    /**
     * Returns the pickup interval of the simulation.
     *
     * @return The pickup interval of the simulation.
     */
    @Override
    public int getPickupInterval() {
        return ui.getPickupInterval();
    }

    /**
     * Returns the simulation time of the simulation.
     *
     * @return The simulation time of the simulation.
     */
    @Override
    public double getSimulationTime() {
        return ui.getTime();
    }

    /**
     * Returns the order interval of the simulation.
     *
     * @return The order interval of the simulation.
     */
	@Override
    public int getOrderInterval(){
		return ui.getOrderInterval();
	}

    /**
     * Returns the current simulation.
     *
     * @return The current simulation.
     */
    @Override
    public Simulation getSimulation() {
        return simulation;
    }

    /**
     * Populates the past simulations in the UI by adding all the simulations from the database to the history window.
     */
    @Override
    public void populatePastSimulations() {
        ui.addSimulationToHistory(findAll(Simulation.class));
    }
}
