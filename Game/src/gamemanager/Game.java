package gamemanager;

import javax.net.ssl.SSLEngineResult.Status;

import inputmanager.InputManager;
import inputmanager.InputManager.actions;

import displaymanager.DisplayManager;
import displaymanager.DisplayManager.viewPoint;
import entity.Map;
import entity.Player;
import entity.Tile;
import entity.TileTexture;

public class Game {

	enum GameStatus {
		PlacingBeforeBattle, InCharMenu, MoveSelection, TargetSelection, ExploringMap
	}

	Map map;
	DisplayManager dm;
	InputManager im;
	GameStatus state;
	Player Player1;
	Player Player2;

	int cursorX;
	int cursorY;

	boolean quit;

	public Game(Map map, DisplayManager dm) {
		this.map = map;
		this.dm = dm;
		dm.init();
		im = new InputManager();
		cursorX = 0;
		cursorY = 0;
		quit = false;
		map.getTile(0, 0).setHighlighted(true);
		state=GameStatus.PlacingBeforeBattle;
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
				dm.RequestFocusOn(cursorX, cursorY,
						map.getTile(cursorX, cursorY).getHeight());
			}
		}
	}

	public void run() {
		while (!dm.isRequestClose() && !quit) {
			map.LightUpPossibleMovement(5, 5, 3);
			if (!dm.isBusy()) {
				manageKeyInput(im.getInputs());
			}
			dm.Update();
		}
		dm.Clean();
	}

	private void manageKeyInput(actions act) {
		if (!act.equals(actions.none)) {
			switch (state) {

			case PlacingBeforeBattle:
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
					quit = true;
					break;

				default:
					break;
				}
				break;

			case InCharMenu:
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
					quit = true;
					break;

				default:
					break;
				}
				break;

			case MoveSelection:
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
					quit = true;
					break;

				default:
					break;
				}
				break;

			case TargetSelection:
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
					quit = true;
					break;

				default:
					break;
				}
				break;

			case ExploringMap:
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
					quit = true;
					break;

				default:
					break;
				}
				break;

			default:
				break;
			}
		}
	}

	public static void main(String[] argv) {

		Map map = new Map(10, 10, "lolilol");
		DisplayManager dm = new DisplayManager(map);
		TileTexture tt = new TileTexture();
		tt.LoadBundles(map.getAllTextureTypes());
		map.BindTextures(tt);
		
		Game g = new Game(map, dm);
		g.run();

	}

}
