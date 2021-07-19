package cn.rongcloud.quickstart.homepage;



import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.rtcdemo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cn.rongcloud.quickstart.utils.HttpUtil;
import cn.rongcloud.quickstart.utils.UIUtil;
import cn.rongcloud.quickstart.view.CustomDatePicker;

public class createMeetingActivity extends AppCompatActivity {

    private CustomDatePicker customDatePicker;
    private String now;
    private TextView meetingTimeText;
    Switch passwordSwitch;
    EditText passwordText,meetingNameText,meetingIntroText;
    Button button2;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_create);

        //回退按钮绑定事件
        ImageButton BackBtn;
        BackBtn=findViewById(R.id.BackBtn);
        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(createMeetingActivity.this, homeListActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //初始化
        meetingNameText=findViewById(R.id.meetingNameText);
        meetingIntroText=findViewById(R.id.meetingIntroText);
        button2=findViewById(R.id.button2);

        //时间控件
        meetingTimeText = findViewById(R.id.meetingTimeText);
        findViewById(R.id.meetingTimeText).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(meetingTimeText.getText().toString().trim()))
                    customDatePicker.show(now);
                else  // 日期格式为yyyy-MM-dd
                    customDatePicker.show(meetingTimeText.getText().toString());
            }

        });

        DatePicker();



        //控制密码输入框是否能输入
        passwordText=findViewById(R.id.passwordText);
        passwordSwitch=findViewById(R.id.passwordSwitch);
        final boolean[] check = {true};
        passwordText.setEnabled(false);
        passwordSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {
                    // User has clicked check box

                    if(check[0]){
                        passwordText.setEnabled(true);
                        passwordText.setHint("输入6位数字");
                        Log.e("选中","选中");
                        check[0] =false;
                    }else{
                        passwordText.setEnabled(false);
                        Log.e("未选","未选");
                        check[0] =true;
                        passwordText.setHint("");
                        passwordText.setText("");
                    }


                }
                else
                {
                    return;
                }
            }
        });


        //提交表单
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(meetingNameText.getText().toString())&&!TextUtils.isEmpty(meetingTimeText.getText().toString())){
                    if(!TextUtils.isEmpty(passwordText.getText().toString())&&!isPassword(passwordText.getText().toString())){
                        Toast.makeText(createMeetingActivity.this, "密码格式错误！", Toast.LENGTH_SHORT).show();
                    }else{

                        SharedPreferences sp = createMeetingActivity.this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                        int id;
                        id=sp.getInt("id", 0);
                        Log.e("id", String.valueOf(id));
                        String date=meetingTimeText.getText().toString()+":00";
                        Log.e("date", date);
                        new Thread(()->{

                            JSONObject params = new JSONObject();
                            try {
                                params.put("meeting_name",(meetingNameText.getText().toString()));
                                params.put("meeting_introduce",(meetingIntroText.getText().toString()));
                                params.put("meeting_password",(passwordText.getText().toString()));
                                params.put("meeting_create_user_id",id);
                                params.put("meeting_start_time",date);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String res = null;
                            try {
                                res = HttpUtil.postJson("meeting/add",params);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Log.e("Res",res);
                            try {
                                JSONObject ja = new  JSONObject(res);
                                String msg=ja.getString("msg");
                                runOnUiThread(()->{

                                    UIUtil.alertMessage(createMeetingActivity.this, msg, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent =new Intent(createMeetingActivity.this, homeListActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                });
                                Log.e("msg:",msg);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }).start();



                    }

                }else{
                    Toast.makeText(createMeetingActivity.this, "会议名称、会议时间都不能为空！", Toast.LENGTH_SHORT).show();
                }

            }
        });








    }





    /**
     * 正则验证
     */
    //验证密码为6位数字
    public boolean isPassword(String password) {
        String str = "^\\d{6}$";

        return password.matches(str);
    }



    /**
     * 显示时间
     */
    private void DatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        //获取当前时间
        now = sdf.format(new Date());
        //tvElectricalTime.setText(now.split(" ")[0]);
        customDatePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                Log.d("yyyyy", time);
                meetingTimeText.setText(time);

            }
        }, now, "2099-01-01 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker.showSpecificTime(true); // 不显示时和分
        customDatePicker.setIsLoop(false); // 不允许循环滚动
    }






}
