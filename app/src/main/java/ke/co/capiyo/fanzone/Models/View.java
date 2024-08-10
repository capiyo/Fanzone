package ke.co.capiyo.fanzone.Models;

public  class View{
    String userId;

    public  View(String userId){
        this.userId=userId;

    }
    private  View(){

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
