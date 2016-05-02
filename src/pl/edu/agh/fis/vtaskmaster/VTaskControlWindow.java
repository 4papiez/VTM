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
public class VTaskControlWindow extends JDialog { //TODO VTCW ??? add awesomely cool light witch informs whether the task is in progress or not

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	Timer tmrH;
	boolean active;
	
	enum VTCState{
		vtcwStarted,
		vtcwPaused,
	}
	
	VTCState state = VTCState.vtcwStarted;
	JButton VTPlay;
	JButton VTPause;
	JButton VTStop;
	
	JLabel lblVTimeHours;
	JLabel lblVTimeMinutes;
	JLabel lblVTaskName;

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
		
		VTStop = new JButton(""); //TODO VTCW implement STOP button, after defining proper behavior of stopping program
		VTStop.setBounds(240, 5, 40, 40);
		
		VTPlay.setIcon(new ImageIcon(System.getProperty("user.dir")+"/src/pl/edu/agh/fis/vtaskmaster/vtplay.png"));
		VTPause.setIcon(new ImageIcon(System.getProperty("user.dir")+"/src/pl/edu/agh/fis/vtaskmaster/vtpause.png"));
		VTStop.setIcon(new ImageIcon(System.getProperty("user.dir")+"/src/pl/edu/agh/fis/vtaskmaster/vtstop.png")); 	        
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
	}
	void setTask(String taskName, String taskTimeHours, String taskTimeMins){
		lblVTaskName.setText(taskName);
		lblVTimeHours.setText(taskTimeHours);
		lblVTimeMinutes.setText(taskTimeMins);
	}
}