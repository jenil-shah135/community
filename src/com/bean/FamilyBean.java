package com.bean;

import java.util.List;

public class FamilyBean {

	private Integer familyID;
	private AddressBean currentAddress;
	private String nativeAddress;		
	private List<MemberBean> familyMembers;
	public Integer getFamilyID() {
		return familyID;
	}
	public void setFamilyID(Integer familyID) {
		this.familyID = familyID;
	}
	public AddressBean getCurrentAddress() {
		return currentAddress;
	}
	public void setCurrentAddress(AddressBean currentAddress) {
		this.currentAddress = currentAddress;
	}
	public String getNativeAddress() {
		return nativeAddress;
	}
	public void setNativeAddress(String nativeAddress) {
		this.nativeAddress = nativeAddress;
	}
	public List<MemberBean> getFamilyMembers() {
		return familyMembers;
	}
	public void setFamilyMembers(List<MemberBean> familyMembers) {
		this.familyMembers = familyMembers;
	}
}