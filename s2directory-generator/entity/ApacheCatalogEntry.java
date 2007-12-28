import java.io.Serializable;

public class ApacheCatalogEntry implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String cn;
	private String apacheCatalogEntryBaseDn;
	private String apacheCatalogEntryName;

	public ApacheCatalogEntry() {
		super();
	}

	public ApacheCatalogEntry(String cn, String apacheCatalogEntryBaseDn) {
		super();
		this.cn = cn;
		this.apacheCatalogEntryBaseDn = apacheCatalogEntryBaseDn;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getCn() {
		return cn;
	}

	public void setApacheCatalogEntryBaseDn(String apacheCatalogEntryBaseDn) {
		this.apacheCatalogEntryBaseDn = apacheCatalogEntryBaseDn;
	}

	public String getApacheCatalogEntryBaseDn() {
		return apacheCatalogEntryBaseDn;
	}

	public void setApacheCatalogEntryName(String apacheCatalogEntryName) {
		this.apacheCatalogEntryName = apacheCatalogEntryName;
	}

	public String getApacheCatalogEntryName() {
		return apacheCatalogEntryName;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MUST: ");
		buffer.append("cn=").append(cn).append(", ");
		buffer.append("apacheCatalogEntryBaseDn=").append(apacheCatalogEntryBaseDn).append(", ");
		buffer.append("MAY: ");
		buffer.append("apacheCatalogEntryName=").append(apacheCatalogEntryName);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}