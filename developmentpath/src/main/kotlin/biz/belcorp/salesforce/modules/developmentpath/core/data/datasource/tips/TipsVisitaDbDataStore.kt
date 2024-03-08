package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.tips

import biz.belcorp.salesforce.core.domain.exceptions.UnsupportedRoleException
import biz.belcorp.salesforce.core.entities.sql.directorio.DirectorioVentaUsuarioEntity
import biz.belcorp.salesforce.core.entities.sql.directorio.DirectorioVentaUsuarioEntity_Table
import biz.belcorp.salesforce.core.entities.sql.plan.DetallePlanRutaRDDEntity
import biz.belcorp.salesforce.core.entities.sql.plan.DetallePlanRutaRDDEntity_Table
import biz.belcorp.salesforce.core.entities.sql.tips.rdd.DetalleTipsVisitaRDDEntity
import biz.belcorp.salesforce.core.entities.sql.tips.rdd.DetalleTipsVisitaRDDEntity_Table
import biz.belcorp.salesforce.core.entities.sql.tips.rdd.TipsVisitaRDDEntity
import biz.belcorp.salesforce.core.entities.sql.tips.rdd.TipsVisitaRDDEntity_Table
import biz.belcorp.salesforce.core.entities.sql.unete.PostulanteEntity
import biz.belcorp.salesforce.core.entities.sql.unete.PostulanteEntity_Table
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tips.TipsVisitaMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.GrupoTipsVisita
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.TipVisita
import com.raizlabs.android.dbflow.kotlinextensions.*

class TipsVisitaDbDataStore(private val tipsVisitaMapper: TipsVisitaMapper) {

    fun obtenerTipsDePersona(personaId: Long, rol: Rol): GrupoTipsVisita? {
        val cabeceraTipsId = obtenerIdCabeceraTip(personaId, rol) ?: return null

        return obtenerGrupoYDetalleTips(cabeceraTipsId)
    }

    fun obtenerIdCabeceraTip(personaId: Long, rol: Rol): Long? {
        return when (rol) {
            Rol.POSIBLE_CONSULTORA -> obtenerIdCabeceraTipsPosibleConsultora(personaId)
            Rol.CONSULTORA -> obtenerIDCabeceraTipsConsultora(personaId)
            Rol.SOCIA_EMPRESARIA -> obtenerIdCabeceraTipsSocia(personaId)
            Rol.GERENTE_ZONA -> obtenerIdCabeceraTipsGz(personaId)
            Rol.GERENTE_REGION -> obtenerIdCabeceraTipsGr(personaId)
            else -> throw UnsupportedRoleException(rol)
        }
    }

    private fun obtenerIDCabeceraTipsConsultora(personaId: Long): Long? {
        val query = (select
            from DetallePlanRutaRDDEntity::class
            where (DetallePlanRutaRDDEntity_Table.ConsultorasID.withTable() eq personaId)
            and (DetallePlanRutaRDDEntity_Table.Rol.withTable() eq Rol.CONSULTORA.codigoRol))

        return query.querySingle()?.tipsid?.toLong()
    }

    private fun obtenerIdCabeceraTipsSocia(personaId: Long): Long? {
        val query = (select
            from DetallePlanRutaRDDEntity::class
            where (DetallePlanRutaRDDEntity_Table.ConsultorasID.withTable() eq personaId)
            and (DetallePlanRutaRDDEntity_Table.Rol.withTable() eq Rol.SOCIA_EMPRESARIA.codigoRol))

        return query.querySingle()?.tipsid?.toLong()
    }

    private fun obtenerIdCabeceraTipsPosibleConsultora(personaId: Long): Long? {
        val query = (select
            from DetallePlanRutaRDDEntity::class
            innerJoin (PostulanteEntity::class) on
            (PostulanteEntity_Table.NumeroDocumento.withTable() eq
                DetallePlanRutaRDDEntity_Table.NumeroDocumento.withTable())
            where (PostulanteEntity_Table.SolicitudPostulanteID.withTable() eq personaId.toInt())
            and (DetallePlanRutaRDDEntity_Table.Rol.withTable() eq Rol.POSIBLE_CONSULTORA.codigoRol))

        return query.querySingle()?.tipsid?.toLong()
    }

    private fun obtenerIdCabeceraTipsGz(personaId: Long): Long? {
        val query = (select
            from DetallePlanRutaRDDEntity::class
            innerJoin DirectorioVentaUsuarioEntity::class on
            (DetallePlanRutaRDDEntity_Table.CodigoConsultora.withTable() eq
                DirectorioVentaUsuarioEntity_Table.Usuario.withTable())
            where (DirectorioVentaUsuarioEntity_Table.consultoraID.withTable() eq personaId)
            and (DetallePlanRutaRDDEntity_Table.Rol.withTable() eq Rol.GERENTE_ZONA.codigoRol))

        return query.querySingle()?.tipsid?.toLong()
    }

    private fun obtenerIdCabeceraTipsGr(personaId: Long): Long? {
        val query = (select
            from DetallePlanRutaRDDEntity::class
            innerJoin DirectorioVentaUsuarioEntity::class on
            (DetallePlanRutaRDDEntity_Table.CodigoConsultora.withTable() eq
                DirectorioVentaUsuarioEntity_Table.Usuario.withTable())
            where (DirectorioVentaUsuarioEntity_Table.consultoraID.withTable() eq personaId)
            and (DetallePlanRutaRDDEntity_Table.Rol.withTable() eq Rol.GERENTE_REGION.codigoRol))

        return query.querySingle()?.tipsid?.toLong()
    }

    private fun obtenerGrupoYDetalleTips(cabeceraId: Long): GrupoTipsVisita? {
        val grupo = obtenerGrupoTips(cabeceraId) ?: return null
        val tips = obtenerTips(cabeceraId)

        grupo.tips = tips
        tips.forEach { it.grupo = grupo }

        return grupo
    }

    private fun obtenerGrupoTips(cabeceraId: Long): GrupoTipsVisita? {
        val query = (select
            from TipsVisitaRDDEntity::class
            where (TipsVisitaRDDEntity_Table.ID.withTable() eq cabeceraId.toInt()))

        val model = checkNotNull(query.querySingle()) { "Tips no disponibles" }

        return tipsVisitaMapper.map(model)
    }

    private fun obtenerTips(cabeceraId: Long): List<TipVisita> {
        val query = (select
            from DetalleTipsVisitaRDDEntity::class
            where (DetalleTipsVisitaRDDEntity_Table.TipsID.withTable() eq
            cabeceraId.toString()))

        return tipsVisitaMapper.map(query.queryList())
    }
}
