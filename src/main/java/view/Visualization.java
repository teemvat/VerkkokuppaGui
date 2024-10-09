package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Visualization extends Canvas implements IVisualization {

	private final GraphicsContext gc;
	
	double i = 0;
	double j = 10;
	
	
	public Visualization(int w, int h) {
		super(w, h);
		gc = this.getGraphicsContext2D();
		clearScreen();
	}
	

	public void clearScreen() {
		gc.setFill(Color.YELLOW);
		gc.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
	
	public void newPackage() {
		gc.setFill(Color.RED);
		gc.fillOval(i,j,10,10);
		
		i = (i + 10) % this.getWidth();
		//j = (j + 12) % this.getHeight();
		if (i==0) j+=10;			
	}

	public void movePackage(){
		i = (i - 10) % this.getWidth();
		if (i<0) j-=10;

		gc.setFill(Color.YELLOW);
		gc.fillRect(i,j,10,10);
	}
	
}
