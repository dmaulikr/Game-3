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
	private UnicodeFont font;
	private Texture ATK;
	private Texture MAG;
	private Texture ARP;
	private Texture MAP;
	private Texture ARM;
	private Texture MAR;
	private Texture I;
	private Texture M;
	private Texture HP;
	private Texture MP;

	Character currentChar;
	Character currentTarget;

	CharBarsRender barRender1;
	CharCaracRender caracRender1;
	CharDescRender descDesc1;

	CharBarsRender barRender2;
	CharCaracRender caracRender2;
	CharDescRender descDesc2;

	ContextMenu contextMenu;

	public HUD(int width, int height) {
		this.height = height;
		this.width = width;
		this.caracRender1 = new CharCaracRender();
		this.caracRender1.setXdep(150f);
		this.caracRender1.setYdep(25f);

		this.barRender1 = new CharBarsRender();
		this.barRender1.setXdep(25f);
		this.barRender1.setYdep(150f);

		this.descDesc1 = new CharDescRender();
		this.descDesc1.setXdep(25f);
		this.descDesc1.setYdep(25f);

		this.caracRender2 = new CharCaracRender();
		this.caracRender2.setXdep((width / 2) + 150f);
		this.caracRender2.setYdep(25f);

		this.barRender2 = new CharBarsRender();
		this.barRender2.setXdep((width / 2) + 25f);
		this.barRender2.setYdep(150f);

		this.descDesc2 = new CharDescRender();
		this.descDesc2.setXdep((width / 2) + 25f);
		this.descDesc2.setYdep(25f);

		this.contextMenu = new ContextMenu();
		this.contextMenu.setXdep((width / 2) - 100f);
		this.contextMenu.setYdep(25f);
	}

	public void SetCurrentChar(Character c) {
		this.currentChar = c;
		this.caracRender1.setCurrentChar(c);
		this.barRender1.setCurrentChar(c);
		this.descDesc1.SetCurrentChar(c);
	}

	public void SetCurrentTarget(Character c) {
		this.currentTarget = c;
		this.caracRender2.setCurrentChar(c);
		this.barRender2.setCurrentChar(c);
		this.descDesc2.SetCurrentChar(c);
		if (c != null) {
			if (c.equals(this.currentChar)) {
				this.currentTarget = null;
				this.caracRender2.setCurrentChar(null);
				this.barRender2.setCurrentChar(null);
				this.descDesc2.SetCurrentChar(null);
			}
		}

	}

	public void Init() {
		try {
			font = new UnicodeFont("content/font/old_london/OldLondon.ttf", 24, false, false);
			font.addAsciiGlyphs();
			font.getEffects().add(new ColorEffect());
			font.loadGlyphs();

		} catch (SlickException e) {
			e.printStackTrace();
		}
		try {
			HP = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("content/textures/HUD/HP.png"));
			MP = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("content/textures/HUD/MP.png"));
			ATK = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("content/textures/HUD/ATK.png"));
			MAG = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("content/textures/HUD/MAG.png"));
			ARM = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("content/textures/HUD/ARM.png"));
			MAR = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("content/textures/HUD/MAR.png"));
			ARP = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("content/textures/HUD/ARP.png"));
			MAP = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("content/textures/HUD/MAP.png"));
			I = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("content/textures/HUD/I.png"));
			M = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("content/textures/HUD/M.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.caracRender1.Init(ATK, MAG, ARM, MAR, ARP, MAP, I, M, font);
		this.barRender1.Init(HP, MP, font);
		this.descDesc1.Init(font);

		this.caracRender2.Init(ATK, MAG, ARM, MAR, ARP, MAP, I, M, font);
		this.barRender2.Init(HP, MP, font);
		this.descDesc2.Init(font);

		this.contextMenu.Init(font);
	}

	public void Render() {
		/*
		 * GL11.glDisable(GL11.GL_TEXTURE_2D); GL11.glColor4f(0.0f, 0.0f, 0.0f,
		 * 1f); GL11.glBegin(GL11.GL_QUADS); GL11.glVertex2f(0, 0);
		 * GL11.glVertex2f(width, 0); GL11.glVertex2f(width, 200);
		 * GL11.glVertex2f(0, 200); GL11.glEnd();
		 */

		if (currentChar != null) {

			caracRender1.Render();
			barRender1.Render();
			descDesc1.Render();
		}

		if (currentTarget != null) {

			caracRender2.Render();
			barRender2.Render();
			descDesc2.Render();
		}

		contextMenu.Render();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	public ContextMenu getContextMenu() {
		return contextMenu;
	}

}
