package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.actual.detalle

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.reconocimiento.ReconocimientoComportamiento
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.reconocimiento.verdetalle.ReconocerOutput
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.reconocer.ReconocimientoModel

class VerReconocimientoMapper {

    fun convertir(entidad: ReconocimientoComportamiento): ReconocimientoModel {
        return ReconocimientoModel(
            id = entidad.comportamiento.id,
            iconoId = entidad.comportamiento.tipoIcono,
            descripcion = entidad.comportamiento.titulo,
            seleccionado = entidad.reconocido
        )
    }

    fun map(t: ReconocerOutput.Recuperar): List<ReconocimientoModel> {
        return t.reconocimientos.map { convertir(it) }
    }
}
