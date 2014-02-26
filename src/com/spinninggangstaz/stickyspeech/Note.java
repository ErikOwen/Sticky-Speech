package com.spinninggangstaz.stickyspeech;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *  A sticky note
 */
public class Note implements Serializable
{
	private String title;
	private String text;
	private Calendar date;
    private static final long serialVersionUID = 7526471155622776147L;
	
	public Note(String text) {
		this(text, new GregorianCalendar());
	}
	
	public Note(String text, Calendar date) {
		this.text = text;
		this.date = date;
	}
	
	public String toString() {
		return getTitle() + getDate().toString();
	}
	
	public String getText() {
		return this.text;
	}
	
	public void setText(String newText) {
		this.text = newText;
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
    
    public void setTitle(String titleString) {
    	this.title = titleString;
    }
    
    public String getTitle() {
    	if (this.title == null || this.title.length() == 0)
    		return this.text;
    	else
    		return title;
    }
}
