package com.spinninggangstaz.stickyspeech;

import java.util.Date;
import org.ocpsoft.prettytime.PrettyTime;

public class HoursAgoDateFormatter implements NoteDateFormatter {

	@Override
	public String getFormattedDate(Note note) {
		PrettyTime prettyFormatter = new PrettyTime(new Date());
		return prettyFormatter.format(note.getDate().getTime())
				+ " (" + note.getDate().getTime().toString() + ")";
	}

}
