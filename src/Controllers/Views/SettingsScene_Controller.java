package Controllers.Views;

import Controllers.MainApp;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Optional;

public class SettingsScene_Controller {

    //********************************************************************************************************//
    //********************************************* CLASS FIELDS *********************************************//

    private MainApp mainApp;
    private BorderPane borderPane;

    @FXML
    private Label name_Label;
    @FXML
    private Label email_Label;
    @FXML
    private Label country_Label;
    @FXML
    private Label deleteAccount_Label;

    //********************************************************************************************************//
    //******************************************** CLASS METHODS *********************************************//

    //Constructor
    @FXML
    private void initialize(){

        this.onHandleDeleteAccount();
    }

    private void initUserInformation(){

        this.name_Label.setText(this.mainApp.getActiveUser().getName());
        this.email_Label.setText(this.mainApp.getActiveUser().getEmail());
        this.country_Label.setText(this.mainApp.getActiveUser().getCountry().toString());
    }

    //Setters & Getters
    public void setMainApp(MainApp mainApp){

        this.mainApp = mainApp;
        this.initUserInformation();
    }

    public void setBorderPane(BorderPane borderPane){

        this.borderPane = borderPane;
    }

    //Handlers
    private void onHandleDeleteAccount(){

        this.deleteAccount_Label.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)) {

                    TextInputDialog dialog = new TextInputDialog("walter");
                    dialog.setTitle("Delete Authorization");
                    dialog.setHeaderText("Please enter your username to confirm");
                    dialog.setContentText("Username:");

                    Optional<String> result = dialog.showAndWait();
                    if (result.isPresent()) {

                        if (result.get().equals(mainApp.getActiveUser().getUsername())) {

                            JSONObject outputJson = new JSONObject();
                            outputJson.put("Request", 2);
                            outputJson.put("Username", result.get());

                            mainApp.getClient().write(outputJson.toJSONString());
                            String requestState = mainApp.getClient().read();
                            parseRequestState(requestState);

                        } else {

                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setHeaderText("Error Deleting Account");
                            alert.setContentText("Username do not match");

                            alert.showAndWait();
                        }
                    }
                }
            }
        });

    }

    private void parseRequestState(String inputString){

        JSONParser parser = new JSONParser();

        try{

            JSONObject inputJson = (JSONObject) parser.parse(inputString);
            boolean requestState = (boolean) inputJson.get("RequestState");

            if(requestState){

                this.mainApp.setActiveUser(null);
                this.mainApp.showSignInScene();

            }else{

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error Deleting Account");
                alert.setContentText("There was an error in the server");

                alert.showAndWait();
            }

        }catch (ParseException e){

            e.printStackTrace();
        }

    }




}
