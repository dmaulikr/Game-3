package entity;

import org.newdawn.slick.opengl.Texture;

public class Tile {

	public enum textureType {
		Grass, Earth, Sand, Stone, none
	}

	public enum tileType {
		Walkable, Uncrossable, DifficultGround
	}

	public enum decorationType {
		None, Flowers1, Flowers2, Flowers3, Tree1
	}

	private int posX;
	private int posY;
	private int height;
	private int heightToDraw;

	private boolean isHighlighted;

	private textureType texture;
	private tileType type;
	private decorationType decoration;

	private Texture textureTop;
	private Texture textureSO;
	private Texture textureNO;
	private Texture textureNE;
	private Texture textureSE;

	// CREATORS
	public Tile() {
		this.setPosX(0);
		this.setPosY(0);
		this.setHeight(10);
		this.setHeightToDraw(0);
		this.setTexture(textureType.Grass);
		this.setType(tileType.Walkable);
		this.setDecoration(decorationType.None);
		this.setHighlighted(false);
	}

	public Tile(int posX, int posY, int height) {
		this.setPosX(posX);
		this.setPosY(posY);
		this.setHeight(height);
		this.setHeightToDraw(0);
		this.setTexture(textureType.Grass);
		this.setType(tileType.Walkable);
		this.setDecoration(decorationType.None);
		this.setHighlighted(false);
	}

	public Tile(int posX, int posY, int height, int heightTotal, textureType texture, tileType type, decorationType decoration) {
		this.setPosX(posX);
		this.setPosY(posY);
		this.setHeight(height);
		this.setHeightToDraw(heightTotal);
		this.setTexture(texture);
		this.setType(type);
		this.setDecoration(decoration);
		this.setHighlighted(false);
	}

	public Tile(String XMLString) {
		int posX;
		int posY;
		int height;
		int heightTotal;
		textureType texture;
		tileType type;
		decorationType decoration;

		posX = Integer.parseInt(GetXMLElement(XMLString, "X"));
		posY = Integer.parseInt(GetXMLElement(XMLString, "Y"));
		height = Integer.parseInt(GetXMLElement(XMLString, "Z"));
		heightTotal = Integer.parseInt(GetXMLElement(XMLString, "Z2"));
		texture = textureType.valueOf((GetXMLElement(XMLString, "Tx")));
		type = tileType.valueOf((GetXMLElement(XMLString, "Ty")));
		decoration = decorationType.valueOf((GetXMLElement(XMLString, "Dc")));

		this.setPosX(posX);
		this.setPosY(posY);
		this.setHeight(height);
		this.setHeightToDraw(heightTotal);
		this.setTexture(texture);
		this.setType(type);
		this.setDecoration(decoration);
		this.setHighlighted(false);
	}

	public void BindTextures(TileTexture tileTexture) {
		Texture[] textures = tileTexture.getBundle(this.getTexture());
		this.setTextureTop(textures[0]);
		this.setTextureSE(textures[1]);
		this.setTextureSO(textures[2]);
		this.setTextureNO(textures[3]);
		this.setTextureNE(textures[4]);
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

		s += "<X>";
		s += this.getPosX();
		s += "</X>";

		s += "<Y>";
		s += this.getPosY();
		s += "</Y>";

		s += "<Z>";
		s += this.getHeight();
		s += "</Z>";

		s += "<Z2>";
		s += this.getHeightToDraw();
		s += "</Z2>";

		s += "<Tx>";
		s += this.getTexture();
		s += "</Tx>";

		s += "<Ty>";
		s += this.getType();
		s += "</Ty>";

		s += "<Dc>";
		s += this.getDecoration();
		s += "</Dc>";

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

	public boolean isHighlighted() {
		return isHighlighted;
	}

	public void setHighlighted(boolean isHighlighted) {
		this.isHighlighted = isHighlighted;
	}

	public Texture getTextureTop() {
		return textureTop;
	}

	public void setTextureTop(Texture textureTop) {
		this.textureTop = textureTop;
	}

	public Texture getTextureSO() {
		return textureSO;
	}

	public void setTextureSO(Texture textureSO) {
		this.textureSO = textureSO;
	}

	public Texture getTextureNO() {
		return textureNO;
	}

	public void setTextureNO(Texture textureNO) {
		this.textureNO = textureNO;
	}

	public Texture getTextureNE() {
		return textureNE;
	}

	public void setTextureNE(Texture textureNE) {
		this.textureNE = textureNE;
	}

	public Texture getTextureSE() {
		return textureSE;
	}

	public void setTextureSE(Texture textureSE) {
		this.textureSE = textureSE;
	}

	public static void main(String[] argv) {
		Tile t = new Tile(0, 5, 12, 12, textureType.Grass, tileType.DifficultGround, decorationType.Flowers3);
		String s = t.toXMLString();
		System.out.println(s);
		Tile t2 = new Tile(s);
		String s2 = t2.toXMLString();
		System.out.println(s2);
	}
}
