package cn.rongcloud.quickstart.utils;





import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class UIUtil {
    /**
     * 弹出确认取消消息弹窗
     * @param context 上下文
     * @param msg 提示信息
     * @param sureAction 确定按钮事件
     * @param cancelAction 取消按钮事件
     */
    public static void alertSureMessage(
            Context context,
            String msg,
            DialogInterface.OnClickListener sureAction,
            DialogInterface.OnClickListener cancelAction
    )
    {
        new AlertDialog.Builder(context)
                .setMessage(msg)
                .setPositiveButton("确定", sureAction)
                .setNegativeButton("取消",cancelAction)
                .show();
    }

    /**
     * 弹出确认消息弹窗
     * @param context 上下文
     * @param msg 提示信息
     * @param sureAction 确定按钮事件
     */
    public static void alertMessage(
            Context context,
            String msg,
            DialogInterface.OnClickListener sureAction
    )
    {
        new AlertDialog.Builder(context)
                .setMessage(msg)
                .setPositiveButton("确定", sureAction)
                .show();
    }
}
