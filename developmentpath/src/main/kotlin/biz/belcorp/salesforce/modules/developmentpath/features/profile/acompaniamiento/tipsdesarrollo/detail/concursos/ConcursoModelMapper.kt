package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.concursos

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.concursos.ConcursoDetalle
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.helper.AcompaniamientoResourcesProvider

class ConcursoModelMapper {

    fun parse(entidades: List<ConcursoDetalle>): List<ConcursoViewModel> {
        return entidades.map { parse(it) }
    }

    private fun parse(detalle: ConcursoDetalle): ConcursoViewModel {
        return ConcursoViewModel(
            tipo = detalle.tipo,
            iconoProgreso = AcompaniamientoResourcesProvider.fromTipoConcurso(detalle.tipo),
            nivel = detalle.nivel,
            puntosNivel = detalle.puntosNivel,
            puntosFaltantes = detalle.puntosFaltantes,
            puntosAcumulados = detalle.puntosAcumulados,
            imagenUrl = detalle.imagenUrl,
            descripcionProgreso = detalle.descripcionProgreso,
            descripcionPremio = detalle.descripcionPremio
        )
    }
}
