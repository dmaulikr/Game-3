package entity;

import java.util.ArrayList;

import entity.Tile.textureType;

public class Map {

	private Tile[][] map;
	private int length;
	private int width;
	private String name;

	public Map(int length, int width, String name) {
		this.setLength(length);
		this.setWidth(width);
		this.setName(name);

		map = new Tile[length][width];
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				map[i][j] = new Tile();
				map[i][j].setPosX(i);
				map[i][j].setPosY(j);
			}
		}
	}

	public Map(String XMLString) {
		int length;
		int width;
		String name;

		length = Integer.parseInt(GetXMLElement(XMLString, "L"));
		width = Integer.parseInt(GetXMLElement(XMLString, "W"));
		name = GetXMLElement(XMLString, "N");

		this.setLength(length);
		this.setWidth(width);
		this.setName(name);

		map = new Tile[length][width];

		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				map[i][j] = new Tile(GetXMLElement(XMLString, "Tile" + i + "/" + j));
			}
		}
	}

	public void BindTextures(TileTexture tileTexture) {
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				map[i][j].BindTextures(tileTexture);
			}
		}
	}

	public void CleanLightUpZones() {
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				getTile(i, j).setHighlightedGreen(false);
			}
		}
	}

	public void LightUpStartZone(int playerNum) {
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				if (getTile(i, j).getDeploymentZone() == playerNum) {
					getTile(i, j).setHighlightedGreen(true);
				} else {
					getTile(i, j).setHighlightedGreen(false);
				}
			}
		}
	}

	public void LightUpPossibleMovement(int X, int Y, int movement) {

		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				int zEcart;
				if (getTile(i, j).getHeight() > getTile(X, Y).getHeight()) {
					zEcart = getTile(i, j).getHeight() - getTile(X, Y).getHeight();
				} else {
					zEcart = getTile(X, Y).getHeight() - getTile(i, j).getHeight();
				}
				if (i >= (X - movement) && i <= (X + movement)) {
					if (i < X) {
						if (j > ((Y - movement) + (X - i) + zEcart) && j < ((Y + movement) - (X - i) - zEcart)) {
							getTile(i, j).setHighlightedGreen(true);
						} else {
							getTile(i, j).setHighlightedGreen(false);
						}
					} else if (i > X) {
						if (j > ((Y - movement) + (i - X) + zEcart) && j < ((Y + movement) - (i - X) - zEcart)) {
							getTile(i, j).setHighlightedGreen(true);
						} else {
							getTile(i, j).setHighlightedGreen(false);
						}
					} else {
						if (j >= (Y - movement)+ zEcart && j <= (Y + movement)- zEcart) {
							getTile(i, j).setHighlightedGreen(true);
						} else {
							getTile(i, j).setHighlightedGreen(false);
						}
					}

				}
			}
		}

	}

	public ArrayList<textureType> getAllTextureTypes() {
		ArrayList<textureType> result = new ArrayList<textureType>();
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				if (result.contains(map[i][j].getTexture())) {
					// do nothing
				} else {
					result.add(map[i][j].getTexture());
				}
			}
		}
		return result;
	}

	public void EditTile(int i, int j, Tile tile) {
		map[i][j].setPosX(tile.getPosX());
		map[i][j].setPosY(tile.getPosY());
		map[i][j].setHeight(tile.getHeight());
		map[i][j].setTexture(tile.getTexture());
		map[i][j].setType(tile.getType());
		map[i][j].setDecoration(tile.getDecoration());
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
		s += "<Map>";
		s += "\n";
		s += "<L>";
		s += this.getLength();
		s += "</L>";

		s += "<W>";
		s += this.getWidth();
		s += "</W>";

		s += "<N>";
		s += this.getName();
		s += "</N>";
		s += "\n";
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				s += "<Tile" + i + "/" + j + ">";
				s += map[i][j].toXMLString();
				s += "</Tile" + i + "/" + j + ">";
				s += "\n";
			}
		}

		s += "</Map>";

		return s;
	}

	public Tile getTile(int i, int j) {
		return map[i][j];
	}

	public int getLength() {
		return length;
	}

	private void setLength(int length) {
		this.length = length;
	}

	public int getWidth() {
		return width;
	}

	private void setWidth(int width) {
		this.width = width;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static void main(String[] argv) {
		Map m = new Map(2, 5, "blabla");
		String s = m.toXMLString();
		System.out.println(s);
		Map m2 = new Map(s);
		String s2 = m2.toXMLString();
		System.out.println(s2);
	}

}
