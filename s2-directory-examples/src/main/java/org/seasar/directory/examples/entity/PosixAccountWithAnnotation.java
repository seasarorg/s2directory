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

public class PosixAccountWithAnnotation implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String OBJECTCLASSES = "posixAccount, inetOrgPerson";
	private String dn;
	private String cn;
	private String uid;
	private String uidNumber;
	public static final String gid_ATTRIBUTE = "gidNumber";
	public static final String gid_COLUMN = "gidDummy";
	private String gid;
	public static final String home_ATTRIBUTE = "homeDirectory";
	private String home;
	public static final String password_ATTRIBUTE = "userPassword";
	private String password;
	private String loginShell;
	private String gecos;
	private String description;

	public PosixAccountWithAnnotation() {
		super();
	}

	public PosixAccountWithAnnotation(String cn, String uid, String uidNumber,
			String gid, String homeDirectory) {
		super();
		this.cn = cn;
		this.uid = uid;
		this.uidNumber = uidNumber;
		this.gid = gid;
		this.home = homeDirectory;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getCn() {
		return cn;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUid() {
		return uid;
	}

	public void setUidNumber(String uidNumber) {
		this.uidNumber = uidNumber;
	}

	public String getUidNumber() {
		return uidNumber;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getGid() {
		return gid;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getHome() {
		return home;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setLoginShell(String loginShell) {
		this.loginShell = loginShell;
	}

	public String getLoginShell() {
		return loginShell;
	}

	public void setGecos(String gecos) {
		this.gecos = gecos;
	}

	public String getGecos() {
		return gecos;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("DN: ");
		buf.append("dn=").append(dn).append(", ");
		buf.append("MUST: ");
		buf.append("cn=").append(cn).append(", ");
		buf.append("uid=").append(uid).append(", ");
		buf.append("uidNumber=").append(uidNumber).append(", ");
		buf.append("gid=").append(gid).append(", ");
		buf.append("home=").append(home).append(", ");
		buf.append("MAY: ");
		buf.append("password=").append(password).append(", ");
		buf.append("loginShell=").append(loginShell).append(", ");
		buf.append("gecos=").append(gecos).append(", ");
		buf.append("description=").append(description);
		return buf.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}
