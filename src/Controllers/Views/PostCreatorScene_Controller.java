package Controllers.Views;

import Controllers.MainApp;
import Controllers.Models.NotificationType;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class PostCreatorScene_Controller {

    //********************************************************************************************************//
    //********************************************* CLASS FIELDS *********************************************//

    private MainApp mainApp;
    private BorderPane borderPane;

    @FXML
    private TextField subject_TextField;
    @FXML
    private TextArea content_TextArea;
    @FXML
    private Button clear_Button;
    @FXML
    private Button done_Button;

    //********************************************************************************************************//
    //******************************************** CLASS METHODS *********************************************//

    //Setters & Getters

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setBorderPane(BorderPane borderPane) {
        this.borderPane = borderPane;
    }

    //Handlers
    @FXML
    private void onHandleClear(){

        this.subject_TextField.clear();
        this.content_TextArea.clear();
    }

    @FXML
    private void onHandleDone(){

        if(validateFields()){

            JSONObject outputJson = new JSONObject();
            outputJson.put("Request", 3);
            outputJson.put("Username", this.mainApp.getActiveUser().getUsername());
            outputJson.put("Post", this.getFields());

            this.mainApp.getClient().write(outputJson.toJSONString());

            String inputString = this.mainApp.getClient().read();
            this.parsePublishRequest(inputString);

        }else{

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Missing Information");
            alert.setContentText("Please enter all the fields");

            alert.showAndWait();
        }
    }

    private boolean validateFields(){

        if(this.subject_TextField.getText().equals("") || this.content_TextArea.getText().equals("")){

            return false;
        }

        return true;
    }

    private JSONObject getFields(){

        JSONObject notification = new JSONObject();
        notification.put("Author", this.mainApp.getActiveUser().getUsername());
        notification.put("Subject", this.subject_TextField.getText());
        notification.put("Content", this.content_TextArea.getText());
        notification.put("Likes", 0);
        notification.put("Dislikes", 0);
        notification.put("Type", NotificationType.POST.toString());

        return notification;
    }

    private void parsePublishRequest(String inputString){

        JSONParser parser = new JSONParser();

        try{

            JSONObject inputJson = (JSONObject) parser.parse(inputString);
            boolean requestState = (boolean) inputJson.get("RequestState");

            if(requestState){

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Post Published");
                alert.setContentText("Sent to your followers");

                alert.showAndWait();

                this.onHandleClear();

            }else {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Request Error");
                alert.setContentText("Pleas try later");

                alert.showAndWait();

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
