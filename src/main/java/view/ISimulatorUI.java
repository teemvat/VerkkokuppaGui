package view;

import simu.model.entity.Simulation;

import java.util.List;

public interface ISimulatorUI {
	
	// Kontrolleri tarvitsee syötteitä, jotka se välittää Moottorille
	public double getTime();
	public long getDelay();
	
	//Kontrolleri antaa käyttöliittymälle tuloksia, joita Moottori tuottaa 
	public void setEndTime(double time);


	//TODO: make UI methods for setting the number of workers to -> controller -> myengine


	public int getOrderHandlers();
	public int getWarehousers();
	public int getPackagers();
	public int getOrderInterval();

	public int getPickupInterval();

    void setReadyOrders(int ordercount);

	void setSimuProgress(double d);
	// Kontrolleri tarvitsee
	public IVisualization getVisualization1();
	public IVisualization getVisualization2();
	public IVisualization getVisualization3();
	public IVisualization getVisualization4();

	void setAverageTime(double time);

    void setLock();
    int getSimulationID();

    void newHistoryWindow();


	void updateSimuStat1(int id);

	void updateSimuStat2(double time);

	void updateSimuStat3(double oInterval);

	void updateSimuStat4(double puInterval);

	void updateSimuStat5(int oh);

	void updateSimuStat6(int wh);

	void updateSimuStat7(int pck);

	void updateSimuStat8(int pReceived);

	void updateSimuStat9(int pProcessed);

	void updateSimuStat10(double avgTime);

	void updateOrdStat1(int id);

	void updateOrdStat2(int simID);

	void updateOrdStat3(int oNum);

	void updateOrdStat4(double arrivalTime);

	void updateOrdStat5(double completionTime);

	void updateOrdStat6(double processingTime);

	void addSimulationToHistory(List<Simulation> sims);


    void setEdit();
}
