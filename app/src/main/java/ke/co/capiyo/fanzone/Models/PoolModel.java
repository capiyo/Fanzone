package ke.co.capiyo.fanzone.Models;

public class PoolModel {
    private String userid;
    private String username;
    private String phone;
    private String password;

    private String balance;
    private  String lastComment;
    private String  gUsername;
    private  String   gClub;


    public PoolModel() {

    }

    public PoolModel(String username, String password, String userid, String phone, String balance,String lastComment,String gUsername,String gClub
    ) {
        this.userid = userid;
        this.username = username;
        this.phone = phone;
        this.password = password;
        this.balance = balance;
        this.lastComment=lastComment;
        this.gUsername=gUsername;
        this.gClub=gClub;


    }



    public String getLastComment() {
        return lastComment;
    }

    public void setLastComment(String lastComment) {
        this.lastComment = lastComment;
    }

    public String getgUsername() {
        return gUsername;
    }

    public void setgUsername(String gUsername) {
        this.gUsername = gUsername;
    }

    public String getgClub() {
        return gClub;
    }

    public void setgClub(String gClub) {
        this.gClub = gClub;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

}
