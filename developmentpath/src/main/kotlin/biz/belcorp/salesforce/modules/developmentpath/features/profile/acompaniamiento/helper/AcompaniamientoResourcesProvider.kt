package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.helper

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.color.Color
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.concursos.TipoConcurso
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.caminobrillante.progress.helper.NivelCaminoBrillanteIdentifier
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.caminobrillante.progress.helper.TipCaminoBrillanteIdentifier
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.section.TipDesarrolloIconIdentifier
import biz.belcorp.salesforce.base.R as BaseR

object AcompaniamientoResourcesProvider {

    @DrawableRes
    fun fromTipDesarrolloIconId(iconId: String): Int = when (iconId) {
        TipDesarrolloIconIdentifier.VENTAGANANCIA -> R.drawable.ic_venta_ganancia
        TipDesarrolloIconIdentifier.DIGITAL -> R.drawable.digital
        TipDesarrolloIconIdentifier.CONCURSOS -> R.drawable.concursos
        TipDesarrolloIconIdentifier.NOVEDADES -> R.drawable.novedades
        TipDesarrolloIconIdentifier.PROGRAMANUEVAS -> R.drawable.programa_nuevas
        TipDesarrolloIconIdentifier.CAMINOBRILLANTE -> R.drawable.ic_tip_camino_brillante
        TipDesarrolloIconIdentifier.ESTABLECIDAS -> R.drawable.establecidas
        else -> R.drawable.ic_upload_image
    }

    @DrawableRes
    fun fromTipCaminoBrillanteId(iconId: Int): Int = when (iconId) {
        TipCaminoBrillanteIdentifier.META -> R.drawable.ic_meta
        TipCaminoBrillanteIdentifier.HITO -> R.drawable.ic_hito
        else -> -Constant.NUMERO_UNO
    }

    @DrawableRes
    fun fromNivelIconoCaminoBrillanteId(nivelId: Int): Int = when (nivelId) {
        NivelCaminoBrillanteIdentifier.BRILLANTE -> R.drawable.ic_diamante
        else -> BaseR.drawable.ic_camino_brillante
    }

    fun fromNivelCaminoBrillanteColor(nivelId: Int): Pair<Int, Int> = when (nivelId) {
        NivelCaminoBrillanteIdentifier.CONSULTORA -> Pair(BaseR.color.consultora, BaseR.color.consultora)
        NivelCaminoBrillanteIdentifier.CORAL -> Pair(BaseR.color.coral, BaseR.color.coral_inactiva)
        NivelCaminoBrillanteIdentifier.AMBAR -> Pair(BaseR.color.ambar, BaseR.color.ambar_inactiva)
        NivelCaminoBrillanteIdentifier.PERLA -> Pair(BaseR.color.perla, BaseR.color.perla_inactiva)
        NivelCaminoBrillanteIdentifier.TOPACIO -> Pair(BaseR.color.topacio, BaseR.color.topacio_inactiva)
        NivelCaminoBrillanteIdentifier.BRILLANTE -> Pair(BaseR.color.brillante, BaseR.color.brillante_inactiva)
        else -> Pair(BaseR.color.black, BaseR.color.black)
    }

    @DrawableRes
    fun fromTipoConcurso(tipo: String): Int = when (tipo) {
        TipoConcurso.REGALO_POR_PEDIDO -> R.drawable.group_icon_bonification
        TipoConcurso.PROGRAMA_BRILLANTE -> R.drawable.ic_programa_brillante
        else -> R.drawable.ic_upload_image
    }

    @ColorRes
    fun fromColor(color: Color): Int = when (color) {
        Color.NEGRO -> BaseR.color.black
        Color.VERDE -> BaseR.color.positivo
        Color.ROJO -> BaseR.color.negativo
        Color.DORADO -> BaseR.color.golden_gana
        Color.MAGENTA -> BaseR.color.magenta
        Color.NINGUNO -> BaseR.color.gray_5
    }
}
