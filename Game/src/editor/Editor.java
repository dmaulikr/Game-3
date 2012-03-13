package editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;

import displaymanager.DisplayManager;

import entity.Map;
import entity.TileTexture;
import gamemanager.Game;

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
	private JMenu preview = new JMenu("Preview");

	private JMenuItem newMenu = new JMenuItem("New");
	private JMenuItem loadMenu = new JMenuItem("Load");
	private JMenuItem saveMenu = new JMenuItem("Save");
	private JMenuItem saveAsMenu = new JMenuItem("Save As");
	private JMenuItem quitMenu = new JMenuItem("Quit");

	private JMenuItem showPreview = new JMenuItem("Show Preview");

	private JPanel selectionPan = new JPanel();
	private TileEditionPan tileEditionPan = new TileEditionPan(this);
	private JSplitPane split;

	private Map currentMap;
	private MapButtonCanvas currentMapCanvas;

	private String currentPath = "";

	public Editor() {
		this.setSize(1000, 800);
		this.setTitle("Map Editor");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		// On initialise nos menus
		// --------------------------
		this.InitialiseMenus();
		this.MiseEnPage();
		this.setJMenuBar(menuBar);
		this.setVisible(true);

	}

	private void MiseEnPage() {
		split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, selectionPan, tileEditionPan);
		this.getContentPane().add(split, BorderLayout.CENTER);
		split.setDividerLocation(750);
	}

	private void ChangeMap(Map newMap) {
		currentMap = newMap;
		currentMapCanvas = new MapButtonCanvas(currentMap, tileEditionPan);
		selectionPan.removeAll();
		selectionPan.setLayout(new BorderLayout(5, 5));
		selectionPan.add(currentMapCanvas, BorderLayout.CENTER);
		selectionPan.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		this.setVisible(true);
	}

	public Map getMap() {
		return currentMap;
	}

	public void setCanvasFocusOn(int X, int Y) {
		currentMapCanvas.setFocusOn(X, Y);
	}

	private void InitialiseMenus() {
		this.file.add(newMenu);
		FileNewAction fna = new FileNewAction();
		newMenu.addActionListener(fna);

		this.file.add(loadMenu);
		FileLoadAction fla = new FileLoadAction();
		loadMenu.addActionListener(fla);

		this.file.add(saveMenu);
		FileSaveAction fsa = new FileSaveAction();
		saveMenu.addActionListener(fsa);

		this.file.add(saveAsMenu);
		FileSaveAsAction fsaa = new FileSaveAsAction();
		saveAsMenu.addActionListener(fsaa);

		this.file.add(quitMenu);
		FileQuitAction fqa = new FileQuitAction();
		quitMenu.addActionListener(fqa);

		this.menuBar.add(file);

		this.preview.add(showPreview);
		ShowPreview sp = new ShowPreview();
		showPreview.addActionListener(sp);

		this.menuBar.add(preview);

		UpdateSaveButton();
	}

	private void UpdateSaveButton() {
		if (currentPath.equals("")) {
			saveMenu.setEnabled(false);
		} else {
			saveMenu.setEnabled(true);
		}
	}

	public void updateCancas() {
		currentMapCanvas.UpdateCanvas();
	}

	public class FileNewAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			NewMapDialog newMapDialog = new NewMapDialog(null, "Create new Map", true);
			NewMapDialogInfo newMapDialogInfo = newMapDialog.showDialog();
			if (newMapDialogInfo.isValid()) {
				currentPath = "";
				Map newMap = new Map(newMapDialogInfo.getLength(), newMapDialogInfo.getWidth(), newMapDialogInfo.getName());
				ChangeMap(newMap);
				UpdateSaveButton();
			}
		}
	}

	public class FileLoadAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser(".");
			FileFilter filter1 = new ExtensionFileFilter("MAP", new String[] { "MAP" });
			fc.setFileFilter(filter1);
			int status = fc.showOpenDialog(null);
			if (status == JFileChooser.APPROVE_OPTION) {
				currentPath = fc.getSelectedFile().getPath();
				try {
					BufferedReader in = new BufferedReader(new FileReader(currentPath));
					String save = "";
					String buff = in.readLine();
					while (buff != null) {
						save += buff;
						buff = in.readLine();
					}

					Map newMap = new Map(save);
					in.close();

					UpdateSaveButton();
					ChangeMap(newMap);
				} catch (FileNotFoundException ex) {
					ex.printStackTrace();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			} else if (status == JFileChooser.CANCEL_OPTION) {
				System.out.println(JFileChooser.CANCEL_OPTION);
			} else {
				System.out.println("erreur select file");
			}
		}
	}

	public class FileSaveAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				BufferedWriter out = new BufferedWriter(new FileWriter(currentPath));
				out.write(currentMap.toXMLString());
				out.close();
				System.out.println(currentMap.toXMLString());
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public class FileSaveAsAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser(".");
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int status = fc.showOpenDialog(null);
			if (status == JFileChooser.APPROVE_OPTION) {
				currentPath = fc.getSelectedFile().getPath() + "/" + currentMap.getName() + ".map";
				UpdateSaveButton();
				try {
					BufferedWriter out = new BufferedWriter(new FileWriter(currentPath));
					out.write(currentMap.toXMLString());
					out.close();
					System.out.println(currentMap.toXMLString());
				} catch (FileNotFoundException ex) {
					ex.printStackTrace();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			} else if (status == JFileChooser.CANCEL_OPTION) {
				System.out.println(JFileChooser.CANCEL_OPTION);
			} else {
				System.out.println("erreur select file");
			}
		}
	}

	public class FileQuitAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

	public class ShowPreview implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (currentMap != null) {

				Game g = new Game(800,600,currentMap, 0);
				g.run();
			}
		}
	}

	public static void main(String[] argv) {
		//String home = System.getProperty("user.dir");
		//System.setProperty("org.lwjgl.librarypath", home + "/lwjgl");
		try {
			// Set cross-platform Java L&F (also called "Metal")
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			// handle exception
		} catch (ClassNotFoundException e) {
			// handle exception
		} catch (InstantiationException e) {
			// handle exception
		} catch (IllegalAccessException e) {
			// handle exception
		}

		Editor e = new Editor();
	}

}
