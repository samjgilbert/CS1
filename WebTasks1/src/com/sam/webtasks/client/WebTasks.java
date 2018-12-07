package com.sam.webtasks.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class WebTasks implements EntryPoint {
	public void onModuleLoad() {
		// set the sequence handler to the initialisation loop and start from the beginning
		SequenceHandler.SetLoop(1,true); 
		SequenceHandler.Next();
	}
}
