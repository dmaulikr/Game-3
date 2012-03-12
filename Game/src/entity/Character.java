package entity;

import org.newdawn.slick.opengl.Texture;

import jobs.Warrior;

enum Race {
	Human, Dwarf, Elve
}

public class Character {
	private Race race;

	private Job actualJob;
	private Job[] jobs;
	private int posX;
	private int posY;

	private int lifePoints;
	private int manaPoints;
	private int maxLifePoints;
	private int maxManaPoints;

	private int initiative;
	private int movement;

	private int attackPower;
	private int magicPower;

	private int armor;
	private int magicArmor;

	private int armorPenetration;
	private int magicPenetration;

	private int level;
	private int experience;

	private String name;

	private int numberOfAnimations;
	private int[] numberOfSprites;

	private Texture[][] sprites;

	private int spriteSpeed;
	
	public Character(Race race, Job job) {
		this.setRace(race);
		this.level = 1;
		this.experience = 0;
		this.setActualJob(job);

		jobs = new Job[jobList.values().length];
		jobs[0] = new Warrior();
		actualJob = jobs[0];

		switch (race) {
		case Human:
			maxLifePoints = 50;
			maxManaPoints = 10;

			attackPower = 15;
			magicPower = 15;

			armor = 13;
			magicArmor = 10;

			armorPenetration = 0;
			magicPenetration = 0;

			initiative = 5;
			movement = 5;
			break;
		case Dwarf:
			maxLifePoints = 60;
			maxManaPoints = 5;

			attackPower = 15;
			magicPower = 15;

			armor = 15;
			magicArmor = 15;

			armorPenetration = 0;
			magicPenetration = 0;

			initiative = 4;
			movement = 4;
			break;
		case Elve:
			maxLifePoints = 40;
			maxManaPoints = 15;

			attackPower = 15;
			magicPower = 15;

			armor = 10;
			magicArmor = 13;

			armorPenetration = 0;
			magicPenetration = 0;

			initiative = 6;
			movement = 6;
			break;
		default:
			break;
		}

	}

	public Character(String XMLString) {
		this.setRace(Race.valueOf(GetXMLElement(XMLString, "RACE")));
		this.level = Integer.valueOf(GetXMLElement(XMLString, "LVL"));
		this.experience = Integer.valueOf(GetXMLElement(XMLString, "XP"));

		maxLifePoints = Integer.valueOf(GetXMLElement(XMLString, "HP"));
		maxManaPoints = Integer.valueOf(GetXMLElement(XMLString, "MP"));

		attackPower = Integer.valueOf(GetXMLElement(XMLString, "ATK"));
		magicPower = Integer.valueOf(GetXMLElement(XMLString, "MAG"));

		armor = Integer.valueOf(GetXMLElement(XMLString, "ARM"));
		magicArmor = Integer.valueOf(GetXMLElement(XMLString, "MAR"));

		armorPenetration = Integer.valueOf(GetXMLElement(XMLString, "ARP"));
		magicPenetration = Integer.valueOf(GetXMLElement(XMLString, "MAP"));

		initiative = Integer.valueOf(GetXMLElement(XMLString, "I"));
		movement = Integer.valueOf(GetXMLElement(XMLString, "MOV"));

		jobs = new Job[jobList.values().length];

		String jobsSaves = GetXMLElement(XMLString, "Jobs");
		String actJob = GetXMLElement(XMLString, "AJ");
		int i = 0;
		for (jobList j : jobList.values()) {
			Job job = null;
			if (j.toString().equals("Warrior")) {
				job = new Warrior();
				job.getCharJobState(GetXMLElement(jobsSaves, j.toString()));
			}
			jobs[i] = job;
			if (job.getName().equals(actJob)) {
				actualJob = jobs[i];
			}
			i++;
		}
	}

	public String toXMLString() {
		String s = new String();
		s = "";

		s += "<LVL>";
		s += this.getLevel();
		s += "</LVL>";

		s += "<XP>";
		s += this.getExperience();
		s += "</XP>";

		s += "<RACE>";
		s += this.getRace();
		s += "</RACE>";

		s += "<HP>";
		s += this.getMaxLifePoints();
		s += "</HP>";

		s += "<MP>";
		s += this.getMaxManaPoints();
		s += "</MP>";

		s += "<ATK>";
		s += this.getAttackPower();
		s += "</ATK>";

		s += "<MAG>";
		s += this.getMagicPower();
		s += "</MAG>";

		s += "<ARM>";
		s += this.getArmor();
		s += "</ARM>";

		s += "<MAR>";
		s += this.getMagicArmor();
		s += "</MAR>";

		s += "<ARP>";
		s += this.getArmorPenetration();
		s += "</ARP>";

		s += "<MAP>";
		s += this.getMagicPenetration();
		s += "</MAP>";

		s += "<I>";
		s += this.getInitiative();
		s += "</I>";

		s += "<MOV>";
		s += this.getMovement();
		s += "</MOV>";

		s += "<AJ>";
		s += this.getActualJob().getName();
		s += "</AJ>";
		s += "\n";

		s += "<Jobs>";
		s += "\n";
		for (int i = 0; i < jobs.length; i++) {
			s += "<" + jobs[i].getName() + ">";
			s += jobs[i].saveToXML();
			s += "</" + jobs[i].getName() + ">";
			s += "\n";
		}
		s += "</Jobs>";
		return s;
	}

	private String GetXMLElement(String XMLString, String balise) {
		int len = ("<" + balise + ">").length();
		int posDeb = XMLString.indexOf("<" + balise + ">");
		int posFin = XMLString.indexOf("</" + balise + ">");

		return XMLString.substring(posDeb + len, posFin);
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getLifePoints() {
		return lifePoints;
	}

	public void setLifePoints(int lifePoints) {
		this.lifePoints = lifePoints;
	}

	public int getManaPoints() {
		return manaPoints;
	}

	public void setManaPoints(int manaPoints) {
		this.manaPoints = manaPoints;
	}

	public int getMaxLifePoints() {
		return maxLifePoints;
	}

	public void setMaxLifePoints(int maxLifePoints) {
		this.maxLifePoints = maxLifePoints;
	}

	public int getMaxManaPoints() {
		return maxManaPoints;
	}

	public void setMaxManaPoints(int maxManaPoints) {
		this.maxManaPoints = maxManaPoints;
	}

	public int getAttackPower() {
		return attackPower;
	}

	public void setAttackPower(int attackPower) {
		this.attackPower = attackPower;
	}

	public int getMagicPower() {
		return magicPower;
	}

	public void setMagicPower(int magicPower) {
		this.magicPower = magicPower;
	}

	public int getArmor() {
		return armor;
	}

	public void setArmor(int armor) {
		this.armor = armor;
	}

	public int getMagicArmor() {
		return magicArmor;
	}

	public void setMagicArmor(int magicArmor) {
		this.magicArmor = magicArmor;
	}

	public int getArmorPenetration() {
		return armorPenetration;
	}

	public void setArmorPenetration(int armorPenetration) {
		this.armorPenetration = armorPenetration;
	}

	public int getMagicPenetration() {
		return magicPenetration;
	}

	public void setMagicPenetration(int magicPenetration) {
		this.magicPenetration = magicPenetration;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public int getInitiative() {
		return initiative;
	}

	public void setInitiative(int initiative) {
		this.initiative = initiative;
	}

	public int getMovement() {
		return movement;
	}

	public void setMovement(int movement) {
		this.movement = movement;
	}

	public Job getActualJob() {
		return actualJob;
	}

	public void setActualJob(Job actualJob) {
		this.actualJob = actualJob;
	}

	public Race getRace() {
		return race;
	}

	public void setRace(Race race) {
		this.race = race;
	}

	public int getNumberOfAnimations() {
		return numberOfAnimations;
	}

	public void setNumberOfAnimations(int numberOfAnimations) {
		this.numberOfAnimations = numberOfAnimations;
	}

	public int[] getNumberOfSprites() {
		return numberOfSprites;
	}

	public void setNumberOfSprites(int[] numberOfSprites) {
		this.numberOfSprites = numberOfSprites;
	}

	public Texture[][] getSprites() {
		return sprites;
	}

	public void setSprites(Texture[][] sprites) {
		this.sprites = sprites;
	}

	public int getSpriteSpeed() {
		return spriteSpeed;
	}

	public void setSpriteSpeed(int spriteSpeed) {
		this.spriteSpeed = spriteSpeed;
	}

	public static void main(String[] argv) {
		Character c1 = new Character(Race.Human, new Warrior());
		String save = c1.toXMLString();
		System.out.println(save);
		Character c2 = new Character(save);
		String save2 = c2.toXMLString();
		System.out.println(save2);

	}

}
