package cn.rongcloud.quickstart.loginpage;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.rtcdemo.R;

import org.json.JSONException;
import org.json.JSONObject;

import cn.rongcloud.quickstart.forgetpage.forgetEmailActivity;
import cn.rongcloud.quickstart.homepage.homeListActivity;
import cn.rongcloud.quickstart.registerpage.registerEmailActivity;
import cn.rongcloud.quickstart.utils.HttpUtil;
import cn.rongcloud.quickstart.utils.UserManage;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;

    //用于其他mod跳转到当前页面
    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView LoginForget = findViewById(R.id.LoginForgetView);
        TextView LoginRegister = findViewById(R.id.LoginRegisterView);
        /*
         *设置文本为超链接 以及点击事件
         *
         * */

        SpannableString sRegister = new SpannableString("注册");
        sRegister.setSpan(new ClickableSpan(){
            @Override
            public  void updateDrawState(TextPaint t){
                super.updateDrawState(t);
                t.setUnderlineText(false); //设置去掉下划线
                t.setColor(Color.rgb(	135,206,250));//设置字体颜色为黑
            }
            @Override
            public void onClick(View widget){

                Intent intent = new Intent(LoginActivity.this, registerEmailActivity.class);//当点击详情页时触发事件函数完成页面跳转
                startActivity(intent);
            }},0,2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);//取出ss参数中第一个到第2个字作为超链接


        LoginRegister.setText(sRegister);
        LoginRegister.setMovementMethod(LinkMovementMethod.getInstance());
        SpannableString sForget = new SpannableString("忘记密码");
        sForget.setSpan(new ClickableSpan(){
            @Override
            public  void updateDrawState(TextPaint t){
                super.updateDrawState(t);
                t.setUnderlineText(false); //设置去掉下划线
                t.setColor(Color.rgb(	135,206,250));//设置字体颜色为黑
            }
            @Override
            public void onClick(View widget){
                Intent intent = new Intent(LoginActivity.this, forgetEmailActivity.class);//当点击详情页时触发事件函数完成页面跳转
                startActivity(intent);
            }},0,4, Spanned.SPAN_INCLUSIVE_INCLUSIVE);//取出ss参数中第一个到第2个字作为超链接
        LoginForget.setText(sForget);
        LoginForget.setMovementMethod(LinkMovementMethod.getInstance());





        etUsername = (EditText) findViewById(R.id.LoginUserName);
        etPassword = (EditText) findViewById(R.id.LoginPassWord);
        TextView msgText=findViewById(R.id.msgText);

        /*
         * 绑定登录事件访问服务器
         *
         * */


        Button btnLogin = (Button) findViewById(R.id.LoginButton);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(etUsername.getText().toString())
                        && !TextUtils.isEmpty(etPassword.getText().toString())) {
                    Log.e("WangJ", "都不空");


                    //开始登录
                    new Thread(()->{
                        //登录结果
                        String res = HttpUtil.post("user/login","username="+(etUsername.getText().toString())+"&password="+(etPassword.getText().toString()));

                        Log.e("Res",res);

                        try {
                            JSONObject ja = new  JSONObject(res);

                            String msg=ja.getString("msg");
                            int code=ja.getInt("code");
                            if(code==0){

                                runOnUiThread(()->{

                                    msgText.setText(msg);
                                });


                            }
                            else{

                                JSONObject user = ja.getJSONObject("data");//通过user字段获取其所包含的JSONObject对象
                                int userId=user.getInt("user_id");
                                String nikename = user.getString("nikename");//通过name字段获取其所包含的字符串
                                int sex=user.getInt("sex");
                                int age=user.getInt("age");
                                String email=user.getString("email");
                                int user_type=user.getInt("user_type");
                                String user_head=user.getString("user_head");
                                String token=user.getString("token");
                                int login_failed_count=user.getInt("login_failed_count");
                                int del_flag=user.getInt("del_flag");
                                String creat_time=user.getString("creat_time");



                                UserManage.getInstance().saveUserInfo(LoginActivity.this, userId, (etUsername.getText().toString()), (etPassword.getText().toString()),nikename,sex,age,email,user_type,user_head,token,login_failed_count,del_flag,creat_time);
                                homeListActivity.start(LoginActivity.this);
                                finish();

                            }
                            Log.e("msg:",msg);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }).start();


                } else {
                    Toast.makeText(LoginActivity.this, "账号、密码都不能为空！", Toast.LENGTH_SHORT).show();
                }
            }
        });














    }










}