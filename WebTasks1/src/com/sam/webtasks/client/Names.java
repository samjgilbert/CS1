package com.sam.webtasks.client;

public class Names {
	//reminder conditions
	public final static int REMINDERS_NOTALLOWED=0;
	public final static int REMINDERS_OPTIONAL=1;
	public final static int REMINDERS_MANDATORY=2;
		
	//Gender
	public final static int GENDER_MALE=0;
	public final static int GENDER_FEMALE=1;
	public final static int GENDER_OTHER=2;
	
	//policy for dealing with participants who have already accessed the task
	//who can take part?
	public final static int ELIGIBILITY_ANYONE=0; //anyone can take part
	public final static int ELIGIBILITY_NEVERCOMPLETED=1; //can only take part if you haven't completed the experiment
	public final static int ELIGIBILITY_NEVERACCESSED=2;  //can only take part if you haven't accessed the task
}

