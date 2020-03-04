package com.example.lol_ban_pick_manager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentTeamActivity extends Fragment {

    RecyclerView recyclerView;
    ImageView imageView_search;
    TeamAdapter adapter;

    static int posIndex;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team,container,false);

        imageView_search = view.findViewById(R.id.team_imageView_search);
        recyclerView = view.findViewById(R.id.team_recyclerView);


        imageView_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo
            }
        });


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new TeamAdapter(ApplicationClass.teams);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new TeamAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                if(ApplicationClass.teams.get(pos).type == 0){
                    Intent intent = new Intent(MainActivity.context, PopupMakeTeamActivity.class);
                    startActivityForResult(intent, 0);
                }else{
                    if(pos == 1){
                        return;
                    }
                    Team team = ApplicationClass.teams.get(pos);
                    Intent intent = new Intent(MainActivity.context, TeamDetailActivity.class);
                    intent.putExtra("teamIndex", pos);

                    startActivityForResult(intent, 1);
                }
            }
        });
        adapter.setOnLongClickListener(new TeamAdapter.OnLongClickListener() {
            @Override
            public void onLongClick(View v, int pos) {
                if(ApplicationClass.teams.get(pos).type == 0 || pos == 1){
                    return;
                }else{
                    posIndex = pos;
                    CustomDialog customDialog = new CustomDialog(MainActivity.context);
                    customDialog.callFunction("삭제하시겠습니까?");
                    customDialog.setOnOkClickListener(new CustomDialog.OnOkClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(ApplicationClass.teams.get(posIndex).using > 0){
                                ApplicationClass.showToast(MainActivity.context,
                                        "이 팀을 포함한 매치가 있어 삭제할 수 없습니다.");
                                return;

                            }
                            ApplicationClass.removeTeam(posIndex);
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
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0){
            if(resultCode == -1){
                int isMake = data.getExtras().getInt("isMake");
                if(isMake == 1){
                    adapter.notifyItemInserted(ApplicationClass.teams.size()-1);
                }
            }
        }
        if(requestCode==1){
            if(resultCode == -1){
                boolean isChange = data.getExtras().getBoolean("isChange");
                if(isChange){
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
}
