package entity;

import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import entity.Tile.textureType;

public class TileTexture {
	public String path = "content/textures/";

	public Texture GrassMain;
	public Texture GrassSE;
	public Texture GrassSO;
	public Texture GrassNO;
	public Texture GrassNE;

	public Texture StoneMain;
	public Texture StoneSE;
	public Texture StoneSO;
	public Texture StoneNO;
	public Texture StoneNE;

	public Texture SandMain;
	public Texture SandSE;
	public Texture SandSO;
	public Texture SandNO;
	public Texture SandNE;

	public Texture EarthMain;
	public Texture EarthSE;
	public Texture EarthSO;
	public Texture EarthNO;
	public Texture EarthNE;

	public TileTexture() {

	}

	public void LoadBundles(ArrayList<textureType> textures) {
		for (textureType textureType : textures) {
			LoadABundle(textureType);
		}
	}

	public void LoadABundle(textureType type) {
		String tempPath = path + type.toString() + "/";
		switch (type) {
		case Grass:
			try {
				GrassMain = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(tempPath + "main.png"));
				GrassSE = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(tempPath + "SE.png"));
				GrassSO = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(tempPath + "SO.png"));
				GrassNO = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(tempPath + "NO.png"));
				GrassNE = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(tempPath + "NE.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case Stone:
			try {
				StoneMain = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(tempPath + "main.png"));
				StoneSE = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(tempPath + "SE.png"));
				StoneSO = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(tempPath + "SO.png"));
				StoneNO = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(tempPath + "NO.png"));
				StoneNE = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(tempPath + "NE.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case Sand:
			try {
				SandMain = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(tempPath + "main.png"));
				SandSE = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(tempPath + "SE.png"));
				SandSO = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(tempPath + "SO.png"));
				SandNO = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(tempPath + "NO.png"));
				SandNE = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(tempPath + "NE.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case Earth:
			try {
				EarthMain = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(tempPath + "main.png"));
				EarthSE = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(tempPath + "SE.png"));
				EarthSO = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(tempPath + "SO.png"));
				EarthNO = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(tempPath + "NO.png"));
				EarthNE = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(tempPath + "NE.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}

	public Texture[] getBundle(textureType type) {
		Texture[] result = new Texture[5];
		switch (type) {
		case Grass:
			result[0] = GrassMain;
			result[1] = GrassSE;
			result[2] = GrassSO;
			result[3] = GrassNO;
			result[4] = GrassNE;

			break;
		case Stone:
			result[0] = StoneMain;
			result[1] = StoneSE;
			result[2] = StoneSO;
			result[3] = StoneNO;
			result[4] = StoneNE;

			break;
		case Sand:
			result[0] = SandMain;
			result[1] = SandSE;
			result[2] = SandSO;
			result[3] = SandNO;
			result[4] = SandNE;

			break;
		case Earth:
			result[0] = EarthMain;
			result[1] = EarthSE;
			result[2] = EarthSO;
			result[3] = EarthNO;
			result[4] = EarthNE;

			break;
		default:
			break;
		}
		return result;
	}
}
