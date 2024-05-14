package com.example.learningexperience;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {

    ImageButton backButton;

    TextView usernameTextView, emailAddressTextView, totalQuestionsAnsweredTextView, totalIncorrectAnswersTextView, totalCorrectAnswersTextView;

    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    FirebaseUser fUser;
    String userId;


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

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        userId = fAuth.getUid();

        //get user Email address and set emailAddressTextView
        if(fUser != null) {
            String emailAddress = fUser.getEmail();
            emailAddressTextView.setText(emailAddress);
        }



        //Get username and stats from FireBase and update corresponding TextViews
        fStore.collection("users").document(userId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String username;
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
    }


}