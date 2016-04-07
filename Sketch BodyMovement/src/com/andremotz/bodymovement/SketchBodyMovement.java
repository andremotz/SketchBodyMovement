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

	int numberOfBodies = 2; // How many bodies are in the system
	int numberOfSteps = 1000; // How many calculation steps should be
								// calculated
	int pictureCount = 20; // Defines every x. picture to save
	float speedFactor = 1; // To controle the speed
	float bodyPosition[][]; // ''' 0 .. X, 1 .. Y '''
	float bodySpeed[][]; // ''' 0 .. Vx, 1 .. Vy '''
	float bodyAttribute[][]; // ''' 0 .. Mass, 1 .. other '''

	// body_color = ["yo","b.","g."] //''' coloring '''
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
	
	Body currentBody;
	Body bodyInstance2;

	void initializeBodies() {
		bodyPosition = new float[3][3];
		bodySpeed = new float[3][3];
		bodyAttribute = new float[3][3];
		
		bodyList = new ArrayList<Body>();
		
		currentBody = new Body();
		currentBody.setBodyPosition(new float[]{500,400});
		currentBody.setBodySpeed(new float[]{0,0});
		currentBody.setBodyAttribute(new float[]{100,100});
		bodyList.add(currentBody);
		
		currentBody = new Body();
		currentBody.setBodyPosition(new float[]{450,400});
		currentBody.setBodySpeed(new float[]{0,1});
		currentBody.setBodyAttribute(new float[]{1,1});
		bodyList.add(currentBody);

		// Body 1
		bodyPosition[0][0] = 500;
		bodyPosition[1][0] = 400;
		bodySpeed[0][0] = 0;
		bodySpeed[1][0] = 0;
		bodyAttribute[0][0] = 100;
		bodyAttribute[1][0] = 100;

		// Body 2
		bodyPosition[0][1] = 450;
		bodyPosition[1][1] = 400;
		bodySpeed[0][1] = 0;
		bodySpeed[1][1] = 1;
		bodyAttribute[0][1] = 1;
		bodyAttribute[1][1] = 1;

		// Body 3
//		body_position[0][2] = 550;
//		body_position[1][2] = 450;
//		body_speed[0][2] = (float) 0.5;
//		body_speed[1][2] = 0;
//		body_attribute[0][2] = 1;
//		body_attribute[1][2] = 1;
		// More bodies can be defined
	}

	void drawPicture() { // to draw a picture
//		for (int j = 0; j < numberOfBodies; j++) {
		for(Body currentBody : bodyList) {
			//point(bodyPosition[0][j], bodyPosition[1][j]);
			point(currentBody.getBodyPosition()[0], currentBody.getBodyPosition()[1]);
		}
	}

	/*
	 * Each iteration, the new position of every body is calculated
	 */
	void iteration() { 
						
//		for (int i = 0; i < numberOfBodies; i++) {
		for(Body currentBody1 : bodyList) {
			float[] speedChange = new float[2];
//			for (int j = 0; j < numberOfBodies; j++) {
			for(Body currentBody2 : bodyList) {
				if (currentBody1 != currentBody2) {
					speedChange = potential(currentBody1, currentBody2);
				}
			}

			// TODO Array-Durchlauf auf Mehrdimonsionalitaet vorbereiten
			
			// jeder body speed x = [0][i]
//			bodySpeed[0][i] = bodySpeed[0][i] + speedChange[0]
//					/ speedFactor;
			float currentBodySpeedX = currentBody1.getBodySpeed()[0];
			currentBodySpeedX = currentBodySpeedX + speedChange[0] / speedFactor;
			
			// jeder body speed y = [1][i]
//			bodySpeed[1][i] = bodySpeed[1][i] + speedChange[1]
//					/ speedFactor;
			float currentBodySpeedY = currentBody1.getBodySpeed()[1];
			currentBodySpeedY = currentBodySpeedY + speedChange[0] / speedFactor;
			
			currentBody1.setBodySpeed(new float[]{currentBodySpeedX, currentBodySpeedY});
			
			
			// jeder body position x
//			bodyPosition[0][i] = bodyPosition[0][i] + bodySpeed[0][i]
//					/ speedFactor;
			float currentBodyPositionX = currentBody1.getBodyPosition()[0];
			currentBodyPositionX = currentBodyPositionX + currentBodySpeedX / speedFactor;
			
			// jeder body position y
//			bodyPosition[1][i] = bodyPosition[1][i] + bodySpeed[1][i]
//					/ speedFactor;
			float currentBodyPositionY = currentBody1.getBodyPosition()[1];
			currentBodyPositionY = currentBodyPositionY + currentBodySpeedY / speedFactor;
			
		}
	}
	
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
		
		speedChange[0] = speedChange[0] + speedChangeNew[0];
		speedChange[1] = speedChange[1] + speedChangeNew[1];

		return speedChange;
	}

	/*
	 * Here can be an arbitrary potential between the bodies be definded
	 */
	float[] potential(int i, int j) { 

		float[] speedChangeNew = new float[2];
		float[] speedChange = new float[2];

		float distanceX = abs(bodyPosition[0][i] - bodyPosition[0][j]);
		float distanceY = abs(bodyPosition[1][i] - bodyPosition[1][j]);
		float distance = abs(pow(pow(distanceX, 2) + pow(distanceY, 2),
				(float) 0.5));
		speedChangeNew[0] = (float) (distanceX / (distance + 1)
				* bodyAttribute[0][j] * 0.003);
		speedChangeNew[1] = (float) (distanceY / (distance + 1)
				* bodyAttribute[0][j] * 0.003);

		if ((bodyPosition[0][i] - bodyPosition[0][j]) > 0) {
			speedChangeNew[0] = -speedChangeNew[0];
		}
		if ((bodyPosition[1][i] - bodyPosition[1][j]) > 0) {
			speedChangeNew[1] = -speedChangeNew[1];
		}

		speedChange[0] = speedChange[0] + speedChangeNew[0];
		speedChange[1] = speedChange[1] + speedChangeNew[1];

		return speedChange;
	}

}
