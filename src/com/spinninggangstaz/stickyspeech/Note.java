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
	private boolean customTitle;
	public static final int titleLength = 20, maxDefaultTitleLength = 50;
    private static final long serialVersionUID = 7526471155622776147L;
	
	public Note(String text) {
		this.text = text;
		this.title = text;
		this.customTitle = false;
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
		if(this.customTitle == false) {
			setTitle();
		}
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

    	if(this.text.length() < maxDefaultTitleLength) {
    		this.title = this.text;
    	}
    	else {
    		this.title = this.text.substring(0, maxDefaultTitleLength) + "...";
    	}
    	if(this.title.contains("\n")) {
    		this.title = this.title.substring(0, this.title.indexOf('\n'));
    	}
    }
    
    public void setTitle(String titleString) {
    	this.title = titleString;
    	this.customTitle = true;
    }
    
    public String getTitle() {
    	return this.title;
    }
}
