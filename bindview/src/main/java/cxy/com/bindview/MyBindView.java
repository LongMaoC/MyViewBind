package cxy.com.bindview;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Constructor;

/**
 * Created by CXY on 2016/11/4.
 */

public class MyBindView {

    public static void bind(Activity activity) {
        String generateClass = activity.getClass().getName() + "_BindView";
        try {
            View view = activity.getWindow().getDecorView();
            Class clazz = Class.forName(generateClass);
            Constructor constructor = clazz.getConstructor(activity.getClass(), View.class);
            constructor.newInstance(activity, view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
