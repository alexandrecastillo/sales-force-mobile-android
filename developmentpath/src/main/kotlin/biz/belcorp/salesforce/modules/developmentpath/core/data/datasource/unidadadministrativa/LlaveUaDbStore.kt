package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.unidadadministrativa

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.exceptions.UnsupportedRoleException
import biz.belcorp.salesforce.core.entities.sql.consultora.ConsultoraEntity
import biz.belcorp.salesforce.core.entities.sql.consultora.ConsultoraEntity_Table
import biz.belcorp.salesforce.core.entities.sql.directorio.DirectorioVentaUsuarioEntity
import biz.belcorp.salesforce.core.entities.sql.directorio.DirectorioVentaUsuarioEntity_Table
import biz.belcorp.salesforce.core.entities.sql.directorio.SociaEmpresariaEntity
import biz.belcorp.salesforce.core.entities.sql.directorio.SociaEmpresariaEntity_Table
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import com.raizlabs.android.dbflow.kotlinextensions.*

class LlaveUaDbStore {

    fun recuperarLlaveUaPorIdRol(personaId: Long, rol: Rol): LlaveUA? {
        return when(rol) {
            Rol.CONSULTORA -> recuperarLlaveDeConsultora(personaId)
            Rol.SOCIA_EMPRESARIA -> recuperarLlaveDeSocia(personaId)
            Rol.GERENTE_ZONA -> recuperarLlaveDeGerenteZona(personaId)
            Rol.GERENTE_REGION -> recuperarLlaveDeGerenteRegion(personaId)
            else -> throw UnsupportedRoleException(rol)
        }
    }

    private fun recuperarLlaveDeConsultora(personaId: Long): LlaveUA? {
        val where = (select from ConsultoraEntity::class
            where (ConsultoraEntity_Table.ConsultorasId.withTable() eq personaId.toInt()))
        val modelo = where.querySingle() ?: return null
        return LlaveUA(
            modelo.region ?: return null,
            modelo.zona ?: return null,
            modelo.seccion ?: return null,
            modelo.consultorasId.toLong()
        )
    }

    private fun recuperarLlaveDeSocia(personaId: Long): LlaveUA? {
        val where = (select from SociaEmpresariaEntity::class
            where (SociaEmpresariaEntity_Table.ConsultorasId.withTable() eq personaId))
        val modelo = where.querySingle() ?: return null
        return LlaveUA(
            modelo.region ?: return null,
            modelo.zona ?: return null,
            modelo.seccion ?: return null,
            null
        )
    }

    private fun recuperarLlaveDeGerenteZona(personaId: Long): LlaveUA? {
        val where = (select from DirectorioVentaUsuarioEntity::class
            where (DirectorioVentaUsuarioEntity_Table.consultoraID.withTable() eq personaId)
            and (DirectorioVentaUsuarioEntity_Table.CodRol.withTable() eq Rol.GERENTE_ZONA.codigoRol))
        val modelo = where.querySingle() ?: return null
        return LlaveUA(
            modelo.codRegion ?: return null,
            modelo.codZona ?: return null,
            null,
            null
        )
    }

    private fun recuperarLlaveDeGerenteRegion(personaId: Long): LlaveUA? {
        val where = (select from DirectorioVentaUsuarioEntity::class
            where (DirectorioVentaUsuarioEntity_Table.consultoraID.withTable() eq personaId)
            and (DirectorioVentaUsuarioEntity_Table.CodRol.withTable() eq Rol.GERENTE_REGION.codigoRol))
        val modelo = where.querySingle() ?: return null
        return LlaveUA(
            modelo.codRegion ?: return null,
            null,
            null,
            null
        )
    }
}
