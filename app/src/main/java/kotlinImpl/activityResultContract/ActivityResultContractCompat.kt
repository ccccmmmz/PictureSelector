package kotlinImpl.activityResultContract

import android.Manifest
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

class ActivityResultContractCompat(private val activityResultCaller: ActivityResultCaller) {


    private val TAG = ActivityResultContractCompat::class.simpleName

    private val mLogEnable = true

    private val mFragmentActivity by lazy {
        when (activityResultCaller) {
            is FragmentActivity -> { activityResultCaller }
            is Fragment -> { activityResultCaller.activity }
            else -> {}
        } as? FragmentActivity
    }

    private val mStartActivityForResult by lazy { ActivityResultContracts.StartActivityForResult() }
    private var mActivityResultLauncher : ActivityResultLauncher<Intent>? = null
    private var callback: ActivityResultCallback<ActivityResult>? = null

    private val mPermissionContract by lazy { ActivityResultContracts.RequestMultiplePermissions() }
    private var mPermissionCallback : ActivityResultCallback<Map<String, Boolean>>? = null
    private var mPermissionLauncher : ActivityResultLauncher<Array<String>>? = null

    private val mImageContract by lazy { ActivityResultContracts.GetMultipleContents() }
    private var mImageSelectLaunch: ActivityResultLauncher<String?>? = null

    init {
        regisStartActivityForResultContract(activityResultCaller)
        regisPermissionContract(activityResultCaller)
        regisImageAlbumFetchContract(activityResultCaller)


    }

    fun regisStartActivityForResultContract(activityResultCaller: ActivityResultCaller) {
        mActivityResultLauncher = activityResultCaller.registerForActivityResult(mStartActivityForResult){
            callback?.onActivityResult(it)
            callback = null
            log("regisStartActivityForResultContract execute complete")
        }
    }
    fun regisPermissionContract(activityResultCaller: ActivityResultCaller){
        mPermissionLauncher =
            activityResultCaller.registerForActivityResult(mPermissionContract) { it ->
                log("permission result $it")
                //mPermissionCallback?.onActivityResult(it)
                val iterator = it.iterator()
                val grantList = ArrayList<String>(it.size)
                val unGrantList = ArrayList<String>(it.size)
                iterator.forEach {
                    if (!it.value) {
                        unGrantList.add(it.key)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            mFragmentActivity?.shouldShowRequestPermissionRationale(it.key)
                        } else {
                            //deny
                        }
                    } else {
                        grantList.add(it.key)
                    }
                }
            }
    }

    fun startActivityForResult(intent: Intent?, callBack: ActivityResultCallback<ActivityResult>){
        log("startActivityForResult with intent = $intent")
        callback = callBack
        mActivityResultLauncher?.launch(intent?: return)

    }

    fun checkPermission(vararg permission: String){
        mPermissionLauncher?.launch(permission as Array<String>)
    }



    fun checkStoragePermission(){
        checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    fun regisImageAlbumFetchContract(activityResultCaller: ActivityResultCaller){
        mImageSelectLaunch = activityResultCaller.registerForActivityResult(mImageContract) {
            log(it?.toString().orEmpty())
        }
    }

    //open system select input
    fun selectAlbum(){
        mImageSelectLaunch?.launch("image/*")
    }

    private fun log(content: String){
        if (mLogEnable) {
            Log.i(TAG, content)
        }
    }


    interface PermissionGrantEvent{
        fun onGrant()
        fun onDenied()
    }

}