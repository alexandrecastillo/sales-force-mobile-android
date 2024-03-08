package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.caminobrillante.progress

import biz.belcorp.mobile.components.design.step.model.StepModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.caminobrillante.progress.model.ProgresoActualCaminoBrillanteModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.caminobrillante.progress.model.TipProgresoCaminoBrillanteModel

interface ProgresoCaminoBrillanteView {
    fun mostrarNivelesCaminoBrillante(niveles: List<StepModel>)
    fun ocultarNivelesCaminoBrillante()
    fun mostrarProgresoCaminoBrillante(entidad: ProgresoActualCaminoBrillanteModel)
    fun ocultarProgresoCaminoBrillante()
    fun mostrarTipsCaminoBrillante(tips: List<TipProgresoCaminoBrillanteModel>)
    fun ocultarTipsCaminoBrillante()
    fun ocultarContenedorCaminoBrillante()
}
