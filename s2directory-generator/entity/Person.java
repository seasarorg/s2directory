import java.io.Serializable;

public class Person implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String sn;
	private String cn;
	private String userPassword;
	private String telephoneNumber;
	private String seeAlso;
	private String description;

	public Person() {
		super();
	}

	public Person(String sn, String cn) {
		super();
		this.sn = sn;
		this.cn = cn;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getSn() {
		return sn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getCn() {
		return cn;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setSeeAlso(String seeAlso) {
		this.seeAlso = seeAlso;
	}

	public String getSeeAlso() {
		return seeAlso;
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
		buffer.append("sn=").append(sn).append(", ");
		buffer.append("cn=").append(cn).append(", ");
		buffer.append("MAY: ");
		buffer.append("userPassword=").append(userPassword).append(", ");
		buffer.append("telephoneNumber=").append(telephoneNumber).append(", ");
		buffer.append("seeAlso=").append(seeAlso).append(", ");
		buffer.append("description=").append(description);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}