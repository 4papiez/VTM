package pl.edu.agh.fis.vtaskmaster.core;


import pl.edu.agh.fis.vtaskmaster.core.model.ExecutedTask;
import pl.edu.agh.fis.vtaskmaster.core.model.Task;

import java.util.ArrayList;

public class CoreManager {
    private CoreDB db;
    private ArrayList<Task> tasks = new ArrayList<>();
    private ArrayList<ExecutedTask> executedTasks = new ArrayList<>();

    public CoreManager() {
        db = new CoreDB();

        tasks.addAll(0, db.getAllTasks());
        executedTasks.addAll(0, db.getAllExecutedTasks());
    }

    public ArrayList<Task> getAllTasks() {
        return tasks;
    }
    public ArrayList<ExecutedTask> getAllExecutedTasks() {
        return executedTasks;
    }

    public static void main(String[] args) {
        CoreManager manager = new CoreManager();
        manager.getAllTasks().forEach(System.out::println);
        manager.getAllExecutedTasks().forEach(System.out::println);
    }



}
