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

public class Editor extends JFrame {
	private JMenuBar menuBar = new JMenuBar();
	private JMenu file = new JMenu("File");
		
	private JMenuItem item1 = new JMenuItem("New");
	private JMenuItem item2 = new JMenuItem("Load");
	private JMenuItem item3 = new JMenuItem("Save");
	private JMenuItem item4 = new JMenuItem("Save As");
	private JMenuItem item5 = new JMenuItem("Quit");
	
	private Map currentMap;

	
	public Editor(){
		this.setSize(1200, 800);
		this.setTitle("Map Editor");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
			
		//On initialise nos menus
		//--------------------------			
		this.file.add(item1);
		item1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				NewMapDialog newMapDialog=new NewMapDialog(null, "Create new Map", true);
				NewMapDialogInfo newMapDialogInfo=newMapDialog.showDialog();
				currentMap=new Map(newMapDialogInfo.getLength(),newMapDialogInfo.getWidth(),newMapDialogInfo.getName());
				System.out.println(currentMap.toXMLString());
			}});
		this.file.add(item2);			
		this.file.add(item3);
		this.file.add(item4);
		this.file.add(item5);
		item5.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}});
        this.menuBar.add(file);
			
		this.setJMenuBar(menuBar);
		this.setVisible(true);
		}
	
	
	
	public static void main(String[] argv) {
		Editor e=new Editor();
	}
}
