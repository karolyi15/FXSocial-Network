package Controllers.Views;

import Controllers.MainApp;
import Controllers.Models.Notification;
import Controllers.Models.NotificationType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PostManagerDialog_Controller {

    //********************************************************************************************************//
    //********************************************* CLASS FIELDS *********************************************//

    private MainApp mainApp;
    private Notification notification;

    @FXML
    private TextField subject_TextField;
    @FXML
    private TextArea content_TextField;
    @FXML
    private Button like_Button;
    @FXML
    private Button dislike_Button;

    //********************************************************************************************************//
    //******************************************** CLASS METHODS *********************************************//

    private void showPost(){

        this.subject_TextField.setText(this.notification.getSubject());
        this.content_TextField.setText(this.notification.getContent());

        if(this.notification.getType() == NotificationType.MESSAGE){

            this.like_Button.setDisable(true);
            this.like_Button.setVisible(false);

            this.dislike_Button.setDisable(true);
            this.dislike_Button.setVisible(false);
        }
    }

    //Handlers
    @FXML
    private void onHandleLike(){

        JSONObject outputJson = new JSONObject();
        outputJson.put("Request", 4);
        outputJson.put("Username", this.notification.getAuthor());
        outputJson.put("PostSubject", this.notification.getSubject());
        outputJson.put("Like", 1);

        this.mainApp.getClient().write(outputJson.toJSONString());
        String inputString =this.mainApp.getClient().read();
        this.parseRequest(inputString);
    }

    @FXML
    private void onHandleDislike(){

        JSONObject outputJson = new JSONObject();
        outputJson.put("Request", 4);
        outputJson.put("Username", this.notification.getAuthor());
        outputJson.put("PostSubject", this.notification.getSubject());
        outputJson.put("Like", 0);

        this.mainApp.getClient().write(outputJson.toJSONString());
        String inputString =this.mainApp.getClient().read();
        this.parseRequest(inputString);
    }

    private void parseRequest(String inputString){

        JSONParser parser = new JSONParser();

        try{

            JSONObject inputJson = (JSONObject) parser.parse(inputString);
            boolean requestState = (boolean) inputJson.get("RequestState");

            if(requestState){
                System.out.println("Like Done");
            }else{
                System.out.println("Error in like");
            }

        }catch (ParseException e){
            e.printStackTrace();
        }
    }

    //Setters & Getters
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
        this.showPost();
    }
}
