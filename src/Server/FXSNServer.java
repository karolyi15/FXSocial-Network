package Server;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.net.Socket;
import java.util.HashMap;

public class FXSNServer extends MultiThreadedServer{

    //********************************************************************************************************//
    //********************************************* CLASS FIELDS *********************************************//

    //Data Base
    private DataBase dataBase;


    //********************************************************************************************************//
    //******************************************** CLASS METHODS *********************************************//

    //Constructor
    public FXSNServer(){

        this.dataBase = DataBase.getInstance();

    }

   @Override
   protected void createServerThread(Socket socket){

        try {

            new FXSNServerThread(this, socket).start();

        }catch (Exception e){

            e.printStackTrace();
        }
   }

    //Data Base Access Management
    public synchronized JSONObject queryUser(String username, String password){

        JSONObject userData = this.dataBase.query("Users", username);
        JSONObject outputJson = new JSONObject();

        if(userData != null){

            if(userData.get("Password").equals(password)){

                outputJson.put("RequestState",true);
                outputJson.put("User",userData);

                return outputJson;
            }
        }

        outputJson.put("RequestState",false);
        return outputJson;
    }

    public synchronized JSONObject addUser(String username, JSONObject userData){

        boolean  requestState = this.dataBase.add("Users",username,userData);

        JSONObject outputJson = new JSONObject();

        outputJson.put("RequestState",requestState);

        return outputJson;
    }

    public synchronized  JSONObject deleteUser(String username){

        boolean requestState = this.dataBase.delete("Users",username);

        JSONObject outputJson = new JSONObject();

        outputJson.put("RequestState", requestState);

        return outputJson;
    }

    public synchronized JSONObject publishPost(String username, JSONObject post){
        JSONObject outputJson = new JSONObject();

        try {

            JSONObject userData = this.dataBase.query("Users", username);

            if (userData == null) {

                outputJson.put("RequestState", false);

            } else {

                JSONArray posts = (JSONArray) userData.get("Posts");
                posts.add(post);

                JSONArray followers = (JSONArray) userData.get("Followers");

                for(int follower=0;follower<followers.size();follower++){

                    JSONObject tempUser = this.dataBase.query("Users", (String) followers.get(follower));
                    JSONArray notifications = (JSONArray) tempUser.get("Notifications");
                    notifications.add(post);
                }

                outputJson.put("RequestState", true);
                this.dataBase.update();
            }

            return outputJson;

        }catch (Exception e){
            outputJson.put("RequestState", false);
            return outputJson;
        }
    }

    public synchronized JSONObject like(String username, String subject, long like){

        JSONObject outputJson = new JSONObject();

        try{

            JSONObject authorData = (JSONObject) this.dataBase.query("Users", username);
            JSONArray posts =(JSONArray) authorData.get("Posts");

            for(int post=0; post<posts.size();post++){

                JSONObject postData = (JSONObject) posts.get(post);
                String postSubject = (String) postData.get("Subject");

                if(postSubject.equals(subject)){

                    if(like == 0){

                        long dislikes = (long) postData.get("Dislikes");
                        dislikes+=1;
                        postData.put("Dislikes", dislikes);

                    }else if(like ==1){

                        long likes = (long) postData.get("Likes");
                        likes+=1;
                        postData.put("Likes", likes);

                        if(likes%10 == 0){
                            JSONArray followers = (JSONArray)  authorData.get("Followers");
                            JSONObject message = this.buildMessage(username,"Message", username+"'s publication has "+likes+" likes" );
                            for(int follower=0;follower<followers.size();follower++){

                                JSONObject tempUser = this.dataBase.query("Users", (String) followers.get(follower));
                                JSONArray notifications = (JSONArray) tempUser.get("Notifications");
                                notifications.add(message);
                            }
                        }

                    }

                    this.dataBase.update();
                    outputJson.put("RequestState", true);
                    return  outputJson;
                }
            }

        }catch (Exception e){

           outputJson.put("RequestState", false);
            return  outputJson;
        }

        outputJson.put("RequestState", false);
        return  outputJson;
    }

    public synchronized JSONObject follow(String famousUsername, String username){

        JSONObject outputJson = new JSONObject();

        try {

            JSONObject famousData = (JSONObject) this.dataBase.query("Users", famousUsername);

            if(famousData.get("AccountType").equals("FAMOUS")) {

                JSONArray famousFollowers = (JSONArray) famousData.get("Followers");
                famousFollowers.add(username);

                if (famousFollowers.size() % 10 == 0) {
                    JSONObject message = this.buildMessage(famousUsername, "Message", "Now " + famousUsername + " has" + famousFollowers.size() + " followers");
                    for (int follower = 0; follower < famousFollowers.size(); follower++) {

                        JSONObject tempUser = this.dataBase.query("Users", (String) famousFollowers.get(follower));
                        JSONArray notifications = (JSONArray) tempUser.get("Notifications");
                        notifications.add(message);
                    }

                }

                outputJson.put("RequestState", true);
                this.dataBase.update();

            }else {

                outputJson.put("RequestState", false);
            }

        }catch (Exception E){

            outputJson.put("RequestState", false);
        }

        return outputJson;
    }

    private JSONObject buildMessage(String author, String subject, String content){

        JSONObject outputJson = new JSONObject();
        outputJson.put("Author", author);
        outputJson.put("Subject", subject);
        outputJson.put("Content", content);
        outputJson.put("Likes", 0);
        outputJson.put("Dislikes", 0);
        outputJson.put("Type", "MESSAGE");

        return outputJson;
    }

    public synchronized JSONObject searchFamous(String username){

        JSONObject outputJson = new JSONObject();

        try{

            JSONObject userData = (JSONObject) this.dataBase.query("Users", username).clone();

            if(userData == null || userData.get("AccountType").equals("NORMAL")){

                outputJson.put("RequestState", false);
                return outputJson;

            }else{

                userData.remove("Password");
                userData.remove("Notifications");

                outputJson.put("User", userData);
                outputJson.put("RequestState", true);
                return outputJson;
            }

        }catch (Exception e){

            outputJson.put("RequestState", false);
            return outputJson;
        }
    }


    //Main
    public static void main(String[] args){

        iServer server = new FXSNServer();
        server.start();
    }
}
