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

	CharCaracRender caracRender1;
	private Texture HP;
	private Texture MP;

	public HUD(int height, int width) {
		this.height = height;
		this.width = width;
		caracRender1 = new CharCaracRender();
		caracRender1.setXdep(150f);
		caracRender1.setYdep(25f);
	}

	public void SetCurrentChar(Character c) {
		this.currentChar = c;
		this.caracRender1.setCurrentChar(c);
	}

	public void SetCurrentTarget(Character c) {
		this.currentTarget = c;
	}

	public void Init() {
		try {
			font = new UnicodeFont("content/font/old_london/OldLondon.ttf", 36,
					false, false);
			font.addAsciiGlyphs();
			font.getEffects().add(new ColorEffect());
			font.loadGlyphs();

			font2 = new UnicodeFont("content/font/old_london/OldLondon.ttf",
					24, false, false);
			font2.addAsciiGlyphs();
			font2.getEffects().add(new ColorEffect());
			font2.loadGlyphs();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		try {
			HP = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("content/textures/HUD/HP.png"));
			MP = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("content/textures/HUD/MP.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		caracRender1.Init();

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
			caracRender1.Render();
		}
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	public void RenderName() {
		Color.white.bind();
		font.drawString(25f, 125f, currentChar.getName(), Color.white);
	}

	public void RenderLvl() {
		Color.white.bind();
		font.drawString(25f, 150f, "lvl : " + currentChar.getLevel(),
				Color.white);
	}

	public void RenderJob() {
		Color.white.bind();
		font.drawString(25f, 175f, currentChar.getActualJob().getName(),
				Color.white);
	}

}
