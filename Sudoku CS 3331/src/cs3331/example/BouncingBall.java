package cs3331.example;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.Timer;
import cs3331.noapplet.NoApplet;

/**
 * Sample NoApplet showing a ball moving inside a rectangular box.
 * See Section 5.5.3 on pages 195-197.
 */
@SuppressWarnings("serial")
public class BouncingBall extends NoApplet {

    private Color color = Color.GREEN;
    private int radius = 20;
    private int x, y;
    private int dx = -2, dy = -4;

    private Image image;
    private Graphics offScreen;
    private Dimension dim;

    private Timer timer;
    private int delay = 10;

    @Override
    public void init() {
        String param = getParameter("delay");
        if (param != null) {
            delay = Integer.parseInt(param);
        }
        dim = getSize();
        x = dim.width * 2 / 3;
        y = dim.height - radius;
        timer = new Timer(delay, e -> repaint());
    }

    @Override
    public void update(Graphics g) {
        // create an off-screen image and its grapchics
        if (image == null) {
            image = createImage(dim.width, dim.height);
            offScreen = image.getGraphics();
        }

        // fill the background
        offScreen.setColor(Color.white);
        offScreen.fillRect(0, 0, dim.width, dim.height);

        // adjust the position of the ball
        if (x < radius || x > dim.width - radius) {
            dx = -dx;
        }
        if (y < radius || y > dim.height - radius) {
            dy = -dy;
        }
        x += dx;
        y += dy;

        // draw the ball and dump the off-screen image
        offScreen.setColor(color);
        offScreen.fillOval(x - radius, y - radius, radius * 2, radius * 2);
        g.drawImage(image,  0,  0,  this);
    }

    @Override
    public void paint(Graphics g) {
        update(g);
    }

    /** Start the animation. */
    @Override
    public void start() {
        timer.start();
    }

    /** Stop the animation. */
    @Override
    public void stop() {
        timer.stop();
    }

    public static void main(String[] args) {
        new BouncingBall().run();
    }
}