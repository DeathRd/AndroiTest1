package cn.rongcloud.quickstart.entity;



/**
 * 用户信息model
 * craeate by libin
 */

public class UserInfo {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;


    //个人信息
    private String nikename;
    private int sex;
    private int age;
    private String email;
    private int user_type;
    private String user_head;
    private String token;
    private int login_failed_count;
    private int del_flag;
    private String creat_time;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



    public String getNikename() {
        return nikename;
    }

    public void setNikename(String nikename) {
        this.nikename = nikename;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public String getUser_head() {
        return user_head;
    }

    public void setUser_head(String user_head) {
        this.user_head = user_head;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getLogin_failed_count() {
        return login_failed_count;
    }

    public void setLogin_failed_count(int login_failed_count) {
        this.login_failed_count = login_failed_count;
    }

    public int getDel_flag() {
        return del_flag;
    }

    public void setDel_flag(int del_flag) {
        this.del_flag = del_flag;
    }

    public String getCreat_time() {
        return creat_time;
    }

    public void setCreat_time(String creat_time) {
        this.creat_time = creat_time;
    }
}
