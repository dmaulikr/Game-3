enum textureType{
	Grass,
	Sand,
	Stone,
	Gravel,
	Dust
}

enum tileType{
	Walkable,
	Uncrossable,
	DifficultGround
}

enum decorationType{
	None,
	Flowers1,
	Flowers2,
	Flowers3,
	Tree1
}

public class Tile {

	private int posX;
	private int posY;
	
	private int heightSE;
	private int heightNE;
	private int heightNO;
	private int heightSO;
	
	private textureType texture;
	private tileType type;
	private decorationType decoration;
	
	//CREATORS
	public Tile()
	{
		this.setPosX(0);
		this.setPosY(0);
		this.setHeightSE(0);
		this.setHeightNE(0);
		this.setHeightNO(0);
		this.setHeightSO(0);		
		this.setTexture(textureType.Grass);
		this.setType(tileType.Walkable);
		this.setDecoration(decorationType.None);
	}
	
	public Tile(int posX,int posY,
			int heightSE, int heightNE, int heightNO, int heightSO,
			textureType texture, tileType type,decorationType decoration)
	{
		this.setPosX(posX);
		this.setPosY(posY);
		this.setHeightSE(heightSE);
		this.setHeightNE(heightNE);
		this.setHeightNO(heightNO);
		this.setHeightSO(heightSO);		
		this.setTexture(texture);
		this.setType(type);
		this.setDecoration(decoration);
	}
	
	public Tile(String XMLString)
	{
		int posX;
		int posY;
		int heightSE;
		int heightNE;
		int heightNO;
		int heightSO;
		textureType texture;
		tileType type;
		decorationType decoration;
		
		posX=Integer.parseInt(GetXMLElement(XMLString,"PosX"));
		posY=Integer.parseInt(GetXMLElement(XMLString,"PosY"));
		heightSE=Integer.parseInt(GetXMLElement(XMLString,"HeightSE"));
		heightNE=Integer.parseInt(GetXMLElement(XMLString,"HeightNE"));
		heightNO=Integer.parseInt(GetXMLElement(XMLString,"HeightNO"));
		heightSO=Integer.parseInt(GetXMLElement(XMLString,"HeightSO"));
		texture=textureType.valueOf((GetXMLElement(XMLString,"Texture")));
		type=tileType.valueOf((GetXMLElement(XMLString,"Type")));
		decoration=decorationType.valueOf((GetXMLElement(XMLString,"Decoration")));
		
		this.setPosX(posX);
		this.setPosY(posY);
		this.setHeightSE(heightSE);
		this.setHeightNE(heightNE);
		this.setHeightNO(heightNO);
		this.setHeightSO(heightSO);		
		this.setTexture(texture);
		this.setType(type);
		this.setDecoration(decoration);	
	}
	
	private String GetXMLElement(String XMLString,String balise)
	{		
		int len=("<"+balise+">").length();
		int posDeb=XMLString.indexOf("<"+balise+">");
		int posFin=XMLString.indexOf("</"+balise+">");
		
		return XMLString.substring(posDeb+len,posFin);
	}
	
	public String toXMLString()
	{
		String s=new String();
		s="";

		s+="<PosX>";
		s+=this.getPosX();
		s+="</PosX>";
		
		s+="<PosY>";
		s+=this.getPosY();
		s+="</PosY>";
		
		s+="<HeightSE>";
		s+=this.getHeightSE();
		s+="</HeightSE>";
		
		s+="<HeightNE>";
		s+=this.getHeightNE();
		s+="</HeightNE>";
		
		s+="<HeightNO>";
		s+=this.getHeightNO();
		s+="</HeightNO>";
		
		s+="<HeightSO>";
		s+=this.getHeightSO();
		s+="</HeightSO>";
		
		s+="<Texture>";
		s+=this.getTexture();
		s+="</Texture>";
		
		s+="<Type>";
		s+=this.getType();
		s+="</Type>";
		
		s+="<Decoration>";
		s+=this.getDecoration();
		s+="</Decoration>";
		
		return s;
	}

	//GET SET	
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

	public int getHeightSE() {
		return heightSE;
	}

	public void setHeightSE(int heightSE) {
		this.heightSE = heightSE;
	}

	public int getHeightNE() {
		return heightNE;
	}

	public void setHeightNE(int heightNE) {
		this.heightNE = heightNE;
	}

	public int getHeightNO() {
		return heightNO;
	}

	public void setHeightNO(int heightNO) {
		this.heightNO = heightNO;
	}

	public int getHeightSO() {
		return heightSO;
	}

	public void setHeightSO(int heightSO) {
		this.heightSO = heightSO;
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
		Tile t= new Tile(0, 5, 12,12,12,12, textureType.Grass, tileType.DifficultGround,decorationType.Flowers3);
		String s=t.toXMLString();
		System.out.println(s);
		Tile t2=new Tile(s);
		String s2=t2.toXMLString();
		System.out.println(s2);
	}
	
	
}
