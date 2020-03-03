package com.example.lol_ban_pick_manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChampionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Champion> mItems;
    ArrayList<Boolean> mIsClicked = new ArrayList<>();
    ArrayList<Boolean> mIsPicked = new ArrayList<>();
    int mOnlyItemPosition = -1;
    OnItemClickListener mListener = null;
    OnLongClickListener mLongListener = null;
    public int getmOnlyItemPosition() {
        return mOnlyItemPosition;
    }

    public Champion getChampion(int pos){
        return mItems.get(pos);
    }
    public boolean getIsClicked(int pos){
        return mIsClicked.get(pos);
    }
    public void setIsClicked(int pos, boolean b){
        mIsClicked.set(pos,b);
    }
    public interface OnItemClickListener{
        void onItemClick(int pos, ImageView imageView);
    }
    public interface OnLongClickListener{
        void onLongClick(View view, int pos);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }
    public void setOnLongClickListener(OnLongClickListener listener){
        this.mLongListener = listener;
    }
    public void setClickClear(boolean isReturn, boolean isNext){
        if(isReturn ==false){
            mIsClicked.set(mOnlyItemPosition, false);
            mIsPicked.set(mOnlyItemPosition, true);

            mOnlyItemPosition = -1;
        }else{
            if(mOnlyItemPosition != -1){
                setIsClicked(mOnlyItemPosition, false);
            }
            BanPickActivity context = ((BanPickActivity)BanPickActivity.context);
            int championIndex = ((Match.PickClass)context.pickSerial.get(context.getPickIndex())).championIndex;

            if(isNext == false){
                mIsPicked.set(championIndex, false);
                mIsClicked.set(championIndex, true);
                mOnlyItemPosition = championIndex;
            } else{
                mIsPicked.set(championIndex, true);
                mIsClicked.set(championIndex, false);
                mOnlyItemPosition = -1;

            }
        }
        notifyDataSetChanged();


    }


    public void setOnlyClick(int pos, boolean isClick){
        if(isClick){
            if(mOnlyItemPosition != -1){
                mIsClicked.set(mOnlyItemPosition, false);
            }
            mOnlyItemPosition = pos;
            notifyDataSetChanged();
        }else{
            mOnlyItemPosition = -1;;
        }
    }

    public class ChampionViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        public ChampionViewHolder(View view){
            super(view);
            imageView = view.findViewById(R.id.cardView_champion_image);
            textView = view.findViewById(R.id.cardView_champion_name);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(mListener != null){
                            if(pos < mIsPicked.size()){
                                if(mIsPicked.get(pos)){
                                    return;
                                }
                            }


                            mListener.onItemClick(pos, imageView);
                        }
                    }
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(mLongListener != null){
                            if(pos < mIsPicked.size()){
                                if(mIsPicked.get(pos)){
                                    return true;
                                }
                            }
                            mLongListener.onLongClick(view, pos);
                        }
                    }
                    return true;
                }
            });
        }
    }

    public ChampionAdapter(ArrayList<Champion> champions){
        mItems = champions;
        for(int i = 0; i < mItems.size(); i++){
            mIsClicked.add(false);
            mIsPicked.add(false);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_champion, parent, false);
        return new ChampionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChampionViewHolder new_holder = (ChampionViewHolder) holder;
        new_holder.textView.setText(mItems.get(position).name);
        new_holder.imageView.setImageResource(mItems.get(position).image);
        if(position < mIsClicked.size()){
            if(mIsPicked.get(position)){
                new_holder.imageView.setColorFilter(Color.parseColor("#D81B60"), PorterDuff.Mode.DST);
                new_holder.imageView.setColorFilter(Color.parseColor("#696969"), PorterDuff.Mode.MULTIPLY);
            } else{
                if(mIsClicked.get(position)){
                    new_holder.imageView.setColorFilter(Color.parseColor("#FFABB5B8"), PorterDuff.Mode.MULTIPLY);
                }else{
                    new_holder.imageView.setColorFilter(Color.parseColor("#FFABB5B8"), PorterDuff.Mode.DST);
                }
            }
        }


    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


}
