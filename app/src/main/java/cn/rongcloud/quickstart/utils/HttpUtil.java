package cn.rongcloud.quickstart.utils;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;

public class HttpUtil {
    private static final String SERVER = "http://120.79.93.22:8080/";
    public static String sessionID = "";
    private static String TAG = "HttpUtil";

    /**
     * get方式获取字符内容
     * @param api 访问地址
     * @return
     */
    public static String get(String api)
    {
        HttpURLConnection conn = null;
        URL url = null;
        BufferedReader br = null;
        BufferedWriter bw = null;

        String res = "";//返回数据
        try {
            url = new URL(SERVER + api);
            conn = (HttpURLConnection)url.openConnection();

            conn.setRequestMethod("GET");

            if (!sessionID.equals(""))
            {
                conn.setRequestProperty("Cookie",sessionID);
            }

            //获取响应码并做内容判断
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String temp = "";
                while((temp = br.readLine()).length() != 0)
                {
                    res += temp;
                }
                Log.d(TAG, "res:"+res);
            }

            //保存Cookie
            if (!"".equals(conn.getHeaderField("Set-Cookie")))
            {
                Log.d(TAG, "Cookie:"+conn.getHeaderField("Set-Cookie"));
                sessionID = conn.getHeaderField("Set-Cookie").split(";")[0];
                Log.d(TAG, "sessionID:"+sessionID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //释放资源
            conn.disconnect();
            try {
                if (bw != null)
                {
                    bw.close();
                }
                if (br != null)
                {
                    br.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    /**
     * post方法获取字符内容
     * @param api
     * @param params
     * @return
     */
    public static String post(String api,String params)
    {
        HttpURLConnection conn = null;
        URL url = null;
        BufferedReader br = null;
        BufferedWriter bw = null;

        String res = "";//返回数据
        try {
            url = new URL(SERVER + api);
            conn = (HttpURLConnection)url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);

            if (!sessionID.equals(""))
            {
                conn.setRequestProperty("Cookie",sessionID);
            }

            //写入参数
            Log.e("WangJ",params);
            if (!TextUtils.isEmpty(params))
            {
                bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
                bw.write(params);
                bw.flush();
                Log.e("WangJ",url.toString());
            }

            //获取响应码并做内容判断
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String temp = "";
                while((temp = br.readLine())!= null)
                {
                    res += temp;
                }
                Log.e(TAG, "res:"+res);
            }

//            //保存Cookie
//            if (TextUtils.isEmpty(sessionID) && "".equals(conn.getHeaderField("Set-Cookie")))
//            {
//                Log.d(TAG, "Cookie:"+conn.getHeaderField("Set-Cookie"));
//                sessionID = conn.getHeaderField("Set-Cookie").split(";")[0];
//                Log.d(TAG, "sessionID:"+sessionID);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //释放资源
            conn.disconnect();

            try {
                if (bw != null)
                {
                    bw.close();
                }
                if (br != null)
                {
                    br.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }


    public static String postJson(String api,JSONObject params) throws IOException, JSONException {
        String res="";


        String urlPath =SERVER + api;
        try {
            URL url = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // 设置允许输出
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            // 设置contentType
            conn.setRequestProperty("Content-Type", "application/json");


//            conn.setRequestProperty("Host", "api-cn.ronghub.com");
//            conn.setRequestProperty("App-Key", "uwd1c0sxdlx2");
//            String App_Secret = "hivjWvDVb8";
//            String Timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳，从 1970 年 1 月 1 日 0 点 0 分 0 秒开始到现在的秒数。
//            String Nonce = String.valueOf(Math.floor(Math.random() * 1000000));//随机数，无长度限制。
//            String Signature = getSha1(App_Secret + Nonce + Timestamp);//数据签名。
//            conn.setRequestProperty("Nonce", Nonce);
//            conn.setRequestProperty("Timestamp", Timestamp);
//            conn.setRequestProperty("Signature", Signature);


            DataOutputStream os = new DataOutputStream( conn.getOutputStream());
            String content = String.valueOf(params);
            os.writeBytes(content);
            os.flush();
            os.close();
            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader in = new InputStreamReader(conn.getInputStream());
                BufferedReader bf = new BufferedReader(in);
                String recieveData = null;

                while ((recieveData = bf.readLine()) != null){
                    res += recieveData + "\n";
                }
                in.close();
                conn.disconnect();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }








    /**
     * 访问接口获取图片
     * @param api
     * @param params
     * @return
     */
    public Bitmap postImage(String api,String params)
    {
        HttpURLConnection conn = null;
        URL url = null;
        BufferedWriter bw = null;
        Bitmap bitmap = null;

        String res = "";//返回数据
        try {
            url = new URL(SERVER + api);
            conn = (HttpURLConnection)url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            if (!sessionID.equals(""))
            {
                conn.setRequestProperty("Cookie",sessionID);
            }

            //写入参数
            if (!TextUtils.isEmpty(params))
            {
                bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
                bw.write(params);
                bw.flush();
            }

            //获取响应码并做内容判断
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                //接收图片
                bitmap = BitmapFactory.decodeStream(conn.getInputStream());
                Log.d(TAG, "res:OK");
            }

            //保存Cookie
            if (!"".equals(conn.getHeaderField("Set-Cookie")))
            {
                Log.d(TAG, "Cookie:"+conn.getHeaderField("Set-Cookie"));
                sessionID = conn.getHeaderField("Set-Cookie").split(";")[0];
                Log.d(TAG, "sessionID:"+sessionID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //释放资源
            conn.disconnect();
            try {
                if (bw != null)
                {
                    bw.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    /**
     * get 方法获取图片
     * @param api
     * @return
     */
    public static Bitmap getImage(String api)
    {
        HttpURLConnection conn = null;
        URL url = null;
        Bitmap bitmap = null;

        String res = "";//返回数据
        try {
            url = new URL(SERVER + api);
            conn = (HttpURLConnection)url.openConnection();

            conn.setRequestMethod("GET");

            if (!sessionID.equals(""))
            {
                conn.setRequestProperty("Cookie",sessionID);
            }

            //获取响应码并做内容判断
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                //接收图片
                bitmap = BitmapFactory.decodeStream(conn.getInputStream());
                Log.d(TAG, "res:OK");
            }

            //保存Cookie
            if (!"".equals(conn.getHeaderField("Set-Cookie")))
            {
                Log.d(TAG, "Cookie:"+conn.getHeaderField("Set-Cookie"));
                sessionID = conn.getHeaderField("Set-Cookie").split(";")[0];
                Log.d(TAG, "sessionID:"+sessionID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //释放资源
            conn.disconnect();
        }
        return bitmap;
    }




    public static String getSha1(String str){
        if(str==null||str.length()==0){
            return null;
        }
        char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9',
                'a','b','c','d','e','f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j*2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }

    }





}
