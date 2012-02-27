import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Editor extends JFrame {
	private JMenuBar menuBar = new JMenuBar();
	private JMenu file = new JMenu("File");
		
	private JMenuItem item1 = new JMenuItem("New");
	private JMenuItem item2 = new JMenuItem("Load");
	private JMenuItem item3 = new JMenuItem("Save");
	private JMenuItem item4 = new JMenuItem("Save As");
	private JMenuItem item5 = new JMenuItem("Quit");
	
	private Map currentMap;

	private String currentPath="";
	
	public Editor(){
		this.setSize(800, 600);
		this.setTitle("Map Editor");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
			
		//On initialise nos menus
		//--------------------------			
		this.InitialiseMenus();
			
		this.setJMenuBar(menuBar);
		this.setVisible(true);
		}
	private void InitialiseMenus(){
		this.file.add(item1);
		item1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				NewMapDialog newMapDialog=new NewMapDialog(null, "Create new Map", true);
				NewMapDialogInfo newMapDialogInfo=newMapDialog.showDialog();
				currentMap=new Map(newMapDialogInfo.getLength(),newMapDialogInfo.getWidth(),newMapDialogInfo.getName());
				System.out.println(currentMap.toXMLString());
			}});
		
		this.file.add(item2);
		item2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser(".");
				FileFilter filter1 = new ExtensionFileFilter("MAP", new String[] { "MAP" });
				fc.setFileFilter(filter1);
				int status = fc.showOpenDialog(null);
		        if (status == JFileChooser.APPROVE_OPTION) {
		        	currentPath = fc.getSelectedFile().getPath();
		            try {
						BufferedReader  in = new BufferedReader(new FileReader(currentPath));
						String line; 
						line=in.readLine();
						currentMap=new Map(line);
						in.close(); 
						System.out.println(currentMap.toXMLString());
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
		        } else if (status == JFileChooser.CANCEL_OPTION) {
		            System.out.println(JFileChooser.CANCEL_OPTION);
		        }else {
		            System.out.println("erreur select file");
		        }}});
		
		this.file.add(item3);
		item3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(currentPath.equals("")){
					
				}
				else{
					
				}
			}});
		
		this.file.add(item4);
		item4.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
			}});
		
		this.file.add(item5);
		item5.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}});
        this.menuBar.add(file);
	}
	
	
	
	public static void main(String[] argv) {
		Editor e=new Editor();
	}
}
