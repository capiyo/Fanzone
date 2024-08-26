package ke.co.capiyo.fanzone;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;

public class Search extends BottomSheetDialogFragment {
    public TextView  invite,message;
    private  AppViewModela modela;
    public  View rootview;
    public TextView username,tell,club,team;


        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable
        ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.activity_search,
                    container, false);
            invite=v.findViewById(R.id.invite);
            username=v.findViewById(R.id.accountName);
            tell=v.findViewById(R.id.accountTell);
            club=v.findViewById(R.id.favclub);
            team=v.findViewById(R.id.favTeam);
            message=v.findViewById(R.id.message);
            message.setOnClickListener(m->openMessaging());
            invite.setOnClickListener(i->sendInvite());
            rootview = v.findViewById(android.R.id.content);
            modela=new ViewModelProvider(requireActivity()).get(AppViewModela.class);
             modela.getData().observe(requireActivity(), data -> {
                 username.setText(data);
                 //Snackbar.make(rootview, "His Id is"+data, Snackbar.LENGTH_LONG)
                      //  .setAction("Action", null).show();
                 //  Toast.mak



            // update UI
               });
           // modela= new ViewModelProvider(requireActivity()).get(AppViewModela.class);
           // Toast.makeText(getContext(),modela.getData().toString(), Toast.LENGTH_SHORT).show();






            return v;



    }

    private void sendInvite() {
    }

    private void openMessaging() {
            Intent intent = new Intent(getContext(), Messaging.class);
          //  intent.putExtra("friendsId", model.getUserid());
            getContext().startActivity(intent);


    }
}