package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.personas

import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity
import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity_Table
import biz.belcorp.salesforce.core.entities.sql.consultora.ConsultoraEntity
import biz.belcorp.salesforce.core.entities.sql.consultora.ConsultoraEntity_Table
import biz.belcorp.salesforce.core.entities.sql.personas.ConsultoraCampaniaJoinned
import biz.belcorp.salesforce.core.entities.sql.plan.DetallePlanRutaRDDEntity
import biz.belcorp.salesforce.core.entities.sql.plan.DetallePlanRutaRDDEntity_Table
import biz.belcorp.salesforce.core.entities.sql.plan.PlanRutaRDDEntity
import biz.belcorp.salesforce.core.entities.sql.plan.PlanRutaRDDEntity_Table
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.visitas.VisitaRddDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.personas.ConsultoraRDDMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.ConsultantDevelopmentPath
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.ConsultoraRdd
import com.raizlabs.android.dbflow.kotlinextensions.and
import com.raizlabs.android.dbflow.kotlinextensions.eq
import com.raizlabs.android.dbflow.kotlinextensions.from
import com.raizlabs.android.dbflow.kotlinextensions.innerJoin
import com.raizlabs.android.dbflow.kotlinextensions.on
import com.raizlabs.android.dbflow.kotlinextensions.orderBy
import com.raizlabs.android.dbflow.kotlinextensions.where
import com.raizlabs.android.dbflow.sql.language.Select

class ConsultoraDBDataStore(
    private val visitaRddDBDataStore: VisitaRddDBDataStore,
    private val consultoraRDDMapper: ConsultoraRDDMapper
) {

    fun obtener(planId: Long): List<ConsultoraRdd> {

        val result = (Select(
            ConsultoraEntity_Table.ConsultorasId.distinct().withTable(),
            ConsultoraEntity_Table.Seccion.withTable(),
            ConsultoraEntity_Table.PrimerNombre.withTable(),
            ConsultoraEntity_Table.SegundoNombre.withTable(),
            ConsultoraEntity_Table.PrimerApellido.withTable(),
            ConsultoraEntity_Table.SegundoApellido.withTable(),
            ConsultoraEntity_Table.CampaniaIngreso.withTable(),
            ConsultoraEntity_Table.Email.withTable(),
            ConsultoraEntity_Table.Cumpleanios.withTable(),
            ConsultoraEntity_Table.Direccion.withTable(),
            ConsultoraEntity_Table.Latitud.withTable(),
            ConsultoraEntity_Table.Longitud.withTable(),
            ConsultoraEntity_Table.TelefonoCelular.withTable(),
            ConsultoraEntity_Table.TelefonoCasa.withTable(),
            ConsultoraEntity_Table.DocumentoIdentidad.withTable(),
            ConsultoraEntity_Table.Codigo.withTable(),
            ConsultoraEntity_Table.Segmento.withTable(),
            ConsultoraEntity_Table.SegmentoInternoFFVV.withTable(),
            ConsultoraEntity_Table.DigVerif.withTable(),
            ConsultoraEntity_Table.CantidadProductoPPU.withTable(),
            ConsultoraEntity_Table.ComparteCatalogo.withTable(),
            ConsultoraEntity_Table.MensajeSugerido.withTable()
        )
            from ConsultoraEntity::class
            innerJoin DetallePlanRutaRDDEntity::class on
            (DetallePlanRutaRDDEntity_Table.ConsultorasID.withTable() eq
                ConsultoraEntity_Table.ConsultorasId.withTable())
            innerJoin PlanRutaRDDEntity::class on
            (DetallePlanRutaRDDEntity_Table.PlanVisitaID.withTable() eq
                PlanRutaRDDEntity_Table.ID.withTable())
            where (DetallePlanRutaRDDEntity_Table.PlanVisitaID.withTable() eq planId)
            and (DetallePlanRutaRDDEntity_Table.Rol.withTable() eq (Rol.CONSULTORA.codigoRol))
            orderBy (DetallePlanRutaRDDEntity_Table.ID.withTable().asc()))

        val totalVisitas = visitaRddDBDataStore.obtenerTodasPorRol(planId)

        return consultoraRDDMapper.parse(result.queryList(), totalVisitas)
    }

    fun getConsultants(planId: Long): List<ConsultantDevelopmentPath> {

        val result = (Select(
            DetallePlanRutaRDDEntity_Table.FechaVisita.withTable(),
            DetallePlanRutaRDDEntity_Table.TipsID.withTable()
        )
            from DetallePlanRutaRDDEntity::class
            innerJoin ConsultoraEntity::class on
            (ConsultoraEntity_Table.ConsultorasId.withTable() eq
                DetallePlanRutaRDDEntity_Table.ConsultorasID.withTable())
            innerJoin PlanRutaRDDEntity::class on
            (DetallePlanRutaRDDEntity_Table.PlanVisitaID.withTable() eq
                PlanRutaRDDEntity_Table.ID.withTable())
            where (DetallePlanRutaRDDEntity_Table.PlanVisitaID.withTable() eq planId)
            and (DetallePlanRutaRDDEntity_Table.Rol.withTable() eq (Rol.CONSULTORA.codigoRol))
            and (DetallePlanRutaRDDEntity_Table.FechaPlanificacion.withTable().isNotNull)
            orderBy (DetallePlanRutaRDDEntity_Table.ID.withTable().asc()))


        return consultoraRDDMapper.mapConsultant(result.queryList())
    }

    fun recuperarPorId(consultoraId: Long): ConsultoraRdd? {
        val query = (
            Select(
                ConsultoraEntity_Table.ConsultorasId.distinct().withTable(),
                ConsultoraEntity_Table.Seccion.withTable(),
                ConsultoraEntity_Table.PrimerNombre.withTable(),
                ConsultoraEntity_Table.SegundoNombre.withTable(),
                ConsultoraEntity_Table.PrimerApellido.withTable(),
                ConsultoraEntity_Table.SegundoApellido.withTable(),
                ConsultoraEntity_Table.CampaniaIngreso.withTable(),
                ConsultoraEntity_Table.Email.withTable(),
                ConsultoraEntity_Table.Cumpleanios.withTable(),
                ConsultoraEntity_Table.Direccion.withTable(),
                ConsultoraEntity_Table.Latitud.withTable(),
                ConsultoraEntity_Table.Longitud.withTable(),
                ConsultoraEntity_Table.TelefonoCelular.withTable(),
                ConsultoraEntity_Table.TelefonoCasa.withTable(),
                ConsultoraEntity_Table.DocumentoIdentidad.withTable(),
                ConsultoraEntity_Table.Codigo.withTable(),
                ConsultoraEntity_Table.Segmento.withTable(),
                ConsultoraEntity_Table.SegmentoInternoFFVV.withTable(),
                ConsultoraEntity_Table.DigVerif.withTable(),
                ConsultoraEntity_Table.VentaRetail.withTable(),
                ConsultoraEntity_Table.RecaudoComisionable.withTable(),
                ConsultoraEntity_Table.RecaudoNoComisionable.withTable(),
                ConsultoraEntity_Table.RecaudoTotal.withTable(),
                ConsultoraEntity_Table.Ganancia.withTable(),
                ConsultoraEntity_Table.VentaFacturada.withTable(),
                ConsultoraEntity_Table.VentaGanancia.withTable(),
                ConsultoraEntity_Table.ConsultoraConsecutiva.withTable(),
                ConsultoraEntity_Table.GananciaRetail.withTable(),
                ConsultoraEntity_Table.CantidadProductoPPU.withTable(),
                ConsultoraEntity_Table.UltimaFacturacion.withTable(),
                ConsultoraEntity_Table.SaldoPendiente.withTable(),
                ConsultoraEntity_Table.Zona.withTable(),
                ConsultoraEntity_Table.Region.withTable(),
                CampaniaUaEntity_Table.Codigo.withTable().`as`("CampaniaCodigo"),
                CampaniaUaEntity_Table.FechaInicio.withTable().`as`("CampaniaInicio"),
                CampaniaUaEntity_Table.FechaFin.withTable().`as`("CampaniaFin"),
                CampaniaUaEntity_Table.FechaInicioFacturacion.withTable()
                    .`as`("CampaniaInicioFacturacion"),
                CampaniaUaEntity_Table.NombreCorto.withTable().`as`("CampaniaNombreCorto"),
                CampaniaUaEntity_Table.Orden.withTable().`as`("CampaniaOrden"),
                CampaniaUaEntity_Table.Periodo.withTable(),
                CampaniaUaEntity_Table.PrimerDiaFacturacion.withTable()
            )

                from ConsultoraEntity::class
                innerJoin CampaniaUaEntity::class
                on ((ConsultoraEntity_Table.Seccion.withTable() eq CampaniaUaEntity_Table.Seccion.withTable())
                and (ConsultoraEntity_Table.Zona.withTable() eq CampaniaUaEntity_Table.Zona.withTable())
                and (ConsultoraEntity_Table.Region.withTable() eq CampaniaUaEntity_Table.Region.withTable()))
                where (ConsultoraEntity_Table.ConsultorasId.withTable() eq consultoraId.toInt())
                and (CampaniaUaEntity_Table.Orden.withTable() eq 1))

        val entidad = query.queryCustomSingle(ConsultoraCampaniaJoinned::class.java)

        var consultora: ConsultoraRdd? = null

        if (entidad != null) {
            consultora = consultoraRDDMapper.parse(entidad)
            val agenda = visitaRddDBDataStore.obtenerVisitasDeConsultora(consultoraId)
            consultora.establecerAgenda(agenda)
            consultora.agenda.establecerPersona(consultora)
        }

        return consultora
    }
}
