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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Editor extends JFrame {
	private JMenuBar menuBar = new JMenuBar();
	private JMenu file = new JMenu("File");
		
	private JMenuItem newMenu = new JMenuItem("New");
	private JMenuItem loadMenu = new JMenuItem("Load");
	private JMenuItem saveMenu = new JMenuItem("Save");
	private JMenuItem saveAsMenu = new JMenuItem("Save As");
	private JMenuItem quitMenu = new JMenuItem("Quit");
	
	
	private JPanel pantest = new JPanel();
	private TileEditionPan tileEditionPan=new TileEditionPan();
	private JSplitPane split;
	
	private Map currentMap;

	private String currentPath="";
	
	public Editor(){
		this.setSize(1000, 800);
		this.setTitle("Map Editor");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
			
		//On initialise nos menus
		//--------------------------			
		this.InitialiseMenus();
		
		//this.getContentPane().add(pantest);
		//this.getContentPane().add(tileEditionPan);
		split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pantest, tileEditionPan);
		this.getContentPane().add(split, BorderLayout.CENTER);
		split.setDividerLocation(750);
		UpdateSaveButton();	
		this.setJMenuBar(menuBar);
		this.setVisible(true);
		}
	
	
	private void InitialiseMenus(){
		this.file.add(newMenu);
		newMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				NewMapDialog newMapDialog=new NewMapDialog(null, "Create new Map", true);
				NewMapDialogInfo newMapDialogInfo=newMapDialog.showDialog();
				if(newMapDialogInfo.isValid()){
					currentMap=new Map(newMapDialogInfo.getLength(),newMapDialogInfo.getWidth(),newMapDialogInfo.getName());
					System.out.println(currentMap.toXMLString());
				}
			}});
		
		this.file.add(loadMenu);
		loadMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser(".");
				FileFilter filter1 = new ExtensionFileFilter("MAP", new String[] { "MAP" });
				fc.setFileFilter(filter1);
				int status = fc.showOpenDialog(null);
		        if (status == JFileChooser.APPROVE_OPTION) {
		        	currentPath = fc.getSelectedFile().getPath();
		            try {
						BufferedReader in = new BufferedReader(new FileReader(currentPath));
						currentMap=new Map(in.readLine());
						UpdateSaveButton();
						in.close(); 
						System.out.println(currentMap.toXMLString());
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} 
		        } else if (status == JFileChooser.CANCEL_OPTION) {
		            System.out.println(JFileChooser.CANCEL_OPTION);
		        }else {
		            System.out.println("erreur select file");
		        }}});
		
		this.file.add(saveMenu);
		saveMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					BufferedWriter out = new BufferedWriter(new FileWriter(currentPath+currentMap.getName()+".map"));
					out.write(currentMap.toXMLString());
					out.close(); 
					System.out.println(currentMap.toXMLString());
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}});
		
		this.file.add(saveAsMenu);
		saveAsMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser(".");
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int status = fc.showOpenDialog(null);
				if (status == JFileChooser.APPROVE_OPTION) {
		        	currentPath = fc.getSelectedFile().getPath()+"/";
		        	UpdateSaveButton();
		            try {
		            	BufferedWriter out = new BufferedWriter(new FileWriter(currentPath+currentMap.getName()+".map"));
						out.write(currentMap.toXMLString());
						out.close(); 
						System.out.println(currentMap.toXMLString());
					} catch (FileNotFoundException e){
						e.printStackTrace();
					} catch (IOException e){
						e.printStackTrace();
					} 
		        } else if (status == JFileChooser.CANCEL_OPTION) {
		            System.out.println(JFileChooser.CANCEL_OPTION);
		        }else {
		            System.out.println("erreur select file");
		        }}});
		
		this.file.add(quitMenu);
		quitMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}});
        this.menuBar.add(file);
	}
	
	private void UpdateSaveButton(){
		if(currentPath.equals("")){
			saveMenu.setEnabled(false);
		}else{
			saveMenu.setEnabled(true);
		}
	}
	
	public static void main(String[] argv) {
		Editor e=new Editor();
	}
}
