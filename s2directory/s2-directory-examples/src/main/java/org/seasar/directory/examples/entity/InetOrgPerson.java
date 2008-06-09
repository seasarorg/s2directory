/*
 * Copyright 2005-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.directory.examples.entity;

import java.io.Serializable;

public class InetOrgPerson implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String audio;
	private String businessCategory;
	private String carLicense;
	private String departmentNumber;
	private String displayName;
	private String employeeNumber;
	private String employeeType;
	private String givenName;
	private String homePhone;
	private String homePostalAddress;
	private String initials;
	private String jpegPhoto;
	private String labeledURI;
	private byte[] mail;
	private String manager;
	private String mobile;
	private String o;
	private String pager;
	private byte[] photo;
	private String roomNumber;
	private String secretary;
	private String uid;
	private String userCertificate;
	private String x500UniqueIdentifier;
	private String preferredLanguage;
	private String userSMIMECertificate;
	private String userPKCS12;

	public InetOrgPerson() {
		super();
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setAudio(String audio) {
		this.audio = audio;
	}

	public String getAudio() {
		return audio;
	}

	public void setBusinessCategory(String businessCategory) {
		this.businessCategory = businessCategory;
	}

	public String getBusinessCategory() {
		return businessCategory;
	}

	public void setCarLicense(String carLicense) {
		this.carLicense = carLicense;
	}

	public String getCarLicense() {
		return carLicense;
	}

	public void setDepartmentNumber(String departmentNumber) {
		this.departmentNumber = departmentNumber;
	}

	public String getDepartmentNumber() {
		return departmentNumber;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

	public String getEmployeeType() {
		return employeeType;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePostalAddress(String homePostalAddress) {
		this.homePostalAddress = homePostalAddress;
	}

	public String getHomePostalAddress() {
		return homePostalAddress;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

	public String getInitials() {
		return initials;
	}

	public void setJpegPhoto(String jpegPhoto) {
		this.jpegPhoto = jpegPhoto;
	}

	public String getJpegPhoto() {
		return jpegPhoto;
	}

	public void setLabeledURI(String labeledURI) {
		this.labeledURI = labeledURI;
	}

	public String getLabeledURI() {
		return labeledURI;
	}

	public void setMail(byte[] mail) {
		this.mail = mail;
	}

	public byte[] getMail() {
		return mail;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getManager() {
		return manager;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMobile() {
		return mobile;
	}

	public void setO(String o) {
		this.o = o;
	}

	public String getO() {
		return o;
	}

	public void setPager(String pager) {
		this.pager = pager;
	}

	public String getPager() {
		return pager;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setSecretary(String secretary) {
		this.secretary = secretary;
	}

	public String getSecretary() {
		return secretary;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUid() {
		return uid;
	}

	public void setUserCertificate(String userCertificate) {
		this.userCertificate = userCertificate;
	}

	public String getUserCertificate() {
		return userCertificate;
	}

	public void setX500UniqueIdentifier(String x500UniqueIdentifier) {
		this.x500UniqueIdentifier = x500UniqueIdentifier;
	}

	public String getX500UniqueIdentifier() {
		return x500UniqueIdentifier;
	}

	public void setPreferredLanguage(String preferredLanguage) {
		this.preferredLanguage = preferredLanguage;
	}

	public String getPreferredLanguage() {
		return preferredLanguage;
	}

	public void setUserSMIMECertificate(String userSMIMECertificate) {
		this.userSMIMECertificate = userSMIMECertificate;
	}

	public String getUserSMIMECertificate() {
		return userSMIMECertificate;
	}

	public void setUserPKCS12(String userPKCS12) {
		this.userPKCS12 = userPKCS12;
	}

	public String getUserPKCS12() {
		return userPKCS12;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MAY: ");
		buffer.append("audio=").append(audio).append(", ");
		buffer
			.append("businessCategory=")
			.append(businessCategory)
			.append(", ");
		buffer.append("carLicense=").append(carLicense).append(", ");
		buffer
			.append("departmentNumber=")
			.append(departmentNumber)
			.append(", ");
		buffer.append("displayName=").append(displayName).append(", ");
		buffer.append("employeeNumber=").append(employeeNumber).append(", ");
		buffer.append("employeeType=").append(employeeType).append(", ");
		buffer.append("givenName=").append(givenName).append(", ");
		buffer.append("homePhone=").append(homePhone).append(", ");
		buffer.append("homePostalAddress=").append(homePostalAddress).append(
			", ");
		buffer.append("initials=").append(initials).append(", ");
		buffer.append("jpegPhoto=").append(jpegPhoto).append(", ");
		buffer.append("labeledURI=").append(labeledURI).append(", ");
		buffer.append("mail=").append(mail).append(", ");
		buffer.append("manager=").append(manager).append(", ");
		buffer.append("mobile=").append(mobile).append(", ");
		buffer.append("o=").append(o).append(", ");
		buffer.append("pager=").append(pager).append(", ");
		buffer.append("photo=").append(photo).append(", ");
		buffer.append("roomNumber=").append(roomNumber).append(", ");
		buffer.append("secretary=").append(secretary).append(", ");
		buffer.append("uid=").append(uid).append(", ");
		buffer.append("userCertificate=").append(userCertificate).append(", ");
		buffer
			.append("x500UniqueIdentifier=")
			.append(x500UniqueIdentifier)
			.append(", ");
		buffer.append("preferredLanguage=").append(preferredLanguage).append(
			", ");
		buffer
			.append("userSMIMECertificate=")
			.append(userSMIMECertificate)
			.append(", ");
		buffer.append("userPKCS12=").append(userPKCS12);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}