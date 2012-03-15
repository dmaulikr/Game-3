package displaymanager;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.opengl.Texture;

public class BarDrawer {

	private float Xdep;
	private float Ydep;
	private Texture icon;
	private float colorR;
	private float colorG;
	private float colorB;

	public BarDrawer(float colorR, float colorG, float colorB) {
		this.Xdep = 0;
		this.Ydep = 0;
		this.colorR = colorR;
		this.colorG = colorG;
		this.colorB = colorB;
		this.icon = null;
	}

	public void Init() {

	}

	public void Render(float prct) {
		DrawIcon(Xdep, Ydep);
		if (prct == 100f) {
			DrawBar(Xdep + 25f, Ydep, prct);
		} else {
			DrawBackgroundBar(Xdep + 25f, Ydep, prct);
			DrawBar(Xdep + 25f, Ydep, prct);

		}
	}

	private void DrawBackgroundBar(float X, float Y, float currentValue) {
		GL11.glColor4f(colorR - (colorR / 2f), colorG - (colorG / 2f), colorB - (colorB / 2f), 1f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(X + currentValue * 2f, Y);
		GL11.glVertex2f(X + 200f, Y);
		GL11.glVertex2f(X + 200f, Y + 25f);
		GL11.glVertex2f(X + currentValue * 2f, Y + 25f);
		GL11.glEnd();
	}

	private void DrawBar(float X, float Y, float currentValue) {
		GL11.glColor4f(colorR, colorG, colorB, 1f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(X, Y);
		GL11.glVertex2f(X + currentValue * 2f, Y);
		GL11.glVertex2f(X + currentValue * 2f, Y + 25f);
		GL11.glVertex2f(X, Y + 25f);
		GL11.glEnd();
	}

	private void DrawIcon(float X, float Y) {
		if (icon != null) {
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glColor4f(1f, 1f, 1f, 1f);
			icon.bind();
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2d(0, 0);
			GL11.glVertex2f(X, Y);
			GL11.glTexCoord2d(1, 0);
			GL11.glVertex2f(X + 25f, Y);
			GL11.glTexCoord2d(1, 1);
			GL11.glVertex2f(X + 25f, Y + 25f);
			GL11.glTexCoord2d(0, 1);
			GL11.glVertex2f(X, Y + 25f);
			GL11.glEnd();
			GL11.glDisable(GL11.GL_TEXTURE_2D);
		}
	}

	public void setXdep(float xdep) {
		this.Xdep = xdep;
	}

	public void setYdep(float ydep) {
		this.Ydep = ydep;
	}

	public void setIcon(Texture icon) {
		this.icon = icon;
	}

}
