package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.caminobrillante.progress.helper

import androidx.annotation.IntDef

@IntDef(
    TipCaminoBrillanteIdentifier.HITO,
    TipCaminoBrillanteIdentifier.META,
    TipCaminoBrillanteIdentifier.CONSTANCIA
)
annotation class TipCaminoBrillanteIdentifier {
    companion object {
        const val HITO = 1
        const val META = 2
        const val CONSTANCIA = 3
    }
}
