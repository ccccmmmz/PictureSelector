package kotlinImpl.activityResultContract

import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class ActivityResultContractCompat {


    private val TAG = ActivityResultContractCompat::class.simpleName

    private val mLogEnable = true

    private val mStartActivityForResult by lazy { ActivityResultContracts.StartActivityForResult() }

    private var mActivityResultLauncher : ActivityResultLauncher<Intent>? = null

    private var callback: ActivityResultCallback<ActivityResult>? = null

    fun regisStartActivityForResultContract(activityResultCaller: ActivityResultCaller) {
        if (callback == null) {
            log("regisStartActivityForResultContract return because callback = null")
            return
        }
        mActivityResultLauncher = activityResultCaller.registerForActivityResult(mStartActivityForResult){
            callback?.onActivityResult(it)
            callback = null
            log("regisStartActivityForResultContract execute complete")
        }
    }

    fun startActivityForResult(intent: Intent?, callBack: ActivityResultCallback<ActivityResult>){
        log("startActivityForResult with intent = $intent")
        callback = callBack
        mActivityResultLauncher?.launch(intent?: return)
    }

    private fun log(content: String){
        if (mLogEnable) {
            Log.i(TAG, content)
        }
    }


}