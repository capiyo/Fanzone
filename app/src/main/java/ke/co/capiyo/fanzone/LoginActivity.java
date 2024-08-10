package ke.co.capiyo.fanzone;




import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;

import ke.co.capiyo.fanzone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import ke.co.capiyo.fanzone.util.Utils;

public class LoginActivity extends AppCompatActivity {
    public  TextView newUser,loginTxt;
    public  String username,password;
    public EditText usernameEd,passwordEd;
    public FirebaseAuth firebaseAuth;
    public FirebaseUser firebaseUser;
    public FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        usernameEd=findViewById(R.id.logUsername);
        passwordEd=findViewById(R.id.logPassword);
        newUser=findViewById(R.id.newUser);
        newUser.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					firebaseAuth=FirebaseAuth.getInstance();
					firebaseAuth.signOut();
					startActivity(new Intent(LoginActivity.this,Register.class));
				}
			});
        loginTxt=findViewById(R.id.login);
        loginTxt.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					loginUser();
				}
			});
    }

    private void loginUser() {
        username=usernameEd.getText().toString();
        password=passwordEd.getText().toString();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
        }
        else {
            if (!Utils.isValidEmail(username)){
                username+="@fz.com";
                Toast.makeText(LoginActivity.this,"username is set to "+username,Toast.LENGTH_LONG).show();
            }
            firebaseAuth=FirebaseAuth.getInstance();
            firebaseAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
				public void onComplete(@NonNull Task<AuthResult> task) {
					if (task.isSuccessful()){
						finish();
						startActivity(new Intent(LoginActivity.this,MainActivity.class));
					}
					else {
						Toast.makeText(getApplicationContext(), "Login failed check your connection", Toast.LENGTH_SHORT).show();
					}
				}
				});

		}
    }



}
