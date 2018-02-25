package cs3331.example;

import java.awt.*;

public class GrowBall extends AnimationApplet {

    public void paint(Graphics g){
        g.setColor(Color.GREEN);
        Dimension dim = getSize();
        int x = dim.width/2;
        g.fillOval(x, x, x, x);
    }

    public void init(){

    }

}
