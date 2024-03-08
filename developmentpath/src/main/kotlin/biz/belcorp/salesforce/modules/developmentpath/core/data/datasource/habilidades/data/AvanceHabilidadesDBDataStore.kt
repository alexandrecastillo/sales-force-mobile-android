package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.habilidades.data

import biz.belcorp.salesforce.core.entities.sql.habilidades.ReconocimientoHabilidadesRDDEntity
import biz.belcorp.salesforce.core.entities.sql.habilidades.ReconocimientoHabilidadesRDDEntity_Table
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import com.google.gson.Gson
import com.raizlabs.android.dbflow.kotlinextensions.*

class AvanceHabilidadesDBDataStore {

    private val gson = Gson()

    fun getAvanceHabilidades(zona: String? = null, region: String, campania: String): Pair<String, Array<Long>> {

        var query = (select from ReconocimientoHabilidadesRDDEntity::class
            where (ReconocimientoHabilidadesRDDEntity_Table.Campania eq campania)
            and (ReconocimientoHabilidadesRDDEntity_Table.Region eq region))

        if (zona != null) {
            query = query and (ReconocimientoHabilidadesRDDEntity_Table.Zona eq zona)
        }

        val result = query.querySingle()
        val habilidades = gson.fromJson(result?.habilidades ?: Constant.EMPTY_ARRAY, Array<Long>::class.java)

        return Pair(campania, habilidades)
    }

}
