package ke.co.capiyo.fanzone;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class Search extends BottomSheetDialogFragment {
    public Button  invite,message;


        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable
        ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.activity_search,
                    container, false);
            invite=v.findViewById(R.id.invite);
            message=v.findViewById(R.id.message);
            message.setOnClickListener(m->openMessaging());
            invite.setOnClickListener(i->sendInvite());





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