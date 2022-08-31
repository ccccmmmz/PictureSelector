package kotlinImpl

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.luck.pictureselector.MainActivity
import com.luck.pictureselector.R
import kotlinImpl.activityResultContract.ActivityResultContractCompat
import kotlinImpl.activity_bundle.BundleData
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
//        mActivityContract?.selectAlbum()
        testTractData()
    }

    /**
     * tract large data
     */
    private fun testTractData(){
        startActivity(Intent(this, MainActivity::class.java).apply {
            putExtras(buildLargeDataBundleForSuccess())
            //read
        })
    }


    /**
     * result
     * Caused by: android.os.TransactionTooLargeException: data parcel size 5000420 bytes
     *
     */
    private fun buildLargeDataBundleForError(): Bundle{
        return bundleOf(Pair("test", ByteArray(1000 * 1000 * 5).apply {
            //100M
            for (i in 0 until 1000 * 1000 * 5L) {
                if (i < Int.MAX_VALUE)
                    this[0] = i.toByte()
            }
        }))
    }

    private fun buildLargeDataBundleForSuccess(): Bundle{

        val bundleData = BundleData(ByteArray(1000 * 1000 * 100).apply {
            //100M
            for (i in 0 until 1000 * 1000 * 100L) {
                if (i < Int.MAX_VALUE)
                    this[0] = i.toByte()
            }
        })
        return bundleOf(Pair("text", bundleData))
    }
}