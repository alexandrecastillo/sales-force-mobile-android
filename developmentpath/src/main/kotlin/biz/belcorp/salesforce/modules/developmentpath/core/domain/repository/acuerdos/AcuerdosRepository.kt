package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.acuerdos

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.acuerdos.Acuerdo
import io.reactivex.Completable

interface AcuerdosRepository {
    fun obtener(unidadAdministrativa: LlaveUA): List<Acuerdo>
    fun obtenerUltimasCampanias(unidadAdministrativa: LlaveUA, cantidadCampanias: Int): List<Acuerdo>
    fun obtenerParaCampaniaActual(unidadAdministrativa: LlaveUA): List<Acuerdo>
     fun obtener(acuerdoId: Long): Acuerdo?
    fun insertar(acuerdos: List<Acuerdo.ModeloCreacion>)
    fun editar(acuerdo: Acuerdo)
    fun eliminar(id: Long)
    fun sincronizar(): Completable
}
