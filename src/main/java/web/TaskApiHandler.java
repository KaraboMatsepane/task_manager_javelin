package web;

import data.InMemoryTaskDB;
import data.TaskDB;
import domain.CreateTaskRequest;
import domain.Task;
import io.javalin.http.Context;

public class TaskApiHandler {

    private static final TaskDB taskDatabase = new InMemoryTaskDB();

    public static void getAll(Context ctx) {
        ctx.json(taskDatabase.getAllTasks());
    }

    public static void getTask(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Task task = taskDatabase.getTask(id);
        ctx.status(200);
        ctx.json(task);
    }

    public static void completeTask(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));

        taskDatabase.completeTask(id);
        Task task = taskDatabase.getTask(id);

        ctx.status(200);
        ctx.json(task);
    }

    public static void createTask(Context ctx) {
        CreateTaskRequest taskRequest = ctx.bodyAsClass(CreateTaskRequest.class);
        Task task = new Task(taskRequest.description());
        taskDatabase.createTask(task);

        ctx.status(201);
        ctx.json(task);
    }

    public static void deleteTask(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        taskDatabase.deleteTask(id);

        ctx.status(204);
    }
}
