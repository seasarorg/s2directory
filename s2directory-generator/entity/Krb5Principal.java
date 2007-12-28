import java.io.Serializable;

public class Krb5Principal implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String krb5PrincipalName;
	private String cn;
	private String krb5PrincipalRealm;

	public Krb5Principal() {
		super();
	}

	public Krb5Principal(String krb5PrincipalName) {
		super();
		this.krb5PrincipalName = krb5PrincipalName;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setKrb5PrincipalName(String krb5PrincipalName) {
		this.krb5PrincipalName = krb5PrincipalName;
	}

	public String getKrb5PrincipalName() {
		return krb5PrincipalName;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getCn() {
		return cn;
	}

	public void setKrb5PrincipalRealm(String krb5PrincipalRealm) {
		this.krb5PrincipalRealm = krb5PrincipalRealm;
	}

	public String getKrb5PrincipalRealm() {
		return krb5PrincipalRealm;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MUST: ");
		buffer.append("krb5PrincipalName=").append(krb5PrincipalName).append(", ");
		buffer.append("MAY: ");
		buffer.append("cn=").append(cn).append(", ");
		buffer.append("krb5PrincipalRealm=").append(krb5PrincipalRealm);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}