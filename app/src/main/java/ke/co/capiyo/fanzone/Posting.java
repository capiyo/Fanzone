package ke.co.capiyo.fanzone;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Random;

import ke.co.capiyo.fanzone.Models.PoolModel;
import ke.co.capiyo.fanzone.Models.Profile;

public class Posting   extends BottomSheetDialogFragment {
    public TextView addImage,submit;
    public EditText  messageEd;
    public  Uri selectedImageUri;
    public  FirebaseDatabase firebaseDatabase;
    public FirebaseUser firebaseUser;
    public  String message,childId,favClub,favTeam;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
    ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_posting,
                container, false);
        addImage=v.findViewById(R.id.addImage);
        submit=v.findViewById(R.id.submit);
        messageEd=v.findViewById(R.id.message);
        addImage.setOnClickListener(k ->openSheet());
        submit.setOnClickListener(l ->sendToStorage());
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
       // loadProfile();



        return v;


    }

    private void openSheet() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, "SelectImage"), 1);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;

        selectedImageUri = data.getData();
        // videoView.setVisibility(View.VISIBLE);
        submitToStorage();


    }

    public String generateRandom() {
        Random rand = new Random();
        int rand_int1 = rand.nextInt(1000000);
        childId= String.valueOf(rand_int1);
        return childId;

    }
 public  void loadProfile(){

     DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Profiles")
             .child(firebaseUser.getUid());
     reference.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
             Profile myModel = snapshot.getValue(Profile.class);
             favClub=myModel.getClub();
             favTeam=myModel.getNational();

         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
     });

 }

 public void sendToStorage(){
     message=messageEd.getText().toString();

     DatabaseReference usersRef = FirebaseDatabase.getInstance().
             getReference("Users").child(firebaseUser.getUid());
     usersRef.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
             PoolModel myUsers = snapshot.getValue(PoolModel.class);
             HashMap<String, Object> hashMap = new HashMap<>();

             hashMap.put("userid", firebaseUser.getUid());
             hashMap.put("username", myUsers.getUsername());
             hashMap.put("phone", myUsers.getPhone());
             hashMap.put("imageUrl", "");
             hashMap.put("message",message);
             hashMap.put("counter", "0");
             hashMap.put("club","Manchester United");
             hashMap.put("team","France");
             hashMap.put("lastSeen", "Online");
             hashMap.put("likeCount", "likeCount");

             DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
             reference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                 @Override
                 public void onComplete(@NonNull Task<Void> task) {
                     if (task.isSuccessful()) {

                         Toast.makeText(getContext(), "Message  was  sent successfully", Toast.LENGTH_LONG).show();

                     }

                 }
             });

         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
     });


 }











    private void submitToStorage() {
       // loadProfile();
        message=messageEd.getText().toString();



        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading...");
        progressDialog.setMessage("Uploading plz wait");
        progressDialog.show();
        //Uri uploadUri = Uri.fromFile(new File(selectedImageUri.toString()));


        final StorageReference imageRef = FirebaseStorage.getInstance().getReference("Images").
                child(generateRandom());
        imageRef.putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(final Uri uri) {
                        DatabaseReference usersRef = FirebaseDatabase.getInstance().
                                getReference("Users").child(firebaseUser.getUid());
                        usersRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                PoolModel myUsers = snapshot.getValue(PoolModel.class);
                                HashMap<String, Object> hashMap = new HashMap<>();

                                hashMap.put("userid", firebaseUser.getUid());
                                hashMap.put("username", myUsers.getUsername());
                                hashMap.put("phone", myUsers.getPhone());
                                hashMap.put("imageUrl", uri.toString());
                                hashMap.put("message",message);
                                hashMap.put("counter", "0");
                                hashMap.put("club","Manchester United");
                                hashMap.put("team","France");
                                hashMap.put("lastSeen", "Online");
                                hashMap.put("likeCount", "likeCount");

                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
                                reference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            progressDialog.dismiss();
                                            Toast.makeText(getContext(), "Upload  was successful", Toast.LENGTH_LONG).show();
                                            getActivity().getSupportFragmentManager().popBackStack();
                                        }

                                    }
                                });

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }


                });


            }
        });




    }








}
