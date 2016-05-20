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

    public CoreManager(String dbName) {
        db = new CoreDB(dbName);
    }


    /**
     * Closes connection with database. Should be called when finishing work with Database.
     */
    public void finalize() {
        db.closeConnection();
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
     * @param taskName name of the task.
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

    /**
     * Gets list of tasks which were executed and were finished.
     *
     * @return ArrayList of {@link pl.edu.agh.fis.vtaskmaster.core.model.Task} which were executed and finished.
     */
    public ArrayList<Task> getHistory() {
        ArrayList<Task> tasks;
        try {
            tasks = db.getHistory();
        }
        catch (SQLException e) {
            tasks = null;
        }
        return tasks;
    }

    /**
     * Removes task with a given name from the DB.
     * @param taskName name of task which you want to remove
     * @return Boolean - true on success and false on failure (failure probably means that there was no such task)
     */
    public boolean removeTaskByName(String taskName) {
        try {
            db.removeTaskByName(taskName);
        }
        catch (SQLException e) {
            return false;
        }
        return true;
    }

    /**
     * This method helps to save tasks in DB - if there's already task with a given name, it updates it. Otherwise -
     * new task is added.
     *
     * It's important, that if you get task from DB and change its name, it still remembers its old name
     * in member variable oldName. It allows to update tasks even if their names were changed.
     * @see pl.edu.agh.fis.vtaskmaster.core.model.Task#oldName
     *
     *
     * @param task task which you want to save
     * @return Boolean - true on success and false on failure
     */
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

    /**
     * Method starts executing task, which means that it's added to table in DB called ExecutedTasks.
     *
     * You should pass current time (type: long) as the second parameter. It will be stored as a starting time.
     *
     * This method is connected with
     * {@link pl.edu.agh.fis.vtaskmaster.core.CoreManager#finishTask(ExecutedTask, long)}
     * {@link pl.edu.agh.fis.vtaskmaster.core.CoreManager#updateExecutedTask(ExecutedTask)}
     *
     * @param task task which you want to start executing
     * @param startTime here you should pass current time
     * @return Boolean - true on success and false on failure
     *
     * @see pl.edu.agh.fis.vtaskmaster.core.model.ExecutedTask
     */
    public boolean executeTask(Task task, long startTime) {
        try {
            db.addExecutedTask(task, startTime);
        }
        catch (SQLException e) {
            return false;
        }
        return true;
    }

    /**
     * It updates executed task.
     *
     * You should use it to update elapsedTime of the task.
     * Simple example:
     *  task.setElapsedTime(elapsedTime);
     *  manager.updateExecutedTask(task);}
     *
     * @param task ExecutedTask which you want to update
     * @return Boolean - true on success and false on failure
     */
    public boolean updateExecutedTask(ExecutedTask task) {
        try {
            db.updateExecutedTask(task);
        }
        catch (SQLException e) {
            return false;
        }
        return true;
    }

    /**
     * Finishes execution of the task (basically - just sets done flag and its endTime).
     *
     * @param task task which you want to finish
     * @param endTime you should pass current time
     * @return Boolean - true on success and false on failure
     */
    public boolean finishTask(ExecutedTask task, long endTime) {
        task.setDone(true);
        task.setEndTime(endTime);
        return updateExecutedTask(task);
    }


    public static void main(String[] args) {
        CoreManager manager = new CoreManager();
        manager.saveTask(new Task("hello", "hello", 1, 500, true, false));
        manager.getAllTasks().forEach(System.out::println);
        manager.getAllExecutedTasks().forEach(System.out::println);
    }



}
