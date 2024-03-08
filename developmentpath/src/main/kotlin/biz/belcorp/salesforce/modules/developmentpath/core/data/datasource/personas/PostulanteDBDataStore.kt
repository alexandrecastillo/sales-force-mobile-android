package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.personas

import biz.belcorp.salesforce.core.entities.sql.perfil.PostulanteDetallePlanJoinned
import biz.belcorp.salesforce.core.entities.sql.plan.DetallePlanRutaRDDEntity
import biz.belcorp.salesforce.core.entities.sql.plan.DetallePlanRutaRDDEntity_Table
import biz.belcorp.salesforce.core.entities.sql.unete.PostulanteEntity
import biz.belcorp.salesforce.core.entities.sql.unete.PostulanteEntity_Table
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.perfil.PosibleConsultoraMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PosibleConsultoraRdd
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.raizlabs.android.dbflow.sql.language.Select

class PostulanteDBDataStore(private val posibleConsultoraMapper: PosibleConsultoraMapper) {

    fun recuperarPorId(id: Long): PosibleConsultoraRdd? {
        val posibleConsultoraModel = (seleccionarColumnas()
            from PostulanteEntity::class
            innerJoin DetallePlanRutaRDDEntity::class on (
            PostulanteEntity_Table.NumeroDocumento.withTable() eq
                DetallePlanRutaRDDEntity_Table.NumeroDocumento.withTable())
            where (PostulanteEntity_Table.SolicitudPostulanteID.withTable() eq id.toInt()))
            .queryCustomSingle(PostulanteDetallePlanJoinned::class.java) ?: return null

        return posibleConsultoraMapper.parse(posibleConsultoraModel)
    }

    fun obtener(planId: Long): List<PosibleConsultoraRdd> {
        val result = (seleccionarColumnas()
            from DetallePlanRutaRDDEntity::class
            innerJoin PostulanteEntity::class on (
            PostulanteEntity_Table.NumeroDocumento.withTable() eq
                DetallePlanRutaRDDEntity_Table.NumeroDocumento.withTable())
            where (DetallePlanRutaRDDEntity_Table.PlanVisitaID.withTable() eq planId)
            and (DetallePlanRutaRDDEntity_Table.Rol eq Rol.POSIBLE_CONSULTORA.codigoRol))

        return posibleConsultoraMapper.parse(result.queryCustomList(PostulanteDetallePlanJoinned::class.java)
            .distinctBy { it.numeroDocumento })
    }

    private fun seleccionarColumnas(): Select {
        return Select(
            PostulanteEntity_Table.SolicitudPostulanteID.withTable(),
            PostulanteEntity_Table.NumeroDocumento.withTable(),
            PostulanteEntity_Table.PrimerNombre.withTable(),
            PostulanteEntity_Table.SegundoNombre.withTable(),
            PostulanteEntity_Table.ApellidoPaterno.withTable(),
            PostulanteEntity_Table.ApellidoMaterno.withTable(),
            PostulanteEntity_Table.Telefono.withTable(),
            PostulanteEntity_Table.Celular.withTable(),
            PostulanteEntity_Table.CorreoElectronico.withTable(),
            PostulanteEntity_Table.Direccion.withTable(),
            PostulanteEntity_Table.Latitud.withTable(),
            PostulanteEntity_Table.Longitud.withTable(),
            PostulanteEntity_Table.EstadoPostulante.withTable(),
            PostulanteEntity_Table.FuenteIngreso.withTable(),
            PostulanteEntity_Table.FechaNacimiento.withTable(),
            DetallePlanRutaRDDEntity_Table.ID.withTable().`as`("DetallePlanId"),
            DetallePlanRutaRDDEntity_Table.PlanVisitaID.withTable(),
            DetallePlanRutaRDDEntity_Table.FechaPlanificacion.withTable(),
            DetallePlanRutaRDDEntity_Table.FechaReprogramacion.withTable(),
            DetallePlanRutaRDDEntity_Table.FechaVisita.withTable(),
            DetallePlanRutaRDDEntity_Table.Planificado.withTable()
        )
    }
}
