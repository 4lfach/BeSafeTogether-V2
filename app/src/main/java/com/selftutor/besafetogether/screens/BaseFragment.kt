package com.selftutor.besafetogether.screens

import android.widget.Toast
import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {

    fun showToast(messageRes: Int){
        Toast.makeText(context, getString(messageRes), Toast.LENGTH_SHORT).show()
    }
}