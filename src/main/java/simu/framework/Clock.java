package simu.framework;

/**
 * Clock class for simulation time
 * Singleton pattern
 */
public class Clock {

	private double time;
	private static Clock instance;
	
	private Clock(){
		time = 0;
	}

	/**
	 * Gets the instance of the Clock. If the instance is null, creates a new instance.
	 * @return the instance of the Clock
	 */
	public static Clock getInstance(){
		if (instance == null){
			instance = new Clock();
		}
		return instance;
	}
	
	public void setTime(double time){
		this.time = time;
	}

	public double getTime(){
		return time;
	}
}
