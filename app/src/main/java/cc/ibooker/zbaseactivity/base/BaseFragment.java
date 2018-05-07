package cc.ibooker.zbaseactivity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import cc.ibooker.zbaseactivity.broadcastreceiver.NetBroadcastReceiver;

/**
 * BaseFragment是所有Fragment的基类，把一些公共的方法放到里面，如基础样式设置，权限封装，网络状态监听等
 * <p>
 * Created by 邹峰立 on 2017/10/19.
 */
public abstract class BaseFragment extends Fragment implements NetBroadcastReceiver.NetChangeListener {
    public static NetBroadcastReceiver.NetChangeListener netEvent;// 网络状态改变监听事件

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化netEvent
        netEvent = this;

        // 初始化方法
        init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        netEvent = null;
    }

    // 抽象 - 初始化方法，可以对控件进行初始化，也可以对数据进行初始化
    protected abstract void init();

    /**
     * 网络状态改变时间监听
     *
     * @param netWorkState true有网络，false无网络
     */
    @Override
    public void onNetChange(boolean netWorkState) {
    }
}
