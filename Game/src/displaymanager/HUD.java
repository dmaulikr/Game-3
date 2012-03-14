package displaymanager;

import java.awt.Font;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import entity.TileTexture;

public class HUD {
	private int height;
	private int width;
	UnicodeFont font;

	public HUD(int height, int width) {
		this.height = height;
		this.width = width;

	}

	public void Init() {

		try {
			Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
			font = new UnicodeFont(awtFont, 24, true, false);
			font.addAsciiGlyphs();
			font.getEffects().add(new ColorEffect());
			font.loadGlyphs();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}

	public void Render() {
		Color.white.bind();
		GL11.glColor4f(.5f, .5f, .5f, 1f); // draw quad
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(0, 0);
		GL11.glVertex2f(width, 0);
		GL11.glVertex2f(width, 200);
		GL11.glVertex2f(0, 200);
		GL11.glEnd();

		Color.white.bind();
		font.drawString(100f, 100f, "yoyoyoyo sisi bien?", Color.yellow);
	}
}
