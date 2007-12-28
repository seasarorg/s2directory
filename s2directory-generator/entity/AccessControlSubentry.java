import java.io.Serializable;

public class AccessControlSubentry implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String prescriptiveACI;

	public AccessControlSubentry() {
		super();
	}

	public AccessControlSubentry(String prescriptiveACI) {
		super();
		this.prescriptiveACI = prescriptiveACI;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setPrescriptiveACI(String prescriptiveACI) {
		this.prescriptiveACI = prescriptiveACI;
	}

	public String getPrescriptiveACI() {
		return prescriptiveACI;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MUST: ");
		buffer.append("prescriptiveACI=").append(prescriptiveACI);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}