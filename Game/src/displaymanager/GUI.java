package displaymanager;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import entity.TileTexture;

public class GUI {

	public GUI(){
		
	}
	
	public void Init() {

	}
	
	public void Render() {
		GL11.glColor3f(1f, .5f, 1f);
		// draw quad
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(100, 100);
		GL11.glVertex2f(100 + 200, 100);
		GL11.glVertex2f(100 + 200, 100 + 200);
		GL11.glVertex2f(100, 100 + 200);
		GL11.glVertex2f(0, 0);
		GL11.glVertex2f(1, 0);
		GL11.glVertex2f(1, 1);
		GL11.glVertex2f(0, 1);
		GL11.glEnd();
	}
}
