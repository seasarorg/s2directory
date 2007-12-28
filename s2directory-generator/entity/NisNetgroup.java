import java.io.Serializable;

public class NisNetgroup implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String cn;
	private String nisNetgroupTriple;
	private String memberNisNetgroup;
	private String description;

	public NisNetgroup() {
		super();
	}

	public NisNetgroup(String cn) {
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

	public void setNisNetgroupTriple(String nisNetgroupTriple) {
		this.nisNetgroupTriple = nisNetgroupTriple;
	}

	public String getNisNetgroupTriple() {
		return nisNetgroupTriple;
	}

	public void setMemberNisNetgroup(String memberNisNetgroup) {
		this.memberNisNetgroup = memberNisNetgroup;
	}

	public String getMemberNisNetgroup() {
		return memberNisNetgroup;
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
		buffer.append("nisNetgroupTriple=").append(nisNetgroupTriple).append(", ");
		buffer.append("memberNisNetgroup=").append(memberNisNetgroup).append(", ");
		buffer.append("description=").append(description);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}