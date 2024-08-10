package ke.co.capiyo.fanzone.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ke.co.capiyo.fanzone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import ke.co.capiyo.fanzone.Models.DiscussMod;

public class DiscussAd extends RecyclerView.Adapter<DiscussAd.MyViewHolder> {


    private final List<DiscussMod> myModel;

    private final Context mContext;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private  static final  int MSG_LEFT=0;
    private  static final int MSG_RIGHT=1;

    public DiscussAd(Context context, List<DiscussMod> myModel) {
        this.mContext = context;
        this.myModel =myModel;

    }

    @NonNull
    @Override
    public  DiscussAd.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==MSG_RIGHT){
            View view=LayoutInflater.from(mContext).inflate(R.layout.message_rightt,parent,false);
            return  new  DiscussAd.MyViewHolder(view);
        }
        else {
            View view=LayoutInflater.from(mContext).inflate(R.layout.message_left,parent,false);
            return  new  DiscussAd.MyViewHolder(view);

        }


    }


    @Override
    public void onBindViewHolder(@NonNull DiscussAd.MyViewHolder holder, int position) {
        DiscussMod model=myModel.get(position);
		if(!model.getSenderid().equals(firebaseUser.getUid())){

		holder.usernameTv.setVisibility(View.VISIBLE);
		holder.usernameTv.setText(model.getUsername());
		}


        if (!model.getMessages().equals("")){
            holder.displayMessage.setVisibility(View.VISIBLE);

            holder.displayMessage.setText(model.getMessages());

        }
        else {
			holder.displayMessage.setVisibility(View.GONE);
        }
		//holder.displayImage.setImageResource(R.drawable.finder);

		//  holder.displayImage.setImageResource(R.mipmap.account_avatar_foreground);



    }



    @Override
    public int getItemCount() {
        return myModel.size();
    }

    public class  MyViewHolder extends RecyclerView.ViewHolder {
        public TextView displayMessage, displayCaption,usernameTv;

        public  ImageView displayImage;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            displayMessage=itemView.findViewById(R.id.showMessage);
			displayImage=itemView.findViewById(R.id.showStatus);
			usernameTv=itemView.findViewById(R.id.dUsername);

        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        if (myModel.get(position).getSenderid().equals(firebaseUser.getUid())){
            return MSG_RIGHT;
        }else {
            return  MSG_LEFT;
        }
    }
}
