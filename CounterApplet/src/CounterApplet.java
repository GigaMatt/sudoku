import java.applet.Applet;

import javax.swing.JLabel;
import javax.swing.*;
import java.awt.event.*;

public class CounterApplet extends Applet{


	private Counter counter = new Counter();
	private JTextField display = new JTextField(4);

	public void init() {
		add(new JLabel("Value:"));
		add(display);//adds text field
		display.setText(Integer.toString(counter.value()));//shows initial value of 0
		JButton button = new JButton("Increment");
		add(button);//adds button

		button.addActionListener(new MyActionListener());

	}

	private class MyActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			//System.out.println("Button Clicked!");
			counter.increment();//increments as we press the button
			display.setText(Integer.toString(counter.value()));
		}

	}

	public class Counter{
		private int value;

		public void increment() {
			value++;//increments by 1
		}

		public int value() {
			return value;
		}
	}
}