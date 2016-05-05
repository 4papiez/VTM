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

    public CoreDB(String dbName) {
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
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbName, config.toProperties());
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

    public boolean addTask(Task task) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO tasks VALUES (?, ?, ?, ?, ?, ?)"
        );
        preparedStatement.setString(1, task.getName());
        preparedStatement.setString(2, task.getDescription());
        preparedStatement.setInt(3, task.getPriority());
        preparedStatement.setLong(4, task.getExpectedTime());
        preparedStatement.setBoolean(5, task.isFavourite());
        preparedStatement.setBoolean(6, task.isTodo());
        preparedStatement.execute();

        return true;
    }

    public boolean removeTaskByName(String taskName) throws SQLException {
        return statement.execute("DELETE FROM tasks WHERE name=" + taskName);
    }

    public boolean updateTask(Task task) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE tasks SET name = ?, description = ?, priority = ?, expectedTime = ?," +
                        "favourite = ?, todo = ? WHERE name = ?"
        );

        statement.setString(1, task.getName());
        statement.setString(2, task.getDescription());
        statement.setInt(3, task.getPriority());
        statement.setLong(4, task.getExpectedTime());
        statement.setBoolean(5, task.isFavourite());
        statement.setBoolean(6, task.isTodo());
        statement.setString(7, task.getOldName());

        statement.executeUpdate();
        statement.close();
        return true;
    }

    public boolean updateExecutedTask(ExecutedTask task) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE executedTasks SET duration = ? " +
                        "WHERE id = ?"
        );


        statement.setLong(1, task.getElapsedTime());
        statement.setInt(2, task.getId());

        statement.executeUpdate();
        statement.close();
        return true;
    }

    public boolean addExecutedTask(Task task, long startTime) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO executedTasks VALUES (NULL, ?, ?, ?, ?, ?)"
        );
        preparedStatement.setString(1, task.getName());
        preparedStatement.setLong(2, startTime);
        preparedStatement.setLong(3, 0);
        preparedStatement.setLong(4, 0);
        preparedStatement.setBoolean(5, false);
        preparedStatement.execute();

        return true;
    }

    private ArrayList<Task> getTasksWithQuery(String query) throws SQLException {
        ArrayList<Task> tasks = new ArrayList<>();
        ResultSet result = statement.executeQuery(query);
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
        return getTasksWithQuery("SELECT * FROM tasks WHERE todo");
    }

    public ArrayList<Task> getFavourites() throws SQLException {
        return getTasksWithQuery("SELECT * FROM tasks WHERE favourite");
    }

    public ArrayList<Task> getAllTasks() throws SQLException {
        return getTasksWithQuery("SELECT * FROM tasks");
    }

    public ArrayList<Task> getHistory() throws SQLException {
        return getTasksWithQuery(
                "SELECT name, description, priority, expectedTime, favourite, todo " +
                        "FROM tasks AS T JOIN executedTasks AS E " +
                        "ON E.taskName=T.name"
        );
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

    public boolean isTaskWithName(String taskName) throws SQLException {
        ResultSet result = statement.executeQuery("SELECT * FROM tasks WHERE name = '" + taskName + "'");
        return result.getFetchSize() == 1;
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
                    new ExecutedTask(id, taskName, startTime, endTime, elapsedTime, done)
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
            System.out.println("_______");

            Task myTask = db.getTaskByName("kupic chleb");
            if (myTask != null) {
                myTask.setName("zmienic bieg");
                myTask.setDescription("szybko i sprawnie");
                db.updateTask(myTask);
            }
            db.getAllTasks().forEach(System.out::println);

            db.getAllExecutedTasks().forEach(System.out::println);
            ExecutedTask myExecutedTask = db.getAllExecutedTasks().get(0);
            if (myExecutedTask != null) {
                myExecutedTask.setElapsedTime(600);
                db.updateExecutedTask(myExecutedTask);
            }
            db.getAllExecutedTasks().forEach(System.out::println);


        }
        catch (SQLException e) {

        }

    }

}
