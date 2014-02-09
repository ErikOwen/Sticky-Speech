package com.spinninggangstaz.stickyspeech;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.*;
import java.util.ArrayList;

public class MessageDB {
	static ArrayList<Message> msgList;
	private static boolean createdList = false;
	
	public static void addMessage(Message msg) {
		msgList.add(msg);
	}
	
	public static void deleteMessage(Message msg) {
		msgList.remove(msg);
	}
	
	public static void editMessage(int index, String str)
	{
		msgList.get(index).editText(str);
	}
	
	public static ArrayList<Message> getList() {
		return msgList;
	}

    public static void saveMessages()
    {
        FileOutputStream fout = null;
        ObjectOutputStream out = null;
        try {
        	File dir = new File(StickySpeechApplication.getAppContext().getFilesDir(), "memoData");
        	File file = new File(StickySpeechApplication.getAppContext().getFilesDir(), "memoData/memoData.txt");
        	
        	if(!dir.isDirectory()) {
        		dir.mkdir();
        	}
        	if(!file.isFile()) {
        		file.createNewFile();
        	}
        	fout = new FileOutputStream(file);
                    
        	out = new ObjectOutputStream(fout);
        	out.writeObject(msgList);
        	out.flush();
        	out.close();
        	fout.close();
        }
        catch (IOException ioe) {
            Log.w("StickySpeech", "Unable to write to file.");
        }
    }

    public static void loadMessages()
    {
        ObjectInputStream in = null;
        FileInputStream fis = null;
        ArrayList<Message> list = null;
        try {
        	File file = new File(StickySpeechApplication.getAppContext().getFilesDir(), "memoData/memoData.txt");
        	if(file.exists() && file.length() > 0) {
            	InputStream reader = new FileInputStream(file);
                in = new ObjectInputStream(reader);
                list = (ArrayList<Message>)in.readObject();
                in.close();
                
                if(createdList == false) {
                	createdList = true;
                	msgList = new ArrayList<Message>();
                }
                msgList.clear();
            	msgList.addAll(list);
        	}
        	else {
        		msgList = new ArrayList<Message>();
        		createdList = true;
        	}
        }
        catch (Exception ex) {
            Log.w("stickyspeech", "exception in Message DB load : " + ex.getMessage());
        }
    }


}
