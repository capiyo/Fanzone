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

import ke.co.capiyo.fanzone.Adapters.CommentsAdapter;
import ke.co.capiyo.fanzone.Models.CommentsMod;
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
import java.util.Objects;

public class Comments extends AppCompatActivity {
    public Toolbar toolbar;
    public TextView mUsername, phone, mTime, sendComment, openGallery, noteTextTV;

    public Intent intent;
    public String gameId, comments, phoneNumber, noteText, username;
    public EditText replyComment;
    public FirebaseUser firebaseUser;

    public RecyclerView commentsRecyclerView;
    public CommentsAdapter commentsAdapter;
    public List<CommentsMod> models;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_activity);
        //fillToolbar();
        intent = getIntent();

        toolbar = findViewById(R.id.secondToolbar);
        mUsername = findViewById(R.id.musername);
        phone = findViewById(R.id.mphone);
        noteTextTV = findViewById(R.id.poolNote);
        //mTime=findViewById(R.id.messagingToolbarTime);
        //friendsImage=findViewById(R.id.messagingToolbarImage);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle extras = intent.getExtras();


        assert extras != null;
        gameId = extras.getString("friendsId");
        phoneNumber = extras.getString("phone");
        noteText = extras.getString("note");


//        System.out.println("game: "+gameId);
//        System.out.println("Phone: "+ phoneNumber);
//        System.out.println("note: "+ noteText);

        fillToolbar();
        replyComment = findViewById(R.id.replyText);
        sendComment = findViewById(R.id.sendComment);

        commentsRecyclerView = findViewById(R.id.commentsRecyclerView);

        sendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendComments();
            }
        });


        commentsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        commentsRecyclerView.setLayoutManager(linearLayoutManager);

        models = new ArrayList<>();


        // set the note in the TV
        noteTextTV.setText(noteText);

        this.readMessages();

    }

    private void readMessages() {
        DatabaseReference commentsRef = FirebaseDatabase.getInstance().getReference("Comments");
        commentsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                models.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CommentsMod myModel = dataSnapshot.getValue(CommentsMod.class);
                    assert myModel != null;
                    if (myModel.getGameId().equals(gameId)) {
                        models.add(myModel);

                    }
                    commentsAdapter = new CommentsAdapter(Comments.this, models);
                    commentsRecyclerView.setAdapter(commentsAdapter);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void sendComments() {
        comments = replyComment.getText().toString().trim();
        if (TextUtils.isEmpty(comments)) {
            Toast.makeText(Comments.this, "Please enter  Your comment", Toast.LENGTH_LONG).show();
        } else {


            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("statusUrl", "");
            hashMap.put("senderId", firebaseUser.getUid());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                hashMap.put("time", LocalDateTime.now().toString());
            }
            hashMap.put("gameId", gameId);
            hashMap.put("comments", comments);
            hashMap.put("username", username);

            DatabaseReference commentsRef = FirebaseDatabase.getInstance().getReference("Comments");
            commentsRef.push().setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    replyComment.setText("");
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
                username = myModel.getUsername();
                mUsername.setText(username);
                phone.setText(phoneNumber);

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
                    assert myModel != null;
                    int lastCount = Integer.parseInt(myModel.getCounter());

                    //counter++;
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("counter", Integer.toString(lastCount)+1);
                    dataSnapshot.getRef().updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            //holder.likeCount.setText(checkCounter.getLikeCount());
                        }
                    });


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }));


    }


    // get Image url and Captions


}
