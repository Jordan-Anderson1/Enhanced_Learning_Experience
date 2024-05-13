package com.example.learningexperience;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ResultsActivity extends AppCompatActivity {

    String answer1, answer2, answer3, correctAnswer1, correctAnswer2, correctAnswer3;

    TextView q1userAnswer, q1correctAnswer, q2userAnswer, q2correctAnswer, q3userAnswer, q3correctAnswer, q1desc, q2desc, q3desc;

    Button returnHomeButton;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String userId;

    Long totalQuestionsAnswered, totalCorrectAnswers, totalIncorrectAnswers;

    String firstQuestion, secondQuestion, thirdQuestion;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_results);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        userId = fAuth.getUid();

        Intent intent = getIntent();
        answer1 = intent.getStringExtra("answer1");
        answer2 = intent.getStringExtra("answer2");
        answer3 = intent.getStringExtra("answer3");
        correctAnswer1 = intent.getStringExtra("correctAnswer1");
        correctAnswer2 = intent.getStringExtra("correctAnswer2");
        correctAnswer3 = intent.getStringExtra("correctAnswer3");
        firstQuestion = intent.getStringExtra("firstQuestion");
        secondQuestion = intent.getStringExtra("secondQuestion");
        thirdQuestion = intent.getStringExtra("secondQuestion");

        q1desc = findViewById(R.id.firstQuestion);
        q2desc = findViewById(R.id.secondQuestion);
        q3desc = findViewById(R.id.thirdQuestion);


        q1userAnswer = findViewById(R.id.q1userAnswer);
        q2userAnswer = findViewById(R.id.q2userAnswer);
        q3userAnswer = findViewById(R.id.q3userAnswer);

        q1correctAnswer = findViewById(R.id.q1correctAnswer);
        q2correctAnswer = findViewById(R.id.q2correctAnswer);
        q3correctAnswer = findViewById(R.id.q3correctAnswer);

        q1userAnswer.setText("Your answer: " + answer1);
        q2userAnswer.setText("Your answer: " + answer2);
        q3userAnswer.setText("Your answer: " + answer3);

        q1correctAnswer.setText("Correct answer: " + correctAnswer1);
        q2correctAnswer.setText("Correct answer: " + correctAnswer2);
        q3correctAnswer.setText("Correct answer: " + correctAnswer3);

        q1desc.setText(firstQuestion);
        q2desc.setText(secondQuestion);
        q3desc.setText(thirdQuestion);

        returnHomeButton = findViewById(R.id.returnHomeButton);











        //query firestore and update answers correct etc.
        fStore.collection("users").document(userId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        totalQuestionsAnswered = (Long) documentSnapshot.get("totalQuestionsAnswered");
                        totalCorrectAnswers = (Long) documentSnapshot.get("totalCorrectAnswers");
                        totalIncorrectAnswers = (Long) documentSnapshot.get("totalIncorrectAnswers");

                        Log.d("Firebase", "total correct" + totalCorrectAnswers + "*** total incorrect " + totalIncorrectAnswers + "*** overall total answered " + totalQuestionsAnswered);

                        //update retrieved values locally
                        if(answer1.equals(correctAnswer1)){
                            totalCorrectAnswers++;
                        }else{
                            totalIncorrectAnswers++;
                        }

                        if(answer2.equals(correctAnswer2)){
                            totalCorrectAnswers++;
                        }else{
                            totalIncorrectAnswers++;
                        }

                        if(answer3.equals(correctAnswer3)){
                            totalCorrectAnswers++;
                        }else{
                            totalIncorrectAnswers++;
                        }


                        //Increase total questions count
                        totalQuestionsAnswered += 3;


                        //send updated values to FireStore
                        Map<String, Object> values = new HashMap<>();
                        values.put("totalCorrectAnswers", totalCorrectAnswers);
                        values.put("totalIncorrectAnswers", totalIncorrectAnswers);
                        values.put("totalQuestionsAnswered", totalQuestionsAnswered);

                        fStore.collection("users").document(userId)
                                .update(values)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d("Firebase", "Great success");




                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("Firebase", "No BUENO");
                                    }
                                });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Firebase", "Unable to retrieve documents in ResultsActivity");
                    }
                });





        returnHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}