package kotlinImpl

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.luck.pictureselector.R
import kotlinImpl.activityResultContract.ActivityResultContractCompat
import kotlinImpl.recycler_view.CommonAdapter

class TestActivity: FragmentActivity() {

    private var mActivityContract : ActivityResultContractCompat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission_test)
        mActivityContract = ActivityResultContractCompat(this)

        val recyclerView = findViewById<RecyclerView>(R.id.rvTest)
        val commonAdapter = CommonAdapter<String>()

    }

    //test file permission
    fun testFilePermission(view: View) {
        //ActivityResultKit.checkStoragePermission(this)
        mActivityContract?.selectAlbum()
    }
}