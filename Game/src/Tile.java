enum textureType {
	Grass, Earth, Sand, Stone
}

enum tileType {
	Walkable, Uncrossable, DifficultGround
}

enum decorationType {
	None, Flowers1, Flowers2, Flowers3, Tree1
}

public class Tile {

	private int posX;
	private int posY;
	private int height;
	private int heightToDraw;

	private textureType texture;
	private tileType type;
	private decorationType decoration;

	// CREATORS
	public Tile() {
		this.setPosX(0);
		this.setPosY(0);
		this.setHeight(0);
		this.setHeightToDraw(0);
		this.setTexture(textureType.Grass);
		this.setType(tileType.Walkable);
		this.setDecoration(decorationType.None);
	}

	public Tile(int posX, int posY, int height,int heightTotal, textureType texture,
			tileType type, decorationType decoration) {
		this.setPosX(posX);
		this.setPosY(posY);
		this.setHeight(height);
		this.setHeightToDraw(heightTotal);
		this.setTexture(texture);
		this.setType(type);
		this.setDecoration(decoration);
	}

	public Tile(String XMLString) {
		int posX;
		int posY;
		int height;
		int heightTotal;
		textureType texture;
		tileType type;
		decorationType decoration;

		posX = Integer.parseInt(GetXMLElement(XMLString, "PosX"));
		posY = Integer.parseInt(GetXMLElement(XMLString, "PosY"));
		height = Integer.parseInt(GetXMLElement(XMLString, "Height"));
		heightTotal = Integer.parseInt(GetXMLElement(XMLString, "HeightTotal"));
		texture = textureType.valueOf((GetXMLElement(XMLString, "Texture")));
		type = tileType.valueOf((GetXMLElement(XMLString, "Type")));
		decoration = decorationType.valueOf((GetXMLElement(XMLString,
				"Decoration")));

		this.setPosX(posX);
		this.setPosY(posY);
		this.setHeight(height);
		this.setHeightToDraw(heightTotal);
		this.setTexture(texture);
		this.setType(type);
		this.setDecoration(decoration);
	}

	private String GetXMLElement(String XMLString, String balise) {
		int len = ("<" + balise + ">").length();
		int posDeb = XMLString.indexOf("<" + balise + ">");
		int posFin = XMLString.indexOf("</" + balise + ">");

		return XMLString.substring(posDeb + len, posFin);
	}

	public String toXMLString() {
		String s = new String();
		s = "";

		s += "<PosX>";
		s += this.getPosX();
		s += "</PosX>";

		s += "<PosY>";
		s += this.getPosY();
		s += "</PosY>";

		s += "<Height>";
		s += this.getHeight();
		s += "</Height>";

		s += "<HeightTotal>";
		s += this.getHeightToDraw();
		s += "</HeightTotal>";

		s += "<Texture>";
		s += this.getTexture();
		s += "</Texture>";

		s += "<Type>";
		s += this.getType();
		s += "</Type>";

		s += "<Decoration>";
		s += this.getDecoration();
		s += "</Decoration>";

		return s;
	}

	// GET SET
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

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getHeightToDraw() {
		return heightToDraw;
	}

	public void setHeightToDraw(int heightTotal) {
		this.heightToDraw = heightTotal;
	}

	public textureType getTexture() {
		return texture;
	}

	public void setTexture(textureType texture) {
		this.texture = texture;
	}

	public tileType getType() {
		return type;
	}

	public void setType(tileType type) {
		this.type = type;
	}

	public decorationType getDecoration() {
		return decoration;
	}

	public void setDecoration(decorationType decoration) {
		this.decoration = decoration;
	}

	public static void main(String[] argv) {
		Tile t = new Tile(0, 5, 12, 12, textureType.Grass,
				tileType.DifficultGround, decorationType.Flowers3);
		String s = t.toXMLString();
		System.out.println(s);
		Tile t2 = new Tile(s);
		String s2 = t2.toXMLString();
		System.out.println(s2);
	}

}
