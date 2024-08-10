package ke.co.capiyo.fanzone;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.support.v7.widget.RecyclerView;
//import android.support.annotation.NonNull;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.LinearLayoutManager;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ke.co.capiyo.fanzone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ke.co.capiyo.fanzone.Adapters.DiscoverAdapter;
import ke.co.capiyo.fanzone.Models.DiscoverModels;
import java.util.ArrayList;
import java.util.List;


public class Discover extends Fragment {
    public FirebaseAuth mAuth;
    public FirebaseDatabase firebaseDatabase;
    public FirebaseUser firebaseUser;
    public RecyclerView recyclerView;
    public DiscoverAdapter discoverAdapter;
    public List<DiscoverModels> model;


    public Discover() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
		View view=  inflater.inflate(R.layout.discover, container, false);
        mAuth=FirebaseAuth.getInstance();
        firebaseUser=mAuth.getCurrentUser();
        firebaseDatabase=FirebaseDatabase.getInstance();

        recyclerView = view.findViewById(R.id.disvoverRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        model= new ArrayList<>();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
				@Override
				public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
					super.onScrollStateChanged(recyclerView, newState);
					if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE){
						readPosts();
					}
				}
			});


        readPosts();
        return  view;

    }
    private void readPosts() {
      final   ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading...");
        progressDialog.setMessage("Wait till i complete my work please");
		// progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Discover");
        databaseReference.addValueEventListener(new ValueEventListener() {
				@Override
				public void onDataChange(@NonNull DataSnapshot snapshot) {
					model.clear();
					for(DataSnapshot dataSnapshot:snapshot.getChildren()){
						DiscoverModels myModel=dataSnapshot.getValue(DiscoverModels.class);
						assert myModel != null;
						if (!myModel.getVideoUrl().equals("videoUrl")){
							model.add(myModel);
							progressDialog.dismiss();

						}


					}
					discoverAdapter=new DiscoverAdapter(getContext(),model);
					recyclerView.setAdapter(discoverAdapter);
					//recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.HORIZONTAL));


				}

				@Override
				public void onCancelled(@NonNull DatabaseError error) {
					progressDialog.dismiss();

				}
			});

    }


}
