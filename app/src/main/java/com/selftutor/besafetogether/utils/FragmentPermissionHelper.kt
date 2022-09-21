package com.selftutor.besafetogether.utils

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity

class FragmentPermissionHelper {

    fun startPermissionRequest(fr: FragmentActivity, REQUESTED_PERMISSION: String,fp: FragmentPermission){
        val requestPermissionLauncher : ActivityResultLauncher<String> =
            fr.registerForActivityResult(
                ActivityResultContracts.RequestPermission(), fp::onGranted)

        requestPermissionLauncher.launch(
            REQUESTED_PERMISSION)
    }
    companion object{
        fun getPermissionLauncher(fr: FragmentActivity): ActivityResultLauncher<Array<String>> {
            val requestMultiplePermissionsLauncher =
                fr.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                    permissions.entries.forEach {

                    }
                }

            return requestMultiplePermissionsLauncher
        }
    }
}