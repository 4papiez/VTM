package pl.edu.agh.fis.vtaskmaster.core.model;


public class Task {
    private String name;
    private String oldName;
    private String description;
    private int priority;
    private long expectedTime;
    private boolean favourite;
    private boolean todo;

    public String getOldName() {
        return oldName;
    }
    public long getExpectedTime() {
        return expectedTime;
    }

    public void setExpectedTime(long expectedTime) {
        this.expectedTime = expectedTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public boolean isTodo() {
        return todo;
    }

    public void setTodo(boolean todo) {
        this.todo = todo;
    }

    public Task(String name, String description, int priority, long expectedTime, boolean favourite, boolean todo) {
        this.name = name;
        this.oldName = name;
        this.description = description;
        this.priority = priority;
        this.expectedTime = expectedTime;
        this.favourite = favourite;
        this.todo = todo;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                ", expected time=" + expectedTime +
                ", favourite=" + favourite +
                ", todo=" + todo +
                '}';
    }
}
