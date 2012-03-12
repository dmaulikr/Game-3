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

	Player[] players;
	Player currentPlayer;
	int nbPlayers;

	int cursorX;
	int cursorY;

	boolean quit;

	public Game(Map map, int nbPlayers) {
		this.map = map;
		dm = new DisplayManager(map);
		this.dm.init();
		TileTexture tt = new TileTexture();
		tt.LoadBundles(map.getAllTextureTypes());
		this.map.BindTextures(tt);
		this.im = new InputManager();
		this.cursorX = 0;
		this.cursorY = 0;
		this.quit = false;
		this.map.getTile(0, 0).setHighlighted(true);
		this.state = GameStatus.PlacingBeforeBattle;
		this.nbPlayers = nbPlayers;
		this.players = new Player[this.nbPlayers];
		this.currentPlayer = null;
	}

	public void setPlayer(int i, Player player) {
		players[i] = player;
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
				dm.RequestFocusOn(cursorX, cursorY, map.getTile(cursorX, cursorY).getHeight());
			}
		}
	}

	public void run() {
		while (!dm.isRequestClose() && !quit) {
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

		Game g = new Game(map, 2);
		g.run();

	}

}
