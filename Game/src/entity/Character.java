package entity;

import org.newdawn.slick.opengl.Texture;

import displaymanager.DisplayManager.viewPoint;

import jobs.Warrior;
import entity.Job.jobList;

public class Character {
	public enum Race {
		Human, Dwarf, Elve
	}

	public enum Gender {
		Male, Female
	}

	public enum CharState {
		Walking, Standing, Hitting, Casting, TakingDmg, Wounded, Dead
	}

	private Race race;
	private Gender gender;
	private Job actualJob;
	private Job[] jobs;

	private float posX;
	private float posY;
	private float posZ;

	private float speed;

	private int currentTileX;
	private int currentTileY;

	private int tileToGoX;
	private int tileToGoY;

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

	private CharState state;
	private CharState lastState;

	private Animation currentAnimation;

	private CharAnimationBible animationBible;

	private int spriteSpeed;

	public Character(Race race, Gender gender) {
		this.setRace(race);
		this.setGender(gender);
		this.level = 1;
		this.experience = 0;
		this.currentAnimation = null;
		this.setState(CharState.Standing);
		lastState = this.getState();
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
		this.setGender(Gender.valueOf(GetXMLElement(XMLString, "GEN")));
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

		this.currentAnimation = null;
		this.setState(CharState.Standing);
		lastState = this.getState();
	}

	public void Update(float GameTimeLapse, Map map) {
		this.currentAnimation.Update(GameTimeLapse);
		if (tileToGoX != currentTileX || tileToGoY != currentTileY) {
			int ecartX = 0;
			int ecartY = 0;
			boolean Xpos = false;
			boolean Ypos = false;

			if (tileToGoX > currentTileX) {
				ecartX = tileToGoX - currentTileX;
				Xpos = true;
			}
			if (tileToGoX < currentTileX) {
				ecartX = currentTileX - tileToGoX;
				Xpos = false;
			}

			if (tileToGoY > currentTileY) {
				ecartY = tileToGoY - currentTileY;
				Ypos = true;
			}
			if (tileToGoY < currentTileY) {
				ecartY = currentTileY - tileToGoY;
				Ypos = false;
			}

			if (ecartX > ecartY) {
				if (Xpos) {
					if ((posX + speed * GameTimeLapse) < currentTileX + 1) {
						posX += speed * GameTimeLapse;
					} else if ((posX + speed * GameTimeLapse) >= currentTileX + 1) {
						posX = currentTileX + 1;
					} else if (posX == currentTileX + 1) {
						currentTileX++;
					}

					if (posX > currentTileX + 0.5f && posX < currentTileX + 1) {
						posZ = fdex1(posX, (map.getTile(currentTileX + 1, currentTileY).getHeight()), (map.getTile(currentTileX, currentTileY).getHeight()));
					} else if (posX < currentTileX + 0.25f) {
						posZ = fdex2(posX, (map.getTile(currentTileX, currentTileY).getHeight()));
					}
				} else {
					if ((posX - speed * GameTimeLapse) < currentTileX - 1) {
						posX -= speed * GameTimeLapse;
					} else if ((posX + speed * GameTimeLapse) >= currentTileX - 1) {
						posX = currentTileX - 1;
					} else if (posX == currentTileX - 1) {
						currentTileX--;
					}

					if (posX < currentTileX + 0.5f && posX > currentTileX - 1) {
						posZ = fdex1(posX, (map.getTile(currentTileX - 1, currentTileY).getHeight()), (map.getTile(currentTileX, currentTileY).getHeight()));
					} else if (posX > currentTileX + 0.75f) {
						posZ = fdex2(posX, (map.getTile(currentTileX, currentTileY).getHeight()));
					}
				}
			} else {

				if (Ypos) {
					if ((posY + speed * GameTimeLapse) < currentTileY + 1) {
						posY += speed * GameTimeLapse;
					} else if ((posY + speed * GameTimeLapse) >= currentTileY + 1) {
						posY = currentTileY + 1;
					}

					if (posY == currentTileY + 1) {
						currentTileY++;
					}

					if (posY > currentTileY + 0.5f && posY < currentTileY + 1) {
						posZ = fdex1(posY, (map.getTile(currentTileX, currentTileY + 1).getHeight()), (map.getTile(currentTileX, currentTileY).getHeight()));
					} else if (posX < currentTileY + 0.25f) {
						posZ = fdex2(posY, (map.getTile(currentTileX, currentTileY).getHeight()));
					}
				} else {
					if ((posY - speed * GameTimeLapse) > currentTileY - 1) {
						posY -= speed * GameTimeLapse;
					} else if ((posY - speed * GameTimeLapse) <= currentTileY - 1) {
						posY = currentTileY - 1;
					}

					if (posY == currentTileY - 1) {
						currentTileY--;
					}

					if (posY < currentTileY + 0.5f && posY > currentTileY - 1) {
						posZ = fdex1(posY, (map.getTile(currentTileX, currentTileY - 1).getHeight()), (map.getTile(currentTileX, currentTileY).getHeight()));
					} else if (posX < currentTileY + 0.75f) {
						posZ = fdex2(posY, (map.getTile(currentTileX, currentTileY).getHeight()));
					}
				}
			}
		}
	}

	private float fdex1(float x, float z2, float z0) {
		return (((z2 + 0.25f) / 0.5f) * x) + z0;
	}

	private float fdex2(float x, float z0) {
		return ((z0 + 0.25f) - 0.25f * x);
	}

	public void linkAnimationBible(CharAnimationBible bible) {
		this.animationBible = bible;
	}

	public void SetCurrentAnimationViewPoint(viewPoint vp) {
		this.currentAnimation = animationBible.getAnimation(vp, jobList.valueOf(actualJob.getName()), this.race, this.gender, this.state);
	}

	public Texture getTexture() {
		return this.currentAnimation.getTexture();
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

		s += "<GEN>";
		s += this.getGender();
		s += "</GEN>";

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

	public float getPosX() {
		return posX;
	}

	public void setPosX(float posX) {
		this.posX = posX;
	}

	public float getPosY() {
		return posY;
	}

	public void setPosY(float posY) {
		this.posY = posY;
	}

	public float getPosZ() {
		return posZ;
	}

	public void setPosZ(float posZ) {
		this.posZ = posZ;
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

	public void setActualJob(jobList actualJob) {
		for (Job j : jobs) {
			if (j.getName() == actualJob.toString()) {
				this.actualJob = j;
			}
		}
	}

	public Race getRace() {
		return race;
	}

	public void setRace(Race race) {
		this.race = race;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public int getSpriteSpeed() {
		return spriteSpeed;
	}

	public void setSpriteSpeed(int spriteSpeed) {
		this.spriteSpeed = spriteSpeed;
	}

	public CharState getState() {
		return state;
	}

	public void setState(CharState state) {
		this.state = state;
	}

	public int getCurrentTileX() {
		return currentTileX;
	}

	public void setCurrentTileX(int currentTileX) {
		this.currentTileX = currentTileX;
		this.posX = (float) currentTileX;
	}

	public int getCurrentTileY() {
		return currentTileY;
	}

	public void setCurrentTileY(int currentTileY) {
		this.currentTileY = currentTileY;
		this.posY = (float) currentTileY;
	}

	public int getTileToGoX() {
		return tileToGoX;
	}

	public void setTileToGoX(int tileToGoX) {
		this.tileToGoX = tileToGoX;
	}

	public int getTileToGoY() {
		return tileToGoY;
	}

	public void setTileToGoY(int tileToGoY) {
		this.tileToGoY = tileToGoY;
	}

	public void setHeight(float z) {
		this.posZ = z;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public static void main(String[] argv) {
		Character c1 = new Character(Race.Human, Gender.Female);
		String save = c1.toXMLString();
		System.out.println(save);
		Character c2 = new Character(save);
		String save2 = c2.toXMLString();
		System.out.println(save2);

	}

}
