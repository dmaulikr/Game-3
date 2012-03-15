package displaymanager;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import entity.Character;

public class CharCaracRender {
	private Texture ATK;
	private Texture MAG;
	private Texture ARP;
	private Texture MAP;
	private Texture ARM;
	private Texture MAR;
	private Texture I;
	private Texture M;
	private UnicodeFont font;
	private float Xdep;
	private float Ydep;
	private Character currentChar;

	public CharCaracRender() {
		Xdep = 0;
		Ydep = 0;
		currentChar = null;
	}

	public void Init(Texture ATK, Texture MAG, Texture ARM, Texture MAR, Texture ARP, Texture MAP, Texture I, Texture M, UnicodeFont font) {
		this.font = font;
		this.ATK = ATK;
		this.MAG = MAG;
		this.ARM = ARM;
		this.MAR = MAR;
		this.ARP = ARP;
		this.MAP = MAP;
		this.I = I;
		this.M = M;
	}

	public void Render() {
		if (currentChar != null) {
			RenderATK(currentChar, Xdep, Ydep);
			RenderMAG(currentChar, Xdep + 50f, Ydep);
			RenderARM(currentChar, Xdep, Ydep + 25f);
			RenderMAR(currentChar, Xdep + 50f, Ydep + 25f);
			RenderARP(currentChar, Xdep, Ydep + 50f);
			RenderMAP(currentChar, Xdep + 50f, Ydep + 50f);
			RenderM(currentChar, Xdep, Ydep + 75f);
			RenderI(currentChar, Xdep + 50f, Ydep + 75f);
		}
	}

	public void RenderATK(Character c, float X, float Y) {
		RenderATKimg(X, Y);
		Color.white.bind();
		font.drawString(X + 25f, Y, String.valueOf(c.getAttackPower()), Color.white);
	}

	public void RenderMAG(Character c, float X, float Y) {
		RenderMAGimg(X, Y);
		Color.white.bind();
		font.drawString(X + 25f, Y, String.valueOf(c.getMagicPower()), Color.white);
	}

	public void RenderARM(Character c, float X, float Y) {
		RenderARMimg(X, Y);
		Color.white.bind();
		font.drawString(X + 25f, Y, String.valueOf(c.getArmor()), Color.white);
	}

	public void RenderMAR(Character c, float X, float Y) {
		RenderMARimg(X, Y);
		Color.white.bind();
		font.drawString(X + 25f, Y, String.valueOf(c.getMagicArmor()), Color.white);
	}

	public void RenderARP(Character c, float X, float Y) {
		RenderARPimg(X, Y);
		Color.white.bind();
		font.drawString(X + 25f, Y, String.valueOf(c.getArmorPenetration()), Color.white);
	}

	public void RenderMAP(Character c, float X, float Y) {
		RenderMAPimg(X, Y);
		Color.white.bind();
		font.drawString(X + 25f, Y, String.valueOf(c.getMagicPenetration()), Color.white);
	}

	public void RenderI(Character c, float X, float Y) {
		RenderIimg(X, Y);
		Color.white.bind();
		font.drawString(X + 25f, Y, String.valueOf(c.getInitiative()), Color.white);
	}

	public void RenderM(Character c, float X, float Y) {
		RenderMimg(X, Y);
		Color.white.bind();
		font.drawString(X + 25f, Y, String.valueOf(c.getMovement()), Color.white);
	}

	public void RenderATKimg(float X, float Y) {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glColor4f(1f, 1f, 1f, 1f);
		ATK.bind();
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

	public void RenderMAGimg(float X, float Y) {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glColor4f(1f, 1f, 1f, 1f);
		MAG.bind();
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

	public void RenderARMimg(float X, float Y) {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glColor4f(1f, 1f, 1f, 1f);
		ARM.bind();
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

	public void RenderMARimg(float X, float Y) {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glColor4f(1f, 1f, 1f, 1f);
		MAR.bind();
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

	public void RenderARPimg(float X, float Y) {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glColor4f(1f, 1f, 1f, 1f);
		ARP.bind();
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

	public void RenderMAPimg(float X, float Y) {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glColor4f(1f, 1f, 1f, 1f);
		MAP.bind();
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

	public void RenderMimg(float X, float Y) {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glColor4f(1f, 1f, 1f, 1f);
		M.bind();
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

	public void RenderIimg(float X, float Y) {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glColor4f(1f, 1f, 1f, 1f);
		I.bind();
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

	public void setXdep(float xdep) {
		Xdep = xdep;
	}

	public void setYdep(float ydep) {
		Ydep = ydep;
	}

	public void setCurrentChar(Character c) {
		this.currentChar = c;
	}

}
