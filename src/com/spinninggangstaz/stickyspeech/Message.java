package com.spinninggangstaz.stickyspeech;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import android.text.format.Time;

public class Message implements Serializable{
	private String text;
	private Time date;
    private static final long serialVersionUID = 7526471155622776147L;
	
	public Message(String text) {
		this.text = text;
	}
	
	public Message(String text, Time date) {
		this.text = text;
		this.date = date;
	}
	
	public String toString() {
		return this.text;
	}
	
	public void editText(String newText) {
		this.text = newText;
	}
	
	public void setDate(Time setDate) {
		this.date = setDate;
	}
}
