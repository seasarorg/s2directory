import java.io.Serializable;

public class DSA implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String knowledgeInformation;

	public DSA() {
		super();
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setKnowledgeInformation(String knowledgeInformation) {
		this.knowledgeInformation = knowledgeInformation;
	}

	public String getKnowledgeInformation() {
		return knowledgeInformation;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MAY: ");
		buffer.append("knowledgeInformation=").append(knowledgeInformation);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}