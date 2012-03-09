package inputmanager;

import org.lwjgl.input.Keyboard;

public class InputManager {

	public enum actions {
		VIEW_SOUTH, VIEW_NORTH, VIEW_EAST, VIEW_WEST,

		UP, DOWN, LEFT, RIGHT,

		QUIT, ENTER,

		PLUS,MINUS,
		
		none
	}

	public InputManager() {

	}

	public actions getInputs() {
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_F1) {
					return actions.VIEW_SOUTH;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_F2) {
					return actions.VIEW_WEST;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_F3) {
					return actions.VIEW_NORTH;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_F4) {
					return actions.VIEW_EAST;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_UP) {
					return actions.UP;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
					return actions.DOWN;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_LEFT) {
					return actions.LEFT;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT) {
					return actions.RIGHT;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
					return actions.QUIT;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_RETURN) {
					return actions.ENTER;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_P) {
					return actions.PLUS;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_O) {
					return actions.MINUS;
				}
			}
		}
		return actions.none;
	}

}
