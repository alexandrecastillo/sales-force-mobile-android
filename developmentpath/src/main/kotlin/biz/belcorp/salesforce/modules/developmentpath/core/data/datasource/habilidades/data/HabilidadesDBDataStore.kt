package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.habilidades.data

import biz.belcorp.salesforce.core.entities.sql.habilidades.HabilidadEntity
import biz.belcorp.salesforce.core.entities.sql.habilidades.HabilidadEntity_Table
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.habilidades.HabilidadMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.habilidades.Habilidad
import com.raizlabs.android.dbflow.kotlinextensions.from
import com.raizlabs.android.dbflow.kotlinextensions.select
import com.raizlabs.android.dbflow.kotlinextensions.where

class HabilidadesDBDataStore(private val habilidadMapper: HabilidadMapper) {

    fun obtener(rol: Rol): List<Habilidad> {
        val codigoRol = rol.codigoRol
        val where = select from HabilidadEntity::class where HabilidadEntity_Table.Rol.eq(codigoRol)
        return habilidadMapper.parsearHabilidades(where.queryList())
    }
}
