package edu.wpi.smartcoach.model;

import java.util.Date;

public class PatientMetrics {
	private int id;
	private float patientHeight;
	private float patientWeight;
	private Date lastUpateTimeDate;
	private float patientGoalWeight;

	public PatientMetrics(float patientHeight, float patientWeight, Date lastUpateTimeDate, float patientGoalWeight) {
		super();
		this.patientHeight = patientHeight;
		this.patientWeight = patientWeight;
		this.lastUpateTimeDate = lastUpateTimeDate;
		this.patientGoalWeight = patientGoalWeight;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getHeight() {
		return patientHeight;
	}

	public void setHeight(float patientHeight) {
		this.patientHeight = patientHeight;
	}

	public float getWeight() {
		return patientWeight;
	}

	public void setWeight(float patientWeight) {
		this.patientWeight = patientWeight;
	}

	public Date getLastUpateTimeDate() {
		return lastUpateTimeDate;
	}

	public void setLastUpateTimeDate(Date lastUpateTimeDate) {
		this.lastUpateTimeDate = lastUpateTimeDate;
	}

	public float getGoalWeight() {
		return patientGoalWeight;
	}

	public void setGoalWeight(float patientGoalWeight) {
		this.patientGoalWeight = patientGoalWeight;
	}

}
