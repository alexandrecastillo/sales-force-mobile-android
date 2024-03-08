package biz.belcorp.salesforce.modules.developmentpath.features.rdd.helpers

import android.content.Context
import android.widget.ImageView
import androidx.core.content.ContextCompat
import biz.belcorp.salesforce.components.widgets.TextDrawable
import biz.belcorp.salesforce.core.utils.sp
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.utils.iniciales
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@Deprecated("Commened to Build Dashboard")
fun ImageView.setIsertIconPerson(context: Context, nombreCompleto: String) {
    val placeholderCircular = TextDrawable.builder()
        .beginConfig()
        .fontSize(context.sp(16))
        .endConfig()
        .buildRound(
            nombreCompleto.iniciales(),
            ContextCompat.getColor(context, R.color.rdd_accent)
        )

    val requestOptions = RequestOptions.circleCropTransform()
        .placeholder(placeholderCircular)

    Glide.with(context)
        .load("")
        .apply(requestOptions)
        .into(this)
}
