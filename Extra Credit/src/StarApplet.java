/**
 * Matthew S Montoya
 * CS 3331
 * Dr. Yoonsik Cheon
 * Exam 1 Extra Credit
 * Last modified 28 March 2018
 */


/**
 JUST DRAWS A BALL
 */
import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Collection;

import javax.swing.Timer;

public class StarApplet extends Applet {
	protected Dimension dim;				//screen dimension
	private Timer timer; 					//animation timer

	//attributes of the star
	private int x, y;						//current position
	private int radius = 20;
	protected Color color = Color.ORANGE;	//sets to red by default \/ unknown background color
	Collection <Planet> collection;

	//Initialize the ball and the animation timer
	public void init() {
		dim = getSize();
		x = dim.width/2;
		y = dim.height/2;
		timer = new Timer(100, e -> repaint());
		for(i<0)...
			create planets and put them in list then paint them
	} 

	//Paint the circle
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, dim.width, dim.height);
		g.setColor(color);
		g.fillOval(x-radius, y-radius, radius*2, radius*2);
	}

	//Create sun to animate
	protected Sun createSun() {
		return new Sun(dim.width/2, dim.height/2, Color.ORANGE);
	}

	//Overridden here to start the animation timer
	public void start() {
		timer.start();
	}

	//Overridden here to stop the animation timer
	public void stop() {
		timer.stop();
	}

}
