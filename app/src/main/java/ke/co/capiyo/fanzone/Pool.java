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

import ke.co.capiyo.fanzone.Adapters.PoolAdapter;
import ke.co.capiyo.fanzone.Models.Posts;
import ke.co.capiyo.fanzone.Models.Profile;

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


public class Pool extends Fragment {
    public final int PICK_VIDEO = 1;
    public TextView postVideos, counter;
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
    public PoolAdapter poolAdapter;
    public List<Posts> modela;
    public ProgressDialog progressdialogue;


    public Pool() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.pool, container, false);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth
                .getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        recyclerView = view.findViewById(R.id.poolRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        modela = new ArrayList<>();
        Reference = FirebaseDatabase.getInstance().getReference("Posts");
        readPosts();
        return view;
    }

    private void readPosts() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Fanzone Working...");
        progressDialog.setMessage("Wait Am Loading Your Pool Members");
        progressDialog.show();
        Reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                modela.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Posts model = dataSnapshot.getValue(Posts.class);
                    assert model != null;
                    //if (!firebaseUser.getUid().equals(model.getUserId())) {
                    modela.add(model);
                    //}
                    //poolAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }
                poolAdapter = new PoolAdapter(getContext(), modela);
                recyclerView.setAdapter(poolAdapter);
                recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();

            }
        });


    }

}
