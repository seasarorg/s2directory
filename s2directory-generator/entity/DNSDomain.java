import java.io.Serializable;

public class DNSDomain implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String aRecord;
	private String mDRecord;
	private String mXRecord;
	private String nSRecord;
	private String sOARecord;
	private String cNAMERecord;

	public DNSDomain() {
		super();
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setARecord(String aRecord) {
		this.aRecord = aRecord;
	}

	public String getARecord() {
		return aRecord;
	}

	public void setMDRecord(String mDRecord) {
		this.mDRecord = mDRecord;
	}

	public String getMDRecord() {
		return mDRecord;
	}

	public void setMXRecord(String mXRecord) {
		this.mXRecord = mXRecord;
	}

	public String getMXRecord() {
		return mXRecord;
	}

	public void setNSRecord(String nSRecord) {
		this.nSRecord = nSRecord;
	}

	public String getNSRecord() {
		return nSRecord;
	}

	public void setSOARecord(String sOARecord) {
		this.sOARecord = sOARecord;
	}

	public String getSOARecord() {
		return sOARecord;
	}

	public void setCNAMERecord(String cNAMERecord) {
		this.cNAMERecord = cNAMERecord;
	}

	public String getCNAMERecord() {
		return cNAMERecord;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MAY: ");
		buffer.append("aRecord=").append(aRecord).append(", ");
		buffer.append("mDRecord=").append(mDRecord).append(", ");
		buffer.append("mXRecord=").append(mXRecord).append(", ");
		buffer.append("nSRecord=").append(nSRecord).append(", ");
		buffer.append("sOARecord=").append(sOARecord).append(", ");
		buffer.append("cNAMERecord=").append(cNAMERecord);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}