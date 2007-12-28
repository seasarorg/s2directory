import java.io.Serializable;

public class FriendlyCountry implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String co;

	public FriendlyCountry() {
		super();
	}

	public FriendlyCountry(String co) {
		super();
		this.co = co;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setCo(String co) {
		this.co = co;
	}

	public String getCo() {
		return co;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MUST: ");
		buffer.append("co=").append(co);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}