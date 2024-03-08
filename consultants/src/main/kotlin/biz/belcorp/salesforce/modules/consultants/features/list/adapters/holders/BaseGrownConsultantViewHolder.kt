package biz.belcorp.salesforce.modules.consultants.features.list.adapters.holders

import android.view.View
import biz.belcorp.salesforce.modules.consultants.R
import biz.belcorp.salesforce.modules.consultants.core.domain.constants.BeautyConsultantConstants
import biz.belcorp.salesforce.modules.consultants.features.list.adapters.OnConsultantItemSelected
import biz.belcorp.salesforce.modules.consultants.features.list.models.ConsultoraModel
import kotlinx.android.synthetic.main.container_view_grown_consultant_list.view.*
import kotlinx.android.synthetic.main.item_view_grown_consultant_list.view.*

abstract class BaseGrownConsultantViewHolder(view: View) : BaseViewHolder(view) {

    private var beautyConsultantLevelColorsHash = hashMapOf(
        BeautyConsultantConstants.CONSULTORA_BRILLANTE to R.drawable.shape_bright_consultant_circle,
        BeautyConsultantConstants.CONSULTORA_TOPACIO to R.drawable.shape_topaz_consultant_circle,
        BeautyConsultantConstants.CONSULTORA_PERLA to R.drawable.shape_pearl_consultant_circle,
        BeautyConsultantConstants.CONSULTORA_AMBAR to R.drawable.shape_amber_consultant_circle,
        BeautyConsultantConstants.CONSULTORA_CORAL to R.drawable.shape_coral_consultant_circle,
        BeautyConsultantConstants.CONSULTORA_CONSULTORA to R.drawable.shape_consultant_consultant_circle
    )


    var listener: OnConsultantItemSelected? = null
    var model: ConsultoraModel? = null


    fun handleLevelView(level: String) {
        level.let {
            val drawable = getDotDrawable(level)
            drawDotShapeLevel(drawable)
            itemView.tvwBeautyConsultantLevelVal?.text = it
        }
    }

    private fun getDotDrawable(level: String) = when {
        BeautyConsultantConstants.CONSULTORA_BRILLANTE.equals(level, ignoreCase = true) ->
            beautyConsultantLevelColorsHash[BeautyConsultantConstants.CONSULTORA_BRILLANTE]
        level.equals(BeautyConsultantConstants.CONSULTORA_TOPACIO, ignoreCase = true) ->
            beautyConsultantLevelColorsHash[BeautyConsultantConstants.CONSULTORA_TOPACIO]
        level.equals(BeautyConsultantConstants.CONSULTORA_PERLA, ignoreCase = true) ->
            beautyConsultantLevelColorsHash[BeautyConsultantConstants.CONSULTORA_PERLA]
        level.equals(BeautyConsultantConstants.CONSULTORA_AMBAR, ignoreCase = true) ->
            beautyConsultantLevelColorsHash[BeautyConsultantConstants.CONSULTORA_AMBAR]
        level.equals(BeautyConsultantConstants.CONSULTORA_CORAL, ignoreCase = true) ->
            beautyConsultantLevelColorsHash[BeautyConsultantConstants.CONSULTORA_CORAL]
        level.equals(BeautyConsultantConstants.CONSULTORA_CONSULTORA, ignoreCase = true) ->
            beautyConsultantLevelColorsHash[BeautyConsultantConstants.CONSULTORA_CONSULTORA]
        else -> beautyConsultantLevelColorsHash[BeautyConsultantConstants.CONSULTORA_CONSULTORA]
    }


    private fun drawDotShapeLevel(drawableInt: Int?) {
        drawableInt?.let { d ->
            itemView.ivwDotColorConsultantLevelIcon?.setImageResource(d)
        }
    }

    fun setListeners() {
        model?.let { o ->
            itemView.cvwGrownConsultantItemContainer?.setOnClickListener {
                onItemViewSelected(o)
            }
            itemView.ivwWhatsAppIcon?.setOnClickListener {
                onWhatsAppIconSelected(o.telefonoCelular)
            }
            itemView.ivwCallIcon?.setOnClickListener {
                onCallIconSelected(o.seccion, o.telefonoCelular)
            }
        }
    }

    abstract fun onItemViewSelected(model: ConsultoraModel)
    abstract fun onWhatsAppIconSelected(phoneNumber: String?)
    abstract fun onCallIconSelected(section: String?, phoneNumber: String?)

}
