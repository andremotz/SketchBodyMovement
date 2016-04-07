package com.andremotz.bodymovement;

import java.util.ArrayList;

import processing.core.*;

public class SketchBodyMovement extends PApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final int WIDTH = 1440;
	public final int HEIGHT = 900;

	// To control the speed
	float speedFactor = 1; 

	ArrayList<Body> bodyList;

	public static void main(String[] args) {
		PApplet.main(new String[] { "--present",
				"com.andremotz.bodymovement.SketchBodyMovement" });
	}

	public void setup() {
		size(WIDTH, HEIGHT);
		initializeBodies();
	}

	public void draw() {
		drawPicture();
		iteration();
	}
	
	void initializeBodies() {
		bodyList = new ArrayList<Body>();
		Body currentBody = new Body();
		
		// Body 1
		currentBody.setBodyPosition(new float[]{500,500});
		currentBody.setBodySpeed(new float[]{0,0});
		currentBody.setBodyAttribute(new float[]{100,100});
		bodyList.add(currentBody);
		
		// Body 2
		currentBody = new Body();
		currentBody.setBodyPosition(new float[]{450,400});
		currentBody.setBodySpeed(new float[]{5,1});
		currentBody.setBodyAttribute(new float[]{1,1});
		bodyList.add(currentBody);
		
		// Body 3
		currentBody = new Body();
		currentBody.setBodyPosition(new float[]{750,450});
		currentBody.setBodySpeed(new float[]{(float)-0.5,0});
		currentBody.setBodyAttribute(new float[]{100,100});
		bodyList.add(currentBody);

	}

	void drawPicture() {
		for(Body currentBody : bodyList) {
			point(currentBody.getBodyPosition()[0], currentBody.getBodyPosition()[1]);
		}
	}

	/*
	 * Each iteration, the new position of every body is calculated
	 */
	void iteration() { 
						
		for(Body currentBody1 : bodyList) {
			float[] speedChange = new float[2];
			for(Body currentBody2 : bodyList) {
				if (currentBody1 != currentBody2) {
					speedChange = potential(currentBody1, currentBody2);
				}
			}

			// TODO Array-Durchlauf auf Mehrdimonsionalitaet vorbereiten
			
			// bodySpeed1x = bodySpeed1x + speedchangeX / 
			float currentBodySpeedX = currentBody1.getBodySpeed()[0];
			currentBodySpeedX = currentBodySpeedX + speedChange[0] / speedFactor;
			
			// jeder body speed y = [1][i]
			float currentBodySpeedY = currentBody1.getBodySpeed()[1];
			currentBodySpeedY = currentBodySpeedY + speedChange[1] / speedFactor;
			
			currentBody1.setBodySpeed(new float[]{currentBodySpeedX, currentBodySpeedY});
			
			// jeder body position x
			float currentBodyPositionX = currentBody1.getBodyPosition()[0];
			currentBodyPositionX = currentBodyPositionX + currentBodySpeedX / speedFactor;
			
			// jeder body position y
			float currentBodyPositionY = currentBody1.getBodyPosition()[1];
			currentBodyPositionY = currentBodyPositionY + currentBodySpeedY / speedFactor;
			
			currentBody1.setBodyPosition(new float[]{currentBodyPositionX, currentBodyPositionY});
			
		}
	}
	
	/*
	 * Here can be an arbitrary potential between the bodies be definded
	 */
	float[] potential(Body currentBody1, Body currentBody2) {
		float[] speedChangeNew = new float[2];
		float[] speedChange = new float[2];
		
		float currentBody1PositionX = currentBody1.getBodyPosition()[0];
		float currentBody1PositionY = currentBody1.getBodyPosition()[1];
		
		float currentBody2PositionX = currentBody2.getBodyPosition()[0];
		float currentBody2PositionY = currentBody2.getBodyPosition()[1];
		
		float distanceX = abs(currentBody1PositionX - currentBody2PositionX);
		float distanceY = abs(currentBody1PositionY - currentBody2PositionY);
		float distance = abs(pow(pow(distanceX, 2) + pow(distanceY, 2),
				(float) 0.5));
		
		float bodyAttributeX = currentBody2.getBodyAttribute()[0];
		speedChangeNew[0] = (float) ((distanceX /(distance +1)) * bodyAttributeX * 0.003);
		
		float bodyAttributeY = currentBody2.getBodyAttribute()[0];
		speedChangeNew[1] = (float) ((distanceY /(distance +1)) * bodyAttributeY * 0.003);
		
		if (currentBody1PositionX - currentBody2PositionX > 0) {
			speedChangeNew[0] = -speedChangeNew[0];
		}
		if (currentBody1PositionY - currentBody2PositionY > 0) {
			speedChangeNew[1] = -speedChangeNew[1];
		}

		speedChange[0] = speedChange[0] + speedChangeNew[0];
		speedChange[1] = speedChange[1] + speedChangeNew[1];
		
		return speedChange;
	}

}
