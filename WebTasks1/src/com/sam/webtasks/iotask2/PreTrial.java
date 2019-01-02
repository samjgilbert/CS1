package com.sam.webtasks.iotask2;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PreTrial {
	//TODO: measure choice time
	
	final HTML displayText = new HTML();
    final HorizontalPanel horizontalPanel = new HorizontalPanel();
    final VerticalPanel verticalPanel = new VerticalPanel();
    final HorizontalPanel buttonPanel = new HorizontalPanel();
    public String displayString = "";
    
    if (IOtask2BlockContext.showPoints()) {
    	displayString = displayString + "You have scored a total of " + IOtask2BlockContext.getTotalPoints() + " points so far.<br><br>";
    }

    int points = IOtask2BlockContext.currentTargetValue();
    
    if (points == 0) {
        displayString = displayString + "This time you must do the task without setting any reminders.<br><br>"
                + "Please touch the button below to start.";
    } else if (points == 10) {
        displayString = displayString + "This time you <b>must</b> set a reminder for every special circle.<br><br>"
                + "Please touch the button below to start.";
    } else {
        displayString = displayString + "This time you have a choice.<br><br>Please touch the option that you prefer.<br><br>";
    }

    displayText.setHTML(displayString);

    final Button reminderButton = new Button("Special circles worth<br><b>" + points + " </b>points<br><br>"
            + "Reminders allowed");

    if (points == 10) {
        reminderButton.setHTML("Special circles worth<br><b>" + points + " </b>points<br><br>"
                + "You <b>must</b> set reminders");
    }

    final Button noReminderButton = new Button("Special circles worth<br><b>" + 10 + " </b>points<br><br>"
            + "Reminders <b>not</b> allowed");

    if (colourCounterbalance==0) {
        reminderButton.setStyleName("pinkButton");
        noReminderButton.setStyleName("greenButton");
    } else {
        reminderButton.setStyleName("greenButton");
        noReminderButton.setStyleName("pinkButton");
    }

    if (order == 0) {
        if (points > 0) {
            buttonPanel.add(reminderButton);
        }

        if (points < targetValue) {
            buttonPanel.add(noReminderButton);
        }
    } else {
        if (points < targetValue) {
            buttonPanel.add(noReminderButton);
        }

        if (points > 0) {
            buttonPanel.add(reminderButton);
        }
    }

    //set up vertical panel
    verticalPanel.setWidth("75%");
    //verticalPanel.setHeight(Window.getClientHeight() + "px");
    verticalPanel.setHeight("300px");

    verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
    verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

    //add elements to panel
    displayText.setStyleName("instructionText");
    verticalPanel.add(displayText);
    verticalPanel.add(buttonPanel);

    //place vertical panel inside horizontal panel, so it can be centred
    horizontalPanel.setWidth(Window.getClientWidth() + "px");
    horizontalPanel.setHeight(Window.getClientHeight() + "px");

    horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
    horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

    horizontalPanel.add(verticalPanel);

    //add panel to root
    RootPanel.get().add(horizontalPanel);

    if ((points > 0) & (points < targetValue)) {
        reminderButton.setWidth(noReminderButton.getOffsetWidth() + "px");
    }
/*
    reminderButton.addClickHandler(new ClickHandler() {
        public void onClick(ClickEvent event) {
            noReminder = 0;

            Date responseTime = new Date();

            String phpString = "preTrial.php?participantID=";
            phpString = phpString + workerId + "&trialNum=" + trialNum + "&rewardValue=" + points;
            phpString = phpString + "&assignment=" + order + "&choice=0&RT=";
            phpString = phpString + ((int) (responseTime.getTime() - instructionStart.getTime()));

            if (showInstructions) {
                phpCall(phpString);
            }

            RootPanel.get().remove(horizontalPanel);

            blockParams.moveAny = true;
            currentValue = points;

            new Timer() {
                public void run() {
                    position[1]++;
                    trialHandler();
                }
            }.schedule(pauseDur);
        }
    });

    noReminderButton.addClickHandler(new ClickHandler() {
        public void onClick(ClickEvent event) {
            noReminder = 1;

            Date responseTime = new Date();

            String phpString = "preTrial.php?participantID=";
            phpString = phpString + workerId + "&trialNum=" + trialNum + "&rewardValue=" + points;
            phpString = phpString + "&assignment=" + order + "&choice=1&RT=";
            phpString = phpString + ((int) (responseTime.getTime() - instructionStart.getTime()));

            if (showInstructions) {
                phpCall(phpString);
            }

            RootPanel.get().remove(horizontalPanel);

            blockParams.moveAny = false;
            currentValue = targetValue;

            new Timer() {
                public void run() {
                    position[1]++;
                    trialHandler();
                }
            }.schedule(pauseDur);
        }
    });*/
}
