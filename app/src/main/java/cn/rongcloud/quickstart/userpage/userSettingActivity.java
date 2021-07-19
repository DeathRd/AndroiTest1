package cn.rongcloud.quickstart.userpage;



import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.rtcdemo.R;

import cn.rongcloud.quickstart.loginpage.LoginActivity;
import cn.rongcloud.quickstart.utils.UserManage;


public class userSettingActivity extends AppCompatActivity {

    ImageButton exitImgBtn,registerBackBtn;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_user);




        /**
         * 返回用户页面
         */
        registerBackBtn=findViewById(R.id.registerBackBtn);
        registerBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });






        /**
         *
         * 退出事件
         */
        exitImgBtn=findViewById(R.id.exitImgBtn);
        exitImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserManage.getInstance().delUserInfo(userSettingActivity.this);
                LoginActivity.start(userSettingActivity.this);
                finish();
                userListActivity.instance.finish();
            }
        });



    }




}
