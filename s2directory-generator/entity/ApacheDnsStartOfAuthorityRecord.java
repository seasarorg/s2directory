import java.io.Serializable;

public class ApacheDnsStartOfAuthorityRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String apacheDnsSoaMName;
	private String apacheDnsSoaRName;
	private String apacheDnsSoaMinimum;
	private String apacheDnsClass;
	private String apacheDnsSoaSerial;
	private String apacheDnsSoaRefresh;
	private String apacheDnsSoaRetry;
	private String apacheDnsSoaExpire;

	public ApacheDnsStartOfAuthorityRecord() {
		super();
	}

	public ApacheDnsStartOfAuthorityRecord(String apacheDnsSoaMName, String apacheDnsSoaRName, String apacheDnsSoaMinimum) {
		super();
		this.apacheDnsSoaMName = apacheDnsSoaMName;
		this.apacheDnsSoaRName = apacheDnsSoaRName;
		this.apacheDnsSoaMinimum = apacheDnsSoaMinimum;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setApacheDnsSoaMName(String apacheDnsSoaMName) {
		this.apacheDnsSoaMName = apacheDnsSoaMName;
	}

	public String getApacheDnsSoaMName() {
		return apacheDnsSoaMName;
	}

	public void setApacheDnsSoaRName(String apacheDnsSoaRName) {
		this.apacheDnsSoaRName = apacheDnsSoaRName;
	}

	public String getApacheDnsSoaRName() {
		return apacheDnsSoaRName;
	}

	public void setApacheDnsSoaMinimum(String apacheDnsSoaMinimum) {
		this.apacheDnsSoaMinimum = apacheDnsSoaMinimum;
	}

	public String getApacheDnsSoaMinimum() {
		return apacheDnsSoaMinimum;
	}

	public void setApacheDnsClass(String apacheDnsClass) {
		this.apacheDnsClass = apacheDnsClass;
	}

	public String getApacheDnsClass() {
		return apacheDnsClass;
	}

	public void setApacheDnsSoaSerial(String apacheDnsSoaSerial) {
		this.apacheDnsSoaSerial = apacheDnsSoaSerial;
	}

	public String getApacheDnsSoaSerial() {
		return apacheDnsSoaSerial;
	}

	public void setApacheDnsSoaRefresh(String apacheDnsSoaRefresh) {
		this.apacheDnsSoaRefresh = apacheDnsSoaRefresh;
	}

	public String getApacheDnsSoaRefresh() {
		return apacheDnsSoaRefresh;
	}

	public void setApacheDnsSoaRetry(String apacheDnsSoaRetry) {
		this.apacheDnsSoaRetry = apacheDnsSoaRetry;
	}

	public String getApacheDnsSoaRetry() {
		return apacheDnsSoaRetry;
	}

	public void setApacheDnsSoaExpire(String apacheDnsSoaExpire) {
		this.apacheDnsSoaExpire = apacheDnsSoaExpire;
	}

	public String getApacheDnsSoaExpire() {
		return apacheDnsSoaExpire;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MUST: ");
		buffer.append("apacheDnsSoaMName=").append(apacheDnsSoaMName).append(", ");
		buffer.append("apacheDnsSoaRName=").append(apacheDnsSoaRName).append(", ");
		buffer.append("apacheDnsSoaMinimum=").append(apacheDnsSoaMinimum).append(", ");
		buffer.append("MAY: ");
		buffer.append("apacheDnsClass=").append(apacheDnsClass).append(", ");
		buffer.append("apacheDnsSoaSerial=").append(apacheDnsSoaSerial).append(", ");
		buffer.append("apacheDnsSoaRefresh=").append(apacheDnsSoaRefresh).append(", ");
		buffer.append("apacheDnsSoaRetry=").append(apacheDnsSoaRetry).append(", ");
		buffer.append("apacheDnsSoaExpire=").append(apacheDnsSoaExpire);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}