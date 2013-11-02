package com.bean;

import java.util.Date;

public class MemberBean {

	private Integer memberID;
	private String firstName;
	private String middleName;
	private String lastName;
	private boolean maritalStatus;
	private String contactNo;
	private String emailID;
	private String bloodGroup;
	private String educationQualification;	
	private OccupationBean occupation;
	private String workingAddress;	
	private Date birthDate;
	private String birthPlace;
	private boolean gender;
	private int familyID;
	private RelationBean relation;	
	public Integer getMemberID() {
		return memberID;
	}
	public void setMemberID(Integer memberID) {
		this.memberID = memberID;
	}	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public boolean getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(boolean maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getEmailID() {
		return emailID;
	}
	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}
	public String getBloodGroup() {
		return bloodGroup;
	}
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	public String getEducationQualification() {
		return educationQualification;
	}
	public void setEducationQualification(String educationQualification) {
		this.educationQualification = educationQualification;
	}
	public OccupationBean getOccupation() {
		return occupation;
	}
	public void setOccupation(OccupationBean occupation) {
		this.occupation = occupation;
	}
	public String getWorkingAddress() {
		return workingAddress;
	}
	public void setWorkingAddress(String workingAddress) {
		this.workingAddress = workingAddress;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getBirthPlace() {
		return birthPlace;
	}
	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}
	public boolean getGender() {
		return gender;
	}
	public void setGender(boolean gender) {
		this.gender = gender;
	}
	public int getFamilyID() {
		return familyID;
	}
	public void setFamilyID(int familyID) {
		this.familyID = familyID;
	}
	public RelationBean getRelation() {
		return relation;
	}
	public void setRelation(RelationBean relation) {
		this.relation = relation;
	}	
}