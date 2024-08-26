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
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ke.co.capiyo.fanzone.Adapters.PendingAdapter;
import ke.co.capiyo.fanzone.Models.PendingModel;

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


public class Archive extends Fragment {
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
    public PendingAdapter archiveAdapter;
    public List<PendingModel> modela;
    public ProgressDialog progressdialogue;
    public CardView emptyCard;
    public  boolean  checker= false;


    public Archive() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.archive, container, false);


        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        emptyCard=view.findViewById(R.id.emptyCard);

        recyclerView = view.findViewById(R.id.archiveRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        modela = new ArrayList<>();
        Reference = FirebaseDatabase.getInstance().getReference("Bets");


        readPendings();


        return view;
    }

    private void readPendings() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Fanzone Working...");
        progressDialog.setMessage("Patients...");
        progressDialog.show();

        Reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                modela.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    PendingModel model = dataSnapshot.getValue(PendingModel.class);
                    assert model != null;

                    if (model.getStarterId().equals(firebaseUser.getUid()) ||  model.getFinisherId().equals(firebaseUser.getUid())) {
                         modela.add(model);
                         checker=true;
                             //emptyCard.setVisibility(View.VISIBLE);
                             //Display Empty Card view
                      ///  emptyCard.setVisibility(View.GONE);


                    }


                    //poolAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();

                }
                archiveAdapter = new PendingAdapter(getContext(), modela);
                recyclerView.setAdapter(archiveAdapter);
                recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();

            }
        });


    }

}
