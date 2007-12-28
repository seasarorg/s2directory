import java.io.Serializable;

public class Subschema implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String dITStructureRules;
	private String nameForms;
	private String dITContentRules;
	private String objectClasses;
	private String attributeTypes;
	private String matchingRules;
	private String matchingRuleUse;

	public Subschema() {
		super();
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setDITStructureRules(String dITStructureRules) {
		this.dITStructureRules = dITStructureRules;
	}

	public String getDITStructureRules() {
		return dITStructureRules;
	}

	public void setNameForms(String nameForms) {
		this.nameForms = nameForms;
	}

	public String getNameForms() {
		return nameForms;
	}

	public void setDITContentRules(String dITContentRules) {
		this.dITContentRules = dITContentRules;
	}

	public String getDITContentRules() {
		return dITContentRules;
	}

	public void setObjectClasses(String objectClasses) {
		this.objectClasses = objectClasses;
	}

	public String getObjectClasses() {
		return objectClasses;
	}

	public void setAttributeTypes(String attributeTypes) {
		this.attributeTypes = attributeTypes;
	}

	public String getAttributeTypes() {
		return attributeTypes;
	}

	public void setMatchingRules(String matchingRules) {
		this.matchingRules = matchingRules;
	}

	public String getMatchingRules() {
		return matchingRules;
	}

	public void setMatchingRuleUse(String matchingRuleUse) {
		this.matchingRuleUse = matchingRuleUse;
	}

	public String getMatchingRuleUse() {
		return matchingRuleUse;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MAY: ");
		buffer.append("dITStructureRules=").append(dITStructureRules).append(", ");
		buffer.append("nameForms=").append(nameForms).append(", ");
		buffer.append("dITContentRules=").append(dITContentRules).append(", ");
		buffer.append("objectClasses=").append(objectClasses).append(", ");
		buffer.append("attributeTypes=").append(attributeTypes).append(", ");
		buffer.append("matchingRules=").append(matchingRules).append(", ");
		buffer.append("matchingRuleUse=").append(matchingRuleUse);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}