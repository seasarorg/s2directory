import java.io.Serializable;

public class UserSecurityInformation implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String supportedAlgorithms;

	public UserSecurityInformation() {
		super();
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setSupportedAlgorithms(String supportedAlgorithms) {
		this.supportedAlgorithms = supportedAlgorithms;
	}

	public String getSupportedAlgorithms() {
		return supportedAlgorithms;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MAY: ");
		buffer.append("supportedAlgorithms=").append(supportedAlgorithms);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}