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

public class Controller implements IControllerForEng, IControllerForView, IControllerForDao {   // UUSI

    private IEngine engine;
    private ISimulatorUI ui;
    private SimulationDAO sdao = new SimulationDAO();
    private OrderDAO odao = new OrderDAO();
    private Simulation simulation;


    public Controller(ISimulatorUI ui) {
        this.ui = ui;
    }


    // Moottorin ohjausta:

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



    @Override
    public void slow() { // hidastetaan moottorisäiettä
        engine.setDelay((long) (engine.getDelay() * 1.10));
    }



    @Override
    public void fast() { // nopeutetaan moottorisäiettä
        engine.setDelay((long) (engine.getDelay() * 0.9));
    }

    @Override
    public long getDelay() {
        return engine.getDelay();
    }


    // Simulointitulosten välittämistä käyttöliittymään.
    // Koska FX-ui:n päivitykset tulevat moottorisäikeestä, ne pitää ohjata JavaFX-säikeeseen:




    @Override
    public void visualizeArrival() {
        Platform.runLater(new Runnable() {
            public void run() {
                ui.getVisualization1().newPackage();
            }
        });
    }

    @Override
    public void visualizeWarehouse() {
        Platform.runLater(new Runnable() {
            public void run() {
                ui.getVisualization2().newPackage();
                ui.getVisualization1().movePackage();
            }
        });
    }

    @Override
    public void visualizePacking() {
        Platform.runLater(new Runnable() {
            public void run() {
                ui.getVisualization3().newPackage();
                ui.getVisualization2().movePackage();
            }
        });
    }

    @Override
    public void visualizeShipping() {
        Platform.runLater(new Runnable() {
            public void run() {
                ui.getVisualization4().newPackage();
                ui.getVisualization3().movePackage();
            }
        });
    }

	@Override
	public void visualizeClear(){
		Platform.runLater(new Runnable(){
			public void run(){
				ui.getVisualization4().clearScreen();
			}
		});
	}

	@Override
	public void showProgress(){
		double maxTime = ui.getTime();
		double currentTime = Clock.getInstance().getTime();
		ui.setSimuProgress((currentTime + 4) / maxTime);
	}


    @Override
    public Simulation getSimulationByID(int id) {
        Simulation simulation1 = find(Simulation.class, id);
        setSimulationStats(simulation1);
        return simulation1;
    }

    @Override
    public void searchPackage(int i) {
        Order order = find(Order.class, i);
        setOrderStats(order);
    }



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

    public void setOrderStats(Order order) {
        ui.updateOrdStat1(order.getOrderID());
        ui.updateOrdStat2(order.getSimulationID());
        ui.updateOrdStat3(order.getOrderNumber());
        ui.updateOrdStat4(order.getArrivalTime());
        ui.updateOrdStat5(order.getCompletionTime());
        ui.updateOrdStat6(order.getProcessingTime());
    }


    public void showAverageTime(double time){
		Platform.runLater(() ->ui.setAverageTime(time));
	}

	@Override
	public void showTotalShipped(int orders){
		Platform.runLater(() ->ui.setReadyOrders(orders));
	}

    public double getAverageTime() {
        return simulation.getAverageTime();
    }


    // Tietokantatoimintoja:
    // Tallenna uusi simulaatioistunto tietokantaan aina kun käyttäjä painaa start-nappia
    // Tallenna uusi lähetys tietokantaan aina kun paketti saapuu orderhandlerille
    @Override
    public <T> T save(T entity) {
        if (entity instanceof Simulation) {
            sdao.persist((Simulation) entity);
        } else if (entity instanceof Order) {
            odao.persist((Order) entity);
        }
        return entity;
    }

	// Päivitä simulaation ja lähetyksen tietoja aina kun uusi lähetys valmistuu
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

    @Override
    public <T> T find(Class<T> type, int id) {
        if (type == Simulation.class) {
            return (T) sdao.find(id);
        } else if (type == Order.class) {
            return (T) odao.find(id);
        }
        return null;
    }

    @Override
    public <T> List<T> findAll(Class<T> type) {
        if (type == Simulation.class) {
            return (List<T>) sdao.findAll();
        } else if (type == Order.class) {
            return (List<T>) odao.findAll();
        }
        return new ArrayList<>();
    }
@Override
public void setEdit(){
        ui.setEdit();
}


    @Override
    public int getOrdHndlAmount() {
        return ui.getOrderHandlers();
    }

    @Override
    public int getWarehouseAmount() {
        return ui.getWarehousers();
    }

    @Override
    public int getPackagerAmount() {
        return ui.getPackagers();
    }

    @Override
    public int getPickupInterval() {
        return ui.getPickupInterval();
    }

    @Override
    public double getSimulationTime() {
        return ui.getTime();
    }

	@Override
    public int getOrderInterval(){
		return ui.getOrderInterval();
	}

    @Override
    public Simulation getSimulation() {
        return simulation;
    }
    @Override
    public void populatePastSimulations() {
        ui.addSimulationToHistory(findAll(Simulation.class));
    }

}
