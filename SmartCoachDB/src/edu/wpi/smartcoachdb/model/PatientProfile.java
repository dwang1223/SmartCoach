package edu.wpi.smartcoachdb.model;

import java.util.Date;

/**
 * PatientProfile class
 * 
 * @author diwang
 * 
 */
public class PatientProfile {
	private int id;
	private String patientFirstName;
	private String patientLastName;
	private String patientGender;
	private Date patientBirthday;
	private String patientAddress;
	private String patientOccupation;

	public PatientProfile(String patientFirstName, String patientLastName,
			String patientGender, Date patientBirthday, String patientAddress,
			String patientOccupation) {
		super();
		this.patientFirstName = patientFirstName;
		this.patientLastName = patientLastName;
		this.patientGender = patientGender;
		this.patientBirthday = patientBirthday;
		this.patientAddress = patientAddress;
		this.patientOccupation = patientOccupation;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPatientFirstName() {
		return patientFirstName;
	}

	public void setPatientFirstName(String patientFirstName) {
		this.patientFirstName = patientFirstName;
	}

	public String getPatientLastName() {
		return patientLastName;
	}

	public void setPatientLastName(String patientLastName) {
		this.patientLastName = patientLastName;
	}

	public String getPatientGender() {
		return patientGender;
	}

	public void setPatientGender(String patientGender) {
		this.patientGender = patientGender;
	}

	public Date getPatientBirthday() {
		return patientBirthday;
	}

	public void setPatientBirthday(Date patientBirthday) {
		this.patientBirthday = patientBirthday;
	}

	public String getPatientAddress() {
		return patientAddress;
	}

	public void setPatientAddress(String patientAddress) {
		this.patientAddress = patientAddress;
	}

	public String getPatientOccupation() {
		return patientOccupation;
	}

	public void setPatientOccupation(String patientOccupation) {
		this.patientOccupation = patientOccupation;
	}

}
