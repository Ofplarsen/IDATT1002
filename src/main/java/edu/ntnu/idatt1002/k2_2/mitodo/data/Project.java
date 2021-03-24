package edu.ntnu.idatt1002.k2_2.mitodo.data;

import java.util.ArrayList;
import java.util.UUID;

public class Project {
    private UUID id;
    private String title;
    private ArrayList<Task> tasks;
    private ArrayList<Project> projects;

    public Project(String title) {
        this.title = title;
        this.tasks = new ArrayList<>();
        this.projects = new ArrayList<>();
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
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
            if (task.getId().equals(id)) {
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
            if (task.getId().equals(id)) {
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
        application.getProjectbyTitle(projectTitle).addTask(tasks.get(indexOfTask));
        removeTaskbyTitle(title);
    }

    public void removeTask(UUID id) {
        tasks.removeIf(task -> task.getId().equals(id));
    }

    public void removeTaskbyTitle(String title) {
        tasks.removeIf(task -> task.getTitle().equals(title));
    }

    public ArrayList<Project> getProjects() {
        return projects;
    }

    public Project getProject(UUID id) {
        for (Project project : projects) {
            if (project.getId().equals(id)) {
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
            if (project.getId().equals(id)) {
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

    public void removeProject(UUID id) {
        projects.removeIf(project -> project.getId().equals(id));
    }

    public void removeProjectbyTitle(String title) {
        projects.removeIf(project -> project.getTitle().equals(title));
    }

    @Override
    public String toString() {
        return "\nProject{" +
                "\nid=" + id +
                "\ntitle='" + title +
                "\ntasks=" + tasks +
                "\nprojects=" + projects +
                "\n}";
    }
}
