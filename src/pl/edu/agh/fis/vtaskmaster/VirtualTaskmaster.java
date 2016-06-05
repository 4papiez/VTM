package pl.edu.agh.fis.vtaskmaster;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import pl.edu.agh.fis.vtaskmaster.VTaskControlWindow.VTCState;
import pl.edu.agh.fis.vtaskmaster.VTasksManager.returnState;
import pl.edu.agh.fis.vtaskmaster.core.CoreManager;
import pl.edu.agh.fis.vtaskmaster.core.CoreStats;
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
    private int[] id;
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
        id = new int[5];
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
                VTMainWindowDeleteButton();
            }
        });
        vTMW.btnRun.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                VTMainWindowRunButton();
            }
        });
        vTMW.btnManageTasks.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                VTMainWindowManageTasksButton();    
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
                try {
                    vTS.lblEffectivenessV.setText(
                            String.valueOf(database.stats.efficiency(CoreStats.TasksFilter.ALL))
                    );
                    vTS.lblEffectivenessFavV.setText(
                            String.valueOf(database.stats.efficiency(CoreStats.TasksFilter.FAVOURITES))
                    );
                    vTS.lblEffectivenessNFavV.setText(
                            String.valueOf(database.stats.efficiency(CoreStats.TasksFilter.NON_FAVOURITES))
                    );
                    vTS.lblOnTimeV.setText(
                            String.valueOf(database.stats.onTime())
                    );
                    vTS.lblDifferenceV.setText(
                            String.valueOf(database.stats.averageDifference(true))
                    );
                    vTS.lblDifferenceProcV.setText(
                            String.valueOf(database.stats.averageDifference(false)) + "%"
                    );
                    vTS.lblTaskCountV.setText(
                            String.valueOf(database.stats.numberOfDoneTasks())
                    );
                    vTS.lblTimeCountV.setText(
                            String.valueOf(database.stats.timeSpentWorking())
                    );
                    vTS.lblAvgPriorV.setText(
                            String.valueOf(database.stats.averagePriority())
                    );
                    vTS.setVisible(true);
                } catch(SQLException exception) {
                    System.err.println("Blad odczytu statystyk z bazy danych.");
                }
        	}
        });
        /**
         * VirtualTasksManager event handlers
         */
        vTM.btnDelete.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
                VTasksManagerDeleteButton();
            }
        });
        vTM.btnFavourites.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                VTasksManagerFavouritesButton();
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
                VTasksManagerToListButton();
                
            }
        });
        vTM.btnFromList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                VTasksManagerFromListButton();
            }
        });
        
        /**
         * VTaskControlWindow event handlers
         */
        tmrMin = new Timer(60000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                timerAction();
            }
        });
        tmrMin.start();

        MouseAdapter mAdapterPlay = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	VTaskControlWindowPlayButton(0);
            }
        };
        MouseAdapter mAdapterPause = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                VTaskControlWindowPauseButton(0);
            }
        };
        MouseAdapter mAdapterStop = new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                VTaskControlWindowStopButton(0);
            }
        };
        vtcwTab[0].VTPlay.addMouseListener(mAdapterPlay);
        vtcwTab[0].VTPause.addMouseListener(mAdapterPause);
        vtcwTab[0].VTStop.addMouseListener(mAdapterStop);

        mAdapterPlay = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                VTaskControlWindowPlayButton(1);
            }
        };
        mAdapterPause = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	VTaskControlWindowPauseButton(1);
            }
        };
        mAdapterStop = new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                VTaskControlWindowStopButton(1);
            }
        };
        vtcwTab[1].VTPlay.addMouseListener(mAdapterPlay);
        vtcwTab[1].VTPause.addMouseListener(mAdapterPause);
        vtcwTab[1].VTStop.addMouseListener(mAdapterStop);

        mAdapterPlay = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	VTaskControlWindowPlayButton(2);
            }
        };
        mAdapterPause = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	VTaskControlWindowPauseButton(2);
            }
        };
        mAdapterStop = new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
            	 VTaskControlWindowStopButton(2);
            }
        };
        vtcwTab[2].VTPlay.addMouseListener(mAdapterPlay);
        vtcwTab[2].VTPause.addMouseListener(mAdapterPause);
        vtcwTab[2].VTStop.addMouseListener(mAdapterStop);

        mAdapterPlay = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	VTaskControlWindowPlayButton(3);
            }
        };
        mAdapterPause = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	VTaskControlWindowPauseButton(3);
            }
        };
        mAdapterStop = new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
            	 VTaskControlWindowStopButton(3);
            }
        };
        vtcwTab[3].VTPlay.addMouseListener(mAdapterPlay);
        vtcwTab[3].VTPause.addMouseListener(mAdapterPause);
        vtcwTab[3].VTStop.addMouseListener(mAdapterStop);

        mAdapterPlay = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	VTaskControlWindowPlayButton(4);
            }
        };
        mAdapterPause = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	VTaskControlWindowPauseButton(4);
            }
        };
        mAdapterStop = new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
            	 VTaskControlWindowStopButton(4);
            }
        };
        vtcwTab[4].VTPlay.addMouseListener(mAdapterPlay);
        vtcwTab[4].VTPause.addMouseListener(mAdapterPause);
        vtcwTab[4].VTStop.addMouseListener(mAdapterStop);
    }
    /**
     * VTMainWindow functions
     */
    /**
     *  Behavior of delete button in the main window
     *  checks which row is selected - then removes that row
     *  TODO flag todo should be unset in database
     */
    void VTMainWindowDeleteButton(){
		int selRow = vTMW.tblToDo.getSelectedRow();
        if (selRow != -1 && vTMW.tblToDo.getValueAt(selRow, 0) != null) {
        	Task task = database.getTaskByName((String) vTMW.tblToDo.getValueAt(selRow, 0));
            task.setTodo(false);
            database.saveTask(task);
            ((DefaultTableModel) vTMW.tblToDo.getModel()).removeRow(selRow);
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "You need to select filled row.");
        }
	}
    /**
     *  Behavior of run button in main window
     *  checks which row is selected, than sends data to
     *  Control Window and adds executed task to database
     */
    void VTMainWindowRunButton(){
    	int selRow = vTMW.tblToDo.getSelectedRow();
        if (selRow != -1) {
            int h = getHour((String) vTMW.tblToDo.getValueAt(selRow, 2), false);
            int min = getHour((String) vTMW.tblToDo.getValueAt(selRow, 2), true);
            handleVTCW(h, min, (String) vTMW.tblToDo.getValueAt(selRow, 0), (int) vTMW.tblToDo.getValueAt(selRow, 1));
            System.out.println("name: " + (String) vTMW.tblToDo.getValueAt(selRow, 0));
            database.executeTask(database.getTaskByName((String) vTMW.tblToDo.getValueAt(selRow, 0)), System.currentTimeMillis());
            ((DefaultTableModel) vTMW.tblToDo.getModel()).removeRow(selRow);
        }
    }
    /**
     *  Behavior of ManageTasks button
     *  Assumes that window will be rejected, then - if run or todo was clicked
     *  Changes its state:
     *  RUN:
     *  gets user input and handles, enabling ControlWindow
     *  toDo
     *  Copying user input into todo table 
     */
    void VTMainWindowManageTasksButton(){
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
                int eRow = tblFindEmptyRow(vTMW.tblToDo);
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
        int row = tblFindEmptyRow(vTM.tblHistory);
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
    /**
     *  VTasksManager functions
     */
    
    /**
     * Behavior of Delete button in TasksManager
     * finds selected row, and removes it
     */
    void VTasksManagerDeleteButton(){
    	JTable tbl = (JTable) ((((JScrollPane) vTM.tabbedPane.getSelectedComponent()).getViewport().getComponents()[0]));
        int selRow = tbl.getSelectedRow();
        if (selRow != -1 && tbl.getValueAt(selRow, 0) != null) {
            ((DefaultTableModel) tbl.getModel()).removeRow(selRow);
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "You need to select filled row.");
        }
    }
    /*
     * Behavior of FavouritesButton in TasksManager
     * When user selected tblHistory and one of its rows
     * Sends data from this row to tblFavourites, sets flag favorites in database
     */
    void VTasksManagerFavouritesButton(){
    	if((JTable)((((JScrollPane)vTM.tabbedPane.getSelectedComponent()).getViewport().getComponents()[0])) == vTM.tblHistory){
            ((DefaultTableModel) vTM.tblFavourites.getModel()).addRow(new Object[]{null,null,null,null});
            int row = tblFindEmptyRow(vTM.tblFavourites);
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
    /**
     * Behavior of ToList button in VTasksManager
     * Collects user input, inserting it into table and database
     * Enables editing tasks 
     */
    void VTasksManagerToListButton(){
    	JTable tbl = (JTable)((((JScrollPane)vTM.tabbedPane.getSelectedComponent()).getViewport().getComponents()[0]));
        if (tbl == vTM.tblHistory
        		|| vTM.textField.getText().equals("")
        		|| ((int)vTM.spnr_hour.getValue() == 0 && (int)vTM.spnr_mint.getValue() == 0)
        		|| vTM.textPane.getText().equals("")) return;
        int selRow = tbl.getSelectedRow();
        vTM.tabEdit = true;
        if(selRow == -1)
            selRow = tblFindEmptyRow(tbl);
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
    
    /**
     * Behavior of FromList button in VTasksManager
     * Collects data, inserting it into user input fields, from where task can be run
     * Enables editing tasks 
     */
    void VTasksManagerFromListButton(){
    	JTable tbl = (JTable)((((JScrollPane)vTM.tabbedPane.getSelectedComponent()).getViewport().getComponents()[0]));
        int selRow = tbl.getSelectedRow();
        vTM.tabEdit = true;
        if(selRow != -1){
            vTM.textField.setText(tbl.getValueAt(selRow, 0).toString());
            vTM.spnr_prior.setValue(Integer.parseInt(tbl.getValueAt(selRow, 1).toString()));
            String time = tbl.getValueAt(selRow, 2).toString();
            vTM.spnr_hour.setValue(getHour(time,false));
            vTM.spnr_mint.setValue(getHour(time,true));
        }else{
            JOptionPane.showMessageDialog(new JFrame(), "You need to select a row.");
        }
        vTM.tabEdit = false;
    }
    /**
     * VTaskControlWindow functions
     */
    /**
     * Behavior of Stop Button in TaskControlWindow
     * Ends execution of task, erases data, finishes task in database
     * @param winIndx - number of handler in table
     */
    void VTaskControlWindowStopButton(int winIndx){
    	state[winIndx] = VTCState.vtcwFinished; vtcwTab[winIndx].active = false; vtcwTab[winIndx].setVisible(false);
        elapsedTime[winIndx] = elapsedTime[winIndx] - (currTime[winIndx] - startTime[winIndx]);
        ArrayList<ExecutedTask> tasks = database.getAllExecutedTasks();
        for(int j = 0; j < tasks.size(); j++){
            System.out.println(tasks.get(j).getTaskName() + vtcwTab[winIndx].lblVTaskName.getText());
        	if(tasks.get(j).getTaskName().equals(vtcwTab[winIndx].lblVTaskName.getText()) && !(tasks.get(j).isDone())){
        		database.finishTask(tasks.get(j), System.currentTimeMillis());
        		break;
        	}
        }
        vtcwTab[winIndx] = new VTaskControlWindow("empty slot", "00", "00");
        vTMW.vtcwLblTab[winIndx].setText("Empty slot");
    }
    /**
     * Behavior of PlayButton in TaskControlWindow
     * If task was paused - restarts
     * @param winIndx - index of window in table
     */
    void VTaskControlWindowPlayButton(int winIndx){
    	state[winIndx] = VTCState.vtcwStarted;
        currTime[winIndx] = System.currentTimeMillis();
        startTime[winIndx] = currTime[winIndx];
        vtcwTab[winIndx].lblInProgress.setIcon(new ImageIcon(System.getProperty("user.dir")+sep+"src"+sep+"pl"+sep+"edu"+sep+"agh"+sep+"fis"+sep+"vtaskmaster"+sep+"lighton.png"));
    }
    /**
     * Behavior of PauseButton in TaskControlWindow
     * If task was run - pauses
     * @param winIndx - index of window in table
     */
    void VTaskControlWindowPauseButton(int winIndx){
    	state[winIndx] = VTCState.vtcwPaused;
        elapsedTime[winIndx] = elapsedTime[winIndx] - (currTime[winIndx] - startTime[winIndx]);
        vtcwTab[winIndx].lblInProgress.setIcon(new ImageIcon(System.getProperty("user.dir")+sep+"src"+sep+"pl"+sep+"edu"+sep+"agh"+sep+"fis"+sep+"vtaskmaster"+sep+"lightoff.png"));
    }
    /**
     * Each minute updates every TaskControlWindow view and its representation in database
     */
    void timerAction(){
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
    

    /**
     * VTaskmaster auxiliary functions
     */

    /**
     * Searches for empty handler in main window
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
     * Applies data from manager to found empty handler,
     * and displays user progress on the widget.
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
            vtcwTab[handler].setTask(task,
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
     * Calculates the time that going to be shown on the TaskControl widget
     * 
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
     * Counts the time in milliseconds from the human-friendly representation
     * 
     * @param hours   - hours that remains
     * @param minutes - minutes that remains
     * @return elapsed time
     */
    int elapsedTimeCalc(String hours, String minutes) {
        int hour = Integer.parseInt(hours);
        int min = Integer.parseInt(minutes);
        return hour * 3600000 + min * 60000;
    }
    /**
     * Short function that checks whether data is valid
     * 
     * @param h - time in hours to the end of the task (+min - nonzero)
     * @param min - time in minutes to the end of the task (+ h nonzero)
     * @param name - can't be equal to the empty string
     * @param desc - can't be equal to the empty string
     * @return
     */
    boolean validateDataVTM(int h, int min, String name, String desc){
        return ((h != 0 || min != 0) && !name.equals("") && !desc.equals(""));
    }
    /**
     * For every record in task ArrayList calculates proper time representation
     * (from long representing milliseconds of time to the end of the task
     * to the proper, user friendly format.
     * Then it fills every column with proper data - name, prior, and time
     * 
     * @param tbl - JTable that needs to be filled with data
     * @param tasks - ArrayList of data witch which table is needed to be filled
     */
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
	/**
	 * Finds empty row in the given table
	 * 
	 * @param tbl - table to find empty row in
	 * @return i - index of empty row in the given table
	 */
	static int tblFindEmptyRow(JTable tbl) {
		int i = 0;
		while(tbl.getValueAt(i,0) != null) i++;
		return i;
	}
	/**
	 * Parsing time in milliseconds from VTCW displayed timeString 
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

