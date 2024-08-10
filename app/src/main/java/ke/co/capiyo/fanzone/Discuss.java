package ke.co.capiyo.fanzone;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ke.co.capiyo.fanzone.Adapters.DiscussAd;
import ke.co.capiyo.fanzone.Models.DiscussMod;
import ke.co.capiyo.fanzone.Models.GamesModel;
import ke.co.capiyo.fanzone.Models.PoolModel;

import ke.co.capiyo.fanzone.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
//import android.support.v7.widget.DividerItemDecoration;
//import android.support.v7.widget.LinearLayoutManager;

public class Discuss extends AppCompatActivity {
    public Toolbar toolbar;
    public TextView mUsername, phone, mTime, sendMessage, openGallery;

    public Intent intent;
    public String gameId, comments, homeTeam, awayTeam, username;
    public EditText replyMessage;
    public FirebaseUser firebaseUser;

    public RecyclerView discussRecyclerview;
    public DiscussAd discussAd;
    public List<DiscussMod> models;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discuss);
        //fillToolbar();
        intent = getIntent();

        toolbar = findViewById(R.id.secondToolbar);
        mUsername = findViewById(R.id.musername);
        phone = findViewById(R.id.mphone);
        //mTime=findViewById(R.id.messagingToolbarTime);
        //friendsImage=findViewById(R.id.messagingToolbarImage);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle extras = intent.getExtras();


        gameId = extras.getString("gameId");
        homeTeam = extras.getString("homeTeam");
        awayTeam = extras.getString("awayTeam");

        fillToolbar();
        replyMessage = findViewById(R.id.replyText);
        sendMessage = findViewById(R.id.sendMessage);

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendComments();
            }
        });


        discussRecyclerview = findViewById(R.id.discussRecyclerView);
        discussRecyclerview.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        discussRecyclerview.setLayoutManager(linearLayoutManager);

        models = new ArrayList<>();


        readMessages();

    }

    private void readMessages() {
        DatabaseReference messageRef = FirebaseDatabase.getInstance().getReference("Discuss");
        messageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                models.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DiscussMod myModel = dataSnapshot.getValue(DiscussMod.class);
                    assert myModel != null;
                    if (myModel.getGameId().equals(gameId)) {
                        models.add(myModel);

                    }
                    discussAd = new DiscussAd(Discuss.this, models);
                    discussRecyclerview.setAdapter(discussAd);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void sendComments() {
        comments = replyMessage.getText().toString();
        if (TextUtils.isEmpty(comments)) {
            Toast.makeText(Discuss.this, "Please enter  Your comment", Toast.LENGTH_LONG).show();
        } else {


            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("statusUrl", "");
            hashMap.put("senderid", firebaseUser.getUid());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                hashMap.put("time", LocalDateTime.now().toString());
            }
            hashMap.put("gameId", gameId);
            hashMap.put("messages", comments);
            hashMap.put("username", username);

            DatabaseReference messagesref = FirebaseDatabase.getInstance().getReference("Discuss");
            messagesref.push().setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    replyMessage.setText("");
                    updateComments(gameId);
                }
            });


        }


    }

    public void fillToolbar() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PoolModel myModel = snapshot.getValue(PoolModel.class);
                assert myModel != null;


                mUsername.setText(homeTeam);
                phone.setText(awayTeam);
                username = myModel.getUsername();

                //toolbarImage.setBackgroundResource(R.drawable.finder);


                //navUsername.setText(myModel.getUsername());
                //navEname.setText(myModel.getEname());


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });

        // Toast.makeText(MainActivity.this, "Welcome to Fanzone", Toast.LENGTH_SHORT).show();

    }

    public void updateComments(String gameId) {
        final DatabaseReference gamesRef = FirebaseDatabase.getInstance().getReference("Games");
        Query query = gamesRef.orderByChild("gameId").equalTo(gameId);

        query.addListenerForSingleValueEvent((new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //   GamesModel myModel=snapshot.getValue(GamesModel.class);
                //int newCount=Integer.parseInt(myModel.getCounter());

                //int lastCount=newCount+1;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    GamesModel myModel = dataSnapshot.getValue(GamesModel.class);
                    //  games
                    int lastCount = Integer.parseInt(myModel.getCounter()) + 1;

                    //counter++;
                    assert myModel != null;
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("counter", Integer.toString(lastCount));
                    dataSnapshot.getRef().updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            //holder.likeCount.setText(checkCounter.getLikeCount());
                        }
                    });


                    //holder.myAmount.setText(betDetails(myModel.get);

                    //counter++;
                    //betDetails(betId,holder);
											   /*HashMap<String, Object> hashMap = new HashMap<>();
											   hashMap.put("counter","60");
											  snapshot.getRef().child("counter").setValue("60").addOnSuccessListener(new OnSuccessListener<Void>() {
													   @Override
													   public void onSuccess(Void unused) {
														   //replyMessage.setText("");
													   }
												   });
												   */
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }));


    }


    // get Image url and Captions


}
