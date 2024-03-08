package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.focos

import biz.belcorp.salesforce.core.entities.sql.focos.FocoRddEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.arregloVacioSiNull
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.Foco
import com.google.gson.Gson

class FocosMapper {

    private val gson = Gson()

    fun map(serializado: String?, modelos: List<FocoRddEntity>): MutableList<Foco> {
        val ids = gson.fromJson(serializado.arregloVacioSiNull(), Array<Long>::class.java)

        val modelosFiltrados = modelos.filter { ids.contains(it.codigo) }

        return modelosFiltrados.map { Foco(id = it.codigo, descripcion = it.descripcion) }.toMutableList()
    }
}
