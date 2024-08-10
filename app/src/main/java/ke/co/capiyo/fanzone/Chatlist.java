package ke.co.capiyo.fanzone;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ke.co.capiyo.fanzone.Adapters.ChatlistAdapter;
import ke.co.capiyo.fanzone.Adapters.PoolAdapter;
import ke.co.capiyo.fanzone.Models.ChatlistModels;
import ke.co.capiyo.fanzone.Models.MessageModels;
import ke.co.capiyo.fanzone.Models.Posts;

public class Chatlist extends BottomSheetDialogFragment {
    public Button invite, message;
    public String videoUrl, postingMessage;
    public RecyclerView recyclerView;
    public ChatlistAdapter  chatlistAdapter;
    public List<ChatlistModels> modela;
    public ProgressDialog progressdialogue;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
    ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_chatlist,
                container, false);
        recyclerView=v.findViewById(R.id.chatRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        modela = new ArrayList<>();
       //readChats();
        marker();
        return  v;
    }


        private void readChats() {
            //FirebaseAuth.getInstance().getCurrentUser() == null) {
              //  startActivity(new Intent(MainActivity.this, LoginActivity.class));
                FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();


            DatabaseReference Reference = FirebaseDatabase.getInstance().getReference("Messages");
            Reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                        for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                            MessageModels models = dataSnapshot1.getValue(MessageModels.class);
                            assert models != null;
                            DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Messages")
                                    .child(models.getReceiverid());
                            Query query = reference1.orderByChild("receiverid").equalTo(firebaseUser.getUid());
                            query.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    MessageModels model= snapshot.getValue(MessageModels.class);
                                   // String senderid=model.getSenderid();
                                    DatabaseReference ref2= FirebaseDatabase.getInstance().getReference("Posts");
                                    ref2.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            modela.clear();
                                            for (DataSnapshot dataSnapshot2:snapshot.getChildren()){
                                                ChatlistModels model=dataSnapshot2.getValue(ChatlistModels.class);
                                                modela.add(model);






                                            }

                                            chatlistAdapter= new ChatlistAdapter(getContext(), modela);
                                            recyclerView.setAdapter(chatlistAdapter);
                                            recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL));





                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });




                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


                        }
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });





           /* ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Fanzone Working...");
            progressDialog.setMessage("Wait Am Loading Your Pool Members");
            progressDialog.show();
            Reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    modela.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        ChatlistModels model = dataSnapshot.getValue(ChatlistModels.class);
                        assert model != null;
                        //if (!firebaseUser.getUid().equals(model.getUserId())) {
                          modela.add(model);
                        //}
                        //poolAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                     chatlistAdapter= new ChatlistAdapter(getContext(), modela);
                    recyclerView.setAdapter(chatlistAdapter);
                    recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL));

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressDialog.dismiss();

                }
            });

            */






        }

        public  void marker(){

            DatabaseReference ref2= FirebaseDatabase.getInstance().getReference("Posts");
            ref2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    modela.clear();
                    for (DataSnapshot dataSnapshot2:snapshot.getChildren()){
                        ChatlistModels model=dataSnapshot2.getValue(ChatlistModels.class);
                        modela.add(model);

                        //progressdialogue.dismiss();





                    }

                    chatlistAdapter= new ChatlistAdapter(getContext(), modela);
                    recyclerView.setAdapter(chatlistAdapter);
                    recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL));





                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });




        }

        }








































