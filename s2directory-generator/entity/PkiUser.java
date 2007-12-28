import java.io.Serializable;

public class PkiUser implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String userCertificate;

	public PkiUser() {
		super();
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setUserCertificate(String userCertificate) {
		this.userCertificate = userCertificate;
	}

	public String getUserCertificate() {
		return userCertificate;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MAY: ");
		buffer.append("userCertificate=").append(userCertificate);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}