package ke.co.capiyo.fanzone.Models;

public class Posts {
    private String userid;
    private String club;


    private String team;


    private String message;
    private String phone;
    private String username;
    private String counter;



    private String  imageUrl;
    private  String  comments;


    private  String  statusImage;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    private  String  status;





    public Posts() {


    }

    public Posts(String username, String phone, String club, String team,
                 String userid, String note, String counter, String imageUrl, String statusImage, String status) {
        this.userid = userid;
        this.club = club;
        this.team = team;
        this.username = username;
        this.phone = phone;
        this.counter = counter;

          this.imageUrl = imageUrl;
        this.statusImage = statusImage;
        this.status = status;
    }
    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }
    public String getComments() {
        return comments;
    }
    public String getStatusImage() {
        return statusImage;
    }

    public void setStatusImage(String statusImage) {
        this.statusImage = statusImage;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }


    public String getNational() {
        return team;
    }

    public void setNational(String national) {
        this.team = national;
    }






}
