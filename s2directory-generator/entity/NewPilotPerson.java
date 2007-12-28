import java.io.Serializable;

public class NewPilotPerson implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String uid;
	private String textEncodedORAddress;
	private String mail;
	private String drink;
	private String roomNumber;
	private String userClass;
	private String homePhone;
	private String homePostalAddress;
	private String secretary;
	private String personalTitle;
	private String preferredDeliveryMethod;
	private String businessCategory;
	private String janetMailbox;
	private String otherMailbox;
	private String mobile;
	private String pager;
	private String organizationalStatus;
	private String mailPreferenceOption;
	private String personalSignature;

	public NewPilotPerson() {
		super();
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

	public void setTextEncodedORAddress(String textEncodedORAddress) {
		this.textEncodedORAddress = textEncodedORAddress;
	}

	public String getTextEncodedORAddress() {
		return textEncodedORAddress;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMail() {
		return mail;
	}

	public void setDrink(String drink) {
		this.drink = drink;
	}

	public String getDrink() {
		return drink;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setUserClass(String userClass) {
		this.userClass = userClass;
	}

	public String getUserClass() {
		return userClass;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePostalAddress(String homePostalAddress) {
		this.homePostalAddress = homePostalAddress;
	}

	public String getHomePostalAddress() {
		return homePostalAddress;
	}

	public void setSecretary(String secretary) {
		this.secretary = secretary;
	}

	public String getSecretary() {
		return secretary;
	}

	public void setPersonalTitle(String personalTitle) {
		this.personalTitle = personalTitle;
	}

	public String getPersonalTitle() {
		return personalTitle;
	}

	public void setPreferredDeliveryMethod(String preferredDeliveryMethod) {
		this.preferredDeliveryMethod = preferredDeliveryMethod;
	}

	public String getPreferredDeliveryMethod() {
		return preferredDeliveryMethod;
	}

	public void setBusinessCategory(String businessCategory) {
		this.businessCategory = businessCategory;
	}

	public String getBusinessCategory() {
		return businessCategory;
	}

	public void setJanetMailbox(String janetMailbox) {
		this.janetMailbox = janetMailbox;
	}

	public String getJanetMailbox() {
		return janetMailbox;
	}

	public void setOtherMailbox(String otherMailbox) {
		this.otherMailbox = otherMailbox;
	}

	public String getOtherMailbox() {
		return otherMailbox;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMobile() {
		return mobile;
	}

	public void setPager(String pager) {
		this.pager = pager;
	}

	public String getPager() {
		return pager;
	}

	public void setOrganizationalStatus(String organizationalStatus) {
		this.organizationalStatus = organizationalStatus;
	}

	public String getOrganizationalStatus() {
		return organizationalStatus;
	}

	public void setMailPreferenceOption(String mailPreferenceOption) {
		this.mailPreferenceOption = mailPreferenceOption;
	}

	public String getMailPreferenceOption() {
		return mailPreferenceOption;
	}

	public void setPersonalSignature(String personalSignature) {
		this.personalSignature = personalSignature;
	}

	public String getPersonalSignature() {
		return personalSignature;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MAY: ");
		buffer.append("uid=").append(uid).append(", ");
		buffer.append("textEncodedORAddress=").append(textEncodedORAddress).append(", ");
		buffer.append("mail=").append(mail).append(", ");
		buffer.append("drink=").append(drink).append(", ");
		buffer.append("roomNumber=").append(roomNumber).append(", ");
		buffer.append("userClass=").append(userClass).append(", ");
		buffer.append("homePhone=").append(homePhone).append(", ");
		buffer.append("homePostalAddress=").append(homePostalAddress).append(", ");
		buffer.append("secretary=").append(secretary).append(", ");
		buffer.append("personalTitle=").append(personalTitle).append(", ");
		buffer.append("preferredDeliveryMethod=").append(preferredDeliveryMethod).append(", ");
		buffer.append("businessCategory=").append(businessCategory).append(", ");
		buffer.append("janetMailbox=").append(janetMailbox).append(", ");
		buffer.append("otherMailbox=").append(otherMailbox).append(", ");
		buffer.append("mobile=").append(mobile).append(", ");
		buffer.append("pager=").append(pager).append(", ");
		buffer.append("organizationalStatus=").append(organizationalStatus).append(", ");
		buffer.append("mailPreferenceOption=").append(mailPreferenceOption).append(", ");
		buffer.append("personalSignature=").append(personalSignature);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}