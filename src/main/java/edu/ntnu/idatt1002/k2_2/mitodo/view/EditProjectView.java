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
    }

    // Called for edit
    public void setProject(Project project)
    {
        this.project= project;
        projectTitle.setText(project.getTitle());
        if(rootProject == null){
            rootProject = project.getParent();
            if(rootProject == null){
                rootProject = Client.getRootProject();
            }
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

    private boolean pAC(String title){
        return rootProject.getProjects().stream().anyMatch(p -> p.getTitle().equalsIgnoreCase(title));
    }

    public void saveAndExit()
    {
        try {

            Project testPro = new Project(projectTitle.getText(), rootProject);
            if(rootProject != null) {
                System.out.println(rootProject.getTitle() + " - rootProject");
            }
            if(rootProject != null && pAC(testPro.getTitle())) {
                if(project != null && project.getTitle().equals(projectTitle.getText())){
                    System.out.println("Text equals as before");
                }else {
                    throw new IllegalArgumentException("Project already created");
                }
            }


            if (project != null) {
                if(project.getTitle().equals(projectTitle.getText())){
                    System.out.println("Text equals as before");
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
