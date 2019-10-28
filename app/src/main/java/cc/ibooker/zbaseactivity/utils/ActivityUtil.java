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
    private List<Activity> activityList;
    private static ActivityUtil instance;

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
        if (!isContains(activity))// 没有包含直接添加
            activityList.add(activity);
        else {// 包含-Activity置顶
            activityList.remove(activity);
            activityList.add(activity);
        }
    }

    // 移除Activity
    public synchronized void removeActivity(Activity activity) {
        if (activityList != null)
            activityList.remove(activity);
    }

    // 保存当前Activity移除其他Activity
    public synchronized void removeAllActivityKeepCurrent(Activity activity) {
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
    }

    // 遍历所有Activity并finish
    public synchronized void exitSystem() {
        for (Activity activity : activityList) {
            if (activity != null)
                activity.finish();
        }
        System.gc();
        Process.killProcess(Process.myPid());
        System.exit(0);
    }

    // 当前Activity
    public synchronized Activity currentActivity() {
        if (activityList == null || activityList.size() <= 0)
            return null;
        else
            return activityList.get(activityList.size() - 1);
    }

    // 获取 保存的Activity数量
    public synchronized int getActivitiesNumber() {
        if (activityList == null)
            activityList = new ArrayList<>();
        return activityList.size();
    }

    // 是否包含某个Activity
    public synchronized boolean isContains(Activity activity) {
        boolean bool = false;
        if (activity != null && activityList != null) {
            for (Activity activity1 : activityList) {
                if (activity1 != null &&
                        activity.getComponentName().getClassName().equals(
                                activity1.getComponentName().getClassName())) {
                    bool = true;
                    break;
                }
            }
        }
        return bool;
    }
}
