package kotlinImpl.ActivityResultContract

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.luck.picture.lib.engine.ImageEngine
import com.luck.picture.lib.interfaces.OnCallbackListener
import com.luck.picture.lib.utils.ActivityCompatHelper
import com.luck.pictureselector.R

class GlideEngine : ImageEngine {
    override fun loadImage(context: Context, url: String, imageView: ImageView) {
        if (!ActivityCompatHelper.assertValidRequest(context)) {
            return
        }
        Glide.with(context)
            .load(url)
            .into(imageView)
    }

    override fun loadImageBitmap(
        context: Context,
        url: String,
        maxWidth: Int,
        maxHeight: Int,
        call: OnCallbackListener<Bitmap>?
    ) {
        if (!ActivityCompatHelper.assertValidRequest(context)) {
            return
        }
        Glide.with(context)
            .asBitmap()
            .override(maxWidth, maxHeight)
            .load(url)
            .into(object : CustomTarget<Bitmap?>() {

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    call?.onCall(null)
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap?>?
                ) {
                    call?.onCall(resource)
                }
            })
    }

    override fun loadAlbumCover(context: Context, url: String, imageView: ImageView) {
        if (!ActivityCompatHelper.assertValidRequest(context)) {
            return
        }
        Glide.with(context)
            .asBitmap()
            .load(url)
            .override(180, 180)
            .sizeMultiplier(0.5f)
            .transform(CenterCrop(), RoundedCorners(8))
            .placeholder(R.drawable.ps_image_placeholder)
            .into(imageView)
    }

    override fun loadGridImage(context: Context, url: String, imageView: ImageView) {
        if (!ActivityCompatHelper.assertValidRequest(context)) {
            return
        }
        Glide.with(context)
            .load(url)
            .override(200, 200)
            .centerCrop()
            .placeholder(R.drawable.ps_image_placeholder)
            .into(imageView)
    }

    override fun pauseRequests(context: Context?) {
        context?.let {
            Glide.with(it).pauseRequests()
        }
    }

    override fun resumeRequests(context: Context?) {
        context?.let { Glide.with(it).resumeRequests() }
    }

    @SuppressLint("CheckResult")
    fun loadImage(target: ImageView?, url: Any?, requestWidth: Int = 0, requestHeight: Int = 0, @DrawableRes placeHolder: Int){
        val context = target?.context ?: return
        if (ActivityCompatHelper.assertValidRequest(context)) {
            return
        }
        Glide.with(context).load(url).apply {
            if (requestHeight != 0 && requestWidth != 0) {
                override(requestWidth, requestHeight)
            }
            if (placeHolder != 0) {
                placeholder(placeHolder)
            }
            into(target)
        }

    }
}