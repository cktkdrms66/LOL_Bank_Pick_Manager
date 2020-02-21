package com.example.lol_ban_pick_manager;

import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TeamAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Team> mItems;
    private ArrayList<Boolean> mIsClicked = new ArrayList<>();
    private int mOnlyItemPosition = -1;

    public int getmOnlyItemPosition() {
        return mOnlyItemPosition;
    }

    public void setOnlyClick(int pos, boolean isClick){
        if(isClick){
            if(mOnlyItemPosition != -1){
                mIsClicked.set(mOnlyItemPosition, false);
            }
            mOnlyItemPosition = pos;
            notifyDataSetChanged();
        }else{
            mOnlyItemPosition = -1;
        }
    }

    public Team getTeam(){
        if(mOnlyItemPosition == -1){
            return mItems.get(1);
        }
        return mItems.get(mOnlyItemPosition);
    }
    public int getTeamLogo(){
        if(mOnlyItemPosition == -1){
            return R.drawable.normal_team_logo;
        }
        return mItems.get(mOnlyItemPosition).logo;

    }
    public String getTeamName(){
        if(mOnlyItemPosition == -1){
            return "기본팀";
        }
        return mItems.get(mOnlyItemPosition).name;
    }
    public class TeamViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout constraintLayout;
        TextView textView_name;
        TextView textView_backgroud;
        ImageView imageView_logo;
        ImageView imageView_color[] = new ImageView[5];
        TextView textView_player[] = new TextView[5];
        ImageView imageView_plus;
        public TeamViewHolder(View view){
            super(view);
            constraintLayout = view.findViewById(R.id.cardView_game_layout);
            imageView_plus = view.findViewById(R.id.cardView_team_plus);
            textView_name = view.findViewById(R.id.cardView_team_name);
            textView_backgroud = view.findViewById(R.id.cardView_team_logo_backgroud);
            imageView_logo = view.findViewById(R.id.cardView_team_logo);
            imageView_color[0] = view.findViewById(R.id.cardView_team_color0);
            imageView_color[1] = view.findViewById(R.id.cardView_team_color1);
            imageView_color[2] = view.findViewById(R.id.cardView_team_color2);
            imageView_color[3] = view.findViewById(R.id.cardView_team_color3);
            imageView_color[4] = view.findViewById(R.id.cardView_team_color4);
            textView_player[0] = view.findViewById(R.id.cardView_team_player0);
            textView_player[1] = view.findViewById(R.id.cardView_team_player1);
            textView_player[2] = view.findViewById(R.id.cardView_team_player2);
            textView_player[3] = view.findViewById(R.id.cardView_team_player3);
            textView_player[4] = view.findViewById(R.id.cardView_team_player4);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(mIsClicked.get(pos)){
                            mIsClicked.set(pos, false);
                            setOnlyClick(pos, false);
                            constraintLayout.setBackgroundResource(R.drawable.custom_team_cardview_backgroud);

                        }else{
                            mIsClicked.set(pos, true);
                            setOnlyClick(pos, true);
                            constraintLayout.setBackgroundResource(R.drawable.custom_team_cardview_backgroud_clicked);
                        }
                    }
                }
            });
        }
    }

    public TeamAdapter(ArrayList<Team> mItems){
        this.mItems = mItems;
        for(int i = 0; i < mItems.size(); i++){
            mIsClicked.add(false);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_team, parent, false);
        return new TeamViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TeamViewHolder new_holder = (TeamViewHolder) holder;
            new_holder.textView_name.setText(mItems.get(position).name);
            if(mItems.get(position).type == 0){
                new_holder.textView_backgroud.setBackgroundResource(R.drawable.nothing);
                new_holder.imageView_plus.setImageResource(R.drawable.plus);
            }else{
                new_holder.textView_backgroud.setBackgroundResource(R.drawable.team_logo_backgroud);
                new_holder.imageView_plus.setImageResource(R.drawable.nothing);
            }
            new_holder.imageView_logo.setImageResource(mItems.get(position).logo);
            for(int i = 0; i < 5; i++){
                String tear = mItems.get(position).players[i].tear;
                new_holder.textView_player[i].setText(tear);
                new_holder.imageView_color[i].setColorFilter(Team.tear_color(tear), PorterDuff.Mode.SRC_IN);
            }
            if(mIsClicked.get(position)){
                new_holder.constraintLayout.setBackground(ContextCompat.getDrawable(PopupTeamActivity.context,
                        R.drawable.custom_team_cardview_backgroud_clicked));
            }else{
                new_holder.constraintLayout.setBackground(ContextCompat.getDrawable(PopupTeamActivity.context,
                        R.drawable.custom_team_cardview_backgroud));
            }


    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
