package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.unidadadministrativa

import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity
import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity_Table
import biz.belcorp.salesforce.core.entities.sql.directorio.SociaEmpresariaEntity
import biz.belcorp.salesforce.core.entities.sql.directorio.SociaEmpresariaEntity_Table
import biz.belcorp.salesforce.core.entities.sql.plan.PlanRutaRDDEntity
import biz.belcorp.salesforce.core.entities.sql.plan.PlanRutaRDDEntity_Table
import biz.belcorp.salesforce.core.entities.sql.ua.SeccionEntity
import biz.belcorp.salesforce.core.entities.sql.ua.SeccionEntity_Table
import biz.belcorp.salesforce.core.entities.sql.ua.SeccionSociaJoinned
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.unidadadministrativa.SeccionSeMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.SeccionRdd
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.raizlabs.android.dbflow.sql.language.Select

class SeccionDBDataStore(private val seccionAvanceMapper: SeccionSeMapper) {

    fun recuperarParaAvance(codigoZona: String): List<SeccionRdd> {

        val where = (Select(
            SeccionEntity_Table.SeccionId.withTable(),
            SeccionEntity_Table.Codigo.withTable(),
            SeccionEntity_Table.Zona.withTable(),
            SeccionEntity_Table.Region.withTable(),
            SociaEmpresariaEntity_Table.Nivel.withTable(),
            SociaEmpresariaEntity_Table.Exitosa.withTable(),
            SociaEmpresariaEntity_Table.ConsultorasId.withTable(),
            CampaniaUaEntity_Table.Codigo.withTable().`as`("CodigoCampania"),
            CampaniaUaEntity_Table.NombreCorto.withTable(),
            CampaniaUaEntity_Table.FechaInicio.withTable(),
            CampaniaUaEntity_Table.FechaInicioFacturacion.withTable(),
            SociaEmpresariaEntity_Table.PrimerNombre.withTable(),
            SociaEmpresariaEntity_Table.PrimerApellido.withTable(),
            PlanRutaRDDEntity_Table.ID.withTable().`as`("PlanId"),
            PlanRutaRDDEntity_Table.TotalPlanificadas.withTable(),
            PlanRutaRDDEntity_Table.TotalVisitadas.withTable(),
            PlanRutaRDDEntity_Table.DiasVisitando.withTable()
        )

            from SeccionEntity::class

            leftOuterJoin SociaEmpresariaEntity::class
            on (SeccionEntity_Table.ConsultoraCodigo.withTable() eq
            SociaEmpresariaEntity_Table.Codigo.withTable())

            leftOuterJoin CampaniaUaEntity::class
            on ((SeccionEntity_Table.Region.withTable()
            eq CampaniaUaEntity_Table.Region.withTable())
            and (SeccionEntity_Table.Zona.withTable()
            eq CampaniaUaEntity_Table.Zona.withTable())
            and (SeccionEntity_Table.Codigo.withTable()
            eq CampaniaUaEntity_Table.Seccion.withTable())
            and (CampaniaUaEntity_Table.Orden.withTable()
            eq CampaniaUaEntity.ORDEN_CAMPANIA_ACTUAL))

            leftOuterJoin PlanRutaRDDEntity::class
            on ((SeccionEntity_Table.Zona.withTable()
            eq PlanRutaRDDEntity_Table.Zona.withTable())
            and (SeccionEntity_Table.Codigo.withTable()
            eq PlanRutaRDDEntity_Table.Seccion.withTable())
            and (PlanRutaRDDEntity_Table.Campania.withTable()
            eq CampaniaUaEntity_Table.Codigo.withTable())
            and (CampaniaUaEntity_Table.Orden.withTable()
            eq CampaniaUaEntity.ORDEN_CAMPANIA_ACTUAL))

            where (SeccionEntity_Table.Zona.withTable() eq codigoZona)
            orderBy (SeccionEntity_Table.Codigo.withTable().asc()))

        val modelos = where.queryCustomList(SeccionSociaJoinned::class.java)

        return seccionAvanceMapper.parse(modelos)
    }
}
