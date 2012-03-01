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

public class DemoDisplay2 {
	private Map demoMap;
	private float scale;

	float angleX = (float) Math.toDegrees(Math.atan(0.5)); // 26,565
	float angleY = -45.0f;

	float size = 0.2f;

	Texture tex;

	public DemoDisplay2(Map demoMap) {
		setDemoMap(demoMap);
		float a = 1f / (float) demoMap.getLength();
		float b = 1f / (float) demoMap.getWidth();
		setScale(Math.min(a, b));
	}

	public void start() {
		try {
			Display.setDisplayMode(new DisplayMode(1000, 900));
			Display.setSwapInterval(1);
			Display.sync(60);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		// init OpenGL here
		GL11.glViewport(0, 0, 1000, 900);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();

		
		GL11.glRotatef(angleX, 1, 0, 0); // 26,565
		GL11.glRotatef(angleY, 0, 1, 0); // -45
		
		GL11.glTranslated(-0.6f, -0.6f, -0.6f);

		//
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();

		while (!Display.isCloseRequested()) {

			Update();
			if (Display.isVisible()) {
				Draw();
			}
			Display.update();
		}

		Display.destroy();
	}

	public static void main(String[] argv) {
		DemoDisplay2 display = new DemoDisplay2(new Map(5, 5, "lolilol"));
		display.start();
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
			}
		}
	}

	private void DrawATile(Tile t) {
		SelectColor(t);
		GL11.glBegin(GL11.GL_QUADS);
		
		GL11.glVertex3d(t.getPosY() * scale, t.getHeight() * scale/5, t.getPosX()* scale);
		GL11.glVertex3d(t.getPosY() * scale, t.getHeight() * scale/5,(t.getPosX() + 1) * scale);
		GL11.glVertex3d((t.getPosY() + 1) * scale, t.getHeight() * scale/5,(t.getPosX() + 1) * scale);
		GL11.glVertex3d((t.getPosY() + 1) * scale, t.getHeight() * scale/5,t.getPosX() * scale);

		GL11.glEnd();
		
		GL11.glColor3f(0f, 0f, 0f);
		GL11.glBegin(GL11.GL_LINE_LOOP);
		
		GL11.glVertex3d(t.getPosY() * scale, t.getHeight() * scale/5, t.getPosX()* scale);
		GL11.glVertex3d(t.getPosY() * scale, t.getHeight() * scale/5,(t.getPosX() + 1) * scale);
		GL11.glVertex3d((t.getPosY() + 1) * scale, t.getHeight() * scale/5,(t.getPosX() + 1) * scale);
		GL11.glVertex3d((t.getPosY() + 1) * scale, t.getHeight() * scale/5,t.getPosX() * scale);

		GL11.glEnd();
	}

	private void SelectColor(Tile tile) {
		switch (tile.getTexture()) {
		case Grass:
			GL11.glColor3f(0.18f, 0.5f, 0.17f);
			break;

		case Earth:
			GL11.glColor3f(0.5f, 0.25f, 0f);
			break;

		case Sand:
			GL11.glColor3f(1f, 1f, 0.5f);
			break;

		case Stone:
			GL11.glColor3f(0.5f, 0.5f, 0.5f);
			break;

		default:
			GL11.glColor3f(0.5f, 0.5f, 1.0f);
			break;
		}
	}

	private void Update() {
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			size += 0.01f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			size -= 0.01f;
		}

	}

}
