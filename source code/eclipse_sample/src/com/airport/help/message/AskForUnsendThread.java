package com.airport.help.message;

import com.airport.commons.GlobalMsgTypes;
import com.airport.network.NetworkService;



public class AskForUnsendThread extends Thread{
	
	@Override
	public void run() {
		try {
			sleep(500);
		} catch(Exception e) {}
		// to ask for unsend msgs
		NetworkService.getInstance().sendUpload(GlobalMsgTypes.msgAskForUnsendMsgs, "xxxxxx"); // because here this msg is not used
	}

}
