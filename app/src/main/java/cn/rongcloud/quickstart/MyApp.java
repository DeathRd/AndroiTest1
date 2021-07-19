package cn.rongcloud.quickstart;

import android.app.Application;
import io.rong.imlib.RongIMClient;

public class MyApp extends Application {



   public static final String appkey = "sfci50a7sjl7i";
  /**
   * TODO: 请替换成您自己 AppKey 对应的 Secret
   * 这里仅用于模拟从 App Server 获取 UserID 对应的 Token, 开发者在上线应用时客户端代码不要存储该 Secret，
   * 否则有被用户反编译获取的风险，拥有 Secret 可以向融云 Server 请求高级权限操作，对应用安全造成恶劣影响。
   */



  @Override
  public void onCreate() {
    super.onCreate();
    RongIMClient.init(this, appkey, false);
  }


}
