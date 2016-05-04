package pl.edu.agh.fis.vtaskmaster.core;

import com.sun.javafx.tk.Toolkit;
import org.sqlite.SQLiteConfig;
import pl.edu.agh.fis.vtaskmaster.core.model.ExecutedTask;
import pl.edu.agh.fis.vtaskmaster.core.model.Task;

import java.sql.*;
import java.util.ArrayList;


public class CoreDB {
    // sqlite driver for JDBC
    public static final String DRIVER = "org.sqlite.JDBC";
    // address of the database
    public static final String DB_URL = "jdbc:sqlite:vtaskmaster.db";

    private Connection connection;
    private Statement statement;


    public CoreDB() {
        try {
            Class.forName(CoreDB.DRIVER);
        }
        catch (ClassNotFoundException e) {
            System.err.println("Nie znaleziono sterownika JDBC");
            e.printStackTrace();
        }

        try {
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            connection = DriverManager.getConnection(CoreDB.DB_URL, config.toProperties());
            statement = connection.createStatement();
        }
        catch (SQLException e) {
            System.err.println("Błąd połączenia z bazą");
            e.printStackTrace();
        }

        initDB();
    }

    // creates tables in the database
    public boolean initDB() {
        String createTasks = "CREATE TABLE IF NOT EXISTS tasks(" +
                "name           varchar(45)     PRIMARY KEY NOT NULL, " +
                "description    TEXT, " +
                "priority       SMALLINT        NOT NULL, " +
                "expectedTime   BIGINT         NOT NULL, " +
                "favourite      BOOLEAN         DEFAULT FALSE, " +
                "todo           BOOLEAN         DEFAULT TRUE, " +
                "UNIQUE(name) ON CONFLICT ABORT)";

        String createExecutedTasks = "CREATE TABLE IF NOT EXISTS executedTasks(" +
                "id             INTEGER         PRIMARY KEY AUTOINCREMENT, " +
                "taskName       varchar(45), " +
                "startTime      BIGINT          NOT NULL, " +
                "endTime        BIGINT          NOT NULL, " +
                "duration       BIGINT          NOT NULL, " +
                "done           BOOLEAN         NOT NULL, " +
                "FOREIGN KEY(taskName)           REFERENCES tasks(name))";

        try {
            statement.execute(createTasks);
            statement.execute(createExecutedTasks);
        }
        catch (SQLException e) {
            System.err.println("Błąd w trakcie tworzenia tabel");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean addTask(String name, String description, int priority, long expectedTime,
                           boolean favourite, boolean todo) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO tasks VALUES (?, ?, ?, ?, ?, ?)"
            );
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, description);
            preparedStatement.setInt(3, priority);
            preparedStatement.setLong(4, expectedTime);
            preparedStatement.setBoolean(5, favourite);
            preparedStatement.setBoolean(6, todo);
            preparedStatement.execute();
        }
        catch (SQLException e) {
            System.err.println("Błąd przy dodawaniu zadania");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean addExecutedTask(String taskName, long startTime, long endTime,
                                   long totalDuration, boolean done) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO executedTasks VALUES (NULL, ?, ?, ?, ?, ?)"
            );
            preparedStatement.setString(1, taskName);
            preparedStatement.setLong(2, startTime);
            preparedStatement.setLong(3, endTime);
            preparedStatement.setLong(4, totalDuration);
            preparedStatement.setBoolean(5, done);
            preparedStatement.execute();
        }
        catch (SQLException e) {
            System.err.println("Błąd przy dodawaniu wykonanego zadania");
            e.printStackTrace();
            return false;
        }
        return true;
    }



    public ArrayList<Task> getTasksWithCondition(String condition) throws SQLException {
        ArrayList<Task> tasks = new ArrayList<>();
        ResultSet result = statement.executeQuery("SELECT * FROM tasks " + condition);
        int priority;
        boolean favourite, todo;
        String name, description;
        long expectedTime;
        while(result.next()) {
            name = result.getString("name");
            description = result.getString("description");
            priority = result.getInt("priority");
            expectedTime = result.getLong("expectedTime");
            favourite = result.getBoolean("favourite");
            todo = result.getBoolean("todo");

            tasks.add(
                    new Task(name, description, priority, expectedTime, favourite, todo)
            );
        }
        return tasks;
    }

    public ArrayList<Task> getTodo() throws SQLException {
        return getTasksWithCondition("WHERE todo");
    }

    public ArrayList<Task> getFavourites() throws SQLException {
        return getTasksWithCondition("WHERE favourite");
    }

    public ArrayList<Task> getAllTasks() throws SQLException {
        return getTasksWithCondition("");
    }

    Task getTaskByName(String name) throws SQLException {
        int id, priority;
        boolean favourite, todo;
        String description;
        long expectedTime;
        Task task = null;

        ResultSet result = statement.executeQuery("SELECT * FROM tasks WHERE name='"
                + name + "'");

        if (result.next()) {
            priority = result.getInt("priority");
            description = result.getString("description");
            expectedTime = result.getLong("expectedTime");
            favourite = result.getBoolean("favourite");
            todo = result.getBoolean("todo");

            task = new Task(name, description, priority, expectedTime, favourite, todo);
        }
        else
        {
            // there's no such task
            return null;
        }
        return task;
    }

    public ArrayList<ExecutedTask> getAllExecutedTasks() throws SQLException {
        ArrayList<ExecutedTask> allTasks = new ArrayList<>();

        ResultSet result = statement.executeQuery("SELECT * FROM executedTasks");
        String taskName;
        int id;
        boolean done;
        long startTime, endTime, elapsedTime;
        while(result.next()) {
            id = result.getInt("id");
            taskName = result.getString("taskName");
            done = result.getBoolean("done");
            startTime = result.getLong("startTime");
            endTime = result.getLong("endTime");
            elapsedTime = result.getLong("duration");

            allTasks.add(
                    new ExecutedTask(taskName, startTime, endTime, elapsedTime, done)
            );
        }

        return allTasks;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println("Problem z zamknieciem polaczenia");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CoreDB db = new CoreDB();
        //db.addTask("kupic chleb", "wazne zadanie", 1, 500, true, false);
        //db.addTask("kupic maslo", "wazne niesamowicie zadanie", 1, 500, true, false);
        //db.addTask("sprzedac koniaszka", "wazne bardzo zadanie", 1, 500, false, false);
        try {
            db.getAllTasks().forEach(System.out::println);
        }
        catch (SQLException e) {

        }

    }

}
