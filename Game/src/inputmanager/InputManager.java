package inputmanager;

import org.lwjgl.input.Keyboard;

public class InputManager {

	boolean upPressed;
	boolean downPressed;
	boolean leftPressed;
	boolean rightPressed;

	int timer;
	int timerInitialValue;

	public enum actions {
		VIEW_SOUTH, VIEW_NORTH, VIEW_EAST, VIEW_WEST,

		UP, DOWN, LEFT, RIGHT,

		QUIT, ENTER,

		PLUS, MINUS,

		none
	}

	public InputManager() {
		timer = 10;
		upPressed = false;
		downPressed = false;
		leftPressed = false;
		rightPressed = false;
		timerInitialValue = 10;
		timer = timerInitialValue;
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
					upPressed = true;
					timer = timerInitialValue;
					return actions.UP;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
					downPressed = true;
					timer = timerInitialValue;
					return actions.DOWN;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_LEFT) {
					leftPressed = true;
					timer = timerInitialValue;
					return actions.LEFT;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT) {
					rightPressed = true;
					timer = timerInitialValue;
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
		if (upPressed) {
			if (timer == 0) {
				if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
					return actions.UP;
				} else {
					upPressed = false;
					timer = timerInitialValue;
				}
			} else {
				timer--;
			}
		}
		if (downPressed) {
			if (timer == 0) {
				if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
					return actions.DOWN;
				} else {
					downPressed = false;
					timer = timerInitialValue;
				}
			} else {
				timer--;
			}
		}
		if (leftPressed) {
			if (timer == 0) {
				if (!Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
					leftPressed = false;
					timer = timerInitialValue;
				} else {
					return actions.LEFT;
				}
			} else {
				timer--;
			}
		}
		if (rightPressed) {
			if (timer == 0) {
				if (!Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
					rightPressed = false;
					timer = timerInitialValue;
				} else {
					return actions.RIGHT;
				}
			} else {
				timer--;
			}

		}
		return actions.none;
	}

}
