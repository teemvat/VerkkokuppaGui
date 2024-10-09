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
		engine = new MyEngine(this); // luodaan uusi moottorisäie jokaista simulointia varten
		simulation = save(new Simulation(this));
		engine.setSimulationTime(ui.getTime());
		engine.setDelay(ui.getDelay());
		engine.makeWorkers(ui.getOrderHandlers(), ui.getWarehousers(), ui.getPackagers());//UI:sta saadut arvot
		ui.getVisualization1().clearScreen();
		ui.getVisualization2().clearScreen();
		ui.getVisualization3().clearScreen();
		ui.getVisualization4().clearScreen();
		((Thread) engine).start();
		//((Thread)moottori).run(); // Ei missään tapauksessa näin. Miksi?
	}

	public int getOrderHandlers() {
		return ui.getOrderHandlers();
	}

	public int getWarehousers() {
		return ui.getWarehousers();
	}

	public int getPackagers() {
		return ui.getPackagers();
	}

	
	@Override
	public void slow() { // hidastetaan moottorisäiettä
		engine.setDelay((long)(engine.getDelay()*1.10));
	}

	@Override
	public void fast() { // nopeutetaan moottorisäiettä
		engine.setDelay((long)(engine.getDelay()*0.9));
	}
	
	
	
	// Simulointitulosten välittämistä käyttöliittymään.
	// Koska FX-ui:n päivitykset tulevat moottorisäikeestä, ne pitää ohjata JavaFX-säikeeseen:
		
	@Override
	public void showEndTime(double time) {
		Platform.runLater(()->ui.setEndTime(time));
	}

	
	@Override
	public void visualizeArrival() {
		Platform.runLater(new Runnable(){
			public void run(){
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
		Platform.runLater(new Runnable(){
			public void run(){
				ui.getVisualization3().newPackage();
				ui.getVisualization2().movePackage();
			}
		});
	}

	@Override
	public void visualizeShipping() {
		Platform.runLater(new Runnable(){
			public void run(){
				ui.getVisualization4().newPackage();
				ui.getVisualization3().movePackage();
			}
		});
	}

	@Override
	public void showProgress(){
		double maxTime = ui.getTime();
		double currentTime = Clock.getInstance().getTime();
		ui.setSimuProgress(currentTime / maxTime);
	}



	public void showAverageTime(double time){
		Platform.runLater(() ->ui.setAverageTime(time));
	}

	@Override
	public void showTotalShipped(int orders){
		Platform.runLater(() ->ui.setReadyOrders(orders));
	}

	public double getAverageTime(){
		return simulation.getAverageTime();
	}



	// Tietokantatoimintoja:
	// TODO etsi oikeat kohdat näille metodikutsuille

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
		s.setPackagesProcessed(s.getPackagesProcessed() +1);
		s.updateAverageTime(o.getProcessingTime());
		ui.setAverageTime(s.getAverageTime()); // miksä tää ei toimi?
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
	public double getInterval() {
		return ui.getOrderHandlers();
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
	public Simulation getSimulation() {
		return simulation;
	}
}
