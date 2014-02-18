package com.spinninggangstaz.stickyspeech;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.ocpsoft.prettytime.PrettyTime;

public class HoursAgoDateFormatter implements NoteDateFormatter {

	@Override
	public String getFormattedDate(Note note) {
		PrettyTime prettyFormatter = new PrettyTime(new Date());
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

		String strDate = sdf.format(note.getDate().getTime());
		
		
		//String niceDate = note.getDate().getTime()
		return prettyFormatter.format(note.getDate().getTime())
				+ "    (" + strDate + ")";
		
		//note.getDate().getTime().
	}

}
