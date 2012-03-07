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
	private boolean[] dependance;

	
	public Skill(String name, String desc, int expPrice, skillType type) {
		setName(name);
		setDesc(desc);
		setExpPrice(expPrice);
		setActive(false);
		setBought(false);
		setType(type);
		setDependance(new boolean[0]);
		RefreshDependencies();
	}

	public void process(Character character) {
		// set here the code !!
		// if skill, activate the right boolean in the char
		// if amelioration, upgrade the char stats.
	}

	public int BuySkill(int expPoint) {
		if (isActive()) {
			// Skill is visible in the tree
			if (!isBought()) {
				if (expPrice <= expPoint) {
					expPoint -= expPoint;
					setActive(true);
					// Action OK
					return 0;
				} else {
					// YOU'VE NOT ENOUGH MINERALS
					return 1;
				}
			}else{
				return 2;
			// Already bougth
			}
		}		
		return 3;
	}
	
	public void RefreshDependencies(){
		boolean result=true;
		for (int i = 0; i < dependance.length; i++) {
			if(dependance[i]==false){
				result=false;
			}
		}
		setActive(result);
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

	public boolean[] getDependance() {
		return dependance;
	}

	public void setDependance(boolean[] dependance) {
		this.dependance = dependance;
		RefreshDependencies();
	}

/*
 DEPRECATED

	public Skill(String name, String desc, int expPrice, skillType type, boolean[] dependance) {
		setName(name);
		setDesc(desc);
		setExpPrice(expPrice);
		setActive(false);
		setBought(false);
		setType(type);
		setDependance(dependance);
		RefreshDependencies();
	}	
 
	public Skill(String XMLString) {
		String XMLname = GetXMLElement(XMLString, "Name");
		String XMLdesc = GetXMLElement(XMLString, "Desc");

		int XMLexpPrice = Integer.parseInt(GetXMLElement(XMLString, "Price"));
		skillType XMLtype = skillType.valueOf(GetXMLElement(XMLString, "Type"));

		int XMLnbDep = Integer.parseInt(GetXMLElement(XMLString, "DependanceNb"));
		boolean[] XMLdep = new boolean[XMLnbDep];
		for (int i = 0; i < XMLnbDep; i++) {
			XMLdep[i] = false;
		}

		setName(XMLname);
		setDesc(XMLdesc);
		setExpPrice(XMLexpPrice);
		setActive(false);
		setBought(false);
		setType(XMLtype);
		setDependance(XMLdep);
		RefreshDependencies();
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

		s += "<Price>";
		s += getExpPrice();
		s += "</Price>";

		s += "<Type>";
		s += getType();
		s += "</Type>";

		s += "<DependanceNb>";
		s += getDependance().length;
		s += "</DependanceNb>";		

		return s;
	}

	private String GetXMLElement(String XMLString, String balise) {
		int len = ("<" + balise + ">").length();
		int posDeb = XMLString.indexOf("<" + balise + ">");
		int posFin = XMLString.indexOf("</" + balise + ">");

		return XMLString.substring(posDeb + len, posFin);
	}
	
	public static void main(String[] argv) {
		Skill s1= new Skill("taper", "taper tel le bonhomme", 100, skillType.capacity);
		String test=s1.toXMLString();
		System.out.println(test);
		System.out.println(s1.isActive());
		Skill s2= new Skill(test);
		test=s1.toXMLString();
		System.out.println(test);
		System.out.println(s2.isActive());
		boolean[] dep= new boolean[1] ;
		dep[0]=s2.isBought;
		Skill s3= new Skill("taper2", "taper tel le bonhomme mais plus fort", 100, skillType.capacity,dep);
		test=s3.toXMLString();
		System.out.println(test);
		System.out.println(s3.isActive());
	}	
*/
}
