package cn.rongcloud.quickstart.userpage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class userAlterActivity extends AppCompatActivity {

    ImageButton forgetBackBtn3;
    TextView userNameText,EmailText;
    EditText NikeEdit,SexEdit,ageEdit;
    Button button4;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_user);

        /**
         * 返回用户页面
         */
        forgetBackBtn3=findViewById(R.id.forgetBackBtn3);
        forgetBackBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


        /**
         *
         * 显示用户基本信息
         */
        userNameText=findViewById(R.id.userNameText);
        EmailText=findViewById(R.id.EmailText);
        String email,username,nikename;
        int sex,age;
        SharedPreferences sp = userAlterActivity.this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        email=sp.getString("email", "");
        username=sp.getString("USER_NAME", "");
        nikename=sp.getString("nikename", "");
        sex=sp.getInt("sex", 0);
        age=sp.getInt("age", 0);

        userNameText.setText(username);
        EmailText.setText(email);
        NikeEdit.setText(nikename);
        String SEX="";
        if (sex==0)SEX="女";
        else if(sex==1)SEX="男";

        SexEdit.setText(SEX);
        ageEdit.setText(age);


        String user_head=sp.getString("user_head", "");

        NikeEdit=findViewById(R.id.NikeEdit);
        SexEdit=findViewById(R.id.SexEdit);
        ageEdit=findViewById(R.id.ageEdit);
        button4=findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(ageEdit.getText().toString())&&!TextUtils.isEmpty(SexEdit.getText().toString())&&!TextUtils.isEmpty(NikeEdit.getText().toString())){

                    new Thread(()->{

                        JSONObject params = new JSONObject();
                        try {
                            params.put("username",username);
                            params.put("nikename",(NikeEdit.getText().toString()));
                            params.put("sex",(SexEdit.getText().toString()));
                            params.put("age",(ageEdit.getText().toString()));
                            params.put("email",(EmailText.getText().toString()));
                            params.put("user_head",user_head);




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
                            int code=ja.getInt("code");

                                runOnUiThread(()->{

                                    Toast.makeText(userAlterActivity.this, msg, Toast.LENGTH_SHORT).show();
                                });

                                finish();

                            Log.e("msg:",msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }).start();

                }else{
                    Toast.makeText(userAlterActivity.this, "不能设置为空值", Toast.LENGTH_SHORT).show();
                }
            }



        });






    }





}
