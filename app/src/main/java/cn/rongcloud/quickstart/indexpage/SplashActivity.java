package cn.rongcloud.quickstart.indexpage;




import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.rtcdemo.R;

import cn.rongcloud.quickstart.entity.UserInfo;
import cn.rongcloud.quickstart.homepage.homeListActivity;
import cn.rongcloud.quickstart.loginpage.LoginActivity;
import cn.rongcloud.quickstart.utils.UserManage;
import io.rong.imlib.RongIMClient;


/**
 * 启动页，app刚打开时的activity
 * create by linbin
 */
public class SplashActivity extends Activity {

    private static final int GO_HOME = 0;//去主页
    private static final int GO_LOGIN = 1;//去登录页
    /**
     * 跳转判断
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_HOME://去主页
                    Log.e("run","主页");


                    homeListActivity.start(SplashActivity.this);

                    finish();
                    break;
                case GO_LOGIN://去登录页
                    Log.e("run","登录");
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);

                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (UserManage.getInstance().hasUserInfo(this))//自动登录判断，SharePrefences中有数据，则跳转到主页，没数据则跳转到登录页
        {
            SharedPreferences sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            UserInfo userInfo = new UserInfo();

            userInfo.setPassword(sp.getString("PASSWORD", ""));
            Log.e("valueUserName",sp.getString("USER_NAME", ""));
            Log.e("token",sp.getString("token",""));
            String token=sp.getString("token","");
            connect(token);


            mHandler.sendEmptyMessageDelayed(GO_HOME, 2000);
        } else {
            mHandler.sendEmptyMessageAtTime(GO_LOGIN, 2000);
        }






    }

    private void connect(String token) {
        if (RongIMClient.getInstance().getCurrentConnectionStatus() == RongIMClient.ConnectionStatusListener.ConnectionStatus.CONNECTED) {

            return;
        }
        RongIMClient.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onSuccess(String userid) {


            }

            @Override
            public void onError(RongIMClient.ConnectionErrorCode connectionErrorCode) {

            }

            @Override
            public void onDatabaseOpened(RongIMClient.DatabaseOpenStatus databaseOpenStatus) {

            }
        });
    }


}