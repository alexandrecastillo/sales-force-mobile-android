package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.focos.propios

import biz.belcorp.salesforce.core.entities.sql.focos.FocoDetalleRddEntity
import biz.belcorp.salesforce.core.entities.sql.focos.FocoRddEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.Foco
import com.google.gson.Gson

class MisFocosDataMapper {

    val gson = Gson()

    fun parse(focosMaestros: List<FocoRddEntity>, focosDetalle: FocoDetalleRddEntity): List<Foco> {

        val idsFocos =
            gson.fromJson(focosDetalle.focos ?: Constant.EMPTY_ARRAY, Array<Long>::class.java)
        val filtrados = focosMaestros.filter { it.codigo in idsFocos }
        return filtrados.map { parse(it) }
    }

    private fun parse(focosMaestro: FocoRddEntity) =
        Foco(id = focosMaestro.codigo, descripcion = focosMaestro.descripcion)
}
