package gamemanager;

import javax.net.ssl.SSLEngineResult.Status;

import jobs.Warrior;

import inputmanager.InputManager;
import inputmanager.InputManager.actions;
import entity.Job.jobList;
import displaymanager.DisplayManager;
import displaymanager.DisplayManager.viewPoint;
import entity.Map;
import entity.Player;
import entity.Tile;
import entity.TileTexture;
import entity.Character;
import entity.Character.*;

public class Game {

	enum GameStatus {
		PlacingBeforeBattle, Pending, InCharMenu, MoveSelection, TargetSelection, ExploringMap
	}

	Map map;
	DisplayManager dm;
	InputManager im;
	GameStatus state;

	Player[] players;
	Player currentPlayer;
	int indexPlayer;
	int indexChar;
	int nbPlayers;

	int charIndex;
	Character currentChar;

	int cursorX;
	int cursorY;

	boolean quit;

	boolean charPlaced;
	int deployementcountDown;
	int playerCountDown;

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

	private void UpdateLogic() {
		switch (state) {

		case PlacingBeforeBattle:
			if (charPlaced) {
				if (deployementcountDown > 0) {
					deployementcountDown--;
					indexChar++;
					if (indexChar < players[indexPlayer].getChars().length) {
						currentChar = players[indexPlayer].getChars()[indexChar];
					}
				}
				if (deployementcountDown == 0) {
					if (playerCountDown > 0) {
						playerCountDown--;
						if (indexPlayer < players.length - 1) {
							indexPlayer++;
							currentPlayer = players[indexPlayer];
							deployementcountDown = currentPlayer.getChars().length;
							indexChar = 0;
							currentChar = players[indexPlayer].getChars()[indexChar];
							map.LightUpStartZone(indexPlayer + 1);
						}
					}
					if (playerCountDown == 0) {
						state = GameStatus.Pending;
					}
				}

				charPlaced = false;
			}
			break;

		case Pending:
			break;

		}
	}

	public void setPlayer(int i, Player player) {
		players[i] = player;
	}

	private void initPlacingBeforeBattle() {
		this.indexPlayer = 0;
		this.currentPlayer = players[this.indexPlayer];
		this.indexChar = 0;
		this.currentChar = currentPlayer.getChars()[this.indexChar];
		this.playerCountDown = players.length;
		this.deployementcountDown = currentPlayer.getChars().length;
		this.charPlaced = false;
		this.map.LightUpStartZone(indexPlayer + 1);

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
		initPlacingBeforeBattle();
		while (!dm.isRequestClose() && !quit) {
			if (!dm.isBusy()) {
				manageKeyInput(im.getInputs());
				UpdateLogic();
			}
			dm.Update();
		}
		dm.Clean();
	}

	private void PlaceChar() {
		if (map.getTile(cursorX, cursorY).getDeploymentZone() == this.indexPlayer + 1) {
			currentChar.setCurrentTileX(cursorX);
			currentChar.setCurrentTileY(cursorY);
			currentChar.setHeight(map.getTile(cursorX, cursorY).getHeight());
			charPlaced = true;
			dm.getCharsToDRaw().add(players[this.indexPlayer].getChars()[indexChar]);
		}
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
					PlaceChar();
					break;
				default:
					break;
				}
				break;
			case Pending:
				switch (act) {
				case QUIT:
					quit = true;
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
		Character c1 = new Character(Race.Human, Gender.Male);
		Character c2 = new Character(Race.Human, Gender.Male);
		Character c3 = new Character(Race.Human, Gender.Male);
		Character c4 = new Character(Race.Human, Gender.Male);
		Character c5 = new Character(Race.Human, Gender.Male);
		Player p1 = new Player("bobyx", new Character[] { c1, c2 });
		Player p2 = new Player("bobyxou", new Character[] { c3, c4, c5 });

		map.getTile(0, 0).setDeploymentZone(1);
		map.getTile(0, 1).setHeight(4);
		map.getTile(0, 1).setDeploymentZone(1);
		map.getTile(0, 2).setHeight(5);
		map.getTile(0, 2).setDeploymentZone(1);
		map.getTile(0, 3).setDeploymentZone(1);

		map.getTile(2, 0).setDeploymentZone(2);
		map.getTile(2, 0).setHeight(4);
		map.getTile(2, 1).setDeploymentZone(2);
		map.getTile(2, 1).setHeight(5);
		map.getTile(2, 2).setDeploymentZone(2);
		map.getTile(2, 2).setHeight(6);
		map.getTile(2, 3).setDeploymentZone(2);
		map.getTile(2, 3).setHeight(7);

		Game g = new Game(map, 1);
		g.setPlayer(0, p1);
		g.setPlayer(1, p2);
		g.run();

	}
}
