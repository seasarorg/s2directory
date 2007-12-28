import java.io.Serializable;

public class ApacheDnsTextRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String apacheDnsCharacterString;

	public ApacheDnsTextRecord() {
		super();
	}

	public ApacheDnsTextRecord(String apacheDnsCharacterString) {
		super();
		this.apacheDnsCharacterString = apacheDnsCharacterString;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setApacheDnsCharacterString(String apacheDnsCharacterString) {
		this.apacheDnsCharacterString = apacheDnsCharacterString;
	}

	public String getApacheDnsCharacterString() {
		return apacheDnsCharacterString;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MUST: ");
		buffer.append("apacheDnsCharacterString=").append(apacheDnsCharacterString);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}