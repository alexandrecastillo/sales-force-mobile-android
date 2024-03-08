package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.comportamientos

import biz.belcorp.salesforce.core.entities.sql.comportamientos.ComportamientoEntity
import biz.belcorp.salesforce.core.entities.sql.comportamientos.ComportamientoEntity_Table
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import com.raizlabs.android.dbflow.kotlinextensions.eq
import com.raizlabs.android.dbflow.kotlinextensions.from
import com.raizlabs.android.dbflow.kotlinextensions.select
import com.raizlabs.android.dbflow.kotlinextensions.where

class ComportamientosDbStore {

    fun obtener(rol: Rol): List<ComportamientoEntity> {
        val query = (select
            from ComportamientoEntity::class
            where (ComportamientoEntity_Table.Rol.withTable() eq rol.codigoRol))
        return query.queryList()
    }
}
