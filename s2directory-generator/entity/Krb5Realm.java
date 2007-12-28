import java.io.Serializable;

public class Krb5Realm implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String krb5RealmName;

	public Krb5Realm() {
		super();
	}

	public Krb5Realm(String krb5RealmName) {
		super();
		this.krb5RealmName = krb5RealmName;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setKrb5RealmName(String krb5RealmName) {
		this.krb5RealmName = krb5RealmName;
	}

	public String getKrb5RealmName() {
		return krb5RealmName;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MUST: ");
		buffer.append("krb5RealmName=").append(krb5RealmName);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}