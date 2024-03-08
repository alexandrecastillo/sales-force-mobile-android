package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.reconocimiento.data

import biz.belcorp.salesforce.core.entities.sql.directorio.UsuarioPadreSesionEntity
import com.raizlabs.android.dbflow.kotlinextensions.from
import com.raizlabs.android.dbflow.kotlinextensions.select

class MadreLocalDataStore {
    fun recuperar(): UsuarioPadreSesionEntity? {
        val query = (select from UsuarioPadreSesionEntity::class)
        return query.querySingle()
    }
}
