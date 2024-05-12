package com.example.learningexperience;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterInterestsActivity extends AppCompatActivity {

    //TextView algorithms, dataStructures, webDevelopment, testing;

    ArrayList<String> interests;
    Button nextButton;
    FirebaseFirestore fStore;
    FirebaseAuth mAuth;

    String userId;




    LinearLayout algorithmsCard, dataStructuresCard, webDevelopmentCard, testingCard, cDevelopmentCard,
            pythonDevelopmentCard, chemistryCard, physicsCard, biologyCard, engineeringCard;

    boolean algorithmsCardIsClicked, dataStructuresCardIsClicked, webDevelopmentCardIsClicked,
            testingCardIsClicked, cDevelopmentCardIsClicked, pythonDevelopmentCardIsClicked,
            chemistryCardIsClicked, physicsCardIsClicked, biologyCardIsClicked, engineeringCardIsClicked;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_interests);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        interests = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        algorithmsCard = findViewById(R.id.algorithmsCard);
        dataStructuresCard = findViewById(R.id.dataStructuresCard);
        webDevelopmentCard = findViewById(R.id.webDevelopmentCard);
        testingCard = findViewById(R.id.testingCard);
        cDevelopmentCard = findViewById(R.id.cDevelopmentCard);
        pythonDevelopmentCard = findViewById(R.id.pythonDevelopmentCard);
        chemistryCard = findViewById(R.id.chemistryCard);
        physicsCard = findViewById(R.id.physicsCard);
        biologyCard = findViewById(R.id.biologyCard);
        engineeringCard = findViewById(R.id.engineeringCard);

        algorithmsCardIsClicked = false;
        dataStructuresCardIsClicked = false;
        webDevelopmentCardIsClicked = false;
        testingCardIsClicked = false;
        cDevelopmentCardIsClicked = false;
        pythonDevelopmentCardIsClicked = false;
        chemistryCardIsClicked = false;
        physicsCardIsClicked = false;
        biologyCardIsClicked = false;
        engineeringCardIsClicked = false;




        //On click listeners add/remove the interest from the ArrayList. Is not pushed to server until Next button is pressed.

        View.OnClickListener cardClickListener = new View.OnClickListener(){
            public void onClick(View v) {
                int viewId = v.getId();

                if (viewId == R.id.algorithmsCard) {

                    algorithmsCardIsClicked = !algorithmsCardIsClicked;


                    if (algorithmsCardIsClicked) {
                        algorithmsCard.setBackgroundResource(R.drawable.interest_card_background_clicked);
                        interests.add("algorithms");
                        Log.d("interests", String.valueOf(interests));
                    } else {
                        algorithmsCard.setBackgroundResource(R.drawable.interest_card_background);
                        interests.remove("algorithms");
                        Log.d("interests", String.valueOf(interests));
                    }

                } else if (viewId == R.id.dataStructuresCard) {

                    dataStructuresCardIsClicked = !dataStructuresCardIsClicked;

                    if (dataStructuresCardIsClicked) {
                        dataStructuresCard.setBackgroundResource(R.drawable.interest_card_background_clicked);
                        interests.add("data structures");
                        Log.d("interests", String.valueOf(interests));
                    } else {
                        dataStructuresCard.setBackgroundResource(R.drawable.interest_card_background);
                        interests.remove("data structures");
                        Log.d("interests", String.valueOf(interests));
                    }

                } else if (viewId == R.id.webDevelopmentCard) {

                    webDevelopmentCardIsClicked = !webDevelopmentCardIsClicked;

                    if (webDevelopmentCardIsClicked) {
                        webDevelopmentCard.setBackgroundResource(R.drawable.interest_card_background_clicked);
                        interests.add("web development");
                        Log.d("interests", String.valueOf(interests));
                    } else {
                        webDevelopmentCard.setBackgroundResource(R.drawable.interest_card_background);
                        interests.remove("web development");
                        Log.d("interests", String.valueOf(interests));
                    }

                } else if (viewId == R.id.testingCard) {

                    testingCardIsClicked = !testingCardIsClicked;

                    if (testingCardIsClicked) {
                        testingCard.setBackgroundResource(R.drawable.interest_card_background_clicked);
                        interests.add("testing");
                        Log.d("interests", String.valueOf(interests));
                    } else {
                        testingCard.setBackgroundResource(R.drawable.interest_card_background);
                        interests.remove("testing");
                        Log.d("interests", String.valueOf(interests));
                    }

                } else if (viewId == R.id.cDevelopmentCard) {

                    cDevelopmentCardIsClicked = !cDevelopmentCardIsClicked;

                    if (cDevelopmentCardIsClicked) {
                        cDevelopmentCard.setBackgroundResource(R.drawable.interest_card_background_clicked);
                        interests.add("c++ development");
                        Log.d("interests", String.valueOf(interests));
                    } else {
                        cDevelopmentCard.setBackgroundResource(R.drawable.interest_card_background);
                        interests.remove("c++ development");
                        Log.d("interests", String.valueOf(interests));
                    }

                } else if (viewId == R.id.pythonDevelopmentCard) {

                    pythonDevelopmentCardIsClicked = !pythonDevelopmentCardIsClicked;

                    if (pythonDevelopmentCardIsClicked) {
                        pythonDevelopmentCard.setBackgroundResource(R.drawable.interest_card_background_clicked);
                        interests.add("python development");
                        Log.d("interests", String.valueOf(interests));
                    } else {
                        pythonDevelopmentCard.setBackgroundResource(R.drawable.interest_card_background);
                        interests.remove("python development");
                        Log.d("interests", String.valueOf(interests));
                    }

                } else if (viewId == R.id.chemistryCard) {

                    chemistryCardIsClicked = !chemistryCardIsClicked;

                    if (chemistryCardIsClicked) {
                        chemistryCard.setBackgroundResource(R.drawable.interest_card_background_clicked);
                        interests.add("chemistry");
                        Log.d("interests", String.valueOf(interests));
                    } else {
                        chemistryCard.setBackgroundResource(R.drawable.interest_card_background);
                        interests.remove("chemistry");
                        Log.d("interests", String.valueOf(interests));
                    }

                } else if (viewId == R.id.physicsCard) {

                    physicsCardIsClicked = !physicsCardIsClicked;

                    if (physicsCardIsClicked) {
                        physicsCard.setBackgroundResource(R.drawable.interest_card_background_clicked);
                        interests.add("physics");
                        Log.d("interests", String.valueOf(interests));
                    } else {
                        physicsCard.setBackgroundResource(R.drawable.interest_card_background);
                        interests.remove("physics");
                        Log.d("interests", String.valueOf(interests));
                    }

                } else if (viewId == R.id.biologyCard) {

                    biologyCardIsClicked = !biologyCardIsClicked;

                    if (biologyCardIsClicked) {
                        biologyCard.setBackgroundResource(R.drawable.interest_card_background_clicked);
                        interests.add("biology");
                        Log.d("interests", String.valueOf(interests));
                    } else {
                        biologyCard.setBackgroundResource(R.drawable.interest_card_background);
                        interests.remove("biology");
                        Log.d("interests", String.valueOf(interests));
                    }

                } else if (viewId == R.id.engineeringCard) {

                    engineeringCardIsClicked = !engineeringCardIsClicked;

                    if (engineeringCardIsClicked) {
                        engineeringCard.setBackgroundResource(R.drawable.interest_card_background_clicked);
                        interests.add("engineering");
                        Log.d("interests", String.valueOf(interests));
                    } else {
                        engineeringCard.setBackgroundResource(R.drawable.interest_card_background);
                        interests.remove("engineering");
                        Log.d("interests", String.valueOf(interests));
                    }
                }
            }

        };

        //set on click listener to each card
        algorithmsCard.setOnClickListener(cardClickListener);
        dataStructuresCard.setOnClickListener(cardClickListener);
        webDevelopmentCard.setOnClickListener(cardClickListener);
        testingCard.setOnClickListener(cardClickListener);
        cDevelopmentCard.setOnClickListener(cardClickListener);
        pythonDevelopmentCard.setOnClickListener(cardClickListener);
        chemistryCard.setOnClickListener(cardClickListener);
        physicsCard.setOnClickListener(cardClickListener);
        biologyCard.setOnClickListener(cardClickListener);
        engineeringCard.setOnClickListener(cardClickListener);


        nextButton = findViewById(R.id.nextButton);



        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userId = mAuth.getCurrentUser().getUid();
                DocumentReference docRef = fStore.collection("users").document(userId);

                for(int i = 0; i < interests.size(); i++){
                    String interest = interests.get(i);
                    docRef.update("interests", FieldValue.arrayUnion(interest));
                }

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}