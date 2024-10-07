package view;

public interface ISimulatorUI {
	
	// Kontrolleri tarvitsee syötteitä, jotka se välittää Moottorille
	public double getTime();
	public long getDelay();
	
	//Kontrolleri antaa käyttöliittymälle tuloksia, joita Moottori tuottaa 
	public void setEndTime(double time);


	//TODO: make UI methods for setting the number of workers-> controller
	public int getOrderHandlers();
	public int getWarehousers();
	public int getPackagers();

	// Kontrolleri tarvitsee  
	public IVisualization getVisualization();

}
