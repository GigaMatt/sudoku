/**
 * Matthew S Montoya
 * CS 3331
 * Dr. Yoonsik Cheon
 * Exam 1 Extra Credit
 * Last modified 28 March 2018
 */
import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.Timer;

public class SolarSystemApplet extends Applet{

	protected Dimension dim;				//screen dimension
	private Timer timer; 					//animation timer
	private Sun sun;
	//private OrbitingBody orbitingBody = new OrbitingBody(radius, radius, color);
	Collection <SolarSystemApplet> planets;


	//attributes of the orbiting structures
	private int x, y;						//current position
	private int radius = 20;
	protected Color color = Color.ORANGE;	//sets to red by default \/ unknown background color


	//Initalize the orbiting objects and the animation timer
	public void init() {
		dim = getSize();
		sun = createSun();
		//orbitingBody = createOrbitingBody();
		x = dim.width/2;
		y = dim.height/2;
		timer = new Timer(100, e -> repaint());
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
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