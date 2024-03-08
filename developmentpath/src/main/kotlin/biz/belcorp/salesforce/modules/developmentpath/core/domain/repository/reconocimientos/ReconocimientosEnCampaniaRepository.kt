package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.reconocimientos

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.reconocimiento.TodosLosReconocimientosEnCampania

interface ReconocimientosEnCampaniaRepository {
    fun obtenerParaCampaniaActual(llaveUA: LlaveUA): TodosLosReconocimientosEnCampania
    fun obtenerParaPenultimasCampanias(
        llaveUA: LlaveUA,
        limiteCampanias: Int
    ): List<TodosLosReconocimientosEnCampania>
}
