package com.example.lol_ban_pick_manager;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class CustomDialog {

    static int victoryType = 2;
    static int where;
    private Context context;
    private OnOkClickListener mOkListener;
    static int tear = -1;
    public CustomDialog(Context context) {
        this.context = context;
    }

    public interface OnOkClickListener{
        void onClick(View v);
    }


    public void setOnOkClickListener(OnOkClickListener listener){
        mOkListener = listener;
    }


    public void callTearLevel(int w){
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.dialog_tear_level);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.



        final ImageView[] imageViews = new ImageView[4];
        imageViews[0] = dlg.findViewById(R.id.dialog_tear0);
        imageViews[1] = dlg.findViewById(R.id.dialog_tear1);
        imageViews[2] = dlg.findViewById(R.id.dialog_tear2);
        imageViews[3] = dlg.findViewById(R.id.dialog_tear3);

        final TextView[] textViews = new TextView[4];
        textViews[0] = dlg.findViewById(R.id.dialog_tear_text0);
        textViews[1] = dlg.findViewById(R.id.dialog_tear_text1);
        textViews[2] = dlg.findViewById(R.id.dialog_tear_text2);
        textViews[3] = dlg.findViewById(R.id.dialog_tear_text3);

        where = w;
        for(int i = 0 ; i < 4; i++){
            imageViews[i].setColorFilter(Team.tear_color(tear), PorterDuff.Mode.SRC_IN);
            imageViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int i = 0;
                    ImageView nowView = (ImageView)view;
                    for(ImageView imageView : imageViews){
                        if(nowView == imageView){
                            break;
                        }
                        i++;
                    }
                    dlg.dismiss();
                    Context context;
                    if(where == 0){
                        context = PopupMakePlayerActivity.context;
                        ((PopupMakePlayerActivity)context).imageView_tear.setColorFilter(Team.tear_color(tear), PorterDuff.Mode.SRC_IN);
                        ((PopupMakePlayerActivity)context).textView_tear.setText(Team.getTearFromInt(tear) + (i+1));
                        ((PopupMakePlayerActivity)context).tear = Team.getTearFromInt(tear) + (i+1);
                    }else{
                        context = PlayerDetailActivity.context;
                        ((PlayerDetailActivity)context).imageView_tear.setColorFilter(Team.tear_color(tear), PorterDuff.Mode.SRC_IN);
                        ((PlayerDetailActivity)context).textView_tear.setText(Team.getTearFromInt(tear) + (i+1));
                        ((PlayerDetailActivity)context).player.tear = Team.getTearFromInt(tear) + (i+1);
                        ((PlayerDetailActivity)context).isChange = true;
                        ApplicationClass.saveRePlayer(((PlayerDetailActivity)context).player);

                    }

                }
            });
        }

    }
    public void callFunction(final int where){
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.dialog_tear);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.

        final ImageView[] imageViews = new ImageView[10];
        imageViews[0] = dlg.findViewById(R.id.dialog_tear0);
        imageViews[1] = dlg.findViewById(R.id.dialog_tear1);
        imageViews[2] = dlg.findViewById(R.id.dialog_tear2);
        imageViews[3] = dlg.findViewById(R.id.dialog_tear3);
        imageViews[4] = dlg.findViewById(R.id.dialog_tear4);
        imageViews[5] = dlg.findViewById(R.id.dialog_tear10);
        imageViews[6] = dlg.findViewById(R.id.dialog_tear11);
        imageViews[7] = dlg.findViewById(R.id.dialog_tear12);
        imageViews[8] = dlg.findViewById(R.id.dialog_tear13);
        imageViews[9] = dlg.findViewById(R.id.dialog_tear14);
        
        final TextView[] textViews = new TextView[10];
        textViews[0] = dlg.findViewById(R.id.dialog_tear_text0);
        textViews[1] = dlg.findViewById(R.id.dialog_tear_text1);
        textViews[2] = dlg.findViewById(R.id.dialog_tear_text2);
        textViews[3] = dlg.findViewById(R.id.dialog_tear_text3);
        textViews[4] = dlg.findViewById(R.id.dialog_tear_text4);
        textViews[5] = dlg.findViewById(R.id.dialog_tear_text5);
        textViews[6] = dlg.findViewById(R.id.dialog_tear_text6);
        textViews[7] = dlg.findViewById(R.id.dialog_tear_text7);
        textViews[8] = dlg.findViewById(R.id.dialog_tear_text8);
        textViews[9] = dlg.findViewById(R.id.dialog_tear_text9);


        for(int i = 0 ; i < 10; i++){
            imageViews[i].setColorFilter(Team.tear_color(textViews[i].getText().toString()), PorterDuff.Mode.SRC_IN);
            imageViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int i = 0;
                    ImageView nowView = (ImageView)view;
                    for(ImageView imageView : imageViews){
                        if(nowView == imageView){
                            break;
                        }
                        i++;
                    }
                    dlg.dismiss();
                    tear = i;
                    Context context;
                    if(where == 0){
                        context = PopupMakePlayerActivity.context;
                    }else{
                        context = PlayerDetailActivity.context;
                    }
                    if(1 <= i  && i < 7){
                        CustomDialog customDialog = new CustomDialog(context);
                        customDialog.callTearLevel(where);
                    }else{
                        if(where == 0){
                            ((PopupMakePlayerActivity)context).imageView_tear.setColorFilter(Team.tear_color(i), PorterDuff.Mode.SRC_IN);
                            ((PopupMakePlayerActivity)context).textView_tear.setText(Team.getTearFromInt(i));
                            ((PopupMakePlayerActivity)context).tear = Team.getTearFromInt(i);
                        }else{
                            ((PlayerDetailActivity)context).imageView_tear.setColorFilter(Team.tear_color(i), PorterDuff.Mode.SRC_IN);
                            ((PlayerDetailActivity)context).textView_tear.setText(Team.getTearFromInt(i));
                            ((PlayerDetailActivity)context).player.tear = Team.getTearFromInt(i);
                            ((PlayerDetailActivity)context).isChange = true;
                            ApplicationClass.saveRePlayer(((PlayerDetailActivity)context).player);
                        }
                    }
                }
            });
        }


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
