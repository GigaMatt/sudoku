package cs3331.example;
import java.util.Calendar;

import cs3331.example.DigitalClock;

public class CuckooDigitalClock extends DigitalClock{
	public CuckooDigitalClock(String[] args) {
		super(args);
		// TODO Auto-generated constructor stub
	}

	/* Play Audio at the top of the hour*/
	@Override
	public void play(String file) {
		Calendar.getCalendar();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		

		play("cuckoo.au");

	}

}
