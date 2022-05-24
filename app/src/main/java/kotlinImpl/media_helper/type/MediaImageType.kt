package kotlinImpl.media_helper.type

import androidx.annotation.IntDef
import com.luck.picture.lib.config.PictureMimeType.PNG
import java.lang.annotation.RetentionPolicy


/**
 * image type
 */



@Retention(AnnotationRetention.SOURCE)
annotation class MediaImageType(
    val PNG: Int = 1,
    val JPG: Int = 2,

)
