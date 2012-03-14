package displaymanager;

import java.awt.Font;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import entity.TileTexture;

public class HUD {
	private int height;
	private int width;

	public HUD(int height, int width) {
		this.height = height;
		this.width = width;

	}

	public void Init() {

	}

	public void Render() {
		GL11.glColor3f(1f, .5f, 1f);
		// draw quad
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(0, height);
		GL11.glVertex2f(width, height);
		GL11.glVertex2f(width, height - 200);
		GL11.glVertex2f(0, height - 200);
		GL11.glEnd();
	}
}
