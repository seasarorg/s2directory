import java.io.Serializable;

public class Referral implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String ref;

	public Referral() {
		super();
	}

	public Referral(String ref) {
		super();
		this.ref = ref;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getRef() {
		return ref;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MUST: ");
		buffer.append("ref=").append(ref);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}