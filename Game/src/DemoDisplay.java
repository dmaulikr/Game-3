import java.awt.Color;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class DemoDisplay {
	private Map demoMap;
	int scale =15;
	public DemoDisplay(Map demoMap) {
		setDemoMap(demoMap);
	}

	public void start() {
		try {
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		// init OpenGL here
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 800, 0, 600, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		while (!Display.isCloseRequested()) {

			// render OpenGL here

			// Clear the screen and depth buffer
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			int startX=100+(4*scale*(demoMap.getLength()));
			int startY=500;
			
			for (int i = 0; i < demoMap.getLength(); i++) {
				for (int j = 0; j < demoMap.getWidth(); j++) {
					DrawTile(demoMap.getTile(i,j), startX,startY);
					startX+=4*scale;
				}
				startX=100+(4*scale*(demoMap.getLength()-(i)))+(i-3)*scale;
				startY-=3*scale;
			}
			
			

			Display.update();
		}

		Display.destroy();
	}

	public static void main(String[] argv) {
		DemoDisplay display = new DemoDisplay(new Map(5, 5, "lolilol"));
		display.start();
	}

	public Map getDemoMap() {
		return demoMap;
	}

	public void setDemoMap(Map demoMap) {
		this.demoMap = demoMap;
	}

	public void DrawTile(Tile tile, int startX, int startY) {
		SelectColor(tile);
		int internalY = startY+tile.getHeight();	
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(startX,internalY);
		GL11.glVertex2f(startX+4*scale,internalY);
		GL11.glVertex2f(startX+scale,internalY-3*scale);
		GL11.glVertex2f(startX-3*scale,internalY-3*scale);
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
	
	

}