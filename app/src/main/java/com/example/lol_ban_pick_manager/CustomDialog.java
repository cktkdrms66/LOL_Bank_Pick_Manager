package com.example.lol_ban_pick_manager;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CustomDialog {
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

    public static void showDialog(Context context, String text){
        CustomDialog customDialog = new CustomDialog(context);
        customDialog.callFunction(text);
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
