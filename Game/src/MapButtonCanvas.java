import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.lang.Math;

public class MapButtonCanvas extends JPanel{
	
	JButton[][] buttonCanvas;
	
	private int selectedX;
	private int selectedY;
	private Map map;
	private TileEditionPan tep;
	
	public MapButtonCanvas(Map map,TileEditionPan tep){
		GridLayout gl = new GridLayout(map.getLength(), map.getWidth());
		this.setLayout(gl);		
		setMap(map);
		setTep(tep);
	    buttonCanvas= new JButton[map.getLength()][map.getWidth()];
	    
		for (int i = 0; i < map.getLength(); i++) {
			for (int j = 0; j < map.getWidth(); j++) {
				buttonCanvas[i][j]=new JButton();
				SelectionAction al = new SelectionAction(i,j);
				buttonCanvas[i][j].addActionListener(al);
				this.add(buttonCanvas[i][j]);
			}
		}
		UpdateCanvas();
	}
	
	public void UpdateCanvas(){
		for (int i = 0; i < map.getLength(); i++) {
			for (int j = 0; j < map.getWidth(); j++) {
				switch (map.getTile(i, j).getTexture()) {
				
				case Grass:
					buttonCanvas[i][j].setBackground(new Color(45,125,43));
					break;
				
				case Earth:
					buttonCanvas[i][j].setBackground(new Color(128,64,0));
					break;
					
				case Sand:
					buttonCanvas[i][j].setBackground(new Color(255,255,128));
					break;
				
				case Stone:
					buttonCanvas[i][j].setBackground(new Color(128,128,128));
					break;

				default:
					buttonCanvas[i][j].setBackground(Color.GRAY);
					break;
				}
			}
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

	class SelectionAction implements ActionListener{
		private int X;
		private int Y;
		
		public SelectionAction(int X, int Y){
			setX(X);
			setY(Y);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			getTep().LoadTile(getMap().getTile(getX(), getY()));
			if(getTep().isFocusable()){
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

	        

	

