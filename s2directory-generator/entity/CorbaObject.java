import java.io.Serializable;

public class CorbaObject implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String corbaRepositoryId;
	private String description;

	public CorbaObject() {
		super();
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setCorbaRepositoryId(String corbaRepositoryId) {
		this.corbaRepositoryId = corbaRepositoryId;
	}

	public String getCorbaRepositoryId() {
		return corbaRepositoryId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MAY: ");
		buffer.append("corbaRepositoryId=").append(corbaRepositoryId).append(", ");
		buffer.append("description=").append(description);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}