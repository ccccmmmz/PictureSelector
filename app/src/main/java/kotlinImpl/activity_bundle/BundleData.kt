package kotlinImpl.activity_bundle

import android.os.Binder

class BundleData(bigdata: Any?): Binder() {
    val data: Any? = bigdata
}

class ImageBinder : Binder(){
    val byteData = ByteArray(10)
        get() = field
}