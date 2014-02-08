package com.spinninggangstaz.stickyspeech;

import java.util.Date;

public class Message {
	
	String text;
	Date date;
	
	public Message(String text, Date date) {
		this.text = text;
		this.date = date;
	}
	
	public void editText(String newText) {
		this.text = newText;
	}

}
