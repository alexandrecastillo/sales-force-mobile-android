package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.focos

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.Foco
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.GerenteRegionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.GerenteZonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.SociaEmpresariaRdd
import io.reactivex.Completable

interface ListadoFocosEnAsignacionRepository {
    fun obtenerFocos(): List<Foco>
    fun obtenerFocosIdSE(personaId: Long): Array<Long>
    fun obtenerFocosIdGZ(personaId: Long): Array<Long>
    fun obtenerFocosIdGR(personaId: Long): Array<Long>
    fun obtenerFocosPorUA(llaveUA: LlaveUA): Array<Long>
    fun recuperarFocosPara(personaId: Long, rol: Rol): List<Foco>
    fun obtenerSocias(): List<SociaEmpresariaRdd>
    fun obtenerGZs(): List<GerenteZonaRdd>
    fun obtenerGRs(): List<GerenteRegionRdd>
    fun sincronizar(): Completable
}
