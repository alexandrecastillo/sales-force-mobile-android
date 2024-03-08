package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.desempenio

import biz.belcorp.salesforce.core.entities.sql.datos.DesempenioEntity
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.desempenio.DesempenioCampanias
import com.google.gson.Gson

class DesempenioMapper {

    val gson = Gson()

    fun parse(entity: DesempenioEntity): List<DesempenioCampanias> {
        val desempenios: MutableList<DesempenioCampanias> = mutableListOf()
        val estado = gson.fromJson(entity.estado ?: "[]", Array<String>::class.java)
        (0..5).mapTo(desempenios) {
            DesempenioCampanias(estado = estado[it], campania = "")
        }
        return desempenios
    }
}
