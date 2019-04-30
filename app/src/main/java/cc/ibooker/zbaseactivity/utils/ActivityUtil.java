package cc.ibooker.zbaseactivity.utils;

import android.app.Activity;
import android.os.Process;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Activity工具类
 *
 * @author 邹峰立
 */
public class ActivityUtil {
    private List<Activity> activityList = new ArrayList<>();
    private static ActivityUtil instance;
    private boolean lock = false;

    // 单例模式
    public static ActivityUtil getInstance() {
        if (null == instance) {
            synchronized (ActivityUtil.class) {
                instance = new ActivityUtil();
            }
        }
        return instance;
    }

    // 添加Activity到容器中
    public synchronized void addActivity(Activity activity) {
        if (activityList == null)
            activityList = new ArrayList<>();
        activityList.add(activity);
    }

    // 移除Activity
    public synchronized void removeActivity(Activity activity) {
        if (activityList != null)
            activityList.remove(activity);
    }

    // 保存Activity不变移除其他Activity
    public void removeActivitysKeepA(Activity activity) {
        if (!lock) {
            lock = true;
            Iterator<Activity> iterator = activityList.iterator();
            while (iterator.hasNext()) {
                Activity activity1 = iterator.next();
                if (activity1 != null
                        && activity != null
                        && !activity.getComponentName().getClassName().equals(
                        activity1.getComponentName().getClassName())) {
                    activity1.finish();
                    iterator.remove();
                }
            }
            lock = false;
        }
    }

    // 遍历所有Activity并finish
    public void exitSystem() {
        for (Activity activity : activityList) {
            if (activity != null)
                activity.finish();
        }
        System.gc();
        android.os.Process.killProcess(Process.myPid());
        System.exit(0);
    }

}
