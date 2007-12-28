import java.io.Serializable;

public class DcObject implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String dc;

	public DcObject() {
		super();
	}

	public DcObject(String dc) {
		super();
		this.dc = dc;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setDc(String dc) {
		this.dc = dc;
	}

	public String getDc() {
		return dc;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MUST: ");
		buffer.append("dc=").append(dc);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}