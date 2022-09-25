package com.selftutor.besafetogether.screens

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

open class BaseFragment: Fragment() {

    fun showToast(messageRes: Int){
        Toast.makeText(context, getString(messageRes), Toast.LENGTH_SHORT).show()
    }

    fun showToast(messageRes: String){
        Toast.makeText(context, messageRes, Toast.LENGTH_SHORT).show()
    }

    fun navigate(id: Int){
        findNavController().navigate(id)
    }
}