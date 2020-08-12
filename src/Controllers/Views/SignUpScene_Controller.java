package Controllers.Views;

import Controllers.MainApp;
import Controllers.Models.AccountType;
import Controllers.Models.Countries;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SignUpScene_Controller {

    //********************************************************************************************************//
    //********************************************* CLASS FIELDS *********************************************//

    private MainApp mainApp;

    @FXML
    private TextField name_TextField;
    @FXML
    private TextField username_TextField;
    @FXML
    private TextField email_TextField;
    @FXML
    private ComboBox<Countries> country_ComboBox;
    @FXML
    private ComboBox<AccountType> accountType_ComboBox;
    @FXML
    private PasswordField password_PasswordField;
    @FXML
    private PasswordField confirmPassword_PasswordField;
    @FXML
    private Button confirm_Button;
    @FXML
    private Button cancel_Button;

    //********************************************************************************************************//
    //******************************************** CLASS METHODS *********************************************//

    //Constructor
    @FXML
    private void initialize(){

        this.initAccountTypes();
        this.initCountries();
    }

    private void initAccountTypes(){

        for(int type=0; type<AccountType.values().length;type++){

            this.accountType_ComboBox.getItems().add(AccountType.values()[type]);
        }
    }

    private void initCountries(){

        for(int country=0;country<Countries.values().length;country++){

            this.country_ComboBox.getItems().add(Countries.values()[country]);
        }
    }

    //Validations
    private JSONObject getFields(){

        JSONObject fieldsData = new JSONObject();

        fieldsData.put("Name",this.name_TextField.getText());
        fieldsData.put("Username",this.username_TextField.getText());
        fieldsData.put("Email",this.email_TextField.getText());
        fieldsData.put("Country",this.country_ComboBox.getValue().toString());
        fieldsData.put("AccountType",this.accountType_ComboBox.getValue().toString());
        fieldsData.put("Password",this.password_PasswordField.getText());
        fieldsData.put("Notifications",new JSONArray());
        fieldsData.put("Followers",new JSONArray());
        fieldsData.put("Posts",new JSONArray());

        return fieldsData;
    }

    private boolean validateFields(){

        if(this.name_TextField.getText().equals("") || this.username_TextField.getText().equals("")){

            return false;

        }else if(this.email_TextField.getText().equals("")){

            return false;

        }else if(this.country_ComboBox.getValue() == null || this.accountType_ComboBox.getValue() ==null){

            return  false;

        }else if(this.password_PasswordField.getText().equals("") || this.confirmPassword_PasswordField.getText().equals("")){

            return false;
        }else{

            return true;
        }

    }

    private boolean validatePassword(){

        if(this.password_PasswordField.getText().equals(this.confirmPassword_PasswordField.getText())){

            return  true;
        }

        return false;

    }

    //Handlers
    @FXML
    private void onHandleConfirm(){

        if(!this.validateFields()){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Missing Information");
            alert.setContentText("Please enter all the fields");

            alert.showAndWait();

        }else if(!this.validatePassword()){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error on Password");
            alert.setContentText("Confirm password field do not match password field");

            alert.showAndWait();

        }else{

            JSONObject outputJson = new JSONObject();
            outputJson.put("Request",0);
            outputJson.put("User",this.getFields());

            this.mainApp.getClient().write(outputJson.toJSONString());
            String inputString = this.mainApp.getClient().read();
            this.parseRequestState(inputString);

        }
    }

    private void parseRequestState(String inputString){

        JSONParser parser = new JSONParser();

        try {

            JSONObject inputJson = (JSONObject) parser.parse(inputString);
            boolean requestState = (boolean) inputJson.get("RequestState");

            if(requestState){

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Account Created");
                alert.setContentText("Congratulations you have created an account!");

                alert.showAndWait();

                this.mainApp.showSignInScene();

            }else {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error Creating Account");
                alert.setContentText("Username already exists");

                alert.showAndWait();

            }

        }catch (ParseException e){

            e.printStackTrace();
        }
    }

    @FXML
    private void onHandleCancel(){

        this.mainApp.showSignInScene();
    }

    //Setters & Getters
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

}
