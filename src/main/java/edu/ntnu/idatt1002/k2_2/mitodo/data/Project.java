package edu.ntnu.idatt1002.k2_2.mitodo.data;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;
import com.fasterxml.jackson.databind.ser.std.UUIDSerializer;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Public class Project
 */
public class Project {
    private UUID ID ;
    private String title;
    private ArrayList<Task> tasks;
    private ArrayList<Project> projects;

    public Project(String title) {
        this.title = title;
        this.tasks = new ArrayList<>();
        this.projects = new ArrayList<>();
        this.ID = UUID.randomUUID();
    }

    public Project(){} //Empty constructor needed for some JSON related shenanigans

    public UUID getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> taskBucket = new ArrayList<>();
        if (tasks.size() > 0) {
            for (Task task : tasks) {
                taskBucket.add(task);
            }
        }
        for (Project project : projects) {
            if (project.getTasks().size() > 0) {
                for (Task task : project.getAllTasks()) {
                    taskBucket.add(task);
                }
            }
        }
        if (taskBucket.size() > 0) {
            return taskBucket;
        }
        return null;
    }

    public Task getTask(UUID id) {
        for (Task task : tasks) {
            if (task.getID().equals(id)) {
                return task;
            }
        }
        return null;
    }

    public Task getTask(String title) {
        for (Task task : tasks) {
            if (task.getTitle().equals(title)) {
                return task;
            }
        }
        return null;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void moveTask(UUID id, UUID projectId, Project application) {
        int indexOfTask = -1;
        for (Task task : tasks) {
            if (task.getID().equals(id)) {
                indexOfTask = tasks.indexOf(task);
            }
        }
        application.getProject(projectId).addTask(tasks.get(indexOfTask));
        removeTask(id);
    }

    public void moveTaskbyTitle(String title, String projectTitle, Project application) {
        int indexOfTask = -1;
        for (Task task : tasks) {
            if (task.getTitle().equals(title)) {
                indexOfTask = tasks.indexOf(task);
            }
        }
        application.getProjectbyTitle(projectTitle).addTask(tasks.get(indexOfTask)); //TODO: This will throw an IndexOutOfBoundsException if the title param does not match any task in the project, is that the way we want it to be? Maybe create our own exception?
        removeTaskbyTitle(title);
    }

    public boolean removeTask(UUID id) {
        return tasks.removeIf(task -> task.getID().equals(id));
    }

    public boolean removeTaskbyTitle(String title) {
        return tasks.removeIf(task -> task.getTitle().equals(title));
    }

    public ArrayList<Project> getProjects() {
        return projects;
    }

    public Project getProject(UUID id) {
        for (Project project : projects) {
            if (project.getID().equals(id)) {
                return project;
            } else {
                if (project.getProjects().size() > 0) {
                    return project.getProject(id);
                }
            }
        }
        return null;
    }

    public Project getProjectbyTitle(String title) {
        for (Project project : projects) {
            if (project.getTitle().equals(title)) {
                return project;
            } else {
                if (project.getProjects().size() > 0) {
                    return project.getProjectbyTitle(title);
                }
            }
        }
        return null;
    }

    public void addProject(Project project) {
        projects.add(project);
    }

    public void moveProject(UUID id, int index) {
        for (Project project : projects) {
            if (project.getID().equals(id)) {
                projects.remove(projects.indexOf(project));
                projects.add(index,project);
            }
        }
    }

    public void moveProjectbyTitle(String title, int index) {
        for (Project project : projects) {
            if (project.getTitle().equals(title)) {
                projects.remove(projects.indexOf(project));
                projects.add(index,project);
            }
        }
    }

    public boolean removeProject(UUID id) {
        return projects.removeIf(project -> project.getID().equals(id));
    }

    public boolean removeProjectbyTitle(String title) {
        return projects.removeIf(project -> project.getTitle().equals(title));
    }

    @Override
    public String toString() {
        return "\nProject{" +
                "\nid=" + ID +
                "\ntitle='" + title +
                "\ntasks=" + tasks +
                "\nprojects=" + projects +
                "\n}";
    }
}