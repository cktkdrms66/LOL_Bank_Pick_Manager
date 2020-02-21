package com.example.lol_ban_pick_manager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MatchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private ArrayList<Match> mItems;
    private MatchAdapter.OnItemClickListener mListener = null;
    public interface OnItemClickListener{
        void onItemClick(View v, int pos);
    }

    public void setOnItemClickListener(MatchAdapter.OnItemClickListener listener){
        this.mListener = listener;
    }


    public class MatchViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView_team0_logo;
        ImageView imageView_team1_logo;
        TextView textView_team0_name;
        TextView textView_team1_name;
        TextView textView_back0;
        TextView textView_back1;
        TextView textView_title;
        TextView textView_vs;
        ConstraintLayout constraintLayout;
        ImageView imageView_plus;

        public MatchViewHolder(View view){
            super(view);
            textView_back0 = view.findViewById(R.id.cardView_match_team0_back);
            textView_back1 = view.findViewById(R.id.cardView_match_team1_back);
            textView_vs = view.findViewById(R.id.cardView_match_vs);
            imageView_plus = view.findViewById(R.id.cardView_match_plus);
            imageView_team0_logo = view.findViewById(R.id.cardView_match_team0);
            imageView_team1_logo = view.findViewById(R.id.cardView_match_team1);
            textView_team0_name = view.findViewById(R.id.cardView_match_team0_name);
            textView_team1_name = view.findViewById(R.id.cardView_match_team1_name);
            textView_title = view.findViewById(R.id.cardView_match_name);
            constraintLayout = view.findViewById(R.id.cardView_game_layout);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        mListener.onItemClick(view, pos);
                    }
                }
            });
        }
    }

    public MatchAdapter(ArrayList<Match> champions){
        mItems = champions;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_match, parent, false);
        return new MatchAdapter.MatchViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MatchViewHolder new_holder= (MatchViewHolder) holder;

        new_holder.textView_title.setText(mItems.get(position).name);

        if(mItems.get(position).type == 0){
            new_holder.imageView_plus.setImageResource(R.drawable.plus);
            new_holder.imageView_team0_logo.setImageResource(R.drawable.nothing);
            new_holder.imageView_team1_logo.setImageResource(R.drawable.nothing);
            new_holder.textView_team0_name.setVisibility(View.INVISIBLE);
            new_holder.textView_team1_name.setVisibility(View.INVISIBLE);
            new_holder.textView_vs.setVisibility(View.INVISIBLE);
            new_holder.textView_back0.setVisibility(View.INVISIBLE);
            new_holder.textView_back1.setVisibility(View.INVISIBLE);

        }else{
            new_holder.imageView_team0_logo.setImageResource(mItems.get(position).team0.logo);
            new_holder.imageView_team1_logo.setImageResource(mItems.get(position).team1.logo);
            new_holder.textView_team0_name.setText(mItems.get(position).team0.name);
            new_holder.textView_team1_name.setText(mItems.get(position).team1.name);
            if(mItems.get(position).isTeam0Blue == false){
                new_holder.textView_team0_name.setTextColor(0xF44336);
                new_holder.textView_team1_name.setTextColor(0x2196F3);
            }
        }


    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


}
