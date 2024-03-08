package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.caminobrillante.progress.model

import biz.belcorp.mobile.components.design.step.model.StepModel

class ProgresoCaminoBrillanteModel(
    val niveles: List<StepModel> = emptyList(),
    val progreso: ProgresoActualCaminoBrillanteModel = ProgresoActualCaminoBrillanteModel.crearModelo(),
    val tipsProgreso: List<TipProgresoCaminoBrillanteModel> = emptyList()
)
