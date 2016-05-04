package pl.edu.agh.fis.vtaskmaster.core;

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
                "id             INTEGER         PRIMARY KEY AUTOINCREMENT, " +
                "name           varchar(45)     NOT NULL, " +
                "description    TEXT, " +
                "priority       SMALLINT        NOT NULL, " +
                "expectedTime   BIGINT         NOT NULL, " +
                "favourite      BOOLEAN         DEFAULT FALSE, " +
                "todo           BOOLEAN         DEFAULT TRUE, " +
                "UNIQUE(name) ON CONFLICT ABORT)";

        String createExecutedTasks = "CREATE TABLE IF NOT EXISTS executedTasks(" +
                "id             INTEGER         PRIMARY KEY AUTOINCREMENT, " +
                "task_id        INTEGER, " +
                "startTime      BIGINT          NOT NULL, " +
                "endTime        BIGINT          NOT NULL, " +
                "duration       BIGINT          NOT NULL, " +
                "done           BOOLEAN         NOT NULL, " +
                "FOREIGN KEY(task_id)           REFERENCES tasks(id))";

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
                    "INSERT INTO tasks VALUES (NULL, ?, ?, ?, ?, ?, ?)"
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

    public boolean addExecutedTask(int taskId, long startTime, long endTime,
                                   long totalDuration, boolean done) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO executedTasks VALUES (NULL, ?, ?, ?, ?, ?)"
            );
            preparedStatement.setInt(1, taskId);
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

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> allTasks = new ArrayList<>();
        try {
            ResultSet result = statement.executeQuery("SELECT * FROM tasks");
            int id, priority;
            boolean favourite, todo;
            String name, description;
            long expectedTime;
            while(result.next()) {
                id = result.getInt("id");
                name = result.getString("name");
                description = result.getString("description");
                priority = result.getInt("priority");
                expectedTime = result.getLong("expectedTime");
                favourite = result.getBoolean("favourite");
                todo = result.getBoolean("todo");

                allTasks.add(
                        new Task(id, name, description, priority, expectedTime, favourite, todo)
                );
            }
        }
        catch (SQLException e) {
            System.err.println("Błąd przy odczycie zadań");
            e.printStackTrace();
        }
        return allTasks;
    }

    Task getTaskByName(String name) {
        int id, priority;
        boolean favourite, todo;
        String description;
        long expectedTime;
        Task task = null;
        try {
            ResultSet result = statement.executeQuery("SELECT * FROM tasks WHERE name='"
                    + name + "'");

            if (result.next()) {
                id = result.getInt("id");
                priority = result.getInt("priority");
                description = result.getString("description");
                expectedTime = result.getLong("expectedTime");
                favourite = result.getBoolean("favourite");
                todo = result.getBoolean("todo");

                task = new Task(id, name, description, priority, expectedTime, favourite, todo);
            }
            else
            {
                // there's no such task
                return null;
            }
        }
        catch (SQLException e) {
            System.err.println("Błąd przy odczycie zadania");
            e.printStackTrace();
        }
        return task;
    }

    public ArrayList<ExecutedTask> getAllExecutedTasks() {
        ArrayList<ExecutedTask> allTasks = new ArrayList<>();
        try {
            ResultSet result = statement.executeQuery("SELECT * FROM executedTasks");
            int id, taskId;
            boolean done;
            long startTime, endTime, elapsedTime;
            while(result.next()) {
                id = result.getInt("id");
                taskId = result.getInt("task_id");
                done = result.getBoolean("done");
                startTime = result.getLong("startTime");
                endTime = result.getLong("endTime");
                elapsedTime = result.getLong("duration");

                allTasks.add(
                        new ExecutedTask(taskId, startTime, endTime, elapsedTime, done)
                );
            }
        }
        catch (SQLException e) {
            System.err.println("Błąd przy odczycie wykonanych zadań");
            e.printStackTrace();
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
        db.addTask("kupic chleb", "wazne zadanie", 1, 500, true, false);
        db.addTask("kupic maslo", "wazne niesamowicie zadanie", 1, 500, true, false);
        db.addTask("sprzedac konia", "wazne bardzo zadanie", 1, 500, true, false);

        db.addExecutedTask(db.getTaskByName("kupic chleb").getId(),
                500, 700, 200, true);
        db.addExecutedTask(db.getTaskByName("kupic maslo").getId(),
                500, 700, 200, false);
    }

}
