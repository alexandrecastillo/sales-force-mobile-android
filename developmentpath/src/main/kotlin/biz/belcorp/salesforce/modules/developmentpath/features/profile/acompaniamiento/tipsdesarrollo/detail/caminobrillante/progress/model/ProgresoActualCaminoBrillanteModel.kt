package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.caminobrillante.progress.model

import biz.belcorp.salesforce.core.constants.Constant

class ProgresoActualCaminoBrillanteModel(
    val progreso: Long,
    val hito: Long,
    val meta: Long
) {

    companion object {
        fun crearModelo() = ProgresoActualCaminoBrillanteModel(progreso = -1, hito = -1, meta = -1)
    }

    val esValido get() = progreso > Constant.UNO_NEGATIVO && hito > Constant.UNO_NEGATIVO && meta > Constant.UNO_NEGATIVO
}
