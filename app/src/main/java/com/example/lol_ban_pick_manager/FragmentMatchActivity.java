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
        MatchAdapter adapter = new MatchAdapter(ApplicationClass.matches);
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

        return  view;
    }


}
