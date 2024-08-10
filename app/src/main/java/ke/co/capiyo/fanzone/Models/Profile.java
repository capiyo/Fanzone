package ke.co.capiyo.fanzone.Models;

public class Profile {
    private String userId;
    private String club;
    private String national;
    private String note;
    private String phone;
    private String username;
    private String counter;

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public Profile() {

    }

    public Profile(String username, String phone, String club, String national, String userId, String note, String counter) {
        this.userId = userId;
        this.club = club;
        this.national = national;
        this.note = note;
        this.username = username;
        this.phone = phone;
        this.counter = counter;


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


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getNational() {
        return national;
    }

    public void setNational(String national) {
        this.national = national;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


}
