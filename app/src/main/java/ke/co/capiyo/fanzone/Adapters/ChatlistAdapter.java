package ke.co.capiyo.fanzone.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import ke.co.capiyo.fanzone.Comments;
import ke.co.capiyo.fanzone.Messaging;
import ke.co.capiyo.fanzone.Models.ChatlistModels;
import ke.co.capiyo.fanzone.Models.CommentsMod;
import ke.co.capiyo.fanzone.R;

public class ChatlistAdapter extends RecyclerView.Adapter<ChatlistAdapter.MyViewHolder> {


    private final List<ChatlistModels> model;
    private final Context mContext;
    public String friendsId;
    private DatabaseReference mUserReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;


    public ChatlistAdapter(Context context, List<ChatlistModels> model) {
        this.mContext = context;
        this.model= model;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_items, parent, false);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        return new MyViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ChatlistModels models = model.get(position);
        //  holder.notification.setChecked(true);
//        holder.notification.setCheckMarkDrawable(R.drawable.finder);
       // holder.notification.setBackgroundResource(R.drawable.new_message);

        holder.clubTv.setText(models.getClub());
//////        holder.myAmount.setText(myModel.get);
        holder.nationalTv.setText(models.getNational());
        holder.noteTv.setText(models.getMessage());
        holder.usernameTv.setText(models.getUsername());
        holder.phoneTv.setText(models.getPhone());





        holder.relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Messaging.class);
                intent.putExtra("friendsId", models.getUserid());
                mContext.startActivity(intent);
            }

        });



        //holder.accountimage.setBackgroundResource(R.drawable.finder);


        //firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //assert firebaseUser != null;




       /* holder.openMessaging.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//Intent intent = new Intent(mContext, Messaging.class);
					//intent.putExtra("friendsId", myPosts.getUserid());
					//mContext.startActivity(intent);


				}

			});
			*/
        // holder.counter.setText(myPosts.getCounter());

    }

    public int getCommentsCounter(String friendsId){
        AtomicReference<Integer> numCom = new AtomicReference<>(0);

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Comments");
        Query query = usersRef.orderByChild("gameId").equalTo(friendsId);
        query.get().addOnCompleteListener(task -> {
            DataSnapshot data = task.getResult();
            for (DataSnapshot i : data.getChildren()) {
                CommentsMod myModel = i.getValue(CommentsMod.class);
                numCom.getAndSet(numCom.get() + 1);
                assert myModel != null;
            }
//            System.out.println("Debug :: count => " + numCom.get());
        });
        return numCom.get();
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public void posts(final MyViewHolder holder) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView usernameTv, phoneTv, clubTv, nationalTv, noteTv, counterTv;
        public TextView openMessaging, picMessage;
        public ImageView accountimag;
        public ImageView postImage;
        public RelativeLayout relative;
       // public Button notification;
        public CircularImageView poolImage;
        public CardView viewer;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            relative = itemView.findViewById(R.id.chatRelative);
            usernameTv = itemView.findViewById(R.id.chatusername);
            phoneTv = itemView.findViewById(R.id.chatphone);
            clubTv = itemView.findViewById(R.id.chatclub);
            noteTv = itemView.findViewById(R.id.chatnotification);
            nationalTv = itemView.findViewById(R.id.chatnational);
            accountimag=itemView.findViewById(R.id.chatImage);


        }

    }


}

