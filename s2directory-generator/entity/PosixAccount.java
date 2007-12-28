import java.io.Serializable;

public class PosixAccount implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String cn;
	private String uid;
	private String uidNumber;
	private String gidNumber;
	private String homeDirectory;
	private String userPassword;
	private String loginShell;
	private String gecos;
	private String description;

	public PosixAccount() {
		super();
	}

	public PosixAccount(String cn, String uid, String uidNumber, String gidNumber, String homeDirectory) {
		super();
		this.cn = cn;
		this.uid = uid;
		this.uidNumber = uidNumber;
		this.gidNumber = gidNumber;
		this.homeDirectory = homeDirectory;
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

	public void setGidNumber(String gidNumber) {
		this.gidNumber = gidNumber;
	}

	public String getGidNumber() {
		return gidNumber;
	}

	public void setHomeDirectory(String homeDirectory) {
		this.homeDirectory = homeDirectory;
	}

	public String getHomeDirectory() {
		return homeDirectory;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserPassword() {
		return userPassword;
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
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MUST: ");
		buffer.append("cn=").append(cn).append(", ");
		buffer.append("uid=").append(uid).append(", ");
		buffer.append("uidNumber=").append(uidNumber).append(", ");
		buffer.append("gidNumber=").append(gidNumber).append(", ");
		buffer.append("homeDirectory=").append(homeDirectory).append(", ");
		buffer.append("MAY: ");
		buffer.append("userPassword=").append(userPassword).append(", ");
		buffer.append("loginShell=").append(loginShell).append(", ");
		buffer.append("gecos=").append(gecos).append(", ");
		buffer.append("description=").append(description);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}