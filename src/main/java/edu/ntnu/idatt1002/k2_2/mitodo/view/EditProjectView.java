package edu.ntnu.idatt1002.k2_2.mitodo.view;

import edu.ntnu.idatt1002.k2_2.mitodo.Client;
import edu.ntnu.idatt1002.k2_2.mitodo.data.Project;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class EditProjectView extends View{
    @FXML
    private TextField projectTitle;
    @FXML
    private VBox parent;

    private Project project;
    private Project rootProject;


    //Called for create
    public void setParentProject(Project rootProject)
    {
        this.rootProject = rootProject;
        System.out.println(rootProject);
    }

    // Called for edit
    public void setProject(Project project)
    {
        this.project= project;
        projectTitle.setText(project.getTitle());
        if(rootProject == null){
            rootProject = project.getParent();
        }
    }


    public void cancel()
    {
        ProjectView projectView = (ProjectView) Client.setView("ProjectView");
        if (project != null)
        {
            projectView.setProject(project);
        }
        else
        {
            projectView.setProject(Client.getQuickTasks());
        }
    }

    public void saveAndExit()
    {
        try {

            Project projectTest = rootProject.addProject(projectTitle.getText(), rootProject);

            if(projectTest.getParent() != null && projectTest.getParent().projectAlreadyCreated(projectTitle.getText())){
                rootProject.removeProject(projectTest.getID());
                throw new IllegalArgumentException("Project already created");
            }

            projectTest.getParent().projectAlreadyCreated(projectTitle.getText());
            projectTest.getParent().getProjects().forEach(p -> System.out.println(p.getTitle()));
            System.out.println(projectTest.getParent().getTitle());
            rootProject.removeProject(projectTest.getID());

            if (project != null) {
                if(project.getTitle().equals(projectTitle.getText())){

                }else {
                    project.setTitle(projectTitle.getText());
                    System.out.println("Project = 0");
                }
            } else {
                System.out.println("Legger til");
                project = rootProject.addProject(projectTitle.getText(), rootProject);
            }

            Client.getPrimaryView().updateMainMenu();
            cancel();
        }catch (IllegalArgumentException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

        public void delete(){
        if(project != null)
        {
            Client.getRootProject().removeFromAll(project.getID());
            Client.getPrimaryView().updateMainMenu();
            project = null;
        }
        cancel();
    }

    public Node getParent()
    {
        return parent;
    }
}
