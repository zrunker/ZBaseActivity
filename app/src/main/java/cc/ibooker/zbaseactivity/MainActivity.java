package cc.ibooker.zbaseactivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import cc.ibooker.zbaseactivity.base.BaseActivity;
import cc.ibooker.zbaseactivity.utils.ActivityUtil;
import cc.ibooker.zbaseactivity.utils.ConstantUtil;

public class MainActivity extends BaseActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    // 初始化控件
    private void initView() {
        textView = findViewById(R.id.text);
    }

    @Override
    protected void init() {
        // 判断权限
        if (!hasPermission(Manifest.permission.READ_PHONE_STATE)) {
            requestPermission(ConstantUtil.PERMISSIONS_REQUEST_READ_PHONE_STATE, Manifest.permission.READ_PHONE_STATE);
        }
    }

    // 处理请求权限结果
    @Override
    public void doRequestPermissionsResult(int requestCode, @NonNull int[] grantResults) {
        switch (requestCode) {
            case ConstantUtil.PERMISSIONS_REQUEST_READ_PHONE_STATE:// 读取手机信息权限
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限请求成功
                    Toast.makeText(this, "权限请求成功", Toast.LENGTH_SHORT).show();
                } else {
                    // 权限请求失败
                    Toast.makeText(this, "权限请求失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    // 处理网络状态结果
    @Override
    public void onNetChange(boolean netWorkState) {
        super.onNetChange(netWorkState);
        if (textView != null)
            textView.setText(netWorkState ? "有网络" : "无网络");
    }

    // 设置返回按钮的监听事件
    private long exitTime = 0;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 监听返回键，点击两次退出程序
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 5000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_LONG).show();
                exitTime = System.currentTimeMillis();
            } else {
                ActivityUtil.getInstance().exitSystem();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
