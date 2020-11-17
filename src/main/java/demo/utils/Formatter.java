package demo.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Formatter {
	
	public static String getFormatedCurrentDateMinusYears(int years) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -years);
		Date d = cal.getTime();
		return sdf.format(d);
	}

}
