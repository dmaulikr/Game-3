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

	CharBarsRender barRender1;
	CharCaracRender caracRender1;
	CharDescRender descDesc1;

	private Texture HP;
	private Texture MP;

	public HUD(int height, int width) {
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

	}

	public void SetCurrentChar(Character c) {
		this.currentChar = c;
		this.caracRender1.setCurrentChar(c);
		this.barRender1.setCurrentChar(c);
		this.descDesc1.SetCurrentChar(c);
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
			HP = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("content/textures/HUD/HP.png"));
			MP = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("content/textures/HUD/MP.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		this.caracRender1.Init();
		this.barRender1.Init();
		this.descDesc1.Init();
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

			caracRender1.Render();
			barRender1.Render();
			descDesc1.Render();
		}
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

}
