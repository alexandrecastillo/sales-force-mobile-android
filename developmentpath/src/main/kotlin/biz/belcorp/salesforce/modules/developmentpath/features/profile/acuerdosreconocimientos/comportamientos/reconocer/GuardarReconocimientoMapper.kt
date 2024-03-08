package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.reconocer

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.comportamientos.Comportamiento
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.comportamientos.reconocer.ReconocerOutput
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.Seleccionable

class GuardarReconocimientoMapper {

    private fun mapSeleccionable(entidad: Seleccionable<Comportamiento>): ReconocimientoModel {
        return ReconocimientoModel(
            id = entidad.elemento.id,
            iconoId = entidad.elemento.tipoIcono,
            descripcion = entidad.elemento.titulo,
            seleccionado = entidad.seleccionado
        )
    }

    fun map(t: ReconocerOutput.Recuperar) = GuardarReconocimientoModel().apply {
        reconocimientos = t.comportamientos.map { mapSeleccionable(it) }
        seleccionados = t.totalSeleccionados
        total = t.totalComportamientos
    }
}
