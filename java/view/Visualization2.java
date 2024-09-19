package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Visualization2 extends Canvas implements IVisualization {
	
	private GraphicsContext gc;
	
	int asiakasLkm = 0;

	public Visualization2(int w, int h) {
		super(w, h);
		gc = this.getGraphicsContext2D();
		clearScreen();
	}
	

	public void clearScreen() {
		gc.setFill(Color.YELLOW);
		gc.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
	
	public void newPackage() {
		
		asiakasLkm++;
		
		gc.setFill(Color.YELLOW);
		gc.fillRect(100,80, 100, 20);
		gc.setFill(Color.RED);
		gc.setFont(new Font(20));
		gc.fillText("Package " + asiakasLkm, 100, 100);
		
	}
	

}
