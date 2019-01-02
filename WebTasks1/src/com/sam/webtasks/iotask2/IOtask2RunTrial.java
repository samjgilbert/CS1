package com.sam.webtasks.iotask2;

import java.util.Date;

import com.ait.lienzo.client.core.animation.AnimationProperties;
import com.ait.lienzo.client.core.animation.AnimationTweener;
import com.ait.lienzo.client.core.animation.IAnimation;
import com.ait.lienzo.client.core.animation.IAnimationCallback;
import com.ait.lienzo.client.core.animation.IAnimationHandle;
import com.ait.lienzo.client.core.animation.AnimationProperty.Properties;
import com.ait.lienzo.client.core.event.NodeDragEndEvent;
import com.ait.lienzo.client.core.event.NodeDragEndHandler;
import com.ait.lienzo.client.core.event.NodeDragMoveEvent;
import com.ait.lienzo.client.core.event.NodeDragMoveHandler;
import com.ait.lienzo.client.core.event.NodeDragStartEvent;
import com.ait.lienzo.client.core.event.NodeDragStartHandler;
import com.ait.lienzo.client.core.event.NodeMouseDoubleClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseDoubleClickHandler;
import com.ait.lienzo.client.core.shape.Circle;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Line;
import com.ait.lienzo.client.core.shape.Text;
import com.ait.lienzo.client.core.types.DragBounds;
import com.ait.lienzo.client.widget.DragContext;
import com.ait.lienzo.client.widget.LienzoPanel;
import com.ait.lienzo.shared.core.types.Color;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.lienzo.shared.core.types.TextAlign;
import com.ait.lienzo.shared.core.types.TextBaseLine;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sam.webtasks.client.Names;
import com.sam.webtasks.iotask1.IOtask1BlockContext;
import com.sam.webtasks.iotask1.IOtask1DisplayParams;

public class IOtask2RunTrial {

	public static void Run() {
		// get block context
		IOtask2Block block = IOtask2BlockContext.getContext();

		Date blockStart = new Date();

		blockStart = new Date();

		// set up labels to put inside circles
		final String[] labels = new String[block.nCircles];

		for (int i = 0; i < block.nCircles; i++) {
			labels[i] = "" + (i + 1);
		}

		// set size parameters
		int xDim = Window.getClientWidth();
		int yDim = Window.getClientHeight();
		int minDim = 0; // smaller of the two dimensions

		if (xDim <= yDim) {
			minDim = xDim;
		} else {
			minDim = yDim;
		}

		final int boxSize = (int) (minDim * IOtask2DisplayParams.boxSize);
		int margin = (int) (boxSize * IOtask2DisplayParams.margin);
		final int circleRadius = (int) (boxSize * IOtask2DisplayParams.circleRadius);

		final LienzoPanel panel = new LienzoPanel(boxSize, boxSize);

		// Window.setMargin("0px");
		final VerticalPanel verticalPanel = new VerticalPanel();

		verticalPanel.setWidth(Window.getClientWidth() + "px");
		verticalPanel.setHeight(Window.getClientHeight() + "px");

		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

		final HorizontalPanel horizontalPanel = new HorizontalPanel();

		horizontalPanel.add(panel);

		verticalPanel.add(horizontalPanel);
		RootPanel.get().add(verticalPanel);

		// set up outline
		Layer bgLayer = new Layer();

		Line left = new Line(0, 0, 0, boxSize).setStrokeColor(IOtask2DisplayParams.circleColours[1]).setStrokeWidth(10);
		Line right = new Line(boxSize, 0, boxSize, boxSize).setStrokeColor(IOtask2DisplayParams.circleColours[2])
				.setStrokeWidth(10);
		Line bottom = new Line(0, boxSize, boxSize, boxSize).setStrokeColor(ColorName.BLACK).setStrokeWidth(10);
		Line top = new Line(0, 0, boxSize, 0).setStrokeColor(IOtask2DisplayParams.circleColours[3]).setStrokeWidth(10);

		// Rectangle outlineRectangle = new Rectangle(boxSize, boxSize);
		// outlineRectangle.setStrokeColor(ColorName.BLACK).setStrokeWidth(6);
		// bgLayer.add(outlineRectangle);
		bgLayer.add(left);
		bgLayer.add(right);
		bgLayer.add(bottom);
		bgLayer.add(top);
		// put background layer on screen
		panel.add(bgLayer);

		// set up circles
		final Layer circleLayer = new Layer();
		/*
		 * final Circle targetHitCircle = new Circle(circleRadius);
		 * targetHitCircle.setFillColor(ColorName.WHITE);
		 * targetHitCircle.setStrokeColor(ColorName.GRAY);
		 * targetHitCircle.setStrokeWidth(2);
		 * 
		 * final Text targetText = new Text("+" + targetValue, "Verdana, sans-serif",
		 * null, 40); targetText.setTextAlign(TextAlign.CENTER);
		 * targetText.setTextBaseLine(TextBaseLine.MIDDLE);
		 * targetText.setFillColor(ColorName.GRAY);
		 * 
		 * final Group targetHit = new Group(); targetHit.add(targetHitCircle);
		 * targetHit.add(targetText);
		 * 
		 * targetHit.setVisible(false); targetHit.setX((int) boxSize / 2);
		 * targetHit.setY((int) boxSize / 2); targetHit.setAlpha(0.3);
		 */

		int[] circleX = new int[block.nCircles];
		int[] circleY = new int[block.nCircles];
		final Circle[] circles = new Circle[block.nCircles];
		final Circle[] circleOverlays = new Circle[block.nCircles];
		final Text[] circleText = new Text[block.nCircles];
		final Group[] circleGroup = new Group[block.nCircles];
		int randomFlag = 0;
		IOtask2BlockContext.setCompletedCircles(0);
		IOtask2BlockContext.setNextCircle(0);
		IOtask2BlockContext.setCircleAdjust(0);
		IOtask2BlockContext.setnHits(0);

		/*
		 * completedCircles = 0; nextCircle = 0; nTargets = 0; nHits = 0; circleAdjust =
		 * 0;
		 */

		int c = 0;
		int randomCounter = 0;

		while (c < block.nCircles) {
			randomFlag = 1;

			while (randomFlag == 1) {
				randomCounter++;

				if (randomCounter > 10000) {
					randomCounter = 0;
					c = 0; // start again if failing to position the circles appropriately
				}

				randomFlag = 0;

				circleX[c] = Random.nextInt(boxSize - (2 * circleRadius) - (2 * margin)) + circleRadius + margin;
				circleY[c] = Random.nextInt(boxSize - (2 * circleRadius) - (2 * margin)) + circleRadius + margin;

				for (int cc = 0; cc < c; cc++) {
					int distanceX = circleX[c] - circleX[cc];
					int distanceY = circleY[c] - circleY[cc];

					double distanceAbs = Math.pow(Math.pow(distanceX, 2) + Math.pow(distanceY, 2), 0.5);

					if (distanceAbs < (3 * circleRadius)) {
						randomFlag = 1;
					}
				}
			}

			c++;
		}

		final int[] circleXref = circleX;
		final int[] circleYref = circleY;

		for (c = 0; c < block.nCircles; c++) {
			circles[c] = new Circle(circleRadius);
			circleOverlays[c] = new Circle(circleRadius);
			circleText[c] = new Text(labels[c], "Verdana, sans-serif", null, 40);
			circleGroup[c] = new Group();

			circles[c].setFillColor(IOtask2DisplayParams.circleColours[0]);
			circles[c].setStrokeColor(ColorName.BLACK);
			circles[c].setStrokeWidth(2);

			circleText[c].setTextAlign(TextAlign.CENTER);
			circleText[c].setTextBaseLine(TextBaseLine.MIDDLE);
			circleText[c].setFillColor(ColorName.BLACK);

			circleOverlays[c].setFillColor(ColorName.BLACK);
			circleOverlays[c].setAlpha(0.0000001);

			circleGroup[c].add(circles[c]);
			circleGroup[c].add(circleText[c]);
			circleGroup[c].add(circleOverlays[c]);
			circleGroup[c].setX(circleX[c]);
			circleGroup[c].setY(circleY[c]);

			switch (block.offloadCondition) {
			case Names.REMINDERS_NOTALLOWED:
				circleGroup[c].setDraggable(false);
				break;
			default:
				circleGroup[c].setDraggable(true);
				break;
			}

			circleGroup[c].setDragBounds(new DragBounds(1, 1, boxSize, boxSize));

			circleLayer.add(circleGroup[c]);

			final int finalc = c; // need to set up a final version of the c variable so that it works in the code
									// below

			circleGroup[c].addNodeDragStartHandler(new NodeDragStartHandler() {
				@Override
				public void onNodeDragStart(NodeDragStartEvent event) {
					// reset the double click flag. this is used to quit the task
					// using a double click to the first circle followed by
					// a double click to the last circle
					IOtask2BlockContext.setDoubleClickFlag(false);

					// reset the exit flag, which is only tripped if participant drags circle out of
					// box
					IOtask2BlockContext.setExitFlag(0);

					// set the timestamp for the start of the drag
					// IOtask2BlockContext.setDragStart();

					DragContext dC = event.getDragContext();

					// figure out which circle was clicked for (int c = 0; c < nCircles; c++) {
					double xDist = dC.getDragStartX() - circleGroup[finalc].getX();
					double yDist = dC.getDragStartY() - circleGroup[finalc].getY();

					if (Math.pow(xDist, 2) <= Math.pow(circleRadius, 2)) {
						if (Math.pow(yDist, 2) <= Math.pow(circleRadius, 2)) {
							IOtask2BlockContext.setClickedCircle(finalc);
						}
					}
				}
			});

			// circleLayer.add(targetHit);

			circleGroup[c].addNodeDragMoveHandler(new NodeDragMoveHandler() {
				@Override
				public void onNodeDragMove(NodeDragMoveEvent event) {
					if (IOtask2BlockContext.getClickedCircle() == IOtask2BlockContext.getReminderFlag()) {
						IOtask2BlockContext.setReminderFlag(-1);

						if (IOtask2BlockContext.getReminderFlag() == IOtask2BlockContext.getBackupReminderFlag()) {
							IOtask2BlockContext.setBackupReminderFlag(-1);
						}
					}

					if (IOtask2BlockContext.getClickedCircle() == IOtask2BlockContext.getBackupReminderFlag()) {
						IOtask2BlockContext.setBackupReminderFlag(-1);
					}

					if (event.getX() <= 0) { // left
						IOtask2BlockContext.setExitFlag(1);
					}

					if (event.getX() >= boxSize) { // right
						IOtask2BlockContext.setExitFlag(2);
					}

					if (event.getY() <= 0) { // up
						IOtask2BlockContext.setExitFlag(3);
					}

					if (event.getY() >= boxSize) { // down
						IOtask2BlockContext.setExitFlag(4);
					}

					if (IOtask2BlockContext.getExitFlag() > 0) {
						DragContext dC = event.getDragContext();

						if ((IOtask2BlockContext.getClickedCircle() == IOtask2BlockContext.getNextCircle())
								&& ((IOtask2BlockContext.getReminderFlag() == -1)
										|| ((IOtask2BlockContext.getCompletedCircles()
												- IOtask2BlockContext.getReminderCompletedCircles()) < 1))) {
							if (IOtask2BlockContext.getCheckExitFlag() == 1) {
								IOtask2BlockContext.setReminderFlag(IOtask2BlockContext.getBackupReminderFlag());

								IOtask2BlockContext
										.setReminderCompletedCircles(IOtask2BlockContext.getBackupCompletedCircles());
								IOtask2BlockContext.setBackupReminderFlag(-1);
								IOtask2BlockContext.setCheckExitFlag(0);

								int circleNum = IOtask2BlockContext.getClickedCircle()
										+ IOtask2BlockContext.getCircleAdjust();

								if (IOtask2BlockContext.getExitFlag() == circleNum) {
									IOtask2BlockContext.incrementHits();
									circles[IOtask2BlockContext.getClickedCircle()].setFillColor(ColorName.GREENYELLOW);
								} else if (IOtask2BlockContext.getExitFlag() < 4) { // incorrect target response
									circles[IOtask2BlockContext.getClickedCircle()].setFillColor(ColorName.RED);
								} else { // ongoing response
									// circles[clickedCircle].setFillColor(ColorName.PURPLE);
								}

								circleText[IOtask2BlockContext.getClickedCircle()].setVisible(false);
								// circleGroup[clickedCircle].setDragBounds(new DragBounds(dC.getEventX()-10,
								// dC.getEventY()-10, dC.getEventX()+10, dC.getEventY()+10));
								circleLayer.draw();

							}
						} else {
							if (IOtask2BlockContext.getFlashFlag() == false) {
								if (IOtask2BlockContext.getClickedCircle() != IOtask2BlockContext.getNextCircle()) {
									circles[IOtask2BlockContext.getNextCircle()].setFillColor(ColorName.RED);
								} else {
									// Window.alert("reminderFlag = " + reminderFlag + "&backupReminderFlag = " +
									// backupReminderFlag);
									circles[IOtask2BlockContext.getReminderFlag()].setFillColor(ColorName.WHITE);
								}

								circleLayer.draw();

								new Timer() {
									public void run() {
										circles[IOtask2BlockContext.getNextCircle()]
												.setFillColor(IOtask2DisplayParams.circleColours[0]);
										circles[IOtask2BlockContext.getReminderFlag()]
												.setFillColor(IOtask2DisplayParams.circleColours[0]);
										circleLayer.draw();
									}
								}.schedule(400);

								IOtask2BlockContext.setFlashFlag(false);
							}

							IOtask2BlockContext.setExitFlag(0);
						}
					}
				}
			});

			circleGroup[c].addNodeDragEndHandler((NodeDragEndHandler) new NodeDragEndHandler() {
				@Override
				public void onNodeDragEnd(NodeDragEndEvent event) {
					AnimationProperties grow = new AnimationProperties();
					grow.push(Properties.SCALE(5));

					/*
					 * 
					 * if (exitFlag == trialParams.targets[clickedCircle + circleAdjust]) { if
					 * ((exitFlag > 0) && (showInstructions)) { totalPoints += currentValue; }
					 * 
					 * if ((exitFlag > 0) && showPoints) { targetText.setText("+" + currentValue);
					 * targetHit.setVisible(true); IAnimationHandle handleFeedback =
					 * targetHit.animate(AnimationTweener.LINEAR, grow, 200); } }
					 * 
					 * int extraAdjust = 0;
					 * 
					 * if (clickedCircle < nextCircle) { extraAdjust = trialParams.nCircles; }
					 * 
					 * if ((exitFlag == 0) && (trialParams.targets[clickedCircle + circleAdjust +
					 * extraAdjust] > 0)) { if (showPoints) { targetText.setText("-5");
					 * targetHit.setVisible(true); IAnimationHandle handleFeedback =
					 * targetHit.animate(AnimationTweener.LINEAR, grow, 200); } }
					 * 
					 * final AnimationProperties shrink = new AnimationProperties();
					 * shrink.push(Properties.SCALE(0));
					 * 
					 * new Timer() { public void run() { IAnimationHandle handleFeedback2 =
					 * targetHit.animate(AnimationTweener.LINEAR, shrink, 200); } }.schedule(350);
					 */
					
					final AnimationProperties shrink = new AnimationProperties();
					shrink.push(Properties.SCALE(0));

					// dragEnd = new Date();

					// outputData(trialParams.nCircles, circleGroup, trialParams.labels,
					// trialParams.blockNum);

					IOtask2BlockContext.setFlashFlag(false);

					if (IOtask2BlockContext.getReminderLockout()) {
						if ((IOtask2BlockContext.getExitFlag() == 0) & (IOtask2BlockContext.getClickedCircle() != IOtask2BlockContext.getNextCircle())) {
							for (int c = 0; c < IOtask2BlockContext.getnCircles(); c++) {
								circleGroup[c].setDraggable(false);
								circles[c].setAlpha(0.3);
							}

							circleLayer.draw();

							new Timer() {
								public void run() {
									for (int c = 0; c < IOtask2BlockContext.getnCircles(); c++) {
										circleGroup[c].setDraggable(true);
										circles[c].setAlpha(1);
									}

									circleLayer.draw();
								}
							}.schedule(IOtask2BlockContext.getReminderLockoutTime());
						}
					}

					if ((IOtask2BlockContext.getExitFlag() > 0) & (IOtask2BlockContext.getClickedCircle() == IOtask2BlockContext.getNextCircle())) {
						IOtask2BlockContext.incrementCompletedCircles();
/*
						if (trialParams.targets[completedCircles + 5] > 0) {
							nTargets++;
						}
*/
						if ((IOtask2BlockContext.getCompletedCircles() % IOtask2BlockContext.getnCircles()) == 0) {
							IOtask2BlockContext.doCircleAdjustment();
						}

						IAnimationHandle handle = circles[IOtask2BlockContext.getClickedCircle()].animate(AnimationTweener.LINEAR, shrink, 200);

						if ((IOtask2BlockContext.getTotalCircles() - IOtask2BlockContext.getCompletedCircles()) >= IOtask2BlockContext.getnCircles()) { // more circles to
																										// add on screen
							//final int clickedCircleFinal = clickedCircle;
							//final int completedCirclesFinal = completedCircles;
/*
							if (trialParams.targets[completedCirclesFinal + 5] > 0) {
								if ((noReminder == 0) && (blockParams.moveAny)) {
									// if ((currentValue==targetValue)&&(blockParams.moveAny)) {
									// Window.alert("backup");
									backupReminderFlag = clickedCircleFinal;
									backupCompletedCircles = completedCirclesFinal;

									if ((completedCirclesFinal - reminderCompletedCircles) > 1) {
										reminderFlag = clickedCircleFinal;
										reminderCompletedCircles = completedCirclesFinal;
									}
								}
							}
*/
							final int clickedCircleFinal = IOtask2BlockContext.getClickedCircle();
							final int newCircle = IOtask2BlockContext.getCompletedCircles() + IOtask2BlockContext.getnCircles() - 1;	
							
							new Timer() {
								public void run() {
									int targetStatus = IOtask2BlockContext.getTargetSide(newCircle);
									
									circles[IOtask2BlockContext.getClickedCircle()].setFillColor(IOtask2DisplayParams.circleColours[targetStatus]);
									circleGroup[clickedCircleFinal].setX(circleXref[clickedCircleFinal]);
									circleGroup[clickedCircleFinal].setY(circleYref[clickedCircleFinal]);

									if (IOtask2BlockContext.getOffloadCondition() == Names.REMINDERS_NOTALLOWED) {
										circleGroup[clickedCircleFinal].setDraggable(false);
									}

									AnimationProperties grow = new AnimationProperties();
									grow.push(Properties.SCALE(1));

									IAnimationHandle handle = circles[clickedCircleFinal].animate(AnimationTweener.LINEAR, grow, 200);
								}
							}.schedule(300);

							new Timer() {
								public void run() {
									circleText[clickedCircleFinal].setText(labels[newCircle]);

									circleText[clickedCircleFinal].setVisible(true);

									circleLayer.draw();
								}
							}.schedule(400);

							final double startR = (double) IOtask2DisplayParams.circleColours[IOtask2BlockContext.getTargetSide(newCircle)].getR();
							final double startG = (double) IOtask2DisplayParams.circleColours[IOtask2BlockContext.getTargetSide(newCircle)].getG();
							final double startB = (double) IOtask2DisplayParams.circleColours[IOtask2BlockContext.getTargetSide(newCircle)].getB();

							final double endR = (double) IOtask2DisplayParams.circleColours[0].getR();
							final double endG = (double) IOtask2DisplayParams.circleColours[0].getG();
							final double endB = (double) IOtask2DisplayParams.circleColours[0].getB();

							new Timer() {
								public void run() {
									IAnimationCallback callback = new IAnimationCallback() {
										public void onClose(IAnimation callback, IAnimationHandle handle) {

										}

										public void onFrame(IAnimation callback, IAnimationHandle handle) {
											double percent = callback.getPercent();
											double newR = startR + (percent * (endR - startR));
											double newG = startG + (percent * (endG - startG));
											double newB = startB + (percent * (endB - startB));

											int R = (int) newR;
											int G = (int) newG;
											int B = (int) newB;

											Color newColor = new Color(R, G, B);

											circles[clickedCircleFinal].setFillColor(newColor);
											circleLayer.draw();
										}

										public void onStart(IAnimation callback, IAnimationHandle handle) {

										}
									};

									IAnimationHandle handle = circles[clickedCircleFinal].animate(null, null, 400,
											callback);
								}
							}.schedule(2000);

							/*
							 * new Timer() { public void run() { IAnimationCallback callback = new
							 * IAnimationCallback() { public void onStart(IAnimation animation,
							 * IAnimationHandle handle) { // TODO Auto-generated method stub
							 * 
							 * }
							 * 
							 * 
							 * 
							 * public void onClose(IAnimation animation, IAnimationHandle handle) {
							 * circles[clickedCircleFinal].setFillColor(circleColours[0]);
							 * circleLayer.draw(); }
							 * 
							 * public void onFrame(IAnimation animation, IAnimationHandle handle, long
							 * duration, double percent) { double newR = startR + (percent * (endR -
							 * startR)); double newG = startG + (percent * (endG - startG)); double newB =
							 * startB + (percent * (endB - startB));
							 * 
							 * int R = (int) newR; int G = (int) newG; int B = (int) newB;
							 * 
							 * Color newColor = new Color(R, G, B);
							 * 
							 * circles[clickedCircleFinal].setFillColor(newColor); circleLayer.draw();
							 * 
							 * 
							 * 
							 * 
							 * @Override public void onFrame(IAnimation animation, IAnimationHandle handle)
							 * { // TODO Auto-generated method stub
							 * 
							 * } };
							 * 
							 * IAnimationHandle handle = circles[clickedCircleFinal].animate(null, shrink,
							 * 400, callback); } }.schedule(2000);
							 */
						}

						//checkExit = 1; // ready for next exit event
						IOtask2BlockContext.setExitFlag(0);
						IOtask2BlockContext.incrementNextCircle();

						if (IOtask2BlockContext.getCompletedCircles() == IOtask2BlockContext.getTotalCircles()) {/*
							new Timer() {
								public void run() {
									final Date endTime = new Date();
									
									

									String phpString = "postTrial.php?participantID=";

									if (position[0] > 22) {
										phpString = phpString + workerId + "&trialNum=" + trialNum + "&rewardValue="
												+ points;
										phpString = phpString + "&nHits=" + nHits + "&noReminder=" + noReminder;
										phpString = phpString + "&duration="
												+ ((int) (endTime.getTime() - blockStart.getTime()));
									} else {
										phpString = phpString + workerId + "&trialNum=" + (trialNum - 100)
												+ "&rewardValue=" + points;
										phpString = phpString + "&nHits=" + nHits + "&noReminder=" + noReminder;
										phpString = phpString + "&duration="
												+ ((int) (endTime.getTime() - blockStart.getTime()));
									}

									phpCall(phpString);

									timeCounter = 1;

									final String phpStringFinal = phpString;

									new Timer() {
										public void run() {
											timeCounter++;

											if ((phpOutput.equals("OK")) | checkSaved == false) {
												cancel();

												trialTimer.cancel();

												RootPanel.get().remove(verticalPanel);

												trialNum++;

												new Timer() {
													public void run() {
														for (int c = 0; c < trialParams.nCircles; c++) {
															circles[c] = null;
															circleGroup[c] = null;
															circleText[c] = null;
														}

														if (showFeedback) {
															giveFeedback();
														} else {
															trialHandler();
														}
													}
												}.schedule(1000);
											}

											if (timeCounter > 70) {
												Window.alert(
														"Data was not saved. Please check your internet connection before continuing");
												timeCounter = 1;
												phpCall(phpStringFinal);
											}
										}
									}.scheduleRepeating(100);
								}
							}.schedule(1000);*/
						} else {
							if (IOtask2BlockContext.getNextCircle() < IOtask2BlockContext.getnCircles()) {
								circleGroup[IOtask2BlockContext.getNextCircle()].setDraggable(true);
							}
						}
					}
				}
			});
		}

		circleGroup[0].addNodeMouseDoubleClickHandler((NodeMouseDoubleClickHandler) new NodeMouseDoubleClickHandler() {
			@Override
			public void onNodeMouseDoubleClick(NodeMouseDoubleClickEvent event) {
				//quitFlag = true;
			}
		});
/*
		circleGroup[trialParams.nCircles - 1].addNodeMouseDoubleClickHandler(new NodeMouseDoubleClickHandler() {
			public void onNodeMouseDoubleClick(NodeMouseDoubleClickEvent event) {
				if (quitFlag == true) {
					RootPanel.get().remove(verticalPanel);
					trialNum++;
					trialHandler();
					return;
				}
			}
		});*/

		circleGroup[0].setDraggable(true);

		panel.add(circleLayer);
		circleLayer.draw();

		// trialTimeCounter = 180;
		// trialTimer.scheduleRepeating(1000);
	}

}
