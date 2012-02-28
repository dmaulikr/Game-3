import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JPanel;

public class TileEditionPan extends JPanel{

	private CornerHeightSlide sliderHeightSE = new CornerHeightSlide("HeightSE");
	private CornerHeightSlide sliderHeightNE = new CornerHeightSlide("HeightNE");
	private CornerHeightSlide sliderHeightNO = new CornerHeightSlide("HeightNO");
	private CornerHeightSlide sliderHeightSO = new CornerHeightSlide("HeightSO");
	private JComboBox textureCombo = new JComboBox();
	private JComboBox typeCombo = new JComboBox();
	private JComboBox decorationCombo = new JComboBox();
	
	
	
	public TileEditionPan(){
		textureCombo.addItem(textureType.Grass.toString());
		textureCombo.addItem(textureType.Sand.toString());
		textureCombo.addItem(textureType.Stone.toString());
		textureCombo.addItem(textureType.Gravel.toString());
		textureCombo.addItem(textureType.Dust.toString());
		
		
		
		this.add(sliderHeightSE);
		this.add(sliderHeightNE);
		this.add(sliderHeightNO);
		this.add(sliderHeightSO);
		
		this.add(textureCombo);
		this.add(typeCombo);
		this.add(decorationCombo);
	}
}
