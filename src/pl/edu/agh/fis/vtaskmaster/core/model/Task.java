package pl.edu.agh.fis.vtaskmaster.core.model;


public class Task {
    private int id;
    private String name;
    private String description;
    private int priority;
    private boolean favourite;
    private boolean todo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Task(int id, String name, String description, int priority, boolean favourite, boolean todo) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.favourite = favourite;
        this.todo = todo;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                ", favourite=" + favourite +
                ", todo=" + todo +
                '}';
    }
}
