package com.spinninggangstaz.stickyspeech;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *  A sticky note
 */
public class Note implements Serializable{
	private String title;
	private String text;
	private Calendar date;
	private static final int titleLength = 12;
    private static final long serialVersionUID = 7526471155622776147L;
	
	public Note(String text) {
		this.text = text;
		this.title = text;
	}
	
	public Note(String text, Calendar date) {
		this.text = text;
		this.setTitle();
		this.date = date;
	}
	
	public String toString() {
		return this.text;
	}
	
	public void editText(String newText) {
		this.text = newText;
		setTitle();
	}
	
	public void setDate(Calendar setDate) {
		this.date = setDate;
	}

    public Calendar getDate() {
    	if (this.date == null) {
    		this.date = new GregorianCalendar();
    	}
        return this.date;
    }
    
    private void setTitle() {
    	if(this.text.length() < titleLength) {
    		this.title = this.text;
    	}
    	else {
    		this.title = this.text.substring(0, titleLength) + "...";
    	}
    	if(this.title.contains("\n")) {
    		this.title = this.title.substring(0, this.title.indexOf('\n'));
    	}
    }
    
    public String getTitle() {
    	return this.title;
    }
}
