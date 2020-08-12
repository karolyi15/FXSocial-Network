package Controllers.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Base64;

public class User {

    //********************************************************************************************************//
    //********************************************* CLASS FIELDS *********************************************//


    private StringProperty name;
    private StringProperty username;
    private StringProperty email;
    private Countries country;
    private AccountType accountType;
    private IntegerProperty followerCounter;
    private ObservableList<User> followers;
    private ObservableList<Notification> notifications;
    private ObservableList<Notification> posts;

    //********************************************************************************************************//
    //******************************************** CLASS METHODS *********************************************//

    //Constructor
    public User(){

        this.name = new SimpleStringProperty("");
        this.username = new SimpleStringProperty("");
        this.email = new SimpleStringProperty("");
        this.country = Countries.DEFAULT;
        this.accountType = AccountType.NORMAL;
        this.followerCounter = new SimpleIntegerProperty(0);
        this.followers = FXCollections.observableArrayList();
        this.notifications = FXCollections.observableArrayList();
        this.posts = FXCollections.observableArrayList();
    }

    public User(String username){


        this.name = new SimpleStringProperty("");
        this.username = new SimpleStringProperty(username);
        this.email = new SimpleStringProperty("");
        this.country = Countries.DEFAULT;
        this.accountType = AccountType.NORMAL;
        this.followerCounter = new SimpleIntegerProperty(0);
        this.followers = FXCollections.observableArrayList();
        this.posts = FXCollections.observableArrayList();
    }

    public User(String imgBase64,String name, String username, String email , String country, AccountType type,int followerCounter ,ArrayList<User> followers, ArrayList<Notification> notifications, ArrayList<Notification> posts){

        this.name = new SimpleStringProperty(name);
        this.username = new SimpleStringProperty(username);
        this.email = new SimpleStringProperty(email);
        this.country = Countries.valueOf(country);
        this.accountType = type;
        this.followerCounter = new SimpleIntegerProperty(followerCounter);

        this.followers = FXCollections.observableArrayList();
        this.notifications = FXCollections.observableArrayList();

        this.followers.addAll(followers);
        this.notifications.addAll(notifications);
        this.posts.addAll(posts);
    }

    public User(JSONObject userData){

        this.name = new SimpleStringProperty((String) userData.get("Name"));
        this.username = new SimpleStringProperty((String) userData.get("Username"));
        this.email = new SimpleStringProperty((String) userData.get("Email"));
        this.country = Countries.valueOf((String) userData.get("Country"));
        this.accountType = AccountType.valueOf((String) userData.get("AccountType"));

        //Parse Followers
        JSONArray followers = (JSONArray) userData.get("Followers");
        this.followerCounter = new SimpleIntegerProperty(followers.size());
        this.followers = FXCollections.observableArrayList();

        for(int follower=0;follower<followers.size();follower++){

            this.followers.add(new User((String) followers.get(follower)));
        }

        //Parse Notifications
        JSONArray notifications = (JSONArray) userData.get("Notifications");
        this.notifications = FXCollections.observableArrayList();

        for(int notification=0;notification<notifications.size();notification++){

            this.notifications.add(new Notification((JSONObject) notifications.get(notification)));
        }

        //Parse Posts
        JSONArray posts = (JSONArray) userData.get("Posts");
        this.posts = FXCollections.observableArrayList();

        for(int post=0;post<posts.size();post++){

            this.posts.add(new Notification((JSONObject) posts.get(post)));
        }
    }

    //Setters & Getters


    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getUsername() {
        return this.username.getValue();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public Countries getCountry() {
        return country;
    }

    public void setCountry(Countries country) {
        this.country = country;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public int getFollowerCounter() {
        return followerCounter.get();
    }

    public IntegerProperty followerCounterProperty() {
        return followerCounter;
    }

    public void setFollowerCounter(int followerCounter) {
        this.followerCounter.set(followerCounter);
    }

    public ObservableList<User> getFollowers() {
        return followers;
    }

    public void setFollowers(ObservableList<User> followers) {
        this.followers = followers;
    }

    public ObservableList<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(ObservableList<Notification> notifications) {
        this.notifications = notifications;
    }

    public ObservableList<Notification> getPosts() {
        return posts;
    }

    public void setPosts(ObservableList<Notification> posts) {
        this.posts = posts;
    }

    public JSONObject toJson(){

        JSONObject user = new JSONObject();


        user.put("Name",this.name.getValue());
        user.put("Username", this.username.getValue());
        user.put("Email", this.email.getValue());
        user.put("Country", this.country.toString());
        user.put("AccountType", this.accountType.toString());

        //Followers
        JSONArray followers = new JSONArray();
        for(int follower=0;follower<this.followers.size();follower++){

            followers.add(this.followers.get(follower).getUsername());
        }
        user.put("Followers",followers);

        //Notifications
        JSONArray notifications = new JSONArray();
        for(int notification=0; notification<this.notifications.size();notification++){

            notifications.add(this.notifications.get(notification).toJson());
        }
        user.put("Notifications",notifications);

        //Pots
        JSONArray posts = new JSONArray();
        for(int post=0;post<this.posts.size();post++){

            posts.add(this.posts.get(post).toJson());
        }
        user.put("Posts",posts);

        return user;
    }
}
