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
                        Toast.makeText(forgetEmailActivity.this, "?????????????????????????????????", Toast.LENGTH_SHORT).show();
                    }


                }else{
                    Toast.makeText(forgetEmailActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
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

                                //?????????????????????
                                Intent intent = new Intent(forgetEmailActivity.this, forgetActivity.class);//?????????????????????????????????????????????????????????
                                Bundle bundle = new Bundle();// ??????Bundle??????
                                bundle.putString("emailText",(emailForgetText.getText().toString()) );
                                intent.putExtras(bundle);// ???Bundle???????????????Intent???
                                startActivity(intent);
                                finish();
                            }
                            Log.e("msg:",msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }).start();





                }else{
                    Toast.makeText(forgetEmailActivity.this, "????????????????????????", Toast.LENGTH_SHORT).show();
                }
            }
        });





    }


    //??????email??????????????????
    public boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";


        return email.matches(str);
    }




    //???????????????
    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //????????????
        @Override
        public void onTick(long l) {
            //?????????????????????????????????
            button.setClickable(false);
            button.setText(l/1000+"s????????????????????????");

        }

        //?????????????????????
        @Override
        public void onFinish() {
            //?????????Button????????????
            button.setText("?????????????????????");
            //???????????????
            button.setClickable(true);
        }
    }






}
