package ke.co.capiyo.fanzone;

//import android.app.AlertDialog;
//import android.app.Fragment;
//import android.app.FragmentManager;
//import android.app.ProgressDialog;

import static ke.co.capiyo.fanzone.util.Constants.B2C_PARTY_A;
import static ke.co.capiyo.fanzone.util.Constants.BUSINESS_SHORT_CODE;
import static ke.co.capiyo.fanzone.util.Constants.CALLBACKURL;
import static ke.co.capiyo.fanzone.util.Constants.COMMAND_ID;
import static ke.co.capiyo.fanzone.util.Constants.PARTYB;
import static ke.co.capiyo.fanzone.util.Constants.PASSKEY;
import static ke.co.capiyo.fanzone.util.Constants.TRANSACTION_TYPE;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import ke.co.capiyo.fanzone.Model.AccessToken;
import ke.co.capiyo.fanzone.Model.BulkPaymentRequest;
import ke.co.capiyo.fanzone.Model.BulkPaymentResponse;
import ke.co.capiyo.fanzone.Model.STKPush;
import ke.co.capiyo.fanzone.Model.STKPushResponse;
import ke.co.capiyo.fanzone.Models.MessageModels;
import ke.co.capiyo.fanzone.Models.PoolModel;

import ke.co.capiyo.fanzone.Services.MpesaListener;
import ke.co.capiyo.fanzone.api.APIClient;
import ke.co.capiyo.fanzone.util.Constants;
import ke.co.capiyo.fanzone.util.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import  ke.co.capiyo.fanzone.databinding.MainBinding;


public class MainActivity extends AppCompatActivity {
    public static MpesaListener mpesaListener;
    public FirebaseAuth firebaseAuth;
    public FirebaseUser firebaseUser;
    public TextView toolbarUsername,searchTv,notifications,
            toolbarphone, toolbarBalance, toolbartopup, toolbarWithdraw, toolbarPostNews;
    public ImageView toolbarImage;
    public FloatingActionButton account,post,chatsfl;
    public Button sync;
    public EditText searchEd;
    public String gameId, username, phone, status, balance, _balance,search,lastname,numbers;
    public Uri selectedImageUri;
    private APIClient mApiClient;
    private ProgressDialog mProgressDialog;
   public   int counter=0;
    ArrayList<String> chats;
    private AppViewModela timeTableViewModel;

    @Override
    protected void onStart() {
        super.onStart();

        toolbartopup.setOnClickListener(v -> topupDialogue());
        toolbarWithdraw.setOnClickListener(v -> withdrawDialogue(_balance));
        account.setOnClickListener(v ->openSheet(new Account()));
        chatsfl.setOnClickListener(v ->openSheet(new Chatlist()));
        post.setOnClickListener(v ->openSheet(new Posting()));
        searchTv.setOnClickListener(s->searchUsers());
        notifications.setOnClickListener(n->openSheet(new Chatlist()));
       // getArray();
        // FirebaseAuth mauth= FirebaseAuth.getInstance();
        // FirebaseUser myUser=mauth.getCurrentUser();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();

        } else {





            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users")
                    .child(firebaseUser.getUid());
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    PoolModel myModel = snapshot.getValue(PoolModel.class);
                    assert myModel != null;
                    username = myModel.getUsername();
                    phone = myModel.getPhone();
                    balance = "Ksh. " + myModel.getBalance();
                    _balance = myModel.getBalance();


                   toolbarUsername.setText(myModel.getUsername());

                    toolbarBalance.setText(balance);
                   // checkNewMessage();


                // getArray();








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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
//        MpesaListener mpesaListener = this.mpesaListener;

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);

        MainBinding  activityMainBinding= DataBindingUtil.setContentView(this,R.layout.main);
        activityMainBinding.setViewmodel(new AppViewModela());
        activityMainBinding.executePendingBindings();







       // Glide.with(context)
              //  .load("http://via.placeholder.com/300.png")
             //   .into(ivImg);







        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        //viewpagerAdapter.addFragment(new Games(),"Chats");

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewpager);
        ViewpagerAdapter viewpagerAdapter = new ViewpagerAdapter(getSupportFragmentManager());

        tabLayout.setTabMode(TabLayout.MODE_FIXED); // set the tab items to fit screen


        viewpagerAdapter.addFragments(new Pool(), "Pool");
        viewpagerAdapter.addFragments(new Games(), "Games");
        viewpagerAdapter.addFragments(new Pending(), "Pledged");
//        viewpagerAdapter.addFragments(new Discover(), "Reels");
        //viewpagerAdapter.addFragments(new Archive(), "Archive");

        viewPager.setAdapter(viewpagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        getArray();

       // String notify=String.valueOf(counter);
       // notifications.setText(numbers);


        //getArray();
        activateToolbar();


    }
    @BindingAdapter({"toastMessage"})

    public  static  void  runme(View v, String userId){
        if(userId !=null){
            Toast.makeText(v.getContext(), "Hellllllooo", Toast.LENGTH_SHORT).show();
        }
    }

    public  void activateToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);


        account=findViewById(R.id.accountfl);
        post=findViewById(R.id.postfl);
        toolbarUsername = findViewById(R.id.username);
        toolbarphone = findViewById(R.id.phone);
        toolbarBalance = findViewById(R.id.accountbalance);
        toolbarImage = findViewById(R.id.toolbarImage);
        toolbartopup = findViewById(R.id.topupAcc);
        toolbarWithdraw = findViewById(R.id.withdraw);
        searchEd=findViewById(R.id.search);
        searchTv=findViewById(R.id.searchTv);
        notifications=findViewById(R.id.notification);
        chatsfl=findViewById(R.id.chats);


    }

    private void searchUsers() {

        search=searchEd.getText().toString();
         lastname =search+"@fz.com";
      //  Toast.makeText(this, lastname, Toast.LENGTH_SHORT).show();


            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");
            Query query = usersRef.orderByChild("username").equalTo(lastname.toLowerCase());
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                         timeTableViewModel = new ViewModelProvider(MainActivity.this).get(AppViewModela.class);
                        // timeTableViewModel.getData().observe(MainActivity.this, hisId -> {
                             timeTableViewModel.setNameData(lastname);


                            // update UI
                    //    });
                         //timeTableViewModel.setNameData(lastname);
                      //  Toast.makeText(MainActivity.this,lastname,Toast.LENGTH_LONG).show();
                        openSheet(new Search());

                        //Main Activity is supposed to set value


                    }
                    else {

                      Toast.makeText(MainActivity.this, "No such name in our  Systems", Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            /*query.get().addOnCompleteListener(task -> {
                DataSnapshot data = task.getResult();
                for (DataSnapshot i : data.getChildren()) {
                    if(i.exists()){
                        Toast.makeText(this, "That name exist in db", Toast.LENGTH_SHORT).show();
                        //openSheet(new Search());
                        //The name exist now what
                        //open a new  bottom sheet dialogu

                            //Intent intent = new Intent(, Messaging.class);
                           // intent.putExtra("friendsId", model.getUserid());
                          ///  mContext.startActivity(intent);



                    }
                    else {
                        Toast.makeText(this, "That name doesn't exist ", Toast.LENGTH_SHORT).show();
                    }

                }
//            System.out.println("Debug :: count => " + numCom.get());
            });

            */





    }

    public     void openSheet(BottomSheetDialogFragment  fragment){
        fragment.show(getSupportFragmentManager(), fragment.getTag());

    }

    public static Bitmap textAsBitmap(String text, float textSize, int textColor) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.0f); // round
        int height = (int) (baseline + paint.descent() + 0.0f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }




    //TabLayout tabLayout=findViewById(R.id.tabLayout);
    //ViewPager viewPager=findViewById(R.id.viewpager);
    //ViewpagerAdapter viewpagerAdapter= new ViewpagerAdapter(getSupportFragmentManager());
    //viewpagerAdapter.add()

    //viewpagerAdapter.addFragment(new Pending(),"Vishop");

    //viewPager.setAdapter(viewpagerAdapter);
    //tabLayout.setupWithViewPager(viewPager);

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            // firebaseAuth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }/* else if (itemId == R.id.settings) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }*/ else if (itemId == R.id.profile) {
            profileDialogue();
        } else if (itemId == R.id.topup) {
            topupDialogue();
        } else if (itemId == R.id.search) {
            searchDialogue();
        }/* else if (itemId == R.id.video) {
            pickVideo();
        }*/
        return super.onOptionsItemSelected(item);
    }

    private void searchDialogue() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final TextView searchText = new TextView(this);
        searchText.setText("Team");

        final EditText etSearch = new EditText(this);

        layout.addView(searchText);
        layout.addView(etSearch);


        final AlertDialog.Builder alert = new AlertDialog.Builder(
                this);
        alert.setTitle("Search for games.");
        alert.setCancelable(true);
        alert.setView(layout)
                .setPositiveButton("Search", (dialog, which) -> {
                    final String searchTxt = etSearch.getText().toString(); //TODO: use phone in final build
                    searchGames(searchTxt);
                })
                .setNegativeButton("Cancel",
                        (dialog, id) -> dialog.cancel());
        final AlertDialog dialog = alert.create();
        dialog.show();
    }

    private void searchGames(String searchTxt) {
        DatabaseReference gamesRef = FirebaseDatabase.getInstance().getReference("Games");

        // search for the game
        gamesRef.orderByChild("homeTeam").equalTo(searchTxt).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot data = task.getResult();
                Intent intent = new Intent(MainActivity.this, Pool.class);
                intent.putExtra("games", (CharSequence) data);
                MainActivity.this.startActivity(intent);
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void topupDialogue() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final TextView tvPhoneNumber = new TextView(this);
        tvPhoneNumber.setText("Phone number");
        final TextView tvAmount = new TextView(this);
        tvAmount.setText("Amount");

        final EditText etPhone = new EditText(this);
        final EditText etAmount = new EditText(this);

        layout.addView(tvPhoneNumber);
        layout.addView(etPhone);
        layout.addView(tvAmount);
        layout.addView(etAmount);

        mApiClient = new APIClient();
        mProgressDialog = new ProgressDialog(this);
        mApiClient.setIsDebug(true); //Set True to enable logging, false to disable.

        getAccessToken();

        final AlertDialog.Builder alert = new AlertDialog.Builder(
                this);
        alert.setTitle("Add funds to your account.");
        alert.setCancelable(true);
        alert.setView(layout)
                .setPositiveButton("Continue", (dialog, which) -> {
                    final String phoneNumber = etPhone.getText().toString(); //TODO: use phone in final build
                    final String amount = etAmount.getText().toString();
                    performSTKPush(phoneNumber, amount);
                })
                .setNegativeButton("Cancel",
                        (dialog, id) -> dialog.cancel());
        final AlertDialog dialog = alert.create();
        dialog.show();
    }

    @SuppressLint("SetTextI18n")
    private void withdrawDialogue(String curBalance) {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final TextView tvAmount = new TextView(this);
        tvAmount.setText("Amount");

        final EditText etAmount = new EditText(this);

        layout.addView(tvAmount);
        layout.addView(etAmount);

        mApiClient = new APIClient();
        mProgressDialog = new ProgressDialog(this);
        mApiClient.setIsDebug(true); //Set True to enable logging, false to disable.

        getTestAccessToken();

        final AlertDialog.Builder alert = new AlertDialog.Builder(
                this);
        alert.setTitle("Withdraw funds from your account.");
        alert.setCancelable(true);
        alert.setView(layout)
                .setPositiveButton("Continue", (dialog, which) -> {
                    final String amount = etAmount.getText().toString();
                    // TODO: Confirm account balance before initiating withdrawal.
                    //Toast.makeText(MainActivity.this, "Your account balance is: "+ _balance, Toast.LENGTH_LONG).show();
                    if (Integer.parseInt(amount) < 49) {
                        Toast.makeText(MainActivity.this, "You can only withdraw Ksh.50/= and more!!", Toast.LENGTH_LONG).show();
                        dialog.cancel();
                    } else if (Integer.parseInt(curBalance) > Integer.parseInt(amount))
                        performB2CPayment(Utils.sanitizePhoneNumber(phone), amount);
                    else {
                        Toast.makeText(MainActivity.this, "Your account balance is less than the amount you're withdrawing!!", Toast.LENGTH_LONG).show();
                        dialog.cancel();
                    }

                })
                .setNegativeButton("Cancel",
                        (dialog, id) -> dialog.cancel());
        final AlertDialog dialog = alert.create();
        dialog.show();
    }

    public String generateRandom() {
        Random rand = new Random();
        int rand_int1 = rand.nextInt(1000000);
        gameId = String.valueOf(rand_int1);
        return gameId;

    }

    @SuppressLint("SetTextI18n")
    public void profileDialogue() {
        //Context context = mapView.getContext();
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final TextView clubTv = new TextView(this);
        final TextView nationalTv = new TextView(this);
        final TextView noteTv = new TextView(this);
        clubTv.setText("Favourite Club");
        nationalTv.setText("Favourite National Team");
        noteTv.setText("Note for Pool mates");

// Add a TextView here for the "Title" label, as noted in the comments
        final EditText club = new EditText(this);
        //titleBox.setHint("Title");
        //layout.addView(titleBox); // Notice this is an add method

// Add another TextView here for the "Description" label
        final EditText national = new EditText(this);
        //descriptionBox.setHint("Description");
        //layout.addView(descriptionBox); // Another add method
        final EditText note = new EditText(this);
        //hisAmount=amountEd.getText().toString();
        layout.addView(clubTv);
        layout.addView(club);
        layout.addView(nationalTv);
        layout.addView(national);


        layout.addView(noteTv);

        layout.addView(note);


        final AlertDialog.Builder alert = new AlertDialog.Builder(
                this);
        alert.setTitle("Update your profile...");

        alert.setCancelable(true);
        alert.setView(layout)
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String sClub = club.getText().toString();
                        final String sNational = national.getText().toString();
                        final String sNote = note.getText().toString();
                        profileDatabase(sClub, sNational, sNote);

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

    public void profileDatabase(final String club, final String national, final String note) {

        DatabaseReference profileRef = FirebaseDatabase.getInstance().getReference("Profiles");


        HashMap<String, String> hashMap = new HashMap<>();


        hashMap.put("userId", firebaseUser.getUid());
        hashMap.put("username", username);
        hashMap.put("status", "");
        hashMap.put("club", club);
        hashMap.put("phone", phone);

        hashMap.put("national", national);
        hashMap.put("note", note);


        //DatabaseReference postRef=FirebaseDatabase.getInstance().getReference("Posts");
        profileRef.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(MainActivity.this, "Your profile  was successfully  submitted", Toast.LENGTH_LONG).show();

                }

            }
        });
    }

  /*  public void pickVideo() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, "selectImage"), 1);


    }


    // alertDialogue for amount

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;

        selectedImageUri = data.getData();
        // videoView.setVisibility(View.VISIBLE);
       // submitToStorage();


    }

    private void submitToStorage() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.setMessage("capiyo" + selectedImageUri.toString());
        progressDialog.show();
        //Uri uploadUri = Uri.fromFile(new File(selectedImageUri.toString()));


        final StorageReference vidoeRef = FirebaseStorage.getInstance().getReference("Videos").child(firebaseUser.getUid());
        vidoeRef.putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                vidoeRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(final Uri uri) {
                        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
                        usersRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                PoolModel myUsers = snapshot.getValue(PoolModel.class);
                                HashMap<String, Object> hashMap = new HashMap<>();


                                hashMap.put("userid", firebaseUser.getUid());
                                hashMap.put("username", myUsers.getUsername());
                                hashMap.put("phone", myUsers.getPhone());
                                hashMap.put("videoUrl", uri.toString());
                                // hashMap.put("videoMessage",videoMessage);
                                hashMap.put("counter", "0");
                                hashMap.put("lastSeen", "lastSeen");
                                hashMap.put("likeCount", "likeCount");

                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Videos");
                                reference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            progressDialog.dismiss();
                                            Toast.makeText(MainActivity.this, "Upload succcess", Toast.LENGTH_LONG).show();

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


   */


    public void getAccessToken() {
        mApiClient.setGetAccessToken(true);
        mApiClient.mpesaSTKService().getAccessToken().enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(@NonNull Call<AccessToken> call, @NonNull Response<AccessToken> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    mApiClient.setAuthToken(response.body().accessToken);
                }
            }

            @Override
            public void onFailure(@NonNull Call<AccessToken> call, @NonNull Throwable t) {

            }
        });
    }

    public void getTestAccessToken() {
        mApiClient.setGetAccessToken(true);
        mApiClient.mpesaB2CService().getTestAccessToken().enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(@NonNull Call<AccessToken> call, @NonNull Response<AccessToken> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    mApiClient.setAuthToken(response.body().accessToken);
                }
            }

            @Override
            public void onFailure(@NonNull Call<AccessToken> call, @NonNull Throwable t) {

            }
        });
    }

    public void performB2CPayment(String phone_number, String amount) {
        mProgressDialog.setMessage("Processing your request");
        mProgressDialog.setTitle("Please Wait...");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();
        BulkPaymentRequest b2cRequest = new BulkPaymentRequest(
                generateRandom(),
                Constants.INITIATOR_NAME,
//                Utils.getEncryptedPasswdFromFile(Constants.INITIATOR_PASSWORD),
                Constants.SECURITY_CREDENTIAL,
                COMMAND_ID,
                amount,
                B2C_PARTY_A,
                phone_number,
                "Withdrawal of funds from your Fanzone account",
                Constants.QUEUE_TIMEOUT_URL,
                Constants.RESULT_URL, //Account reference
                "Wager winnings"  // Occasion
        );

        mApiClient.setGetAccessToken(false);
//        System.out.println("DEBUG Withdrawal => "+stkPush.getSecurityCredential());
//        Toast.makeText(MainActivity.this, b2cRequest.toString(), Toast.LENGTH_LONG).show();
        //Sending the data to the Mpesa API, remember to remove the logging when in production.
        mApiClient.mpesaB2CService().makeB2CPayment(b2cRequest).enqueue(new Callback<BulkPaymentResponse>() {
            @Override
            public void onResponse(@NonNull Call<BulkPaymentResponse> call, @NonNull Response<BulkPaymentResponse> response) {
                mProgressDialog.dismiss();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("BulkPaymentResponse");
                reference.push().setValue(response.body());
                try {
                    if (response.isSuccessful()) {
                        Timber.d("post submitted to API. %s", response);
//                        reference.push().setValue(response);
                        assert response.body() != null;
                        Toast.makeText(MainActivity.this, response.body().getResponseDescription(), Toast.LENGTH_LONG).show();
                    } /*else if (response.code() == 400) {
                        Timber.d("Error occurred!! . %s", response.body());
                        //                        reference.push().setValue(response);
                        assert response.body() != null;
                        Toast.makeText(MainActivity.this, response.body().toString(), Toast.LENGTH_LONG).show();

                    }*/ else {
//                        assert response.errorBody() != null;
                        Timber.e("Response %s", response.body());
                        Toast.makeText(MainActivity.this, "Could not successfully withdraw. Please try again later!", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Please try again later!", Toast.LENGTH_LONG).show();
                    Timber.e("Error occurred!! %s", response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<BulkPaymentResponse> call, @NonNull Throwable t) {
                mProgressDialog.dismiss();
                Timber.e(t);
            }
        });
    }

    public void performSTKPush(String phone_number, String amount) {
        mProgressDialog.setMessage("Processing your request");
        mProgressDialog.setTitle("Please Wait...");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();
        String timestamp = Utils.getTimestamp();
        STKPush stkPush = new STKPush(
                BUSINESS_SHORT_CODE,
                Utils.getPassword(BUSINESS_SHORT_CODE, PASSKEY, timestamp),
                timestamp,
                TRANSACTION_TYPE,
                String.valueOf(amount),
                Utils.sanitizePhoneNumber(phone_number),
                PARTYB,
                Utils.sanitizePhoneNumber(phone_number),
                CALLBACKURL,
                Utils.sanitizePhoneNumber(phone_number), //Account reference
                "Account topup"  //Transaction description
        );

        mApiClient.setGetAccessToken(false);

        //Sending the data to the Mpesa API, remember to remove the logging when in production.
        mApiClient.mpesaSTKService().sendPush(stkPush).enqueue(new Callback<STKPushResponse>() {
            @Override
            public void onResponse(@NonNull Call<STKPushResponse> call, @NonNull Response<STKPushResponse> response) {
                mProgressDialog.dismiss();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("STKPushResponse");
                try {
                    if (response.isSuccessful()) {
                        Timber.d("post submitted to API. %s", response.body());
                        reference.push().setValue(response.body());
                        assert response.body() != null;
                        Toast.makeText(MainActivity.this, response.body().getCustomerMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        assert response.errorBody() != null;
                        Timber.e("Response %s", response.errorBody().string());
                        Toast.makeText(MainActivity.this, "Could not successfully top up. Please try again later!", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<STKPushResponse> call, @NonNull Throwable t) {
                mProgressDialog.dismiss();
                Timber.e(t);
            }
        });
    }

    public static class ViewpagerAdapter extends FragmentPagerAdapter {
        private final ArrayList<Fragment> fragments;
        private final ArrayList<String> titles;

        ViewpagerAdapter(FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();

        }

        // @NonNull
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragments(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);

        }

        //@Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }


    }

    public    void checkNewMessage() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Messages");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });










        /*reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {


                        assert models != null;

                      //  Query query	= snapshot1.orderByChild("userid").equalTo(firebaseUser.getUid())
                        //	.orderByChild("balance");
                        if(models.getReceiverid().equals(firebaseUser.getUid())){
                             //   models.getSeen().equals("false")
                            String  seen=models.getMessages();
                            Toast.makeText(MainActivity.this, seen, Toast.LENGTH_SHORT).show();

                                counter+=1;


                        }

                    }
                        }

                //0740852071



                if(counter>0){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        notifications.setTextColor(R.color.bes);


                }




                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });

         */





    }



        ///Get array Of peoples id who has messagede me

        public String getArray() {
        ArrayList<String> list= new ArrayList<String>();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Messages");


            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                            MessageModels models = snapshot1.getValue(MessageModels.class);
                            assert models != null;
                            DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Messages")
                                    .child(models.getReceiverid());
                            Query query = reference1.orderByChild("receiverid").equalTo(firebaseUser.getUid());


                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {

                                        for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                                            MessageModels model = dataSnapshot1.getValue(MessageModels.class);
                                            assert model != null;
                                            if (model.getSeen().equals("false") && model.getReceiverid().equals(firebaseUser.getUid())) {

                                               // counter +=1;

                                                notifications.setText("Bet Invitations");




















                                             //   list.add(models.getMessages());
                                             //   Toast.makeText(MainActivity.this,String.valueOf(list.size()), Toast.LENGTH_SHORT).show();
                                              // notifications.setText(String.valueOf(list.size()));

                                            }
                                          //  numbers=String.valueOf(counter);


                                            //notifications.setText(numbers);
                                            //Toast.makeText(MainActivity.this,counter, Toast.LENGTH_SHORT).show();
                                        /*for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                                            MessageModels model = dataSnapshot2.getValue(MessageModels.class);
                                            /*




                                        }
                                    */

                                        }
                                       // Toast.makeText(MainActivity.this,String.valueOf(counter), Toast.LENGTH_SHORT).show();



                                    }
                                   /* for (int i = 0; i <list.size() ; i++) {
                                        counter=counter+1;

                                    }

                                    */
                                  // notifications.setText("Notifications: "+String.valueOf(counter/4));
                                   // Toast.makeText(MainActivity.this,String.valueOf(counter), Toast.LENGTH_SHORT).show();





                                }




                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                                 // Toast.makeText(MainActivity.this,String.valueOf(counter), Toast.LENGTH_SHORT).show();
                            });
                          //  Toast.makeText(MainActivity.this,String.valueOf(counter), Toast.LENGTH_SHORT).show();



                        }
                      //  Toast.makeText(MainActivity.this,String.valueOf(counter), Toast.LENGTH_SHORT).show();


                    }
                    //Toast.makeText(MainActivity.this,String.valueOf(counter), Toast.LENGTH_SHORT).show();


                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
                  //Toast.makeText(MainActivity.this,String.valueOf(counter), Toast.LENGTH_SHORT).show();


            });
            //notifications.setText("Notification: " + String.valueOf(counter / 2));



          return  String.valueOf(counter);
        }


         //  notifications.setText("Notifications: "+String.valueOf(counter));
    }



