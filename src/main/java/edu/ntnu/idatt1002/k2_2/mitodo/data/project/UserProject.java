package edu.ntnu.idatt1002.k2_2.mitodo.data.project;

/**
 * A class representing a project made by the user.
 * All user project belongs to a parent project.
 *
 * @version 1.0.0
 */
public class UserProject extends Project
{
    private String title;
    private Project parent;
    protected final static int MAX_TITLE_SIZE = 28;

    /**
     * Constructs a new UserProject.
     * @param title The title of the project.
     * @param parent The parent project.
     * @throws IllegalArgumentException If the the length of the title is 0 or more than 28
     * or the parent project already contains a project with the given title.
     */
    public UserProject(String title, Project parent) throws IllegalArgumentException
    {
        this.parent = parent;
        if(title.isEmpty() || title.isBlank())
        {
            throw new IllegalArgumentException("Title of projects can't be empty");
        }
        if(title.length() > MAX_TITLE_SIZE){
            throw new IllegalArgumentException("Project Name must be below " + MAX_TITLE_SIZE + ".");
        }
        if(projectAlreadyCreated(title))
        {
            throw new IllegalArgumentException("Project already created");
        }

        this.title = title.trim();
    }

    public String getTitle()
    {
        return title;
    }

    /**
     * Sets the project title.
     * @param title The title of the project.
     * @throws IllegalArgumentException If the the length of the title is 0 or more than 28
     * or the parent project already contains a project with the given title.
     */
    public void setTitle(String title) throws IllegalArgumentException
    {
        if(title.isEmpty() || title.isBlank())
        {
            throw new IllegalArgumentException("Title of projects can't be empty");
        }
        if(title.length() > MAX_TITLE_SIZE){
            throw new IllegalArgumentException("Project Name must be below " + MAX_TITLE_SIZE + ".");
        }
        if(projectAlreadyCreated(title))
        {
            throw new IllegalArgumentException("Project already created");
        }
        this.title = title;
    }

    /**
     * Checks if this project can move to a new parent project.
     * @param newParentProject The new parent project.
     * @return False if this equals the new parent project or this contains the parent project. Otherwise true.
     */
    public boolean canMoveTo(Project newParentProject)
    {
        if (equals(newParentProject)) return false;
        return !getAllProjects().contains(newParentProject);
    }

    /**
     * Moves this project to a new parent project.
     * @param newParentProject The new parent project.
     * @return True if the project was moved to the new parent project and removed from the old parent project. Otherwise false.
     */
    public boolean moveTo(Project newParentProject)
    {
        if (!canMoveTo(newParentProject)) return false;

        parent.removeProject(getID());
        parent = newParentProject;
        parent.addProject(this);

        return true;
    }

    /**
     * @param title The title
     * @return True if the parent project already has a project with the given title. Otherwise false.
     */
    public boolean projectAlreadyCreated(String title)
    {
        if(parent == null || parent.getProjects().size() == 0)
        {
            return false;
        }
        return parent.getProjects().stream().anyMatch(p -> p.getTitle().equalsIgnoreCase(title));
    }

    /**
     * @return The parent project.
     */
    public Project getParent()
    {
        return parent;
    }

    @Override
    public String toString()
    {
        return title;
    }
}