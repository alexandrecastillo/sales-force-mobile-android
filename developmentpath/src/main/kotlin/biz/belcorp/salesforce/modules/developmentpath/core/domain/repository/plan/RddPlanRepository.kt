package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.plan

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.plan.InfoPlanRdd
import io.reactivex.Single

interface RddPlanRepository {
    fun obtenerPlanParaDV(): Long?
    fun obtenerPlanParaGR(region: String): Long?
    fun obtenerPlanParaGZ(zona: String): Long?
    fun obtenerPlanParaSE(zona: String, seccion: String): Long?
    fun obtenerPlanAlQuePerteneceUnaPersona(identificador: PersonaRdd.Identificador): InfoPlanRdd?
    fun obtenerPlanAlQuePerteneceSocia(personaId: Long): InfoPlanRdd?
    fun obtenerPlanAlquePertenecePosibleConsultora(id: Long): InfoPlanRdd?
    fun obtenerPlanAlQuePerteneceGerenteZona(idPersona: Long): InfoPlanRdd?
    fun obtenerPlanAlQuePerteneceGerenteRegion(idPersona: Long): InfoPlanRdd?
    fun obtenerRolDePlan(id: Long): Rol?
    fun obtenerInfoPlanRdd(planId: Long): InfoPlanRdd?
    fun obtenerInfoPlanRdd(llaveUA: LlaveUA): InfoPlanRdd?
    fun obtenerInfoPlanRddAsync(planId: Long): Single<InfoPlanRdd>
    fun cantidadVisitasRegistradas(personaId: Long, rol: Rol, codigoCampania: String): Long
    fun obtenerPlanParaRolDuenio(rol: Rol): Long?
    fun obtenerPlanAlQuePerteneceConsultora(personaId: Long): InfoPlanRdd?
    fun obtenerCampaniaActualPlanRDD(rol: Rol): String?
}
