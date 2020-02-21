package com.example.lol_ban_pick_manager;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CustomDialog {

    static int victoryType = 2;
    private Context context;
    private OnOkClickListener mOkListener;
    public CustomDialog(Context context) {
        this.context = context;
    }

    public interface OnOkClickListener{
        void onClick(View v);
    }


    public void setOnOkClickListener(OnOkClickListener listener){
        mOkListener = listener;
    }


    public void callFunction(String name0, String name1){
        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.dialog_list);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.

        final TextView[] textViews = new TextView[3];
        textViews[0] = dlg.findViewById(R.id.dialog_list_textView0);
        textViews[1] = dlg.findViewById(R.id.dialog_list_textView1);
        textViews[2] = dlg.findViewById(R.id.dialog_list_textView2);
        final Button okButton = dlg.findViewById(R.id.dialog_list_ok);
        final Button cancelButton = dlg.findViewById(R.id.dialog_list_cancel);


        textViews[0].setText(name0);
        textViews[1].setText(name1);
        textViews[2].setText("설정 안함");
        textViews[2].setTextColor(Color.BLACK);
        victoryType = 2;
        for(int i = 0; i < 3; i++){
            textViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int i = 0;
                    for(TextView textView: textViews){
                        if(textView == view){
                            break;
                        }
                        i++;
                    }
                    for(int j = 0; j < 3; j++){
                        if(j == i){
                            textViews[j].setTextColor(Color.BLACK);
                        }else{
                            textViews[j].setTextColor(0x81000000);
                        }
                    }
                    victoryType = i;
                }
            });
        }
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOkListener.onClick(view);
                dlg.dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
            }
        });
    }

    public void callFunction(String text) {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.dialog_normal);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.

        final TextView message = dlg.findViewById(R.id.dialog_text);
        final Button okButton = dlg.findViewById(R.id.dialog_okButton);
        final Button cancelButton = dlg.findViewById(R.id.dialog_cancelButton);

        message.setText(text);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOkListener.onClick(view);
                dlg.dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
            }
        });
    }
}
