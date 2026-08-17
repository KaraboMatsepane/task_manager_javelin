package data;

import domain.Task;

import java.util.List;

/**
 * Storage abstraction for tasks, independent of how/where they are persisted.
 */
public interface TaskDB {

    /** @return all stored tasks */
    List<Task> getAllTasks();

    /**
     * @param id the task id to look up
     * @return the matching task
     * @throws IllegalArgumentException if no task with {@code id} exists
     */
    Task getTask(int id);

    /**
     * Marks the given task as complete.
     *
     * @param id the task id to complete
     * @throws IllegalArgumentException if no task with {@code id} exists
     */
    void completeTask(int id);

    /**
     * Stores a new task.
     *
     * @param task the task to store
     */
    void createTask(Task task);

    /**
     * Removes a task.
     *
     * @param id the task id to remove
     * @throws IllegalArgumentException if no task with {@code id} exists
     */
    void deleteTask(int id);
}
