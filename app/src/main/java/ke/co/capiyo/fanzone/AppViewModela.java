package ke.co.capiyo.fanzone;


import   android.text.TextUtils;
import  androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import ke.co.capiyo.fanzone.Models.View;

public class AppViewModela extends  BaseObservable{

    public View models;

    @Bindable
    public  String getxUserid(){
        return  models.getUserId();

    }
    public  void  setxUserid(String userid){
        models.setUserId(userid);
        notifyPropertyChanged(BR.xUserid);

    }





    public  void  onButtonClicked(){
        if(isValid()){

        }

    }
    public  boolean isValid(){
        return !TextUtils.isEmpty(getxUserid());
    }


    public AppViewModela(){
        models=new View("");
    }


}
