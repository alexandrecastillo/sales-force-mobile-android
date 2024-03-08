package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.desarrollo.view.comportamientos.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

data class DesarrolloComportamientoModel(val texto: String,
                                         val porcentaje: Int,
                                         @DrawableRes val iconoId: Int,
                                         @ColorRes val color: Int)
