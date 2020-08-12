package Controllers.Views;

import Controllers.MainApp;
import Controllers.Models.User;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SignInScene_Controller {

    //********************************************************************************************************//
    //********************************************* CLASS FIELDS *********************************************//

    private MainApp mainApp;

    @FXML
    private Label createAccount_Label;
    @FXML
    private TextField username_TextField;
    @FXML
    private PasswordField password_PasswordField;
    @FXML
    private Button signIn_Button;

    //********************************************************************************************************//
    //******************************************** CLASS METHODS *********************************************//

    //Constructor
    @FXML
    private void initialize(){

        this.onHandleCreateAccount();
    }

    //Handlers
    @FXML
    private void onHandleSignIn(){

        String username = this.username_TextField.getText();
        String password = this.password_PasswordField.getText();

        if(!username.equals("") && !password.equals("")){

            JSONObject outputJson = new JSONObject();
            outputJson.put("Request",1);
            outputJson.put("Username", username);
            outputJson.put("Password",password);

            this.mainApp.getClient().write(outputJson.toJSONString());

            String inputString = this.mainApp.getClient().read();
            this.parseRequestState(inputString);

        }else{

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Missing Information");
            alert.setContentText("Please enter all the fields");

            alert.showAndWait();
        }
    }

    private void parseRequestState(String inputString){

        JSONParser parser = new JSONParser();

        try{

            JSONObject inputJson = (JSONObject) parser.parse(inputString);
            boolean requestState = (boolean) inputJson.get("RequestState");

            if(requestState){

                this.mainApp.setActiveUser(new User((JSONObject) inputJson.get("User")));
                this.mainApp.showMenuScene();

            }else{

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error Getting User Information");
                alert.setContentText("Invalid username or password");

                alert.showAndWait();
            }

        }catch (ParseException e){

            e.printStackTrace();
        }
    }

    private void onHandleCreateAccount(){

        this.createAccount_Label.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){

                    mainApp.showSignUpScene();
                }
            }
        });
    }

    //Setter & Getters
    public void setMainApp(MainApp mainApp){

        this.mainApp = mainApp;
    }
}
