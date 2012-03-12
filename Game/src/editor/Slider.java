package editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JLabel;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Slider extends JPanel {

	private String name;
	JSlider slide;
	private int startValue = 25;

	public Slider(String nameParam) {
		this.name = nameParam;

		slide = new JSlider();

		slide.setMaximum(50);
		slide.setMinimum(0);
		slide.setValue(startValue);
		// );
		slide.setPaintTicks(true);
		slide.setPaintLabels(true);
		slide.setMinorTickSpacing(1);
		slide.setMajorTickSpacing(10);
		setBorder(BorderFactory.createTitledBorder(name + " : " + slide.getValue()));
		slide.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				setBorder(BorderFactory.createTitledBorder(name + " : " + slide.getValue()));
			}
		});

		this.setPreferredSize(new Dimension(200, 90));
		slide.setPreferredSize(new Dimension(170, 50));

		this.add(slide, BorderLayout.CENTER);
	}

	public void setTheValueTo(int value) {
		this.slide.setValue(value);
		setBorder(BorderFactory.createTitledBorder(name + " : " + value));
	}

	public void Reset() {
		setTheValueTo(startValue);
	}
}