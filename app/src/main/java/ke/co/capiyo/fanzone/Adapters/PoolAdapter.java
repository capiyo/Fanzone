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

import ke.co.capiyo.fanzone.Comments;
import ke.co.capiyo.fanzone.Messaging;
import ke.co.capiyo.fanzone.Models.CommentsMod;
import ke.co.capiyo.fanzone.Models.Posts;

import ke.co.capiyo.fanzone.R;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class PoolAdapter extends RecyclerView.Adapter<PoolAdapter.MyViewHolder> {


    private final List<Posts> modela;
    private final Context mContext;
    public String friendsId;
    private DatabaseReference mUserReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;


    public PoolAdapter(Context context, List<Posts> modela) {
        this.mContext = context;
        this.modela = modela;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pool_items, parent, false);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        return new MyViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Posts model = modela.get(position);
        //  holder.notification.setChecked(true);
//        holder.notification.setCheckMarkDrawable(R.drawable.finder);

        holder.clubTv.setText(model.getClub());
//////        holder.myAmount.setText(myModel.get);
        //holder.nationalTv.setText(model.getNational());
        if(model.getMessage().isEmpty()){
            holder.noteTv.setVisibility(View.GONE);
        }
        holder.noteTv.setText(model.getMessage());
        holder.usernameTv.setText(model.getUsername());
        holder.phoneTv.setText(model.getPhone());
        if(model.getImageUrl().isEmpty()){
            holder.viewer.setVisibility(View.GONE);

        }
        else {
            holder.accountimag.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    . load(model.getImageUrl())
                            .into(holder.accountimag);


        }








        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Profiles");
        Query query = usersRef.orderByChild("userId").equalTo(firebaseUser.getUid());



//        int nComments = Integer.parseInt(model.getCounter());
        AtomicInteger numCom = new AtomicInteger(0);

        DatabaseReference commentsRef = FirebaseDatabase.getInstance().getReference("Comments");
        Query queryCom = commentsRef.orderByChild("gameId").equalTo(model.getUserid());
        queryCom.get().addOnCompleteListener(task -> {
            DataSnapshot data = task.getResult();
            for (DataSnapshot i : data.getChildren()) {
                CommentsMod myModel = i.getValue(CommentsMod.class);
                numCom.getAndSet(numCom.get() + 1);
                assert myModel != null;
            }
            System.out.println("Debug :: count => " + numCom.get());
          //  String counterTvTxt = numCom.get() == 0 ? "No comments" : ("♥ " + numCom.get() + " comments");
           // holder.counterTv.setText(counterTvTxt);
        });




        /* usersRef.addValueEventListener((new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

//                Profile myModel = snapshot.getValue(Profile.class);
                try {
                    // myModel = model; // == null ? new Profile() : model;
                    holder.clubTv.setText(model.getClub());
                    //  holder.myAmount.setText(myModel.get);
                    holder.nationalTv.setText(model.getNational());
                    holder.noteTv.setText(model.getNote());
                    holder.usernameTv.setText(model.getUsername());
                    holder.phoneTv.setText(model.getPhone());
                } catch (Exception ex) {
                    System.out.println("Pool Load Error: "+ex.getMessage());
                }


                //holder.myAmount.setText(betDetails(myModel.get);

                //counter++;
                //betDetails(betId,holder);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }));*/

        ;
        ;
        //posts(holder);
        holder.relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Messaging.class);
                intent.putExtra("friendsId", model.getUserid());
                mContext.startActivity(intent);
            }

        });

        holder.counterTv.setOnClickListener(v -> {
//            Toast.makeText(mContext,"Pool toast", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(mContext, Comments.class);
            intent.putExtra("friendsId", model.getUserid());
            intent.putExtra("phone", model.getPhone());
            intent.putExtra("note", model.getMessage());
            mContext.startActivity(intent);
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
        return modela.size();
    }

    public void posts(final MyViewHolder holder) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView usernameTv, phoneTv, clubTv, nationalTv, noteTv, counterTv;
        public TextView openMessaging, picMessage;
        public ImageView accountimag;
        public ImageView postImage;
        public RelativeLayout relative;
        public Button notification;
        public CircularImageView poolImage;
        public CardView viewer;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            relative = itemView.findViewById(R.id.myRelativePA);
            usernameTv = itemView.findViewById(R.id.username);
            viewer=itemView.findViewById(R.id.imagerh);
            phoneTv = itemView.findViewById(R.id.phone);
            clubTv = itemView.findViewById(R.id.club);
            noteTv = itemView.findViewById(R.id.note);
          //  nationalTv = itemView.findViewById(R.id.national);
            counterTv = itemView.findViewById(R.id.counter);
            accountimag=itemView.findViewById(R.id.imager);


            poolImage = itemView.findViewById(R.id.poolImage);

        }

    }


}
