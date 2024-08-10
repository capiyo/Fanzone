package ke.co.capiyo.fanzone.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ke.co.capiyo.fanzone.Models.MessageModels;
import ke.co.capiyo.fanzone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {


    private static final int MSG_LEFT = 0;
    private static final int MSG_RIGHT = 1;
    private final List<MessageModels> myModel;
    private final Context mContext;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    public MessageAdapter(Context context, List<MessageModels> myModel) {
        this.mContext = context;
        this.myModel = myModel;

    }

    @NonNull
    @Override
    public MessageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.message_rightt, parent, false);
            return new MessageAdapter.MyViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.message_left, parent, false);
            return new MessageAdapter.MyViewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MyViewHolder holder, int position) {
        MessageModels model = myModel.get(position);
        if (!model.getMessages().equals("")) {
            holder.displayMessage.setVisibility(View.VISIBLE);
            holder.displayMessage.setText(model.getMessages());
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
        if (myModel.get(position).getSenderid().equals(firebaseUser.getUid())) {
            return MSG_RIGHT;
        } else {
            return MSG_LEFT;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView displayMessage, displayCaption;

        public ImageView displayImage;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            displayMessage = itemView.findViewById(R.id.showMessage);
            displayImage = itemView.findViewById(R.id.showStatus);


        }
    }
}
