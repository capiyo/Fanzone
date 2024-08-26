package ke.co.capiyo.fanzone;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AppViewModela extends ViewModel {

    private MutableLiveData<String>  hisId=new MutableLiveData<String >() ;

    public void setNameData(String  data){
        hisId.setValue(data);
    }

    public  LiveData<String>  getData(){

        return  hisId;
    }


    }