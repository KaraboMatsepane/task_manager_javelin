package web;

import data.InMemoryTaskDB;
import data.TaskDB;
import domain.CreateTaskRequest;
import domain.Task;
import io.javalin.http.Context;

/**
 * HTTP handlers for the {@code /tasks} endpoints.
 * <p>
 * Each method adapts a Javalin {@link Context} to and from {@link TaskDB},
 * translating request path params / bodies into {@link Task} operations and
 * writing the resulting status code and JSON body back onto the response.
 */
public class TaskApiHandler {

    private static final TaskDB taskDatabase = new InMemoryTaskDB();

    /** Responds with a {@code 200} and the JSON array of all stored tasks. */
    public static void getAll(Context ctx) {
        ctx.json(taskDatabase.getAllTasks());
    }

    /**
     * Responds with a {@code 200} and the JSON task matching the {@code id} path param.
     *
     * @throws NumberFormatException if the {@code id} path param is not an integer
     * @throws IllegalArgumentException if no task with that id exists
     */
    public static void getTask(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Task task = taskDatabase.getTask(id);
        ctx.status(200);
        ctx.json(task);
    }

    /**
     * Marks the task matching the {@code id} path param as complete, then responds
     * with a {@code 200} and the JSON representation of the now-completed task.
     *
     * @throws NumberFormatException if the {@code id} path param is not an integer
     * @throws IllegalArgumentException if no task with that id exists
     */
    public static void completeTask(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));

        taskDatabase.completeTask(id);
        Task task = taskDatabase.getTask(id);

        ctx.status(200);
        ctx.json(task);
    }

    /**
     * Creates a task from the {@link CreateTaskRequest} JSON body, then responds
     * with a {@code 201} and the JSON representation of the created task.
     *
     * @throws IllegalArgumentException if the request's description is null or blank
     */
    public static void createTask(Context ctx) {
        CreateTaskRequest taskRequest = ctx.bodyAsClass(CreateTaskRequest.class);
        Task task = new Task(taskRequest.description());
        taskDatabase.createTask(task);

        ctx.status(201);
        ctx.json(task);
    }

    /**
     * Removes the task matching the {@code id} path param, then responds with a
     * {@code 204} and no body.
     *
     * @throws NumberFormatException if the {@code id} path param is not an integer
     * @throws IllegalArgumentException if no task with that id exists
     */
    public static void deleteTask(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        taskDatabase.deleteTask(id);

        ctx.status(204);
    }
}
