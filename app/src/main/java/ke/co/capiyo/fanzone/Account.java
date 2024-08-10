package ke.co.capiyo.fanzone;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import ke.co.capiyo.fanzone.Models.PoolModel;
import ke.co.capiyo.fanzone.Models.Profile;

public class Account extends BottomSheetDialogFragment {
   public TextView profiler;
    public TextView profilerNmae,profilerTell,balancel,cluba,openUpdate,team;
    public FirebaseUser firebaseUser;
    public  String username,phone;


        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable
        ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.activity_account,
                    container, false);
            profilerTell=v.findViewById(R.id.accountTell);
            profilerNmae=v.findViewById(R.id.accountName);
            openUpdate=v.findViewById(R.id.profilerUpdate);

            balancel=v.findViewById(R.id.balanceacc);
            cluba=v.findViewById(R.id.favclubl);
            team=v.findViewById(R.id.favTeam);





            firebaseUser= FirebaseAuth.getInstance().getCurrentUser();


            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users")
                    .child(firebaseUser.getUid());
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    PoolModel myModel = snapshot.getValue(PoolModel.class);
                    assert myModel != null;
                    username=myModel.getUsername();
                    phone=myModel.getPhone();

                    profilerNmae.setText(myModel.getUsername());
                    profilerTell.setText(myModel.getPhone());

                    // checkNewMessage();



                    openUpdate.setOnClickListener(vv -> profileDialogue());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            fillProfile();




            return v;



    }

    public  void  fillProfile(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Profiles")
                .child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Profile myModel = snapshot.getValue(Profile.class);
                assert myModel != null;
                cluba.setText(myModel.getClub());
                team.setText(myModel.getNational());










//                    toolbarImage.setBackgroundResource(R.drawable.finder);


                //navUsername.setText(myModel.getUsername());
                //navEname.setText(myModel.getEname());


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });

        // Toast.makeText(MainActivity.this, "Welcome to Fanzone", Toast.LENGTH_SHORT).show();
    }







    private void updateProfle() {




    }



    public void profileDialogue() {
        //Context context = mapView.getContext();
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        final TextView clubTv = new TextView(getContext());
        final TextView nationalTv = new TextView(getContext());
        final TextView noteTv = new TextView(getContext());
        clubTv.setText("Favourite Club");
        nationalTv.setText("Favourite National Team");
        noteTv.setText("Note for Pool mates");

// Add a TextView here for the "Title" label, as noted in the comments
        final EditText club = new EditText(getContext());
        //titleBox.setHint("Title");
        //layout.addView(titleBox); // Notice this is an add method

// Add another TextView here for the "Description" label
        final EditText national = new EditText(getContext());
        //descriptionBox.setHint("Description");
        //layout.addView(descriptionBox); // Another add method
        final EditText note = new EditText(getContext());
        //hisAmount=amountEd.getText().toString();
        layout.addView(clubTv);
        layout.addView(club);
        layout.addView(nationalTv);
        layout.addView(national);


        layout.addView(noteTv);

        layout.addView(note);

        final AlertDialog.Builder alert = new AlertDialog.Builder(
                getContext());
        alert.setTitle("Update your profile...");

        alert.setCancelable(true);
        alert.setView(layout)
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String sClub = club.getText().toString();
                        final String sNational = national.getText().toString();
                        final String sNote = note.getText().toString();
                        profileDatabase(sClub,sNational,sNote);

                        //betDatabase(homeTeam,awayTeam,gameId,myTeam);


                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        final AlertDialog dialog = alert.create();
        dialog.show();

    }

    public void profileDatabase(String  club,String national,String note) {

        DatabaseReference profileRef = FirebaseDatabase.getInstance().getReference("Profiles").child(firebaseUser.getUid());


        HashMap<String, String> hashMap = new HashMap<>();


        hashMap.put("userId", firebaseUser.getUid());
        hashMap.put("username", username);
        hashMap.put("status", "");
        hashMap.put("club", club);
        hashMap.put("phone", phone);

        hashMap.put("national", national);
        hashMap.put("note", note);


        //DatabaseReference postRef=FirebaseDatabase.getInstance().getReference("Posts");
        profileRef.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(getContext(), "Your profile  was successfully  submitted", Toast.LENGTH_LONG).show();

                }

            }
        });
    }



}