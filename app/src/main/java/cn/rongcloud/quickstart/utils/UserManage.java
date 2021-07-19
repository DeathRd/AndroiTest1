package cn.rongcloud.quickstart.utils;



import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import cn.rongcloud.quickstart.entity.UserInfo;


/**
 * 保存用户信息的管理类
 * Created by libin
 */

public class UserManage {

    private static UserManage instance;

    private UserManage() {
    }

    public static UserManage getInstance() {
        if (instance == null) {
            instance = new UserManage();
        }
        return instance;
    }


    /**
     * 保存自动登录的用户信息
     */
    public void saveUserInfo(Context context,int userId, String username, String password,String nikename,int sex,int age, String email, int user_type,String user_head, String token,int login_failed_count, int del_flag,String creat_time) {
        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);//Context.MODE_PRIVATE表示SharePrefences的数据只有自己应用程序能访问。
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("id", userId);
        editor.putString("USER_NAME", username);
        editor.putString("PASSWORD", password);
        editor.putString("nikename", nikename);
        editor.putInt("sex", sex);
        editor.putInt("age", age);
        editor.putString("email", email);
        editor.putInt("user_type", user_type);
        editor.putString("8", user_head);
        editor.putString("token", token);
        editor.putInt("login_failed_count", login_failed_count);
        editor.putInt("del_flag", del_flag);
        editor.putString("creat_time", creat_time);
        editor.commit();
    }


    /**
     * 获取用户信息model
     *
     * @param context
     * @param
     * @param
     */
    public UserInfo getUserInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(sp.getString("USER_NAME", ""));
        userInfo.setPassword(sp.getString("PASSWORD", ""));
        return userInfo;
    }


    /**
     * userInfo中是否有数据
     */
    public boolean hasUserInfo(Context context) {
        UserInfo userInfo = getUserInfo(context);
        if (userInfo != null) {
            if ((!TextUtils.isEmpty(userInfo.getUserName())) && (!TextUtils.isEmpty(userInfo.getPassword()))) {//有数据
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 删除UserInfo的数据
     */
    public void delUserInfo(Context context){
        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        sp.edit().clear().commit();

    }





}