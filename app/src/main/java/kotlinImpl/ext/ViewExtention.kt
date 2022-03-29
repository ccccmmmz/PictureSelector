package kotlinImpl.ext

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.luck.picture.lib.app.PictureAppMaster
import com.luck.pictureselector.App

class ViewExtention private constructor() {
    companion object {

        /**
         * view create
         */
        fun Int.inflate(parent: ViewGroup? = null, attach: Boolean = false): View {
            // TODO: Application provider
            return LayoutInflater.from(parent?.context ?: PictureAppMaster.getInstance().appContext)
                .inflate(this, parent, attach)
        }
    }
}