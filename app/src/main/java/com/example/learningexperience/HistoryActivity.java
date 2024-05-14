package com.example.learningexperience;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String userId;
    List<HistoryItem> historyItemList;
    HistoryAdapter historyAdapter;

    ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        userId = fAuth.getUid();

        historyItemList = new ArrayList<>();

        backButton = findViewById(R.id.backButton);

        //get data from firestore and add to a List
        fStore.collection("users").document(userId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        Log.d("Firebase", "Success");
                        if(documentSnapshot.exists()){
                            List<Map<String, Object>> list = (List<Map<String, Object>>) documentSnapshot.get("history");

                            for(Map<String, Object> questionData : list){
                                HistoryItem historyItem = new HistoryItem(questionData.get("question").toString(),
                                        questionData.get("answer1").toString(),
                                        questionData.get("answer2").toString(),
                                        questionData.get("answer3").toString(),
                                        questionData.get("correctAnswer").toString(),
                                        questionData.get("userAnswer").toString());

                                historyItemList.add(historyItem);
                            }

                            recyclerView = findViewById(R.id.recyclerView);
                            recyclerView.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
                            historyAdapter = new HistoryAdapter(historyItemList);
                            recyclerView.setAdapter(historyAdapter);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}