package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gr.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

data class DesarrolloHabilidadModel(
    val descripcion: String,
    val porcentaje: Int,
    @DrawableRes val iconResId: Int,
    @ColorRes val colorResId: Int
)
