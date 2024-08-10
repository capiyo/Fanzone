package ke.co.capiyo.fanzone;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ke.co.capiyo.fanzone.Adapters.GamesAdapter;
import ke.co.capiyo.fanzone.Models.GamesModel;

import ke.co.capiyo.fanzone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
//import android.support.v7.widget.DividerItemDecoration;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//
//import android.support.v4.app.Fragment;


public class Games extends Fragment {
    public final int PICK_VIDEO = 1;
    public TextView postVideos;
    public VideoView postImageView;
    public Uri selectedVideoUri;
    public FirebaseAuth mAuth;
    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference, Reference;
    public ProgressDialog progressDialog;
    public FirebaseUser firebaseUser;
    //public FirebaseStorage firebaseStorage;
    //public StorageReference storageReference;
    public String videoUrl, postingMessage;
    public RecyclerView recyclerView;
    public GamesAdapter gamesAdapter;
    public List<GamesModel> modela;
    public ProgressDialog progressdialogue;


    public Games() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.games, container, false);


        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        recyclerView = view.findViewById(R.id.gamesRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        modela = new ArrayList<>();
        Reference = FirebaseDatabase.getInstance().getReference("Games");


        readGames();


        return view;
    }

    private void readGames() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Fanzone Working...");
        progressDialog.setMessage("Wait Am Loading Your Pool Members");
        progressDialog.show();

        Reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                modela.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    GamesModel model = dataSnapshot.getValue(GamesModel.class);
                    assert model != null;

                    //if(firebaseUser.getUid() !=model.getUserid()){
                    modela.add(model);


                    //}

                    //poolAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();

                }
                gamesAdapter = new GamesAdapter(getContext(), modela);
                recyclerView.setAdapter(gamesAdapter);
                recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();

            }
        });


    }

}
