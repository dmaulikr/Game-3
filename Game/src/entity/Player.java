package entity;

public class Player {
	private Character[] chars;
	private String name;

	public Player(String name, Character[] chars) {
		this.setName(name);
		this.setChars(chars);
	}

	public Character[] getChars() {
		return chars;
	}

	public void setChars(Character[] chars) {
		this.chars = chars;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
