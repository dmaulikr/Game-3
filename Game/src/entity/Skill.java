package entity;

public class Skill {

	public enum skillType {
		amelioration, capacity
	}

	private String name;
	private String desc;
	private boolean isActive; // can be bougth, unlocked.
	private boolean isBought; // is bought, must be applied.
	private int expPrice;
	private skillType type;
	private Skill child;

	public Skill(String name, String desc, int expPrice, boolean bought, skillType type) {
		setName(name);
		setDesc(desc);
		setExpPrice(expPrice);
		setActive(true);
		setBought(bought);
		setType(type);
		setChild(null);
	}

	public Skill(String name, String desc, int expPrice, boolean bought, boolean active, skillType type) {
		setName(name);
		setDesc(desc);
		setExpPrice(expPrice);
		setActive(active);
		setBought(bought);
		setType(type);
		setChild(null);
	}

	public Skill(String name, String desc, int expPrice, boolean bought, skillType type, Skill child) {
		setName(name);
		setDesc(desc);
		setExpPrice(expPrice);
		setActive(true);
		setBought(bought);
		setType(type);
		setChild(child);
	}

	public Skill(String name, String desc, int expPrice, boolean bought, boolean active, skillType type, Skill child) {
		setName(name);
		setDesc(desc);
		setExpPrice(expPrice);
		setActive(active);
		setBought(bought);
		setType(type);
		setChild(child);
	}

	public Skill(String XMLString) {
		String XMLname = GetXMLElement(XMLString, "Name");
		String XMLdesc = GetXMLElement(XMLString, "Desc");
		boolean XMLactive;
		boolean XMLbought;
		
		if (GetXMLElement(XMLString, "Active").equals("true")) {
			XMLactive = true;
		} else {
			XMLactive = false;
		}
		if (GetXMLElement(XMLString, "Bought").equals("true")) {
			XMLbought = true;
		} else {
			XMLbought = false;
		}
		
		int XMLexpPrice = Integer.parseInt(GetXMLElement(XMLString, "Price"));
		skillType XMLtype = skillType.valueOf(GetXMLElement(XMLString, "Type"));
		
		Skill XMLchild;
		if (!GetXMLElement(XMLString, "Child").equals("")) {
			XMLchild = new Skill(GetXMLElement(XMLString, "Child"));
		} else {
			XMLchild = null;
		}

		setName(XMLname);
		setDesc(XMLdesc);
		setExpPrice(XMLexpPrice);
		setActive(XMLactive);
		setBought(XMLbought);
		setType(XMLtype);
		setChild(XMLchild);		
	}

	public String toXMLString() {
		String s = new String();
		s = "";

		s += "<Name>";
		s += getName();
		s += "</Name>";

		s += "<Desc>";
		s += getDesc();
		s += "</Desc>";

		s += "<Active>";
		s += isActive();
		s += "</Active>";

		s += "<Bought>";
		s += isBought();
		s += "</Bought>";

		s += "<Price>";
		s += getExpPrice();
		s += "</Price>";

		s += "<Type>";
		s += getType();
		s += "</Type>";

		s += "<Child>";
		if (child != null) {
			s += getChild().toXMLString();
		}
		s += "</Child>";
		return s;
	}

	private String GetXMLElement(String XMLString, String balise) {
		int len = ("<" + balise + ">").length();
		int posDeb = XMLString.indexOf("<" + balise + ">");
		int posFin = XMLString.indexOf("</" + balise + ">");

		return XMLString.substring(posDeb + len, posFin);
	}

	public void process(Character character) {
		// set here the code !!
		// if skill, activate the right boolean in the char
		// if amelioration, upgrade the char stats.
	}

	public int BuySkill(int expPoint) {
		if (!isActive()) {
			if (expPrice <= expPoint) {
				expPoint -= expPoint;
				setActive(true);
				// Action OK
				return 0;
			} else {
				// YOU'VE NOT ENOUGH MINERALS
				return 1;
			}
		}
		// Already bougth
		return 2;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isBought() {
		return isBought;
	}

	public void setBought(boolean isBought) {
		this.isBought = isBought;
	}

	public int getExpPrice() {
		return expPrice;
	}

	public void setExpPrice(int expPrice) {
		this.expPrice = expPrice;
	}

	public skillType getType() {
		return type;
	}

	public void setType(skillType type) {
		this.type = type;
	}

	public Skill getChild() {
		return child;
	}

	public void setChild(Skill child) {
		this.child = child;
	}
}
