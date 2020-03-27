package com.vv.gilde;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.FragmentActivity;

/**
 * 管理 RequestManager
 */
public class RequestManagerRetriver {

    public RequestManager get(FragmentActivity fragmentActivity) { // this
        return new RequestManager(fragmentActivity);
    }

    public RequestManager get(Activity activity) {
        return new RequestManager(activity);
    }

    public RequestManager get(Context context) {
        return new RequestManager(context);
    }

}
