package Controllers;

import Controllers.Models.Notification;
import Controllers.Models.User;
import Controllers.Views.*;


import Server.Client;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.json.simple.JSONObject;

import java.io.IOException;

public class MainApp extends Application {

    //********************************************************************************************************//
    //********************************************* CLASS FIELDS *********************************************//

    private Stage primaryStage;
    private BorderPane rootLayout;

    private Client client;
    private User activeUser;

    //********************************************************************************************************//
    //******************************************** CLASS METHODS *********************************************//

    //Constructor
    public MainApp(){

        this.client = new Client();
        this.client.start();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("FXSocialNetwork");
        this.primaryStage.setResizable(false);
        this.customCloseRequest();

        this.initRootLayout();
        this.showSignInScene();
    }

    private void customCloseRequest(){

        this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {

                JSONObject outputJson = new JSONObject();
                outputJson.put("Request", -1);

                client.write(outputJson.toJSONString());
            }
        });
    }

    private void initRootLayout(){

        try {

            //Load Fxml File
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("Views/RootLayout.fxml"));
            this.rootLayout = (BorderPane) loader.load();

            //Create Scene
            Scene rootLayoutScene = new Scene(this.rootLayout);

            //Set
            this.primaryStage.setScene(rootLayoutScene);
            this.primaryStage.show();

        }catch (IOException e){

            e.printStackTrace();
        }

    }


    public void showSignInScene(){

        try {

            //Load Fxml File
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("Views/SignInScene_UI.fxml"));
            AnchorPane signInScene = (AnchorPane) loader.load();

            //Set Controller
            SignInScene_Controller controller = loader.getController();
            controller.setMainApp(this);

            //Set
            this.rootLayout.setCenter(signInScene);

        }catch (IOException e){

            e.printStackTrace();
        }

    }

    public void showSignUpScene(){

        try {

            //Load Fxml File
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("Views/SignUpScene_UI.fxml"));
            AnchorPane signUpScene = (AnchorPane) loader.load();

            //Set Controller
            SignUpScene_Controller controller = loader.getController();
            controller.setMainApp(this);

            //Set
            this.rootLayout.setCenter(signUpScene);

        }catch (IOException e){

            e.printStackTrace();
        }

    }

    public void showMenuScene(){

        try {

            //Load Fxml File
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("Views/MenuScene_UI.fxml"));
            AnchorPane menuScene = (AnchorPane) loader.load();

            //Set Controller
            MenuScene_Controller controller = loader.getController();
            controller.setMainApp(this);

            //Set
            this.rootLayout.setCenter(menuScene);

        }catch (IOException e){

            e.printStackTrace();
        }

    }

    public void showNotificationsScene(BorderPane borderPane){

        try {

            //Load Fxml File
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("Views/NotificationsScene_UI.fxml"));
            AnchorPane notificationScene = (AnchorPane) loader.load();

            //Set Controller
            NotificationsScene_Controller controller = loader.getController();
            controller.setMainApp(this);
            controller.setBorderPane(borderPane);

            //Set
            borderPane.setCenter(notificationScene);

        }catch (IOException e){

            e.printStackTrace();
        }

    }

    public void showSearchScene(BorderPane borderPane){

        try {

            //Load Fxml File
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("Views/SearchScene_UI.fxml"));
            AnchorPane searchScene = (AnchorPane) loader.load();

            //Set Controller
            SearchScene_Controller controller = loader.getController();
            controller.setMainApp(this);
            controller.setBorderPane(borderPane);

            //Set
            borderPane.setCenter(searchScene);

        }catch (IOException e){

            e.printStackTrace();
        }

    }

    public void showSettingsScene(BorderPane borderPane){

        try {

            //Load Fxml File
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("Views/SettingsScene_UI.fxml"));
            AnchorPane settingsScene = (AnchorPane) loader.load();

            //Set Controller
            SettingsScene_Controller controller = loader.getController();
            controller.setMainApp(this);
            controller.setBorderPane(borderPane);

            //Set
            borderPane.setCenter(settingsScene);

        }catch (IOException e){

            e.printStackTrace();
        }
    }

    public void showAboutScene(BorderPane borderPane){


        try {

            //Load Fxml File
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("Views/AboutScene_UI.fxml"));
            AnchorPane settingsScene = (AnchorPane) loader.load();

            //Set
            borderPane.setCenter(settingsScene);

        }catch (IOException e){

            e.printStackTrace();
        }
    }

    public void showPostCreatorScene(BorderPane borderPane){

        try {

            //Load Fxml File
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("Views/PostCreatorScene_UI.fxml"));
            AnchorPane postCreatorScene = (AnchorPane) loader.load();

            //Set Controller
            PostCreatorScene_Controller controller = loader.getController();
            controller.setMainApp(this);
            controller.setBorderPane(borderPane);

            //Set
            borderPane.setCenter(postCreatorScene);

        }catch (IOException e){

            e.printStackTrace();
        }
    }

    public void showPostDialog(Notification notification){

        try {

            //Load Fxml File
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("Views/PostManagerDialog_UI.fxml"));
            AnchorPane postManagerDialog = (AnchorPane) loader.load();

            //Create Stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Post Viewer");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.primaryStage);
            Scene scene = new Scene(postManagerDialog);
            dialogStage.setScene(scene);

            //Set Controller
            PostManagerDialog_Controller controller = loader.getController();
            controller.setMainApp(this);
            controller.setNotification(notification);

            dialogStage.showAndWait();

        }catch (IOException e){

            e.printStackTrace();
        }
    }


    //Setters & Getters
    public Client getClient() {
        return this.client;
    }

    public User getActiveUser() {
        return this.activeUser;
    }

    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
