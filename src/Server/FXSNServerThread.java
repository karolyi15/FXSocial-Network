package Server;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.Socket;

public class FXSNServerThread extends WriterServerThread{

    //********************************************************************************************************//
    //********************************************* CLASS FIELDS *********************************************//

    private FXSNServer fxsnServer;

    //********************************************************************************************************//
    //******************************************** CLASS METHODS *********************************************//

    //Constructor
    public FXSNServerThread(FXSNServer fxsnServer, Socket socket){

        super(fxsnServer,socket);
        this.fxsnServer = (FXSNServer) this.server;
    }

    //Client Communication
    @Override
    protected void parseInputString(String inputString){

        JSONParser parser = new JSONParser();

        try{

            JSONObject inputJson = (JSONObject) parser.parse(inputString);
            System.out.println(inputString);
            long requestID = (long) inputJson.get("Request");

            if(requestID == -1){
                //Terminate Thread
                this.terminate();

            }else if(requestID == 0){
                //Create User
                JSONObject userData = (JSONObject) inputJson.get("User");
                String username = (String) userData.get("Username");

                JSONObject requestState = this.fxsnServer.addUser(username,userData);
                this.write(requestState.toJSONString());

            }else if(requestID == 1){
                //Access User
                String username = (String) inputJson.get("Username");
                String password = (String) inputJson.get("Password");

                JSONObject userData = this.fxsnServer.queryUser(username,password);
                this.write(userData.toJSONString());

            }else  if(requestID == 2){
                //Remove User
                String username = (String) inputJson.get("Username");
                JSONObject requestState =this.fxsnServer.deleteUser(username);

                this.write(requestState.toJSONString());

            }else if(requestID == 3){
                //Publish Post
                String username = (String) inputJson.get("Username");
                JSONObject post = (JSONObject) inputJson.get("Post");

                this.write(this.fxsnServer.publishPost(username,post).toJSONString());

            }else if(requestID == 4){
                //Likes-Dislike

                String username = (String) inputJson.get("Username");
                String subject = (String) inputJson.get("PostSubject");
                long like = (long) inputJson.get("Like");

                this.write(this.fxsnServer.like(username, subject, like).toJSONString());

            }else if(requestID == 5){
                //Follow
                String famousUsername = (String) inputJson.get("Famous");
                String username = (String) inputJson.get("Username");

                this.write(this.fxsnServer.follow(famousUsername, username).toJSONString());

            }else if(requestID == 6){
                //Search Famous

                String username = (String) inputJson.get("Username");
                //System.out.println(username);
                JSONObject requestState = this.fxsnServer.searchFamous(username);
                System.out.println(requestState.toJSONString());

                this.write(requestState.toJSONString());
            }

        }catch (ParseException e){

            e.printStackTrace();
        }
    }
}
