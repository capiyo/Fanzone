package ke.co.capiyo.fanzone.Models;

public class CommentsMod {
    private String senderId;
    private String comments;
    private String statusUrl;
    private String time;
    private String username;

    public CommentsMod() {

    }

    public CommentsMod(String senderId, String messages, String statusUrl, String time, String username) {
        this.senderId = senderId;
        this.comments = messages;
        this.statusUrl = statusUrl;
        this.time = time;
        this.username = username;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getStatusUrl() {
        return statusUrl;
    }

    public void setStatusUrl(String statusUrl) {
        this.statusUrl = statusUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

}
