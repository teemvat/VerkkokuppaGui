package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Visualization class is responsible for drawing the simulation.
 * It extends Canvas and implements IVisualization.
 */
public class Visualization extends Canvas implements IVisualization {

	private final GraphicsContext gc;

	double i = 0;
	double j = 10;


	public Visualization(int w, int h) {
		super(w, h);
		gc = this.getGraphicsContext2D();
		clearScreen();
	}


	/**
	 * Clear the screen by filling it with dark gray color.
	 */
	public void clearScreen() {
		gc.setFill(Color.DARKGRAY);
		gc.fillRect(0, 0, this.getWidth(), this.getHeight());
		i = 0;
		j = 10;
	}

	/**
	 * Draw a new package on the screen by filling an oval with red color.
	 * The oval is drawn at the coordinates (i, j) and its size is 10x10.
	 */
	public void newPackage() {
		gc.setFill(Color.RED);
		gc.fillOval(i,j,10,10);

		i = (i + 10) % this.getWidth();
		//j = (j + 12) % this.getHeight();
		if (i==0) j+=10;
	}

	/**
	 * Move the package on the screen:
	 * 1. Fill the current position of the package with dark gray color.
	 * 2. Move the package to the left by 10 pixels.
	 * 3. If the package has moved outside the screen, move it to the next row.
	 * 4. Fill the new position of the package with dark gray color.
	 * 5. Draw the package at the new position.
	 * 6. Update the position of the package.
	 */
	public void movePackage(){
		i = (i - 10) % this.getWidth();
		if (i<0) j-=10;

		gc.setFill(Color.DARKGRAY);
		gc.fillRect(i,j,10,10);
	}
}