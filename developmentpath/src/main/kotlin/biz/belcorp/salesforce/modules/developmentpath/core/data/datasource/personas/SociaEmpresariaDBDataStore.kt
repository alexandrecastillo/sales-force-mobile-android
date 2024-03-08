package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.personas

import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity
import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity_Table
import biz.belcorp.salesforce.core.entities.sql.directorio.SociaEmpresariaEntity
import biz.belcorp.salesforce.core.entities.sql.directorio.SociaEmpresariaEntity_Table
import biz.belcorp.salesforce.core.entities.sql.personas.SociaEmpresariaSeccionJoinned
import biz.belcorp.salesforce.core.entities.sql.plan.DetallePlanRutaRDDEntity
import biz.belcorp.salesforce.core.entities.sql.plan.DetallePlanRutaRDDEntity_Table
import biz.belcorp.salesforce.core.entities.sql.plan.PlanRutaRDDEntity
import biz.belcorp.salesforce.core.entities.sql.plan.PlanRutaRDDEntity_Table
import biz.belcorp.salesforce.core.entities.sql.ua.SeccionEntity
import biz.belcorp.salesforce.core.entities.sql.ua.SeccionEntity_Table
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.personas.SociaEmpresariaMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.SociaEmpresariaRdd
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.raizlabs.android.dbflow.sql.language.Select

class SociaEmpresariaDBDataStore(private val sociaEmpresariaMapper: SociaEmpresariaMapper) {

    fun obtener(planId: Long): List<SociaEmpresariaRdd> {

        val where = (Select(
            SociaEmpresariaEntity_Table.ConsultorasId.withTable().distinct(),
            SociaEmpresariaEntity_Table.Codigo.withTable(),
            SociaEmpresariaEntity_Table.DocumentoIdentidad.withTable(),
            SociaEmpresariaEntity_Table.PrimerNombre.withTable(),
            SociaEmpresariaEntity_Table.SegundoNombre.withTable(),
            SociaEmpresariaEntity_Table.PrimerApellido.withTable(),
            SociaEmpresariaEntity_Table.SegundoApellido.withTable(),
            SociaEmpresariaEntity_Table.Email.withTable(),
            SociaEmpresariaEntity_Table.TelefonoCelular.withTable(),
            SociaEmpresariaEntity_Table.TelefonoCasa.withTable(),
            SociaEmpresariaEntity_Table.TelefonoTrabajo.withTable(),
            SociaEmpresariaEntity_Table.Cumpleanios.withTable(),
            SociaEmpresariaEntity_Table.Latitud.withTable(),
            SociaEmpresariaEntity_Table.Longitud.withTable(),
            SociaEmpresariaEntity_Table.Direccion.withTable(),
            SociaEmpresariaEntity_Table.Zona.withTable(),
            SeccionEntity_Table.Codigo.withTable().`as`("Seccion"),
            SociaEmpresariaEntity_Table.Nivel.withTable()
        )

            from SociaEmpresariaEntity::class
            innerJoin DetallePlanRutaRDDEntity::class on
            (SociaEmpresariaEntity_Table.ConsultorasId.withTable() eq
                DetallePlanRutaRDDEntity_Table.ConsultorasID.withTable())
            innerJoin PlanRutaRDDEntity::class on
            (DetallePlanRutaRDDEntity_Table.PlanVisitaID.withTable() eq
                PlanRutaRDDEntity_Table.ID.withTable())
            innerJoin SeccionEntity::class on
            (SociaEmpresariaEntity_Table.Codigo.withTable() eq
                SeccionEntity_Table.ConsultoraCodigo.withTable())

            where (DetallePlanRutaRDDEntity_Table.PlanVisitaID.withTable() eq planId)
            and (DetallePlanRutaRDDEntity_Table.Rol.withTable() eq (Rol.SOCIA_EMPRESARIA.codigoRol))
            orderBy DetallePlanRutaRDDEntity_Table.ID.withTable().asc())

        return sociaEmpresariaMapper.parseList(where.queryCustomList(SociaEmpresariaSeccionJoinned::class.java))
    }

    fun recuperarPorId(sociaId: Long): SociaEmpresariaRdd? {
        val query = (Select(
            SociaEmpresariaEntity_Table.ConsultorasId.withTable(),
            SociaEmpresariaEntity_Table.Codigo.withTable(),
            SociaEmpresariaEntity_Table.DocumentoIdentidad.withTable(),
            SociaEmpresariaEntity_Table.PrimerNombre.withTable(),
            SociaEmpresariaEntity_Table.SegundoNombre.withTable(),
            SociaEmpresariaEntity_Table.PrimerApellido.withTable(),
            SociaEmpresariaEntity_Table.SegundoApellido.withTable(),
            SociaEmpresariaEntity_Table.Email.withTable(),
            SociaEmpresariaEntity_Table.TelefonoCelular.withTable(),
            SociaEmpresariaEntity_Table.TelefonoCasa.withTable(),
            SociaEmpresariaEntity_Table.TelefonoTrabajo.withTable(),
            SociaEmpresariaEntity_Table.Cumpleanios.withTable(),
            SociaEmpresariaEntity_Table.Latitud.withTable(),
            SociaEmpresariaEntity_Table.Longitud.withTable(),
            SociaEmpresariaEntity_Table.Direccion.withTable(),
            SociaEmpresariaEntity_Table.Zona.withTable(),
            SociaEmpresariaEntity_Table.CampaniaIngreso.withTable(),
            SociaEmpresariaEntity_Table.UltimaFacturacion.withTable(),
            SociaEmpresariaEntity_Table.SaldoPendiente.withTable(),
            SociaEmpresariaEntity_Table.VentaGanancia.withTable(),
            SociaEmpresariaEntity_Table.ConsultoraConsecutiva.withTable(),
            SociaEmpresariaEntity_Table.VentaFacturada.withTable(),
            SociaEmpresariaEntity_Table.RecaudoComisionable.withTable(),
            SociaEmpresariaEntity_Table.Ganancia.withTable(),
            SociaEmpresariaEntity_Table.RecaudoTotal.withTable(),
            SociaEmpresariaEntity_Table.RecaudoNoComisionable.withTable(),
            SociaEmpresariaEntity_Table.GananciaRetail.withTable(),
            SociaEmpresariaEntity_Table.VentaRetail.withTable(),
            SociaEmpresariaEntity_Table.FechaNacimiento.withTable(),
            SociaEmpresariaEntity_Table.FechaAniversario.withTable(),
            SeccionEntity_Table.Codigo.withTable().`as`("Seccion"),
            SeccionEntity_Table.Region.withTable(),
            SociaEmpresariaEntity_Table.Nivel.withTable(),
            SociaEmpresariaEntity_Table.Exitosa.withTable(),
            CampaniaUaEntity_Table.Codigo.withTable().`as`("CodigoCampania"),
            CampaniaUaEntity_Table.FechaInicio.withTable(),
            CampaniaUaEntity_Table.FechaFin.withTable(),
            CampaniaUaEntity_Table.FechaInicioFacturacion.withTable(),
            CampaniaUaEntity_Table.NombreCorto.withTable(),
            CampaniaUaEntity_Table.Orden.withTable(),
            CampaniaUaEntity_Table.Periodo.withTable(),
            CampaniaUaEntity_Table.PrimerDiaFacturacion.withTable()
        )

            from SociaEmpresariaEntity::class
            innerJoin SeccionEntity::class on
            (SociaEmpresariaEntity_Table.Codigo.withTable() eq
                SeccionEntity_Table.ConsultoraCodigo.withTable())
            innerJoin CampaniaUaEntity::class on
            (CampaniaUaEntity_Table.Seccion.withTable() eq SociaEmpresariaEntity_Table.Seccion.withTable()
                and (CampaniaUaEntity_Table.Zona.withTable() eq SociaEmpresariaEntity_Table.Zona.withTable())
                and (CampaniaUaEntity_Table.Region.withTable() eq SociaEmpresariaEntity_Table.Region.withTable()))
            where (SociaEmpresariaEntity_Table.ConsultorasId.withTable() eq sociaId)
            and (CampaniaUaEntity_Table.Orden.withTable() eq 1))

        val modelo = query.queryCustomSingle(SociaEmpresariaSeccionJoinned::class.java)
            ?: return null

        return sociaEmpresariaMapper.parse(modelo)
    }

    fun recuperarIdDeSociaSegunCodigo(codigo: String): Long? {
        return (select from SociaEmpresariaEntity::class
            where (SociaEmpresariaEntity_Table.Codigo eq codigo))
            .querySingle()?.consultorasId

    }
}
