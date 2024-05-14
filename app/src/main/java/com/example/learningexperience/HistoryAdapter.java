package com.example.learningexperience;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private static List<HistoryItem> historyItemList;

    public HistoryAdapter(List<HistoryItem> historyList){
        historyItemList = historyList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryItem historyItem = historyItemList.get(position);

        holder.questionNumberTextView.setText((position + 1) + ". Question " + (position + 1));
        holder.questionDetailsTextView.setText(historyItem.getQuestion());
        holder.answerOneTextView.setText(historyItem.getAnswer1());
        holder.answerTwoTextView.setText(historyItem.getAnswer2());
        holder.answerThreeTextView.setText(historyItem.getAnswer3());

        holder.answerOneIndicator.setImageResource(R.drawable.plain_circle);
        holder.answerTwoIndicator.setImageResource(R.drawable.plain_circle);
        holder.answerThreeIndicator.setImageResource(R.drawable.plain_circle);


        //set indicator for correct answer
        if(historyItem.getCorrectAnswer().equals(historyItem.getAnswer1())){
            holder.answerOneIndicator.setImageResource(R.drawable.green_circle);
        }else if(historyItem.getCorrectAnswer().equals(historyItem.getAnswer2())){
            holder.answerTwoIndicator.setImageResource(R.drawable.green_circle);
        }else if(historyItem.getCorrectAnswer().equals(historyItem.getAnswer3())){
            holder.answerThreeIndicator.setImageResource(R.drawable.green_circle);
        }

        //set red indicator if answered incorrectly
        if(!historyItem.userAnswer.equals(historyItem.getCorrectAnswer())){
            if(historyItem.userAnswer.equals(historyItem.getAnswer1())){
                holder.answerOneIndicator.setImageResource(R.drawable.red_circle);
            }else if(historyItem.userAnswer.equals(historyItem.getAnswer2())){
                holder.answerTwoIndicator.setImageResource(R.drawable.red_circle);
            }else if(historyItem.userAnswer.equals(historyItem.getAnswer3())){
                holder.answerThreeIndicator.setImageResource(R.drawable.red_circle);
            }
        }


    }

    @Override
    public int getItemCount() {
        return historyItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{


        TextView questionNumberTextView, questionDetailsTextView, answerOneTextView, answerTwoTextView, answerThreeTextView;
        ImageView answerOneIndicator, answerTwoIndicator, answerThreeIndicator;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            questionNumberTextView = itemView.findViewById(R.id.questionNumberTextView);
            questionDetailsTextView = itemView.findViewById(R.id.questionDetailsTextView);
            answerOneTextView = itemView.findViewById(R.id.answerOneTextView);
            answerTwoTextView = itemView.findViewById(R.id.answerTwoTextView);
            answerThreeTextView = itemView.findViewById(R.id.answerThreeTextView);

            answerOneIndicator = itemView.findViewById(R.id.answerOneIndicator);
            answerTwoIndicator = itemView.findViewById(R.id.answerTwoIndicator);
            answerThreeIndicator = itemView.findViewById(R.id.answerThreeIndicator);



        }
    }
}
