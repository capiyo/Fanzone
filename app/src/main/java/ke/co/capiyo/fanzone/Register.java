package ke.co.capiyo.fanzone;

//mport androidx.annotation.NonNull;
//mport androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.provider.Browser;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ke.co.capiyo.fanzone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.Random;

import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import ke.co.capiyo.fanzone.util.Utils;

public class Register extends AppCompatActivity {
    public TextView submit, termsLinkTV;
    public EditText usernameEd, phoneEd, passwordEd, confirmPassEd;
    public String username, phone, password, confirmPassword, gameId;
    public CheckBox acceptTermsCB;
    public FirebaseAuth firebaseAuth;
    public FirebaseUser firebaseUser;
    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;
    public ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        usernameEd = findViewById(R.id.regUsername);
        phoneEd = findViewById(R.id.phoneEd);
        passwordEd = findViewById(R.id.regPassword);
        confirmPassEd = findViewById(R.id.regConpassword);
        acceptTermsCB = findViewById(R.id.acceptTerms);
        termsLinkTV = findViewById(R.id.termsLink);

        Uri path = Uri.parse("");
        //find t & c's
        try {
            path = Uri.parse("android.resource://ke.co.capiyo.fanzone/terms/terms.html");
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        Uri finalPath = path;
        termsLinkTV.setOnClickListener(v -> Browser.sendString(Register.this, finalPath.toString()));


        databaseReference = FirebaseDatabase.getInstance().getReference("Users");


        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!acceptTermsCB.isChecked()) {
                    Toast.makeText(getApplicationContext(), "Please accept our Terms and Conditions!", Toast.LENGTH_LONG).show();
                } else {
                    validateCredentilas();
                }
            }
        });


    }

    public void validateCredentilas() {
        username = usernameEd.getText().toString();
        phone = phoneEd.getText().toString();
        password = passwordEd.getText().toString();
        confirmPassword = confirmPassEd.getText().toString();
        // Toast.makeText(getApplicationContext(),"at least  8 characters",Toast.LENGTH_LONG).show();
        // validate the credentials before submitting to database


        if (username.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(Register.this, "Please fill all fields", Toast.LENGTH_LONG).show();
        } else if (password.length() <= 8) {
            Toast.makeText(Register.this, "Password should be at least  8 characters", Toast.LENGTH_LONG).show();

        } else {
            if (!Utils.isValidEmail(username)) {
                username += "@fz.com";
                Toast.makeText(Register.this, "Your username is set to " + username, Toast.LENGTH_LONG).show();
            }


            // submit details to firebase
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Submitting...");
            progressDialog.setMessage("Please Wait Till Fanzone Finish Registration");
            progressDialog.show();
            firebaseAuth = FirebaseAuth.getInstance();

            firebaseAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        //progressDialog.dismiss();
                        firebaseUser = firebaseAuth.getCurrentUser();

                        HashMap<String, String> map = new HashMap<>();
                        map.put("userid", firebaseUser.getUid());
                        map.put("username", username);
                        map.put("phone", "+254" + phone.substring(1));
                        map.put("password", password);
                        map.put("balance", "0");
                        DatabaseReference Reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
                        Reference.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
//											for(int i=0;i<14;i++){
//												gamesDatabase();
//											}
                                    progressDialog.dismiss();
                                    startActivity(new Intent(Register.this, MainActivity.class));

                                } else {

                                    Toast.makeText(getApplicationContext(), "Upload fail check your connection", Toast.LENGTH_SHORT).show();
                                }


                            }
                        });

                    } else {
                        //progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Upload fail check your connection", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }


    }

    public void gamesDatabase() {
        generateRandom();
        final DatabaseReference betsRef = FirebaseDatabase.getInstance().getReference("Games");


        HashMap<String, String> hashMap = new HashMap<>();


        hashMap.put("gameId", gameId);
        hashMap.put("date", "date");
        hashMap.put("time", "time");
        hashMap.put("counter", "0");


        hashMap.put("homeTeam", "homeTeam");
        hashMap.put("awayTeam", "arsenal");
        hashMap.put("outcome", "outcome");


        hashMap.put("progress", "Upcoming");
        hashMap.put("score", "");
        //DatabaseReference postRef=FirebaseDatabase.getInstance().getReference("Posts");
        betsRef.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //myTeam ="";
                    //homeTeam="";
                    //awayTeam="";

                    Toast.makeText(Register.this, "Welcome to Fanzone", Toast.LENGTH_LONG).show();
                  finish();
                }

            }
        });
    }

    public String generateRandom() {
        Random rand = new Random();
        int rand_int1 = rand.nextInt(1000000);
        gameId = String.valueOf(rand_int1);
        return gameId;

    }

}
