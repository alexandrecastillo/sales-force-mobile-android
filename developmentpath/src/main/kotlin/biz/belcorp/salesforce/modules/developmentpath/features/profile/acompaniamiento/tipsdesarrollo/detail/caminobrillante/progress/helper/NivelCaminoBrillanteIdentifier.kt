package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.caminobrillante.progress.helper

import androidx.annotation.IntDef

@IntDef(
    NivelCaminoBrillanteIdentifier.CONSULTORA,
    NivelCaminoBrillanteIdentifier.CORAL,
    NivelCaminoBrillanteIdentifier.AMBAR,
    NivelCaminoBrillanteIdentifier.PERLA,
    NivelCaminoBrillanteIdentifier.TOPACIO,
    NivelCaminoBrillanteIdentifier.BRILLANTE
)
annotation class NivelCaminoBrillanteIdentifier {
    companion object {
        const val CONSULTORA = 1
        const val CORAL = 2
        const val AMBAR = 3
        const val PERLA = 4
        const val TOPACIO = 5
        const val BRILLANTE = 6
    }
}
