import java.io.Serializable;

public class LabeledURIObject implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String labeledURI;

	public LabeledURIObject() {
		super();
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setLabeledURI(String labeledURI) {
		this.labeledURI = labeledURI;
	}

	public String getLabeledURI() {
		return labeledURI;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MAY: ");
		buffer.append("labeledURI=").append(labeledURI);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}