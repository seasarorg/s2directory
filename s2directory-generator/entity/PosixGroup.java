import java.io.Serializable;

public class PosixGroup implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String cn;
	private String gidNumber;
	private String userPassword;
	private String memberUid;
	private String description;

	public PosixGroup() {
		super();
	}

	public PosixGroup(String cn, String gidNumber) {
		super();
		this.cn = cn;
		this.gidNumber = gidNumber;
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

	public void setGidNumber(String gidNumber) {
		this.gidNumber = gidNumber;
	}

	public String getGidNumber() {
		return gidNumber;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setMemberUid(String memberUid) {
		this.memberUid = memberUid;
	}

	public String getMemberUid() {
		return memberUid;
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
		buffer.append("gidNumber=").append(gidNumber).append(", ");
		buffer.append("MAY: ");
		buffer.append("userPassword=").append(userPassword).append(", ");
		buffer.append("memberUid=").append(memberUid).append(", ");
		buffer.append("description=").append(description);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}