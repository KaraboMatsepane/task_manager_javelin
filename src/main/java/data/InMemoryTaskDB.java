package data;

import domain.Task;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory {@link TaskDB} implementation backed by a {@link Map} keyed by task id.
 * <p>
 * All data lives only in memory and is lost when the application stops. A
 * {@link ConcurrentHashMap} is used because Javalin handles requests on
 * multiple threads, so tasks may be created, completed, or deleted concurrently.
 */
public class InMemoryTaskDB implements TaskDB {

    private final Map<Integer, Task> tasks;

    public InMemoryTaskDB() {
        tasks = new ConcurrentHashMap<>();
    }

    /** @return all stored tasks */
    @Override
    public List<Task> getAllTasks() {
        return tasks.values().stream().toList();
    }

    /**
     * @param id the task id to look up
     * @return the matching task
     * @throws IllegalArgumentException if no task with {@code id} exists
     */
    @Override
    public Task getTask(int id) {
        return requireTask(id);
    }

    /**
     * Marks the given task as complete.
     *
     * @param id the task id to complete
     * @throws IllegalArgumentException if no task with {@code id} exists
     */
    @Override
    public void completeTask(int id) {
        requireTask(id).completeTask();
    }

    /**
     * Stores a new task.
     *
     * @param task the task to store
     */
    @Override
    public void createTask(Task task) {
        tasks.put(task.getId(), task);
    }

    /**
     * Removes a task.
     *
     * @param id the task id to remove
     * @throws IllegalArgumentException if no task with {@code id} exists
     */
    @Override
    public void deleteTask(int id) {
        requireTask(id);

        tasks.remove(id);
    }

    /**
     * Looks up a task by id.
     *
     * @param id the task id to look up
     * @return the matching task
     * @throws IllegalArgumentException if no task with {@code id} exists
     */
    private Task requireTask(int id) {
        Task task = tasks.get(id);
        if (task == null) {
            throw new IllegalArgumentException("task not found");
        }
        return task;
    }

}
