package ke.co.capiyo.fanzone.Adapters;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ke.co.capiyo.fanzone.Messaging;
import ke.co.capiyo.fanzone.Models.PendingModel;
import ke.co.capiyo.fanzone.Models.PoolModel;
import ke.co.capiyo.fanzone.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

//import timber.log.Timber;

public class ArchiveAdapter extends RecyclerView.Adapter<ArchiveAdapter.MyViewHolder> {


    private final List<PendingModel> modela;
    private final Context mContext;
    public String friendsId, myTeam, myAmount, homeTeam, awayTeam, gameId;
    public PendingModel pending;
    private DatabaseReference mUserReference;
    private DataSnapshot gamesUpdated, betsWon;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;


    public ArchiveAdapter(Context context, List<PendingModel> modela) {
        this.mContext = context;
        this.modela = modela;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.archive_items, parent, false);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        return new MyViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final PendingModel pending = modela.get(position);
        gameId = pending.getGameId();

        if (pending.getMyTeam().equals("homeTeam")) {
            if (firebaseUser.getUid().equals(pending.getStarterId())) {
                //getMyDetails(pending.getStarterId(),holder);


                //holder.homeTeam.setBackgroundColor(Color.GREEN);
                //getMyDetails(pending.getStarterId(),holder);
                holder.linear.setVisibility(View.GONE);
                holder.hisLinear.setVisibility(View.GONE);
//				holder.homeTeam.setBackgroundResource(R.drawable.buttona);
                holder.awayTeam.setEnabled(false);
                final String hisTeam = pending.getAwayTeam();
                //holder.awayTeam.setClickable(false);


            } else {
                //holder.linear.setVisibility(View.GONE);
                holder.hisLinear.setVisibility(View.GONE);
                getMyDetails(pending.getStarterId(), holder);
//				holder.awayTeam.setBackgroundResource(R.drawable.buttona);
//				holder.homeTeam.setBackgroundResource(R.drawable.friends);
                //holder.awayTeam.setOnClickListener();

                //holder.awayTeam.setEnabled(false);
                //holder.homeTeam.setBackgroundColor(Color.RED);
            }


            //holder.homeTeam.setClickable(false);
            //holder.homeTeam.setVisibility(View.INVISIBLE);

            holder.awayTeam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String hisTeam = pending.getAwayTeam();
                    addFinisher(pending.getBetId(), hisTeam);


                    //amountDialogue(homeTeam,awayTeam,gameId,myTeam);

                }
            });


        }
        else if (pending.getMyTeam().equals("awayTeam")) {
            if (firebaseUser.getUid().equals(pending.getStarterId())) {
                //getMyDetails(pending.getStarterId(),holder);
                //holder.awayTeam.setBackgroundColor(Color.GREEN);
                holder.homeTeam.setEnabled(false);
//				holder.awayTeam.setBackgroundResource(R.drawable.buttona);
                //final String hisTeam=pending.getAwayTeam();
                //holder.myPhone.setText(pending.getStarterId());

                holder.hisLinear.setVisibility(View.GONE);

                holder.linear.setVisibility(View.GONE);


            } else {
                holder.linear.setVisibility(View.GONE);
                getHisDetails(pending.getStarterId(), holder);
//				holder.homeTeam.setBackgroundResource(R.drawable.buttona);


//				holder.awayTeam.setBackgroundResource(R.drawable.friends);
                //holder.homeTeam.setEnabled(false);
                //holder.awayTeam.setBackgroundColor(Color.RED);
            }
            //holder.awayTeam.setVisibility(View.INVISIBLE);
            //holder.awayTeam.setClickable(false);
            //holder.awayTeam.setBackgroundColor(Color.GREEN);
            holder.homeTeam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String hisTeam = pending.getAwayTeam();
                    addFinisher(pending.getBetId(), hisTeam);


                    //myTeam="homeTeam";

                    //amountDialogue(homeTeam,awayTeam,gameId,myTeam);

                }
            });


        }
        //Toast.makeText(mContext, pending.getMyTeam(), Toast.LENGTH_SHORT).show();


        holder.homeTeam.setText(pending.getHomeTeam());
        holder.awayTeam.setText(pending.getAwayTeam());
        //holder.progressTv.setText(pending.getProgress());


//		holder.accountimage.setBackgroundResource(R.drawable.finder);


        //firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //assert firebaseUser != null;


        holder.openMessaging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Messaging.class);
                intent.putExtra("friendsId", pending.getStarterId());
                mContext.startActivity(intent);
            }
        });

        // holder.counter.setText(myPosts.getCounter());

        /*
         * The algorithm for updating games upon game end
         *
         */




    }


    public void updateBetsOnGameEnd(String gameId){
        Task<DataSnapshot> betsRef = mUserReference.child("Bets/").orderByChild(gameId).get();
        betsRef.addOnCompleteListener(
                task -> {
                  if(task.isSuccessful()){
                       betsWon = task.getResult();
                       if(betsWon.getChildrenCount()<1){
                           //Timber.log(1, "No bets for the game...");
                       } else {
                           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                               betsWon.getChildren().forEach(
                                       DataSnapshot::getChildren
                               );
                           }
                       }
                  }
                }
        );
    }
    @Override
    public int getItemCount() {
        return modela.size();
    }

    public void getHisDetails(String hisId, final MyViewHolder holder) {
        if (hisId.equals(firebaseUser.getUid())) {
            holder.linear.setVisibility(View.GONE);
        } else {


            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users").child(hisId);
            //Query query=usersRef.orderByChild("userid").equalTo(myId);

            usersRef.addValueEventListener((new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    PoolModel myModel = snapshot.getValue(PoolModel.class);
                    holder.hisName.setText(myModel.getUsername());
                    holder.hisPhone.setText(myModel.getPhone());
                    //counter++;


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            }));
        }

    }
	/*public void amountDialogue( final String homeTeam, final String awayTeam, final String gameId, final String myTeam){
	 //Context context = mapView.getContext();
	 LinearLayout layout = new LinearLayout(mContext);
	 layout.setOrientation(LinearLayout.VERTICAL);

	 // Add a TextView here for the "Title" label, as noted in the comments
	 final EditText titleBox = new EditText(mContext);
	 //titleBox.setHint("Title");
	 //layout.addView(titleBox); // Notice this is an add method

	 // Add another TextView here for the "Description" label
	 final EditText descriptionBox = new EditText(mContext);
	 //descriptionBox.setHint("Description");
	 //layout.addView(descriptionBox); // Another add method
	 EditText amountEd=new EditText(mContext);
	 myAmount=amountEd.getText().toString();
	 layout.addView(amountEd);
	 final AlertDialog.Builder alert = new AlertDialog.Builder(
	 mContext);
	 alert.setTitle(" Enter Amount  To  Continue");

	 alert.setCancelable(true);
	 alert.setView(layout)
	 .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
	 @Override
	 public void onClick(DialogInterface dialog, int which) {
	 betDatabase(homeTeam,awayTeam,gameId,myTeam);





	 }
	 })
	 .setNegativeButton("Cancel",
	 new DialogInterface.OnClickListener() {
	 public void onClick(DialogInterface dialog, int id) {
	 dialog.cancel();
	 }
	 });

	 final AlertDialog dialog = alert.create();
	 dialog.show();

	 }
	 */

	/*public void betDatabase(final String homeTeam, final String awayTeam, final String gameId, final String myTeam ,final String betId){
	 generateRandom();
	 DatabaseReference betsRef=FirebaseDatabase.getInstance().getReference("Bets");
	 betsRef.addValueEventListener(new ValueEventListener() {
	 @Override
	 public void onDataChange(@NonNull DataSnapshot snapshot) {

	 HashMap<String ,String> hashMap= new HashMap<>();
	 hashMap.put("starterId",firebaseUser.getUid());
	 hashMap.put("finisherId","");
	 hashMap.put("gameId",gameId);
	 hashMap.put("betId",betId);
	 hashMap.put("myTeam",myTeam);
	 hashMap.put("homeTeam",homeTeam);
	 hashMap.put("awayTeam",awayTeam);
	 hashMap.put("hisTeam","hisTeam");
	 hashMap.put("myAmount",myAmount);
	 hashMap.put("hisAmount","");
	 hashMap.put("progress","Upcoming");
	 hashMap.put("score","");
	 DatabaseReference postRef=FirebaseDatabase.getInstance().getReference("Posts");
	 postRef.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
	 @Override
	 public void onComplete(@NonNull Task<Void> task) {
	 if (task.isSuccessful()){

	 Toast.makeText(mContext,"Your Bet was placed successfully please check on your bets",Toast.LENGTH_LONG).show();

	 }

	 }
	 });}
	 @Override
	 public void onCancelled(@NonNull DatabaseError error) {

	 }
	 });



	 // alertDialogue for amount




	 } 


	 */

    public void getMyDetails(String hisId, final MyViewHolder holder) {
        if (hisId.equals(firebaseUser.getUid())) {
            holder.linear.setVisibility(View.GONE);
        } else {


            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users").child(hisId);
            //Query query=usersRef.orderByChild("userid").equalTo(myId);

            usersRef.addValueEventListener((new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    PoolModel myModel = snapshot.getValue(PoolModel.class);
                    holder.myName.setText(myModel.getUsername());
                    holder.myPhone.setText(myModel.getPhone());
                    //counter++;


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            }));
        }

    }

    public void addFinisher(final String betId, final String hisTeam) {
        DatabaseReference betsref = FirebaseDatabase.getInstance().getReference("Bets");
        Query query = betsref.orderByChild("betId").equalTo(betId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    PendingModel myModel = dataSnapshot.getValue(PendingModel.class);
                    //counter++;
                    assert myModel != null;
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("finisherId", firebaseUser.getUid());
                    hashMap.put("hisTeam", hisTeam);
                    dataSnapshot.getRef().updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            //holder.likeCount.setText(checkCounter.getLikeCount());
                            Toast.makeText(mContext, "Your Bet was placed successfully please check on your bets", Toast.LENGTH_LONG);
                        }
                    });


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView usernameTv, phoneTv, progressTv, myName, myPhone, hisName, hisPhone;

        public TextView openMessaging, picMessage;
        public ImageView accountimage;
        public ImageView postImage;
        public Button homeTeam, awayTeam;
        public LinearLayout linear, hisLinear;

        public TextView counter;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            homeTeam = itemView.findViewById(R.id.homeTeam);
            myName = itemView.findViewById(R.id.myUsername);
            myPhone = itemView.findViewById(R.id.myPhone);
            awayTeam = itemView.findViewById(R.id.awayTeam);
            hisLinear = itemView.findViewById(R.id.hisLinear);
            linear = itemView.findViewById(R.id.linearLayout);
            hisName = itemView.findViewById(R.id.hisUsername);
            hisPhone = itemView.findViewById(R.id.hisPhone);
            openMessaging = itemView.findViewById(R.id.messaging);
            //progressTv=itemView.findViewById(R.id.progress);
//			accountimage=itemView.findViewById(R.id.myImage);


        }
    }


}
