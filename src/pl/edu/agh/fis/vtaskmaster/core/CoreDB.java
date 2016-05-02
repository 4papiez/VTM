package pl.edu.agh.fis.vtaskmaster.core;

import org.sqlite.SQLiteConfig;
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
                "favourite      BOOLEAN         DEFAULT FALSE, " +
                "todo           BOOLEAN         DEFAULT TRUE, " +
                "UNIQUE(name) ON CONFLICT ABORT)";

        String createExecutedTasks = "CREATE TABLE IF NOT EXISTS executedTasks(" +
                "id             INTEGER         PRIMARY KEY AUTOINCREMENT, " +
                "task_id        INTEGER, " +
                "startTime      BIGINT          NOT NULL, " +
                "endTime        BIGINT          NOT NULL, " +
                "duration       BIGINT          NOT NULL, " +
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

    public boolean addTask(String name, String description, int priority,
                           boolean favourite, boolean todo) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO tasks VALUES (NULL, ?, ?, ?, ?, ?)"
            );
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, description);
            preparedStatement.setInt(3, priority);
            preparedStatement.setBoolean(4, favourite);
            preparedStatement.setBoolean(5, todo);
            preparedStatement.execute();
        }
        catch (SQLException e) {
            System.err.println("Błąd przy dodawaniu zadania");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean addExecutedTask(int taskId, long startTime, long endTime, long totalDuration) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO executedTasks VALUES (NULL, ?, ?, ?, ?)"
            );
            preparedStatement.setInt(1, taskId);
            preparedStatement.setLong(2, startTime);
            preparedStatement.setLong(3, endTime);
            preparedStatement.setLong(4, totalDuration);
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
            while(result.next()) {
                id = result.getInt("id");
                name = result.getString("name");
                description = result.getString("description");
                priority = result.getInt("priority");
                favourite = result.getBoolean("favourite");
                todo = result.getBoolean("todo");

                allTasks.add(
                        new Task(id, name, description, priority, favourite, todo)
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
        Task task = null;
        try {
            ResultSet result = statement.executeQuery("SELECT * FROM tasks WHERE name='"
                    + name + "'");

            if (result.next()) {
                id = result.getInt("id");
                priority = result.getInt("priority");
                description = result.getString("description");
                favourite = result.getBoolean("favourite");
                todo = result.getBoolean("todo");

                task = new Task(id, name, description, priority, favourite, todo);
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


    public static void main(String[] args) {
        CoreDB db = new CoreDB();
        //db.addTask("elo", "melo", 1, true, false);
        //db.addTask("gelo", "melo", 1, true, false);

        System.out.println(db.getTaskByName("elo"));
    }

}
