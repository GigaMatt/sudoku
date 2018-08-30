/**
 * Matthew S Montoya
 * CS 3331
 * Dr. Yoonsik Cheon
 * Exam 1 Extra Credit
 * Last modified 28 March 2018
 */
import java.awt.Color;
import java.awt.Graphics;

public class Planet extends OrbitingBody implements GetParams {
	
	public Planet(int x, int y, Color color) {
		//super(x, y, color);
		// TODO Auto-generated constructor stub
	}

	String name;
	int planetNumber;
	

	

	
	
	//Create Planet to animate
	protected OrbitingBody orbitingBody() {
		return null;
		//return new OrbitingBody(dim.width/2, dim.height/2, Color.BLUE);
	}
	
}
