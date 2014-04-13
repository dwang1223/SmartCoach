package edu.wpi.smartcoachdb.model;

import java.util.Date;

public class PatientInfo {
	private int id;
	private float patientHeight;
	private float patientWeight;
	private int patientAge;
	private Date lastUpateTimeDate;
	private float patientGoalWeight;

	public PatientInfo(float patientHeight, float patientWeight,
			int patientAge, Date lastUpateTimeDate, float patientGoalWeight) {
		super();
		this.patientHeight = patientHeight;
		this.patientWeight = patientWeight;
		this.patientAge = patientAge;
		this.lastUpateTimeDate = lastUpateTimeDate;
		this.patientGoalWeight = patientGoalWeight;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getPatientHeight() {
		return patientHeight;
	}

	public void setPatientHeight(float patientHeight) {
		this.patientHeight = patientHeight;
	}

	public float getPatientWeight() {
		return patientWeight;
	}

	public void setPatientWeight(float patientWeight) {
		this.patientWeight = patientWeight;
	}

	public int getPatientAge() {
		return patientAge;
	}

	public void setPatientAge(int patientAge) {
		this.patientAge = patientAge;
	}

	public Date getLastUpateTimeDate() {
		return lastUpateTimeDate;
	}

	public void setLastUpateTimeDate(Date lastUpateTimeDate) {
		this.lastUpateTimeDate = lastUpateTimeDate;
	}

	public float getPatientGoalWeight() {
		return patientGoalWeight;
	}

	public void setPatientGoalWeight(float patientGoalWeight) {
		this.patientGoalWeight = patientGoalWeight;
	}

}
