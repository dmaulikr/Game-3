package displaymanager;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import entity.Map;

public class DisplayManager {

	private int height;
	private int width;
	boolean requestClose;

	GameboardRender gameRender;
	HUD hud;

	public DisplayManager(int width, int height, Map map) {
		this.height = height;
		this.width = width;
		this.requestClose = false;
		this.gameRender = new GameboardRender(map);
		this.hud = new HUD(this.width, this.height);
		try {
			Display.setDisplayMode(new DisplayMode(this.width, this.height));
			Display.setSwapInterval(1);
			Display.sync(60);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void Init() {
		GL11.glViewport(0, 0, this.width, this.height);
		GL11.glDepthRange(0, 1000);
		gameRender.Init();
		hud.Init();
		Ready3D();
	}

	public void run() {
		while (!requestClose) {
			Render();
		}
		Display.destroy();
	}

	public void Render() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		requestClose = Display.isCloseRequested();
		if (Display.isVisible()) {
			Render3D();
			Render2D();
		}
		Display.update();
		Display.sync(60);
	}

	public boolean isRequestClose() {
		return requestClose;
	}

	private void Render3D() {
		// Ready3D();
		make3D();
		gameRender.Render();
	}

	private void Render2D() {
		// Ready2D();
		make2D();
		hud.Render();
	}

	private void make2D() {
		// Remove the Z axis
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		GL11.glOrtho(0, this.width, this.height, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
	}

	private void make3D() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPopMatrix();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	private void Ready3D() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);

		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();

		GL11.glOrtho(-1, 1, -1, 1, -10, 1000);

		GL11.glRotatef((float) Math.toDegrees(Math.atan(0.5)), 1, 0, 0);
		GL11.glRotatef(-45.0f, 0, 1, 0);
		GL11.glTranslatef(0, -0.5f, 0);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
	}

	public void Clean() {
		Display.destroy();
	}

	public GameboardRender getGameBoard() {
		return this.gameRender;
	}

	public HUD getHUD() {
		return this.hud;
	}

	public static void main(String[] argv) {
		Map map = new Map(10, 15, "lolilol");
		map.getTile(2, 2).setHeight(2);
		DisplayManager display = new DisplayManager(800, 600, map);
		display.Init();
		display.run();
	}
}
