package kotlinImpl.activityResultContract

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.result.*
import androidx.activity.result.contract.ActivityResultContracts

/**
 * launchActivityImpl
 */
class ActivityResultKit {
    companion object {
        private const val TAG = "ActivityResultLauncher"
        /**
         * startActivity for result
         */
        private val mStartActivityForResult by lazy { ActivityResultContracts.StartActivityForResult() }

        private var mActivityResultLauncher : ActivityResultLauncher<Intent>? = null

        private lateinit var callback: ActivityResultCallback<ActivityResult>


        fun regisStartActivityForResultContract(activityResultCaller: ActivityResultCaller) {
            mActivityResultLauncher =
                activityResultCaller.registerForActivityResult(mStartActivityForResult, callback)
        }

        fun startActivityForResult(intent: Intent?, callBack:ActivityResultCallback<ActivityResult>){
            callback = callBack
            mActivityResultLauncher?.launch(intent?: return)
        }

        /**
         * launch Activity
         * @param resultAction onActivityResult callback
         */
        fun launch(
            activityResultCaller: ActivityResultCaller,
            resultAction: Result? = null,
        ): ActivityResultLauncher<Intent> {
            return activityResultCaller.registerForActivityResult(
                mStartActivityForResult,
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
        fun checkPermission(activityResultCaller: ActivityResultCaller, array: Array<String>, resultAction: ((Boolean) -> Unit)? = null){
            activityResultCaller.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                it.forEach { entry ->
                    if (!entry.value) {
                        //no grant
                        resultAction?.invoke(false)
                        Log.i(TAG, "checkPermission: no grant")
                        return@forEach
                    }
                }
                resultAction?.invoke(true)
                Log.i(TAG, "checkPermission: all grant")
            }.launch(array)
        }

        /**
         * request storage permission
         */
        fun checkStoragePermission(activityResultCaller: ActivityResultCaller){
            checkPermission(activityResultCaller, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE))
        }


    }


}