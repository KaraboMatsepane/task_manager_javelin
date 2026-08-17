package domain;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A single to-do item managed by the task manager.
 * <p>
 * Every task is assigned a unique, auto-incrementing id at construction time
 * and starts out incomplete. Ids are drawn from a shared counter so they stay
 * unique across concurrent requests (Javalin serves requests on multiple
 * threads), but they are not persisted and will restart from 1 each time the
 * application starts.
 */
public class Task {

    private static final AtomicInteger idCounter = new AtomicInteger(0);

    private final int id;
    private final String description;
    private boolean done;

    /**
     * Creates a new, incomplete task with the next available id.
     *
     * @param description human-readable summary of the task; must not be null or blank
     * @throws IllegalArgumentException if {@code description} is null or blank
     */
    public Task(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("description must not be null or blank");
        }
        this.description = description;
        this.id = idCounter.incrementAndGet();
        this.done = false;
    }

    /** @return the task's unique id */
    public int getId() {
        return id;
    }

    /** @return the task's description */
    public String getDescription() {
        return description;
    }

    /** @return {@code true} if the task has been marked complete */
    public boolean isDone() {
        return done;
    }

    /** Marks the task as complete. */
    public void completeTask() {
        done = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task task)) return false;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        String taskIsDone = isDone() ? "[x]" : "[ ]";
        return "%s %d - %s".formatted(taskIsDone, id, description);
    }
}
