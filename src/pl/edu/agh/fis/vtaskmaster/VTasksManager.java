package pl.edu.agh.fis.vtaskmaster;

import java.awt.Color;
import java.awt.EventQueue;
import java.lang.Integer;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JOptionPane;

/**
 * 
 * @author Kamil Piastowicz
 * @version preprepre0.000000001zeta
 *
 */
public class VTasksManager extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel contentPane;
	JTextField textField;
	JTable tblFavourites;
	JTable tblHistory;
	JButton btnDelete;
	JTextPane textPane;
	JSpinner spinner;
	JSpinner spinner_1;
	JSpinner spinner_2;
	JTabbedPane tabbedPane;
	enum returnState{VTM_RUN, VTM_TODO, VTM_REJECTED}
	returnState rS;
	JButton btnRun;
	JButton btnFromList;
	JButton btnToList;
	JButton btnToDo;
	JButton btnFavourites;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VTasksManager frame = new VTasksManager();
					frame.setVisible(true);
					frame.setTitle("Manage your tasks");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VTasksManager() {
		setTitle("Manage your tasks");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		new JOptionPane();
		contentPane = new JPanel();
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(12, 16, 172, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textPane = new JTextPane();
		textPane.setBounds(12, 50, 172, 105);
		contentPane.add(textPane);
		
		JLabel lblTaskDesc = new JLabel("Task description:");
		lblTaskDesc.setBounds(22, 35, 142, 15);
		contentPane.add(lblTaskDesc);
		
		JLabel lblTaskName = new JLabel("Task name:");
		lblTaskName.setBounds(22, 2, 108, 15);
		contentPane.add(lblTaskName);
		
		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		spinner.setBounds(12, 181, 40, 20);		
		contentPane.add(spinner);
		
		spinner_1 = new JSpinner();
		spinner_1.setModel(new SpinnerNumberModel(0, 0, 23, 1));
		spinner_1.setBounds(89, 181, 40, 20);
		contentPane.add(spinner_1);
		
		spinner_2 = new JSpinner();
		spinner_2.setModel(new SpinnerNumberModel(0, 0, 59, 1));
		spinner_2.setBounds(124, 181, 40, 20);
		contentPane.add(spinner_2);
		
		JLabel lblPrior = new JLabel("Priority:");
		lblPrior.setBounds(12, 167, 142, 15);
		contentPane.add(lblPrior);
		
		JLabel lblTime = new JLabel("Time:");
		lblTime.setBounds(89, 167, 53, 15);
		contentPane.add(lblTime);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(232, 16, 206, 185);
		contentPane.add(tabbedPane);
		
		JScrollPane scrollPane = new JScrollPane();
		tabbedPane.addTab("Favourites", null, scrollPane, null);
		
		tblFavourites = new JTable();
		tblFavourites.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblFavourites.setAutoCreateRowSorter(true);
		tblFavourites.setFillsViewportHeight(true);
		tblFavourites.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		scrollPane.setViewportView(tblFavourites);
		tblFavourites.setModel(new DefaultTableModel(new Object[][] {{null,null,null,null}},new String[] {"Task", "Prior", "ETime", "ATime"}));
		tblFavourites.getColumnModel().getColumn(0).setPreferredWidth(67);
		tblFavourites.getColumnModel().getColumn(0).setMinWidth(67);
		tblFavourites.getColumnModel().getColumn(0).setMaxWidth(67);
		tblFavourites.getColumnModel().getColumn(1).setPreferredWidth(40);
		tblFavourites.getColumnModel().getColumn(1).setMinWidth(40);
		tblFavourites.getColumnModel().getColumn(1).setMaxWidth(40);
		tblFavourites.getColumnModel().getColumn(2).setPreferredWidth(40);
		tblFavourites.getColumnModel().getColumn(2).setMinWidth(40);
		tblFavourites.getColumnModel().getColumn(2).setMaxWidth(40);
		tblFavourites.getColumnModel().getColumn(3).setPreferredWidth(40);
		tblFavourites.getColumnModel().getColumn(3).setMinWidth(40);
		tblFavourites.getColumnModel().getColumn(3).setMaxWidth(40);
		tblFavourites.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblFavourites.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		scrollPane.setViewportView(tblFavourites);
		scrollPane.getViewport().setView(tblFavourites);
		tblFavourites.setFillsViewportHeight(true);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		tabbedPane.addTab("History", null, scrollPane_2, null);
		
		tblHistory = new JTable();
		tblHistory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblHistory.setAutoCreateRowSorter(true);
		tblHistory.setFillsViewportHeight(true);
		tblHistory.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		scrollPane_2.setViewportView(tblHistory);
		tblHistory.setModel(new DefaultTableModel(
			new Object[][] {{null,null,null,null}},new String[] {"Task", "Prior", "ETime", "ATime"}));
		tblHistory.getColumnModel().getColumn(0).setPreferredWidth(67);
		tblHistory.getColumnModel().getColumn(0).setMinWidth(67);
		tblHistory.getColumnModel().getColumn(0).setMaxWidth(67);
		tblHistory.getColumnModel().getColumn(1).setPreferredWidth(40);
		tblHistory.getColumnModel().getColumn(1).setMinWidth(40);
		tblHistory.getColumnModel().getColumn(1).setMaxWidth(40);
		tblHistory.getColumnModel().getColumn(2).setPreferredWidth(40);
		tblHistory.getColumnModel().getColumn(2).setMinWidth(40);
		tblHistory.getColumnModel().getColumn(2).setMaxWidth(40);
		tblHistory.getColumnModel().getColumn(3).setPreferredWidth(40);
		tblHistory.getColumnModel().getColumn(3).setMinWidth(40);
		tblHistory.getColumnModel().getColumn(3).setMaxWidth(40);
		tblHistory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblHistory.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		scrollPane_2.setViewportView(tblHistory);
		scrollPane_2.getViewport().setView(tblHistory);
		tblHistory.setFillsViewportHeight(true);
		
		btnDelete = new JButton("Delete");
		btnDelete.setBounds(157, 213, 124, 25);
		contentPane.add(btnDelete);
		
		btnRun = new JButton("Run now");
		btnRun.setBounds(284, 210, 142, 50);
		contentPane.add(btnRun);
		
		btnToDo = new JButton("Add to TO-DO");
		btnToDo.setBounds(12, 210, 142, 50);
		contentPane.add(btnToDo);
		
		btnToList = new JButton(">");
		btnToList.setBounds(186, 50, 45, 40);
		contentPane.add(btnToList);
		
		btnFromList = new JButton("<");
		btnFromList.setBounds(186, 91, 45, 40);
		contentPane.add(btnFromList);
		
		btnFavourites = new JButton("Favourites");
		btnFavourites.setBounds(157, 235, 124, 25);
		contentPane.add(btnFavourites);
		
	}
	/**
	 * 
	 * @param tbl - table to find empty row in
	 * @return i - index of empty row in the given table
	 */
	static protected int tblFindEmptyRow(JTable tbl) {
		int i = 0;
		while(tbl.getValueAt(i,0) != null) i++;
		return i;
	}
	/**
	 * 
	 * @param time - String representation of time to be parsed to int
	 * @param minute - defines if method should return minutes/true or hours/false
	 * @return int time-elem - number of minutes and hours
	 */
	static int getHour(String time, boolean minute) {
		if(time.length() == 5 && minute){
			return ((Integer.parseInt((new Character(time.charAt(3)).toString())))* 10 + Integer.parseInt((new Character(time.charAt(4)).toString())));
		}
		else if(time.length() == 4 && minute){
			return ((Integer.parseInt((new Character(time.charAt(2)).toString())))* 10 + Integer.parseInt((new Character(time.charAt(3)).toString())));
		}
		else if(time.length() == 5 && !minute){
			return ((Integer.parseInt((new Character(time.charAt(0)).toString())))* 10 + Integer.parseInt((new Character(time.charAt(1)).toString())));
		}
		else if(time.length() == 4 && !minute){
			return ((Integer.parseInt((new Character(time.charAt(0)).toString()))));
		}
		return 0;
	}
}