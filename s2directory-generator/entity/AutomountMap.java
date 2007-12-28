import java.io.Serializable;

public class AutomountMap implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String ou;

	public AutomountMap() {
		super();
	}

	public AutomountMap(String ou) {
		super();
		this.ou = ou;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setOu(String ou) {
		this.ou = ou;
	}

	public String getOu() {
		return ou;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MUST: ");
		buffer.append("ou=").append(ou);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}