package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.resultados

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.ConsultoraRdd
import io.reactivex.Single

interface ResultadoVisitasRepository {
    fun recuperarCantidadConsultorasFacturadas(): Long
    fun recuperarCantidadConsultorasNoFacturadas(): Long
    fun recuperarConsultorasFacturadas(): List<ConsultoraRdd>
    fun recuperarConsultorasNoFacturadas(): List<ConsultoraRdd>
    fun recuperarMontosDeConsultoras(campania: String, ua: LlaveUA): Single<Map<Long, Double>>
}
