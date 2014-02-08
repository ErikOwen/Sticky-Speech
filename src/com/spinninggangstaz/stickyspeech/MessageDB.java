package com.spinninggangstaz.stickyspeech;

import android.content.Context;

import java.io.*;
import java.util.ArrayList;

public class MessageDB {
	ArrayList<Message> msgList;

	public MessageDB() {
		msgList = new ArrayList<Message>();
	}
	
	public void addMessage(Message msg) {
		msgList.add(msg);
	}
	
	public void deleteMessage(Message msg) {
		msgList.remove(msg);
	}
	
	public String toString() {
		return this.msgList.toString();
	}

    public void saveMessages()
    {
        FileOutputStream fout = null;
        ObjectOutputStream out = null;
        try {
            fout = new FileOutputStream("memoData"
                + System.getProperty("file.separator") + "memoData.txt");
            out = new ObjectOutputStream(fout);
            out.writeObject(msgList);
            out.flush();
            out.close();
            fout.close();
        }
        catch (IOException ioe) {
            System.out.println("Error in save method");
        }
    }

    public void loadMessages()
    {
        ObjectInputStream in = null;
        FileInputStream fis = null;
        ArrayList<Message> list = null;
        try {
            fis = StickySpeechApplication.getAppContext().getApplicationContext().openFileInput("memoData"
                    + System.getProperty("file.separator") + "memoData.txt");
            in = new ObjectInputStream(fis);
            list = (ArrayList<Message>)in.readObject();
            in.close();
            fis.close();
        }
        catch (Exception ex) {
            System.out.println("Error in get method");
        }
        if(list != null) {
            msgList = list;
        }
        else {
            msgList = new ArrayList<Message>();
        }

    }


}
