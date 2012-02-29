import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TileEditionPan extends JPanel {

	private CornerHeightSlide sliderHeightSE;
	private CornerHeightSlide sliderHeightNE;
	private CornerHeightSlide sliderHeightNO;
	private CornerHeightSlide sliderHeightSO;

	private JPanel texturePanel;
	private JComboBox textureCombo;
	private JLabel textureLabel;

	private JPanel typePanel;
	private JComboBox typeCombo;
	private JLabel typeLabel;

	private JPanel decorationPanel;
	private JComboBox decorationCombo;
	private JLabel decorationLabel;

	private JPanel idPanel;
	private JLabel idLabel;

	private Tile currentTile;

	private Editor containerFrame;

	private boolean saveable;

	private OnSlideEvent slideEvent;
	
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
		sliderHeightSE.Reset();
		sliderHeightNE.Reset();
		sliderHeightNO.Reset();
		sliderHeightSO.Reset();
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
		slideEvent= new OnSlideEvent();
		
		sliderHeightSE = new CornerHeightSlide("HeightSE");
		sliderHeightNE = new CornerHeightSlide("HeightNE");
		sliderHeightNO = new CornerHeightSlide("HeightNO");
		sliderHeightSO = new CornerHeightSlide("HeightSO");
		ActivateActionToSlides();

		texturePanel = new JPanel();
		textureCombo = new JComboBox();
		textureLabel = new JLabel("Texture :");

		typePanel = new JPanel();
		typeCombo = new JComboBox();
		typeLabel = new JLabel("Type :");

		decorationPanel = new JPanel();
		decorationCombo = new JComboBox();
		decorationLabel = new JLabel("Decoration :");

		idPanel = new JPanel();
		idLabel = new JLabel();
	}

	private void PreparePanels() {
		PrepareTexturePanel();
		PrepareTypePanel();
		PrepareDecorationPanel();
		PrepareIdPanel();
	}

	private void ActivateActionToSlides() {
		sliderHeightSE.slide.addChangeListener(slideEvent);
		sliderHeightNE.slide.addChangeListener(slideEvent);
		sliderHeightNO.slide.addChangeListener(slideEvent);
		sliderHeightSO.slide.addChangeListener(slideEvent);
	}
	private void DeactivateActionToSlides() {
		sliderHeightSE.slide.removeChangeListener(slideEvent);
		sliderHeightNE.slide.removeChangeListener(slideEvent);
		sliderHeightNO.slide.removeChangeListener(slideEvent);
		sliderHeightSO.slide.removeChangeListener(slideEvent);
	}

	private void PrepareTexturePanel() {
		textureType[] textureArray = textureType.values();
		for (int i = 0; i < textureArray.length; i++) {
			textureCombo.addItem(textureArray[i].toString());
		}
		texturePanel.add(textureLabel);
		texturePanel.add(textureCombo);
		texturePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		textureCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SaveTile();
			}
		});
	}

	private void PrepareTypePanel() {
		tileType[] tileTypeArray = tileType.values();
		for (int i = 0; i < tileTypeArray.length; i++) {
			typeCombo.addItem(tileTypeArray[i].toString());
		}
		typePanel.add(typeLabel);
		typePanel.add(typeCombo);
		typePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		typeCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SaveTile();
			}
		});
	}

	private void PrepareDecorationPanel() {
		decorationType[] decorationArray = decorationType.values();
		for (int i = 0; i < decorationArray.length; i++) {
			decorationCombo.addItem(decorationArray[i].toString());
		}
		decorationPanel.add(decorationLabel);
		decorationPanel.add(decorationCombo);
		decorationPanel.setBorder(BorderFactory
				.createLineBorder(Color.BLACK, 1));
		decorationCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SaveTile();
			}
		});
	}

	private void PrepareIdPanel() {
		idLabel.setText("X/Y");
		idPanel.add(idLabel, BorderLayout.CENTER);
		idPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
	}

	public void AddElements() {
		this.setLayout(new GridLayout(8, 1));

		this.add(sliderHeightSE);
		this.add(sliderHeightNE);
		this.add(sliderHeightNO);
		this.add(sliderHeightSO);

		this.add(texturePanel);
		this.add(typePanel);
		this.add(decorationPanel);
		this.add(idPanel);
	}

	public void LoadTile(Tile tile) {
		this.currentTile = tile;
		DeactivateActionToSlides();
		this.sliderHeightSE.setTheValueTo(tile.getHeightSE());
		this.sliderHeightNE.setTheValueTo(tile.getHeightNE());
		this.sliderHeightNO.setTheValueTo(tile.getHeightNO());
		this.sliderHeightSO.setTheValueTo(tile.getHeightSO());
		ActivateActionToSlides();
		this.textureCombo.setSelectedItem(tile.getTexture().toString());
		this.typeCombo.setSelectedItem(tile.getType().toString());
		this.decorationCombo.setSelectedItem(tile.getDecoration().toString());

		this.idLabel.setText(tile.getPosX() + "/" + tile.getPosY());
		CheckSaveButton();
	}

	public void SaveTile() {
		if (currentTile.getHeightSE() != this.sliderHeightSE.slide.getValue()
				|| currentTile.getHeightNE() != this.sliderHeightNE.slide
						.getValue()
				|| currentTile.getHeightNO() != this.sliderHeightNO.slide
						.getValue()
				|| currentTile.getHeightSO() != this.sliderHeightSO.slide
						.getValue()
				|| currentTile.getTexture() != textureType
						.valueOf(this.textureCombo.getSelectedItem().toString())
				|| currentTile.getType() != tileType
						.valueOf(this.typeCombo.getSelectedItem().toString())
				|| currentTile.getDecoration()!= decorationType
						.valueOf(this.decorationCombo.getSelectedItem().toString())) {

			currentTile.setHeightSE(this.sliderHeightSE.slide.getValue());
			currentTile.setHeightNE(this.sliderHeightNE.slide.getValue());
			currentTile.setHeightNO(this.sliderHeightNO.slide.getValue());
			currentTile.setHeightSO(this.sliderHeightSO.slide.getValue());

			currentTile.setTexture(textureType.valueOf(this.textureCombo
					.getSelectedItem().toString()));
			currentTile.setType(tileType.valueOf(this.typeCombo
					.getSelectedItem().toString()));
			currentTile
					.setDecoration(decorationType.valueOf(this.decorationCombo
							.getSelectedItem().toString()));
			containerFrame.updateCancas();
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
				case 'a':
					typeCombo.setSelectedItem(tileType.Walkable.toString());
					SaveTile();
					break;
				case 'z':
					typeCombo.setSelectedItem(tileType.Uncrossable.toString());
					SaveTile();
					break;
				case 'e':
					typeCombo.setSelectedItem(tileType.DifficultGround
							.toString());
					SaveTile();
					break;

				case 'q':
					textureCombo.setSelectedItem(textureType.Grass.toString());
					SaveTile();
					break;
				case 's':
					textureCombo.setSelectedItem(textureType.Earth.toString());
					SaveTile();
					break;
				case 'd':
					textureCombo.setSelectedItem(textureType.Sand.toString());
					SaveTile();
					break;
				case 'f':
					textureCombo.setSelectedItem(textureType.Stone.toString());
					SaveTile();
					break;

				default:
					switch (e.getKeyCode()) {
					// arrowup
					case 38:
						SaveTile();
						if (currentTile.getPosX() - 1 >= 0) {
							LoadTile(containerFrame.getMap().getTile(
									currentTile.getPosX() - 1,
									currentTile.getPosY()));
						}
						if (isFocusable()) {
							requestFocusInWindow();
						}
						containerFrame.setCanvasFocusOn(currentTile.getPosX(),
								currentTile.getPosY());
						break;
					// arrowleft
					case 37:
						SaveTile();
						if (currentTile.getPosY() - 1 >= 0) {
							LoadTile(containerFrame.getMap().getTile(
									currentTile.getPosX(),
									currentTile.getPosY() - 1));
						}
						if (isFocusable()) {
							requestFocusInWindow();
						}
						containerFrame.setCanvasFocusOn(currentTile.getPosX(),
								currentTile.getPosY());
						break;
					// arrowrigth
					case 39:
						SaveTile();
						if (currentTile.getPosY() < containerFrame.getMap()
								.getWidth() - 1) {
							LoadTile(containerFrame.getMap().getTile(
									currentTile.getPosX(),
									currentTile.getPosY() + 1));
						}
						if (isFocusable()) {
							requestFocusInWindow();
						}
						containerFrame.setCanvasFocusOn(currentTile.getPosX(),
								currentTile.getPosY());
						break;
					// arrowdown
					case 40:
						SaveTile();
						if (currentTile.getPosX() < containerFrame.getMap()
								.getLength() - 1) {
							LoadTile(containerFrame.getMap().getTile(
									currentTile.getPosX() + 1,
									currentTile.getPosY()));
						}
						if (isFocusable()) {
							requestFocusInWindow();
						}
						containerFrame.setCanvasFocusOn(currentTile.getPosX(),
								currentTile.getPosY());
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
	
	public class OnSlideEvent implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent e) {
			SaveTile();
		}
		
	}
}