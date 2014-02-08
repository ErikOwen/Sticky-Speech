package com.spinninggangstaz.stickyspeech;

import android.content.Context;

import java.io.*;
import java.util.ArrayList;

public class MessageDB{
	
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

    public void saveMessages() throws IOException
    {
        FileOutputStream fout = null;
        ObjectOutputStream out = null;
        try {
            fout = new FileOutputStream("res" + System.getProperty("file.separator") + "memoData"
                + System.getProperty("file.separator") + "memoData.txt");
            out = new ObjectOutputStream(fout);
            out.writeObject(msgList);
            out.flush();
            out.close();
        }
        catch (IOException ioe) {
            System.out.println("Error in save method");
        }
        finally {
            out.close();
            fout.close();
        }
    }

    public void loadMessages() throws IOException
    {
        ObjectInputStream in = null;
        FileInputStream fis = null;
        ArrayList<Message> list = null;
        try {
            fis = StickySpeechApplication.getAppContext().getApplicationContext().openFileInput("res"
                    + System.getProperty("file.separator") + "memoData"
                    + System.getProperty("file.separator") + "memoData.txt");
            in = new ObjectInputStream(fis);
            list = (ArrayList<Message>)in.readObject();
        }
        catch (Exception ex) {
            System.out.println("Error in get method");
        }
        finally {
            in.close();
            fis.close();
        }
        if(list != null) {
            msgList = list;
        }

    }


}
