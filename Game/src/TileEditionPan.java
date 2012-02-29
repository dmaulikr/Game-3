import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TileEditionPan extends JPanel{

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
	
	private JPanel actionPanel;
	private JButton actionSave;
	private JButton actionCancel;
	
	private Tile currentTile;
	
	public TileEditionPan(){
		CreateElements();
		PreparePanels();
		AddElements();		
		CheckSaveButton();
	}
	
	public void Reset(){
		currentTile=null;
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
	
	public void CheckSaveButton(){
		if (currentTile!=null){
			actionSave.setEnabled(true);
		}else{
			actionSave.setEnabled(false);
		}
	}
	
	public void CreateElements(){
		sliderHeightSE = new CornerHeightSlide("HeightSE");
		sliderHeightNE = new CornerHeightSlide("HeightNE");
		sliderHeightNO = new CornerHeightSlide("HeightNO");
		sliderHeightSO = new CornerHeightSlide("HeightSO");
		
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
		
		actionPanel = new JPanel();
		actionSave = new JButton();
		actionCancel = new JButton();
	}
	
	private void PreparePanels(){
		PrepareTexturePanel();
		PrepareTypePanel();
		PrepareDecorationPanel();
		PrepareIdPanel();
		PrepareActionPanel();
	}
	
	private void PrepareTexturePanel(){
		textureType[] textureArray=textureType.values();
		for (int i = 0; i < textureArray.length; i++) {
			textureCombo.addItem(textureArray[i].toString());		
		}		
		texturePanel.add(textureLabel);
		texturePanel.add(textureCombo);
		texturePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));		
	}
	
	private void PrepareTypePanel(){
		tileType[] tileTypeArray=tileType.values();
		for (int i = 0; i < tileTypeArray.length; i++) {
			typeCombo.addItem(tileTypeArray[i].toString());		
		}
		typePanel.add(typeLabel);
		typePanel.add(typeCombo);
		typePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));		
	}
	
	private void PrepareDecorationPanel(){
		decorationType[] decorationArray=decorationType.values();
		for (int i = 0; i < decorationArray.length; i++) {
			decorationCombo.addItem(decorationArray[i].toString());		
		}
		decorationPanel.add(decorationLabel);
		decorationPanel.add(decorationCombo);
		decorationPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));		
	}
	
	private void PrepareIdPanel(){
		idLabel.setText("X/Y");
		idPanel.add(idLabel, BorderLayout.CENTER);
		idPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));			
	}
	
	private void PrepareActionPanel(){
		actionPanel.setLayout(new GridLayout(1, 2));
		
		actionSave.setText("Save");
		actionSave.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				SaveTile();
			}
			});		
		actionPanel.add(actionSave);
		
		actionCancel.setText("Cancel");
		actionCancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Reset();
			}
			});			
		actionPanel.add(actionCancel);
		actionPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));		
	}
	
	public void AddElements(){
		this.setLayout(new GridLayout(9, 1));
		
		this.add(sliderHeightSE);
		this.add(sliderHeightNE);
		this.add(sliderHeightNO);
		this.add(sliderHeightSO);
		
		this.add(texturePanel);
		this.add(typePanel);
		this.add(decorationPanel);
		this.add(idPanel);
		this.add(actionPanel);
	}
	
	public void LoadTile(Tile tile){
		this.currentTile=tile;
		this.sliderHeightSE.setTheValueTo(tile.getHeightSE());
		this.sliderHeightNE.setTheValueTo(tile.getHeightNE());
		this.sliderHeightNO.setTheValueTo(tile.getHeightNO());
		this.sliderHeightSO.setTheValueTo(tile.getHeightSO());
		
		this.textureCombo.setSelectedItem(tile.getTexture().toString());
		this.typeCombo.setSelectedItem(tile.getType().toString());
		this.decorationCombo.setSelectedItem(tile.getDecoration().toString());
		
		this.idLabel.setText(tile.getPosX()+"/"+tile.getPosY());
		CheckSaveButton();
	}
	
	public void SaveTile(){
		currentTile.setHeightSE(this.sliderHeightSE.slide.getValue());
		currentTile.setHeightNE(this.sliderHeightNE.slide.getValue());
		currentTile.setHeightNO(this.sliderHeightNO.slide.getValue());
		currentTile.setHeightSO(this.sliderHeightSO.slide.getValue());
		
		currentTile.setTexture(textureType.valueOf(this.textureCombo.getSelectedItem().toString()));
		currentTile.setType(tileType.valueOf(this.typeCombo.getSelectedItem().toString()));
		currentTile.setDecoration(decorationType.valueOf(this.decorationCombo.getSelectedItem().toString()));
	}
}
