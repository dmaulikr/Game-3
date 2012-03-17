package editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import entity.Tile;
import entity.Tile.decorationType;
import entity.Tile.textureType;
import entity.Tile.tileType;

public class TileEditionPan extends JPanel {

	private Slider sliderHeight;

	private JPanel texturePanel;
	private JComboBox<textureType> textureCombo;

	private JPanel typePanel;
	private JComboBox<tileType> typeCombo;

	private JPanel decorationPanel;
	private JComboBox<decorationType> decorationCombo;

	private JPanel deployementZoneNumPanel;
	private JButton deployementZoneNumMinus;
	private JTextField deployementZoneNumTextField;
	private JButton deployementZoneNumPlus;

	private JPanel idPanel;
	private JLabel idLabel;

	private Tile currentTile;

	private Editor containerFrame;

	private boolean saveable;

	private OnSlideEvent slideEvent;
	private ComboSelectEvent comboSelectEvent;

	public TileEditionPan(Editor containerFrame) {
		CreateElements();
		PreparePanels();
		AddElements();
		CheckSaveButton();

		this.setContainerFrame(containerFrame);
		this.addKeyListener(new KeyShortcut());
	}

	public void Reset() {
		currentTile = null;
		sliderHeight.Reset();
		textureCombo.setSelectedIndex(0);
		typeCombo.setSelectedIndex(0);
		decorationCombo.setSelectedIndex(0);
		idLabel.setText("X/Y");
		CheckSaveButton();
	}

	public void CheckSaveButton() {
		if (currentTile != null) {
			setSaveable(true);
		} else {
			setSaveable(false);
		}
	}

	public void CreateElements() {
		slideEvent = new OnSlideEvent();
		comboSelectEvent = new ComboSelectEvent();

		sliderHeight = new Slider("Height");

		texturePanel = new JPanel();
		textureCombo = new JComboBox<textureType>();

		typePanel = new JPanel();
		typeCombo = new JComboBox<tileType>();

		decorationPanel = new JPanel();
		decorationCombo = new JComboBox<decorationType>();

		deployementZoneNumPanel = new JPanel();
		deployementZoneNumMinus = new JButton("-");
		deployementZoneNumTextField = new JTextField();
		deployementZoneNumPlus = new JButton("+");

		idPanel = new JPanel();
		idLabel = new JLabel();
	}

	private void PreparePanels() {
		PrepareTexturePanel();
		PrepareTypePanel();
		PrepareDecorationPanel();
		PrepareIdPanel();
		PrepareDeployementPanel();
		ActivateActions();
	}

	private void ActivateActions() {
		sliderHeight.slide.addChangeListener(slideEvent);
		textureCombo.addActionListener(comboSelectEvent);
		typeCombo.addActionListener(comboSelectEvent);
		decorationCombo.addActionListener(comboSelectEvent);
	}

	private void DeactivateActions() {
		sliderHeight.slide.removeChangeListener(slideEvent);
		textureCombo.removeActionListener(comboSelectEvent);
		typeCombo.removeActionListener(comboSelectEvent);
		decorationCombo.removeActionListener(comboSelectEvent);
	}

	private void PrepareTexturePanel() {
		Tile.textureType[] textureArray = Tile.textureType.values();
		for (int i = 0; i < textureArray.length; i++) {
			textureCombo.addItem(textureArray[i]);
		}
		texturePanel.setBorder(BorderFactory.createTitledBorder("Texture :"));
		texturePanel.add(textureCombo);

	}

	private void PrepareTypePanel() {
		Tile.tileType[] tileTypeArray = Tile.tileType.values();
		for (int i = 0; i < tileTypeArray.length; i++) {
			typeCombo.addItem(tileTypeArray[i]);
		}
		typePanel.setBorder(BorderFactory.createTitledBorder("Type :"));
		typePanel.add(typeCombo);
	}

	private void PrepareDecorationPanel() {
		Tile.decorationType[] decorationArray = Tile.decorationType.values();
		for (int i = 0; i < decorationArray.length; i++) {
			decorationCombo.addItem(decorationArray[i]);
		}
		decorationPanel.setBorder(BorderFactory.createTitledBorder("Decoration :"));
		decorationPanel.add(decorationCombo);
	}

	private void PrepareDeployementPanel() {
		deployementZoneNumPanel.setBorder(BorderFactory.createTitledBorder("Deployement Zone for player :"));
		deployementZoneNumTextField.setEditable(false);
		deployementZoneNumTextField.setText("0");

		deployementZoneNumMinus.setFont(new Font("Arial", Font.BOLD, 10));
		deployementZoneNumMinus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int deployementZoneNum = Integer.parseInt(deployementZoneNumTextField.getText());
				if (deployementZoneNum > 1) {
					deployementZoneNum--;
				}
				deployementZoneNumTextField.setText(String.valueOf(deployementZoneNum));
				SaveTile();
			}
		});

		deployementZoneNumPlus.setFont(new Font("Arial", Font.BOLD, 10));
		deployementZoneNumPlus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int deployementZoneNum = Integer.parseInt(deployementZoneNumTextField.getText());
				deployementZoneNum++;
				deployementZoneNumTextField.setText(String.valueOf(deployementZoneNum));
				SaveTile();
			}
		});

		deployementZoneNumPanel.setLayout(new GridLayout(1, 3));
		deployementZoneNumPanel.add(deployementZoneNumMinus);
		deployementZoneNumPanel.add(deployementZoneNumTextField);
		deployementZoneNumPanel.add(deployementZoneNumPlus);
	}

	private void PrepareIdPanel() {
		idPanel.setBorder(BorderFactory.createTitledBorder("X/Y :"));

		idLabel.setText("X/Y");
		idPanel.add(idLabel, BorderLayout.CENTER);
	}

	public void AddElements() {
		this.setLayout(new GridLayout(9, 1));

		this.add(sliderHeight);

		this.add(texturePanel);
		this.add(typePanel);
		this.add(decorationPanel);
		this.add(deployementZoneNumPanel);
		this.add(idPanel);
	}

	public void LoadTile(Tile tile) {
		this.currentTile = tile;

		DeactivateActions();

		this.sliderHeight.setTheValueTo(tile.getHeight());

		this.textureCombo.setSelectedItem(tile.getTexture());
		this.typeCombo.setSelectedItem(tile.getType());
		this.decorationCombo.setSelectedItem(tile.getDecoration());
		this.deployementZoneNumTextField.setText(String.valueOf(tile.getDeploymentZone()));
		ActivateActions();

		this.idLabel.setText(tile.getPosX() + "/" + tile.getPosY());
		CheckSaveButton();
	}

	public void SaveTile() {
		if (currentTile != null) {
			if (currentTile.getHeight() != this.sliderHeight.slide.getValue() || currentTile.getTexture() != Tile.textureType.valueOf(this.textureCombo.getSelectedItem().toString())
					|| currentTile.getType() != Tile.tileType.valueOf(this.typeCombo.getSelectedItem().toString())
					|| currentTile.getDecoration() != Tile.decorationType.valueOf(this.decorationCombo.getSelectedItem().toString())
					|| currentTile.getDeploymentZone() != Integer.valueOf(deployementZoneNumTextField.getText())) {

				currentTile.setHeight(this.sliderHeight.slide.getValue());

				currentTile.setTexture(Tile.textureType.valueOf(this.textureCombo.getSelectedItem().toString()));
				currentTile.setType(Tile.tileType.valueOf(this.typeCombo.getSelectedItem().toString()));
				currentTile.setDecoration(Tile.decorationType.valueOf(this.decorationCombo.getSelectedItem().toString()));
				currentTile.setDeploymentZone(Integer.valueOf(deployementZoneNumTextField.getText()));
				containerFrame.updateCancas();
			}
		}
	}

	public Editor getContainerFrame() {
		return containerFrame;
	}

	public void setContainerFrame(Editor containerFrame) {
		this.containerFrame = containerFrame;
	}

	public boolean isSaveable() {
		return saveable;
	}

	public void setSaveable(boolean saveable) {
		this.saveable = saveable;
	}

	public class KeyShortcut implements KeyListener {
		@Override
		public void keyPressed(KeyEvent e) {
			if (currentTile != null) {
				switch (e.getKeyChar()) {
				case '0':
					deployementZoneNumTextField.setText("0");
					SaveTile();
					break;
				case '1':
					deployementZoneNumTextField.setText("1");
					SaveTile();
					break;
				case '2':
					deployementZoneNumTextField.setText("2");
					SaveTile();
					break;
				case '3':
					deployementZoneNumTextField.setText("3");
					SaveTile();
					break;

				case 'a':
					typeCombo.setSelectedItem(Tile.tileType.Walkable);
					SaveTile();
					break;
				case 'z':
					typeCombo.setSelectedItem(Tile.tileType.Uncrossable);
					SaveTile();
					break;
				case 'e':
					typeCombo.setSelectedItem(Tile.tileType.DifficultGround);
					SaveTile();
					break;

				case 'q':
					textureCombo.setSelectedItem(Tile.textureType.Grass);
					SaveTile();
					break;
				case 's':
					textureCombo.setSelectedItem(Tile.textureType.Earth);
					SaveTile();
					break;
				case 'd':
					textureCombo.setSelectedItem(Tile.textureType.Sand);
					SaveTile();
					break;
				case 'f':
					textureCombo.setSelectedItem(Tile.textureType.Stone);
					SaveTile();
					break;

				case 'w':
					if (sliderHeight.slide.getValue() > 0) {
						sliderHeight.slide.setValue(sliderHeight.slide.getValue() - 1);
					}
					SaveTile();
					break;
				case 'x':
					if (sliderHeight.slide.getValue() < 25) {
						sliderHeight.slide.setValue(sliderHeight.slide.getValue() + 1);
					}
					SaveTile();
					break;

				default:
					switch (e.getKeyCode()) {
					// arrowup
					case 38:
						SaveTile();
						if (currentTile.getPosX() - 1 >= 0) {
							LoadTile(containerFrame.getMap().getTile(currentTile.getPosX() - 1, currentTile.getPosY()));
						}
						if (isFocusable()) {
							requestFocusInWindow();
						}
						containerFrame.setCanvasFocusOn(currentTile.getPosX(), currentTile.getPosY());
						break;
					// arrowleft
					case 37:
						SaveTile();
						if (currentTile.getPosY() - 1 >= 0) {
							LoadTile(containerFrame.getMap().getTile(currentTile.getPosX(), currentTile.getPosY() - 1));
						}
						if (isFocusable()) {
							requestFocusInWindow();
						}
						containerFrame.setCanvasFocusOn(currentTile.getPosX(), currentTile.getPosY());
						break;
					// arrowrigth
					case 39:
						SaveTile();
						if (currentTile.getPosY() < containerFrame.getMap().getWidth() - 1) {
							LoadTile(containerFrame.getMap().getTile(currentTile.getPosX(), currentTile.getPosY() + 1));
						}
						if (isFocusable()) {
							requestFocusInWindow();
						}
						containerFrame.setCanvasFocusOn(currentTile.getPosX(), currentTile.getPosY());
						break;
					// arrowdown
					case 40:
						SaveTile();
						if (currentTile.getPosX() < containerFrame.getMap().getLength() - 1) {
							LoadTile(containerFrame.getMap().getTile(currentTile.getPosX() + 1, currentTile.getPosY()));
						}
						if (isFocusable()) {
							requestFocusInWindow();
						}
						containerFrame.setCanvasFocusOn(currentTile.getPosX(), currentTile.getPosY());
						break;
					default:
						System.out.println(e.getKeyCode());
						break;
					}
				}
			}

		}

		@Override
		public void keyReleased(KeyEvent e) {

		}

		@Override
		public void keyTyped(KeyEvent e) {

		}
	}

	public class OnSlideEvent implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			SaveTile();
		}

	}

	public class ComboSelectEvent implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			SaveTile();
		}
	}

}