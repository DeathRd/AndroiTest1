package cn.rongcloud.quickstart.registerpage;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.rtcdemo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.rongcloud.quickstart.utils.HttpUtil;
import cn.rongcloud.quickstart.utils.UIUtil;

public class registerActivity extends AppCompatActivity {



    EditText registerUserName,registerPassWord,registerAffirmWord;
    TextView registerCheckView;
    Button registerBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ImageButton imageButton;
        imageButton=findViewById(R.id.registerBackBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //输入框焦点事件

        registerUserName=findViewById(R.id.registerUserName);
        registerCheckView=findViewById(R.id.registerCheckView);
        registerUserName.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){//获得焦点
                    //在这里可以对获得焦点进行处理

                }else{//失去焦点
                    //在这里可以对输入的文本内容进行有效的验证
                    new Thread(()->{

                        JSONObject params = new JSONObject();
                        try {
                            params.put("username",registerUserName.getText().toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String res = null;
                        try {
                            res = HttpUtil.postJson("user/checkUsername",params);
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
                                registerCheckView.setText(msg);

                            });
                            Log.e("msg:",msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }).start();



                }
            }
        });



        /*
         * 获取上个页面传过来的邮箱
         * 注册按钮点击事件
         * */


        Intent intent = getIntent();
        String emailText= intent.getExtras().get("emailText").toString();
        Log.e("email:",emailText);




        registerPassWord=findViewById(R.id.registerPassWord);
        registerAffirmWord=findViewById(R.id.registerAffirmWord);
        registerBtn=findViewById(R.id.registerBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(registerPassWord.getText().toString())&&!TextUtils.isEmpty(registerAffirmWord.getText().toString())&&!TextUtils.isEmpty(registerUserName.getText().toString())){
                    if((registerPassWord.getText().toString()).equals(registerAffirmWord.getText().toString())){

                        if(isPassword(registerPassWord.getText().toString())&&isUsername(registerUserName.getText().toString())){


                            Log.e("pswd1",registerPassWord.getText().toString());
                            Log.e("pswd2",registerAffirmWord.getText().toString());
                            new Thread(()->{

                                JSONObject params = new JSONObject();
                                try {
                                    params.put("username",(registerUserName.getText().toString()));
                                    params.put("password",(registerPassWord.getText().toString()));
                                    params.put("nikename","");
                                    params.put("sex",-1);
                                    params.put("age",-1);
                                    params.put("email",emailText);
                                    params.put("user_head","");

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                String res = null;
                                try {
                                    res = HttpUtil.postJson("user/save",params);
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

                                        UIUtil.alertMessage(registerActivity.this, msg, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                            }
                                        });
                                    });
                                    Log.e("msg:",msg);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }).start();

                        }else{
                            Toast.makeText(registerActivity.this, "请输入正确格式！", Toast.LENGTH_SHORT).show();
                        }


                    }else{
                        Toast.makeText(registerActivity.this, "密码和确认密码不同！", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(registerActivity.this, "账号、密码都不能为空！", Toast.LENGTH_SHORT).show();
                }
            }
        });






    }




    /*
     * 正则匹配
     *
     * */

    //判断password格式是否正确
    public boolean isPassword(String password) {
        String str = "^[-_a-zA-Z0-9]{6,12}$";

        return password.matches(str);
    }
    //判断username格式是否正确
    public boolean isUsername(String username) {
        String str = "^.{1,11}$";

        return username.matches(str);
    }



}
