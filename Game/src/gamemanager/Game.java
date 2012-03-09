package gamemanager;

import inputmanager.InputManager;
import inputmanager.InputManager.actions;

import displaymanager.DisplayManager;
import displaymanager.DisplayManager.viewPoint;
import entity.Map;
import entity.Player;
import entity.Tile;

public class Game {

	Map map;
	DisplayManager dm;
	InputManager im;

	Player Player1;
	Player Player2;

	int cursorX;
	int cursorY;

	public Game(Map map, DisplayManager dm) {
		this.map = map;
		this.dm = dm;
		dm.init();
		im = new InputManager();
		cursorX = 0;
		cursorY = 0;
		map.getTile(0, 0).setHighlighted(true);
	}

	private void goUp() {
		switch (dm.getCurrentView()) {
		case South:
			if (cursorX > 0) {
				cursorX--;
			}
			break;
		case West:
			if (cursorY < map.getWidth() - 1) {
				cursorY++;
			}
			break;
		case North:
			if (cursorX < map.getLength() - 1) {
				cursorX++;
			}
			break;
		case East:
			if (cursorY > 0) {
				cursorY--;
			}
			break;
		}
		UpdateCursor();
	}

	private void goDown() {
		switch (dm.getCurrentView()) {
		case South:
			if (cursorX < map.getLength() - 1) {
				cursorX++;
			}
			break;
		case West:
			if (cursorY > 0) {
				cursorY--;
			}
			break;
		case North:
			if (cursorX > 0) {
				cursorX--;
			}
			break;
		case East:
			if (cursorY < map.getWidth() - 1) {
				cursorY++;
			}
			break;
		}
		UpdateCursor();
	}

	private void goLeft() {
		switch (dm.getCurrentView()) {
		case South:
			if (cursorY > 0) {
				cursorY--;
			}
			break;
		case West:
			if (cursorX > 0) {
				cursorX--;
			}
			break;
		case North:
			if (cursorY < map.getWidth() - 1) {
				cursorY++;
			}
			break;
		case East:
			if (cursorX < map.getLength() - 1) {
				cursorX++;
			}
			break;
		}
		UpdateCursor();
	}

	private void goRigth() {
		switch (dm.getCurrentView()) {
		case South:
			if (cursorY < map.getWidth() - 1) {
				cursorY++;
			}
			
			break;
		case West:
			if (cursorX < map.getLength() - 1) {
				cursorX++;
			}
			
			break;
		case North:
			if (cursorY > 0) {
				cursorY--;
			}
			
			break;
		case East:
			if (cursorX > 0) {
				cursorX--;
			}
			
			break;
		}
		UpdateCursor();
	}

	public void UpdateCursor() {
		for (int i = 0; i < map.getLength(); i++) {
			for (int j = 0; j < map.getWidth(); j++) {
				if (i == cursorX && j == cursorY) {
					map.getTile(i, j).setHighlighted(true);
				} else {
					map.getTile(i, j).setHighlighted(false);
				}
			}
		}
	}

	public void run() {

		boolean quit = false;
		while (!dm.isRequestClose() && !quit) {
			actions act = im.getInputs();
			if (!act.equals(actions.none)) {
				switch (act) {
				case VIEW_SOUTH:
					dm.RequestView(viewPoint.South);
					break;
				case VIEW_NORTH:
					dm.RequestView(viewPoint.North);
					break;
				case VIEW_EAST:
					dm.RequestView(viewPoint.East);
					break;
				case VIEW_WEST:
					dm.RequestView(viewPoint.West);
					break;
				case UP:
					goUp();
					break;
				case DOWN:
					goDown();
					break;
				case LEFT:
					goLeft();
					break;
				case RIGHT:
					goRigth();
					break;
				case QUIT:
					quit = true;
					break;
				case ENTER:

					break;

				default:
					break;
				}
			}
			dm.Update();
		}
	}

	public static void main(String[] argv) {

		Map map = new Map(10, 10, "lolilol");
		DisplayManager dm = new DisplayManager(map);

		Game g = new Game(map, dm);
		g.run();

	}

}
