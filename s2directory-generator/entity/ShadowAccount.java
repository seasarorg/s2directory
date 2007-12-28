import java.io.Serializable;

public class ShadowAccount implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String uid;
	private String userPassword;
	private String shadowLastChange;
	private String shadowMin;
	private String shadowMax;
	private String shadowWarning;
	private String shadowInactive;
	private String shadowExpire;
	private String shadowFlag;
	private String description;

	public ShadowAccount() {
		super();
	}

	public ShadowAccount(String uid) {
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

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setShadowLastChange(String shadowLastChange) {
		this.shadowLastChange = shadowLastChange;
	}

	public String getShadowLastChange() {
		return shadowLastChange;
	}

	public void setShadowMin(String shadowMin) {
		this.shadowMin = shadowMin;
	}

	public String getShadowMin() {
		return shadowMin;
	}

	public void setShadowMax(String shadowMax) {
		this.shadowMax = shadowMax;
	}

	public String getShadowMax() {
		return shadowMax;
	}

	public void setShadowWarning(String shadowWarning) {
		this.shadowWarning = shadowWarning;
	}

	public String getShadowWarning() {
		return shadowWarning;
	}

	public void setShadowInactive(String shadowInactive) {
		this.shadowInactive = shadowInactive;
	}

	public String getShadowInactive() {
		return shadowInactive;
	}

	public void setShadowExpire(String shadowExpire) {
		this.shadowExpire = shadowExpire;
	}

	public String getShadowExpire() {
		return shadowExpire;
	}

	public void setShadowFlag(String shadowFlag) {
		this.shadowFlag = shadowFlag;
	}

	public String getShadowFlag() {
		return shadowFlag;
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
		buffer.append("uid=").append(uid).append(", ");
		buffer.append("MAY: ");
		buffer.append("userPassword=").append(userPassword).append(", ");
		buffer.append("shadowLastChange=").append(shadowLastChange).append(", ");
		buffer.append("shadowMin=").append(shadowMin).append(", ");
		buffer.append("shadowMax=").append(shadowMax).append(", ");
		buffer.append("shadowWarning=").append(shadowWarning).append(", ");
		buffer.append("shadowInactive=").append(shadowInactive).append(", ");
		buffer.append("shadowExpire=").append(shadowExpire).append(", ");
		buffer.append("shadowFlag=").append(shadowFlag).append(", ");
		buffer.append("description=").append(description);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}