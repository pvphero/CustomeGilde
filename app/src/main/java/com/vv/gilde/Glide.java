package com.vv.gilde;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.FragmentActivity;

public class Glide {

    RequestManagerRetriver retriver;

    public Glide(RequestManagerRetriver retriver) {
        this.retriver = retriver;
    }

    public static RequestManager with(FragmentActivity fragmentActivity) {
        return getRetriver(fragmentActivity).get(fragmentActivity);
    }

    public static RequestManager with(Activity activity) {
        return getRetriver(activity).get(activity);
    }

    public static RequestManager with(Context context) {
        return getRetriver(context).get(context);
    }

    // Glide 转变的开始
    private static RequestManagerRetriver getRetriver(Context context) {
        return Glide.get(context).getRetriver();
    }

    // Glide 是 new 出来的 -- > 转变
    private static Glide get(Context context) {
        return new GlideBuilder(context).build();
    }

    /**
     * TODO 下面都是具体
     */

    public RequestManagerRetriver getRetriver() {
        return retriver;
    }

    public void test() {

    }

    public void test3() {

    }

    public void test2() {

    }

    // .....

}
