package Controllers.Models;

import javafx.beans.property.*;
import org.json.simple.JSONObject;

public class Notification {

    //********************************************************************************************************//
    //********************************************* CLASS FIELDS *********************************************//

    private StringProperty author;
    private StringProperty subject;
    private StringProperty content;
    private LongProperty likes;
    private LongProperty dislikes;
    private NotificationType type;

    //********************************************************************************************************//
    //******************************************** CLASS METHODS *********************************************//

    //Constructor
    public Notification(){

        this.author = new SimpleStringProperty("");
        this.subject = new SimpleStringProperty("");
        this.content = new SimpleStringProperty("");
        this.likes = new SimpleLongProperty(0);
        this.dislikes = new SimpleLongProperty(0);
        this.type = NotificationType.MESSAGE;
    }

    public Notification(String author, String subject, String content, int likes, int dislikes, NotificationType type){

        this.author = new SimpleStringProperty(author);
        this.subject = new SimpleStringProperty(subject);
        this.content = new SimpleStringProperty(content);
        this.likes = new SimpleLongProperty(likes);
        this.dislikes = new SimpleLongProperty(dislikes);
        this.type = type;
    }

    public Notification(JSONObject notificationData){

        this.author = new SimpleStringProperty((String) notificationData.get("Author"));
        this.subject = new SimpleStringProperty((String) notificationData.get("Subject"));
        this.content = new SimpleStringProperty((String) notificationData.get("Content"));
        this.likes = new SimpleLongProperty((long) notificationData.get("Likes"));
        this.dislikes = new SimpleLongProperty((long) notificationData.get("Dislikes"));
        this.type = NotificationType.valueOf((String) notificationData.get("Type"));
    }

    //Setters & Getters
    public String getAuthor() {
        return author.get();
    }

    public StringProperty authorProperty() {
        return author;
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }

    public String getSubject() {
        return subject.get();
    }

    public StringProperty subjectProperty() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject.set(subject);
    }

    public String getContent() {
        return content.get();
    }

    public StringProperty contentProperty() {
        return content;
    }

    public void setContent(String content) {
        this.content.set(content);
    }

    public long getLikes() {
        return likes.get();
    }

    public LongProperty likesProperty() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes.set(likes);
    }

    public long getDislikes() {
        return dislikes.get();
    }

    public LongProperty dislikesProperty() {
        return dislikes;
    }

    public void setDislikes(long dislikes) {
        this.dislikes.set(dislikes);
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public JSONObject toJson(){

        JSONObject outputJson = new JSONObject();

        outputJson.put("Author",this.author.getValue());
        outputJson.put("Subject", this.subject.getValue());
        outputJson.put("Content", this.content.getValue());
        outputJson.put("Likes", this.likes.getValue());
        outputJson.put("Dislikes", this.dislikes.getValue());
        outputJson.put("Type", this.type.toString());

        return outputJson;
    }
}
