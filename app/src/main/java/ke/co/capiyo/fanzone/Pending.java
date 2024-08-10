package ke.co.capiyo.fanzone;

import android.net.Uri;
//import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


//import android.support.annotation.NonNull;
/*mport androidx.annotation.NonNull;
 import androidx.fragment.app.Fragment;
 androidx.recyclerview.widget.DividerItemDecoration;
 import androidx.recyclerview.widget.LinearLayoutManager;
 import androidx.recyclerview.widget.RecyclerView;
 */

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.VideoView;

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
//import com.google.firebase.storage.FirebaseStorage;
//mport com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import android.app.ProgressDialog;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
//import android.support.v7.widget.DividerItemDecoration;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//
//import android.support.v4.app.Fragment;


public class Pending extends Fragment {
    public TextView postVideos;
    public VideoView postImageView;
    public final int PICK_VIDEO = 1;
    public Uri selectedVideoUri;
    public FirebaseAuth mAuth;
    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference,Reference;
    public ProgressDialog progressDialog;
    public FirebaseUser firebaseUser;
    //public FirebaseStorage firebaseStorage;
    //public StorageReference storageReference;
    public String videoUrl, postingMessage;
    public RecyclerView recyclerView;
    public PendingAdapter pendingAdapter;
    public List<PendingModel> modela;
	public ProgressDialog progressdialogue;



    public Pending() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.pending, container, false);


        mAuth=FirebaseAuth.getInstance();
        firebaseUser=mAuth.getCurrentUser();
        firebaseDatabase=FirebaseDatabase.getInstance();

        recyclerView = view.findViewById(R.id.pendingRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        modela= new ArrayList<>();
        Reference= FirebaseDatabase.getInstance().getReference("Bets");




		readPendings();


        return view;
    }

    private void readPendings() {
		progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Fanzone Working...");
        progressDialog.setMessage("Wait Am Loading  Unfinished Bests");
        progressDialog.show();

        Reference.addValueEventListener(new ValueEventListener() {
				@Override
				public void onDataChange(@NonNull DataSnapshot snapshot) {
					modela.clear();
					for(DataSnapshot dataSnapshot:snapshot.getChildren()){
						PendingModel model=dataSnapshot.getValue(PendingModel.class);
						assert model != null;

						if(model.getFinisherId().equals("")){
						modela.add(model);


						}

						//poolAdapter.notifyDataSetChanged();
						progressDialog.dismiss();

					}
					pendingAdapter=new PendingAdapter(getContext(),modela);
					recyclerView.setAdapter(pendingAdapter);
					recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(),DividerItemDecoration.HORIZONTAL));


				}

				@Override
				public void onCancelled(@NonNull  DatabaseError error) {
					progressDialog.dismiss();

				}
			});


    }

}
