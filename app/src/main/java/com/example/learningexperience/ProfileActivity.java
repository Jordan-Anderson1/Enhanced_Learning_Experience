package com.example.learningexperience;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    ImageButton backButton, addProfileImageButton, goToHistoryButton;
    Button shareButton;
    CardView upgradeCardView;

    TextView usernameTextView, emailAddressTextView, totalQuestionsAnsweredTextView, totalIncorrectAnswersTextView, totalCorrectAnswersTextView;

    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    FirebaseUser fUser;
    String userId;
    ImageView profileImage;
    StorageReference storageReference;
    Uri imageUri;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        backButton = findViewById(R.id.backButton);
        usernameTextView = findViewById(R.id.username);
        emailAddressTextView = findViewById(R.id.emailAddress);
        totalQuestionsAnsweredTextView = findViewById(R.id.totalQuestionsAnsweredTextView);
        totalIncorrectAnswersTextView = findViewById(R.id.totalIncorrectAnswersTextView);
        totalCorrectAnswersTextView = findViewById(R.id.totalCorrectAnswersTextView);
        profileImage = findViewById(R.id.profileImage);
        addProfileImageButton = findViewById(R.id.addProfileImage);
        goToHistoryButton = findViewById(R.id.goToHistoryButton);
        shareButton = findViewById(R.id.shareButton);
        upgradeCardView = findViewById(R.id.upgradeCardView);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        userId = fAuth.getUid();

        //get user Email address and set emailAddressTextView
        fUser = fAuth.getCurrentUser();
        String emailAddress = fUser.getEmail();
        emailAddressTextView.setText(emailAddress);


        //get profile image from fStore and set profile image if successful. Set to default if unsuccessful
        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageReference.child("users/"+userId+"/profile.jpeg");

        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                profileImage.setImageResource(R.drawable.baseline_person_24);
            }
        });



        //Get username and stats from FireBase and update corresponding TextViews
        fStore.collection("users").document(userId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        int totalQuestionsAnswered, totalIncorrectAnswers, totalCorrectAnswers;

                        username = documentSnapshot.getString("username");
                        usernameTextView.setText(username);

                        totalQuestionsAnswered = documentSnapshot.getLong("totalQuestionsAnswered").intValue();
                        totalCorrectAnswers = documentSnapshot.getLong("totalCorrectAnswers").intValue();
                        totalIncorrectAnswers = documentSnapshot.getLong("totalIncorrectAnswers").intValue();

                        totalQuestionsAnsweredTextView.setText(String.valueOf(totalQuestionsAnswered));
                        totalCorrectAnswersTextView.setText(String.valueOf(totalCorrectAnswers));
                        totalIncorrectAnswersTextView.setText(String.valueOf(totalIncorrectAnswers));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

        //set on click listener for back button. Goes back to MainActivity
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //set OnClick listener to addProfileImage. Sends to fStore and updates MainActivity
        addProfileImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);
            }
        });

        //set on click listener to go to quiz history
        goToHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });

        //set onclick listener to share profile
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String body = "Join " + username + " on the Learning Experiences app and view their " +
                        "stats! Click here to download: https://play.google.com/LearningExperiencesApp";
                intent.putExtra(Intent.EXTRA_TEXT, body);
                startActivity(Intent.createChooser(intent, "Share"));
            }
        });


        //Set on click listener to go to upgradeActivity
        upgradeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, UpgradeActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000 && data != null){
            if(resultCode == Activity.RESULT_OK){
                imageUri = data.getData();
                profileImage.setImageURI(imageUri);

                uploadImageToFirebase(imageUri);
            }
        }

    }


    //uploads profile image to FireBase
    private void uploadImageToFirebase(Uri imageUri) {

        if(imageUri == null){
            Toast.makeText(this, "Image URI is null. Upload canceled.", Toast.LENGTH_SHORT).show();
            return;
        }

        StorageReference fileRef = storageReference.child("users/"+userId+"/profile.jpeg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext(), "Image uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Image upload failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


}