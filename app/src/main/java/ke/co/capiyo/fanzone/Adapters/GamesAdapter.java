package ke.co.capiyo.fanzone.Adapters;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import ke.co.capiyo.fanzone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import ke.co.capiyo.fanzone.Discuss;
import ke.co.capiyo.fanzone.Models.GamesModel;

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.MyViewHolder> {


    private final List<GamesModel> modela;
    private final Context mContext;
    public String friendsId, myAmount, betId;
    private DatabaseReference mUserReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;


    public GamesAdapter(Context context, List<GamesModel> modela) {
        this.mContext = context;
        this.modela = modela;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.games_items, parent, false);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        return new MyViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final GamesModel games = modela.get(position);
        // gameId=games.getGameId();
        final String homeTeam = games.getHomeTeam();
        final String awayTeam = games.getAwayTeam();
        final String gameId = games.getGameId();


        //awayTeam=games.getAwayTeam();

        //awayTeam=games.getAwayTeam();
        holder.homeTeam.setText(homeTeam);
        String comments = games.getCounter() + " comments";
        holder.openDiscuss.setText(comments);
        holder.dateTv.setText(games.getDate());
        holder.timeTv.setText(games.getTime());
        holder.awayTeam.setText(awayTeam);
        holder.progressTv.setText(games.getProgress());
        holder.homeTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    //myTeam ="";
                    //homeTeam="";
                    //awayTeam="";
                    final String myTeam = "homeTeam";

                    //Toast.makeText(mContext,homeTeam+awayTeam,Toast.LENGTH_LONG).show();
                    amountDialogue(homeTeam, awayTeam, gameId, myTeam);

                }
                //awayTeam=games.getAwayTeam();
                //homeTeam=games.getHomeTeam();


                //myTeam=games.getHomeTeam();
                //awayTeam=games.getAwayTeam()
                ;

            }
        });

        holder.awayTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //myTeam=games.getAwayTeam();
                //awayTeam=games.getAwayTeam();
                //homeTeam=games.getHomeTeam();
                final String myTeam = "awayTeam";
                amountDialogue(homeTeam, awayTeam, gameId, myTeam);


            }
        });


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

        ;
        holder.openDiscuss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Discuss.class);
                Bundle extras = new Bundle();
                extras.putString("gameId", gameId);
                extras.putString("homeTeam", games.getHomeTeam());
                extras.putString("awayTeam", games.getAwayTeam());
                intent.putExtras(extras);
                mContext.startActivity(intent);
            }

        });
    }


    @Override
    public int getItemCount() {
        return modela.size();
    }

    public void amountDialogue(final String homeTeam, final String awayTeam, final String gameId, final String myTeam) {
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
        final EditText amountEd = new EditText(mContext);

        //layout.addView(amountEd);
        final AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
        alert.setTitle(" Enter Amount  To  Continue");

        alert.setCancelable(true);
        alert.setView(amountEd).setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myAmount = amountEd.getText().toString();
                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");
                //Query query	= usersRef.orderByChild("userid").equalTo(firebaseUser.getUid())
                //		.orderByChild("balance");
                usersRef.child(firebaseUser.getUid()).get().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Object usr = task.getResult();
                                String usrBalance = (String) task.getResult().child("balance").getValue();
                                System.out.println("User balance: " + usrBalance);
                                assert usrBalance != null;
                                if (Integer.parseInt(usrBalance) < Integer.parseInt(myAmount)) {
                                    Toast.makeText(mContext, "You have insufficient balance to bet: Ksh. " + myAmount + ". Please topup your account", Toast.LENGTH_LONG).show();
                                } else {
                                    int i = Integer.parseInt(usrBalance) - Integer.parseInt(myAmount);
                                    usersRef.child(firebaseUser.getUid()).child("balance").setValue(String.valueOf(i)).addOnCompleteListener(task1 -> {
										betDatabase(homeTeam, awayTeam, gameId, myTeam);
									});
                                }

                            } else {
								Toast.makeText(mContext, "Could not place bet.", Toast.LENGTH_LONG).show();
							}
						}

                );
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        final AlertDialog dialog = alert.create();
        dialog.show();

    }

    public void betDatabase(String homeTeam, String awayTeam, String gameId, String myTeam) {
        generateRandom();
        final DatabaseReference betsRef = FirebaseDatabase.getInstance().getReference("Bets");


        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("starterId", firebaseUser.getUid());
        hashMap.put("finisherId", "");
        hashMap.put("gameId", gameId);
        hashMap.put("betId", betId);
        hashMap.put("myTeam", myTeam);
        hashMap.put("homeTeam", homeTeam);
        hashMap.put("awayTeam", awayTeam);
        hashMap.put("hisTeam", "hisTeam");
        hashMap.put("myAmount", myAmount);
        hashMap.put("hisAmount", "");
        hashMap.put("progress", "Upcoming"); // fullTime
        hashMap.put("score", "");
        hashMap.put("outcome", "");
        hashMap.put("winner", ""); // if paid is  "false" and  progress is "fullTime"   and winner
        // is my ID then update my account with 80% of the total amount both parties used.
        hashMap.put("paid", String.valueOf(false));
        //DatabaseReference postRef=FirebaseDatabase.getInstance().getReference("Posts");
        betsRef.push().setValue(hashMap).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                //myTeam ="";
                //homeTeam="";
                //awayTeam="";

                Toast.makeText(mContext, "Your Bet was placed successfully please check on your bets", Toast.LENGTH_LONG).show();

            }

        });
    }

    public String generateRandom() {
        Random rand = new Random();
        int rand_int1 = rand.nextInt(100000);
        betId = String.valueOf(rand_int1);
        return betId;

    }


    // alertDialogue for amount

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView usernameTv, phoneTv, progressTv, timeTv, dateTv, openDiscuss,homeTeam,awayTeam;
        public TextView openMessaging, picMessage;
        public ImageView accountimage;
        public ImageView postImage;
        public LinearLayout layout;

        public TextView counter;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            homeTeam = itemView.findViewById(R.id.homeTeam);
            timeTv = itemView.findViewById(R.id.time);
            dateTv = itemView.findViewById(R.id.date);
            awayTeam = itemView.findViewById(R.id.awayTeam);
            progressTv = itemView.findViewById(R.id.progress);
            openDiscuss = itemView.findViewById(R.id.openDiscus);


        }
    }

}
