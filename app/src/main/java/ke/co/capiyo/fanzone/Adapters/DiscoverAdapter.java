package ke.co.capiyo.fanzone.Adapters;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ke.co.capiyo.fanzone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import ke.co.capiyo.fanzone.Messaging;
import ke.co.capiyo.fanzone.Models.DiscoverModels;

public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.MyViewHolder> {


    private final List<DiscoverModels>  discoverModels;
    private DatabaseReference mUserReference;
    public String friendsId;
    public  static int counter=0;

    private final Context mContext;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    public DiscoverAdapter(Context context, List<DiscoverModels> models) {
        this.mContext = context;
        this.discoverModels = models;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.discover_items, parent, false);
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        return new MyViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
       final  DiscoverModels model =discoverModels.get(position);
        holder.usernameTv.setText(model.getUsername());
       // holder.enameTv.setText(model.getEname());
        

		if (!model.getVideoUrl().equals("videoUrl")){
			holder.videoMessage.setText(model.getVideoMessage());
			holder.videoView.setVideoURI(Uri.parse(model.getVideoUrl()));
			MediaController mediaController= new MediaController(mContext);
			mediaController.setMediaPlayer(holder.videoView);
			holder.videoView.requestFocus();
			holder.videoView.seekTo(2);
			holder.videoView.start();


		}
		holder.videoView.seekTo(2);
		holder.videoView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					holder.videoView.start();

				}
			});





        holder.likeCount.setText(model.getLikeCount());



        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        if (firebaseUser.getUid().equals(model.getUserid())){
            holder.openMessaging.setVisibility(View.INVISIBLE);
        }
        else {
            holder.openMessaging.setVisibility(View.VISIBLE);
        }



        holder.openMessaging.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mContext, Messaging.class);
					intent.putExtra("friendsId", model.getUserid());
					mContext.startActivity(intent);


				}

			});


    }






















    @Override
    public int getItemCount() {
        return  discoverModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView usernameTv, phoneTv;
        public TextView openMessaging, videoMessage;
        
        public VideoView videoView;
        public  TextView likeCount;
        



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTv=itemView.findViewById(R.id.videoItemsUsername);
            phoneTv=itemView.findViewById(R.id.videoItemsphone);
            videoView=itemView.findViewById(R.id.videoview);
            
            videoMessage=itemView.findViewById(R.id.videoMessage);
            openMessaging=itemView.findViewById(R.id.openMessaging);
            likeCount=itemView.findViewById(R.id.counter);
            




        }
    }


}
