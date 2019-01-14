package com.storiqa.storiqawallet.ui.base.navigator

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

open class Navigator(protected val activity: FragmentActivity) : INavigator {

    override fun finishActivity() {
        activity.finish()
    }

    override fun finishActivityWithResult(resultCode: Int, resultIntentFun: (Intent.() -> Unit)?) {
        val intent = resultIntentFun?.let { Intent().apply(it) }
        activity.setResult(resultCode, intent)
        activity.finish()
    }

    override fun finishAffinity() {
        activity.finishAffinity()
    }

    override fun startActivity(intent: Intent) {
        activity.startActivity(intent)
    }

    override fun startActivity(action: String, uri: Uri?) {
        activity.startActivity(Intent(action, uri))
    }

    override fun startActivity(activityClass: Class<out Activity>, adaptIntentFun: (Intent.() -> Unit)?) {
        startActivityInternal(activityClass, null, adaptIntentFun)
    }

    override fun startActivity(clsActivity: Class<out Activity>, vararg flags: Int) {
        val intent = Intent(activity, clsActivity)
        flags.forEach { intent.addFlags(it) }
        startActivity(intent)
    }

    override fun startActivity(clsActivity: Class<out Activity>, bundle: Bundle) {
        val intent = Intent(activity, clsActivity)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun startActivityForResult(activityClass: Class<out Activity>, requestCode: Int, adaptIntentFun: (Intent.() -> Unit)?) {
        startActivityInternal(activityClass, requestCode, adaptIntentFun)
    }

    private fun startActivityInternal(activityClass: Class<out Activity>, requestCode: Int?, adaptIntentFun: (Intent.() -> Unit)?) {
        val intent = Intent(activity, activityClass)
        adaptIntentFun?.invoke(intent)

        if (requestCode != null) {
            activity.startActivityForResult(intent, requestCode)
        } else {
            activity.startActivity(intent)
        }
    }

    override fun replaceFragment(@IdRes containerId: Int, fragment: Fragment, fragmentTag: String?) {
        replaceFragmentInternal(activity.supportFragmentManager, containerId, fragment, fragmentTag, false, null)
    }

    override fun replaceFragmentAndAddToBackStack(@IdRes containerId: Int, fragment: Fragment, fragmentTag: String?, backstackTag: String?) {
        replaceFragmentInternal(activity.supportFragmentManager, containerId, fragment, fragmentTag, true, backstackTag)
    }

    protected fun replaceFragmentInternal(fm: FragmentManager, @IdRes containerId: Int, fragment: Fragment, fragmentTag: String?, addToBackstack: Boolean, backstackTag: String?) {
        val ft = fm.beginTransaction().replace(containerId, fragment, fragmentTag)
        if (addToBackstack) {
            ft.addToBackStack(backstackTag).commit()
            fm.executePendingTransactions()
        } else {
            ft.commitNow()
        }
    }

    override fun <T : DialogFragment> showDialogFragment(dialog: T, fragmentTag: String) {
        dialog.show(activity.supportFragmentManager, fragmentTag)
    }

    override fun popFragmentBackStackImmediate() {
        activity.supportFragmentManager.popBackStackImmediate()
    }
}