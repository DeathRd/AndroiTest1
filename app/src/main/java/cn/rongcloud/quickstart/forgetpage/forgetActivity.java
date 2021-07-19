package cn.rongcloud.quickstart.forgetpage;



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

public class forgetActivity extends AppCompatActivity {
    EditText forgetPassWord,forgetAffirmWord;
    TextView registerCheckView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        ImageButton imageButton;
        imageButton=findViewById(R.id.forgetBackBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //获取上个页面传过来的邮箱
        Intent intent = getIntent();
        String emailText= intent.getExtras().get("emailText").toString();
        Log.e("email:",emailText);
        forgetPassWord=findViewById(R.id.newPasswordText);
        forgetAffirmWord=findViewById(R.id.checkPasswordText);
        button=findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(forgetPassWord.getText().toString())&&!TextUtils.isEmpty(forgetAffirmWord.getText().toString())){
                    if((forgetPassWord.getText().toString()).equals(forgetAffirmWord.getText().toString())){

                        if(isPassword(forgetPassWord.getText().toString())){
                            Log.e("pswd1",forgetPassWord.getText().toString());
                            Log.e("pswd2",forgetAffirmWord.getText().toString());
                            new Thread(()->{

                                JSONObject params = new JSONObject();
                                try {
                                    params.put("password",(forgetPassWord.getText().toString()));
                                    params.put("email",emailText);


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                String res = null;
                                try {
                                    res = HttpUtil.postJson("user/changPassword",params);
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

                                        UIUtil.alertMessage(forgetActivity.this, msg, new DialogInterface.OnClickListener() {
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
                            Toast.makeText(forgetActivity.this, "请输入正确格式！", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(forgetActivity.this, "密码和确认密码不同！", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(forgetActivity.this, "密码都不能为空！", Toast.LENGTH_SHORT).show();
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





}
