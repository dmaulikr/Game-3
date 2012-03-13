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

	public DisplayManager(int height, int width, Map map) {
		this.height = height;
		this.width = width;
		this.requestClose = false;
		this.gameRender = new GameboardRender(map);
		try {
			Display.setDisplayMode(new DisplayMode(this.height, this.width));
			Display.setSwapInterval(1);
			Display.sync(60);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void init() {
		GL11.glViewport(0, 0, 800, 600);
		GL11.glDepthRange(0, 1000);
		Ready2D();
		Ready3D();
		gameRender.init();
	}

	public void run() {
		while (!requestClose) {
			Render();
		}
		Display.destroy();
	}

	public void Render() {
		requestClose = Display.isCloseRequested();
		if (Display.isVisible()) {
			Render3D();
			Render2D();
		}
		Display.update();
	}

	public boolean isRequestClose() {
		return requestClose;
	}

	private void Render3D() {
		Ready3D();
		gameRender.Render();
	}

	private void Render2D() {
		Ready2D();
	}

	private void Ready3D() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);

		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();

		GL11.glOrtho(-1, 1, -1, 1, -10, 1000);

		GL11.glRotatef((float) Math.toDegrees(Math.atan(0.6)), 1, 0, 0); // 26,565
		GL11.glRotatef(-45.0f, 0, 1, 0); // -45

		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
	}

	private void Ready2D() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);

		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();

		GL11.glOrtho(0, width, 0, height, -1, 1);

		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
	}

	public void Clean() {
		Display.destroy();
	}

	public GameboardRender getGameBoard() {
		return this.gameRender;
	}

	public static void main(String[] argv) {
		Map map = new Map(20, 25, "lolilol");
		map.getTile(2, 2).setHeight(25);
		DisplayManager display = new DisplayManager(800, 600, map);
		display.init();
		display.run();
	}
}
