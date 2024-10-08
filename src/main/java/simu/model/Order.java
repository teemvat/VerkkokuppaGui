package simu.model;

import simu.framework.Clock;
import simu.framework.Trace;

import java.util.Random;

// TODO:
// Asiakas koodataan simulointimallin edellyttämällä tavalla (data!)
public class Order {
	private double arrivalTime; // tilauksen saapumisaika
	private double endTime;
	private int id;
	private static int counter = 1; // antaa id:n
	private static long avgTime = 0; // palveluajan keskiarvo
	private int orderType; // 1 tai 2

	Random randy = new Random();
	
	public Order(){
	    id = counter++;

		orderType = randy.nextInt(2) + 1;
		arrivalTime = Clock.getInstance().getTime();
		//Trace.out(Trace.Level.INFO, "New order nro " + id + " arrived at "+ arrivalTime);
	}

	public double getEndTime() {
		return endTime;
	}

	public void setEndTime(double endTime) {
		this.endTime = endTime;
	}//this is set at INSHIPPING SERVICEPOINT

	public double getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(double arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getOrderType() {
		return orderType;
	}



	public int getId() {
		return id;
	}
	
	public void report(){
		Trace.out(Trace.Level.INFO, "\nOrders "+id+ " shipped today! ");
		Trace.out(Trace.Level.INFO, "Last order  "+id+ " arrived: " + arrivalTime);
		Trace.out(Trace.Level.INFO,"Last order "+id+ " exited: " + endTime);
		Trace.out(Trace.Level.INFO,"Last order "+id+ " stayed: " +(endTime - arrivalTime));
		avgTime += (endTime - arrivalTime);
		double average = avgTime/id;
		System.out.println("Orders average service time: "+ average);
	}
	public void reportAfterPackaging() {
		Trace.out(Trace.Level.INFO, "\nOrder " + id + " ready from packing! ");
	}

}
