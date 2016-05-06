package pl.edu.agh.fis.vtaskmaster;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.table.DefaultTableModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Timer;

import pl.edu.agh.fis.vtaskmaster.VTaskControlWindow.VTCState;
import pl.edu.agh.fis.vtaskmaster.VTasksManager.returnState;

/**
 * 
 * @author Grzegorz Burzyński
 * @author Kamil Piastowicz
 * @version 0.000000002zeta
 *
 */
public class VirtualTaskmaster {

	private VTMainWindow vTMW;
	private VTasksManager vTM;
	private VTaskControlWindow[] vtcwTab;
	private VTaskControlWindow.VTCState[] state;
	private long[] currTime;
	private long[] startTime;
	private long[] elapsedTime;
	private Timer tmrMin;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VirtualTaskmaster VT = new VirtualTaskmaster();
					VT.vTMW.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the application.
	 */
	public VirtualTaskmaster() {
		initialize();
	}

	/**
	 * Initialize the components controlled.
	 */
	private void initialize() {
		vTMW = new VTMainWindow();
		vTM = new VTasksManager();
		vtcwTab = new VTaskControlWindow[5];
		currTime = new long[5];
		elapsedTime = new long[5];
		startTime = new long[5];
		state = new VTaskControlWindow.VTCState[5];
		for(int i = 0; i < 5; i++)
			vtcwTab[i] = new VTaskControlWindow("empty slot", "00", "00");
			

		vTM.setModal(true);
		vTM.setVisible(false);
	
	/**
	 * VirtualTaskmasterMainWindow event handlers
	 */
		vTMW.btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selRow = vTMW.tblToDo.getSelectedRow();
				if(selRow != -1 && vTMW.tblToDo.getValueAt(selRow, 0) != null){			
					((DefaultTableModel) vTMW.tblToDo.getModel()).removeRow(selRow);
				}else{
					JOptionPane.showMessageDialog(new JFrame(), "You need to select filled row.");
				}
			}
		});
		vTMW.btnRun.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selRow = vTMW.tblToDo.getSelectedRow();
				if(selRow != -1){
					int h = VTasksManager.getHour((String) vTMW.tblToDo.getValueAt(selRow, 2),false);
					int min = VTasksManager.getHour((String) vTMW.tblToDo.getValueAt(selRow, 2),true);
					handleVTCW(h,min,(String)vTMW.tblToDo.getValueAt(selRow, 0),(int)vTMW.tblToDo.getValueAt(selRow, 1));
					((DefaultTableModel) vTMW.tblToDo.getModel()).removeRow(selRow);
				}
			}
		});
		vTMW.btnManageTasks.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {	
				vTM.rS = returnState.VTM_REJECTED;
				vTM.setVisible(true);
				
				JTable tbl = (JTable)((((JScrollPane)vTM.tabbedPane.getSelectedComponent()).getViewport().getComponents()[0]));
				int selRow = tbl.getSelectedRow();
				if(vTM.rS == returnState.VTM_RUN){
					if(selRow == -1)
						selRow = VTasksManager.tblFindEmptyRow(tbl);
					    handleVTCW(VTasksManager.getHour((String) tbl.getValueAt(selRow, 2),false),
							   VTasksManager.getHour((String) tbl.getValueAt(selRow, 2),true),
							   (String)tbl.getValueAt(selRow, 0),
							   (int)(tbl.getValueAt(selRow, 1)));
				}else if(vTM.rS == returnState.VTM_TODO){
					if(selRow != -1 && tbl.getValueAt(selRow,0) != null){
						int eRow = VTasksManager.tblFindEmptyRow(vTMW.tblToDo);
						((DefaultTableModel)vTMW.tblToDo.getModel()).addRow(new Object[]{null,null,null,null});
						vTMW.tblToDo.setValueAt(tbl.getValueAt(selRow,0), eRow, 0);
						vTMW.tblToDo.setValueAt(tbl.getValueAt(selRow,1), eRow, 1);
						vTMW.tblToDo.setValueAt(tbl.getValueAt(selRow,2), eRow, 2);
						vTMW.tblToDo.setValueAt(tbl.getValueAt(selRow,3), eRow, 3);
					}else{
						JOptionPane.showMessageDialog(new JFrame(), "You need to select filled row.");
					}	
				}
			}
		});
		vTMW.vtcwBtnTab[0].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(vtcwTab[0].active){
						vtcwTab[0].setVisible(!vtcwTab[0].isVisible());
				}
			}
		});
		vTMW.vtcwBtnTab[1].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(vtcwTab[1].active){
						vtcwTab[1].setVisible(!vtcwTab[1].isVisible());
				}
			}
		});
		vTMW.vtcwBtnTab[2].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(vtcwTab[2].active){
						vtcwTab[2].setVisible(!vtcwTab[2].isVisible());
				}
			}
		});
		vTMW.vtcwBtnTab[3].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(vtcwTab[3].active){
						vtcwTab[3].setVisible(!vtcwTab[3].isVisible());
				}
			}
		});
		vTMW.vtcwBtnTab[4].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(vtcwTab[4].active){
						vtcwTab[4].setVisible(!vtcwTab[4].isVisible());
				}
			}
		});
		/**
		 * VirtualTasksManager event handlers
		 */
		vTM.btnDelete.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JTable tbl = (JTable)((((JScrollPane)vTM.tabbedPane.getSelectedComponent()).getViewport().getComponents()[0]));
				int selRow = tbl.getSelectedRow();
				if(selRow != -1 && tbl.getValueAt(selRow, 0) != null){			
					((DefaultTableModel) tbl.getModel()).removeRow(selRow);
				}else{
					JOptionPane.showMessageDialog(new JFrame(), "You need to select filled row.");
				}
			}
		});
		vTM.btnFavourites.addMouseListener(new MouseAdapter() { //FIXME VTM adding empty line to tblFavourites if user on tab "Favorites"
			@Override
			public void mouseClicked(MouseEvent e) {
				((DefaultTableModel) vTM.tblFavourites.getModel()).addRow(new Object[]{null,null,null,null});
				int row = VTasksManager.tblFindEmptyRow(vTM.tblFavourites);
				int selRow = vTM.tblHistory.getSelectedRow();
				if(selRow != -1 && vTM.tblHistory.getValueAt(selRow, 0)!=null)
				 {
					vTM.tblFavourites.setValueAt(vTM.tblHistory.getValueAt(selRow,0), row, 0);
					vTM.tblFavourites.setValueAt(vTM.tblHistory.getValueAt(selRow,1), row, 1);
					vTM.tblFavourites.setValueAt(vTM.tblHistory.getValueAt(selRow,2), row, 2);
					vTM.tblFavourites.setValueAt(vTM.tblHistory.getValueAt(selRow,3), row, 3);
				}else{
					JOptionPane.showMessageDialog(new JFrame(), "You need to select a filled row.");
				}
			}
		});
		vTM.btnRun.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JTable tbl = (JTable)((((JScrollPane)vTM.tabbedPane.getSelectedComponent()).getViewport().getComponents()[0]));
				int selRow = tbl.getSelectedRow();
				if(selRow != -1 && tbl.getValueAt(selRow, 0) != null){			
					vTM.rS = returnState.VTM_RUN;
					vTM.setVisible(false);
				}	
			}
		});
		vTM.btnToDo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
					vTM.rS = returnState.VTM_TODO;
					vTM.setVisible(false);
			}
		});
		vTM.btnToList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JTable tbl = (JTable)((((JScrollPane)vTM.tabbedPane.getSelectedComponent()).getViewport().getComponents()[0]));
				int selRow = tbl.getSelectedRow();
				if(selRow == -1)
					selRow = VTasksManager.tblFindEmptyRow(tbl);
				if(tbl.getValueAt(selRow, 0) == null){
					((DefaultTableModel) tbl.getModel()).addRow(new Object[]{null,null,null,null});
				}
				tbl.setValueAt(vTM.textField.getText(),selRow,0);
				tbl.setValueAt(vTM.spinner.getValue(),selRow,1);
				if((int)(vTM.spinner_2.getValue()) > 9){
					tbl.setValueAt(vTM.spinner_1.getValue()+":"+vTM.spinner_2.getValue(),selRow,2);
				}
				else{
					tbl.setValueAt(vTM.spinner_1.getValue()+":0"+vTM.spinner_2.getValue(),selRow,2);					}					
				}
		});
		vTM.btnFromList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JTable tbl = (JTable)((((JScrollPane)vTM.tabbedPane.getSelectedComponent()).getViewport().getComponents()[0]));
				int selRow = tbl.getSelectedRow();
				if(selRow != -1){			
					vTM.textField.setText(tbl.getValueAt(selRow, 0).toString());
					vTM.spinner.setValue(Integer.parseInt(tbl.getValueAt(selRow, 1).toString()));
					String time = tbl.getValueAt(selRow, 2).toString();
					vTM.spinner_1.setValue(VTasksManager.getHour(time,false));
					vTM.spinner_2.setValue(VTasksManager.getHour(time,true));
				}else{
					JOptionPane.showMessageDialog(new JFrame(), "You need to select a row.");
				}
			}
		});
	
		/**
		 * VTaskControlWindow event handlers
		 */
		tmrMin = new Timer(60000,new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae){
				for(int i = 0; i < 5; i++){
					if(state[i] == VTaskControlWindow.VTCState.vtcwStarted){
						vtcwTab[i].lblVTimeMinutes.setText(countDownTime(i,false));
						vtcwTab[i].lblVTimeHours.setText(countDownTime(i,true));
					}
				}
			}			
		});
		tmrMin.start();
		
		MouseAdapter mAdapterPlay = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				state[0] = VTCState.vtcwStarted;
				currTime[0] = System.currentTimeMillis();
				startTime[0] = currTime[0];
			}
		};
		MouseAdapter mAdapterPause = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				state[0] = VTCState.vtcwPaused;
				elapsedTime[0] = elapsedTime[0] - (currTime[0] - startTime[0]);
			}
		};
		vtcwTab[0].VTPlay.addMouseListener(mAdapterPlay);
		vtcwTab[0].VTPause.addMouseListener(mAdapterPause);
		
		mAdapterPlay = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				state[1] = VTCState.vtcwStarted;
				currTime[1] = System.currentTimeMillis();
				startTime[1] = currTime[1];
			}
		};
		mAdapterPause = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				state[1] = VTCState.vtcwPaused;
				elapsedTime[1] = elapsedTime[1] - (currTime[1] - startTime[1]);
			}
		};
		vtcwTab[1].VTPlay.addMouseListener(mAdapterPlay);
		vtcwTab[1].VTPause.addMouseListener(mAdapterPause);
		
		mAdapterPlay = new MouseAdapter() {
		@Override
			public void mouseClicked(MouseEvent e) {
				state[2] = VTCState.vtcwStarted;
				currTime[2] = System.currentTimeMillis();
				startTime[2] = currTime[2];
			}
		};
		mAdapterPause = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				state[2] = VTCState.vtcwPaused;
				elapsedTime[2] = elapsedTime[2] - (currTime[2] - startTime[2]);
			}
		};
		vtcwTab[2].VTPlay.addMouseListener(mAdapterPlay);
		vtcwTab[2].VTPause.addMouseListener(mAdapterPause);
		
		mAdapterPlay = new MouseAdapter() {
			@Override
				public void mouseClicked(MouseEvent e) {
					state[3] = VTCState.vtcwStarted;
					currTime[3] = System.currentTimeMillis();
					startTime[3] = currTime[3];
				}
			};
			mAdapterPause = new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					state[3] = VTCState.vtcwPaused;
					elapsedTime[3] = elapsedTime[3] - (currTime[3] - startTime[3]);
				}
			};
			vtcwTab[3].VTPlay.addMouseListener(mAdapterPlay);
			vtcwTab[3].VTPause.addMouseListener(mAdapterPause);
			
			mAdapterPlay = new MouseAdapter() {
				@Override
					public void mouseClicked(MouseEvent e) {
						state[4] = VTCState.vtcwStarted;
						currTime[4] = System.currentTimeMillis();
						startTime[4] = currTime[4];
					}
				};
				mAdapterPause = new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						state[4] = VTCState.vtcwPaused;
						elapsedTime[4] = elapsedTime[4] - (currTime[4] - startTime[4]);
					}
				};
				vtcwTab[4].VTPlay.addMouseListener(mAdapterPlay);
				vtcwTab[4].VTPause.addMouseListener(mAdapterPause);
		}
		
		
		
		/**
		 * VTaskmaster auxiliary functions
		 */
		private int findEmptyHandler(){
			for(int i = 0; i < 5; i++){
				if(!vtcwTab[i].active)
					return i;
			}
			return -1;
		}
		void handleVTCW(int h, int min, String task, int prior){
			int handler = findEmptyHandler();
			if(handler != -1){
				vtcwTab[handler].setTask(""+task+"("+prior+")",
                        VTMainWindow.timeFiller(h),
                        VTMainWindow.timeFiller(min));
				vtcwTab[handler].setAlwaysOnTop(true);
				vtcwTab[handler].setVisible(true);
				vtcwTab[handler].active = true;
				vTMW.vtcwLblTab[handler].setText(task);
				startTime[handler] = System.currentTimeMillis();
				elapsedTime[handler] = elapsedTimeCalc(vtcwTab[handler].lblVTimeHours.getText(),
						                               vtcwTab[handler].lblVTimeMinutes.getText());
			}else{
				JOptionPane.showMessageDialog(new JFrame(), "Probably, you need to rest...");
			}	
		}
		
		/**
		 * @param cWin - ControlWindow which time is going to be changed
		 * @param retHourTxt - determines with will be returned - hours or minutes
		 * @return properly formatted String
		 */
		String countDownTime(int winIndx, boolean retHourTxt) { 
			currTime[winIndx] = System.currentTimeMillis();
			long time = elapsedTime[winIndx] - (currTime[winIndx] - startTime[winIndx]);
			int timeH = (int) (time/3600000);
			
			if(retHourTxt){
				return VTMainWindow.timeFiller(timeH);
			}else{
				int timeM = (int) ((time - timeH*3600000)/60000);
				return VTMainWindow.timeFiller(timeM);
			}
			
		}
		/**
		 * 
		 * @param hours - hours that remains
		 * @param minutes - minutes that remains
		 * @return elapsed time
		 */
		int elapsedTimeCalc(String hours, String minutes){
			int hour = Integer.parseInt(hours);
			int min = Integer.parseInt(minutes);
			return hour*3600000+min*60000;
		}
}

