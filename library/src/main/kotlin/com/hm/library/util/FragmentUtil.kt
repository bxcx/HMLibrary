package com.hm.library.util

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.hm.library.R


object FragmentUtil {

    fun add(fragmentManager: FragmentManager, container: Int, fragment: Fragment, showAnim: Boolean,
            canBack: Boolean) {
        try {
            val transaction = fragmentManager.beginTransaction()
            if (showAnim)
                transaction.setCustomAnimations(R.anim.push_left_in, R.anim.push_right_out, R.anim.push_left_in,
                        R.anim.push_right_out)
            transaction.add(container, fragment)
            if (canBack)
                transaction.addToBackStack(null)
            transaction.commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun replace(fragmentManager: FragmentManager, container: Int, fragment: Fragment, showAnim: Boolean,
                canBack: Boolean) {
        try {
            val transaction = fragmentManager.beginTransaction()
            if (showAnim)
                transaction.setCustomAnimations(R.anim.push_left_in, R.anim.push_right_out, R.anim.push_left_in,
                        R.anim.push_right_out)
            transaction.replace(container, fragment)
            if (canBack)
                transaction.addToBackStack(null)
            transaction.commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}
