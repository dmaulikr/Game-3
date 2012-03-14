package displaymanager;

import java.awt.Font;
import java.io.IOException;
import entity.Character;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.omg.CORBA.portable.InputStream;

import entity.TileTexture;

public class HUD {
	private int height;
	private int width;
	UnicodeFont font;
	UnicodeFont font2;
	Character currentChar;
	Character currentTarget;
	private Texture ATK;
	private Texture MAG;
	private Texture ARP;
	private Texture MAP;
	private Texture ARM;
	private Texture MAR;
	private Texture HP;
	private Texture MP;

	public HUD(int height, int width) {
		this.height = height;
		this.width = width;
	}

	public void SetCurrentChar(Character c) {
		this.currentChar = c;
	}

	public void SetCurrentTarget(Character c) {
		this.currentTarget = c;
	}

	public void Init() {
		try {
			font = new UnicodeFont("content/font/old_london/OldLondon.ttf", 36, false, false);
			font.addAsciiGlyphs();
			font.getEffects().add(new ColorEffect());
			font.loadGlyphs();

			font2 = new UnicodeFont("content/font/old_london/OldLondon.ttf", 24, false, false);
			font2.addAsciiGlyphs();
			font2.getEffects().add(new ColorEffect());
			font2.loadGlyphs();
		} catch (SlickException e) {
			e.printStackTrace();
		}

		try {
			ATK = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("content/textures/HUD/ATK.png"));
			MAG = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("content/textures/HUD/MAG.png"));
			ARM = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("content/textures/HUD/ARM.png"));
			MAR = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("content/textures/HUD/MAR.png"));
			HP = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("content/textures/HUD/HP.png"));
			MP = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("content/textures/HUD/MP.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void Render() {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor4f(0.0f, 0.0f, 0.0f, 1f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(0, 0);
		GL11.glVertex2f(width, 0);
		GL11.glVertex2f(width, 200);
		GL11.glVertex2f(0, 200);
		GL11.glEnd();

		if (currentChar != null) {

			RenderName();
			RenderLvl();
			RenderJob();

			RenderATK();
			RenderMAG();
			RenderARM();
			RenderMAR();
		}
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	public void RenderName() {
		Color.white.bind();
		font.drawString(25f, 125f, currentChar.getName(), Color.white);
	}

	public void RenderLvl() {
		Color.white.bind();
		font.drawString(25f, 150f, "lvl : " + currentChar.getLevel(), Color.white);
	}

	public void RenderJob() {
		Color.white.bind();
		font.drawString(25f, 175f, currentChar.getActualJob().getName(), Color.white);
	}

	public void RenderATK() {
		RenderATKimg();
		Color.white.bind();
		font2.drawString(150f, 25f, String.valueOf(currentChar.getAttackPower()), Color.white);
	}

	public void RenderMAG() {
		RenderMAGimg();
		Color.white.bind();
		font2.drawString(200f, 25f, String.valueOf(currentChar.getMagicPower()), Color.white);
	}

	public void RenderARM() {
		RenderARMimg();
		Color.white.bind();
		font2.drawString(150f, 50f, String.valueOf(currentChar.getArmor()), Color.white);
	}

	public void RenderMAR() {
		RenderMARimg();
		Color.white.bind();
		font2.drawString(200f, 50f, String.valueOf(currentChar.getMagicArmor()), Color.white);
	}

	public void RenderATKimg() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glColor4f(1f, 1f, 1f, 1f);
		ATK.bind();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2d(0, 0);
		GL11.glVertex2f(125f, 25f);
		GL11.glTexCoord2d(1, 0);
		GL11.glVertex2f(150f, 25f);
		GL11.glTexCoord2d(1, 1);
		GL11.glVertex2f(150f, 50f);
		GL11.glTexCoord2d(0, 1);
		GL11.glVertex2f(125f, 50f);
		GL11.glEnd();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}

	public void RenderMAGimg() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glColor4f(1f, 1f, 1f, 1f);
		MAG.bind();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2d(0, 0);
		GL11.glVertex2f(175f, 25f);
		GL11.glTexCoord2d(1, 0);
		GL11.glVertex2f(200f, 25f);
		GL11.glTexCoord2d(1, 1);
		GL11.glVertex2f(200f, 50f);
		GL11.glTexCoord2d(0, 1);
		GL11.glVertex2f(175f, 50f);
		GL11.glEnd();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}

	public void RenderARMimg() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glColor4f(1f, 1f, 1f, 1f);
		ARM.bind();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2d(0, 0);
		GL11.glVertex2f(125f, 50f);
		GL11.glTexCoord2d(1, 0);
		GL11.glVertex2f(150f, 50f);
		GL11.glTexCoord2d(1, 1);
		GL11.glVertex2f(150f, 75f);
		GL11.glTexCoord2d(0, 1);
		GL11.glVertex2f(125f, 75f);
		GL11.glEnd();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}

	public void RenderMARimg() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glColor4f(1f, 1f, 1f, 1f);
		MAR.bind();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2d(0, 0);
		GL11.glVertex2f(175f, 50f);
		GL11.glTexCoord2d(1, 0);
		GL11.glVertex2f(200f, 50f);
		GL11.glTexCoord2d(1, 1);
		GL11.glVertex2f(200f, 75f);
		GL11.glTexCoord2d(0, 1);
		GL11.glVertex2f(175f, 75f);
		GL11.glEnd();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}

	public void RenderHPimg() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glColor4f(1f, 1f, 1f, 1f);
		HP.bind();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2d(0, 0);
		GL11.glVertex2f(25f, 25f);
		GL11.glTexCoord2d(1, 0);
		GL11.glVertex2f(50f, 25f);
		GL11.glTexCoord2d(1, 1);
		GL11.glVertex2f(50f, 50f);
		GL11.glTexCoord2d(0, 1);
		GL11.glVertex2f(25f, 50f);
		GL11.glEnd();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}

	public void RenderMPimg() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glColor4f(1f, 1f, 1f, 1f);
		MP.bind();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2d(0, 0);
		GL11.glVertex2f(25f, 25f);
		GL11.glTexCoord2d(1, 0);
		GL11.glVertex2f(50f, 25f);
		GL11.glTexCoord2d(1, 1);
		GL11.glVertex2f(50f, 50f);
		GL11.glTexCoord2d(0, 1);
		GL11.glVertex2f(25f, 50f);
		GL11.glEnd();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}

}
