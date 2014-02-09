package com.spinninggangstaz.stickyspeech;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.*;
import java.util.ArrayList;

public class MessageDB {
	static ArrayList<Message> msgList;
	
	public static void addMessage(Message msg) {
		if(msgList == null) {
			msgList = new ArrayList<Message>();
		}
		msgList.add(msg);
	}
	
	public static void deleteMessage(Message msg) {
		msgList.remove(msg);
	}
	
	public static ArrayList<Message> getList() {
		return msgList;
	}

    public static void saveMessages()
    {
        FileOutputStream fout = null;
        ObjectOutputStream out = null;
        try {
        	File file = new File("memoData/memoData.txt");
        		fout = new FileOutputStream(file);
                    
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

    public static void loadMessages()
    {
        ObjectInputStream in = null;
        FileInputStream fis = null;
        ArrayList<Message> list = null;
        try {
        	File file = new File("memoData/memoData.txt");
        	boolean empty = !file.exists() || file.length() == 0;
        	if(!empty) {
            	InputStream reader = StickySpeechApplication.getAppContext()
                	.getAssets().open("memoData/memoData.txt");

                in = new ObjectInputStream(reader);
                list = (ArrayList<Message>)in.readObject();
                in.close();
                fis.close();
        	}
        }
        catch (Exception ex) {
            System.out.println("Error in get method");
            Log.w("stickyspeech", "exception in Message DB load : " + ex.getMessage());
        }
        if(list != null) {
            msgList = list;
        }
        else {
            msgList = new ArrayList<Message>();
        }

    }


}
