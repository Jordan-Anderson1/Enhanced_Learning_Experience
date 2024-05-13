package com.example.learningexperience;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private static List<String> interestsList;

    public RecyclerAdapter(List<String> interests){
        interestsList = interests;
    }
    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.generated_task_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        String item = interestsList.get(position);
        holder.taskNumber.setText("Generated Task " + (position + 1));
        holder.taskDescription.setText("This is a short quiz about " + item);

        switch(item){
            case "algorithms":
                holder.taskLogo.setImageResource(R.drawable.quicksort_svgrepo_com);
                break;
            case "data structures":
                holder.taskLogo.setImageResource(R.drawable.data_trends_svgrepo_com);
                break;
            case "web development":
                holder.taskLogo.setImageResource(R.drawable.web_page_browser_window_svgrepo_com);
                break;
            case "testing":
                holder.taskLogo.setImageResource(R.drawable.usability_testing_svgrepo_com);
                break;
            case "c++ development":
                holder.taskLogo.setImageResource(R.drawable.c_program_icon);
                break;
            case "python development":
                holder.taskLogo.setImageResource(R.drawable.python_svgrepo_com);
                break;
            case "chemistry":
                holder.taskLogo.setImageResource(R.drawable.laboratory_test_icon);
                break;
            case "physics":
                holder.taskLogo.setImageResource(R.drawable.science_atom_icon);
                break;
            case "biology":
                holder.taskLogo.setImageResource(R.drawable.dna_svgrepo_com);
                break;
            case "engineering":
                holder.taskLogo.setImageResource(R.drawable.engineering_wrench_svgrepo_com);
                break;
            default:
                break;


        }

    }

    @Override
    public int getItemCount() {
        return interestsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView taskNumber, taskDescription;
        ImageButton goToTaskButton;

        ImageView taskLogo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            taskNumber = itemView.findViewById(R.id.taskNumber);
            taskDescription = itemView.findViewById(R.id.taskDescription);
            goToTaskButton = itemView.findViewById(R.id.goToTaskButton);
            taskLogo = itemView.findViewById(R.id.taskLogo);


            goToTaskButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        String clickedInterest = interestsList.get(position);

                        Intent intent = new Intent(itemView.getContext(), QuizActivity.class);
                        intent.putExtra("interest", clickedInterest);

                        intent.putExtra("taskNumber", String.valueOf(position + 1));
                        itemView.getContext().startActivity(intent);
                    }
                }
            });

        }
    }
}
