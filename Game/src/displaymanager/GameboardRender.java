package displaymanager;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import entity.Character;
import entity.Map;
import entity.Tile;
import entity.TileTexture;

public class GameboardRender {
	private Map demoMap;
	private float scale;
	private float zscale;

	private Texture imageHerbe;
	private Texture highlight;
	private Texture highlightG;
	private Texture highlightR;
	private Texture cara;

	public enum viewPoint {
		South, // base
		West, // -90°
		North, // -/+180°
		East
		// -270°/+90°
	}

	private viewPoint currentView;

	private ArrayList<Character> charsToDRaw;

	private int currentTileOnFocusX;
	private int currentTileOnFocusY;
	private int currentTileOnFocusZ;

	private float originX;
	private float originY;

	private float focusXToGo;
	private float focusYToGo;
	private float focusZToGo;
	private float rotationToGo;
	private float scaleToGo;

	private boolean isBusy;

	public GameboardRender(Map demoMap) {
		setDemoMap(demoMap);
		setScale(1f / 5f);
		setZscale(1f / 15f);

		originX = 0;
		originY = 0;

		currentTileOnFocusX = 0;
		currentTileOnFocusY = 0;
		currentTileOnFocusZ = 0;
		scaleToGo = 0;
		rotationToGo = 0f;
		isBusy = false;
		currentView = viewPoint.South;
		charsToDRaw = new ArrayList<Character>();

	}

	public void Init() {
		LoadTextures();
		SetupLigths();
		TileTexture tt = new TileTexture();
		tt.LoadBundles(this.demoMap.getAllTextureTypes());
		this.demoMap.BindTextures(tt);
	}

	public void Render() {
		CheckLogic();
		Draw();
	}

	private void Draw() {
		// Clear
		// GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		/*
		 * GL11.glBegin(GL11.GL_LINES);
		 * 
		 * GL11.glColor3d(1.0, 0.0, 0.0); GL11.glVertex3d(0.0, 0.0, 0.0);
		 * GL11.glVertex3d(1.0, 0.0, 0.0);
		 * 
		 * GL11.glColor3d(0.0, 1.0, 0.0); GL11.glVertex3d(0.0, 0.0, 0.0);
		 * GL11.glVertex3d(0.0, 1.0, 0.0);
		 * 
		 * GL11.glColor3d(0.0, 0.0, 1.0); GL11.glVertex3d(0.0, 0.0, 0.0);
		 * GL11.glVertex3d(0.0, 0.0, 1.0);
		 * 
		 * GL11.glEnd();
		 */
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

					if (demoMap.getTile(i, j).isHighlightedGreen() && !demoMap.getTile(i, j).isHighlighted()) {
						DrawHighlightG(demoMap.getTile(i, j));
					}

					if (demoMap.getTile(i, j).isHighlighted()) {
						DrawHighlight(demoMap.getTile(i, j));
					}

					for (Character c : charsToDRaw) {
						if (c.getCurrentTileX() == i && c.getCurrentTileY() == j) {
							DrawChar(c);
						}
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
					if (demoMap.getTile(i, j).isHighlightedGreen() && !demoMap.getTile(i, j).isHighlighted()) {
						DrawHighlightG(demoMap.getTile(i, j));
					}

					if (demoMap.getTile(i, j).isHighlighted()) {
						DrawHighlight(demoMap.getTile(i, j));
					}

					for (Character c : charsToDRaw) {
						if (c.getCurrentTileX() == i && c.getCurrentTileY() == j) {
							DrawChar(c);
						}
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
					if (demoMap.getTile(i, j).isHighlightedGreen() && !demoMap.getTile(i, j).isHighlighted()) {
						DrawHighlightG(demoMap.getTile(i, j));
					}

					if (demoMap.getTile(i, j).isHighlighted()) {
						DrawHighlight(demoMap.getTile(i, j));
					}

					for (Character c : charsToDRaw) {
						if (c.getCurrentTileX() == i && c.getCurrentTileY() == j) {
							DrawChar(c);
						}
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

					if (demoMap.getTile(i, j).isHighlightedRed()) {
						DrawHighlightR(demoMap.getTile(i, j));
					} else if (demoMap.getTile(i, j).isHighlighted()) {
						DrawHighlight(demoMap.getTile(i, j));
					} else if (demoMap.getTile(i, j).isHighlightedGreen()) {
						DrawHighlightG(demoMap.getTile(i, j));
					}

					for (Character c : charsToDRaw) {
						if (c.getCurrentTileX() == i && c.getCurrentTileY() == j) {
							DrawChar(c);
						}
					}
				}
			}
			break;
		}
	}

	private void DrawATile(Tile t) {
		GL11.glColor4f(1f, 1f, 1f, 1f);

		t.getTextureTop().bind();
		// SouthEast means X is constant.
		float x1 = (t.getPosY() * scale) - originY;
		float x2 = ((t.getPosY() + 1) * scale) - originY;

		float y = t.getHeight() * getZscale();

		float z1 = (t.getPosX() * scale) - originX;
		float z2 = ((t.getPosX() + 1) * scale) - originX;

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
		/*
		 * GL11.glColor3f(0f, 0f, 0f); GL11.glBegin(GL11.GL_LINE_LOOP);
		 * GL11.glVertex3d(x1, y, z1); GL11.glVertex3d(x2, y, z1);
		 * GL11.glVertex3d(x2, y, z2); GL11.glVertex3d(x1, y, z2); GL11.glEnd();
		 */
	}

	private void DrawTheLinkSE(Tile t1, Tile t2) {
		GL11.glColor4f(1f, 1f, 1f, 1f);
		t1.getTextureSE().bind();
		// SouthEast means X is constant.
		float x1 = (t2.getPosY() * scale) - originY;
		float x2 = x1;
		float x3 = x1;
		float x4 = x1;

		float y1 = t1.getHeight() * zscale;
		float y2 = y1;
		float y3 = t2.getHeight() * zscale;
		float y4 = y3;

		float z1 = ((t1.getPosX() + 1) * scale) - originX;
		float z2 = (t1.getPosX() * scale) - originX;
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
		/*
		 * GL11.glColor3f(0f, 0f, 0f); GL11.glBegin(GL11.GL_LINE_LOOP);
		 * GL11.glVertex3d(x1, y1, z1); GL11.glVertex3d(x2, y2, z2);
		 * GL11.glVertex3d(x3, y3, z3); GL11.glVertex3d(x4, y4, z4);
		 * GL11.glEnd();
		 */
	}

	private void DrawTheLinkSO(Tile t1, Tile t2) {
		GL11.glColor4f(1f, 1f, 1f, 1f);
		t1.getTextureSO().bind();
		// SouthWest means Z is constant.
		float x1 = (t1.getPosY() * scale) - originY;
		float x2 = ((t1.getPosY() + 1) * scale) - originY;
		float x3 = x2;
		float x4 = x1;

		float y1 = t1.getHeight() * zscale;
		float y2 = y1;
		float y3 = t2.getHeight() * zscale;
		float y4 = y3;

		float z1 = (t2.getPosX() * scale) - originX;
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
		/*
		 * GL11.glColor3f(0f, 0f, 0f); GL11.glBegin(GL11.GL_LINE_LOOP);
		 * GL11.glVertex3d(x1, y1, z1); GL11.glVertex3d(x2, y2, z2);
		 * GL11.glVertex3d(x3, y3, z3); GL11.glVertex3d(x4, y4, z4);
		 * GL11.glEnd();
		 */
	}

	private void DrawTheLinkNE(Tile t1, Tile t2) {
		GL11.glColor4f(1f, 1f, 1f, 1f);
		t1.getTextureNE().bind();
		// NorthEast means Z is constant.
		float x1 = (t1.getPosY() * scale) - originY;
		float x2 = ((t1.getPosY() + 1) * scale) - originY;
		float x3 = x2;
		float x4 = x1;

		float y1 = t1.getHeight() * zscale;
		float y2 = y1;
		float y3 = t2.getHeight() * zscale;
		float y4 = y3;

		float z1 = (t1.getPosX() * scale) - originX;
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
		/*
		 * GL11.glColor3f(0f, 0f, 0f); GL11.glBegin(GL11.GL_LINE_LOOP);
		 * GL11.glVertex3d(x1, y1, z1); GL11.glVertex3d(x2, y2, z2);
		 * GL11.glVertex3d(x3, y3, z3); GL11.glVertex3d(x4, y4, z4);
		 * GL11.glEnd();
		 */
	}

	private void DrawTheLinkNO(Tile t1, Tile t2) {
		GL11.glColor4f(1f, 1f, 1f, 1f);
		t1.getTextureNO().bind();
		// NorthWest means X is constant.
		float x1 = (t1.getPosY() * scale) - originY;
		float x2 = x1;
		float x3 = x1;
		float x4 = x1;

		float y1 = t1.getHeight() * zscale;
		float y2 = y1;
		float y3 = t2.getHeight() * zscale;
		float y4 = y3;

		float z1 = ((t1.getPosX() + 1) * scale) - originX;
		float z2 = (t1.getPosX() * scale) - originX;
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
		/*
		 * GL11.glColor3f(0f, 0f, 0f); GL11.glBegin(GL11.GL_LINE_LOOP);
		 * GL11.glVertex3d(x1, y1, z1); GL11.glVertex3d(x2, y2, z2);
		 * GL11.glVertex3d(x3, y3, z3); GL11.glVertex3d(x4, y4, z4);
		 * GL11.glEnd();
		 */
	}

	public void DrawHighlight(Tile t) {

		GL11.glColor4f(1f, 1f, 1f, 0.4f);
		highlight.bind();

		float x1 = ((float) t.getPosY() * scale) - originY;
		float x2 = (((float) t.getPosY() + 1) * scale) - originY;

		float y1 = ((float) t.getPosX() * scale) - originX;
		float y2 = (((float) t.getPosX() + 1) * scale) - originX;

		float z1 = ((((float) t.getHeight() + 0.1f) * zscale));

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

	public void DrawHighlightG(Tile t) {

		GL11.glColor4f(1f, 1f, 1f, 0.6f);
		highlightG.bind();

		float x1 = ((float) t.getPosY() * scale) - originY;
		float x2 = (((float) t.getPosY() + 1) * scale) - originY;

		float y1 = ((float) t.getPosX() * scale) - originX;
		float y2 = (((float) t.getPosX() + 1) * scale) - originX;

		float z1 = ((((float) t.getHeight() + 0.1f) * zscale));

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

	public void DrawHighlightR(Tile t) {

		GL11.glColor4f(1f, 1f, 1f, 0.6f);
		highlightR.bind();

		float x1 = ((float) t.getPosY() * scale) - originY;
		float x2 = (((float) t.getPosY() + 1) * scale) - originY;

		float y1 = ((float) t.getPosX() * scale) - originX;
		float y2 = (((float) t.getPosX() + 1) * scale) - originX;

		float z1 = ((((float) t.getHeight() + 0.1f) * zscale));

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

	public void DrawChar(Character c) {

		GL11.glColor4f(1f, 1f, 1f, 1f);
		cara.bind();

		float x1 = (c.getPosY() * scale) - originY;
		float x2 = ((c.getPosY() + 1) * scale) - originY;

		float y1 = (c.getPosX() * scale) - originX;
		float y2 = ((c.getPosX() + 1) * scale) - originX;

		float z1 = (c.getPosZ() * zscale);
		float z2 = ((c.getPosZ() + 4.f) * zscale);

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2d(0, 0);
		GL11.glVertex3d(x1, z2, y2);
		GL11.glTexCoord2d(1, 0);
		GL11.glVertex3d(x2, z2, y1);
		GL11.glTexCoord2d(1, 1);
		GL11.glVertex3d(x2, z1, y1);
		GL11.glTexCoord2d(0, 1);
		GL11.glVertex3d(x1, z1, y2);
		GL11.glEnd();

	}

	private void LoadTextures() {
		try {
			imageHerbe = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("images/fleur1.png"));
			highlight = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("images/highlightblue.PNG"));
			highlightG = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("images/highlightgreen.PNG"));
			highlightR = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("images/highlightred.PNG"));
			cara = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("images/perso1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean RequestFocusOn(int X, int Y, int Z) {
		if (isBusy) {
			return false;
		} else {
			focusXToGo = (float) Math.round((X - currentTileOnFocusX) * (scale * 100)) / 100;
			focusYToGo = (float) Math.round((Y - currentTileOnFocusY) * (scale * 100)) / 100;
			focusZToGo = (float) Math.round((Z - currentTileOnFocusZ) * (zscale * 100)) / 100;
			currentTileOnFocusX = X;
			currentTileOnFocusY = Y;
			currentTileOnFocusZ = Z;
			this.isBusy = true;
			return true;
		}
	}

	public void SetFocusOnNoWait(int X, int Y, int Z) {
		float x = (float) Math.round((X - currentTileOnFocusX) * (scale * 100)) / 100;
		float y = (float) Math.round((Y - currentTileOnFocusY) * (scale * 100)) / 100;
		float z = (float) Math.round((Z - currentTileOnFocusZ) * (zscale * 100)) / 100;
		GL11.glTranslatef(y, z, x);
		currentTileOnFocusX = X;
		currentTileOnFocusY = Y;
		currentTileOnFocusZ = Z;
	}

	public boolean RequestView(viewPoint v) {
		if (isBusy) {
			return false;
		} else {
			if (!v.equals(currentView)) {
				this.isBusy = true;
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

	private void CheckLogic() {
		if (isBusy) {
			focusXToGo = (float) Math.round(focusXToGo * 100) / 100;
			focusYToGo = (float) Math.round(focusYToGo * 100) / 100;
			focusZToGo = (float) Math.round(focusZToGo * 100) / 100;
			rotationToGo = (float) Math.round(rotationToGo * 100) / 100;
			float step = 0.05f;
			if (focusXToGo != 0) {
				if (focusXToGo < 0) {
					originX -= step;
					focusXToGo += step;
				} else if (focusXToGo > 0) {
					originX += step;
					focusXToGo -= step;
				}
			}
			if (focusYToGo != 0) {
				if (focusYToGo < 0) {
					originY -= step;
					focusYToGo += step;
				} else if (focusYToGo > 0) {
					originY += step;
					focusYToGo -= step;
				}
			}
			if (focusZToGo != 0) {
				focusZToGo = 0;
				/*
				 * if (focusZToGo < 0) { if (-step < focusZToGo) {
				 * GL11.glTranslated(0, -focusZToGo, 0); focusZToGo = 0; } else
				 * { GL11.glTranslated(0, -step, 0); focusZToGo += step; } }
				 * else if (focusZToGo > 0) { if (step > focusZToGo) {
				 * GL11.glTranslated(0, focusZToGo, 0); focusZToGo = 0; } else {
				 * GL11.glTranslated(0, step, 0); focusZToGo -= step; } }
				 */
			}
			/*
			 * if (scaleToGo != 0) { if (scaleToGo < scale) { scale -= 0.001f; }
			 * if (scaleToGo > scale) { scale += 0.001f; } }
			 */
			if (rotationToGo != 0) {
				double a = Math.sin(Math.PI / 180) * (1 / 2);
				double b = Math.sin(Math.PI / 180) * (1 / 2);

				if (rotationToGo < 0) {
					GL11.glRotatef(1f, 0, 1, 0);
					GL11.glTranslated(-b, 0, a);
					UpdateLights();
					rotationToGo += 1f;
				}
				if (rotationToGo > 0) {
					GL11.glRotatef(-1f, 0, 1, 0);
					GL11.glTranslated(b, 0, -a);
					UpdateLights();
					rotationToGo -= 1f;
				}
				if (rotationToGo == -45f || rotationToGo == -135f || rotationToGo == -225f || rotationToGo == -315f) {
					switch (currentView) {
					case South:
						currentView = viewPoint.West;
						break;
					case West:
						currentView = viewPoint.North;
						break;
					case North:
						currentView = viewPoint.East;
						break;
					case East:
						currentView = viewPoint.South;
						break;
					}
				}
				if (rotationToGo == 45f || rotationToGo == 135f || rotationToGo == 225f || rotationToGo == 315f) {
					switch (currentView) {
					case South:
						currentView = viewPoint.East;
						break;
					case West:
						currentView = viewPoint.South;
						break;
					case North:
						currentView = viewPoint.West;
						break;
					case East:
						currentView = viewPoint.North;
						break;
					}
				}
			}

			if (focusXToGo == 0 && focusYToGo == 0 && focusZToGo == 0 && rotationToGo == 0) {
				this.isBusy = false;
			}
		}

	}

	private void SetupLigths() {
		FloatBuffer matSpecular;
		FloatBuffer lightPosition;
		FloatBuffer whiteLight;
		FloatBuffer lModelAmbient;

		matSpecular = BufferUtils.createFloatBuffer(4);
		matSpecular.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();

		lightPosition = BufferUtils.createFloatBuffer(4);
		lightPosition.put(0.5f).put(0.5f).put(5.0f).put(0.2f).flip();

		whiteLight = BufferUtils.createFloatBuffer(4);
		whiteLight.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();

		lModelAmbient = BufferUtils.createFloatBuffer(4);
		lModelAmbient.put(0.5f).put(0.5f).put(0.5f).put(1.0f).flip();

		GL11.glShadeModel(GL11.GL_SMOOTH);

		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, lightPosition);
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_SPECULAR, whiteLight);
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, whiteLight);
		GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, lModelAmbient);

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_LIGHT0);

	}

	private void UpdateLights() {
		FloatBuffer lightPosition;
		lightPosition = BufferUtils.createFloatBuffer(4);
		lightPosition.put(0.5f).put(0.5f).put(5.0f).put(0.2f).flip();
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, lightPosition);
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

	public boolean isBusy() {
		return isBusy;
	}

	public viewPoint getCurrentView() {
		return currentView;
	}

	public ArrayList<Character> getCharsToDRaw() {
		return charsToDRaw;
	}

	public void setCharsToDRaw(ArrayList<Character> charsToDRaw) {
		this.charsToDRaw = charsToDRaw;
	}
}
