import java.io.Serializable;

public class Device implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String cn;
	private String serialNumber;
	private String seeAlso;
	private String owner;
	private String ou;
	private String o;
	private String l;
	private String description;

	public Device() {
		super();
	}

	public Device(String cn) {
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

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSeeAlso(String seeAlso) {
		this.seeAlso = seeAlso;
	}

	public String getSeeAlso() {
		return seeAlso;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getOwner() {
		return owner;
	}

	public void setOu(String ou) {
		this.ou = ou;
	}

	public String getOu() {
		return ou;
	}

	public void setO(String o) {
		this.o = o;
	}

	public String getO() {
		return o;
	}

	public void setL(String l) {
		this.l = l;
	}

	public String getL() {
		return l;
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
		buffer.append("MUST: ");
		buffer.append("cn=").append(cn).append(", ");
		buffer.append("MAY: ");
		buffer.append("serialNumber=").append(serialNumber).append(", ");
		buffer.append("seeAlso=").append(seeAlso).append(", ");
		buffer.append("owner=").append(owner).append(", ");
		buffer.append("ou=").append(ou).append(", ");
		buffer.append("o=").append(o).append(", ");
		buffer.append("l=").append(l).append(", ");
		buffer.append("description=").append(description);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}