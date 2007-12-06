/*
 * Copyright 2005-2006 the Seasar Foundation and the Others.
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

public class OrganizationalUnit implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String ou;
	private String userPassword;
	private String searchGuide;
	private String seeAlso;
	private String businessCategory;
	private String x121Address;
	private String registeredAddress;
	private String destinationIndicator;
	private String preferredDeliveryMethod;
	private String telexNumber;
	private String teletexTerminalIdentifier;
	private String telephoneNumber;
	private String internationaliSDNNumber;
	private String facsimileTelephoneNumber;
	private String street;
	private String postOfficeBox;
	private String postalCode;
	private String postalAddress;
	private String physicalDeliveryOfficeName;
	private String st;
	private String l;
	private String description;

	public OrganizationalUnit() {
		super();
	}

	public OrganizationalUnit(String ou) {
		super();
		this.ou = ou;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setOu(String ou) {
		this.ou = ou;
	}

	public String getOu() {
		return ou;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setSearchGuide(String searchGuide) {
		this.searchGuide = searchGuide;
	}

	public String getSearchGuide() {
		return searchGuide;
	}

	public void setSeeAlso(String seeAlso) {
		this.seeAlso = seeAlso;
	}

	public String getSeeAlso() {
		return seeAlso;
	}

	public void setBusinessCategory(String businessCategory) {
		this.businessCategory = businessCategory;
	}

	public String getBusinessCategory() {
		return businessCategory;
	}

	public void setX121Address(String x121Address) {
		this.x121Address = x121Address;
	}

	public String getX121Address() {
		return x121Address;
	}

	public void setRegisteredAddress(String registeredAddress) {
		this.registeredAddress = registeredAddress;
	}

	public String getRegisteredAddress() {
		return registeredAddress;
	}

	public void setDestinationIndicator(String destinationIndicator) {
		this.destinationIndicator = destinationIndicator;
	}

	public String getDestinationIndicator() {
		return destinationIndicator;
	}

	public void setPreferredDeliveryMethod(String preferredDeliveryMethod) {
		this.preferredDeliveryMethod = preferredDeliveryMethod;
	}

	public String getPreferredDeliveryMethod() {
		return preferredDeliveryMethod;
	}

	public void setTelexNumber(String telexNumber) {
		this.telexNumber = telexNumber;
	}

	public String getTelexNumber() {
		return telexNumber;
	}

	public void setTeletexTerminalIdentifier(String teletexTerminalIdentifier) {
		this.teletexTerminalIdentifier = teletexTerminalIdentifier;
	}

	public String getTeletexTerminalIdentifier() {
		return teletexTerminalIdentifier;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setInternationaliSDNNumber(String internationaliSDNNumber) {
		this.internationaliSDNNumber = internationaliSDNNumber;
	}

	public String getInternationaliSDNNumber() {
		return internationaliSDNNumber;
	}

	public void setFacsimileTelephoneNumber(String facsimileTelephoneNumber) {
		this.facsimileTelephoneNumber = facsimileTelephoneNumber;
	}

	public String getFacsimileTelephoneNumber() {
		return facsimileTelephoneNumber;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreet() {
		return street;
	}

	public void setPostOfficeBox(String postOfficeBox) {
		this.postOfficeBox = postOfficeBox;
	}

	public String getPostOfficeBox() {
		return postOfficeBox;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}

	public String getPostalAddress() {
		return postalAddress;
	}

	public void setPhysicalDeliveryOfficeName(String physicalDeliveryOfficeName) {
		this.physicalDeliveryOfficeName = physicalDeliveryOfficeName;
	}

	public String getPhysicalDeliveryOfficeName() {
		return physicalDeliveryOfficeName;
	}

	public void setSt(String st) {
		this.st = st;
	}

	public String getSt() {
		return st;
	}

	public void setL(String l) {
		this.l = l;
	}

	public String getL() {
		return l;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MUST: ");
		buffer.append("ou=").append(ou).append(", ");
		buffer.append("MAY: ");
		buffer.append("userPassword=").append(userPassword).append(", ");
		buffer.append("searchGuide=").append(searchGuide).append(", ");
		buffer.append("seeAlso=").append(seeAlso).append(", ");
		buffer
			.append("businessCategory=")
			.append(businessCategory)
			.append(", ");
		buffer.append("x121Address=").append(x121Address).append(", ");
		buffer.append("registeredAddress=").append(registeredAddress).append(
			", ");
		buffer
			.append("destinationIndicator=")
			.append(destinationIndicator)
			.append(", ");
		buffer.append("preferredDeliveryMethod=").append(
			preferredDeliveryMethod).append(", ");
		buffer.append("telexNumber=").append(telexNumber).append(", ");
		buffer.append("teletexTerminalIdentifier=").append(
			teletexTerminalIdentifier).append(", ");
		buffer.append("telephoneNumber=").append(telephoneNumber).append(", ");
		buffer.append("internationaliSDNNumber=").append(
			internationaliSDNNumber).append(", ");
		buffer.append("facsimileTelephoneNumber=").append(
			facsimileTelephoneNumber).append(", ");
		buffer.append("street=").append(street).append(", ");
		buffer.append("postOfficeBox=").append(postOfficeBox).append(", ");
		buffer.append("postalCode=").append(postalCode).append(", ");
		buffer.append("postalAddress=").append(postalAddress).append(", ");
		buffer.append("physicalDeliveryOfficeName=").append(
			physicalDeliveryOfficeName).append(", ");
		buffer.append("st=").append(st).append(", ");
		buffer.append("l=").append(l).append(", ");
		buffer.append("description=").append(description);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}