package Controllers.Views;

import Controllers.MainApp;
import Controllers.Models.Notification;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SearchScene_Controller {

    //********************************************************************************************************//
    //********************************************* CLASS FIELDS *********************************************//

    private MainApp mainApp;
    private BorderPane borderPane;
    private ObservableList<Notification> posts;
    private JSONObject famousData;

    @FXML
    private TableView<Notification> posts_TableView;
    @FXML
    private TableColumn<Notification, String> postSubject_TableColumn;
    @FXML
    private TableColumn<Notification, String> postLikes_TableColumn;
    @FXML
    private TableColumn<Notification, String> postDislikes_TableColumn;
    @FXML
    private Label name_Label;
    @FXML
    private Label country_Label;
    @FXML
    private Label email_Label;
    @FXML
    private TextField searchBar_TextField;
    @FXML
    private Button search_Button;
    @FXML
    private Button follow_Button;
    @FXML
    private ImageView userImage_ImageView;


    //********************************************************************************************************//
    //******************************************** CLASS METHODS *********************************************//

    //Constructor
    public SearchScene_Controller(){

        this.posts = FXCollections.observableArrayList();

      }

    @FXML
    private void initialize(){


        this.postSubject_TableColumn.setCellValueFactory(cellData -> cellData.getValue().subjectProperty());
        this.postLikes_TableColumn.setCellValueFactory(cellData -> cellData.getValue().likesProperty().asString());
        this.postDislikes_TableColumn.setCellValueFactory(cellData -> cellData.getValue().dislikesProperty().asString());

        this.posts_TableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> onHandleSelectPost(newValue));

        this.showUserInformation(null);
    }

    private void showUserInformation(JSONObject userData){

        if(userData == null){

            this.name_Label.setText("");
            this.country_Label.setText("");
            this.email_Label.setText("");

            this.follow_Button.setDisable(true);
            this.follow_Button.setVisible(false);

        }else{

            this.famousData =userData;
            Image userIcon = new Image("file:Resources/Imgs/UserIcon1.png");
            this.userImage_ImageView.setImage(userIcon);
            this.name_Label.setText((String) userData.get("Name"));
            this.country_Label.setText((String) userData.get("Country"));
            this.email_Label.setText((String) userData.get("Email"));

            this.follow_Button.setDisable(false);
            this.follow_Button.setVisible(true);

            JSONArray postsArray = (JSONArray) userData.get("Posts");

            for(int post=0;post<postsArray.size();post++){

                this.posts.add(new Notification((JSONObject) postsArray.get(post)));
            }

            this.posts_TableView.setItems(this.posts);
        }
    }

    //Handlers
    private void onHandleSelectPost(Notification notification){

        this.mainApp.showPostDialog(notification);
    }

    @FXML
    private void onHandleFollow(){

        JSONObject outputJson = new JSONObject();
        outputJson.put("Request", 5);
        outputJson.put("Username", this.mainApp.getActiveUser().getUsername());
        outputJson.put("Famous", this.famousData.get("Username"));

        this.mainApp.getClient().write(outputJson.toJSONString());
        String inputString = this.mainApp.getClient().read();
        this.parseFollowRequest(inputString);

    }

    private void parseFollowRequest(String inputString){

        JSONParser parser = new JSONParser();

        try{

            JSONObject inputJson = (JSONObject) parser.parse(inputString);
            boolean requestState = (boolean) inputJson.get("RequestState");

            if(requestState){

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Follow Complete");
                alert.setContentText("Now you follow "+ this.famousData.get("Name"));

                alert.showAndWait();

            }else{

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Request Error");
                alert.setContentText("Pleas try later");

                alert.showAndWait();
            }

        }catch (ParseException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void onHandleSearch(){

        String username = this.searchBar_TextField.getText();

        JSONObject outputJson = new JSONObject();
        outputJson.put("Request", 6);
        outputJson.put("Username", username);

        this.mainApp.getClient().write(outputJson.toJSONString());


        String inputString = this.mainApp.getClient().read();
        System.out.println(inputString);

        this.parseSearchRequest(inputString);
    }

    private void parseSearchRequest(String inputString){

        JSONParser parser = new JSONParser();

        try{

            JSONObject inputJson = (JSONObject) parser.parse(inputString);
            boolean requestState = (boolean) inputJson.get("RequestState");

            if(requestState){

                JSONObject famousData = (JSONObject) inputJson.get("User");
                this.showUserInformation(famousData);

            }else{

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error User Not Found");
                alert.setContentText("Please check username");

                alert.showAndWait();
            }

        }catch (ParseException e){
            e.printStackTrace();
        }
    }

    //Setters & Getters
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setBorderPane(BorderPane borderPane) {
        this.borderPane = borderPane;
    }
}
