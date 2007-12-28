import java.io.Serializable;

public class Krb5KDCEntry implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String krb5KeyVersionNumber;
	private String krb5ValidStart;
	private String krb5ValidEnd;
	private String krb5PasswordEnd;
	private String krb5MaxLife;
	private String krb5MaxRenew;
	private String krb5KDCFlags;
	private String krb5EncryptionType;
	private String krb5Key;
	private String krb5AccountDisabled;
	private String krb5AccountLockedOut;
	private String krb5AccountExpirationTime;

	public Krb5KDCEntry() {
		super();
	}

	public Krb5KDCEntry(String krb5KeyVersionNumber) {
		super();
		this.krb5KeyVersionNumber = krb5KeyVersionNumber;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setKrb5KeyVersionNumber(String krb5KeyVersionNumber) {
		this.krb5KeyVersionNumber = krb5KeyVersionNumber;
	}

	public String getKrb5KeyVersionNumber() {
		return krb5KeyVersionNumber;
	}

	public void setKrb5ValidStart(String krb5ValidStart) {
		this.krb5ValidStart = krb5ValidStart;
	}

	public String getKrb5ValidStart() {
		return krb5ValidStart;
	}

	public void setKrb5ValidEnd(String krb5ValidEnd) {
		this.krb5ValidEnd = krb5ValidEnd;
	}

	public String getKrb5ValidEnd() {
		return krb5ValidEnd;
	}

	public void setKrb5PasswordEnd(String krb5PasswordEnd) {
		this.krb5PasswordEnd = krb5PasswordEnd;
	}

	public String getKrb5PasswordEnd() {
		return krb5PasswordEnd;
	}

	public void setKrb5MaxLife(String krb5MaxLife) {
		this.krb5MaxLife = krb5MaxLife;
	}

	public String getKrb5MaxLife() {
		return krb5MaxLife;
	}

	public void setKrb5MaxRenew(String krb5MaxRenew) {
		this.krb5MaxRenew = krb5MaxRenew;
	}

	public String getKrb5MaxRenew() {
		return krb5MaxRenew;
	}

	public void setKrb5KDCFlags(String krb5KDCFlags) {
		this.krb5KDCFlags = krb5KDCFlags;
	}

	public String getKrb5KDCFlags() {
		return krb5KDCFlags;
	}

	public void setKrb5EncryptionType(String krb5EncryptionType) {
		this.krb5EncryptionType = krb5EncryptionType;
	}

	public String getKrb5EncryptionType() {
		return krb5EncryptionType;
	}

	public void setKrb5Key(String krb5Key) {
		this.krb5Key = krb5Key;
	}

	public String getKrb5Key() {
		return krb5Key;
	}

	public void setKrb5AccountDisabled(String krb5AccountDisabled) {
		this.krb5AccountDisabled = krb5AccountDisabled;
	}

	public String getKrb5AccountDisabled() {
		return krb5AccountDisabled;
	}

	public void setKrb5AccountLockedOut(String krb5AccountLockedOut) {
		this.krb5AccountLockedOut = krb5AccountLockedOut;
	}

	public String getKrb5AccountLockedOut() {
		return krb5AccountLockedOut;
	}

	public void setKrb5AccountExpirationTime(String krb5AccountExpirationTime) {
		this.krb5AccountExpirationTime = krb5AccountExpirationTime;
	}

	public String getKrb5AccountExpirationTime() {
		return krb5AccountExpirationTime;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MUST: ");
		buffer.append("krb5KeyVersionNumber=").append(krb5KeyVersionNumber).append(", ");
		buffer.append("MAY: ");
		buffer.append("krb5ValidStart=").append(krb5ValidStart).append(", ");
		buffer.append("krb5ValidEnd=").append(krb5ValidEnd).append(", ");
		buffer.append("krb5PasswordEnd=").append(krb5PasswordEnd).append(", ");
		buffer.append("krb5MaxLife=").append(krb5MaxLife).append(", ");
		buffer.append("krb5MaxRenew=").append(krb5MaxRenew).append(", ");
		buffer.append("krb5KDCFlags=").append(krb5KDCFlags).append(", ");
		buffer.append("krb5EncryptionType=").append(krb5EncryptionType).append(", ");
		buffer.append("krb5Key=").append(krb5Key).append(", ");
		buffer.append("krb5AccountDisabled=").append(krb5AccountDisabled).append(", ");
		buffer.append("krb5AccountLockedOut=").append(krb5AccountLockedOut).append(", ");
		buffer.append("krb5AccountExpirationTime=").append(krb5AccountExpirationTime);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}