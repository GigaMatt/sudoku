package undoapplet;

import java.awt.*;
import java.awt.event.*;
import com.sun.java.swing.*;


/**
* The lunch applet presents the Lunch button, which, when invoked
* lunches the main frame
*
* @author Tomer Meshorer
*/
public class LunchApplet extends JApplet {

  //Initialize the applet
  public void init() {
       // set look & feel
     String laf = UIManager.getSystemLookAndFeelClassName();
     try {
	UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
     } catch ( UnsupportedLookAndFeelException exc ) {
	System.err.println("Warning: UnsupportedLookAndFeel: " + laf);
     } catch (Exception exc) {
	System.err.println("Error loading " + laf + ": " + exc);
     }

     JButton lunchBtn = new JButton("Lunch");
     lunchBtn.addActionListener( new ActionListener () {
        public void actionPerformed( ActionEvent evt ) {
       	   UndoPanel  app = new UndoPanel();
   	   JFrame frame  = new JFrame();
   	   frame.getContentPane().add(app);
   	   frame.setSize(600,300);
   	   frame.setVisible(true);

     }});
     getContentPane().add(lunchBtn);
  }

  //Start the applet
  public void start() {
  }

  //Stop the applet
  public void stop() {
  }

  //Destroy the applet
  public void destroy() {
  }


}

