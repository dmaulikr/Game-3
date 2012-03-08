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
	private Texture highlight;

	enum view {
		South, // base
		West, // -90°
		North, // -/+180°
		East // -270°/+90°
	}

	private view currentView;

	private int currentTileOnFocusX;
	private int currentTileOnFocusY;
	private int currentTileOnFocusZ;

	private float focusXToGo;
	private float focusYToGo;
	private float focusZToGo;
	private float rotationToGo;
	private float scaleToGo;

	private boolean isBusy;

	public DisplayManager(Map demoMap) {
		setDemoMap(demoMap);
		setScale(1f / 10f);
		setZscale(1f / 100f);

		currentTileOnFocusX = 0;
		currentTileOnFocusY = 0;
		currentTileOnFocusZ = 0;
		rotationToGo = 0f;
		isBusy = false;
		currentView = view.South;

	}

	public void init() {
		try {
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.setSwapInterval(1);
			Display.sync(60);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		GL11.glViewport(0, 0, 800, 600);
		GL11.glDepthRange(1, 1000);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);

		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();

		GL11.glRotatef((float) Math.toDegrees(Math.atan(0.5)), 1, 0, 0); // 26,565
		GL11.glRotatef(-45.0f, 0, 1, 0); // -45

		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();

		LoadTextures();
	}

	public void run() {
		while (!Display.isCloseRequested()) {
			Update();
			if (Display.isVisible()) {
				Draw();
			}
			Display.update();
		}
		Display.destroy();
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

		switch (currentView) {
		case South:
			for (int i = 0; i < demoMap.getLength(); i++) {
				for (int j = 0; j < demoMap.getWidth(); j++) {
					DrawATile(demoMap.getTile(i, j));
					if (i + 1 < demoMap.getLength()) {
						if (demoMap.getTile(i, j).getHeight() > demoMap.getTile(i + 1, j).getHeight()) {
							DrawTheLinkSO(demoMap.getTile(i, j), demoMap.getTile(i + 1, j));
						}
					} else {
						DrawTheLinkSO(demoMap.getTile(i, j), new Tile(i + 1, j, 0));
					}
					if (j + 1 < demoMap.getWidth()) {
						if (demoMap.getTile(i, j).getHeight() > demoMap.getTile(i, j + 1).getHeight()) {
							DrawTheLinkSE(demoMap.getTile(i, j), demoMap.getTile(i, j + 1));
						}
					} else {
						DrawTheLinkSE(demoMap.getTile(i, j), new Tile(i, j + 1, 0));
					}
				}
			}
			break;
		case West:
			for (int i = 0; i < demoMap.getLength(); i++) {
				for (int j = demoMap.getWidth() - 1; j >= 0; j--) {
					DrawATile(demoMap.getTile(i, j));
					if (i + 1 < demoMap.getLength()) {
						if (demoMap.getTile(i, j).getHeight() > demoMap.getTile(i + 1, j).getHeight()) {
							DrawTheLinkSO(demoMap.getTile(i, j), demoMap.getTile(i + 1, j));
						}
					} else {
						DrawTheLinkSO(demoMap.getTile(i, j), new Tile(i + 1, j, 0));
					}
					if (j > 0) {
						if (demoMap.getTile(i, j).getHeight() > demoMap.getTile(i, j - 1).getHeight()) {
							DrawTheLinkNO(demoMap.getTile(i, j), demoMap.getTile(i, j - 1));
						}
					} else {
						DrawTheLinkNO(demoMap.getTile(i, j), new Tile(i, j - 1, 0));
					}
				}
			}
			break;
		case North:
			for (int i = demoMap.getLength() - 1; i >= 0; i--) {
				for (int j = demoMap.getWidth() - 1; j >= 0; j--) {
					DrawATile(demoMap.getTile(i, j));
					if (i > 0) {
						if (demoMap.getTile(i, j).getHeight() > demoMap.getTile(i - 1, j).getHeight()) {
							DrawTheLinkNE(demoMap.getTile(i, j), demoMap.getTile(i - 1, j));
						}
					} else {
						DrawTheLinkNE(demoMap.getTile(i, j), new Tile(i + 1, j, 0));
					}
					if (j > 0) {
						if (demoMap.getTile(i, j).getHeight() > demoMap.getTile(i, j - 1).getHeight()) {
							DrawTheLinkNO(demoMap.getTile(i, j), demoMap.getTile(i, j - 1));
						}
					} else {
						DrawTheLinkNO(demoMap.getTile(i, j), new Tile(i, j - 1, 0));
					}
				}
			}
			break;
		case East:
			for (int j = 0; j < demoMap.getWidth(); j++) {
				for (int i = demoMap.getLength() - 1; i >= 0; i -= 1) {
					DrawATile(demoMap.getTile(i, j));
					if (i > 0) {
						if (demoMap.getTile(i, j).getHeight() > demoMap.getTile(i - 1, j).getHeight()) {
							DrawTheLinkNE(demoMap.getTile(i, j), demoMap.getTile(i - 1, j));
						}
					} else {
						DrawTheLinkNE(demoMap.getTile(i, j), new Tile(i - 1, j, 0));
					}
					if (j + 1 < demoMap.getWidth()) {
						if (demoMap.getTile(i, j).getHeight() > demoMap.getTile(i, j + 1).getHeight()) {
							DrawTheLinkSE(demoMap.getTile(i, j), demoMap.getTile(i, j + 1));
						}
					} else {
						DrawTheLinkSE(demoMap.getTile(i, j), new Tile(i, j + 1, 0));
					}
				}
			}
			break;
		}
	}

	private void DrawATile(Tile t) {
		SelectColor(t);
		// SouthEast means X is constant.
		float x1 = t.getPosY() * scale;
		float x2 = (t.getPosY() + 1) * scale;

		float y = t.getHeight() * getZscale();

		float z1 = t.getPosX() * scale;
		float z2 = (t.getPosX() + 1) * scale;

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2d(0, 0);
		GL11.glVertex3d(x1, y, z1);
		GL11.glTexCoord2d(1, 0);
		GL11.glVertex3d(x2, y, z1);
		GL11.glTexCoord2d(1, 1);
		GL11.glVertex3d(x2, y, z2);
		GL11.glTexCoord2d(0, 1);
		GL11.glVertex3d(x1, y, z2);
		GL11.glEnd();

		GL11.glColor3f(0f, 0f, 0f);
		GL11.glBegin(GL11.GL_LINE_LOOP);
		GL11.glVertex3d(x1, y, z1);
		GL11.glVertex3d(x2, y, z1);
		GL11.glVertex3d(x2, y, z2);
		GL11.glVertex3d(x1, y, z2);
		GL11.glEnd();
	}

	private void DrawTheLinkSE(Tile t1, Tile t2) {
		SelectColor(t1);
		// SouthEast means X is constant.
		float x1 = t2.getPosY() * scale;
		float x2 = x1;
		float x3 = x1;
		float x4 = x1;

		float y1 = t1.getHeight() * zscale;
		float y2 = y1;
		float y3 = t2.getHeight() * zscale;
		float y4 = y3;

		float z1 = (t1.getPosX() + 1) * scale;
		float z2 = t1.getPosX() * scale;
		float z3 = z2;
		float z4 = z1;

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2d(0, 0);
		GL11.glVertex3d(x1, y1, z1);
		GL11.glTexCoord2d(1, 0);
		GL11.glVertex3d(x2, y2, z2);
		GL11.glTexCoord2d(1, 1);
		GL11.glVertex3d(x3, y3, z3);
		GL11.glTexCoord2d(0, 1);
		GL11.glVertex3d(x4, y4, z4);
		GL11.glEnd();

		GL11.glColor3f(0f, 0f, 0f);
		GL11.glBegin(GL11.GL_LINE_LOOP);
		GL11.glVertex3d(x1, y1, z1);
		GL11.glVertex3d(x2, y2, z2);
		GL11.glVertex3d(x3, y3, z3);
		GL11.glVertex3d(x4, y4, z4);
		GL11.glEnd();
	}

	private void DrawTheLinkSO(Tile t1, Tile t2) {
		SelectColor(t1);
		// SouthWest means Z is constant.
		float x1 = t1.getPosY() * scale;
		float x2 = (t1.getPosY() + 1) * scale;
		float x3 = x2;
		float x4 = x1;

		float y1 = t1.getHeight() * zscale;
		float y2 = y1;
		float y3 = t2.getHeight() * zscale;
		float y4 = y3;

		float z1 = t2.getPosX() * scale;
		float z2 = z1;
		float z3 = z1;
		float z4 = z1;

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2d(0, 0);
		GL11.glVertex3d(x1, y1, z1);
		GL11.glTexCoord2d(1, 0);
		GL11.glVertex3d(x2, y2, z2);
		GL11.glTexCoord2d(1, 1);
		GL11.glVertex3d(x3, y3, z3);
		GL11.glTexCoord2d(0, 1);
		GL11.glVertex3d(x4, y4, z4);
		GL11.glEnd();

		GL11.glColor3f(0f, 0f, 0f);
		GL11.glBegin(GL11.GL_LINE_LOOP);
		GL11.glVertex3d(x1, y1, z1);
		GL11.glVertex3d(x2, y2, z2);
		GL11.glVertex3d(x3, y3, z3);
		GL11.glVertex3d(x4, y4, z4);
		GL11.glEnd();
	}

	private void DrawTheLinkNE(Tile t1, Tile t2) {
		SelectColor(t1);
		// NorthEast means Z is constant.
		float x1 = t1.getPosY() * scale;
		float x2 = (t1.getPosY() + 1) * scale;
		float x3 = x2;
		float x4 = x1;

		float y1 = t1.getHeight() * zscale;
		float y2 = y1;
		float y3 = t2.getHeight() * zscale;
		float y4 = y3;

		float z1 = t1.getPosX() * scale;
		float z2 = z1;
		float z3 = z1;
		float z4 = z1;

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2d(0, 0);
		GL11.glVertex3d(x1, y1, z1);
		GL11.glTexCoord2d(1, 0);
		GL11.glVertex3d(x2, y2, z2);
		GL11.glTexCoord2d(1, 1);
		GL11.glVertex3d(x3, y3, z3);
		GL11.glTexCoord2d(0, 1);
		GL11.glVertex3d(x4, y4, z4);
		GL11.glEnd();

		GL11.glColor3f(0f, 0f, 0f);
		GL11.glBegin(GL11.GL_LINE_LOOP);
		GL11.glVertex3d(x1, y1, z1);
		GL11.glVertex3d(x2, y2, z2);
		GL11.glVertex3d(x3, y3, z3);
		GL11.glVertex3d(x4, y4, z4);
		GL11.glEnd();
	}

	private void DrawTheLinkNO(Tile t1, Tile t2) {
		SelectColor(t1);
		// NorthWest means X is constant.
		float x1 = t1.getPosY() * scale;
		float x2 = x1;
		float x3 = x1;
		float x4 = x1;

		float y1 = t1.getHeight() * zscale;
		float y2 = y1;
		float y3 = t2.getHeight() * zscale;
		float y4 = y3;

		float z1 = (t1.getPosX() + 1) * scale;
		float z2 = t1.getPosX() * scale;
		float z3 = z2;
		float z4 = z1;

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2d(0, 0);
		GL11.glVertex3d(x1, y1, z1);
		GL11.glTexCoord2d(1, 0);
		GL11.glVertex3d(x2, y2, z2);
		GL11.glTexCoord2d(1, 1);
		GL11.glVertex3d(x3, y3, z3);
		GL11.glTexCoord2d(0, 1);
		GL11.glVertex3d(x4, y4, z4);
		GL11.glEnd();

		GL11.glColor3f(0f, 0f, 0f);
		GL11.glBegin(GL11.GL_LINE_LOOP);
		GL11.glVertex3d(x1, y1, z1);
		GL11.glVertex3d(x2, y2, z2);
		GL11.glVertex3d(x3, y3, z3);
		GL11.glVertex3d(x4, y4, z4);
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

	public void DrawHighlight(Tile t) {

		GL11.glColor4f(1f, 1f, 1f, 0.4f);
		highlight.bind();

		float x1 = ((float) t.getPosY() * scale);
		float x2 = (((float) t.getPosY() + 1) * scale);

		float y1 = ((float) t.getPosX() * scale);
		float y2 = (((float) t.getPosX() + 1) * scale);

		float z1 = ((float) t.getHeight() * zscale) + (3f * zscale);

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2d(0, 0);
		GL11.glVertex3d(x1, z1, y1);
		GL11.glTexCoord2d(1, 0);
		GL11.glVertex3d(x2, z1, y1);
		GL11.glTexCoord2d(1, 1);
		GL11.glVertex3d(x2, z1, y2);
		GL11.glTexCoord2d(0, 1);
		GL11.glVertex3d(x1, z1, y2);
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
			highlight = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("images/highlightblue.PNG"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean RequestFocusOn(int X, int Y, int Z) {
		if (isBusy) {
			return false;
		} else {
			focusXToGo = (float) Math.round((X - currentTileOnFocusX) * (scale * 100)) / 100;
			focusYToGo = (float) Math.round((Y - currentTileOnFocusY) * (scale * 100)) / 100;
			focusZToGo = (float) Math.round((Z - currentTileOnFocusZ) * (zscale * 100)) / 100;
			currentTileOnFocusX = X;
			currentTileOnFocusY = Y;
			currentTileOnFocusZ = Z;
			setBusy(true);
			return true;
		}
	}

	private void SetFocusOnNoWait(int X, int Y, int Z) {
		float x = (float) Math.round((X - currentTileOnFocusX) * (scale * 100)) / 100;
		float y = (float) Math.round((Y - currentTileOnFocusY) * (scale * 100)) / 100;
		float z = (float) Math.round((Z - currentTileOnFocusZ) * (zscale * 100)) / 100;
		GL11.glTranslatef(x, z, y);
		currentTileOnFocusX = X;
		currentTileOnFocusY = Y;
		currentTileOnFocusZ = Z;
	}

	public boolean RequestView(view v) {
		if (isBusy) {
			return false;
		} else {
			if (!v.equals(currentView)) {
				setBusy(true);
				switch (currentView) {
				case South:
					switch (v) {
					case West:
						rotationToGo = -90f;
						break;
					case North:
						rotationToGo = -180f;
						break;
					case East:
						rotationToGo = 90f;
						break;
					}
					break;
				case West:
					switch (v) {
					case South:
						rotationToGo = 90f;
						break;
					case North:
						rotationToGo = -90f;
						break;
					case East:
						rotationToGo = -180f;
						break;
					}
					break;
				case North:
					switch (v) {
					case South:
						rotationToGo = -180f;
						break;
					case West:
						rotationToGo = 90f;
						break;
					case East:
						rotationToGo = -90f;
						break;
					}
					break;
				case East:
					switch (v) {
					case South:
						rotationToGo = -90f;
						break;
					case West:
						rotationToGo = -180f;
						break;
					case North:
						rotationToGo = 90f;
						break;
					}
					break;
				default:
					break;
				}
			}
			return true;
		}
	}

	private void Update() {
		if (isBusy) {
			focusXToGo = (float) Math.round(focusXToGo * 100) / 100;
			focusYToGo = (float) Math.round(focusYToGo * 100) / 100;
			focusZToGo = (float) Math.round(focusZToGo * 100) / 100;
			rotationToGo = (float) Math.round(rotationToGo * 100) / 100;

			if (focusXToGo != 0) {
				if (focusXToGo < 0) {
					GL11.glTranslated(0.01f, 0, 0);
					focusXToGo += 0.01f;
				} else if (focusXToGo > 0) {
					GL11.glTranslated(-0.01f, 0, 0);
					focusXToGo -= 0.01f;
				}
			}
			if (focusYToGo != 0) {
				if (focusYToGo < 0) {
					GL11.glTranslated(0, 0, 0.01f);
					focusYToGo += 0.01f;
				} else if (focusYToGo > 0) {
					GL11.glTranslated(0, 0, -0.01f);
					focusYToGo -= 0.01f;
				}
			}
			if (focusZToGo != 0) {
				if (focusZToGo < 0) {
					GL11.glTranslated(0, 0.01f, 0);
					focusZToGo += 0.01f;
				} else if (focusZToGo > 0) {
					GL11.glTranslated(0, -0.01f, 0);
					focusZToGo -= 0.01f;
				}
			}
			/*
			 * if (scaleToGo != 0) { if (scaleToGo < scale) { scale -= 0.001f; }
			 * if (scaleToGo > scale) { scale += 0.001f; } }
			 */
			if (rotationToGo != 0) {
				if (rotationToGo < 0) {
					GL11.glRotatef(1f, 0, 1, 0);
					double a = Math.sin(Math.PI / 180);
					double b = Math.sin(Math.PI / 180);
					GL11.glTranslated(-a / 2, 0, b / 2);
					rotationToGo += 1f;
				}
				if (rotationToGo > 0) {
					GL11.glRotatef(-1f, 0, 1, 0);
					double a = Math.sin(Math.PI / 180);
					double b = Math.sin(Math.PI / 180);
					GL11.glTranslated(a / 2, 0, -b / 2);
					rotationToGo -= 1f;
				}
				if (rotationToGo == -45f || rotationToGo == -135f || rotationToGo == -225f || rotationToGo == -315f) {
					switch (currentView) {
					case South:
						currentView = view.West;
						break;
					case West:
						currentView = view.North;
						break;
					case North:
						currentView = view.East;
						break;
					case East:
						currentView = view.South;
						break;
					}
				}
				if (rotationToGo == 45f || rotationToGo == 135f || rotationToGo == 225f || rotationToGo == 315f) {
					switch (currentView) {
					case South:
						currentView = view.East;
						break;
					case West:
						currentView = view.South;
						break;
					case North:
						currentView = view.West;
						break;
					case East:
						currentView = view.North;
						break;
					}
				}
			}

			if (focusXToGo == 0 && focusYToGo == 0 && focusZToGo == 0 && rotationToGo == 0) {
				setBusy(false);
			}
		}

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

	public static void main(String[] argv) {
		Map map = new Map(20, 25, "lolilol");
		map.getTile(2, 2).setHeight(25);
		DisplayManager display = new DisplayManager(map);
		display.init();
		display.run();
	}

	public boolean isBusy() {
		return isBusy;
	}

	public void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}

}
