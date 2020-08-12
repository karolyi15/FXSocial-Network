package Controllers.Views;

import Controllers.MainApp;
import Controllers.Models.AccountType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class MenuScene_Controller {

    //********************************************************************************************************//
    //********************************************* CLASS FIELDS *********************************************//

    private MainApp mainApp;

    //Border Panel
    @FXML
    private BorderPane displayPanel_BorderPane;
    //Image View
    @FXML
    private ImageView userImage_ImageView;
    //Labels
    @FXML
    private Label username_Label;
    @FXML
    private Label accountType_Label;
    @FXML
    private Label followers_Label;
    @FXML
    private Label followersContent_Label;
    //Buttons
    @FXML
    private Button notifications_Button;
    @FXML
    private Button searchUser_Button;
    @FXML
    private Button settings_Button;
    @FXML
    private Button about_Button;
    @FXML
    private Button newPost_Button;

    //********************************************************************************************************//
    //******************************************** CLASS METHODS *********************************************//

    //Constructor
    @FXML
    private void initialize(){

        Image userIcon = new Image("file:Resources/Imgs/UserIcon1.png");
        this.userImage_ImageView.setImage(userIcon);

    }

    private void initUser(){

        String username = this.mainApp.getActiveUser().getUsername();
        this.username_Label.setText(username);

        AccountType accountType = this.mainApp.getActiveUser().getAccountType();
        this.accountType_Label.setText(accountType.toString());

        if(this.mainApp.getActiveUser().getAccountType() == AccountType.FAMOUS) {

            int followers = this.mainApp.getActiveUser().getFollowerCounter();
            this.followersContent_Label.setText(String.valueOf(followers));

        }else{

            this.followers_Label.setText("");
            this.followersContent_Label.setText("");

            this.newPost_Button.setDisable(true);
            this.newPost_Button.setVisible(false);
        }
    }

    //Handlers
    @FXML
    private void onHandleNewPost(){

        this.mainApp.showPostCreatorScene(this.displayPanel_BorderPane);
    }

    @FXML
    private void onHandleNotifications(){

        this.mainApp.showNotificationsScene(this.displayPanel_BorderPane);
    }

    @FXML
    private void onHandleSearch(){

        this.mainApp.showSearchScene(this.displayPanel_BorderPane);
    }

    @FXML
    private void onHandleSettings(){

        this.mainApp.showSettingsScene(this.displayPanel_BorderPane);
    }

    @FXML
    private void onHandleAbout(){

        this.mainApp.showAboutScene(this.displayPanel_BorderPane);
    }

    //Setters & Getters
    public void setMainApp(MainApp mainApp) {

        this.mainApp = mainApp;

        this.initUser();
        this.onHandleNotifications();
    }
}
