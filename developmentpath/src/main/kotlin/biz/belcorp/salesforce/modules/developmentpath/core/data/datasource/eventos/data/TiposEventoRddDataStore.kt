package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.eventos.data

import biz.belcorp.salesforce.core.entities.sql.eventos.TipoEventoRddEntity
import biz.belcorp.salesforce.core.entities.sql.eventos.TipoEventoRddEntity_Table
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import com.raizlabs.android.dbflow.kotlinextensions.eq
import com.raizlabs.android.dbflow.kotlinextensions.from
import com.raizlabs.android.dbflow.kotlinextensions.select
import com.raizlabs.android.dbflow.kotlinextensions.where

class TiposEventoRddDataStore {

    fun recuperar(rol: Rol): List<TipoEventoRddEntity> {
        val where = (select from TipoEventoRddEntity::class
            where (TipoEventoRddEntity_Table.Rol eq rol.codigoRol))
        return where.queryList()
    }

    fun recuperar(): List<TipoEventoRddEntity> {
        val where = (select from TipoEventoRddEntity::class)
        return where.queryList()
    }
}
