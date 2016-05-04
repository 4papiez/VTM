package pl.edu.agh.fis.vtaskmaster.core;


import pl.edu.agh.fis.vtaskmaster.core.model.ExecutedTask;
import pl.edu.agh.fis.vtaskmaster.core.model.Task;

import java.sql.SQLException;
import java.util.ArrayList;

public class CoreManager {
    /** Reference to the DB
     * @see pl.edu.agh.fis.vtaskmaster.core.CoreDB
     */
    private CoreDB db;

    /**
     * Create CoreManager. The constructor initializes the reference to DB.
     */
    public CoreManager() {
        db = new CoreDB();
    }


    /**
     * @return ArrayList of all tasks from the DB.
     */
    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> tasks;
        try {
            tasks = db.getAllTasks();
        }
        catch (SQLException e) {
            tasks = null;
        }
        return tasks;
    }

    /**
     * @return ArrayList of all executed tasks from the DB.
     * @see pl.edu.agh.fis.vtaskmaster.core.model.ExecutedTask
     */
    public ArrayList<ExecutedTask> getAllExecutedTasks() {
        ArrayList<ExecutedTask> tasks;
        try {
            tasks = db.getAllExecutedTasks();
        }
        catch (SQLException e) {
            tasks = null;
        }
        return tasks;
    }

    /**
     * @param taskName - name of the task.
     * @return Task with a given name or null if there's no such task.
     */
    public Task getTaskByName(String taskName) {
        Task task;
        try {
            task = db.getTaskByName(taskName);
        }
        catch (SQLException e) {
            task = null;
        }
        return task;
    }

    /**
     * Gets all tasks which are marked as favourites from DB.
     * @return ArrayList of {@link pl.edu.agh.fis.vtaskmaster.core.model.Task} with a positive favourite flag.
     */
    public ArrayList<Task> getFavourites() {
        ArrayList<Task> tasks;
        try {
            tasks = db.getFavourites();
        }
        catch (SQLException e) {
            tasks = null;
        }
        return tasks;
    }

    /**
     * Gets all tasks which are marked as todo from DB.
     * @return ArrayList of {@link pl.edu.agh.fis.vtaskmaster.core.model.Task} with a positive todo flag.
     */
    public ArrayList<Task> getTodo() {
        ArrayList<Task> tasks;
        try {
            tasks = db.getTodo();
        }
        catch (SQLException e) {
            tasks = null;
        }
        return tasks;
    }

    public boolean removeTaskByName(String taskName) {
        try {
            db.removeTaskByName(taskName);
        }
        catch (SQLException e) {
            return false;
        }
        return true;
    }

    public boolean saveTask(Task task) {
        try {
            // there's already task with a given (old) name, so just update it
            if (db.isTaskWithName(task.getOldName())) {
                db.updateTask(task);
            }
            // there's no task with a given name, so add it
            else {
                db.addTask(task);
            }
        }
        catch (SQLException e) {
            return false;
        }
        return true;
    }

    public boolean executeTask(Task task, long startTime) {
        try {
            db.addExecutedTask(task, startTime);
        }
        catch (SQLException e) {
            return false;
        }
        return true;
    }



    public static void main(String[] args) {
        CoreManager manager = new CoreManager();
        manager.getAllTasks().forEach(System.out::println);
        manager.getAllExecutedTasks().forEach(System.out::println);
    }



}
