package com.andremotz.bodymovement;

public class Body {

	private float[] bodyPosition;
	private float[] bodySpeed;
	private float[] bodyAttribute;
	
	public Body() {
		bodyPosition = new float[2];
		bodySpeed = new float[2];
		bodyAttribute = new float[2];
	}
	
	
	/**
	 * @return the body_position
	 */
	public float[] getBodyPosition() {
		return bodyPosition;
	}
	/**
	 * @param bodyPosition the body_position to set
	 */
	public void setBodyPosition(float[] bodyPosition) {
		this.bodyPosition = bodyPosition;
	}
	/**
	 * @return the body_speed
	 */
	public float[] getBodySpeed() {
		return bodySpeed;
	}
	/**
	 * @param bodySpeed the body_speed to set
	 */
	public void setBodySpeed(float[] bodySpeed) {
		this.bodySpeed = bodySpeed;
	}
	/**
	 * @return the body_attribute
	 */
	public float[] getBodyAttribute() {
		return bodyAttribute;
	}
	/**
	 * @param bodyAttribute the body_attribute to set
	 */
	public void setBodyAttribute(float[] bodyAttribute) {
		this.bodyAttribute = bodyAttribute;
	}
	

}
