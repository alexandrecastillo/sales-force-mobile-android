package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.caminobrillante.progress

import biz.belcorp.mobile.components.design.step.Step
import biz.belcorp.mobile.components.design.step.model.StepModel
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.caminobrillante.NivelActualCaminoBrillante
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.caminobrillante.NivelCaminoBrillante
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.caminobrillante.ProgresoCaminoBrillante
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.caminobrillante.TipProgresoCaminoBrillante
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.helper.AcompaniamientoResourcesProvider
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.caminobrillante.progress.model.ProgresoActualCaminoBrillanteModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.caminobrillante.progress.model.ProgresoCaminoBrillanteModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.caminobrillante.progress.model.TipProgresoCaminoBrillanteModel

class ProgresoCaminoBrillanteMapper {

    fun parse(entidad: ProgresoCaminoBrillante): ProgresoCaminoBrillanteModel {
        return ProgresoCaminoBrillanteModel(
            niveles = crearNiveles(entidad.niveles),
            progreso = crearProgreso(entidad.progreso),
            tipsProgreso = entidad.progreso.tips.map { crearTipProgreso(it) })
    }

    private fun crearNiveles(niveles: List<NivelCaminoBrillante>): List<StepModel> {
        actualizarEstadoNiveles(niveles)
        return niveles.map { crearNivel(it) }
    }

    private fun actualizarEstadoNiveles(niveles: List<NivelCaminoBrillante>) {
        val idNivelActual = niveles.firstOrNull { it.esNivelActual }?.id ?: 0
        niveles.filter { it.id < idNivelActual }.forEach { it.esActiva = false }
    }

    private fun crearNivel(nivel: NivelCaminoBrillante): StepModel {
        val colores = establecerColoresStepModel(nivel.id)
        val icono = establerIconoStepModel(nivel.id)
        return StepModel(
            id = nivel.id,
            titulo = nivel.titulo,
            estado = establecerEstadoStepModel(nivel.esActiva, nivel.esNivelActual),
            colorActiva = colores.first,
            colorInactiva = colores.second
        ).apply { this.icono = icono }
    }

    private fun crearProgreso(nivelActual: NivelActualCaminoBrillante): ProgresoActualCaminoBrillanteModel {
        return ProgresoActualCaminoBrillanteModel(
            progreso = nivelActual.progreso,
            hito = nivelActual.hito,
            meta = nivelActual.meta
        )
    }

    private fun crearTipProgreso(tip: TipProgresoCaminoBrillante): TipProgresoCaminoBrillanteModel {
        return TipProgresoCaminoBrillanteModel(
            texto = tip.texto,
            colores = tip.colores.map { AcompaniamientoResourcesProvider.fromColor(it) },
            icon = establecerIconoTip(tip.icono.valor)
        )
    }

    private fun establecerEstadoStepModel(esActiva: Boolean, esActual: Boolean): Step.State {
        return when {
            esActual -> Step.State.SELECTED
            esActiva -> Step.State.ACTIVE
            else -> Step.State.INACTIVE
        }
    }

    private fun establecerColoresStepModel(nivelId: Int): Pair<Int, Int> {
        return AcompaniamientoResourcesProvider.fromNivelCaminoBrillanteColor(nivelId)
    }

    private fun establerIconoStepModel(nivelId: Int): Int {
        return AcompaniamientoResourcesProvider.fromNivelIconoCaminoBrillanteId(nivelId)
    }

    private fun establecerIconoTip(iconoId: Int): Int {
        return AcompaniamientoResourcesProvider.fromTipCaminoBrillanteId(iconoId)
    }
}
