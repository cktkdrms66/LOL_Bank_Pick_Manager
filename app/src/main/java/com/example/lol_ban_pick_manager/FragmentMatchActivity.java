package com.example.lol_ban_pick_manager;

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

public class FragmentMatchActivity extends Fragment {
    private RecyclerView recyclerView;
    private ImageView imageView_search;

    static int posIndex;
    MatchAdapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_match,container,false);

        imageView_search = view.findViewById(R.id.match_imageView_search);
        recyclerView = view.findViewById(R.id.team_recyclerView);


        imageView_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo
            }
        });


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
