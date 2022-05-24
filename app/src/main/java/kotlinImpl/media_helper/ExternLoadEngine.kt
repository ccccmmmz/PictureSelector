package kotlinImpl.media_helper

import android.content.Context

interface ExternLoadEngine {

    fun loadAllAlbumData(context: Context)

    fun loadOnlyInAppDirAllMedia()

    fun loadFirstPageMedia(buckId: Long, pageSize: Int)

    fun loadPageMediaData(buckId: Long, page: Int, pageSize: Int)


}