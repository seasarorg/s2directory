import java.io.Serializable;

public class DocumentSeries implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String cn;
	private String description;
	private String seeAlso;
	private String telephoneNumber;
	private String l;
	private String o;
	private String ou;

	public DocumentSeries() {
		super();
	}

	public DocumentSeries(String cn) {
		super();
		this.cn = cn;
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

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
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

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MUST: ");
		buffer.append("cn=").append(cn).append(", ");
		buffer.append("MAY: ");
		buffer.append("description=").append(description).append(", ");
		buffer.append("seeAlso=").append(seeAlso).append(", ");
		buffer.append("telephoneNumber=").append(telephoneNumber).append(", ");
		buffer.append("l=").append(l).append(", ");
		buffer.append("o=").append(o).append(", ");
		buffer.append("ou=").append(ou);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}