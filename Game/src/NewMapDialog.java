import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NewMapDialog extends JDialog {
	private NewMapDialogInfo newMapDialogInfo = new NewMapDialogInfo();
	private JLabel nameLabel, lengthLabel, widthLabel,icon;
	private JTextField name,length,width;
	
	public NewMapDialog(JFrame parent, String title, boolean modal){
		super(parent, title, modal);
		this.setSize(400, 300);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.initComponent();
	}
	
	public NewMapDialogInfo showDialog(){
		this.setVisible(true);		
		return this.newMapDialogInfo;		
	}
	
	private void initComponent(){
		//Icone
		icon = new JLabel(new ImageIcon("images/scroll.jpg"));
		JPanel panIcon = new JPanel();
		panIcon.setBackground(Color.white);
		panIcon.setLayout(new BorderLayout());
		panIcon.add(icon);
		
		//Name
		JPanel panNom = new JPanel();
		panNom.setBackground(Color.white);
		panNom.setPreferredSize(new Dimension(240, 60));
		panNom.setBorder(BorderFactory.createTitledBorder("Name of the map"));
		name = new JTextField();
		name.setPreferredSize(new Dimension(100, 25));
		nameLabel = new JLabel("Select a name :");
		panNom.add(nameLabel);
		panNom.add(name);
		name.setText("");
		
		//Length
		JPanel panLength = new JPanel();
		panLength.setBackground(Color.white);
		panLength.setPreferredSize(new Dimension(240, 60));
		panLength.setBorder(BorderFactory.createTitledBorder("Length of the map"));
		length = new JTextField();
		length.setPreferredSize(new Dimension(35, 25));
		length.setEditable(false);
		length.setText("10");
		lengthLabel = new JLabel("Select a length:");
		
		JButton plusButtonLength = new JButton("+");		
		plusButtonLength.setFont(new Font("Arial" ,Font.BOLD,10));
		plusButtonLength.setPreferredSize(new Dimension(40, 25));		
		plusButtonLength.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				int lengthInt=Integer.parseInt(length.getText());
				if(lengthInt<50){lengthInt++;}
				length.setText(String.valueOf(lengthInt));
			}
			});
		
		JButton minusButtonLength = new JButton("-");
		minusButtonLength.setFont(new Font("Arial" ,Font.BOLD,10));
		minusButtonLength.setPreferredSize(new Dimension(40, 25));
		minusButtonLength.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				int lengthInt=Integer.parseInt(length.getText());
				if(lengthInt>1){lengthInt--;}
				length.setText(String.valueOf(lengthInt));
			}
			});
		
		panLength.add(lengthLabel);
		panLength.add(minusButtonLength);
		panLength.add(length);
		panLength.add(plusButtonLength);
		
		
		//Width
		JPanel panWidth = new JPanel();
		panWidth.setBackground(Color.white);
		panWidth.setPreferredSize(new Dimension(240, 60));
		panWidth.setBorder(BorderFactory.createTitledBorder("Width of the map"));
		width = new JTextField();
		width.setEditable(false);
		width.setPreferredSize(new Dimension(35, 25));
		width.setText("10");
		
		JButton plusButtonWidth = new JButton("+");
		plusButtonWidth.setFont(new Font("Arial" ,Font.BOLD,10));
		plusButtonWidth.setPreferredSize(new Dimension(40, 25));
		plusButtonWidth.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				int widthInt=Integer.parseInt(width.getText());
				if(widthInt<50){widthInt++;}
				width.setText(String.valueOf(widthInt));
			}
			});
		
		JButton minusButtonWidth = new JButton("-");
		minusButtonWidth.setFont(new Font("Arial" ,Font.BOLD,10));
		minusButtonWidth.setPreferredSize(new Dimension(40, 25));	
		minusButtonWidth.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				int widthInt=Integer.parseInt(width.getText());
				if(widthInt>1){widthInt--;}
				width.setText(String.valueOf(widthInt));
			}
			});
		
		widthLabel = new JLabel("Select a width :");	
		panWidth.add(widthLabel);
		panWidth.add(minusButtonWidth);
		panWidth.add(width);
		panWidth.add(plusButtonWidth);
		
		
		JPanel content = new JPanel();
		content.setBackground(Color.white);
		content.add(panNom);
		content.add(panLength);
		content.add(panWidth);
		
		JPanel control = new JPanel();
		JButton okBouton = new JButton("OK");
		
		okBouton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {	
				if(name.getText().equals("") || name.getText().equals("ERROR") || !(name.getText().matches("[0-9a-zA-Z]+"))){
					name.setText("ERROR");
				}
				else{
					newMapDialogInfo = new NewMapDialogInfo(name.getText(),Integer.parseInt(length.getText()),Integer.parseInt(width.getText()));
					setVisible(false);
				}
			}		
		});
		
		JButton cancelBouton = new JButton("Annuler");
		cancelBouton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}			
		});
		
		control.add(okBouton);
		control.add(cancelBouton);
		
		this.getContentPane().add(panIcon, BorderLayout.WEST);
		this.getContentPane().add(content, BorderLayout.CENTER);
		this.getContentPane().add(control, BorderLayout.SOUTH);
	}
	
}
