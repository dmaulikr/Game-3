package entity;

public class Job {
	public enum jobList {
		Warrior
	}
	
	private String name;
	private String description;

	private boolean available = false;
	private boolean visible = false;

	private int experiencePoint;

	protected Skill[] jobSkills;

	// Load a state of a Skill Tree
	public void UpdateSkillSheet(boolean[] skillSheetTree) {
		for (int i = 0; i < skillSheetTree.length; i++) {
			jobSkills[i].setBought(skillSheetTree[i]);
		}
	}

	public void getCharJobState(String XMLjobState) {
		setExperiencePoint(Integer.valueOf(GetXMLElement(XMLjobState, "expPoints")));
		int nbSkills = (Integer.valueOf(GetXMLElement(XMLjobState, "nbSkills")));

		String XMLskillPoints = GetXMLElement(XMLjobState, "Skills");
		boolean[] jobSkillPoints = new boolean[nbSkills];

		for (int i = 0; i < nbSkills; i++) {
			if (GetXMLElement(XMLskillPoints, String.valueOf(i)).equals("true")) {
				jobSkillPoints[i] = true;
			} else {
				jobSkillPoints[i] = false;
			}
		}
		UpdateSkillSheet(jobSkillPoints);
	}

	public String saveToXML() {
		String s = new String();
		s = "";

		s += "<expPoints>";
		s += this.getExperiencePoint();
		s += "</expPoints>";

		s += "<nbSkills>";
		s += jobSkills.length;
		s += "</nbSkills>";

		s += "<Skills>";
		for (int i = 0; i < jobSkills.length; i++) {
			s += "<" + i + ">";
			s += jobSkills[i].isBought();
			s += "</" + i + ">";
		}
		s += "</Skills>";

		return s;
	}

	private String GetXMLElement(String XMLString, String balise) {
		int len = ("<" + balise + ">").length();
		int posDeb = XMLString.indexOf("<" + balise + ">");
		int posFin = XMLString.indexOf("</" + balise + ">");

		return XMLString.substring(posDeb + len, posFin);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getExperiencePoint() {
		return experiencePoint;
	}

	public void setExperiencePoint(int experiencePoint) {
		this.experiencePoint = experiencePoint;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}

/*
 * public class Rogue extends Job {
 * 
 * }
 * 
 * public class Mage extends Job {
 * 
 * }
 * 
 * public class Priest extends Job {
 * 
 * }
 */
