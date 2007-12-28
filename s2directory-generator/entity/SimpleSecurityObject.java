import java.io.Serializable;

public class SimpleSecurityObject implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String userPassword;

	public SimpleSecurityObject() {
		super();
	}

	public SimpleSecurityObject(String userPassword) {
		super();
		this.userPassword = userPassword;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MUST: ");
		buffer.append("userPassword=").append(userPassword);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}