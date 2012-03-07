package displaymanager;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.math.*;

import javax.swing.text.html.MinimalHTMLWriter;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import entity.Map;
import entity.Tile;

public class DisplayManager {
	private Map demoMap;
	private float scale;
	private float zscale;
	private Texture textureGrass;
	private Texture textureStone;
	private Texture textureSand;
	private Texture textureEarth;
	private Texture imageHerbe;
	float angleX = (float) Math.toDegrees(Math.atan(0.5)); // 26,565
	float angleY = -45.0f;

	//pour les rotations utiliser GL11.glRotatef(90f, 0, 1, 0);
	//et ne pas oublier de gere l'affichage des cotes des tiles en fonction.
		
	int state;

	private int currentTileOnFocusX;
	private int currentTileOnFocusY;
	private int currentTileOnFocusZ;

	private float focusXToGo;
	private float focusYToGo;
	private float focusZToGo;
	private float scaleToGo;

	public DisplayManager(Map demoMap) {
		setDemoMap(demoMap);
		float a = 1f / (float) demoMap.getLength();
		float b = 1f / (float) demoMap.getWidth();
		setScale(Math.min(a, b));
		setScale(scale * 2f);
		float c = 1f / (float) 50;
		setZscale(c);
	}

	public void start() {
		try {
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.setSwapInterval(1);
			Display.sync(60);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		currentTileOnFocusX = 0;
		currentTileOnFocusY = 0;
		currentTileOnFocusZ = 0;
		GL11.glViewport(0, 0, 800, 600);
		GL11.glDepthRange(1, 1000);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);

		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();

		GL11.glRotatef(angleX, 1, 0, 0); // 26,565
		GL11.glRotatef(angleY, 0, 1, 0); // -45

		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		LoadTextures();
		while (!Display.isCloseRequested()) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			Update();
			if (Display.isVisible()) {
				Draw();
			}
			Display.update();
		}

		Display.destroy();
	}

	
	public Map getDemoMap() {
		return demoMap;
	}

	public void setDemoMap(Map demoMap) {
		this.demoMap = demoMap;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float f) {
		this.scale = f;
	}

	public float getZscale() {
		return zscale;
	}

	public void setZscale(float zscale) {
		this.zscale = zscale;
	}

	private void Draw() {

		// Clear
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		GL11.glBegin(GL11.GL_LINES);

		GL11.glColor3d(1.0, 0.0, 0.0);
		GL11.glVertex3d(0.0, 0.0, 0.0);
		GL11.glVertex3d(1.0, 0.0, 0.0);

		GL11.glColor3d(0.0, 1.0, 0.0);
		GL11.glVertex3d(0.0, 0.0, 0.0);
		GL11.glVertex3d(0.0, 1.0, 0.0);

		GL11.glColor3d(0.0, 0.0, 1.0);
		GL11.glVertex3d(0.0, 0.0, 0.0);
		GL11.glVertex3d(0.0, 0.0, 1.0);

		GL11.glEnd();

		for (int i = 0; i < demoMap.getLength(); i++) {
			for (int j = 0; j < demoMap.getWidth(); j++) {
				DrawATile(demoMap.getTile(i, j));
				//DrawGrass(demoMap.getTile(i, j), 5);
				if (i + 1 < demoMap.getLength()) {
					if (demoMap.getTile(i, j).getHeight() > demoMap.getTile(i + 1, j).getHeight()) {
						DrawTheLinkSE(demoMap.getTile(i, j), demoMap.getTile(i + 1, j));
					}
				} else {
					DrawTheLinkSE(demoMap.getTile(i, j), new Tile(i + 1, j, 0));
				}
				if (j + 1 < demoMap.getWidth()) {
					if (demoMap.getTile(i, j).getHeight() > demoMap.getTile(i, j + 1).getHeight()) {
						DrawTheLinkSO(demoMap.getTile(i, j), demoMap.getTile(i, j + 1));
					}
				} else {
					DrawTheLinkSO(demoMap.getTile(i, j), new Tile(i, j + 1, 0));
				}
			}
		}

	}

	private void DrawATile(Tile t) {
		SelectColor(t);

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2d(0, 0);
		GL11.glVertex3d(t.getPosY() * scale, t.getHeight() * getZscale(), t.getPosX() * scale);
		GL11.glTexCoord2d(1, 0);
		GL11.glVertex3d(t.getPosY() * scale, t.getHeight() * getZscale(), (t.getPosX() + 1) * scale);
		GL11.glTexCoord2d(1, 1);
		GL11.glVertex3d((t.getPosY() + 1) * scale, t.getHeight() * getZscale(), (t.getPosX() + 1) * scale);
		GL11.glTexCoord2d(0, 1);
		GL11.glVertex3d((t.getPosY() + 1) * scale, t.getHeight() * getZscale(), t.getPosX() * scale);

		GL11.glEnd();

		GL11.glColor3f(0f, 0f, 0f);
		GL11.glBegin(GL11.GL_LINE_LOOP);

		GL11.glVertex3d(t.getPosY() * scale, t.getHeight() * getZscale(), t.getPosX() * scale);
		GL11.glVertex3d(t.getPosY() * scale, t.getHeight() * getZscale(), (t.getPosX() + 1) * scale);
		GL11.glVertex3d((t.getPosY() + 1) * scale, t.getHeight() * getZscale(), (t.getPosX() + 1) * scale);
		GL11.glVertex3d((t.getPosY() + 1) * scale, t.getHeight() * getZscale(), t.getPosX() * scale);

		GL11.glEnd();
	}

	private void DrawTheLinkSE(Tile t1, Tile t2) {
		SelectColor(t1);

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2d(0, 0);
		GL11.glVertex3d(t1.getPosY() * scale, t1.getHeight() * getZscale(), (t1.getPosX() + 1) * scale);
		GL11.glTexCoord2d(1, 0);
		GL11.glVertex3d(t2.getPosY() * scale, t2.getHeight() * getZscale(), t2.getPosX() * scale);
		GL11.glTexCoord2d(1, 1);
		GL11.glVertex3d((t2.getPosY() + 1) * scale, t2.getHeight() * getZscale(), t2.getPosX() * scale);
		GL11.glTexCoord2d(0, 1);
		GL11.glVertex3d((t1.getPosY() + 1) * scale, t1.getHeight() * getZscale(), (t1.getPosX() + 1) * scale);

		GL11.glEnd();

		GL11.glColor3f(0f, 0f, 0f);
		GL11.glBegin(GL11.GL_LINE_LOOP);

		GL11.glVertex3d(t1.getPosY() * scale, t1.getHeight() * getZscale(), (t1.getPosX() + 1) * scale);
		GL11.glVertex3d(t2.getPosY() * scale, t2.getHeight() * getZscale(), t2.getPosX() * scale);
		GL11.glVertex3d((t2.getPosY() + 1) * scale, t2.getHeight() * getZscale(), t2.getPosX() * scale);
		GL11.glVertex3d((t1.getPosY() + 1) * scale, t1.getHeight() * getZscale(), (t1.getPosX() + 1) * scale);

		GL11.glEnd();
	}

	private void DrawTheLinkSO(Tile t1, Tile t2) {
		SelectColor(t1);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2d(0, 0);
		GL11.glVertex3d((t1.getPosY() + 1) * scale, t1.getHeight() * getZscale(), (t1.getPosX() + 1) * scale);
		GL11.glTexCoord2d(1, 0);
		GL11.glVertex3d(t2.getPosY() * scale, t2.getHeight() * getZscale(), (t2.getPosX() + 1) * scale);
		GL11.glTexCoord2d(1, 1);
		GL11.glVertex3d(t2.getPosY() * scale, t2.getHeight() * getZscale(), t2.getPosX() * scale);
		GL11.glTexCoord2d(0, 1);
		GL11.glVertex3d((t1.getPosY() + 1) * scale, t1.getHeight() * getZscale(), t1.getPosX() * scale);

		GL11.glEnd();

		GL11.glColor3f(0f, 0f, 0f);
		GL11.glBegin(GL11.GL_LINE_LOOP);

		GL11.glVertex3d((t1.getPosY() + 1) * scale, t1.getHeight() * getZscale(), (t1.getPosX() + 1) * scale);
		GL11.glVertex3d(t2.getPosY() * scale, t2.getHeight() * getZscale(), (t2.getPosX() + 1) * scale);
		GL11.glVertex3d(t2.getPosY() * scale, t2.getHeight() * getZscale(), t2.getPosX() * scale);
		GL11.glVertex3d((t1.getPosY() + 1) * scale, t1.getHeight() * getZscale(), t1.getPosX() * scale);

		GL11.glEnd();
	}

	public void DrawGrass(Tile t, int density) {

		GL11.glColor4f(1f, 1f, 1f, 1f);
		imageHerbe.bind();

		float y1 = ((float) t.getPosX() * scale) + (0.1f) * scale;
		float y2 = ((float) t.getPosX() * scale) + (0.3f) * scale;
		float x1 = ((float) t.getPosY() * scale) + (0.3f) * scale;
		float x2 = ((float) t.getPosY() * scale) + (0.1f) * scale;
		float z1 = ((float) t.getHeight() * zscale) + (0.1f);
		float z2 = t.getHeight() * zscale;

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2d(0, 0);
		GL11.glVertex3d(x1, z1, y1);
		GL11.glTexCoord2d(1, 0);
		GL11.glVertex3d(x2, z1, y2);
		GL11.glTexCoord2d(1, 1);
		GL11.glVertex3d(x2, z2, y2);
		GL11.glTexCoord2d(0, 1);
		GL11.glVertex3d(x1, z2, y1);
		GL11.glEnd();

	}

	private void SelectColor(Tile tile) {
		GL11.glColor4f(1f, 1f, 1f, 1f);
		switch (tile.getTexture()) {
		case Grass:
			textureGrass.bind();
			// GL11.glColor3f(0.18f, 0.5f, 0.17f);
			break;

		case Earth:
			textureEarth.bind();
			// GL11.glColor3f(0.5f, 0.25f, 0f);
			break;

		case Sand:
			textureSand.bind();
			// GL11.glColor3f(1f, 1f, 0.5f);
			break;

		case Stone:
			textureStone.bind();
			// GL11.glColor3f(0.5f, 0.5f, 0.5f);
			break;

		default:
			// GL11.glColor3f(0.5f, 0.5f, 1.0f);
			break;
		}
	}

	private void LoadTextures() {
		try {
			textureGrass = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("images/Grass.PNG"));
			textureSand = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("images/Sand.PNG"));
			textureStone = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("images/Stone.PNG"));
			textureEarth = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("images/Earth.PNG"));
			imageHerbe = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("images/fleur1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void SetFocusOn(int X, int Y, int Z) {
		focusXToGo = (float) Math.round((X - currentTileOnFocusX) * scale * 100) / 100;
		focusYToGo = (float) Math.round((Y - currentTileOnFocusY) * scale * 100) / 100;
		focusZToGo = (float) Math.round((Z - currentTileOnFocusZ) * zscale * 100) / 100;
		currentTileOnFocusX = X;
		currentTileOnFocusY = Y;
		currentTileOnFocusZ = Z;
	}

	private void Update() {
		focusXToGo = (float) Math.round(focusXToGo * 100) / 100;
		focusYToGo = (float) Math.round(focusYToGo * 100) / 100;
		focusZToGo = (float) Math.round(focusZToGo * 100) / 100;

		if (focusXToGo != 0) {
			if (focusXToGo < 0) {
				GL11.glTranslated(0.01, 0, 0);
				focusXToGo += 0.01;
			} else if (focusXToGo > 0) {
				GL11.glTranslated(-0.01, 0, 0);
				focusXToGo -= 0.01;
			}
		}
		if (focusYToGo != 0) {
			if (focusYToGo < 0) {
				GL11.glTranslated(0, 0, 0.01);
				focusYToGo += 0.01;
			} else if (focusYToGo > 0) {
				GL11.glTranslated(0, 0, -0.01);
				focusYToGo -= 0.01;
			}
		}
		if (focusZToGo != 0) {
			if (focusZToGo < 0) {
				GL11.glTranslated(0, 0.01, 0);
				focusZToGo += 0.01;
			} else if (focusZToGo > 0) {
				GL11.glTranslated(0, -0.01, 0);
				focusZToGo -= 0.01;
			}
		}
		if (scaleToGo != 0) {
			if (scaleToGo < scale) {
				scale -= 0.001;
			}
			if (scaleToGo > scale) {
				scale += 0.001;
			}
		}
		if (focusXToGo == 0 && focusYToGo == 0 && focusZToGo == 0) {
			// test();
		}
	}

	private void test() {
		Tile tile;
		switch (state) {
		case 0:
			tile = demoMap.getTile(2, 3);
			SetFocusOn(tile.getPosX(), tile.getPosY(), tile.getHeight());
			break;
		case 1:
			tile = demoMap.getTile(0, 0);
			SetFocusOn(tile.getPosX(), tile.getPosY(), tile.getHeight());
			break;
		case 2:
			tile = demoMap.getTile(4, 4);
			SetFocusOn(tile.getPosX(), tile.getPosY(), tile.getHeight());
			break;
		case 3:
			tile = demoMap.getTile(1, 4);
			SetFocusOn(tile.getPosX(), tile.getPosY(), tile.getHeight());
			break;

		default:
			break;
		}

		state++;
	}
	
	public static void main(String[] argv) {
		DisplayManager display = new DisplayManager(new Map(5, 5, "lolilol"));
		display.start();
	}

}
