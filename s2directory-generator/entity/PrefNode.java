import java.io.Serializable;

public class PrefNode implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String prefNodeName;

	public PrefNode() {
		super();
	}

	public PrefNode(String prefNodeName) {
		super();
		this.prefNodeName = prefNodeName;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setPrefNodeName(String prefNodeName) {
		this.prefNodeName = prefNodeName;
	}

	public String getPrefNodeName() {
		return prefNodeName;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MUST: ");
		buffer.append("prefNodeName=").append(prefNodeName);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}