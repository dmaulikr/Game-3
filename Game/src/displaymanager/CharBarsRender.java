package displaymanager;

import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import entity.Character;

public class CharBarsRender {
	private float Xdep;
	private float Ydep;
	private UnicodeFont font;
	private Character currentChar;
	private Texture HP;
	private Texture MP;
	BarDrawer HPBar;
	BarDrawer MPBar;

	public CharBarsRender() {
		Xdep = 0;
		Ydep = 0;
		currentChar = null;
		HPBar = new BarDrawer(0.8f, 0.05f, 0.09f);
		MPBar = new BarDrawer(0.0f, 0.0f, 1.0f);
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
		} catch (IOException e) {
			e.printStackTrace();
		}
		HPBar.Init();
		MPBar.Init();
		HPBar.setIcon(HP);
		MPBar.setIcon(MP);
	}

	public void Render() {
		if (currentChar != null) {
			float a = ((float) currentChar.getLifePoints() / (float) currentChar.getMaxLifePoints()) * 100f;
			HPBar.Render(a);
			float b = ((float) currentChar.getManaPoints() / (float) currentChar.getMaxManaPoints()) * 100f;
			MPBar.Render(b);

			font.drawString(Xdep + 50f, Ydep, currentChar.getLifePoints() + "/" + currentChar.getMaxLifePoints(), Color.white);
			font.drawString(Xdep + 50f, Ydep + 25f, currentChar.getManaPoints() + "/" + currentChar.getMaxManaPoints(), Color.white);
		}
	}

	public void setXdep(float xdep) {
		Xdep = xdep;
		HPBar.setXdep(xdep);
		MPBar.setXdep(xdep);
	}

	public void setYdep(float ydep) {
		Ydep = ydep;
		HPBar.setYdep(ydep);
		MPBar.setYdep(ydep + 25f);
	}

	public void setCurrentChar(Character c) {
		this.currentChar = c;
	}

}
