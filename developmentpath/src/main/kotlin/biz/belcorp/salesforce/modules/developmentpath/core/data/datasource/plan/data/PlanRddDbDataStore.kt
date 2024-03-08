package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.plan.data

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.exceptions.UnsupportedRoleException
import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity
import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity_Table
import biz.belcorp.salesforce.core.entities.sql.directorio.DirectorioVentaUsuarioEntity
import biz.belcorp.salesforce.core.entities.sql.directorio.DirectorioVentaUsuarioEntity_Table
import biz.belcorp.salesforce.core.entities.sql.plan.DetallePlanRutaRDDEntity
import biz.belcorp.salesforce.core.entities.sql.plan.DetallePlanRutaRDDEntity_Table
import biz.belcorp.salesforce.core.entities.sql.plan.PlanRutaRDDEntity
import biz.belcorp.salesforce.core.entities.sql.plan.PlanRutaRDDEntity_Table
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.eqNullable
import biz.belcorp.salesforce.core.utils.guionSiVacioONull
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.plan.InfoPlanRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.plan.RddPlanRepository
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.raizlabs.android.dbflow.sql.language.Method
import com.raizlabs.android.dbflow.sql.language.Select
import io.reactivex.Single

class PlanRddDbDataStore : RddPlanRepository {

    override fun obtenerPlanParaDV(): Long? {
        val query = (select from PlanRutaRDDEntity::class
            where (PlanRutaRDDEntity_Table.Region.isNull)
            and (PlanRutaRDDEntity_Table.Zona.isNull)
            and (PlanRutaRDDEntity_Table.Seccion.isNull))

        return query.querySingle()?.id?.toLong()
    }

    override fun obtenerPlanParaGR(region: String): Long? {
        val query = (select from PlanRutaRDDEntity::class
            where (PlanRutaRDDEntity_Table.Region eq region)
            and (PlanRutaRDDEntity_Table.Zona.isNull)
            and (PlanRutaRDDEntity_Table.Seccion.isNull))

        return query.querySingle()?.id?.toLong()
    }

    override fun obtenerPlanParaGZ(zona: String): Long? {
        val query = (select from PlanRutaRDDEntity::class
            where (PlanRutaRDDEntity_Table.Zona eq zona)
            and (PlanRutaRDDEntity_Table.Seccion.isNull))

        return query.querySingle()?.id?.toLong()
    }

    override fun obtenerPlanParaSE(zona: String, seccion: String): Long? {
        val query = (select from PlanRutaRDDEntity::class
            where (PlanRutaRDDEntity_Table.Zona eq zona)
            and (PlanRutaRDDEntity_Table.Seccion eq seccion))

        return query.querySingle()?.id?.toLong()
    }

    override fun obtenerPlanAlQuePerteneceUnaPersona(identificador: PersonaRdd.Identificador): InfoPlanRdd? {
        return when (identificador.rol) {
            Rol.POSIBLE_CONSULTORA ->
                obtenerPlanAlquePertenecePosibleConsultora(identificador.id)
            Rol.CONSULTORA ->
                obtenerPlanAlQuePerteneceConsultora(identificador.id)
            Rol.SOCIA_EMPRESARIA ->
                obtenerPlanAlQuePerteneceSocia(identificador.id)
            Rol.GERENTE_ZONA ->
                obtenerPlanAlQuePerteneceGerenteZona(identificador.id)
            Rol.GERENTE_REGION ->
                obtenerPlanAlQuePerteneceGerenteRegion(identificador.id)
            else ->
                throw UnsupportedRoleException(identificador.rol)
        }
    }

    override fun obtenerPlanAlQuePerteneceConsultora(personaId: Long): InfoPlanRdd? {

        val modelo = (seleccionarColumnasDePlan()
            from PlanRutaRDDEntity::class
            innerJoin DetallePlanRutaRDDEntity::class
            on (DetallePlanRutaRDDEntity_Table.PlanVisitaID.withTable()
            eq PlanRutaRDDEntity_Table.ID.withTable())
            where (DetallePlanRutaRDDEntity_Table.ConsultorasID eq personaId)
            and (DetallePlanRutaRDDEntity_Table.Rol.withTable() eq (Rol.CONSULTORA.codigoRol)))
            .querySingle() ?: return null

        return mapInfoPlanRdd(modelo)
    }

    override fun obtenerPlanAlQuePerteneceSocia(personaId: Long): InfoPlanRdd? {

        val modelo = (seleccionarColumnasDePlan()
            from PlanRutaRDDEntity::class
            innerJoin DetallePlanRutaRDDEntity::class
            on (DetallePlanRutaRDDEntity_Table.PlanVisitaID.withTable()
            eq PlanRutaRDDEntity_Table.ID.withTable())
            where (DetallePlanRutaRDDEntity_Table.ConsultorasID eq personaId)
            and (DetallePlanRutaRDDEntity_Table.Rol.withTable() eq (Rol.SOCIA_EMPRESARIA.codigoRol)))
            .querySingle() ?: return null

        return mapInfoPlanRdd(modelo)
    }

    private fun seleccionarColumnasDePlan(): Select {
        return Select(PlanRutaRDDEntity_Table.ID.withTable(),
            PlanRutaRDDEntity_Table.Rol.withTable(),
            PlanRutaRDDEntity_Table.Campania.withTable(),
            PlanRutaRDDEntity_Table.Pais.withTable(),
            PlanRutaRDDEntity_Table.Region.withTable(),
            PlanRutaRDDEntity_Table.Zona.withTable(),
            PlanRutaRDDEntity_Table.Seccion.withTable(),
            PlanRutaRDDEntity_Table.Usuario.withTable())
    }

    override fun obtenerPlanAlQuePerteneceGerenteZona(idPersona: Long): InfoPlanRdd? {
        val query = (seleccionarColumnasDePlan()
            from PlanRutaRDDEntity::class
            innerJoin DetallePlanRutaRDDEntity::class
            on (PlanRutaRDDEntity_Table.ID.withTable()
            eq DetallePlanRutaRDDEntity_Table.PlanVisitaID.withTable())
            innerJoin DirectorioVentaUsuarioEntity::class
            on (DetallePlanRutaRDDEntity_Table.CodigoConsultora.withTable()
            eq DirectorioVentaUsuarioEntity_Table.Usuario.withTable())
            where (DirectorioVentaUsuarioEntity_Table.consultoraID.withTable() eq idPersona)
            and (DirectorioVentaUsuarioEntity_Table.CodRol.withTable() eq Rol.GERENTE_ZONA.codigoRol))

        val modelo = query.querySingle() ?: return null

        return mapInfoPlanRdd(modelo)
    }

    override fun obtenerPlanAlQuePerteneceGerenteRegion(idPersona: Long): InfoPlanRdd? {
        val query = (seleccionarColumnasDePlan()
            from PlanRutaRDDEntity::class
            innerJoin DetallePlanRutaRDDEntity::class
            on (PlanRutaRDDEntity_Table.ID.withTable()
            eq DetallePlanRutaRDDEntity_Table.PlanVisitaID.withTable())
            innerJoin DirectorioVentaUsuarioEntity::class
            on (DetallePlanRutaRDDEntity_Table.CodigoConsultora.withTable()
            eq DirectorioVentaUsuarioEntity_Table.Usuario.withTable())
            where (DirectorioVentaUsuarioEntity_Table.consultoraID.withTable() eq idPersona)
            and (DirectorioVentaUsuarioEntity_Table.CodRol.withTable() eq Rol.GERENTE_REGION.codigoRol))

        val modelo = query.querySingle() ?: return null

        return mapInfoPlanRdd(modelo)
    }

    /* Esto no va a funcionar si la funcionalidad de ver
    posibles consultoras desde gz se concreta. Cambiar el método de ser necesario*/
    override fun obtenerPlanAlquePertenecePosibleConsultora(id: Long): InfoPlanRdd? {
        val modelo = (select from PlanRutaRDDEntity::class).querySingle() ?: return null

        return mapInfoPlanRdd(modelo)
    }

    override fun obtenerRolDePlan(id: Long): Rol? {
        val where = (select
            from PlanRutaRDDEntity::class
            where (PlanRutaRDDEntity_Table.ID eq id.toInt()))

        val codRol = where.querySingle()?.rol ?: return null

        return Rol.Builder.construir(codRol)
    }

    override fun obtenerInfoPlanRdd(planId: Long): InfoPlanRdd? {
        val modelo = (select
            from PlanRutaRDDEntity::class
            where (PlanRutaRDDEntity_Table.ID eq planId.toInt()))
            .querySingle() ?: return null
        return mapInfoPlanRdd(modelo)
    }

    override fun obtenerInfoPlanRdd(llaveUA: LlaveUA): InfoPlanRdd? {
        val where = (select
            from PlanRutaRDDEntity::class
            innerJoin (CampaniaUaEntity::class)
            on ((PlanRutaRDDEntity_Table.Campania.withTable()
            eq CampaniaUaEntity_Table.Codigo.withTable())
            and (CampaniaUaEntity_Table.Region.withTable()
            eq llaveUA.codigoRegion.guionSiVacioONull())
            and (CampaniaUaEntity_Table.Zona.withTable()
            eq llaveUA.codigoZona.guionSiVacioONull())
            and (CampaniaUaEntity_Table.Seccion.withTable()
            eq llaveUA.codigoSeccion.guionSiVacioONull())
            and (CampaniaUaEntity_Table.Orden.withTable()
            eq CampaniaUaEntity.ORDEN_CAMPANIA_ACTUAL))
            where (PlanRutaRDDEntity_Table.Region.withTable()
            eqNullable llaveUA.codigoRegion)
            and (PlanRutaRDDEntity_Table.Zona.withTable()
            eqNullable llaveUA.codigoZona)
            and (PlanRutaRDDEntity_Table.Seccion.withTable()
            eqNullable llaveUA.codigoSeccion))

        val modelo = where.querySingle()

        return mapInfoPlanRdd(modelo ?: return null)
    }

    override fun obtenerInfoPlanRddAsync(planId: Long): Single<InfoPlanRdd> {
        return Single.create {
            it.onSuccess(requireNotNull(obtenerInfoPlanRdd(planId)))
        }
    }

    private fun mapInfoPlanRdd(modelo: PlanRutaRDDEntity): InfoPlanRdd {
        return InfoPlanRdd(
            planId = requireNotNull(modelo.id.toLong()),
            campania = requireNotNull(modelo.campania),
            codigoPais = requireNotNull(modelo.pais),
            codigoRegion = modelo.region,
            codigoZona = modelo.zona,
            codigoSeccion = modelo.seccion,
            rol = Rol.Builder.construir(modelo.rol),
            usuario = modelo.usuario,
            visitadas = modelo.totalVisitadas,
            planificadas = modelo.totalPlanificadas,
            visitedDays = modelo.visitedDays
        )
    }

    override fun cantidadVisitasRegistradas(
        personaId: Long,
        rol: Rol,
        codigoCampania: String
    ): Long {
        return when (rol) {
            Rol.GERENTE_ZONA -> obtenerVisitasRegistradas(personaId, codigoCampania)
            Rol.GERENTE_REGION -> obtenerVisitasRegistradas(personaId, codigoCampania)
            else -> throw UnsupportedRoleException(rol)
        }
    }

    private fun obtenerVisitasRegistradas(personaId: Long, codigoCampania: String): Long {
        return (Select(Method.count())
            from DetallePlanRutaRDDEntity::class
            innerJoin DirectorioVentaUsuarioEntity::class
            on (DetallePlanRutaRDDEntity_Table.CodigoConsultora.withTable()
            eq DirectorioVentaUsuarioEntity_Table.Usuario.withTable())
            innerJoin PlanRutaRDDEntity::class
            on (DetallePlanRutaRDDEntity_Table.PlanVisitaID.withTable()
            eq PlanRutaRDDEntity_Table.ID.withTable())
            where (DirectorioVentaUsuarioEntity_Table.consultoraID.withTable() eq personaId)
            and (PlanRutaRDDEntity_Table.Campania.withTable() eq codigoCampania)
            and (DetallePlanRutaRDDEntity_Table.FechaVisita.withTable().isNotNull))
            .longValue()
    }

    override fun obtenerPlanParaRolDuenio(rol: Rol): Long? {
        val query = (select
            from PlanRutaRDDEntity::class
            where (PlanRutaRDDEntity_Table.Rol eq rol.codigoRol))

        return query.querySingle()?.id?.toLong()
    }

    override fun obtenerCampaniaActualPlanRDD(rol: Rol): String? {
        val query = (select
            from PlanRutaRDDEntity::class
            where (PlanRutaRDDEntity_Table.Rol eq rol.codigoRol))

        return query.querySingle()?.campania
    }
}
