import java.io.Serializable;

public class Alias implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String aliasedObjectName;

	public Alias() {
		super();
	}

	public Alias(String aliasedObjectName) {
		super();
		this.aliasedObjectName = aliasedObjectName;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setAliasedObjectName(String aliasedObjectName) {
		this.aliasedObjectName = aliasedObjectName;
	}

	public String getAliasedObjectName() {
		return aliasedObjectName;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MUST: ");
		buffer.append("aliasedObjectName=").append(aliasedObjectName);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}