import java.io.Serializable;

public class DomainRelatedObject implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String associatedDomain;

	public DomainRelatedObject() {
		super();
	}

	public DomainRelatedObject(String associatedDomain) {
		super();
		this.associatedDomain = associatedDomain;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setAssociatedDomain(String associatedDomain) {
		this.associatedDomain = associatedDomain;
	}

	public String getAssociatedDomain() {
		return associatedDomain;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MUST: ");
		buffer.append("associatedDomain=").append(associatedDomain);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}