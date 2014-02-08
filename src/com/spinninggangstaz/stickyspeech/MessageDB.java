package com.spinninggangstaz.stickyspeech;

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
}
