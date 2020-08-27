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
    private volatile Stack<Activity> activityStack;
    private static ActivityUtil instance;
    private boolean removeAllKeepALock = false;

    // 单例模式
    public static ActivityUtil getInstance() {
        if (null == instance) {
            synchronized (ActivityUtil.class) {
                instance = new ActivityUtil();
            }
        }
        return instance;
    }

    // 初始化activityStack
    private Stack<Activity> initActivityStack() {
        if (activityStack == null)
            activityStack = new Stack<>();
        return activityStack;
    }

    // 添加Activity到容器中
    public synchronized void addActivity(Activity activity) {
        initActivityStack().add(activity);
    }

    // 移除Activity
    public synchronized void removeActivity(Activity activity) {
        if (activityStack != null)
            activityStack.remove(activity);
    }

    // 保存Activity不变移除其他Activity
    public synchronized void removeAllKeepA(Activity activity) {
        if (!removeAllKeepALock && activityStack != null && activityStack.size() > 0) {
            removeAllKeepALock = true;
            Iterator<Activity> iterator = activityStack.iterator();
            while (iterator.hasNext()) {
                Activity activity1 = iterator.next();
                if (activity1 != null
                        && activity != null
                        && !activity.getComponentName().getClassName().equals(
                        activity1.getComponentName().getClassName())
                        && !activity.equals(activity1)) {
                    activity1.finish();
                    iterator.remove();
                }
            }
            removeAllKeepALock = false;
        }
    }

    // 遍历所有Activity并finish
    public void exitSystem() {
        for (Activity activity : activityStack) {
            if (activity != null)
                activity.finish();
        }
        System.gc();
        Process.killProcess(Process.myPid());
        System.exit(0);
    }

    /**
     * 当前Activity
     */
    public Activity currentActivity() {
        if (activityStack == null)
            activityStack = initActivityStack();
        return activityStack.lastElement();
    }

    /**
     * 获取保存的Activity数量
     */
    public int getActivitySize() {
        return initActivityStack().size();
    }
}

