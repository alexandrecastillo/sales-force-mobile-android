package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.section

import androidx.annotation.DrawableRes
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclave.PrepararseEsClave

data class PrepararseEsClaveModel(
    val id: Int,
    @DrawableRes val icon: Int,
    val posicion: Int,
    val esVisible: Boolean,
    val tipo: PrepararseEsClave.Tipo,
    val titulo: String
)
