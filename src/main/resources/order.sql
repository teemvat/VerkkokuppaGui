DROP DATABASE IF EXISTS simulator;
CREATE DATABASE simulator;
USE simulator;

CREATE TABLE SIMULATION (
	simulation_id INT AUTO_INCREMENT PRIMARY KEY,
	simulation_time FLOAT NOT NULL,
	order_interval FLOAT NOT NULL,
	orderhandlers INT NOT NULL,
	warehousers INT NOT NULL,
	packagers INT NOT NULL,
	shippers INT NOT NULL,
	packages_processed INT NOT NULL,
	avg_time FLOAT NOT NULL
);

CREATE TABLE PROCESSED_ORDER (
	order_id INT AUTO_INCREMENT PRIMARY KEY,
	simulation_id INT NOT NULL,
	order_number INT NOT NULL,
	arrival_time FLOAT NOT NULL,
	completion_time FLOAT NOT NULL,
	processing_time FLOAT NOT NULL,
	FOREIGN KEY (simulation_id) REFERENCES SIMULATION(simulation_id)
);
