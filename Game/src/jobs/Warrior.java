package jobs;

import entity.Character;
import entity.Job;
import entity.Skill;

public class Warrior extends Job {

	public Warrior() {
		setName("Warrior");
		setDescription("tapertapertaper");
		setExperiencePoint(0);
		createSkillTree();
	}
	
	public void createSkillTree() {
		ForceAmelio1 forceAmelio1 = new ForceAmelio1();
		ForceAmelio2 forceAmelio2 = new ForceAmelio2();
		ForceAmelio3 forceAmelio3 = new ForceAmelio3();
		ForceAmelio4 forceAmelio4 = new ForceAmelio4();

		forceAmelio2.setDependance(new boolean[] { forceAmelio1.isBought() });
		forceAmelio3.setDependance(new boolean[] { forceAmelio1.isBought(), forceAmelio2.isBought() });
		forceAmelio4.setDependance(new boolean[] { forceAmelio1.isBought(), forceAmelio2.isBought(), forceAmelio3.isBought() });

		jobSkills = new Skill[] { forceAmelio1, forceAmelio2, forceAmelio3, forceAmelio4 };
	}

	// Warrior Skills
	public class ForceAmelio1 extends Skill {

		public ForceAmelio1() {
			super("Force +1", "augmente la force de 1", 100, skillType.amelioration);
		}

		@Override
		public void process(Character character) {
			character.setAttackPower(character.getAttackPower() + 1);
		}
	}

	public class ForceAmelio2 extends Skill {
		public ForceAmelio2() {
			super("Force +2", "augmente la force de 2", 100, skillType.amelioration);
		}

		@Override
		public void process(Character character) {
			character.setAttackPower(character.getAttackPower() + 2);
		}
	}

	public class ForceAmelio3 extends Skill {
		public ForceAmelio3() {
			super("Force +5", "augmente la force de 5", 100, skillType.amelioration);
		}

		@Override
		public void process(Character character) {
			character.setAttackPower(character.getAttackPower() + 5);
		}
	}

	public class ForceAmelio4 extends Skill {
		public ForceAmelio4() {
			super("Force +12", "augmente la force de 12", 100, skillType.amelioration);
		}

		@Override
		public void process(Character character) {
			character.setAttackPower(character.getAttackPower() + 12);
		}
	}

	public class LifeAmelio1 extends Skill {
		public LifeAmelio1() {
			super("HP +30", "augmente la vie de 30", 100, skillType.amelioration);
		}

		@Override
		public void process(Character character) {
			character.setAttackPower(character.getMaxLifePoints() + 20);
		}
	}

	public class LifeAmelio2 extends Skill {
		public LifeAmelio2() {
			super("HP +70", "augmente la vie de 70", 100, skillType.amelioration);
		}

		@Override
		public void process(Character character) {
			character.setAttackPower(character.getMaxLifePoints() + 80);
		}
	}

	public class LifeAmelio3 extends Skill {
		public LifeAmelio3() {
			super("HP +200", "augmente la vie de 200", 100, skillType.amelioration);
		}

		@Override
		public void process(Character character) {
			character.setAttackPower(character.getMaxLifePoints() + 200);
		}
	}

	public class LifeAmelio4 extends Skill {
		public LifeAmelio4() {
			super("HP +300", "augmente la vie de 300", 100, skillType.amelioration);
		}

		@Override
		public void process(Character character) {
			character.setAttackPower(character.getMaxLifePoints() + 300);
		}
	}

	public class Dash extends Skill {
		public Dash() {
			super("Dash", "permet de Dash et de pousser son adversaire", 200, skillType.capacity);
		}

		@Override
		public void process(Character character) {
			// set Dash available a true
		}
	}

	public class CriticalStrike extends Skill {
		public CriticalStrike() {
			super("Critical Strike", "10% de chance de coup critique", 250, skillType.capacity);
		}

		@Override
		public void process(Character character) {
			// set CriticalStrike available a true
		}
	}
}