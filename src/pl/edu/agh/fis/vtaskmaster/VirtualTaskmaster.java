package pl.edu.agh.fis.vtaskmaster;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import pl.edu.agh.fis.vtaskmaster.VTaskControlWindow.VTCState;
import pl.edu.agh.fis.vtaskmaster.VTasksManager.returnState;
import pl.edu.agh.fis.vtaskmaster.core.CoreManager;
import pl.edu.agh.fis.vtaskmaster.core.model.ExecutedTask;
import pl.edu.agh.fis.vtaskmaster.core.model.Task;

/**
 * @author Grzegorz Burzynski
 * @author Kamil Piastowicz
 * @author Wiktor Wudarczyk
 * @version 1.0
 */
public class VirtualTaskmaster {

    private VTMainWindow vTMW;
    private VTasksManager vTM;
    private VTStats vTS;
    private VTaskControlWindow[] vtcwTab;
    private VTaskControlWindow.VTCState[] state;
    private CoreManager database;
    private long[] currTime;
    private long[] startTime;
    private long[] elapsedTime;
    private Timer tmrMin;
    String sep;


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
        vTS = new VTStats();
        vtcwTab = new VTaskControlWindow[5];
        currTime = new long[5];
        elapsedTime = new long[5];
        startTime = new long[5];
        state = new VTaskControlWindow.VTCState[5];
        sep = System.getProperty("file.separator");
        for (int i = 0; i < 5; i++)
            vtcwTab[i] = new VTaskControlWindow("empty slot", "00", "00");


        vTM.setModal(true);
        vTM.setVisible(false);
        database = new CoreManager();
        fillTable(vTM.tblHistory, database.getHistory());
		fillTable(vTM.tblFavourites, database.getFavourites());

        /**
         * VirtualTaskmasterMainWindow event handlers
         */
        vTMW.btnDelete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selRow = vTMW.tblToDo.getSelectedRow();
                if (selRow != -1 && vTMW.tblToDo.getValueAt(selRow, 0) != null) {
                	database.getTaskByName((String) vTMW.tblToDo.getValueAt(selRow, 0)).setTodo(false);
                    ((DefaultTableModel) vTMW.tblToDo.getModel()).removeRow(selRow);
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "You need to select filled row.");
                }
            }
        });
        vTMW.btnRun.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selRow = vTMW.tblToDo.getSelectedRow();
                if (selRow != -1) {
                    int h = VTasksManager.getHour((String) vTMW.tblToDo.getValueAt(selRow, 2), false);
                    int min = VTasksManager.getHour((String) vTMW.tblToDo.getValueAt(selRow, 2), true);
                    handleVTCW(h, min, (String) vTMW.tblToDo.getValueAt(selRow, 0), (int) vTMW.tblToDo.getValueAt(selRow, 1));
                    System.out.println("name: " + (String) vTMW.tblToDo.getValueAt(selRow, 0));
                    database.executeTask(database.getTaskByName((String) vTMW.tblToDo.getValueAt(selRow, 0)), System.currentTimeMillis());
                    ((DefaultTableModel) vTMW.tblToDo.getModel()).removeRow(selRow);
                }
            }
        });
        vTMW.btnManageTasks.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                vTM.rS = returnState.VTM_REJECTED;
                vTM.setVisible(true);

                if(vTM.rS == returnState.VTM_RUN){
                    if(validateDataVTM((int)vTM.spnr_hour.getValue(),(int)vTM.spnr_mint.getValue(),vTM.textField.getText(), vTM.textPane.getText())){
                        handleVTCW((int)vTM.spnr_hour.getValue(),
                                (int)vTM.spnr_mint.getValue(),
                                vTM.textField.getText(),
                                (int)vTM.spnr_prior.getValue());
                        if(database.getTaskByName(vTM.textField.getText()) == null){
                        	database.saveTask(new Task(vTM.textField.getText(), vTM.textPane.getText(), (int)vTM.spnr_prior.getValue(), (long) (((Integer)vTM.spnr_hour.getValue() + (Integer)vTM.spnr_mint.getValue() * 60) * 6000), true, false));
                        }
                        database.executeTask(database.getTaskByName(vTM.textField.getText()), System.currentTimeMillis());		
                    }else{
                        JOptionPane.showMessageDialog(new JFrame(), "You have to provide full description of your task.");
                    }
                }else if(vTM.rS == returnState.VTM_TODO){
                    if(validateDataVTM((int)vTM.spnr_hour.getValue(),(int)vTM.spnr_mint.getValue(),vTM.textField.getText(), vTM.textPane.getText())){
                        int eRow = VTasksManager.tblFindEmptyRow(vTMW.tblToDo);
                        ((DefaultTableModel)vTMW.tblToDo.getModel()).addRow(new Object[]{null,null,null,null});
                        vTMW.tblToDo.setValueAt(vTM.textField.getText(), eRow, 0);
                        vTMW.tblToDo.setValueAt((int)vTM.spnr_prior.getValue(), eRow, 1);
                        vTMW.tblToDo.setValueAt(VTMainWindow.timeFiller((int)vTM.spnr_hour.getValue())+":"+ VTMainWindow.timeFiller((int)vTM.spnr_mint.getValue()), eRow, 2);
                        vTMW.tblToDo.setValueAt("00:00", eRow, 3);
                        if(database.getTaskByName(vTM.textField.getText()) == null){
                        	database.saveTask(new Task(vTM.textField.getText(), vTM.textPane.getText(), (int)vTM.spnr_prior.getValue(), (long) (((Integer)vTM.spnr_hour.getValue() + (Integer)vTM.spnr_mint.getValue() * 60) * 6000), true, false));
                        }
                        database.getTaskByName(vTM.textField.getText()).setTodo(true);
                    }else{
                        JOptionPane.showMessageDialog(new JFrame(), "You have to provide full description of your task.");
                    }
                }
                int row = VTasksManager.tblFindEmptyRow(vTM.tblHistory);
                if(vTM.tblHistory.getValueAt(row, 0) == null){
                    ((DefaultTableModel) vTM.tblHistory.getModel()).addRow(new Object[]{null,null,null,null});
                }
                vTM.tblHistory.setValueAt(vTM.textField.getText(),row,0);
                vTM.tblHistory.setValueAt(vTM.spnr_prior.getValue(),row,1);
                if((int)(vTM.spnr_mint.getValue()) > 9){
                    vTM.tblHistory.setValueAt(vTM.spnr_hour.getValue()+":"+vTM.spnr_mint.getValue(),row,2);
                }
                else{
                    vTM.tblHistory.setValueAt(vTM.spnr_hour.getValue()+":0"+vTM.spnr_mint.getValue(),row,2);
                }
                
            }
        });
        vTMW.vtcwBtnTab[0].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (vtcwTab[0].active) {
                    vtcwTab[0].setVisible(!vtcwTab[0].isVisible());
                }
            }
        });
        vTMW.vtcwBtnTab[1].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (vtcwTab[1].active) {
                    vtcwTab[1].setVisible(!vtcwTab[1].isVisible());
                }
            }
        });
        vTMW.vtcwBtnTab[2].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (vtcwTab[2].active) {
                    vtcwTab[2].setVisible(!vtcwTab[2].isVisible());
                }
            }
        });
        vTMW.vtcwBtnTab[3].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (vtcwTab[3].active) {
                    vtcwTab[3].setVisible(!vtcwTab[3].isVisible());
                }
            }
        });
        vTMW.vtcwBtnTab[4].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (vtcwTab[4].active) {
                    vtcwTab[4].setVisible(!vtcwTab[4].isVisible());
                }
            }
        });
        vTMW.btnStats.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		//TODO replace "0" with proper getter converted to String
        	    vTS.lblEffectivenessV = new JLabel("0");
        	    vTS.lblEffectivenessFavV = new JLabel("0");
     	        vTS.lblEffectivenessNFavV = new JLabel("0");
     	        vTS.lblOnTimeV = new JLabel("0");
     	        vTS.lblDifferenceV = new JLabel("0");
     	        vTS.lblDifferenceProcV = new JLabel("0");
     	        vTS.lblTaskCountV = new JLabel("0");
     	        vTS.lblTimeCountV = new JLabel("0");
     	        vTS.lblAvgPriorV = new JLabel("0");
        		vTS.setVisible(true);
        	}
        });
        /**
         * VirtualTasksManager event handlers
         */
        vTM.btnDelete.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JTable tbl = (JTable) ((((JScrollPane) vTM.tabbedPane.getSelectedComponent()).getViewport().getComponents()[0]));
                int selRow = tbl.getSelectedRow();
                if (selRow != -1 && tbl.getValueAt(selRow, 0) != null) {
                    ((DefaultTableModel) tbl.getModel()).removeRow(selRow);
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "You need to select filled row.");
                }
            }
        });
        vTM.btnFavourites.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if((JTable)((((JScrollPane)vTM.tabbedPane.getSelectedComponent()).getViewport().getComponents()[0])) == vTM.tblHistory){
                    ((DefaultTableModel) vTM.tblFavourites.getModel()).addRow(new Object[]{null,null,null,null});
                    int row = VTasksManager.tblFindEmptyRow(vTM.tblFavourites);
                    int selRow = vTM.tblHistory.getSelectedRow();
                    vTM.tabEdit = true;
                    if(selRow != -1 && vTM.tblHistory.getValueAt(selRow, 0)!=null)
                    {
                        vTM.tblFavourites.setValueAt(vTM.tblHistory.getValueAt(selRow,0), row, 0);
                        vTM.tblFavourites.setValueAt(vTM.tblHistory.getValueAt(selRow,1), row, 1);
                        vTM.tblFavourites.setValueAt(vTM.tblHistory.getValueAt(selRow,2), row, 2);
                        vTM.tblFavourites.setValueAt(vTM.tblHistory.getValueAt(selRow,3), row, 3);
                        database.getTaskByName((String) vTM.tblHistory.getValueAt(selRow, 0)).setFavourite(true);
                    }else{
                        JOptionPane.showMessageDialog(new JFrame(), "You need to select a filled row.");
                    }
                    vTM.tabEdit = true;
                }
            }
        });
        vTM.btnRun.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                vTM.rS = returnState.VTM_RUN;
                vTM.setVisible(false);
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
                if (tbl == vTM.tblHistory
                		|| vTM.textField.getText().equals("")
                		|| ((int)vTM.spnr_hour.getValue() == 0 && (int)vTM.spnr_mint.getValue() == 0)
                		|| vTM.textPane.getText().equals("")) return;
                int selRow = tbl.getSelectedRow();
                vTM.tabEdit = true;
                if(selRow == -1)
                    selRow = VTasksManager.tblFindEmptyRow(tbl);
                if(tbl.getValueAt(selRow, 0) == null){
                    ((DefaultTableModel) tbl.getModel()).addRow(new Object[]{null,null,null,null});
                }
                tbl.setValueAt(vTM.textField.getText(),selRow,0);
                tbl.setValueAt(vTM.spnr_prior.getValue(),selRow,1);
                if((int)(vTM.spnr_mint.getValue()) > 9){
                    tbl.setValueAt(vTM.spnr_hour.getValue()+":"+vTM.spnr_mint.getValue(),selRow,2);
                }
                else{
                    tbl.setValueAt(vTM.spnr_hour.getValue()+":0"+vTM.spnr_mint.getValue(),selRow,2);
                }
                vTM.tabEdit = false;
                System.out.println(vTM.textField.getText());
                if (tbl == vTM.tblFavourites) {
                	if(database.getTaskByName((String) vTM.textField.getText()) == null){
                		database.saveTask(new Task(vTM.textField.getText(), vTM.textPane.getText(), (int)vTM.spnr_prior.getValue(), (long) (((Integer)vTM.spnr_hour.getValue() + (Integer)vTM.spnr_mint.getValue() * 60) * 6000), true, false));
                	}              
                } else {
                	if(database.getTaskByName((String) vTM.textField.getText()) == null){
                		database.saveTask(new Task(vTM.textField.getText(), vTM.textPane.getText(), (int)vTM.spnr_prior.getValue(), (long) (((Integer)vTM.spnr_hour.getValue() + (Integer)vTM.spnr_mint.getValue() * 60) * 6000), false, true));
                	}
                }
                
            }
        });
        vTM.btnFromList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTable tbl = (JTable)((((JScrollPane)vTM.tabbedPane.getSelectedComponent()).getViewport().getComponents()[0]));
                int selRow = tbl.getSelectedRow();
                vTM.tabEdit = true;
                if(selRow != -1){
                    vTM.textField.setText(tbl.getValueAt(selRow, 0).toString());
                    vTM.spnr_prior.setValue(Integer.parseInt(tbl.getValueAt(selRow, 1).toString()));
                    String time = tbl.getValueAt(selRow, 2).toString();
                    vTM.spnr_hour.setValue(VTasksManager.getHour(time,false));
                    vTM.spnr_mint.setValue(VTasksManager.getHour(time,true));
                }else{
                    JOptionPane.showMessageDialog(new JFrame(), "You need to select a row.");
                }
                vTM.tabEdit = false;
            }
        });

        /**
         * VTaskControlWindow event handlers
         */
        tmrMin = new Timer(60000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                for (int i = 0; i < 5; i++) {
                    if (state[i] == VTaskControlWindow.VTCState.vtcwStarted) {
                        vtcwTab[i].lblVTimeMinutes.setText(countDownTime(i, false));
                        vtcwTab[i].lblVTimeHours.setText(countDownTime(i, true));
                        
                        ArrayList<ExecutedTask> tasks = database.getAllExecutedTasks();
                        for(int j = 0; j < tasks.size(); j++){
                        	if(tasks.get(j).getTaskName() == vtcwTab[i].lblVTaskName.getText() && !(tasks.get(j).isDone())){
                        		database.updateExecutedTask(tasks.get(j));
                        	}
                        }
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
                vtcwTab[0].lblInProgress.setIcon(new ImageIcon(System.getProperty("user.dir")+sep+"src"+sep+"pl"+sep+"edu"+sep+"agh"+sep+"fis"+sep+"vtaskmaster"+sep+"lighton.png"));
            }
        };
        MouseAdapter mAdapterPause = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                state[0] = VTCState.vtcwPaused;
                elapsedTime[0] = elapsedTime[0] - (currTime[0] - startTime[0]);
                vtcwTab[0].lblInProgress.setIcon(new ImageIcon(System.getProperty("user.dir")+sep+"src"+sep+"pl"+sep+"edu"+sep+"agh"+sep+"fis"+sep+"vtaskmaster"+sep+"lightoff.png"));
            }
        };
        MouseAdapter mAdapterStop = new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                state[0] = VTCState.vtcwFinished; vtcwTab[0].active = false; vtcwTab[0].setVisible(false);
                elapsedTime[0] = elapsedTime[0] - (currTime[0] - startTime[0]);
                vtcwTab[0] = new VTaskControlWindow("empty slot", "00", "00");
                vTMW.vtcwLblTab[0].setText("Empty slot");
                ArrayList<ExecutedTask> tasks = database.getAllExecutedTasks();
                for(int j = 0; j < tasks.size(); j++){
                	if(tasks.get(j).getTaskName() == vtcwTab[0].lblVTaskName.getText() && !(tasks.get(j).isDone())){
                		database.finishTask(tasks.get(j), System.currentTimeMillis());
                		break;
                	}
                }
            }
        };
        vtcwTab[0].VTPlay.addMouseListener(mAdapterPlay);
        vtcwTab[0].VTPause.addMouseListener(mAdapterPause);
        vtcwTab[0].VTStop.addMouseListener(mAdapterStop);

        mAdapterPlay = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                state[1] = VTCState.vtcwStarted;
                currTime[1] = System.currentTimeMillis();
                startTime[1] = currTime[1];
                vtcwTab[1].lblInProgress.setIcon(new ImageIcon(System.getProperty("user.dir")+sep+"src"+sep+"pl"+sep+"edu"+sep+"agh"+sep+"fis"+sep+"vtaskmaster"+sep+"lighton.png"));
            }
        };
        mAdapterPause = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                state[1] = VTCState.vtcwPaused;
                elapsedTime[1] = elapsedTime[1] - (currTime[1] - startTime[1]);
                vtcwTab[1].lblInProgress.setIcon(new ImageIcon(System.getProperty("user.dir")+sep+"src"+sep+"pl"+sep+"edu"+sep+"agh"+sep+"fis"+sep+"vtaskmaster"+sep+"lightoff.png"));
            }
        };
        mAdapterStop = new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                state[1] = VTCState.vtcwFinished; vtcwTab[1].active = false; vtcwTab[1].setVisible(false);
                elapsedTime[1] = elapsedTime[1] - (currTime[1] - startTime[1]);
                vtcwTab[1] = new VTaskControlWindow("empty slot", "00", "00");
                vTMW.vtcwLblTab[1].setText("Empty slot");
                ArrayList<ExecutedTask> tasks = database.getAllExecutedTasks();
                for(int j = 0; j < tasks.size(); j++){
                	if(tasks.get(j).getTaskName() == vtcwTab[1].lblVTaskName.getText() && !(tasks.get(j).isDone())){
                		database.finishTask(tasks.get(j), System.currentTimeMillis());
                		break;
                	}
                }
                
            }
        };
        vtcwTab[1].VTPlay.addMouseListener(mAdapterPlay);
        vtcwTab[1].VTPause.addMouseListener(mAdapterPause);
        vtcwTab[1].VTStop.addMouseListener(mAdapterStop);

        mAdapterPlay = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                state[2] = VTCState.vtcwStarted;
                currTime[2] = System.currentTimeMillis();
                startTime[2] = currTime[2];
                vtcwTab[2].lblInProgress.setIcon(new ImageIcon(System.getProperty("user.dir")+sep+"src"+sep+"pl"+sep+"edu"+sep+"agh"+sep+"fis"+sep+"vtaskmaster"+sep+"lighton.png"));
            }
        };
        mAdapterPause = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                state[2] = VTCState.vtcwPaused;
                elapsedTime[2] = elapsedTime[2] - (currTime[2] - startTime[2]);
                vtcwTab[2].lblInProgress.setIcon(new ImageIcon(System.getProperty("user.dir")+sep+"src"+sep+"pl"+sep+"edu"+sep+"agh"+sep+"fis"+sep+"vtaskmaster"+sep+"lightoff.png"));
            }
        };
        mAdapterStop = new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                state[2] = VTCState.vtcwFinished; vtcwTab[2].active = false; vtcwTab[2].setVisible(false);
                elapsedTime[2] = elapsedTime[2] - (currTime[2] - startTime[2]);
                vtcwTab[2] = new VTaskControlWindow("empty slot", "00", "00");
                vTMW.vtcwLblTab[2].setText("Empty slot");
                ArrayList<ExecutedTask> tasks = database.getAllExecutedTasks();
                for(int j = 0; j < tasks.size(); j++){
                	if(tasks.get(j).getTaskName() == vtcwTab[2].lblVTaskName.getText() && !(tasks.get(j).isDone())){
                		database.finishTask(tasks.get(j), System.currentTimeMillis());
                		break;
                	}
                }
            }
        };
        vtcwTab[2].VTPlay.addMouseListener(mAdapterPlay);
        vtcwTab[2].VTPause.addMouseListener(mAdapterPause);
        vtcwTab[2].VTStop.addMouseListener(mAdapterStop);

        mAdapterPlay = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                state[3] = VTCState.vtcwStarted;
                currTime[3] = System.currentTimeMillis();
                startTime[3] = currTime[3];
                vtcwTab[3].lblInProgress.setIcon(new ImageIcon(System.getProperty("user.dir")+sep+"src"+sep+"pl"+sep+"edu"+sep+"agh"+sep+"fis"+sep+"vtaskmaster"+sep+"lighton.png"));
            }
        };
        mAdapterPause = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                state[3] = VTCState.vtcwPaused;
                elapsedTime[3] = elapsedTime[3] - (currTime[3] - startTime[3]);
                vtcwTab[3].lblInProgress.setIcon(new ImageIcon(System.getProperty("user.dir")+sep+"src"+sep+"pl"+sep+"edu"+sep+"agh"+sep+"fis"+sep+"vtaskmaster"+sep+"lightoff.png"));
            }
        };
        mAdapterStop = new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                state[3] = VTCState.vtcwFinished; vtcwTab[3].active = false; vtcwTab[3].setVisible(false);
                elapsedTime[3] = elapsedTime[3] - (currTime[3] - startTime[3]);
                vtcwTab[3] = new VTaskControlWindow("empty slot", "00", "00");
                vTMW.vtcwLblTab[3].setText("Empty slot");
                ArrayList<ExecutedTask> tasks = database.getAllExecutedTasks();
                for(int j = 0; j < tasks.size(); j++){
                	if(tasks.get(j).getTaskName() == vtcwTab[3].lblVTaskName.getText() && !(tasks.get(j).isDone())){
                		database.finishTask(tasks.get(j), System.currentTimeMillis());
                		break;
                	}
                }
            }
        };
        vtcwTab[3].VTPlay.addMouseListener(mAdapterPlay);
        vtcwTab[3].VTPause.addMouseListener(mAdapterPause);
        vtcwTab[3].VTStop.addMouseListener(mAdapterStop);

        mAdapterPlay = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                state[4] = VTCState.vtcwStarted;
                currTime[4] = System.currentTimeMillis();
                startTime[4] = currTime[4];
                vtcwTab[4].lblInProgress.setIcon(new ImageIcon(System.getProperty("user.dir")+sep+"src"+sep+"pl"+sep+"edu"+sep+"agh"+sep+"fis"+sep+"vtaskmaster"+sep+"lighton.png"));
            }
        };
        mAdapterPause = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                state[4] = VTCState.vtcwPaused;
                elapsedTime[4] = elapsedTime[4] - (currTime[4] - startTime[4]);
                vtcwTab[4].lblInProgress.setIcon(new ImageIcon(System.getProperty("user.dir")+sep+"src"+sep+"pl"+sep+"edu"+sep+"agh"+sep+"fis"+sep+"vtaskmaster"+sep+"lightoff.png"));
            }
        };
        mAdapterStop = new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                state[4] = VTCState.vtcwFinished; vtcwTab[4].active = false; vtcwTab[4].setVisible(false);
                elapsedTime[4] = elapsedTime[4] - (currTime[4] - startTime[4]);
                vtcwTab[4] = new VTaskControlWindow("empty slot", "00", "00");
                vTMW.vtcwLblTab[4].setText("Empty slot");
                ArrayList<ExecutedTask> tasks = database.getAllExecutedTasks();
                for(int j = 0; j < tasks.size(); j++){
                	if(tasks.get(j).getTaskName() == vtcwTab[4].lblVTaskName.getText() && !(tasks.get(j).isDone())){
                		database.finishTask(tasks.get(j), System.currentTimeMillis());
                		break;
                	}
                }
            }
        };
        vtcwTab[4].VTPlay.addMouseListener(mAdapterPlay);
        vtcwTab[4].VTPause.addMouseListener(mAdapterPause);
        vtcwTab[4].VTStop.addMouseListener(mAdapterStop);
    }


    /**
     * VTaskmaster auxiliary functions
     */

    /**
     *
     * @return index of empty "handler" [button and label in main window]
     */
    private int findEmptyHandler() {
        for (int i = 0; i < 5; i++) {
            if (!vtcwTab[i].active)
                return i;
        }
        return -1;
    }

    /**
     *
     * @brief handles created tasks
     * @param h - user-chosen estimated time (hours)
     * @param min - user-chosen estimated time (minutes)
     * @param task - user-typed task description
     * @param prior - user-chosen priority of the task
     */
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
            currTime[handler] = System.currentTimeMillis();
            startTime[handler] = currTime[handler];
            state[handler] = VTCState.vtcwStarted;
            elapsedTime[handler] = elapsedTimeCalc(vtcwTab[handler].lblVTimeHours.getText(),
                    vtcwTab[handler].lblVTimeMinutes.getText());
        }else{
            JOptionPane.showMessageDialog(new JFrame(), "Probably, you need to rest...");
        }
    }

    /**
     * @param winIndx    - ControlWindow which time is going to be changed
     * @param retHourTxt - determines with will be returned - hours or minutes
     * @return properly formatted String
     */
    String countDownTime(int winIndx, boolean retHourTxt) { //FIXME (time) sometimes shit happens
        currTime[winIndx] = System.currentTimeMillis();
        long time = elapsedTime[winIndx] - (currTime[winIndx] - startTime[winIndx]);
        int timeH = (int) (time/3600000);
        int timeM = (int) ((time - timeH*3600000)/60000);

        if(timeM == 0 && timeH == 0){
            vtcwTab[winIndx].lblVTimeHours.setForeground(Color.RED);
            vtcwTab[winIndx].lblVTimeMinutes.setForeground(Color.RED);
        }
        
        if (timeM == 15 && timeH == 0 && retHourTxt) {
        	vtcwTab[winIndx].setVisible(true);
        	JOptionPane.showMessageDialog(new JFrame(), "15 minutes time to the end of the task:" + vtcwTab[winIndx].lblVTaskName.getText());
        }

        if(retHourTxt){
            return VTMainWindow.timeFiller(Math.abs(timeH));
        }else{
            return VTMainWindow.timeFiller(Math.abs(timeM));
        }
    }

    /**
     * @param hours   - hours that remains
     * @param minutes - minutes that remains
     * @return elapsed time
     */
    int elapsedTimeCalc(String hours, String minutes) {
        int hour = Integer.parseInt(hours);
        int min = Integer.parseInt(minutes);
        return hour * 3600000 + min * 60000;
    }
    boolean validateDataVTM(int h, int min, String name, String desc){
        return ((h != 0 || min != 0) && !name.equals("") && !desc.equals(""));
    }
	void fillTable(JTable tbl, ArrayList<Task> tasks){
		for(int i=0; i<tasks.size(); i++){
			Task task = tasks.get(i);
			    long time = task.getExpectedTime();
			    int timeH = (int)time/3600000; String th;
			    int timeM = (int)((time-timeH*3600000)/60000); String tm;
			    if(timeH < 10) th = 0+""+timeH;
			    else th = ""+timeH;
			    if(timeM < 10) tm = 0+""+timeM;
			    else tm = ""+timeM;
			    
				((DefaultTableModel) tbl.getModel()).addRow(new Object[]{null,null,null,null});
				tbl.setValueAt(task.getName(), i, 0);
				tbl.setValueAt(task.getPriority(), i, 1);
				tbl.setValueAt(th+":"+tm, i, 2); // no idea how to do this properly~
				tbl.setValueAt(th+":"+tm, i, 3); // no idea how to do this properly~
		}
	}
}

