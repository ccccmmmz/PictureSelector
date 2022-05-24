package kotlinImpl

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.luck.pictureselector.R
import kotlinImpl.activityResultContract.ActivityResultKit

class TestActivity: FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission_test)

    }

    //test file permission
    fun testFilePermission(view: View) {
        ActivityResultKit.checkStoragePermission(this)
    }
}