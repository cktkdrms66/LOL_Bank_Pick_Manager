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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.paperdb.Paper;

public class FragmentMatchActivity extends Fragment {
    private RecyclerView recyclerView;
    private ImageView imageView_setting;

    static int posIndex;
    MatchAdapter adapter;
    public class WrapContentLinearLayoutManager extends LinearLayoutManager {
        public WrapContentLinearLayoutManager(Context context) {
            super(context);
        }

        //... constructor
        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException e) {

            }
        }
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_match,container,false);

        imageView_setting = view.findViewById(R.id.match_imageView_setting);
        recyclerView = view.findViewById(R.id.team_recyclerView);


        imageView_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo
                Paper.book().destroy();
                Paper.book().write("totalPlayerNum", 0);
                Paper.book().write("totalTeamNum", 0);
                Paper.book().write("totalMatchNum", 0);

            }
        });


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getActivity()));
        adapter = new MatchAdapter(ApplicationClass.matches);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new MatchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Intent intent;
                if(ApplicationClass.matches.get(pos).type == 0){
                    intent = new Intent(MainActivity.context, PopupMakeMatchActivity.class);
                }else{
                    intent = new Intent(MainActivity.context, PopupGameActivity.class);
                    intent.putExtra("matchIndex", pos);
                }
                startActivity(intent);
            }
        });

        adapter.setOnLongClickListener(new MatchAdapter.OnLongClickListener() {
            @Override
            public void onLongClick(View v, int pos) {
                posIndex = pos;
                CustomDialog customDialog = new CustomDialog(MainActivity.context);
                customDialog.callFunction("삭제하시겠습니까?");
                customDialog.setOnOkClickListener(new CustomDialog.OnOkClickListener() {
                    @Override
                    public void onClick(View v) {
                        ApplicationClass.removeMatch(posIndex);
                        adapter.notifyItemRemoved(posIndex);
                    }
                });
            }
        });

        return  view;
    }


}
