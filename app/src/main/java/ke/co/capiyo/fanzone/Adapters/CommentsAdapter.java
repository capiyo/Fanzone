package ke.co.capiyo.fanzone.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ke.co.capiyo.fanzone.Models.CommentsMod;

import ke.co.capiyo.fanzone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.MyViewHolder> {


    private static final int MSG_LEFT = 0;
    private static final int MSG_RIGHT = 1;
    private final List<CommentsMod> myModel;
    private final Context mContext;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    public CommentsAdapter(Context context, List<CommentsMod> myModel) {
        this.mContext = context;
        this.myModel = myModel;

    }

    @NonNull
    @Override
    public CommentsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.message_rightt, parent, false);
            return new CommentsAdapter.MyViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.message_left, parent, false);
            return new CommentsAdapter.MyViewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull CommentsAdapter.MyViewHolder holder, int position) {
        CommentsMod model = myModel.get(position);
        System.out.println("modelMessages :: " + model.getComments());
//        if (!model.getSenderId().equals(firebaseUser.getUid())) {
//            holder.usernameTv.setVisibility(View.VISIBLE);
//            holder.usernameTv.setText(model.getUsername());
//        }
        if (model.getComments() != null) {
            holder.displayMessage.setVisibility(View.VISIBLE);
            holder.displayMessage.setText(model.getComments());
        } else {
            holder.displayMessage.setVisibility(View.GONE);
        }
        //holder.displayImage.setImageResource(R.drawable.finder);

        //  holder.displayImage.setImageResource(R.mipmap.account_avatar_foreground);


    }


    @Override
    public int getItemCount() {
        return myModel.size();
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        try{
//            System.out.println(myModel.get(position).getUsername());
//        } catch (Exception ex){
//            System.out.println("Debug :: Error occurred reading \n" +
//                    "        myModel.get(position).getSenderId() :: " +
//                    ex.getMessage());
//        }
        if (myModel.get(position).getUsername().equals(firebaseUser.getEmail())) {
            return MSG_RIGHT;
        } else {
            return MSG_LEFT;
        }
//        return  MSG_RIGHT;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView displayMessage, displayCaption, usernameTv;

        public ImageView displayImage;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            displayMessage = itemView.findViewById(R.id.showMessage);
            displayImage = itemView.findViewById(R.id.showStatus);
            usernameTv = itemView.findViewById(R.id.dUsername);

        }
    }
}
