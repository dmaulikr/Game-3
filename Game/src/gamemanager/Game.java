package gamemanager;

import javax.net.ssl.SSLEngineResult.Status;

import org.lwjgl.Sys;

import jobs.Warrior;

import inputmanager.InputManager;
import inputmanager.InputManager.actions;
import entity.Job.jobList;
import displaymanager.DisplayManager;
import displaymanager.GameboardRender.viewPoint;
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

	enum Menus {
		Actions
	}

	String[] actionMenuString = { "Move", "Attack", "Comp", "End Turn" };
	Menus actualMenu;
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

	private long timerTicksPerSecond = Sys.getTimerResolution();
	private long lastLoopTime;

	public Game(int width, int height, Map map, int nbPlayers) {
		this.map = map;
		dm = new DisplayManager(width, height, map);
		this.dm.Init();
		this.im = new InputManager();
		this.cursorX = 0;
		this.cursorY = 0;
		this.quit = false;
		this.map.getTile(0, 0).setHighlighted(true);
		this.state = GameStatus.PlacingBeforeBattle;
		this.nbPlayers = nbPlayers;
		this.players = new Player[this.nbPlayers];
		this.currentPlayer = null;
		lastLoopTime = getTime();
		if (nbPlayers == 0) {
			this.state = GameStatus.ExploringMap;
		}
	}

	private void UpdateLogic() {

		long delta = getTime() - lastLoopTime;
		lastLoopTime = getTime();
		for (Player p : players) {
			for (Character c : p.getChars()) {
				c.Update(delta, map);
			}
		}
		switch (state) {

		case PlacingBeforeBattle:
			if (charPlaced) {
				if (deployementcountDown > 0) {
					deployementcountDown--;
					indexChar++;
					if (indexChar < players[indexPlayer].getChars().length) {
						currentChar = players[indexPlayer].getChars()[indexChar];
						this.dm.getHUD().SetCurrentChar(currentChar);
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
							this.dm.getHUD().SetCurrentChar(currentChar);
							map.LightUpStartZone(indexPlayer + 1);
						}
					}
					if (playerCountDown == 0) {
						state = GameStatus.Pending;
						map.CleanLightUpZones();
					}
				}

				charPlaced = false;
			}
			break;

		case Pending:
			if (!GetTheCharToPlay()) {
				HourglassTick();
			} else {
				dm.getHUD().getContextMenu().setMenu(actionMenuString);
				actualMenu = Menus.Actions;
				state = GameStatus.InCharMenu;
			}
			break;
		case InCharMenu:
			dm.getHUD().getContextMenu().setShow(true);
			if (currentChar.hasMoved()) {
				dm.getHUD().getContextMenu().DisableOption(0);
			}
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
		this.dm.getHUD().SetCurrentChar(currentChar);
		this.playerCountDown = players.length;
		this.deployementcountDown = currentPlayer.getChars().length;
		this.charPlaced = false;
		this.map.LightUpStartZone(indexPlayer + 1);

	}

	public boolean GetTheCharToPlay() {
		for (Player p : players) {
			for (Character c : p.getChars()) {
				if (c.isReadyToPlay()) {
					cursorX = c.getCurrentTileX();
					cursorY = c.getCurrentTileY();
					currentPlayer = p;
					currentChar = c;
					this.dm.getHUD().SetCurrentChar(currentChar);
					c.setReadyToPlay(false);
					UpdateCursor();
					return true;
				}
			}
		}
		return false;
	}

	private void HourglassTick() {
		for (Player p : players) {
			for (Character c : p.getChars()) {
				c.HourglassTick();
			}
		}
	}

	public long getTime() {
		return (Sys.getTime() * 1000) / timerTicksPerSecond;
	}

	public void run() {
		if (this.nbPlayers != 0) {
			initPlacingBeforeBattle();
		}
		actions act;
		while (!dm.isRequestClose() && !quit) {

			act = im.getInputs();
			UpdateLogic();

			if (!dm.getGameBoard().isBusy()) {
				if (currentChar != null) {
					if (!currentChar.IsMoving()) {
						manageKeyInput(act);
					}
				} else {
					manageKeyInput(act);
				}
			} else {
				act = actions.none;
			}
			dm.Render();
		}
		dm.Clean();
	}

	private void PlaceChar() {
		if (map.getTile(cursorX, cursorY).getDeploymentZone() == this.indexPlayer + 1) {
			if (!isTileOccupied(cursorX, cursorY)) {
				currentChar.setCurrentTileX(cursorX);
				currentChar.setCurrentTileY(cursorY);
				currentChar.setHeight(map.getTile(cursorX, cursorY).getHeight());
				charPlaced = true;
				currentChar.setPlaced(true);
				dm.getGameBoard().getCharsToDRaw().add(players[this.indexPlayer].getChars()[indexChar]);
			}
		}
	}

	private boolean isTileOccupied(int X, int Y) {
		for (Player p : players) {
			for (Character c : p.getChars()) {
				if (c.getCurrentTileX() == X && c.getCurrentTileY() == Y) {
					return true;
				}
			}
		}
		return false;
	}

	private Character getCharOnTile(int X, int Y) {
		for (Player p : players) {
			for (Character c : p.getChars()) {
				if (c.getCurrentTileX() == X && c.getCurrentTileY() == Y) {
					return c;
				}
			}
		}
		return null;
	}

	private void ProcessMenuEnter() {
		switch (actualMenu) {
		case Actions:
			switch (dm.getHUD().getContextMenu().getIndex()) {
			case 0:
				if (currentChar.hasMoved()) {

				} else {
					LightUpPossibleMovementR(currentChar.getCurrentTileX(), currentChar.getCurrentTileY(), currentChar.getMovement());
					state = GameStatus.MoveSelection;
				}
				dm.getHUD().getContextMenu().setShow(false);
				break;
			case 1:
				break;
			case 2:
				break;
			case 3:
				currentChar.TurnIsOver();
				state = GameStatus.Pending;
				dm.getHUD().getContextMenu().setShow(false);
				break;
			}
			break;
		}
	}

	public void LightUpPossibleMovementR(int X, int Y, int movement) {
		if (movement <= 0) {
			return;
		} else {
			int zEcart;

			if (X + 1 < map.getLength()) {
				if (map.getTile(X + 1, Y).getHeight() > map.getTile(X, Y).getHeight()) {
					zEcart = map.getTile(X + 1, Y).getHeight() - map.getTile(X, Y).getHeight();
				} else {
					zEcart = map.getTile(X, Y).getHeight() - map.getTile(X + 1, Y).getHeight();
				}
				if (zEcart >= 2) {
					if (!isTileOccupied(X + 1, Y)) {
						map.getTile(X + 1, Y).setHighlightedGreen(true);
					}
					LightUpPossibleMovementR(X + 1, Y, movement - (zEcart + 1));
				} else if (zEcart < 2) {
					if (!isTileOccupied(X + 1, Y)) {
						map.getTile(X + 1, Y).setHighlightedGreen(true);
					}
					LightUpPossibleMovementR(X + 1, Y, movement - 1);
				}
			}
			if (X > 0) {
				if (map.getTile(X - 1, Y).getHeight() > map.getTile(X, Y).getHeight()) {
					zEcart = map.getTile(X - 1, Y).getHeight() - map.getTile(X, Y).getHeight();
				} else {
					zEcart = map.getTile(X, Y).getHeight() - map.getTile(X - 1, Y).getHeight();
				}
				if (zEcart >= 2) {
					if (!isTileOccupied(X - 1, Y)) {
						map.getTile(X - 1, Y).setHighlightedGreen(true);
					}
					LightUpPossibleMovementR(X - 1, Y, movement - (zEcart + 1));
				} else if (zEcart < 2) {
					if (!isTileOccupied(X - 1, Y)) {
						map.getTile(X - 1, Y).setHighlightedGreen(true);
					}
					LightUpPossibleMovementR(X - 1, Y, movement - 1);
				}
			}
			if (Y + 1 < map.getWidth()) {
				if (map.getTile(X, Y + 1).getHeight() > map.getTile(X, Y).getHeight()) {
					zEcart = map.getTile(X, Y + 1).getHeight() - map.getTile(X, Y).getHeight();
				} else {
					zEcart = map.getTile(X, Y).getHeight() - map.getTile(X, Y + 1).getHeight();
				}
				if (zEcart >= 2) {
					if (!isTileOccupied(X, Y + 1)) {
						map.getTile(X, Y + 1).setHighlightedGreen(true);
					}
					LightUpPossibleMovementR(X, Y + 1, movement - (zEcart + 1));
				} else if (zEcart < 2) {
					if (!isTileOccupied(X, Y + 1)) {
						map.getTile(X, Y + 1).setHighlightedGreen(true);
					}
					LightUpPossibleMovementR(X, Y + 1, movement - 1);
				}
			}
			if (Y > 0) {
				if (map.getTile(X, Y - 1).getHeight() > map.getTile(X, Y).getHeight()) {
					zEcart = map.getTile(X, Y - 1).getHeight() - map.getTile(X, Y).getHeight();
				} else {
					zEcart = map.getTile(X, Y).getHeight() - map.getTile(X, Y - 1).getHeight();
				}
				if (zEcart >= 2) {
					if (!isTileOccupied(X, Y - 1)) {
						map.getTile(X, Y - 1).setHighlightedGreen(true);
					}
					LightUpPossibleMovementR(X, Y - 1, movement - (zEcart + 1));
				} else if (zEcart < 2) {
					if (!isTileOccupied(X, Y - 1)) {
						map.getTile(X, Y - 1).setHighlightedGreen(true);
					}
					LightUpPossibleMovementR(X, Y - 1, movement - 1);
				}
			}

		}
	}

	public void LightUpPossibleAttackR(int X, int Y, int range) {
		if (range <= 0) {
			return;
		} else {

		}
	}

	private void Move() {
		if (map.getTile(cursorX, cursorY).isHighlightedGreen() && !isTileOccupied(cursorX, cursorY)) {
			map.getTile(currentChar.getCurrentTileX(), currentChar.getCurrentTileY()).setHighlighted(false);
			currentChar.setTileToGoX(cursorX);
			currentChar.setTileToGoY(cursorY);
			currentChar.setIsMoving(true);
			currentChar.setHasMoved(true);
			map.CleanLightUpZones();
			state = GameStatus.InCharMenu;
		}
	}

	private void manageKeyInput(actions act) {
		if (!act.equals(actions.none)) {
			switch (act) {
			case VIEW_SOUTH:
				dm.getGameBoard().RequestView(viewPoint.South);
				break;

			case VIEW_NORTH:
				dm.getGameBoard().RequestView(viewPoint.North);
				break;

			case VIEW_EAST:
				dm.getGameBoard().RequestView(viewPoint.East);
				break;

			case VIEW_WEST:
				dm.getGameBoard().RequestView(viewPoint.West);
				break;

			case UP:
				if (state == GameStatus.PlacingBeforeBattle || state == GameStatus.MoveSelection || state == GameStatus.TargetSelection || state == GameStatus.ExploringMap) {
					goUp();
				} else if (state == GameStatus.InCharMenu) {
					dm.getHUD().getContextMenu().Previous();
				}
				break;

			case DOWN:
				if (state == GameStatus.PlacingBeforeBattle || state == GameStatus.MoveSelection || state == GameStatus.TargetSelection || state == GameStatus.ExploringMap) {
					goDown();
				} else if (state == GameStatus.InCharMenu) {
					dm.getHUD().getContextMenu().Next();
				}
				break;

			case LEFT:
				if (state == GameStatus.PlacingBeforeBattle || state == GameStatus.MoveSelection || state == GameStatus.TargetSelection || state == GameStatus.ExploringMap) {
					goLeft();
				} else if (state == GameStatus.InCharMenu) {
					dm.getHUD().getContextMenu().Previous();
				}
				break;

			case RIGHT:
				if (state == GameStatus.PlacingBeforeBattle || state == GameStatus.MoveSelection || state == GameStatus.TargetSelection || state == GameStatus.ExploringMap) {
					goRigth();
				} else if (state == GameStatus.InCharMenu) {
					dm.getHUD().getContextMenu().Next();
				}
				break;

			case QUIT:
				quit = true;
				break;

			case ENTER:
				switch (state) {
				case PlacingBeforeBattle:
					PlaceChar();
					break;
				case InCharMenu:
					ProcessMenuEnter();
					break;
				case MoveSelection:
					Move();
					break;
				case TargetSelection:
					break;
				case ExploringMap:
					break;
				}
				break;
			}
		}

	}

	private void goUp() {
		switch (dm.getGameBoard().getCurrentView()) {
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
		switch (dm.getGameBoard().getCurrentView()) {
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
		switch (dm.getGameBoard().getCurrentView()) {
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
		switch (dm.getGameBoard().getCurrentView()) {
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
				} else if (currentChar != null) {
					if (i == currentChar.getPosX() && j == currentChar.getPosY() && currentChar.isPlaced()) {
						map.getTile(i, j).setHighlighted(true);
					} else {
						map.getTile(i, j).setHighlighted(false);
					}
				} else {
					map.getTile(i, j).setHighlighted(false);
				}
				dm.getGameBoard().RequestFocusOn(cursorX, cursorY, map.getTile(cursorX, cursorY).getHeight());
				dm.getHUD().SetCurrentTarget(getCharOnTile(cursorX, cursorY));
			}
		}
	}

	public static void main(String[] argv) {

		Map map = new Map(25, 25, "lolilol");
		Character c1 = new Character(Race.Human, Gender.Male);
		c1.setName("bobix1");
		Character c2 = new Character(Race.Dwarf, Gender.Male);
		c2.setName("bobix2");
		Character c3 = new Character(Race.Elve, Gender.Male);
		c3.setName("bobixou1");
		Character c4 = new Character(Race.Human, Gender.Male);
		c4.setName("bobixou2");
		Character c5 = new Character(Race.Human, Gender.Male);
		c5.setName("bobixou3");
		Player p1 = new Player("bobyx", new Character[] { c1, c2 });
		Player p2 = new Player("bobyxou", new Character[] { c3, c4, c5 });

		c3.setLifePoints(10);

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

		map.getTile(3, 1).setHeight(2);
		map.getTile(3, 2).setHeight(1);
		Game g = new Game(1024, 768, map, 2);
		g.setPlayer(0, p1);
		g.setPlayer(1, p2);
		g.run();

	}
}
