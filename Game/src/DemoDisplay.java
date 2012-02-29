import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class DemoDisplay {
	public void start() {
		try {
			Display.setDisplayMode(new DisplayMode(800,600));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		// init OpenGL here
		
		while (!Display.isCloseRequested()) {
			
			// render OpenGL here
			
			Display.update();
		}
		
		Display.destroy();
	}
	
	public static void main(String[] argv) {
		DemoDisplay display = new DemoDisplay();
		display.start();
	}
}