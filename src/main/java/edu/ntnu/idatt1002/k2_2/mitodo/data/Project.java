package edu.ntnu.idatt1002.k2_2.mitodo.data;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.view.ProjectView;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

/**
 * Public class Project
 */
public class Project implements Serializable
{
    private UUID ID; //Had to remove final for JSON
    private String title;
    private ArrayList<Task> tasks; //Had to remove final for JSON
    private ArrayList<Project> projects; //Had to remove final for JSON
    private Project parent;

    public Project(String title)
    {
        if(title.isEmpty() || title.isBlank()){
            throw new IllegalArgumentException("Title of projects can't be empty");
        }
        if(projectAlreadyCreated(title)){
            throw new IllegalArgumentException("Project already created");
        }

        this.title = title.trim();
        this.tasks = new ArrayList<>();
        this.projects = new ArrayList<>();
        this.ID = UUID.randomUUID();
    }

    public Project(String title, Project parent)
    {
        if(title.isEmpty() || title.isBlank()){
            throw new IllegalArgumentException("Title of projects can't be empty");
        }
        if(projectAlreadyCreated(title)){
            throw new IllegalArgumentException("Project already created");
        }

        this.title = title.trim();
        this.tasks = new ArrayList<>();
        this.projects = new ArrayList<>();
        this.ID = UUID.randomUUID();
        this.parent = parent;
    }

    private Project(){} //Had to add this to use JSON

    private void setID(UUID ID) { //If this is not here, no ID will
        this.ID = ID;
    } //JSON needs this

    public UUID getID()
    {
        return ID;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        if(title.isEmpty() || title.isBlank()){
            throw new IllegalArgumentException("Title of projects can't be empty");
        }
        if(projectAlreadyCreated(title)){
            throw new IllegalArgumentException("Project already created");
        }
        this.title = title;
    }

    public ArrayList<Task> getTasks()
    {
        return new ArrayList<>(tasks);
    }

    public ArrayList<Task> getAllTasks()
    {
        ArrayList<Task> taskBucket = new ArrayList<>(tasks);

        for (Project project : projects)
        {
            taskBucket.addAll(project.getAllTasks());
        }

        return taskBucket;
    }

    public Task getTaskFromAll(UUID ID)
    {
        ArrayList<Task> allTasks = getAllTasks();
        for (Task task : allTasks)
        {
            if (task.getID().equals(ID))
            {
                return task;
            }
        }
        return null;
    }

     public void sortTasksByPriority(ArrayList <Task> list){
        Comparator<Task>priorityComparator = (t1, t2) -> t2.getPriority().compareTo(t1.getPriority());
        list.sort(priorityComparator);
    }

    public void sortTasksByDueDate(ArrayList <Task> list){ //Ugly code, but it works
        ArrayList<Task> noDueDate = new ArrayList<>();
        for(int i = 0; i<list.size(); i++){
            if(list.get(i).getDueDate()==null){
                noDueDate.add(list.remove(i));
            }
        }
        Comparator<Task> dueDateComparator = (t1, t2) -> t2.getDueDate().compareTo(t1.getDueDate());
        list.sort(dueDateComparator);
        list.addAll(noDueDate);
    }

    public void sortTasksByStartDate(ArrayList <Task> list){ //Ugly code, but it works
        ArrayList<Task> noStartDate = new ArrayList<>();
        for(int i = 0; i<list.size(); i++){
            if(list.get(i).getStartDate()==null){
                noStartDate.add(list.remove(i));
            }
        }
        Comparator<Task> startDateComparator = (t1, t2) -> t1.getStartDate().compareTo(t2.getStartDate());
        list.sort(startDateComparator);
        list.addAll(noStartDate);
    
    }


    public Task getTask(UUID id)
    {
        for (Task task : tasks)
        {
            if (task.getID().equals(id))
            {
                return task;
            }
        }

        return null;
    }

    public Task addTask(String title)
    {
         Task task = new Task(title);
         tasks.add(task);
         return task;
    }

    public void moveTask(UUID taskID, UUID projectID) {
         Task task = this.getTask(taskID);
         Client.getRootProject().getProject(projectID).addTask(task.getTitle(), task.getPriority(), task.getStartDate(), task.getDueDate(), task.getComments());
         removeTask(taskID);
    }

    public Task addTask(String title, PriorityEnum priority, LocalDate startDate, LocalDate dueDate, String comments)
    {
        try {
            Task task = new Task(title, priority, startDate, dueDate, comments);
            tasks.add(task);
            return task;
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }
    public Task getNewestTask(){ //Ehmmm, blir litt styr når me ska laga ein ny task, så detta får ver ein løysning enn så lenge
        return tasks.get(tasks.size()-1);
    }

    public boolean removeTask(UUID id)
    {
        return tasks.removeIf(task -> task.getID().equals(id));
    }
    public void removeTasksFromSubProjects(UUID id){ //added because it wasnt able to delete tasks from "over" projects
        tasks.removeIf(task -> task.getID().equals(id));
        for (Project p: projects) {
            p.removeTasksFromSubProjects(id);
        }
    }

    public ArrayList<Project> getProjects()
    {
        return new ArrayList<>(projects);
    }

    public Project getProject(UUID id)
    {
        for (Project project : projects)
        {
            if (project.getID().equals(id))
            {
                return project;
            }
        }
        for (Project project : projects)
        {
            Project result = project.getProject(id);
            if (result != null)
            {
                return result;
            }
        }
        return null;
    }

    public Project addProject(String title)
    {
        Project project = new Project(title);
        projects.add(project);
        return project;
    }

    public Project addProject(String title, Project parent)
    {
        Project project = new Project(title, parent);
        projects.add(project);
        return project;
    }

    public boolean removeProject(UUID id)
    {
        return projects.removeIf(project -> project.getID().equals(id));
    }

    public boolean projectAlreadyCreated(String title){
         if(parent == null || parent.getProjects().size() == 0){
             System.out.println(parent);
             return false;
         }
         parent.getProjects().stream().forEach(p -> System.out.println(p.getTitle()));
         return parent.getProjects().stream().anyMatch(p -> p.getTitle().toLowerCase().equals(title.toLowerCase()));
    }
    public boolean removeFromAll(UUID id)
    {
        boolean removed = removeProject(id);
        if (removed) return true;

        for(Project project : projects)
        {
            removed = project.removeFromAll(id);
            if (removed) return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(ID, project.ID);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(ID);
    }

    public Project getParent(){
        return parent;
    }

    @Override
    public String toString()
    {
        return "\nProject{" +
                "\nid=" + ID +
                "\ntitle='" + title +
                "\ntasks=" + tasks +
                "\nprojects=" + projects +
                "\n}";
    }
}