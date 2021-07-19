package cn.rongcloud.quickstart.userpage;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.rtcdemo.R;

import cn.rongcloud.quickstart.homepage.homeListActivity;


public class userListActivity extends AppCompatActivity {

    ImageButton homeBtn,SettingImgBtn1,SettingImgBtn2,UserImgBtn2;
    TextView UserText1,UserText2;


    public static userListActivity instance = null;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);

        /**
         *   需要关闭页面的地方调用     userListActivity.instance.finish();
         */
        instance = this;



        /**
         *
         * 显示用户基本信息
         */
        UserText1=findViewById(R.id.UserText1);
        UserText2=findViewById(R.id.UserText2);
        String nikename,username;
        SharedPreferences sp = userListActivity.this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        nikename=sp.getString("nikename", "");
        username=sp.getString("USER_NAME", "");
        UserText1.setText(nikename);
        UserText2.setText("用户名："+username);




        /**
         *
         * 底部导航栏
         */
        homeBtn=findViewById(R.id.homeBtn);


        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(userListActivity.this, homeListActivity.class);//当点击详情页时触发事件函数完成页面跳转
                startActivity(intent);
                finish();
            }
        });



        /**
         *
         * 进入设置界面
         */

        SettingImgBtn1=findViewById(R.id.SettingImgBtn1);
        SettingImgBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(userListActivity.this, userSettingActivity.class);//当点击详情页时触发事件函数完成页面跳转
                startActivity(intent);

            }
        });
        SettingImgBtn2=findViewById(R.id.SettingImgBtn2);
        SettingImgBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(userListActivity.this, userSettingActivity.class);//当点击详情页时触发事件函数完成页面跳转
                startActivity(intent);

            }
        });


        UserImgBtn2=findViewById(R.id.UserImgBtn2);
        UserImgBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(userListActivity.this, userAlterActivity.class);//当点击详情页时触发事件函数完成页面跳转
                startActivity(intent);

            }
        });




    }







}
