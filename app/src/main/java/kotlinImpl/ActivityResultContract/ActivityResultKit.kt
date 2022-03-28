package kotlinImpl.ActivityResultContract

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.*
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityOptionsCompat
import com.luck.picture.lib.permissions.PermissionConfig

/**
 * launchActivityImpl
 */
class ActivityResultKit {
    companion object {
        private val TAG = "ActivityResultLauncher"

        /**
         * launch Activity
         * @param resultAction onActivityResult callback
         */
        fun launch(
            activityResultCaller: ActivityResultCaller,
            resultAction: Result? = null,
        ): ActivityResultLauncher<Intent> {
            return activityResultCaller.registerForActivityResult(
                ActivityResultContracts.StartActivityForResult(),
                object : ActivityResultCallback<ActivityResult?> {
                    override fun onActivityResult(result: ActivityResult?) {
                        Log.i(TAG, "onActivityResult")
                        result ?: return
                        val resultCode = result.resultCode
                        if (resultCode == Activity.RESULT_OK) {
                            resultAction?.onActivity(result.data ?: return, true)
                        } else if (resultCode == Activity.RESULT_CANCELED) {
                            resultAction?.onActivity(result.data ?: return, false)

                        }
                    }
                })

        }

        interface Result {
            fun onActivity(intent: Intent, success: Boolean)
        }


        /**
         * 请求单个权限
         */
        fun checkPermission(activityResultCaller: ActivityResultCaller, permission: String, resultAction: (Boolean)-> Unit){
            activityResultCaller.registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                resultAction(it)
            }.launch(permission)
        }

        /**
         * 请求多个权限
         */
        fun checkPermission(activityResultCaller: ActivityResultCaller, array: Array<String>){
            activityResultCaller.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {

            }.launch(array)
        }

    }


}