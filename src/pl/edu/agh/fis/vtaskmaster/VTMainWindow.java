package pl.edu.agh.fis.vtaskmaster;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

/**
 * 
 * @author Kamil Piastowicz
 * @version preprepre0.000000001zeta
 *
 */
public class VTMainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	VirtualTaskmaster controller;
	JPanel contentPane;
	JTable tblToDo;
	JButton btnDelete, btnRun, btnStats, btnManageTasks;
	JLabel[] vtcwLblTab;
	JButton[] vtcwBtnTab;

	/**
	 * Create the frame.
	 */
	public VTMainWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		setResizable(false);
		setTitle("VirtualTaskmaster");
		setBounds(100, 100, 455, 420);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane.setLayout(null);
	    JLabel logoLabel = new JLabel(new ImageIcon(System.getProperty("user.dir")+"/src/pl/edu/agh/fis/vtaskmaster/vtaskmaster.png")); //FIXME VT LOGO - better picture needed
	    System.out.print(System.getProperty("user.dir"));
	    logoLabel.setBounds(75,6,305,40);
	    contentPane.add(logoLabel);	
	    
	    vtcwBtnTab = new JButton[5];
	    vtcwLblTab = new JLabel[5];
	    
	    vtcwBtnTab[0] = new JButton("1");
		vtcwBtnTab[0].setBounds(21, 170, 42, 40);
		contentPane.add(vtcwBtnTab[0]);
		
		vtcwBtnTab[1] = new JButton("2");
		vtcwBtnTab[1].setBounds(21, 208, 42, 40);
		contentPane.add(vtcwBtnTab[1]);
		
		vtcwBtnTab[2] = new JButton("3");
		vtcwBtnTab[2].setBounds(21, 244, 42, 40);
		contentPane.add(vtcwBtnTab[2]);
		
		vtcwBtnTab[3] = new JButton("4");
		vtcwBtnTab[3].setBounds(21, 280, 42, 40);
		contentPane.add(vtcwBtnTab[3]);
		
		vtcwBtnTab[4] = new JButton("5");
		vtcwBtnTab[4].setBounds(21, 317, 42, 40);
		contentPane.add(vtcwBtnTab[4]);
		
		vtcwLblTab[0] = new JLabel("Empty slot");
		vtcwLblTab[0].setBounds(69, 170, 150, 40);
		contentPane.add(vtcwLblTab[0]);
		
		vtcwLblTab[1] = new JLabel("Empty slot");
		vtcwLblTab[1].setBounds(69, 208, 150, 40);
		contentPane.add(vtcwLblTab[1]);
		
		vtcwLblTab[2] = new JLabel("Empty slot");
		vtcwLblTab[2].setBounds(69, 244, 150, 40);
		contentPane.add(vtcwLblTab[2]);
		
		vtcwLblTab[3] = new JLabel("Empty slot");
		vtcwLblTab[3].setBounds(69, 280, 150, 40);
		contentPane.add(vtcwLblTab[3]);
		
		vtcwLblTab[4] = new JLabel("Empty slot");
		vtcwLblTab[4].setBounds(69, 317, 150, 40);
		contentPane.add(vtcwLblTab[4]);
		
		btnRun = new JButton("Run");
		btnRun.setBounds(233, 360, 109, 20);
		contentPane.add(btnRun);
		
		btnDelete = new JButton("Delete");
		btnDelete.setBounds(343, 360, 100, 20);
		contentPane.add(btnDelete);
		
		btnManageTasks = new JButton("Manage Tasks");
		btnManageTasks.setBounds(29, 58, 200, 50);
		contentPane.add(btnManageTasks);
		
		btnStats = new JButton("Stats");
		btnStats.setBounds(29, 108, 200, 50);
		contentPane.add(btnStats);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(237, 58, 206, 299);
		contentPane.add(scrollPane_1);
		
		tblToDo = new JTable();
		tblToDo.setAutoCreateRowSorter(true);
		tblToDo.setModel(new DefaultTableModel(new Object[][] {{null,null,null,null}}, 
				         new String[] {"Task", "Prior", "ETime", "ATime"}));
		tblToDo.getColumnModel().getColumn(0).setPreferredWidth(67);
		tblToDo.getColumnModel().getColumn(0).setMinWidth(67);
		tblToDo.getColumnModel().getColumn(0).setMaxWidth(67);
		tblToDo.getColumnModel().getColumn(1).setPreferredWidth(40);
		tblToDo.getColumnModel().getColumn(1).setMinWidth(40);
		tblToDo.getColumnModel().getColumn(1).setMaxWidth(40);
		tblToDo.getColumnModel().getColumn(2).setPreferredWidth(40);
		tblToDo.getColumnModel().getColumn(2).setMinWidth(40);
		tblToDo.getColumnModel().getColumn(2).setMaxWidth(40);
		tblToDo.getColumnModel().getColumn(3).setPreferredWidth(40);
		tblToDo.getColumnModel().getColumn(3).setMinWidth(40);
		tblToDo.getColumnModel().getColumn(3).setMaxWidth(40);
		tblToDo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblToDo.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		scrollPane_1.setViewportView(tblToDo);
		scrollPane_1.getViewport().setView(tblToDo);
		tblToDo.setFillsViewportHeight(true);
	}
	/**
	 * 
	 * @param toFill - integer to be properly formatted for time display
	 * @return properly formatted string
	 */
	static String timeFiller(int toFill){
		String t = toFill+"";
		if(toFill < 10) t = "0"+t; 
		return t;
	}
}