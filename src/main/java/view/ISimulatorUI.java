package view;

import simu.model.entity.Simulation;

import java.util.List;
/**
 * Interface for the Simulator UI.
 * Provides methods for the controller to interact with the UI and retrieve inputs.
 */
public interface ISimulatorUI {

	/**Time based values*/
	public double getTime();
	public long getDelay();
	void setAverageTime(double time);

    /**Servicepoint based values*/
	public int getOrderHandlers();
	public int getWarehousers();
	public int getPackagers();
	public int getOrderInterval();
	public int getPickupInterval();

    /**These are for simulation progress view*/
    void setReadyOrders(int ordercount);
	void setSimuProgress(double d);

    /**These are for visualisation*/
	public IVisualization getVisualization1();
	public IVisualization getVisualization2();
	public IVisualization getVisualization3();
	public IVisualization getVisualization4();
	void setLock();
	void setEdit();

	void newHistoryWindow();

	/**These are for history functionality*/
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

}
