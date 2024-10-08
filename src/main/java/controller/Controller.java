package controller;

import dao.OrderDAO;
import dao.SimulationDAO;
import javafx.application.Platform;
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

	
	public Controller(ISimulatorUI ui) {
		this.ui = ui;
	}


	
	// Moottorin ohjausta:
		
	@Override
	public void startSimulation() {
		engine = new MyEngine(this); // luodaan uusi moottorisäie jokaista simulointia varten
		engine.setSimulationTime(ui.getTime());
		engine.setDelay(ui.getDelay());
		engine.makeWorkers(ui.getOrderHandlers(), ui.getWarehousers(), ui.getPackagers());//UI:sta saadut arvot
		ui.getVisualization().clearScreen();
		((Thread) engine).start();
		//((Thread)moottori).run(); // Ei missään tapauksessa näin. Miksi?		
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
	public void visualizeOrder() {
		Platform.runLater(new Runnable(){
			public void run(){
				ui.getVisualization().newPackage();
			}
		});
	}



	// Tietokantatoimintoja:

	// Tallenna uusi simulaatioistunto tietokantaan aina kun käyttäjä painaa start-nappia
	// Tallenna uusi lähetys tietokantaan aina kun paketti saapuu orderhandlerille
	@Override
	public <T> void save(T entity) {
		if (entity instanceof Simulation) {
			sdao.persist((Simulation) entity);
		} else if (entity instanceof Order) {
			odao.persist((Order) entity);
		}
	}

	// Päivitä simulaation ja lähetyksen tietoja aina kun uusi lähetys valmistuu
	@Override
	public void update(int simulationID, int orderID, double completionTime) {
		Order o = odao.find(orderID);
		o.setCompletionTime(completionTime);
		o.setProcessingTime();
		odao.update(o);

		Simulation s = sdao.find(simulationID);
		s.setPackages_processed(s.getPackages_processed() +1);
		s.setAvg_time(o.getProcessingTime());
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
}
