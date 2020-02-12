package edu.towson.cosc435.bajgai.todos_madhav

import android.app.Activity
import android.app.Application
import android.os.Bundle

class MyApplication : Application(), Application.ActivityLifecycleCallbacks {

    var isMainActivityVisible: Boolean = false
    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityPaused(act: Activity?) {
        if(act is MainActivity) {
            isMainActivityVisible = false
        }
    }

    override fun onActivityResumed(act: Activity?) {
        if(act is MainActivity) {
            isMainActivityVisible = true
        }
    }

    override fun onActivityStarted(p0: Activity?) {
    }

    override fun onActivityDestroyed(p0: Activity?) {
    }

    override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) {
    }

    override fun onActivityStopped(p0: Activity?) {
    }

    override fun onActivityCreated(p0: Activity?, p1: Bundle?) {
    }
}