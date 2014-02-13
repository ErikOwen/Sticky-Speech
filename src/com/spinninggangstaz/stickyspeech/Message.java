package com.spinninggangstaz.stickyspeech;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Message implements Serializable{
	String text;
	Date date;
    private static final long serialVersionUID = 7526471155622776147L;
	
	public Message(String text) {//, Date date) {
		this.text = text;
		//this.date = date;
	}
	
	public String toString() {
		return this.text;
	}
	
	public void editText(String newText) {
		this.text = newText;
	}
}
