package pl.edu.agh.fis.vtaskmaster;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;


public class VTStats extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8271724930276644469L;
	private final JPanel contentPanel = new JPanel();
	JLabel lblEffectiveness, lblEffectivenessFav,
	       lblEffectivenessNFav, lblOnTime,
	       lblDifference, lblDifferenceProc,
	       lblTaskCount, lblTimeCount, lblAvgPrior,
	       lblEffectivenessV, lblEffectivenessFavV,
	       lblEffectivenessNFavV, lblOnTimeV,
	       lblDifferenceV, lblDifferenceProcV,
	       lblTaskCountV, lblTimeCountV, lblAvgPriorV;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VTStats dialog = new VTStats();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VTStats() {
		setBounds(100, 100, 300, 240);
		getContentPane().setLayout(null);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(null);
		setContentPane(contentPanel);
		setResizable(false);
		
		lblEffectiveness = new JLabel("Effectiveness - general:");
		lblEffectiveness.setBounds(10, 10, 200, 20);
		contentPanel.add(lblEffectiveness);
		
		lblEffectivenessFav = new JLabel("Effectiveness - favourites:");
		lblEffectivenessFav.setBounds(10, 30 , 200, 20);
		contentPanel.add(lblEffectivenessFav);
		
		lblEffectivenessNFav = new JLabel("Effectiveness - non-favourites:");
		lblEffectivenessNFav.setBounds(10, 50,230,20);
		contentPanel.add(lblEffectivenessNFav);
		
		lblOnTime = new JLabel("Finished on time:");
		lblOnTime.setBounds(10, 70, 200, 20);
		contentPanel.add(lblOnTime);
		
		lblDifference = new JLabel("Time difference:");
		lblDifference.setBounds(10, 90, 200, 20);
		contentPanel.add(lblDifference);
		
		lblDifferenceProc = new JLabel("Time difference %:");
		lblDifferenceProc.setBounds(10, 110,200, 20);
		contentPanel.add(lblDifferenceProc);
		
		lblTaskCount = new JLabel("Task count:");
		lblTaskCount.setBounds(10, 130, 200, 20);
		contentPanel.add(lblTaskCount);
		
		lblTimeCount = new JLabel("Time count:");
		lblTimeCount.setBounds(10, 150, 200, 20);
		contentPanel.add(lblTimeCount);
		
		lblAvgPrior = new JLabel("Average prior:");
		lblAvgPrior.setBounds(10, 170, 200, 20);
		contentPanel.add(lblAvgPrior);
		
		lblEffectivenessV = new JLabel("0");
		lblEffectivenessV.setBounds(250, 10, 200, 20);
		contentPanel.add(lblEffectivenessV);
		
		lblEffectivenessFavV = new JLabel("0");
		lblEffectivenessFavV.setBounds(250, 30 , 200, 20);
		contentPanel.add(lblEffectivenessFavV);
		
		lblEffectivenessNFavV = new JLabel("0");
		lblEffectivenessNFavV.setBounds(250, 50,230,20);
		contentPanel.add(lblEffectivenessNFavV);
		
		lblOnTimeV = new JLabel("0");
		lblOnTimeV.setBounds(250, 70, 200, 20);
		contentPanel.add(lblOnTimeV);
		
		lblDifferenceV = new JLabel("0");
		lblDifferenceV.setBounds(250, 90, 200, 20);
		contentPanel.add(lblDifferenceV);
		
		lblDifferenceProcV = new JLabel("0");
		lblDifferenceProcV.setBounds(250, 110,200, 20);
		contentPanel.add(lblDifferenceProcV);
		
		lblTaskCountV = new JLabel("0");
		lblTaskCountV.setBounds(250, 130, 200, 20);
		contentPanel.add(lblTaskCountV);
		
		lblTimeCountV = new JLabel("0");
		lblTimeCountV.setBounds(250, 150, 200, 20);
		contentPanel.add(lblTimeCountV);
		
		lblAvgPriorV = new JLabel("0");
		lblAvgPriorV.setBounds(250, 170, 200, 20);
		contentPanel.add(lblAvgPriorV);
	}
}
