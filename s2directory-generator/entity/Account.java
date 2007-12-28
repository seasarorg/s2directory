import java.io.Serializable;

public class Account implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String uid;
	private String description;
	private String seeAlso;
	private String l;
	private String o;
	private String ou;
	private String host;

	public Account() {
		super();
	}

	public Account(String uid) {
		super();
		this.uid = uid;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUid() {
		return uid;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setSeeAlso(String seeAlso) {
		this.seeAlso = seeAlso;
	}

	public String getSeeAlso() {
		return seeAlso;
	}

	public void setL(String l) {
		this.l = l;
	}

	public String getL() {
		return l;
	}

	public void setO(String o) {
		this.o = o;
	}

	public String getO() {
		return o;
	}

	public void setOu(String ou) {
		this.ou = ou;
	}

	public String getOu() {
		return ou;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getHost() {
		return host;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MUST: ");
		buffer.append("uid=").append(uid).append(", ");
		buffer.append("MAY: ");
		buffer.append("description=").append(description).append(", ");
		buffer.append("seeAlso=").append(seeAlso).append(", ");
		buffer.append("l=").append(l).append(", ");
		buffer.append("o=").append(o).append(", ");
		buffer.append("ou=").append(ou).append(", ");
		buffer.append("host=").append(host);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}