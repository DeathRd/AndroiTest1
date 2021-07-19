package cn.rongcloud.quickstart.homepage;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;


import com.rtcdemo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.rongcloud.quickstart.userpage.userListActivity;
import cn.rongcloud.quickstart.utils.HttpUtil;
import io.rong.imlib.RongIMClient;


public class homeListActivity extends AppCompatActivity {



    //SharedPreferences sp = homeListActivity.this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
    public int id=0;



    //跳转到其他mod的页面
    public static void start(Context context) {
        Intent intent = new Intent(context, homeListActivity.class);
        context.startActivity(intent);
    }

    ImageButton userBtn,createMeetingBtn,joinMeetingBtn;
    RecyclerView rv_list;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_home);



        SharedPreferences sp = homeListActivity.this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        this.id=sp.getInt("id",0);
        String token=sp.getString("token","");

        //连接融云
        connectIMServer(token);






        userBtn=findViewById(R.id.userBtn);

        //用户界面按钮绑定
        userBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homeListActivity.this, userListActivity.class);//当点击详情页时触发事件函数完成页面跳转
                startActivity(intent);
                finish();
            }
        });



        //创建会议 按钮绑定
        createMeetingBtn=findViewById(R.id.createMeetingBtn);
        createMeetingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(homeListActivity.this, createMeetingActivity.class);
                startActivity(intent);
                finish();
            }
        });


        //加入会议 按钮绑定
        joinMeetingBtn=findViewById(R.id.joinMeetingBtn);
        joinMeetingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(homeListActivity.this, joinMeetingActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //添加adapter到界面


        List<HomeListAdapter.HomeListItemModel> modelList = getHomeList();
        HomeListAdapter adapter = new HomeListAdapter(modelList);

        rv_list= findViewById(R.id.rv_list);
        rv_list.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager (this, LinearLayoutManager.VERTICAL ,false);
        rv_list.setLayoutManager(linearLayoutManager);
    }

    /**
     *
     * 更新集合
     */

//初始化rv集合内容

    //private final
    //初始化会议集合到Adapter
    //private final









    //获取新的HomeList

    public List<HomeListAdapter.HomeListItemModel> getHomeList(){
        List<HomeListAdapter.HomeListItemModel> listItemModels=new ArrayList<>();
        //id=sp.getInt("id", 0);
        new Thread(()->{
            Log.e("id", String.valueOf(id));
            String res = HttpUtil.post("meetingUser/meetingList", "user_id="+String.valueOf(id));
            Log.e("id22222", String.valueOf(id));
//            Log.d("Demo",deptStr);
            try {
                JSONObject ja = new  JSONObject(res);

                String msg=ja.getString("msg");
                int code=ja.getInt("code");
                if(code==1){

                    JSONArray dataJ = ja.getJSONArray("data");

                    for (int i = 0; i < dataJ.length();i++)
                    {
                        JSONObject jo = dataJ.getJSONObject(i);
                        HomeListAdapter.HomeListItemModel homeListItemModel=new HomeListAdapter.HomeListItemModel();
                        String meetingNameText=jo.getString("meeting_name");
                        String meetingIdText= String.valueOf(jo.getInt("meeting_id"));
                        String meetingCreatorText=jo.getString("username");
                        String meetingIntroText=jo.getString("meeting_introduce");
                        String meetingStartTime=jo.getString("meeting_start_time");
                        String meetingEndTime=jo.getString("meeting_over_time");

                        homeListItemModel.index=i;
                        homeListItemModel.title=jo.getString("meeting_name");
                        homeListItemModel.desc=jo.getString("meeting_start_time");
                        homeListItemModel.icon= String.valueOf(jo.getInt("meeting_id"));
                        homeListItemModel.clickListener=new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent =new Intent(homeListActivity.this, detailsMeetingActivity.class);
                                Bundle bundle = new Bundle();// 创建Bundle对象
                                bundle.putString("meetingNameText", meetingNameText);
                                bundle.putString("meetingIdText",meetingIdText);
                                bundle.putString("meetingCreatorText",meetingCreatorText);
                                bundle.putString("meetingIntroText", meetingIntroText);
                                bundle.putString("meetingStartTime",meetingStartTime );
                                bundle.putString("meetingEndTime", meetingEndTime);
                                intent.putExtras(bundle);// 将Bundle对象放入到Intent上
                                startActivity(intent);
                                finish();

                            }
                        };

                        listItemModels.add(homeListItemModel);
                    }


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }).start();




        return listItemModels;

    }




    private void connectIMServer(String token) {
        // 关键步骤 2：使用从 App Server 获取的代表 UserID 身份的 Token 字符串，连接融云 IM 服务。
        RongIMClient.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onSuccess(String s) {
                Log.e("连接融云","成功");
                return;
            }

            @Override
            public void onError(RongIMClient.ConnectionErrorCode code) {
                Log.e("连接融云","成功");
                return;
            }

            @Override
            public void onDatabaseOpened(RongIMClient.DatabaseOpenStatus databaseOpenStatus) {
                Log.e("连接融云","打开数据库");
                return;
            }
        });
    }




}
