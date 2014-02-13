package com.spinninggangstaz.stickyspeech;

import android.util.Log;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDB {
	static List<Message> msgList = new ArrayList<Message>();
	static final String dbDirectory = "memoData";
	static final String dbFile = "memoData/memoData.txt";
	
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
	
	public static List<Message> getList() {
		return msgList;
	}

    public static void saveMessages()
    {
        try {
            writeFile();
        }
        catch (IOException ioe) {
            Log.w("StickySpeech", "Unable to write to file " + dbFile + ": " + ioe.getMessage());
        }
    }

	private static void writeFile() throws IOException, FileNotFoundException {
		FileOutputStream fout = null;
		ObjectOutputStream out = null;
		File contextDir = StickySpeechApplication.getAppContext().getFilesDir();
		File dir = new File(contextDir, dbDirectory);
		File file = new File(contextDir, dbFile);
		
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

    public static void loadMessages()
    {
        try {
        	loadFromFile();
        }
        catch (Exception ex) {
            Log.w("stickyspeech", "exception in Message DB load : " + ex.getMessage());
        }
    }

	@SuppressWarnings("unchecked")
	private static void loadFromFile() throws FileNotFoundException,
			StreamCorruptedException, IOException, OptionalDataException,
			ClassNotFoundException {
		File file = new File(StickySpeechApplication.getAppContext().getFilesDir(), dbFile);
		msgList.clear();
		if (file.exists() && file.length() > 0) {
			InputStream reader = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(reader);
		    List<Message> loadedList = (ArrayList<Message>)in.readObject();
		    in.close();

			msgList.addAll(loadedList);
		}
	}


}
