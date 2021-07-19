package cn.rongcloud.quickstart.homepage;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.rtcdemo.R;

import org.json.JSONException;
import org.json.JSONObject;

//import cn.rongcloud.quickstart.MeetingRoom.MeetingActivity;
import cn.rongcloud.quickstart.MeetingRoom.MeetingActivity;
import cn.rongcloud.quickstart.utils.HttpUtil;

public class detailsMeetingActivity extends AppCompatActivity {

    TextView meetingNameText,meetingIdText,meetingCreatorText,meetingIntroText,meetingStartTime,meetingEndTime;
    Button deleteMeetingBtn,joinMeetingBtn;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_details);


        //回退按钮绑定事件
        ImageButton BackBtn;
        BackBtn=findViewById(R.id.BackBtn);
        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(detailsMeetingActivity.this, homeListActivity.class);
                startActivity(intent);
                finish();
            }
        });


        /**
         * 获取上个页面传过来的会议详情
         * 注册按钮点击事件
         * */

        Intent intent = getIntent();
        String meetingName= intent.getExtras().get("meetingNameText").toString();
        String meetingId= intent.getExtras().get("meetingIdText").toString();
        String meetingCreator= intent.getExtras().get("meetingCreatorText").toString();
        String meetingIntro= intent.getExtras().get("meetingIntroText").toString();
        String StartTime= intent.getExtras().get("meetingStartTime").toString();
        String EndTime= intent.getExtras().get("meetingEndTime").toString();
        if("2221-07-03 21:30:00".equals(EndTime)){
            EndTime="";
        }
        Log.e("meetingNameText:",meetingName);
        Log.e("meetingIdText:",meetingId);
        Log.e("meetingCreatorText:",meetingCreator);
        Log.e("meetingIntroText:",meetingIntro);
        Log.e("meetingStartTime:",StartTime);
        Log.e("meetingEndTime:",EndTime);
        meetingNameText=findViewById(R.id.meetingNameText);
        meetingIdText=findViewById(R.id.meetingIdText);
        meetingCreatorText=findViewById(R.id.meetingCreatorText);
        meetingIntroText=findViewById(R.id.meetingIntroText);
        meetingStartTime=findViewById(R.id.meetingStartTime);
        meetingEndTime=findViewById(R.id.meetingEndTime);


        meetingNameText.setText(meetingName);
        meetingIdText.setText(meetingId);
        meetingCreatorText.setText(meetingCreator);
        meetingIntroText.setText(meetingIntro);
        meetingStartTime.setText(StartTime);
        meetingEndTime.setText(EndTime);


        if(!("".equals(EndTime))){
            findViewById(R.id.joinMeetingBtn).setVisibility(View.GONE);
        }


        /**
         *
         * 删除会议
         */


        SharedPreferences sp = detailsMeetingActivity.this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        int id=sp.getInt("id",0);

        deleteMeetingBtn=findViewById(R.id.deleteMeetingBtn);
        deleteMeetingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(()->{
                    //登录结果
                    String res = HttpUtil.post("meetingUser/del","meet_id="+meetingId+"&user_id="+id);

                    Log.e("Res",res);

                    try {
                        JSONObject ja = new  JSONObject(res);

                        String msg=ja.getString("msg");
                        int code=ja.getInt("code");

                        runOnUiThread(()->{
                            Toast.makeText(detailsMeetingActivity.this, msg, Toast.LENGTH_SHORT).show();

                        });
                        Intent intent=new Intent(detailsMeetingActivity.this, homeListActivity.class);
                        startActivity(intent);
                        finish();


                        Log.e("msg:",msg);



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }).start();
            }
        });


        /**
         * 开始会议
         */
        joinMeetingBtn=findViewById(R.id.joinMeetingBtn);
        joinMeetingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent=new Intent(detailsMeetingActivity.this, MeetingActivity.class);
                Bundle bundle = new Bundle();// 创建Bundle对象
                bundle.putString("meetID", meetingId);

                intent.putExtras(bundle);// 将Bundle对象放入到Intent上
                startActivity(intent);

            }
        });











    }




}
