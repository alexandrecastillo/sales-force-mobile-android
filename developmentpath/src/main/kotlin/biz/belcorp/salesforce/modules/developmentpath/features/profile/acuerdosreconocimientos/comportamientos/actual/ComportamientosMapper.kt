package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.actual

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.reconocimiento.ReconocimientoComportamiento
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.reconocimiento.TodosLosReconocimientosEnCampania
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.model.ComportamientoModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.util.ProveedorIconoComportamiento

class ComportamientosMapper(private val proveedorIcono: ProveedorIconoComportamiento) {

    fun map(entidad: ReconocimientoComportamiento): ComportamientoModel {
        return ComportamientoModel(
            id = entidad.comportamiento.id.toInt(),
            iconoId = proveedorIcono.recuperarIcono(entidad.comportamiento.tipoIcono),
            reconocido = entidad.reconocido
        )
    }

    fun map(entidades: List<ReconocimientoComportamiento>): List<ComportamientoModel> {
        return entidades.map { map(it) }
    }

    fun map(response: TodosLosReconocimientosEnCampania): ComportamientosModel {
        return ComportamientosModel(
            realizado = response.realizado,
            reconocimientos = map(response.reconocimiento),
            razon = "${response.reconocidos}/${response.total}",
            progresoPorcentaje = response.porcentaje.toFloat()
        )
    }
}
