package controller;

import javafx.application.Platform;
import simu.framework.IEngine;
import simu.model.MyEngine;
import simu.model.Order;
import view.ISimulatorUI;

public class Controller implements IControllerForEng, IControllerForView {   // UUSI
	
	private IEngine engine;
	private ISimulatorUI ui;
	
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
		ui.getVisualization1().clearScreen();
		ui.getVisualization2().clearScreen();
		ui.getVisualization3().clearScreen();
		ui.getVisualization4().clearScreen();
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
	public void visualizeArrival() {
		Platform.runLater(new Runnable(){
			public void run(){
				ui.getVisualization1().newPackage();
			}
		});
	}

	@Override
	public void visualizeWarehouse() {
		Platform.runLater(new Runnable(){
			public void run(){
				ui.getVisualization2().newPackage();
			}
		});
	}

	@Override
	public void visualizePacking() {
		Platform.runLater(new Runnable(){
			public void run(){
				ui.getVisualization3().newPackage();
			}
		});
	}

	@Override
	public void visualizeShipping() {
		Platform.runLater(new Runnable(){
			public void run(){
				ui.getVisualization4().newPackage();
			}
		});
	}



	public void showAverageTime(double time){
		Platform.runLater(() ->ui.setAverageTime(time));
		//ui.setAverageTime(Order.getAverageTime());
	}

	public double getAverageTime(){
		return Order.getAverageTime();
	}



}
