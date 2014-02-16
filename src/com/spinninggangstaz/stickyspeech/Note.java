package com.spinninggangstaz.stickyspeech;

import java.io.Serializable;
import java.util.Calendar;

/**
 *  A sticky note
 */
public class Note implements Serializable{
	private String title;
	private String text;
	private Calendar date;
    private static final long serialVersionUID = 7526471155622776147L;
	
	public Note(String text) {
		this.text = text;
	}
	
	public Note(String text, Calendar date) {
		this.text = text;
		this.date = date;
	}
	
	public String toString() {
		return this.text;
	}
	
	public void editText(String newText) {
		this.text = newText;
	}
	
	public void setDate(Calendar setDate) {
		this.date = setDate;
	}

    public Calendar getDate() {
        return this.date;
    }
    
    public void setTitle(String title) {
    	this.title = title;
    }
    
    public String getTitle() {
    	return title;
    }
}
