import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JLabel;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class CornerHeightSlide extends JPanel{

	private JLabel descLabel;
	private String name;
	
	public CornerHeightSlide(String nameParam){
		this.name=nameParam;
		descLabel = new JLabel(name+" : 25");
		JSlider slide = new JSlider();
	
		slide.setMaximum(50);
		slide.setMinimum(0);
		slide.setValue(25);
		slide.setPaintTicks(true);
		slide.setPaintLabels(true);
		slide.setMinorTickSpacing(1);
		slide.setMajorTickSpacing(10);
		slide.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent event){
				descLabel.setText(name + " : " + ((JSlider)event.getSource()).getValue() );
			}
		});
		
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
		
		this.setPreferredSize(new Dimension(200, 90));
		descLabel.setPreferredSize(new Dimension(170, 20));
		slide.setPreferredSize(new Dimension(170, 50));
		
		this.add(descLabel, BorderLayout.NORTH);
		this.add(slide, BorderLayout.CENTER);
		
	}	
}