package cn.rongcloud.quickstart.forgetpage;



import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.rtcdemo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.rongcloud.quickstart.utils.HttpUtil;

public class forgetEmailActivity extends AppCompatActivity {
    Button checkEmailBtn,button;
    EditText checkEmailText,emailForgetText;
    String EmailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_forget);




        ImageButton imageButton;
        imageButton=findViewById(R.id.registerBackBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        checkEmailBtn=findViewById(R.id.CheckEmailBtn);
        checkEmailText=(EditText)findViewById(R.id.CheckEmailText);
        button=findViewById(R.id.button);
        emailForgetText=(EditText)findViewById(R.id.EmailForgetText);



        final forgetEmailActivity.MyCountDownTimer myCountDownTimer = new forgetEmailActivity.MyCountDownTimer(60000,1000);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(emailForgetText.getText().toString())){

                    if(isEmail(emailForgetText.getText().toString())){
                        myCountDownTimer.start();
                        new Thread(()->{

                            JSONObject params = new JSONObject();
                            try {
                                params.put("email",(emailForgetText.getText().toString()));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String res = null;
                            try {
                                res = HttpUtil.postJson("email/save",params);
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

                                    Toast.makeText(forgetEmailActivity.this, msg, Toast.LENGTH_SHORT).show();
                                });
                                Log.e("msg:",msg);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }).start();
                    }else{
                        Toast.makeText(forgetEmailActivity.this, "请输入正确格式的邮箱！", Toast.LENGTH_SHORT).show();
                    }


                }else{
                    Toast.makeText(forgetEmailActivity.this, "请输入邮箱！", Toast.LENGTH_SHORT).show();
                }
            }
        });


        checkEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(checkEmailText.getText().toString())){



                    new Thread(()->{

                        JSONObject params = new JSONObject();
                        try {
                            params.put("email",(emailForgetText.getText().toString()));
                            params.put("code",(checkEmailText.getText().toString()));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String res = null;
                        try {
                            res = HttpUtil.postJson("email/verify",params);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("Res",res);
                        try {
                            JSONObject ja = new  JSONObject(res);
                            String msg=ja.getString("msg");
                            int code=ja.getInt("code");
                            if(code==0){
                                runOnUiThread(()->{

                                    Toast.makeText(forgetEmailActivity.this, msg, Toast.LENGTH_SHORT).show();
                                });
                            }
                            else{

                                //跳转到注册页面
                                Intent intent = new Intent(forgetEmailActivity.this, forgetActivity.class);//当点击详情页时触发事件函数完成页面跳转
                                Bundle bundle = new Bundle();// 创建Bundle对象
                                bundle.putString("emailText",(emailForgetText.getText().toString()) );
                                intent.putExtras(bundle);// 将Bundle对象放入到Intent上
                                startActivity(intent);
                                finish();
                            }
                            Log.e("msg:",msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }).start();





                }else{
                    Toast.makeText(forgetEmailActivity.this, "验证码不能为空！", Toast.LENGTH_SHORT).show();
                }
            }
        });





    }


    //判断email格式是否正确
    public boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";


        return email.matches(str);
    }




    //复写倒计时
    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程
        @Override
        public void onTick(long l) {
            //防止计时过程中重复点击
            button.setClickable(false);
            button.setText(l/1000+"s后重新获取验证码");

        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            //重新给Button设置文字
            button.setText("重新获取验证码");
            //设置可点击
            button.setClickable(true);
        }
    }






}