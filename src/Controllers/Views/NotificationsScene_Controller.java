package Controllers.Views;

import Controllers.MainApp;
import Controllers.Models.Notification;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class NotificationsScene_Controller {

    //********************************************************************************************************//
    //********************************************* CLASS FIELDS *********************************************//

    private MainApp mainApp;
    private BorderPane borderPane;

    @FXML
    private TableView<Notification> notifications_TableView;
    @FXML
    private TableColumn<Notification, String> username_TableColumn;
    @FXML
    private TableColumn<Notification, String> subject_TableColumn;
    @FXML
    private Button open_Button;

    //********************************************************************************************************//
    //******************************************** CLASS METHODS *********************************************//

    //Constructor
    @FXML
    private void initialize(){

        this.username_TableColumn.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
        this.subject_TableColumn.setCellValueFactory(cellData -> cellData.getValue().subjectProperty());
    }

    //Handlers
    @FXML
    private void onHandleOpen(){

        int selectedPost =this.notifications_TableView.getSelectionModel().getSelectedIndex();

        if(selectedPost>=0){

        this.mainApp.showPostDialog(this.notifications_TableView.getItems().get(selectedPost));

        }else{

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No Post Selected");
            alert.setContentText("Please select a post");

            alert.showAndWait();
        }

    }

    //Setters & Getters
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        this.notifications_TableView.setItems(this.mainApp.getActiveUser().getNotifications());
    }

    public void setBorderPane(BorderPane borderPane) {
        this.borderPane = borderPane;
    }
}
