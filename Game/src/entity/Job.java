package entity;

public class Job {
	private String name;
	private int experiencePoint;
	private String description;
	private Skill[] jobSkills;
	
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

	public class Warrior extends Job {

	}	

	public class Rogue extends Job {

	}

	public class Mage extends Job {

	}
	
	public class Priest extends Job {

	}
}
