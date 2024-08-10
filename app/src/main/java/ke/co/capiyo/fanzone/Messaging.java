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

import ke.co.capiyo.fanzone.Adapters.MessageAdapter;
import ke.co.capiyo.fanzone.Models.MessageModels;
import ke.co.capiyo.fanzone.Models.PoolModel;

import ke.co.capiyo.fanzone.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
//import android.support.v7.widget.DividerItemDecoration;
//import android.support.v7.widget.LinearLayoutManager;

public class Messaging extends AppCompatActivity {
    public Toolbar toolbar;
    public TextView mUsername, phone, mTime, sendMessage, openGallery;

    public Intent intent;
    public String friendsId, message;
    public EditText replyMessage;
    public FirebaseUser firebaseUser;

    public RecyclerView messagesRecyclerview;
    public MessageAdapter messagesAdapter;
    public List<MessageModels> models;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_activity);
        //fillToolbar();
        intent = getIntent();

        toolbar = findViewById(R.id.secondToolbar);
        mUsername = findViewById(R.id.musername);
        phone = findViewById(R.id.mphone);
        //mTime=findViewById(R.id.messagingToolbarTime);
        //friendsImage=findViewById(R.id.messagingToolbarImage);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        friendsId = intent.getStringExtra("friendsId");
        fillToolbar();
        replyMessage = findViewById(R.id.replyText);
        sendMessage = findViewById(R.id.sendMessage);
        openGallery = findViewById(R.id.sendImages);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessages();
            }
        });


        messagesRecyclerview = findViewById(R.id.messaginRecyclerView);
        messagesRecyclerview.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        messagesRecyclerview.setLayoutManager(linearLayoutManager);

        models = new ArrayList<>();


        readMessages();

    }

    private void readMessages() {
        DatabaseReference messageRef = FirebaseDatabase.getInstance().getReference("Messages");
        messageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                models.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for ( DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        MessageModels models1=dataSnapshot1.getValue(MessageModels.class);
                        if (models1.getReceiverid().equals(firebaseUser.getUid()) && models1.getSenderid().equals(friendsId) ||
                         models1.getReceiverid().equals(friendsId) && models1.getSenderid().equals(firebaseUser.getUid())) {
                            models.add(models1);


                           }


                    }

                    //MessageModels myModel = dataSnapshot.getValue(MessageModels.class);
                    //assert myModel != null;

                  //  if (myModel.getReceiverid().equals(firebaseUser.getUid()) && myModel.getSenderid().equals(friendsId) ||
                         //   myModel.getReceiverid().equals(friendsId) && myModel.getSenderid().equals(firebaseUser.getUid())) {


                 //   }
                    messagesAdapter = new MessageAdapter(Messaging.this, models);
                    messagesRecyclerview.setAdapter(messagesAdapter);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void sendMessages() {
        message = replyMessage.getText().toString();
        if (TextUtils.isEmpty(message)) {
            Toast.makeText(Messaging.this, "Please enter message", Toast.LENGTH_LONG).show();
        } else {
            //Toast.makeText(Messaging.this, "Please enter message", Toast.LENGTH_LONG).show();


            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("statusUrl", "");
            hashMap.put("senderid", firebaseUser.getUid());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                hashMap.put("time", LocalDateTime.now().toString());
            }
            hashMap.put("receiverid", friendsId);
            hashMap.put("messages", message);
            hashMap.put("seen","false");

            DatabaseReference messagesref = FirebaseDatabase.getInstance().getReference("Messages").
                    child(firebaseUser.getUid());
            messagesref.push().setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                    replyMessage.setText("");
                }
            });


        }


    }

    public void fillToolbar() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(friendsId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PoolModel myModel = snapshot.getValue(PoolModel.class);
                System.out.println("DEBUG :: myModel: "+ myModel);
//                assert myModel != null;
                mUsername.setText(myModel.getUsername());
                phone.setText(myModel.getPhone());
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


    // get Image url and Captions


}
