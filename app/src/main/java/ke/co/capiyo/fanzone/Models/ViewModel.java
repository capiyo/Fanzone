package ke.co.capiyo.fanzone.Models;

public  class ViewModel {
    String userId;

    public ViewModel(String userId){
        this.userId=userId;

    }
    private ViewModel(){

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
