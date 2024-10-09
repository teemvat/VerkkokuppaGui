package view;

public interface ISimulatorUI {
	
	// Kontrolleri tarvitsee syötteitä, jotka se välittää Moottorille
	public double getTime();
	public long getDelay();
	
	//Kontrolleri antaa käyttöliittymälle tuloksia, joita Moottori tuottaa 
	public void setEndTime(double time);


	//TODO: make UI methods for setting the number of workers to -> controller -> engine

	int getOrderInterval();

	public int getOrderHandlers();
	public int getWarehousers();
	public int getPackagers();
	public int gerOrderInterval();
	public int getPickupInterval();

    void setReadyOrders(int ordercount);

	void setSimuProgress(double d);

	public IVisualization getVisualization1();
	public IVisualization getVisualization2();
	public IVisualization getVisualization3();
	public IVisualization getVisualization4();

	void setAverageTime(double time);


	void setLock();

	void setEdit();
}
