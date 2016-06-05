package pl.edu.agh.fis.vtaskmaster;

import java.awt.EventQueue;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.Timer;
/**
 * 
 * @author Kamil Piastowicz
 * @version preprepre0.000000001zeta
 *
 */
public class VTaskControlWindow extends JDialog {
	enum VTCState{
		vtcwStarted,
		vtcwPaused,
		vtcwFinished,
	}	
	private static final long serialVersionUID = 1L;		
	//Panes//
	private JPanel contentPane;	
	//Input//
	JButton VTPlay, VTPause, VTStop;
	//Visual//
	JLabel lblVTimeHours, lblVTimeMinutes, lblVTaskName,lblInProgress;
	//Logic//
	VTCState state = VTCState.vtcwStarted;
	boolean active;
	Timer tmrH;
	String sep;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VTaskControlWindow frame = new VTaskControlWindow("", "", "");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VTaskControlWindow(String taskName, String taskTimeHours, String taskTimeMins) {
		active = false;
		setVisible(false);
		setResizable(false);
		
		sep = System.getProperty("file.separator");
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 294, 77);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		VTPlay = new JButton("");
		VTPlay.setBounds(160, 5, 40, 40);		
		
		VTPause = new JButton("");
		VTPause.setBounds(200, 5, 40, 40);
		
		VTStop = new JButton("");
		VTStop.setBounds(240, 5, 40, 40);
		
			        
		contentPane.add(VTPlay);
		contentPane.add(VTPause);
		contentPane.add(VTStop);
		
		lblVTimeHours = new JLabel(taskTimeHours);
		lblVTimeHours.setBounds(8, 20, 32, 15);
		contentPane.add(lblVTimeHours);
		
		lblVTimeMinutes = new JLabel(taskTimeMins);
		lblVTimeMinutes.setBounds(28, 20, 32, 15);
		contentPane.add(lblVTimeMinutes);
		
		JLabel lblVTimeColon = new JLabel(":");
		lblVTimeColon.setBounds(25, 20, 32, 15);
		contentPane.add(lblVTimeColon);
		
		lblVTaskName = new JLabel(taskName);
		lblVTaskName.setFont(new Font("Dialog", Font.BOLD, 18));
		lblVTaskName.setBounds(5, 5, 140, 15);
		contentPane.add(lblVTaskName);
		
		
		VTPlay.setIcon(new ImageIcon(System.getProperty("user.dir")+sep+"src"+sep+"pl"+sep+"edu"+sep+"agh"+sep+"fis"+sep+"vtaskmaster"+sep+"vtplay.png"));
		VTPause.setIcon(new ImageIcon(System.getProperty("user.dir")+sep+"src"+sep+"pl"+sep+"edu"+sep+"agh"+sep+"fis"+sep+"vtaskmaster"+sep+"vtpause.png"));
		VTStop.setIcon(new ImageIcon(System.getProperty("user.dir")+sep+"src"+sep+"pl"+sep+"edu"+sep+"agh"+sep+"fis"+sep+"vtaskmaster"+sep+"vtstop.png"));
		lblInProgress = new JLabel(new ImageIcon(System.getProperty("user.dir")+sep+"src"+sep+"pl"+sep+"edu"+sep+"agh"+sep+"fis"+sep+"vtaskmaster"+sep+"lighton.png"));
		lblInProgress.setBounds(145,30,10,10);
		contentPane.add(lblInProgress);
		
	}
	void setTask(String taskName, String taskTimeHours, String taskTimeMins){
		lblVTaskName.setText(taskName);
		lblVTimeHours.setText(taskTimeHours);
		lblVTimeMinutes.setText(taskTimeMins);
	}
}