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
import android.widget.Toast;


import com.rtcdemo.R;

import org.json.JSONException;
import org.json.JSONObject;

import cn.rongcloud.quickstart.utils.HttpUtil;
import cn.rongcloud.quickstart.utils.UIUtil;

public class joinMeetingActivity extends AppCompatActivity {

    Button button3;
    EditText MeetingPasswordText,MeetingIdText;
    Switch passwordSwitch;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_join);


        //回退按钮绑定事件
        ImageButton BackBtn;
        BackBtn=findViewById(R.id.BackBtn);
        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(joinMeetingActivity.this, homeListActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //初始化
        MeetingPasswordText=findViewById(R.id.MeetingPasswordText);
        MeetingIdText=findViewById(R.id.MeetingIdText);

        button3=findViewById(R.id.button3);



        //控制密码输入框是否能输入

        passwordSwitch=findViewById(R.id.passwordSwitch);
        final boolean[] check = {true};
        MeetingPasswordText.setEnabled(false);
        passwordSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {
                    // User has clicked check box

                    if(check[0]){
                        MeetingPasswordText.setEnabled(true);
                        MeetingPasswordText.setHint("输入6位数字");
                        Log.e("选中","选中");
                        check[0] =false;
                    }else{
                        MeetingPasswordText.setEnabled(false);
                        Log.e("未选","未选");
                        check[0] =true;
                        MeetingPasswordText.setHint("");
                        MeetingPasswordText.setText("");
                    }


                }
                else
                {
                    return;
                }
            }
        });

        //加入会议

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(MeetingIdText.getText().toString())){
                    if(!TextUtils.isEmpty(MeetingPasswordText.getText().toString())&&!isPassword(MeetingPasswordText.getText().toString())){
                        Toast.makeText(joinMeetingActivity.this, "密码格式错误！", Toast.LENGTH_SHORT).show();
                    }else{

                        SharedPreferences sp = joinMeetingActivity.this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                        int id;
                        id=sp.getInt("id", 0);
                        new Thread(()->{
                            //登录结果
                            String res = HttpUtil.post("meetingUser/checkMeetingPassword","meet_id="+(MeetingIdText.getText().toString())+"&meeting_password="+(MeetingPasswordText.getText().toString())+"&user_id="+id);
                            Log.e("Res",res);

                            try {
                                JSONObject ja = new  JSONObject(res);
                                String msg=ja.getString("msg");
                                int code=ja.getInt("code");
                                if(code==0){

                                    runOnUiThread(()->{

                                        Toast.makeText(joinMeetingActivity.this, msg, Toast.LENGTH_SHORT).show();
                                    });


                                }
                                else{
                                    runOnUiThread(()->{

                                        UIUtil.alertMessage(joinMeetingActivity.this, msg, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent =new Intent(joinMeetingActivity.this, homeListActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });
                                    });
                                }

                                Log.e("msg:",msg);



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }).start();


                    }

                }else{
                    Toast.makeText(joinMeetingActivity.this, "会议号不能为空！", Toast.LENGTH_SHORT).show();
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








}
