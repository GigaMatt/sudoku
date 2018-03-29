/**
 * Matthew S Montoya
 * CS 3331
 * Dr. Yoonsik Cheon
 * Exam 1 Extra Credit
 * Last modified 28 March 2018
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.Timer;
import java.applet.Applet;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrbitingBody implements GetParams{

	protected OrbitingBody createBody() {
		return null;
		//return new OrbitingBody(dim);
	}
	
	private Dimension dim;	//Screen Dimensions
	private Color color = Color.GREEN;
	private int radius = 20;
	
	private int angle;	//Angle (in degrees) of the orbiting body
	

	public void calculateX() {
		int center = dim.width/2;
		
		
		//STEPS TO MAKE THIS HAPPEN
		//x and y increment
		//then you calculate them
		//then you fill them
		return (int)(center+distance*Math.cos(Math.toRadians(angle)));	//distance is distance from the center of the planet. We need dx and dy to make this happen. Reference images

	}

	public void calculateY() {
		int center = dim.height/2;
		return (int)(center+distance*Math.cos(Math.toRadians(angle)));
	}

	//might need getters and setters

	////////////////////////////////////////////////////////////
	/*
	protected Dimension dim;				//screen dimension
	private Timer timer; 					//animation timer

	//attributes of the orbiting structures
	private int x, y;						//current position
	private int radius;
	protected Color color = Color.WHITE;


	public void init() {
		dim = getSize();
		//sun = createSun();
		x = dim.width/2;
		y = dim.height/2;
		timer = new Timer(100, e -> repaint());
	}

	public OrbitingBody(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}

	public void paint(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, dim.width, dim.height);
		g.setColor(color);
		g.fillOval(x-radius, y-radius, radius*2, radius*2);
	}
	 */
}
