package biz.belcorp.salesforce.core.utils

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import biz.belcorp.mobile.components.core.GlideApp
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

fun AppCompatImageView.svgColor(@ColorRes color: Int) {
    drawable?.apply {
        if (this !is VectorDrawable) return
        val colorIndicator = ContextCompat.getColor(context, color)
        colorFilter = PorterDuffColorFilter(colorIndicator, PorterDuff.Mode.SRC_IN)
    }
}

fun ImageView.loadImageResource(@DrawableRes resId: Int) {
    Glide.with(context)
        .load(resId)
        .into(this)
}

fun ImageView.loadImageDrawable(drawable: Drawable) {
    Glide.with(context)
        .load(drawable)
        .into(this)
}

fun ImageView.loadImage(url: String?, @DrawableRes error: Int = -1, loaded: () -> Unit) {

    var glide = GlideApp.with(context)
    if (error != -1) {
        glide = glide.setDefaultRequestOptions(
            RequestOptions()
                .centerCrop()
                .error(error)
        )
    }

    glide.load(url)
        .thumbnail(0.1f)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                loaded.invoke()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                loaded.invoke()
                return false
            }

        })
        .into(this)
}
