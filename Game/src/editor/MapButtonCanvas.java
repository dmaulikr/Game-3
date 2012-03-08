package editor;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import entity.Map;

import java.lang.Math;

public class MapButtonCanvas extends JPanel {

	JButton[][] buttonCanvas;
	JButton tempBut;
	private int selectedX;
	private int selectedY;
	private Map map;
	private TileEditionPan tep;

	public MapButtonCanvas(Map map, TileEditionPan tep) {
		int dim = Math.max(map.getLength(), map.getWidth());
		GridLayout gl = new GridLayout(dim, dim);
		this.setLayout(gl);
		setMap(map);
		setTep(tep);
		buttonCanvas = new JButton[dim][dim];

		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				if (i < map.getLength() && j < map.getWidth()) {
					buttonCanvas[i][j] = new JButton();
					SelectionAction al = new SelectionAction(i, j);
					buttonCanvas[i][j].addActionListener(al);
					buttonCanvas[i][j].setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
					this.add(buttonCanvas[i][j]);
				} else {
					buttonCanvas[i][j] = new JButton();
					buttonCanvas[i][j].setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
					this.add(buttonCanvas[i][j]);
				}
			}
		}

		UpdateCanvas();
	}

	public void UpdateCanvas() {
		for (int i = 0; i < map.getLength(); i++) {
			for (int j = 0; j < map.getWidth(); j++) {
				switch (map.getTile(i, j).getTexture()) {

				case Grass:
					buttonCanvas[i][j].setBackground(new Color(45, 125, 43));
					break;

				case Earth:
					buttonCanvas[i][j].setBackground(new Color(128, 64, 0));
					break;

				case Sand:
					buttonCanvas[i][j].setBackground(new Color(255, 255, 128));
					break;

				case Stone:
					buttonCanvas[i][j].setBackground(new Color(128, 128, 128));
					break;

				default:
					buttonCanvas[i][j].setBackground(Color.GRAY);
					break;
				}
			}
		}
	}

	public void setFocusOn(int X, int Y) {
		clearFocus();
		tempBut = buttonCanvas[X][Y];
		tempBut.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
	}

	private void clearFocus() {
		if (tempBut != null) {
			tempBut.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		}
	}

	public int getSelectedX() {
		return selectedX;
	}

	public void setSelectedX(int selectedX) {
		this.selectedX = selectedX;
	}

	public int getSelectedY() {
		return selectedY;
	}

	public void setSelectedY(int selectedY) {
		this.selectedY = selectedY;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public TileEditionPan getTep() {
		return tep;
	}

	public void setTep(TileEditionPan tep) {
		this.tep = tep;
	}

	class SelectionAction implements ActionListener {
		private int X;
		private int Y;

		public SelectionAction(int X, int Y) {
			setX(X);
			setY(Y);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			setFocusOn(X, Y);
			getTep().LoadTile(getMap().getTile(getX(), getY()));
			if (getTep().isFocusable()) {
				getTep().requestFocusInWindow();
			}
		}

		public int getX() {
			return X;
		}

		public void setX(int x) {
			X = x;
		}

		public int getY() {
			return Y;
		}

		public void setY(int y) {
			Y = y;
		}
	}
}
