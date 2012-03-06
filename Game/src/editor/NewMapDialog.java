package editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NewMapDialog extends JDialog {
	private NewMapDialogInfo newMapDialogInfo = new NewMapDialogInfo();

	private JPanel namePanel, lengthPanel, widthPanel, controlPanel;

	private JTextField name, length, width;
	private JButton plusButtonLength, plusButtonWidth, minusButtonLength, minusButtonWidth, okButton, cancelButton;

	private int maxLen = 256;
	private int maxWid = 256;

	public NewMapDialog(JFrame parent, String title, boolean modal) {
		super(parent, title, modal);
		this.setSize(200, 250);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.initComponent();
	}

	public NewMapDialogInfo showDialog() {
		this.setVisible(true);
		return this.newMapDialogInfo;
	}

	private void initComponent() {
		this.setLayout(new GridLayout(4, 1));

		this.initNamePanel();
		this.initLengthPanel();
		this.initWidthPanel();
		this.initControlPanel();

		this.add(namePanel);
		this.add(lengthPanel);
		this.add(widthPanel);
		this.add(controlPanel);
	}

	private void initNamePanel() {
		namePanel = new JPanel();
		namePanel.setBackground(Color.white);
		namePanel.setBorder(BorderFactory.createTitledBorder("Name of the map"));
		name = new JTextField();
		name.setPreferredSize(new Dimension(150, 25));
		namePanel.add(name);
		name.setText("");
	}

	private void initLengthPanel() {
		lengthPanel = new JPanel();
		lengthPanel.setBackground(Color.white);
		lengthPanel.setBorder(BorderFactory.createTitledBorder("Length of the map"));
		length = new JTextField();
		length.setPreferredSize(new Dimension(35, 25));
		length.setEditable(true);
		length.setText("10");
		plusButtonLength = new JButton("+");
		plusButtonLength.setFont(new Font("Arial", Font.BOLD, 10));
		plusButtonLength.setPreferredSize(new Dimension(40, 25));
		plusButtonLength.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int lengthInt = Integer.parseInt(length.getText());
				if (lengthInt < maxLen) {
					lengthInt++;
				} else if (lengthInt > maxWid) {
					lengthInt = maxWid;
				}
				length.setText(String.valueOf(lengthInt));
			}
		});
		minusButtonLength = new JButton("-");
		minusButtonLength.setFont(new Font("Arial", Font.BOLD, 10));
		minusButtonLength.setPreferredSize(new Dimension(40, 25));
		minusButtonLength.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int lengthInt = Integer.parseInt(length.getText());
				if (lengthInt > 1) {
					lengthInt--;
				}
				length.setText(String.valueOf(lengthInt));
			}
		});
		lengthPanel.setLayout(new GridLayout(1, 3));
		lengthPanel.add(minusButtonLength);
		lengthPanel.add(length);
		lengthPanel.add(plusButtonLength);
	}

	private void initWidthPanel() {
		widthPanel = new JPanel();
		widthPanel.setBackground(Color.white);
		widthPanel.setPreferredSize(new Dimension(240, 60));
		widthPanel.setBorder(BorderFactory.createTitledBorder("Width of the map"));
		width = new JTextField();
		width.setEditable(true);
		width.setPreferredSize(new Dimension(35, 25));
		width.setText("10");
		plusButtonWidth = new JButton("+");
		plusButtonWidth.setFont(new Font("Arial", Font.BOLD, 10));
		plusButtonWidth.setPreferredSize(new Dimension(40, 25));
		plusButtonWidth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int widthInt = Integer.parseInt(width.getText());
				if (widthInt < maxWid) {
					widthInt++;
				} else if (widthInt > maxWid) {
					widthInt = maxWid;
				}
				width.setText(String.valueOf(widthInt));
			}
		});
		minusButtonWidth = new JButton("-");
		minusButtonWidth.setFont(new Font("Arial", Font.BOLD, 10));
		minusButtonWidth.setPreferredSize(new Dimension(40, 25));
		minusButtonWidth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int widthInt = Integer.parseInt(width.getText());
				if (widthInt > 1) {
					widthInt--;
				}
				width.setText(String.valueOf(widthInt));
			}
		});
		widthPanel.setLayout(new GridLayout(1, 3));
		widthPanel.add(minusButtonWidth);
		widthPanel.add(width);
		widthPanel.add(plusButtonWidth);
	}

	private void initControlPanel() {
		controlPanel = new JPanel();
		controlPanel.setBackground(Color.white);
		controlPanel.setLayout(new GridLayout(1, 2));
		okButton = new JButton("OK");
		okButton.setPreferredSize(new Dimension(40, 25));
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (name.getText().equals("") || name.getText().equals("ERROR") || !(name.getText().matches("[0-9a-zA-Z]+")) || Integer.parseInt(length.getText()) > maxLen
						|| Integer.parseInt(width.getText()) > maxWid) {
					name.setText("ERROR");
				} else {
					newMapDialogInfo = new NewMapDialogInfo(name.getText(), Integer.parseInt(length.getText()), Integer.parseInt(width.getText()));
					setVisible(false);
				}
			}
		});
		cancelButton = new JButton("Annuler");
		cancelButton.setPreferredSize(new Dimension(40, 25));
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		controlPanel.add(okButton);
		controlPanel.add(cancelButton);
	}

}
