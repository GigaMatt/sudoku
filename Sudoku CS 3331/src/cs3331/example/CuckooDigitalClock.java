package cs3331.example;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Calendar;
import javax.swing.Timer;

import cs3331.noapplet.NoApplet;

/**
 *  Sample NoApplet to display the current time continuously.
 *  See Section 4.7 on pages 149-154.
 */
@SuppressWarnings("serial")
public class CuckooDigitalClock extends DigitalClock {

    public CuckooDigitalClock(String[] args) {
        super(args);
    }

    public void paint(Graphics g){
        super.paint(g);
        play(getCodeBase(), "Audio/Cuckoo.wav");//this is probably wrong
    }

}