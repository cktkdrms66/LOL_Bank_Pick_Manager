package com.example.lol_ban_pick_manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentPlayerActivity extends Fragment {

    RecyclerView recyclerView;
    ImageView imageView_search;
    PlayerAdapter adapter;

    static int posIndex;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player,container,false);

        imageView_search = view.findViewById(R.id.player_imageView_search);
        recyclerView = view.findViewById(R.id.player_recyclerView);


        imageView_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo
            }
        });


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.context,3));
        adapter = new PlayerAdapter(ApplicationClass.players);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new PlayerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                if(ApplicationClass.players.get(pos).type == 0){
                    //todo
                    Intent intent = new Intent(MainActivity.context, PopupMakePlayerActivity.class);
                    startActivityForResult(intent, 1);
                } else{
                    if(pos == 1){
                        return;
                    }
                    int playerIndex = pos;
                    Intent intent = new Intent(MainActivity.context, PlayerDetailActivity.class);
                    intent.putExtra("where", 0);
                    intent.putExtra("playerIndex", playerIndex);
                    startActivityForResult(intent,0);
                }
            }
        });


        adapter.setOnLongItemClickListener(new PlayerAdapter.OnLongClickListener() {
            @Override
            public void onLongClick(View v, int pos) {

                if(ApplicationClass.players.get(pos).type == 0 || pos == 1){
                    return;

                }else{
                    posIndex = pos;
                    CustomDialog customDialog = new CustomDialog(MainActivity.context);
                    customDialog.callFunction("삭제하시겠습니까?");
                    customDialog.setOnOkClickListener(new CustomDialog.OnOkClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(ApplicationClass.players.get(posIndex).using > 0){
                                ApplicationClass.showToast(MainActivity.context,
                                        "이 플레이어로 이루어진 팀이 있어 삭제할 수 없습니다.");
                                return;

                            }
                            ApplicationClass.removePlayer(posIndex);
                            adapter.notifyItemRemoved(posIndex);
                        }
                    });
                }
            }
        });

        return  view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //0 데이터변경 1 추가 2 삭제
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0){
            if(resultCode == -1){
                Boolean isChange = data.getExtras().getBoolean("isChange");
                if(isChange){
                    adapter.notifyDataSetChanged();
                }

            }
        }else if(requestCode ==1){
            if(resultCode == -1){
                System.out.println("추가");
                Boolean isChange = data.getExtras().getBoolean("isChange");
                if(isChange){
                    adapter.notifyItemInserted(ApplicationClass.players.size()-1);
                }
            }
        }
    }
}
