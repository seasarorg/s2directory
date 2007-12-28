import java.io.Serializable;

public class PilotDSA implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String dSAQuality;

	public PilotDSA() {
		super();
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setDSAQuality(String dSAQuality) {
		this.dSAQuality = dSAQuality;
	}

	public String getDSAQuality() {
		return dSAQuality;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MAY: ");
		buffer.append("dSAQuality=").append(dSAQuality);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}