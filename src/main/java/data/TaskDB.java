package data;

import domain.Task;

import java.util.List;

public interface TaskDB {

    List<Task> getAllTasks();

    Task getTask(int id);

    void completeTask(int id);

    void createTask(Task task);

    void deleteTask(int id);
}
