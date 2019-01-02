package com.sam.webtasks.iotask2;

import java.util.Date;

import com.google.gwt.user.client.Window;
import com.sam.webtasks.client.Names;

public class IOtask2BlockContext {
	private static IOtask2Block blockContext;

	public static void setContext(IOtask2Block block) {
		blockContext = block;
	}

	public static IOtask2Block getContext() {
		return (blockContext);
	}

	// use these methods to set and find out the currently clicked circle, and check
	// offloading status
	public static void setClickedCircle(int clickedCircle) {
		blockContext.clickedCircle = clickedCircle;
	}

	public static int getClickedCircle() {
		return (blockContext.clickedCircle);
	}
	
	//find out the current total number of points
	public static int getTotalPoints() {
		return (blockContext.totalPoints);
	}
	
	//are we displaying points to participants?
	public static boolean showPoints() {
		return (blockContext.showPoints);
	}
	
	//what is the target value for the current trial?
	public static int currentTargetValue() {
		return (blockContext.targetValues.get(blockContext.currentTrial));
	}
	
	//what is the maximum number of points (i.e. for targets in the no reminder condition)
	public static int maxPoints() {
		return (blockContext.maxPoints);
	}
	
	//what is the current trial number
	public static int getTrialNum() {
		return (blockContext.currentTrial);
	}
	
	//offloading condition
	public static void setOffloadCondition(int newCondition) {
		blockContext.offloadCondition = newCondition;
	}
	
	//how many points per target will be scored?
	public static void setActualPoints(int newPoints) {
		blockContext.actualPoints = newPoints;
	}
	
	//post-trial feedback
	public static boolean showPostTrialFeedback() {
		return (blockContext.showPostTrialFeedback);
	}
}