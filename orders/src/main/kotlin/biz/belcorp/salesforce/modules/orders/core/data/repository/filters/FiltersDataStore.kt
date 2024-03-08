package biz.belcorp.salesforce.modules.orders.core.data.repository.filters

import biz.belcorp.salesforce.core.entities.sql.filtros.TipoEstadoEntity
import biz.belcorp.salesforce.core.entities.sql.filtros.TipoSaldoEntity
import biz.belcorp.salesforce.core.entities.sql.filtros.TipoSegmentoEntity
import biz.belcorp.salesforce.core.entities.sql.ua.SeccionEntity
import biz.belcorp.salesforce.core.entities.sql.ua.SeccionEntity_Table
import com.raizlabs.android.dbflow.kotlinextensions.from
import com.raizlabs.android.dbflow.kotlinextensions.select


class FiltersDataStore {

    fun getTipoEstadoFilter(): List<TipoEstadoEntity> {
        return (select from TipoEstadoEntity::class).queryList()
    }

    fun getTipoSaldoFilter(): List<TipoSaldoEntity> {
        return (select from TipoSaldoEntity::class).queryList()
    }

    fun getTipoSegmentoFilter(): List<TipoSegmentoEntity> {
        return (select from TipoSegmentoEntity::class).queryList()
    }

    fun getSeccionesFilter(): List<SeccionEntity> {
        return (select from SeccionEntity::class).orderBy(SeccionEntity_Table.Codigo, true).queryList()
    }

}
